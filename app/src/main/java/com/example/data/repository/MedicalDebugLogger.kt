package com.example.data.repository

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Log Level classification for Medical Simulation Debugging.
 */
enum class LogLevel {
    INFO,
    WARN,
    ERROR,
    DEBUG
}

/**
 * Log Category tag for filtering trace logs.
 */
enum class LogCategory(val tag: String, val displayName: String) {
    TTV_MANAGER("TTV_MANAGER", "TTV Manager"),
    CITO_STAGE("CITO_STAGE", "Stage CITO"),
    MEDICAL_LOGIC("MEDICAL_LOGIC", "Medical Logic")
}

/**
 * Structured log item for Medical Logic Debugging.
 */
data class MedicalLogEntry(
    val id: Long = System.currentTimeMillis() + (0..999).random(),
    val timestampFormatted: String = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date()),
    val category: LogCategory,
    val level: LogLevel,
    val message: String,
    val timeScoreSeconds: Int? = null,
    val timeDeltaSeconds: Int? = null,
    val patientStatus: String? = null,
    val mapValue: Int? = null,
    val shockIndex: Double? = null,
    val detailsJson: String? = null
)

/**
 * Centralized Medical Debug Logger for tracking time score changes, vital sign physics,
 * and patient condition transitions during CITO stage and TTV workflow execution.
 */
object MedicalDebugLogger {

    private const val TAG_PREFIX = "MedSimDebug_"
    private const val MAX_LOG_CAPACITY = 250

    private val _logs = MutableStateFlow<List<MedicalLogEntry>>(emptyList())
    val logs: StateFlow<List<MedicalLogEntry>> = _logs.asStateFlow()

    private var lastRecordedStatus: String? = null
    private var lastRecordedTimeScore: Int? = null

    /**
     * Add a structured log entry and output to Android logcat.
     */
    fun log(
        category: LogCategory,
        level: LogLevel,
        message: String,
        timeScoreSeconds: Int? = null,
        timeDeltaSeconds: Int? = null,
        patientStatus: String? = null,
        mapValue: Int? = null,
        shockIndex: Double? = null,
        detailsJson: String? = null
    ) {
        val entry = MedicalLogEntry(
            category = category,
            level = level,
            message = message,
            timeScoreSeconds = timeScoreSeconds,
            timeDeltaSeconds = timeDeltaSeconds,
            patientStatus = patientStatus,
            mapValue = mapValue,
            shockIndex = shockIndex,
            detailsJson = detailsJson
        )

        // 1. Output to Android system Logcat
        val systemTag = "$TAG_PREFIX${category.tag}"
        val formattedLogcatMsg = buildString {
            append(message)
            if (timeScoreSeconds != null) {
                append(" | TimeScore: ${timeScoreSeconds}s")
                if (timeDeltaSeconds != null && timeDeltaSeconds != 0) {
                    val sign = if (timeDeltaSeconds > 0) "+" else ""
                    append(" ($sign${timeDeltaSeconds}s)")
                }
            }
            if (patientStatus != null) append(" | Status: $patientStatus")
            if (mapValue != null) append(" | MAP: $mapValue mmHg")
            if (shockIndex != null) append(" | ShockIndex: ${String.format("%.2f", shockIndex)}")
            if (!detailsJson.isNull構Blank()) append(" | Details: $detailsJson")
        }

        when (level) {
            LogLevel.INFO -> Log.i(systemTag, formattedLogcatMsg)
            LogLevel.WARN -> Log.w(systemTag, formattedLogcatMsg)
            LogLevel.ERROR -> Log.e(systemTag, formattedLogcatMsg)
            LogLevel.DEBUG -> Log.d(systemTag, formattedLogcatMsg)
        }

        // 2. Append to in-memory StateFlow for UI debugging
        val currentList = _logs.value.toMutableList()
        currentList.add(0, entry) // Newest first
        if (currentList.size > MAX_LOG_CAPACITY) {
            currentList.removeAt(currentList.lastIndex)
        }
        _logs.value = currentList
    }

    private fun String?.isNull構Blank(): Boolean = this == null || this.isBlank()

    /**
     * Helper to log explicit CITO Stage Time Score changes.
     */
    fun logTimeScoreChange(
        actionName: String,
        oldSeconds: Int,
        deltaSeconds: Int,
        newSeconds: Int,
        impactType: String,
        reason: String
    ) {
        val sign = if (deltaSeconds >= 0) "+" else ""
        val level = if (deltaSeconds < 0) LogLevel.WARN else LogLevel.INFO
        val msg = "⏱️ [CITO TIME SCORE CHANGE] Action: '$actionName' -> Time Score: ${oldSeconds}s ➔ ${newSeconds}s ($sign${deltaSeconds}s) | Impact: $impactType | Note: $reason"

        log(
            category = LogCategory.CITO_STAGE,
            level = level,
            message = msg,
            timeScoreSeconds = newSeconds,
            timeDeltaSeconds = deltaSeconds,
            detailsJson = "Action: $actionName, Impact: $impactType"
        )
        lastRecordedTimeScore = newSeconds
    }

    /**
     * Helper to log TTV Manager Patient Condition Status changes.
     */
    fun logPatientConditionStatusChange(
        caseTitle: String,
        oldStatus: String?,
        newStatus: String,
        map: Int,
        shockIndex: Double,
        sys: Int,
        dia: Int,
        hr: Int,
        spO2: Int,
        pathology: String,
        triggerEvent: String
    ) {
        val statusShifted = oldStatus != null && oldStatus != newStatus
        val level = when {
            newStatus.contains("ARREST") || newStatus.contains("HENTI") -> LogLevel.ERROR
            newStatus.contains("KRITIS") || newStatus.contains("SYOK") -> LogLevel.WARN
            else -> LogLevel.INFO
        }

        val prefix = if (statusShifted) "🚨 [PATIENT CONDITION STATUS SHIFT]" else "🩺 [TTV STATUS UPDATE]"
        val transitionStr = if (statusShifted) " (From: '$oldStatus' ➔ To: '$newStatus')" else ""
        val msg = "$prefix Case: '$caseTitle'$transitionStr | Condition: $newStatus | BP: $sys/$dia mmHg, HR: $hr bpm, SpO2: $spO2%, MAP: $map mmHg, ShockIndex: ${String.format("%.2f", shockIndex)} | Pathology: $pathology | Trigger: $triggerEvent"

        log(
            category = LogCategory.TTV_MANAGER,
            level = level,
            message = msg,
            patientStatus = newStatus,
            mapValue = map,
            shockIndex = shockIndex,
            detailsJson = "BP: $sys/$dia, HR: $hr, SpO2: $spO2, Pathology: $pathology, Event: $triggerEvent"
        )
        lastRecordedStatus = newStatus
    }

    /**
     * Helper to log CITO Action Evaluation by CitoEvaluator.
     */
    fun logCitoActionEvaluation(
        caseTitle: String,
        actionName: String,
        impactType: String,
        timeDeltaSeconds: Int,
        feedbackMessage: String
    ) {
        val level = when (impactType) {
            "STABILIZED" -> LogLevel.INFO
            "HARMFUL" -> LogLevel.WARN
            "FATAL_COLLAPSE" -> LogLevel.ERROR
            else -> LogLevel.DEBUG
        }

        val msg = "⚡ [CITO EVALUATOR] Case: '$caseTitle' | Action: '$actionName' ➔ Impact: $impactType (${timeDeltaSeconds}s) | Message: $feedbackMessage"
        log(
            category = LogCategory.CITO_STAGE,
            level = level,
            message = msg,
            timeDeltaSeconds = timeDeltaSeconds,
            detailsJson = "Action: $actionName, Case: $caseTitle, Impact: $impactType"
        )
    }

    /**
     * Helper to log Timer Tick / Emergency Decay triggers.
     */
    fun logTimerEvent(
        eventType: String,
        remainingSeconds: Int,
        isEmergencyMode: Boolean,
        note: String
    ) {
        val level = if (remainingSeconds <= 30 && isEmergencyMode) LogLevel.WARN else LogLevel.INFO
        val msg = "⏳ [CITO TIMER EVENT] Event: $eventType | Remaining Time: ${remainingSeconds}s | EmergencyMode: $isEmergencyMode | $note"

        log(
            category = LogCategory.CITO_STAGE,
            level = level,
            message = msg,
            timeScoreSeconds = remainingSeconds
        )
    }

    /**
     * Clear all recorded debug logs.
     */
    fun clearLogs() {
        _logs.value = emptyList()
        lastRecordedStatus = null
        lastRecordedTimeScore = null
        Log.i(TAG_PREFIX + "LOGIC", "Cleared all medical debug logs.")
    }

    fun getLastRecordedStatus(): String? = lastRecordedStatus
}
