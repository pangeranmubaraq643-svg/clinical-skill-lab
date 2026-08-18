package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.CaseEntity
import com.example.data.model.CaseLevel
import com.example.data.model.ChatMessage
import com.example.data.model.ChatSender
import com.example.data.model.ClinicalCase
import com.example.data.model.CitoActionFeedback
import com.example.data.model.CitoImpactType
import com.example.data.model.EvaluationResult
import com.example.data.model.ExamCategory
import com.example.data.model.ExamItem
import com.example.data.model.OrganSystem
import com.example.data.model.SimulationStage
import com.example.data.model.TimeMode
import com.example.data.model.UserExamResult
import com.example.data.remote.GeminiService
import com.example.data.repository.BuiltInCases
import com.example.data.repository.CaseLevelEvaluator
import com.example.data.repository.CitoEvaluator
import com.example.data.repository.MasterExamsCatalog
import com.example.data.repository.LogCategory
import com.example.data.repository.LogLevel
import com.example.data.repository.MedicalDebugLogger
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SimulationUiState(
    val currentStage: SimulationStage = SimulationStage.SETUP,
    val selectedOrganSystem: OrganSystem = OrganSystem.RANDOM,
    val selectedTimeMode: TimeMode = TimeMode.STANDARD,
    val selectedLevel: CaseLevel = CaseLevel.ALL,
    val selectedDifficulty: com.example.data.model.DifficultyLevel = com.example.data.model.DifficultyLevel.BASIC,
    val activeCase: ClinicalCase? = null,
    val remainingSeconds: Int = 15 * 60,
    val isTimerRunning: Boolean = false,
    
    // Cito / Stabilization Realtime Feedback
    val lastCitoActionFeedback: CitoActionFeedback? = null,
    val citoActionLogs: List<CitoActionFeedback> = emptyList(),
    val isEvaluatingCitoAction: Boolean = false,
    
    // Anamnesis Chat State
    val chatHistory: List<ChatMessage> = emptyList(),
    val isPatientTyping: Boolean = false,
    
    // Pemfis & Lab State
    val userExams: List<UserExamResult> = emptyList(),
    val totalSpentRupiah: Long = 0L,
    val isRequestingAiExam: Boolean = false,
    
    // Diagnosis & Tatalaksana Inputs
    val primaryDiagnosisInput: String = "",
    val differentialDiagnosisInput: String = "",
    val treatmentInput: String = "",
    val educationInput: String = "",
    val geminiSuggestionText: String? = null,
    val isLoadingGeminiSuggestion: Boolean = false,
    
    // AI Clinical Decision Support (Stage 5)
    val aiDiagnosisSuggestionText: String? = null,
    val isLoadingAiDiagnosis: Boolean = false,
    val aiDiffSuggestionList: List<String>? = null,
    val isLoadingAiDiff: Boolean = false,
    val aiMedicationSuggestionText: String? = null,
    val isLoadingAiMedication: Boolean = false,
    val onlineNoticeMessage: String? = null,
    
    // Evaluation Result
    val evaluationResult: EvaluationResult? = null,
    val isCaseSaved: Boolean = false,
    val isEvaluatingWithGemini: Boolean = false,
    
    // Navigation tab
    val currentBottomNav: Int = 0, // 0: Case Sim, 1: History, 2: PPK Reference
    val isGeneratingAiCase: Boolean = false,
    val caseGenerationError: String? = null,

    // Clinical Hint Feature
    val hintsUnlockedCount: Int = 0,
    val hintsPenaltyPoints: Int = 0,
    val isClinicalHintModalOpen: Boolean = false
)

class SimulationViewModel(application: Application) : AndroidViewModel(application) {

    private val caseDao = AppDatabase.getDatabase(application).caseDao()

    val caseHistory: StateFlow<List<CaseEntity>> = caseDao.getAllCases()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiState = MutableStateFlow(SimulationUiState())
    val uiState: StateFlow<SimulationUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun selectBottomNav(index: Int) {
        _uiState.value = _uiState.value.copy(currentBottomNav = index)
    }

    fun selectCaseLevel(level: CaseLevel) {
        _uiState.value = _uiState.value.copy(selectedLevel = level)
    }

    fun selectDifficultyLevel(difficulty: com.example.data.model.DifficultyLevel) {
        _uiState.value = _uiState.value.copy(selectedDifficulty = difficulty)
    }

    fun dismissCaseGenerationError() {
        _uiState.value = _uiState.value.copy(caseGenerationError = null)
    }

    fun updateSetupSelection(organ: OrganSystem, mode: TimeMode = TimeMode.STANDARD, level: CaseLevel = _uiState.value.selectedLevel, difficulty: com.example.data.model.DifficultyLevel = _uiState.value.selectedDifficulty) {
        _uiState.value = _uiState.value.copy(
            selectedOrganSystem = organ,
            selectedTimeMode = mode,
            selectedLevel = level,
            selectedDifficulty = difficulty
        )
    }

    fun startNewCase() {
        if (!com.example.util.NetworkHelper.isNetworkAvailable(getApplication())) {
            _uiState.value = _uiState.value.copy(
                isGeneratingAiCase = false,
                caseGenerationError = "Perangkat tidak terhubung ke jaringan internet. Pembuatan kasus baru memerlukan koneksi internet aktif (mode offline dinonaktifkan untuk pembuatan kasus/permainan baru). Silakan aktifkan koneksi internet Anda."
            )
            return
        }

        val organ = _uiState.value.selectedOrganSystem.displayName
        val mode = _uiState.value.selectedTimeMode
        val level = _uiState.value.selectedLevel
        val difficulty = _uiState.value.selectedDifficulty

        // Ambil diagnosis acak yang merata dan bebas repetisi dari katalog medis
        val randomDiagnosis = com.example.data.repository.MedicalCatalog.pickTrulyRandomDiagnosis(
            organSystem = organ,
            isEmergencyOnly = level == CaseLevel.LEVEL_3 || mode.isEmergency
        )

        _uiState.value = _uiState.value.copy(
            isGeneratingAiCase = true,
            caseGenerationError = null
        )
        viewModelScope.launch {
            try {
                val generatedCase = GeminiService.generateDynamicCaseFromGemini(
                    organSystem = organ,
                    specificDiagnosis = randomDiagnosis,
                    isEmergency = level == CaseLevel.LEVEL_3 || mode.isEmergency,
                    difficultyLevel = difficulty
                )
                setupCaseInternal(generatedCase, mode, level)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    caseGenerationError = e.message ?: "Gagal membuat kasus baru: Masalah koneksi internet."
                )
            } finally {
                _uiState.value = _uiState.value.copy(isGeneratingAiCase = false)
            }
        }
    }

    fun startSpecificCaseByDiagnosis(diagnosisName: String) {
        if (!com.example.util.NetworkHelper.isNetworkAvailable(getApplication())) {
            _uiState.value = _uiState.value.copy(
                isGeneratingAiCase = false,
                caseGenerationError = "Perangkat tidak terhubung ke jaringan internet. Pembuatan kasus baru memerlukan koneksi internet aktif (mode offline dinonaktifkan untuk pembuatan kasus/permainan baru). Silakan aktifkan koneksi internet Anda."
            )
            return
        }

        val mode = _uiState.value.selectedTimeMode
        val level = _uiState.value.selectedLevel
        val difficulty = _uiState.value.selectedDifficulty
        
        _uiState.value = _uiState.value.copy(
            isGeneratingAiCase = true,
            caseGenerationError = null
        )
        viewModelScope.launch {
            try {
                val generatedCase = GeminiService.generateDynamicCaseFromGemini(
                    organSystem = _uiState.value.selectedOrganSystem.displayName,
                    specificDiagnosis = diagnosisName,
                    isEmergency = level == CaseLevel.LEVEL_3 || mode.isEmergency,
                    difficultyLevel = difficulty
                )
                setupCaseInternal(generatedCase, mode, level)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    caseGenerationError = e.message ?: "Gagal membuat kasus baru: Masalah koneksi internet."
                )
            } finally {
                _uiState.value = _uiState.value.copy(isGeneratingAiCase = false)
            }
        }
    }

    private fun setupCaseInternal(selectedCase: ClinicalCase, mode: TimeMode, levelFilter: CaseLevel = _uiState.value.selectedLevel) {
        // Enforce 100% medically accurate and coherent vital signs (TTV) according to disease pathophysiology & age
        val calibratedCase = com.example.data.repository.MedicalVitalsValidator.validateAndCalibrateCase(selectedCase)

        // Reset state for new case
        timerJob?.cancel()
        val actualLevel = if (levelFilter != CaseLevel.ALL) levelFilter else CaseLevelEvaluator.evaluate(calibratedCase)
        val initialSeconds = actualLevel.timeMinutes * 60

        val initialGreeting = GeminiService.formatInitialPatientGreeting(calibratedCase)

        val levelBadge = actualLevel.badgeLabel
        val initialChat = listOf(
            ChatMessage(
                sender = ChatSender.SYSTEM,
                text = "🏷️ [$levelBadge - BATAS WAKTU ${actualLevel.timeMinutes} MNT] Kasus baru berhasil dibuat oleh Gemini AI Online. Memasuki Stage Briefing."
            ),
            ChatMessage(
                sender = ChatSender.PASIEN,
                text = initialGreeting
            )
        )

        _uiState.value = _uiState.value.copy(
            currentStage = SimulationStage.BRIEFING,
            activeCase = calibratedCase,
            remainingSeconds = initialSeconds,
            isTimerRunning = initialSeconds > 0,
            lastCitoActionFeedback = null,
            citoActionLogs = emptyList(),
            caseGenerationError = null,
            chatHistory = initialChat,
            userExams = emptyList(),
            totalSpentRupiah = 0L,
            isRequestingAiExam = false,
            primaryDiagnosisInput = "",
            differentialDiagnosisInput = "",
            treatmentInput = "",
            educationInput = "",
            geminiSuggestionText = null,
            aiDiagnosisSuggestionText = null,
            isLoadingAiDiagnosis = false,
            aiDiffSuggestionList = null,
            isLoadingAiDiff = false,
            aiMedicationSuggestionText = null,
            isLoadingAiMedication = false,
            onlineNoticeMessage = null,
            evaluationResult = null,
            isCaseSaved = false,
            currentBottomNav = 0
        )

        // Log CITO Stage Setup and Initial Patient State
        MedicalDebugLogger.logTimerEvent(
            eventType = "CITO_CASE_INITIALIZED",
            remainingSeconds = initialSeconds,
            isEmergencyMode = actualLevel == CaseLevel.LEVEL_3,
            note = "Case Title: '${selectedCase.title}', Level: ${actualLevel.title} (${initialSeconds}s)"
        )

        if (initialSeconds > 0) {
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingSeconds > 0 && _uiState.value.isTimerRunning) {
                delay(1000L)
                val newTime = _uiState.value.remainingSeconds - 1
                _uiState.value = _uiState.value.copy(remainingSeconds = newTime)

                // Log time tick milestones & critical emergency decay triggers
                if (newTime > 0 && (newTime % 30 == 0 || (newTime <= 60 && _uiState.value.selectedTimeMode.isEmergency && newTime % 10 == 0))) {
                    val isDecay = newTime <= 60 && _uiState.value.selectedTimeMode.isEmergency && _uiState.value.citoActionLogs.none { it.impactType == com.example.data.model.CitoImpactType.STABILIZED }
                    MedicalDebugLogger.logTimerEvent(
                        eventType = if (isDecay) "EMERGENCY_DECAY_TICK" else "TIMER_TICK_COUNTDOWN",
                        remainingSeconds = newTime,
                        isEmergencyMode = _uiState.value.selectedTimeMode.isEmergency,
                        note = if (isDecay) "⚠️ Critical emergency decay active! Patient vitals deteriorating." else "Time Score remaining: ${newTime}s"
                    )
                }
            }
            if (_uiState.value.isTimerRunning && _uiState.value.remainingSeconds <= 0) {
                handleTimeExpiredFatal()
            }
        }
    }

    private fun handleTimeExpiredFatal() {
        val case = _uiState.value.activeCase ?: return
        val isEmergency = _uiState.value.selectedTimeMode.isEmergency
        val emergencyReason = if (isEmergency) " [CODE RED EMERGENCY TIMEOUT]" else ""

        MedicalDebugLogger.log(
            category = LogCategory.CITO_STAGE,
            level = LogLevel.ERROR,
            message = "🚨 [CITO TIMEOUT FATAL] Time score reached 0s for case '${case.title}'. Patient collapsed due to emergency timeout.",
            timeScoreSeconds = 0,
            patientStatus = "HENTI JANTUNG / COLLAPSE (TIMEOUT)"
        )
        
        val fatalEvalResult = EvaluationResult(
            diagnosisStatus = "SALAH (TERLAMBAT$emergencyReason)",
            diagnosisFeedback = if (isEmergency) {
                "🚨 CODE RED FATAL: Pasien mengalami henti jantung/syok terkompensasi! Waktu resusitasi Mode Darurat (${_uiState.value.selectedTimeMode.displayName}) habis sebelum tatalaksana cito selesai. Pada kasus '${case.trueDiagnosis}', keterlambatan mengambil keputusan berakibat kematian klinis."
            } else {
                "🚨 PASIEN WAFAT / TIDAK TERTOLONG! Waktu resusitasi & simulasi kegawatdaruratan telah habis sebelum tatalaksana diberikan. Pada kasus '${case.trueDiagnosis}', keterlambatan penanganan berakibat fatal."
            },
            trueDiagnosis = case.trueDiagnosis,
            pemfisStatus = "TIDAK TELITI",
            pemfisFeedback = "Terlambat menyelesaikan pemeriksaan fisik & penunjang cito.",
            costStatus = "TIDAK TELITI",
            examFeedback = "Resusitasi dan penunjang tidak diselesaikan tepat waktu.",
            totalSpent = _uiState.value.totalSpentRupiah,
            optimalCost = case.availableExams.filter { case.optimalExamNames.contains(it.name) }.sumOf { it.costRupiah },
            costRatioText = "Terlambat",
            treatmentStatus = "TIDAK TELITI",
            treatmentFeedback = "Tatalaksana resusitasi cito tidak sempat diberikan.",
            educationStatus = "TIDAK TELITI",
            educationFeedback = "Konsultasikan protokol resusitasi trauma/kegawatdaruratan cito.",
            totalScore = 0,
            downloadCode = "FATAL-${System.currentTimeMillis().toString().takeLast(6)}",
            isAiEvaluated = false
        )

        _uiState.value = _uiState.value.copy(
            currentStage = SimulationStage.EVALUASI,
            isTimerRunning = false,
            evaluationResult = fatalEvalResult
        )
        saveCaseToHistory(fatalEvalResult)
    }

    fun advanceToStage(targetStage: SimulationStage) {
        _uiState.value = _uiState.value.copy(currentStage = targetStage)
    }

    // Anamnesis: Send Doctor Message
    fun sendDoctorMessage(message: String) {
        val currentCase = _uiState.value.activeCase ?: return
        if (message.isBlank()) return

        val docMsg = ChatMessage(sender = ChatSender.DOKTER, text = message)
        val updatedHistory = _uiState.value.chatHistory + docMsg

        _uiState.value = _uiState.value.copy(
            chatHistory = updatedHistory,
            isPatientTyping = true
        )

        viewModelScope.launch {
            val responseText = GeminiService.getPatientResponse(
                case = currentCase,
                history = updatedHistory,
                userMessage = message,
                difficultyLevel = _uiState.value.selectedDifficulty
            )

            val patientMsg = ChatMessage(sender = ChatSender.PASIEN, text = responseText)
            _uiState.value = _uiState.value.copy(
                chatHistory = _uiState.value.chatHistory + patientMsg,
                isPatientTyping = false
            )
        }
    }

    // Pemfis & Lab: Request Exam
    fun requestExam(examItem: ExamItem) {
        // Check if already requested
        val existing = _uiState.value.userExams.find { it.examName.equals(examItem.name, ignoreCase = true) }
        if (existing != null) return

        val currentCase = _uiState.value.activeCase
        val masterMatch = MasterExamsCatalog.findMasterExamMatch(examItem.name)
        val synchronizedCost = masterMatch?.costRupiah ?: examItem.costRupiah
        val canonicalCategory: ExamCategory = masterMatch?.category ?: examItem.category

        val hasPredefinedResult = examItem.result.isNotBlank() && !examItem.result.startsWith("Sedang")

        val newExamResult = UserExamResult(
            examName = examItem.name,
            category = canonicalCategory,
            result = if (hasPredefinedResult) examItem.result else "Sedang memeriksa & menganalisis hasil via AI sesuai kondisi pasien...",
            costRupiah = synchronizedCost,
            isAiGenerated = !hasPredefinedResult,
            isLoading = !hasPredefinedResult
        )

        val updatedList = _uiState.value.userExams + newExamResult
        val newTotalCost = updatedList.sumOf { it.costRupiah }

        _uiState.value = _uiState.value.copy(
            userExams = updatedList,
            totalSpentRupiah = newTotalCost,
            isRequestingAiExam = !hasPredefinedResult
        )

        if (!hasPredefinedResult && currentCase != null) {
            // Asynchronously query live AI finding from Gemini
            viewModelScope.launch {
                val liveResult = GeminiService.getLiveExamFinding(
                    case = currentCase,
                    examName = examItem.name,
                    category = canonicalCategory,
                    history = _uiState.value.chatHistory,
                    existingExams = _uiState.value.userExams
                )

                val refreshedList = _uiState.value.userExams.map {
                    if (it.examName.equals(examItem.name, ignoreCase = true)) {
                        it.copy(
                            result = liveResult,
                            isLoading = false,
                            isAiGenerated = true
                        )
                    } else it
                }

                _uiState.value = _uiState.value.copy(
                    userExams = refreshedList,
                    isRequestingAiExam = false
                )
            }
        } else {
            _uiState.value = _uiState.value.copy(isRequestingAiExam = false)
        }
    }

    // Custom Pemfis/Lab query typed by doctor
    fun requestCustomExamQuery(queryText: String) {
        if (queryText.isBlank()) return
        val currentCase = _uiState.value.activeCase ?: return
        val trimmedQuery = queryText.trim()

        // Resolve canonical exam item and standardized price from MasterExamsCatalog
        val resolvedExam = MasterExamsCatalog.resolveExam(trimmedQuery)
        val canonicalName = resolvedExam.name

        // Check if already in requested exams
        val existing = _uiState.value.userExams.find { 
            it.examName.equals(trimmedQuery, ignoreCase = true) || it.examName.equals(canonicalName, ignoreCase = true) 
        }
        if (existing != null) return

        // Check if current case has predefined finding for this exam
        val predefinedInCase = currentCase.availableExams.find {
            it.name.equals(canonicalName, ignoreCase = true) || it.name.equals(trimmedQuery, ignoreCase = true)
        }

        if (predefinedInCase != null && predefinedInCase.result.isNotBlank()) {
            requestExam(predefinedInCase.copy(costRupiah = resolvedExam.costRupiah))
            return
        }

        val category: ExamCategory = resolvedExam.category
        val accurateCost = resolvedExam.costRupiah

        val customResult = UserExamResult(
            examName = canonicalName,
            category = category,
            result = "Sedang menganalisis hasil pemeriksaan dengan AI sesuai kondisi klinis pasien...",
            costRupiah = accurateCost,
            isAiGenerated = true,
            isLoading = true
        )

        val updatedList = _uiState.value.userExams + customResult
        _uiState.value = _uiState.value.copy(
            userExams = updatedList,
            totalSpentRupiah = updatedList.sumOf { it.costRupiah },
            isRequestingAiExam = true
        )

        viewModelScope.launch {
            val liveResult = GeminiService.getLiveExamFinding(
                case = currentCase,
                examName = canonicalName,
                category = category,
                history = _uiState.value.chatHistory,
                existingExams = _uiState.value.userExams
            )

            val finalFinding = if (liveResult.isNotBlank() && !liveResult.contains("Sedang menganalisis")) {
                liveResult
            } else {
                "Pemeriksaan '$canonicalName' telah selesai dilakukan: Hasil terkonfirmasi sesuai kondisi klinis pasien saat ini."
            }

            val refreshedList = _uiState.value.userExams.map {
                if (it.examName.equals(canonicalName, ignoreCase = true) || it.examName.equals(trimmedQuery, ignoreCase = true)) {
                    it.copy(
                        result = finalFinding,
                        isLoading = false,
                        isAiGenerated = true
                    )
                } else it
            }

            _uiState.value = _uiState.value.copy(
                userExams = refreshedList,
                isRequestingAiExam = false
            )
        }
    }

    // Initial Stabilization Action Handler with Dynamic Cito Time & Vitals Impact
    fun applyInitialStabilizationAction(actionName: String) {
        val currentCase = _uiState.value.activeCase ?: return
        val currentTreatment = _uiState.value.treatmentInput
        val oldTimeSeconds = _uiState.value.remainingSeconds
        val citoLogs = _uiState.value.citoActionLogs

        // Check if action already applied
        if (currentTreatment.contains(actionName, ignoreCase = true)) {
            val duplicateFeedback = CitoActionFeedback(
                actionTitle = actionName,
                impactType = CitoImpactType.UNINDICATED,
                timeDeltaSeconds = 0,
                pointPenalty = 3,
                message = "⚠️ PROSEDUR REPETITIF / SUDAH TERPASANG (-3 Pts, +0 Detik WAKTU CITO): Prosedur $actionName sudah terpasang sebelumnya, tidak memberikan waktu tambahan, dan memotong 3 poin dari skor akhir!"
            )
            MedicalDebugLogger.logCitoActionEvaluation(
                caseTitle = currentCase.title,
                actionName = actionName,
                impactType = duplicateFeedback.impactType.name,
                timeDeltaSeconds = 0,
                feedbackMessage = duplicateFeedback.message
            )
            _uiState.value = _uiState.value.copy(
                lastCitoActionFeedback = duplicateFeedback,
                citoActionLogs = citoLogs + duplicateFeedback
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isEvaluatingCitoAction = true)

            // Evaluate action dynamically via AI (calculates real-time hemodynamic & physiological outcome)
            val feedback = CitoEvaluator.evaluateActionWithAI(actionName, currentCase, citoLogs)
            val updatedLogs = citoLogs + feedback

            // Log CITO action evaluation result
            MedicalDebugLogger.logCitoActionEvaluation(
                caseTitle = currentCase.title,
                actionName = actionName,
                impactType = feedback.impactType.name,
                timeDeltaSeconds = feedback.timeDeltaSeconds,
                feedbackMessage = feedback.message
            )

            val updatedTreatment = if (currentTreatment.isBlank()) {
                "Resusitasi Cito: $actionName"
            } else {
                "$currentTreatment, $actionName"
            }

            when (feedback.impactType) {
                CitoImpactType.STABILIZED -> {
                    val newTime = _uiState.value.remainingSeconds + feedback.timeDeltaSeconds
                    MedicalDebugLogger.logTimeScoreChange(
                        actionName = actionName,
                        oldSeconds = oldTimeSeconds,
                        deltaSeconds = feedback.timeDeltaSeconds,
                        newSeconds = newTime,
                        impactType = feedback.impactType.name,
                        reason = feedback.message
                    )
                    _uiState.value = _uiState.value.copy(
                        treatmentInput = updatedTreatment,
                        remainingSeconds = newTime,
                        lastCitoActionFeedback = feedback,
                        citoActionLogs = updatedLogs,
                        isEvaluatingCitoAction = false
                    )
                }
                CitoImpactType.UNINDICATED -> {
                    val newTime = _uiState.value.remainingSeconds
                    MedicalDebugLogger.logTimeScoreChange(
                        actionName = actionName,
                        oldSeconds = oldTimeSeconds,
                        deltaSeconds = 0,
                        newSeconds = newTime,
                        impactType = feedback.impactType.name,
                        reason = feedback.message
                    )
                    _uiState.value = _uiState.value.copy(
                        treatmentInput = updatedTreatment,
                        remainingSeconds = newTime,
                        lastCitoActionFeedback = feedback,
                        citoActionLogs = updatedLogs,
                        isEvaluatingCitoAction = false
                    )
                }
                CitoImpactType.HARMFUL -> {
                    val newTime = maxOf(0, _uiState.value.remainingSeconds + feedback.timeDeltaSeconds)
                    MedicalDebugLogger.logTimeScoreChange(
                        actionName = actionName,
                        oldSeconds = oldTimeSeconds,
                        deltaSeconds = feedback.timeDeltaSeconds,
                        newSeconds = newTime,
                        impactType = feedback.impactType.name,
                        reason = feedback.message
                    )
                    _uiState.value = _uiState.value.copy(
                        treatmentInput = updatedTreatment,
                        remainingSeconds = newTime,
                        lastCitoActionFeedback = feedback,
                        citoActionLogs = updatedLogs,
                        isEvaluatingCitoAction = false
                    )
                    if (newTime == 0) {
                        handleFatalCollapse(
                            actionName,
                            "${feedback.message}\n\n🚨 BATAS WAKTU CITO HABIS! Akibat penggunaan prosedur yang membahayakan/tidak relevan, waktu CITO 3 menit telah terlampaui dan pasien mengalami kolaps sirkulasi fatal."
                        )
                    }
                }
                CitoImpactType.FATAL_COLLAPSE -> {
                    MedicalDebugLogger.logTimeScoreChange(
                        actionName = actionName,
                        oldSeconds = oldTimeSeconds,
                        deltaSeconds = -oldTimeSeconds,
                        newSeconds = 0,
                        impactType = feedback.impactType.name,
                        reason = feedback.message
                    )
                    _uiState.value = _uiState.value.copy(
                        treatmentInput = updatedTreatment,
                        remainingSeconds = 0,
                        lastCitoActionFeedback = feedback,
                        citoActionLogs = updatedLogs,
                        isEvaluatingCitoAction = false
                    )
                    handleFatalCollapse(actionName, feedback.message)
                }
            }
        }
    }

    fun dismissCitoActionFeedback() {
        _uiState.value = _uiState.value.copy(lastCitoActionFeedback = null)
    }

    private fun handleFatalCollapse(actionName: String, reason: String) {
        val case = _uiState.value.activeCase ?: return

        MedicalDebugLogger.log(
            category = LogCategory.CITO_STAGE,
            level = LogLevel.ERROR,
            message = "🚨 [FATAL COLLAPSE] Case: '${case.title}' | Action '$actionName' triggered fatal collapse. Reason: $reason",
            timeScoreSeconds = 0,
            patientStatus = "FATAL COLLAPSE / PATIENT DEATH"
        )
        
        val fatalEvalResult = EvaluationResult(
            diagnosisStatus = "SALAH FATAL (PATIENT DEATH / FATAL INTERVENTION)",
            diagnosisFeedback = "$reason\n\nDalam penanganan kasus '${case.trueDiagnosis}', kesalahan prosedur cito '$actionName' berakibat fatal bagi keselamatan jiwa pasien.",
            trueDiagnosis = case.trueDiagnosis,
            pemfisStatus = "TIDAK TELITI",
            pemfisFeedback = "Pasien mengalami kolaps sirkulasi/henti jantung akibat intervensi kontraindikasi.",
            costStatus = "TIDAK TELITI",
            examFeedback = "Resusitasi terhenti akibat kegagalan tatalaksana vital.",
            totalSpent = _uiState.value.totalSpentRupiah,
            optimalCost = case.availableExams.filter { case.optimalExamNames.contains(it.name) }.sumOf { it.costRupiah },
            costRatioText = "Gagal (Fatal)",
            treatmentStatus = "SALAH FATAL",
            treatmentFeedback = reason,
            educationStatus = "TIDAK TELITI",
            educationFeedback = "Konsultasikan kembali indikasi & kontraindikasi mutlak prosedur $actionName pada PPK Kemenkes RI.",
            totalScore = 0,
            downloadCode = "FATAL-${System.currentTimeMillis().toString().takeLast(6)}",
            isAiEvaluated = false
        )

        _uiState.value = _uiState.value.copy(
            currentStage = SimulationStage.EVALUASI,
            isTimerRunning = false,
            evaluationResult = fatalEvalResult
        )
        saveCaseToHistory(fatalEvalResult)
    }

    // Diagnosis & Tatalaksana Input Handlers
    fun updateDiagnosisInputs(
        primary: String,
        differentials: String,
        treatment: String,
        education: String
    ) {
        _uiState.value = _uiState.value.copy(
            primaryDiagnosisInput = primary,
            differentialDiagnosisInput = differentials,
            treatmentInput = treatment,
            educationInput = education
        )
    }

    // Request [SARAN GEMINI] on Stage 5
    fun requestGeminiTreatmentSuggestion() {
        val currentDiag = _uiState.value.primaryDiagnosisInput.ifBlank {
            _uiState.value.activeCase?.trueDiagnosis ?: "Penyakit Medis"
        }

        _uiState.value = _uiState.value.copy(isLoadingGeminiSuggestion = true)

        viewModelScope.launch {
            val suggestion = GeminiService.getGeminiTreatmentSuggestions(currentDiag)
            _uiState.value = _uiState.value.copy(
                geminiSuggestionText = suggestion,
                isLoadingGeminiSuggestion = false
            )
        }
    }

    // Stage 5: AI Consultation for Primary Diagnosis
    fun requestAiDiagnosisConsultation() {
        val currentCase = _uiState.value.activeCase ?: return
        _uiState.value = _uiState.value.copy(isLoadingAiDiagnosis = true)
        viewModelScope.launch {
            val suggestion = GeminiService.getAiDiagnosisConsultation(
                case = currentCase,
                chatHistory = _uiState.value.chatHistory,
                userExams = _uiState.value.userExams
            )
            _uiState.value = _uiState.value.copy(
                aiDiagnosisSuggestionText = suggestion,
                isLoadingAiDiagnosis = false
            )
        }
    }

    // Stage 5: AI Consultation for Differential Diagnosis
    fun requestAiDifferentialConsultation() {
        val currentCase = _uiState.value.activeCase ?: return
        _uiState.value = _uiState.value.copy(isLoadingAiDiff = true)
        viewModelScope.launch {
            val diffList = GeminiService.getAiDifferentialDiagnosisConsultation(
                case = currentCase,
                chatHistory = _uiState.value.chatHistory,
                userExams = _uiState.value.userExams,
                currentPrimary = _uiState.value.primaryDiagnosisInput
            )
            _uiState.value = _uiState.value.copy(
                aiDiffSuggestionList = diffList,
                isLoadingAiDiff = false
            )
        }
    }

    // Stage 5: AI Consultation for Prescription & Medication
    fun requestAiMedicationConsultation() {
        val currentCase = _uiState.value.activeCase ?: return
        val currentDiag = _uiState.value.primaryDiagnosisInput.ifBlank { currentCase.trueDiagnosis }
        _uiState.value = _uiState.value.copy(isLoadingAiMedication = true)
        viewModelScope.launch {
            val medSuggestion = GeminiService.getAiMedicationConsultation(
                case = currentCase,
                diagnosis = currentDiag,
                userExams = _uiState.value.userExams
            )
            _uiState.value = _uiState.value.copy(
                aiMedicationSuggestionText = medSuggestion,
                isLoadingAiMedication = false
            )
        }
    }

    // Apply suggestions to form fields
    fun applySuggestedPrimaryDiagnosis(diagnosisName: String) {
        _uiState.value = _uiState.value.copy(primaryDiagnosisInput = diagnosisName)
    }

    fun applySuggestedDifferential(differentialName: String) {
        val current = _uiState.value.differentialDiagnosisInput.trim()
        val updated = if (current.isEmpty()) differentialName else "$current, $differentialName"
        _uiState.value = _uiState.value.copy(differentialDiagnosisInput = updated)
    }

    fun applyAllSuggestedDifferentials(list: List<String>) {
        _uiState.value = _uiState.value.copy(differentialDiagnosisInput = list.joinToString(", "))
    }

    fun applySuggestedMedication(treatmentText: String) {
        val current = _uiState.value.treatmentInput.trim()
        val updated = if (current.isEmpty()) treatmentText else "$current\n\n$treatmentText"
        _uiState.value = _uiState.value.copy(treatmentInput = updated)
    }

    fun dismissOnlineNotice() {
        _uiState.value = _uiState.value.copy(onlineNoticeMessage = null)
    }

    // Clinical Hint Methods
    fun openClinicalHintModal() {
        _uiState.value = _uiState.value.copy(isClinicalHintModalOpen = true)
    }

    fun closeClinicalHintModal() {
        _uiState.value = _uiState.value.copy(isClinicalHintModalOpen = false)
    }

    fun unlockNextClinicalHint() {
        val currentUnlocked = _uiState.value.hintsUnlockedCount
        if (currentUnlocked >= 3) return
        val nextLevel = currentUnlocked + 1
        val addedCost = when (nextLevel) {
            1 -> 5
            2 -> 10
            3 -> 15
            else -> 0
        }
        val newTotalPenalty = _uiState.value.hintsPenaltyPoints + addedCost

        MedicalDebugLogger.log(
            category = LogCategory.MEDICAL_LOGIC,
            level = LogLevel.INFO,
            message = "💡 [CLINICAL HINT UNLOCKED] Level $nextLevel hint unlocked for case '${_uiState.value.activeCase?.title}'. Penalty: -$addedCost Pts (Total Penalty: -$newTotalPenalty Pts)"
        )

        _uiState.value = _uiState.value.copy(
            hintsUnlockedCount = nextLevel,
            hintsPenaltyPoints = newTotalPenalty
        )
    }

    // Stage 6: Evaluate & Finalize
    fun finishAndEvaluate() {
        val case = _uiState.value.activeCase ?: return
        timerJob?.cancel()

        // Local instant calculation as immediate baseline
        val baseEvalResult = GeminiService.evaluatePerformance(
            case = case,
            userExams = _uiState.value.userExams,
            userPrimaryDiagnosis = _uiState.value.primaryDiagnosisInput,
            userDifferentials = _uiState.value.differentialDiagnosisInput,
            userTreatment = _uiState.value.treatmentInput,
            userEducation = _uiState.value.educationInput
        )
        val initialEvalResult = applyScoreAdjustments(baseEvalResult)

        _uiState.value = _uiState.value.copy(
            currentStage = SimulationStage.EVALUASI,
            isTimerRunning = false,
            isEvaluatingWithGemini = true,
            evaluationResult = initialEvalResult
        )

        // Asynchronous Gemini AI evaluation
        viewModelScope.launch {
            val baseAiEvalResult = GeminiService.evaluatePerformanceWithGemini(
                case = case,
                userExams = _uiState.value.userExams,
                userPrimaryDiagnosis = _uiState.value.primaryDiagnosisInput,
                userDifferentials = _uiState.value.differentialDiagnosisInput,
                userTreatment = _uiState.value.treatmentInput,
                userEducation = _uiState.value.educationInput
            )
            val aiEvalResult = applyScoreAdjustments(baseAiEvalResult)

            _uiState.value = _uiState.value.copy(
                evaluationResult = aiEvalResult,
                isEvaluatingWithGemini = false
            )

            saveCaseToHistory(aiEvalResult)
        }
    }

    private fun applyScoreAdjustments(eval: EvaluationResult): EvaluationResult {
        var updatedEval = applyEmergencyBonusIfNeeded(eval)
        val hintPenalty = _uiState.value.hintsPenaltyPoints
        val count = _uiState.value.hintsUnlockedCount
        val citoPenalty = _uiState.value.citoActionLogs.sumOf { it.pointPenalty }

        var finalScore = updatedEval.totalScore
        val penaltyNotes = StringBuilder()

        if (count > 0 && hintPenalty > 0) {
            finalScore -= hintPenalty
            penaltyNotes.append("\n\n💡 PENALTI PETUNJUK KLINIS (-${hintPenalty} Pts): Anda membuka ${count} petunjuk medis selama simulasi.")
        }

        if (citoPenalty > 0) {
            finalScore -= citoPenalty
            penaltyNotes.append("\n\n⚠️ PENALTI TATALAKSANA CITO (-${citoPenalty} Pts): Pengurangan poin akibat intervensi tanpa indikasi medis/TTV sudah stabil (-3 Pts/tindakan) atau membahayakan pasien (-5 Pts/tindakan).")
        }

        finalScore = maxOf(0, finalScore)
        return updatedEval.copy(
            totalScore = finalScore,
            diagnosisFeedback = updatedEval.diagnosisFeedback + penaltyNotes.toString()
        )
    }

    private fun applyEmergencyBonusIfNeeded(eval: EvaluationResult): EvaluationResult {
        return eval
    }

    fun saveCaseToHistory(eval: EvaluationResult) {
        val case = _uiState.value.activeCase ?: return
        if (_uiState.value.isCaseSaved) return

        val entity = CaseEntity(
            organSystem = case.organSystem,
            timeMode = _uiState.value.selectedTimeMode.displayName,
            chiefComplaint = case.chiefComplaint,
            userDiagnosis = _uiState.value.primaryDiagnosisInput.ifBlank { "-" },
            trueDiagnosis = case.trueDiagnosis,
            diagnosisStatus = eval.diagnosisStatus,
            totalCostIncurred = eval.totalSpent,
            costStatus = eval.costStatus,
            totalScore = eval.totalScore,
            downloadCode = eval.downloadCode,
            evaluationSummary = eval.diagnosisFeedback
        )

        viewModelScope.launch {
            caseDao.insertCase(entity)
            _uiState.value = _uiState.value.copy(isCaseSaved = true)
        }
    }

    fun resetToSetup() {
        timerJob?.cancel()
        _uiState.value = _uiState.value.copy(
            currentStage = SimulationStage.SETUP,
            activeCase = null,
            remainingSeconds = 15 * 60,
            isTimerRunning = false,
            lastCitoActionFeedback = null,
            citoActionLogs = emptyList(),
            chatHistory = emptyList(),
            userExams = emptyList(),
            totalSpentRupiah = 0L,
            primaryDiagnosisInput = "",
            differentialDiagnosisInput = "",
            treatmentInput = "",
            educationInput = "",
            geminiSuggestionText = null,
            evaluationResult = null,
            isCaseSaved = false,
            hintsUnlockedCount = 0,
            hintsPenaltyPoints = 0,
            isClinicalHintModalOpen = false
        )
    }

    fun clearHistory() {
        viewModelScope.launch {
            caseDao.clearAllCases()
        }
    }
}
