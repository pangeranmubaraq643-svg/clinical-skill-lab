package com.example.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SimulationStage
import com.example.ui.SimulationViewModel
import com.example.ui.components.StageHeader
import com.example.ui.screens.stages.AnamnesisView
import com.example.ui.screens.stages.BriefingView
import com.example.ui.screens.stages.DiagnosisTatalaksanaView
import com.example.ui.screens.stages.EvaluasiView
import com.example.ui.screens.stages.PemfisLabView
import com.example.ui.screens.stages.SetupView
import com.example.ui.screens.stages.StabilisasiAwalView
import com.example.ui.theme.MedicalTeal40

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.ui.components.CaseReviewModal
import com.example.ui.components.KemenkesGuidelineDrawer
import com.example.ui.components.VitalSignsDashboard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: SimulationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val caseHistoryList by viewModel.caseHistory.collectAsState()
    var showReviewModalInHeader by remember { mutableStateOf(false) }
    var showKemenkesDrawer by remember { mutableStateOf(false) }

    if (showReviewModalInHeader && uiState.activeCase != null) {
        CaseReviewModal(
            activeCase = uiState.activeCase!!,
            userExams = uiState.userExams,
            chatHistory = uiState.chatHistory,
            onDismiss = { showReviewModalInHeader = false },
            onJumpToAnamnesis = {
                showReviewModalInHeader = false
                viewModel.advanceToStage(SimulationStage.ANAMNESIS)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Clinical Case Simulator",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                actions = {
                    // Tombol Panduan Kemenkes RI di TopAppBar hanya saat Evaluasi
                    if (uiState.currentBottomNav == 0 &&
                        uiState.currentStage == SimulationStage.EVALUASI &&
                        uiState.activeCase != null
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.25f),
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clickable { showKemenkesDrawer = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HealthAndSafety,
                                    contentDescription = "Kemenkes Guidelines",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Panduan Kemenkes",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MedicalTeal40
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = uiState.currentBottomNav == 0,
                    onClick = { viewModel.selectBottomNav(0) },
                    icon = { Icon(imageVector = Icons.Default.MedicalServices, contentDescription = "Simulasi") },
                    label = { Text("Simulasi") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MedicalTeal40,
                        selectedTextColor = MedicalTeal40
                    )
                )
                NavigationBarItem(
                    selected = uiState.currentBottomNav == 1,
                    onClick = { viewModel.selectBottomNav(1) },
                    icon = { Icon(imageVector = Icons.Default.History, contentDescription = "Riwayat") },
                    label = { Text("Riwayat (${caseHistoryList.size})") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MedicalTeal40,
                        selectedTextColor = MedicalTeal40
                    )
                )
                NavigationBarItem(
                    selected = uiState.currentBottomNav == 2,
                    onClick = { viewModel.selectBottomNav(2) },
                    icon = { Icon(imageVector = Icons.Default.Book, contentDescription = "PPK Guide") },
                    label = { Text("PPK Guide") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MedicalTeal40,
                        selectedTextColor = MedicalTeal40
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentBottomNav) {
                1 -> {
                    HistoryScreen(
                        caseList = caseHistoryList,
                        onClearHistory = { viewModel.clearHistory() }
                    )
                }
                2 -> {
                    PpkGuideScreen()
                }
                else -> {
                    // Active Simulation Flow
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Header Stage Progress bar (Shown when in active stage briefing -> evaluasi)
                        if (uiState.currentStage != SimulationStage.SETUP) {
                            StageHeader(
                                currentStage = uiState.currentStage,
                                timeMode = uiState.selectedTimeMode,
                                remainingSeconds = uiState.remainingSeconds,
                                totalSpentRupiah = uiState.totalSpentRupiah,
                                hintsUnlockedCount = uiState.hintsUnlockedCount,
                                onOpenReview = { showReviewModalInHeader = true },
                                onOpenClinicalHint = { viewModel.openClinicalHintModal() },
                                onSelectStage = { targetStage -> viewModel.advanceToStage(targetStage) }
                            )

                            uiState.activeCase?.let { activeCase ->
                                VitalSignsDashboard(
                                    activeCase = activeCase,
                                    currentStage = uiState.currentStage,
                                    userExams = uiState.userExams,
                                    treatmentInput = uiState.treatmentInput,
                                    remainingSeconds = uiState.remainingSeconds,
                                    isEmergencyMode = uiState.selectedTimeMode.isEmergency,
                                    citoActionLogs = uiState.citoActionLogs
                                )
                            }
                        }

                        Crossfade(targetState = uiState.currentStage, label = "stage_crossfade") { stage ->
                            when (stage) {
                                SimulationStage.SETUP -> {
                                    SetupView(
                                        selectedOrganSystem = uiState.selectedOrganSystem,
                                        selectedTimeMode = uiState.selectedTimeMode,
                                        selectedLevel = uiState.selectedLevel,
                                        selectedDifficulty = uiState.selectedDifficulty,
                                        isGeneratingAiCase = uiState.isGeneratingAiCase,
                                        caseGenerationError = uiState.caseGenerationError,
                                        onDismissError = { viewModel.dismissCaseGenerationError() },
                                        onSelectOrgan = { organ -> viewModel.updateSetupSelection(organ, uiState.selectedTimeMode, uiState.selectedLevel, uiState.selectedDifficulty) },
                                        onSelectTimeMode = { mode -> viewModel.updateSetupSelection(uiState.selectedOrganSystem, mode, uiState.selectedLevel, uiState.selectedDifficulty) },
                                        onSelectLevel = { level -> viewModel.selectCaseLevel(level) },
                                        onSelectDifficulty = { diff -> viewModel.selectDifficultyLevel(diff) },
                                        onStartCase = { viewModel.startNewCase() },
                                        onStartSpecificCase = { caseName -> viewModel.startSpecificCaseByDiagnosis(caseName) }
                                    )
                                }
                                SimulationStage.BRIEFING -> {
                                    uiState.activeCase?.let { activeCase ->
                                        BriefingView(
                                            activeCase = activeCase,
                                            onNextStage = { viewModel.advanceToStage(SimulationStage.ANAMNESIS) }
                                        )
                                    }
                                }
                                SimulationStage.ANAMNESIS -> {
                                    uiState.activeCase?.let { activeCase ->
                                        AnamnesisView(
                                            activeCase = activeCase,
                                            chatHistory = uiState.chatHistory,
                                            isPatientTyping = uiState.isPatientTyping,
                                            onSendMessage = { msg -> viewModel.sendDoctorMessage(msg) },
                                            onNextStage = { viewModel.advanceToStage(SimulationStage.STABILISASI_AWAL) }
                                        )
                                    }
                                }
                                SimulationStage.STABILISASI_AWAL -> {
                                    uiState.activeCase?.let { activeCase ->
                                        StabilisasiAwalView(
                                            activeCase = activeCase,
                                            treatmentInput = uiState.treatmentInput,
                                            isEmergencyMode = uiState.selectedTimeMode.isEmergency,
                                            lastCitoFeedback = uiState.lastCitoActionFeedback,
                                            citoActionLogs = uiState.citoActionLogs,
                                            isEvaluatingCitoAction = uiState.isEvaluatingCitoAction,
                                            onDismissFeedback = { viewModel.dismissCitoActionFeedback() },
                                            onApplyAction = { action -> viewModel.applyInitialStabilizationAction(action) },
                                            onNextStage = { viewModel.advanceToStage(SimulationStage.PEMFIS_LAB) },
                                            onPreviousStage = { viewModel.advanceToStage(SimulationStage.ANAMNESIS) }
                                        )
                                    }
                                }
                                SimulationStage.PEMFIS_LAB -> {
                                    uiState.activeCase?.let { activeCase ->
                                        PemfisLabView(
                                            activeCase = activeCase,
                                            userExams = uiState.userExams,
                                            totalSpentRupiah = uiState.totalSpentRupiah,
                                            onRequestExam = { exam -> viewModel.requestExam(exam) },
                                            onRequestCustomExam = { query -> viewModel.requestCustomExamQuery(query) },
                                            onFinishExams = { viewModel.advanceToStage(SimulationStage.DIAGNOSIS_TATALAKSANA) },
                                            onJumpToAnamnesis = { viewModel.advanceToStage(SimulationStage.ANAMNESIS) }
                                        )
                                    }
                                }
                                SimulationStage.DIAGNOSIS_TATALAKSANA -> {
                                    DiagnosisTatalaksanaView(
                                        primaryDiagnosis = uiState.primaryDiagnosisInput,
                                        differentialDiagnosis = uiState.differentialDiagnosisInput,
                                        treatment = uiState.treatmentInput,
                                        education = uiState.educationInput,
                                        geminiSuggestion = uiState.geminiSuggestionText,
                                        isLoadingGemini = uiState.isLoadingGeminiSuggestion,
                                        aiDiagnosisSuggestion = uiState.aiDiagnosisSuggestionText,
                                        isLoadingAiDiagnosis = uiState.isLoadingAiDiagnosis,
                                        aiDiffSuggestions = uiState.aiDiffSuggestionList,
                                        isLoadingAiDiff = uiState.isLoadingAiDiff,
                                        aiMedicationSuggestion = uiState.aiMedicationSuggestionText,
                                        isLoadingAiMedication = uiState.isLoadingAiMedication,
                                        activeCase = uiState.activeCase,
                                        userExams = uiState.userExams,
                                        chatHistory = uiState.chatHistory,
                                        onUpdateInputs = { pri, diff, treat, edu -> viewModel.updateDiagnosisInputs(pri, diff, treat, edu) },
                                        onRequestGeminiSuggestion = { viewModel.requestGeminiTreatmentSuggestion() },
                                        onRequestAiDiagnosis = { viewModel.requestAiDiagnosisConsultation() },
                                        onRequestAiDiff = { viewModel.requestAiDifferentialConsultation() },
                                        onRequestAiMedication = { viewModel.requestAiMedicationConsultation() },
                                        onApplySuggestedPrimary = { diag -> viewModel.applySuggestedPrimaryDiagnosis(diag) },
                                        onApplySuggestedDiff = { diff -> viewModel.applySuggestedDifferential(diff) },
                                        onApplyAllSuggestedDiff = { list -> viewModel.applyAllSuggestedDifferentials(list) },
                                        onApplySuggestedMedication = { med -> viewModel.applySuggestedMedication(med) },
                                        onSubmitAndEvaluate = { viewModel.finishAndEvaluate() },
                                        onJumpToAnamnesis = { viewModel.advanceToStage(SimulationStage.ANAMNESIS) }
                                    )
                                }
                                SimulationStage.EVALUASI -> {
                                    uiState.activeCase?.let { activeCase ->
                                        uiState.evaluationResult?.let { evalResult ->
                                            EvaluasiView(
                                                activeCase = activeCase,
                                                evaluationResult = evalResult,
                                                isEvaluatingWithGemini = uiState.isEvaluatingWithGemini,
                                                citoActionLogs = uiState.citoActionLogs,
                                                onReturnHome = { viewModel.resetToSetup() }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Kemenkes Guideline Drawer (Drawer Samping - Hanya saat Evaluasi)
            if (uiState.currentBottomNav == 0 &&
                uiState.currentStage == SimulationStage.EVALUASI &&
                uiState.activeCase != null
            ) {
                KemenkesGuidelineDrawer(
                    activeCase = uiState.activeCase!!,
                    isOpen = showKemenkesDrawer,
                    onClose = { showKemenkesDrawer = false },
                    onApplyTreatmentToInput = { recTreatment ->
                        viewModel.updateDiagnosisInputs(
                            primary = uiState.primaryDiagnosisInput.ifBlank { uiState.activeCase?.trueDiagnosis ?: "" },
                            differentials = uiState.differentialDiagnosisInput,
                            treatment = if (uiState.treatmentInput.isBlank()) recTreatment else "${uiState.treatmentInput}\n\n[Pedoman PPK]: $recTreatment",
                            education = uiState.educationInput
                        )
                    }
                )
            }

            // Clinical Hint Modal (Petunjuk Medis Opsional)
            if (uiState.isClinicalHintModalOpen && uiState.activeCase != null) {
                com.example.ui.components.ClinicalHintModal(
                    activeCase = uiState.activeCase!!,
                    hintsUnlockedCount = uiState.hintsUnlockedCount,
                    hintsPenaltyPoints = uiState.hintsPenaltyPoints,
                    onUnlockNextHint = { viewModel.unlockNextClinicalHint() },
                    onDismiss = { viewModel.closeClinicalHintModal() }
                )
            }
        }
    }
}
