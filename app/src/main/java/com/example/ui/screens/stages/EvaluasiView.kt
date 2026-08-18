package com.example.ui.screens.stages

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClinicalCase
import com.example.data.model.EvaluationResult
import com.example.ui.components.ClinicalPathwayMetricsCard
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTeal40
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator

import com.example.data.model.CitoActionFeedback
import com.example.data.model.CitoImpactType
import com.example.data.repository.VitalSignsManager
import com.example.data.repository.PathologyProfile
import com.example.data.repository.CitoStabilizationGuide
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MonitorHeart

@Composable
fun EvaluasiView(
    activeCase: ClinicalCase,
    evaluationResult: EvaluationResult,
    citoActionLogs: List<CitoActionFeedback> = emptyList(),
    isEvaluatingWithGemini: Boolean = false,
    onReturnHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // AI Loading Banner if Gemini is generating evaluation
        if (isEvaluatingWithGemini) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MedicalTeal40.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MedicalTeal40,
                        strokeWidth = 2.5.dp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "🤖 Gemini AI Sedang Menganalisis Kasus...",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MedicalTeal40
                        )
                        Text(
                            text = "Menilai ketepatan diagnosis, pemfis, analisis biaya, tatalaksana & edukasi pasien",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Konsulen Evaluator Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MedicalTeal40.copy(alpha = 0.15f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = "Konsulen",
                                    tint = MedicalTeal40
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "Evaluasi Konsulen Klinis / Dosen",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "[STAGE: HASIL & EVALUASI]",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (evaluationResult.isAiEvaluated) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MedicalTeal40.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = "🤖 Gemini AI Verified",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MedicalTeal40,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Total Score Badge
                    val scoreColor = when {
                        evaluationResult.totalScore >= 80 -> SuccessGreen
                        evaluationResult.totalScore >= 60 -> WarningAmber
                        else -> EmergencyRed
                    }

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = scoreColor.copy(alpha = 0.15f)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${evaluationResult.totalScore}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 22.sp,
                                color = scoreColor
                            )
                            Text(
                                text = "SKOR / 100",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = scoreColor
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(10.dp))

                // Breakdown Skor Obyektif oleh AI
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📊 Rincian Komponen Penilaian AI (Skor Maks 100):",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (evaluationResult.isAiEvaluated) {
                        Text(
                            text = "🧠 AI Scored",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedicalTeal40
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Diagnosis Score
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Diagnosis", fontSize = 9.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${evaluationResult.diagnosisScore}/35",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (evaluationResult.diagnosisScore >= 30) SuccessGreen else if (evaluationResult.diagnosisScore >= 15) WarningAmber else EmergencyRed
                            )
                        }
                    }

                    // Pemfis & Penunjang Score
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Pemfis & Lab", fontSize = 9.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${evaluationResult.examScore}/25",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (evaluationResult.examScore >= 20) SuccessGreen else if (evaluationResult.examScore >= 12) WarningAmber else EmergencyRed
                            )
                        }
                    }

                    // Tatalaksana Score
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Tatalaksana", fontSize = 9.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${evaluationResult.treatmentScore}/25",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (evaluationResult.treatmentScore >= 20) SuccessGreen else if (evaluationResult.treatmentScore >= 12) WarningAmber else EmergencyRed
                            )
                        }
                    }

                    // Edukasi Score
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Edukasi", fontSize = 9.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${evaluationResult.educationScore}/15",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (evaluationResult.educationScore >= 12) SuccessGreen else if (evaluationResult.educationScore >= 8) WarningAmber else EmergencyRed
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Clinical Pathway Analytics & Visualization Chart (Akurasi vs Waktu & Cost-Effectiveness)
        ClinicalPathwayMetricsCard(
            activeCase = activeCase,
            evaluationResult = evaluationResult
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🚨 EVALUASI KEADAAN TTV PASIEN & TATALAKSANA CITO STABILISASI
        val guide = remember(activeCase, citoActionLogs) {
            VitalSignsManager.getCitoStabilizationGuide(activeCase, citoActionLogs)
        }
        val computedVitals = remember(activeCase, citoActionLogs) {
            VitalSignsManager.computeVitals(activeCase, citoActionLogs, isEmergencyMode = true)
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, if (guide.isTtvStabilized) SuccessGreen.copy(alpha = 0.5f) else EmergencyRed.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Header TTV Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = CircleShape,
                            color = if (guide.isTtvStabilized) SuccessGreen.copy(alpha = 0.15f) else EmergencyRed.copy(alpha = 0.15f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.MonitorHeart,
                                    contentDescription = "TTV",
                                    tint = if (guide.isTtvStabilized) SuccessGreen else EmergencyRed,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Evaluasi Keadaan TTV & Stage Cito",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Profil: ${guide.pathologyName}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (guide.isTtvStabilized) SuccessGreen.copy(alpha = 0.18f) else EmergencyRed.copy(alpha = 0.18f)
                    ) {
                        Text(
                            text = if (guide.isTtvStabilized) "🟢 TTV STABIL" else "🔴 TTV BELUM STABIL",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = if (guide.isTtvStabilized) SuccessGreen else EmergencyRed,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(12.dp))

                // Current Vitals Display
                Text(
                    text = "📊 Status Tanda-Tanda Vital (TTV) Pasien Saat Ini:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "• TD: ${computedVitals.systolic}/${computedVitals.diastolic} mmHg", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "• Nadi: ${computedVitals.hr} x/mnt", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "• RR: ${computedVitals.rr} x/mnt", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "• SpO2: ${computedVitals.spO2}%", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "• Suhu: ${computedVitals.temp}°C", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "• MAP: ${computedVitals.map} mmHg", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Required Cito Actions
                Text(
                    text = "⚡ Tatalaksana Stage Cito Yang Harus Dilakukan Untuk Menstabilkan Pasien:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MedicalTeal40
                )
                Spacer(modifier = Modifier.height(6.dp))

                guide.primaryCitoActions.forEach { action ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = action,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Rationale
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MedicalTeal40.copy(alpha = 0.1f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "💡 Mekanisme Stabilisasi TTV: ${guide.rationale}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MedicalTeal40,
                        modifier = Modifier.padding(10.dp),
                        lineHeight = 15.sp
                    )
                }

                if (citoActionLogs.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "📝 Evaluasi Mendalam Tindakan Cito / Resusitasi (${citoActionLogs.size} Intervensi Dilakukan):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        citoActionLogs.forEachIndexed { index, log ->
                            val isGood = log.impactType == CitoImpactType.STABILIZED
                            val isFatal = log.impactType == CitoImpactType.FATAL_COLLAPSE
                            val isHarmful = log.impactType == CitoImpactType.HARMFUL
                            val cardBg = when {
                                isGood -> SuccessGreen.copy(alpha = 0.08f)
                                isFatal || isHarmful -> EmergencyRed.copy(alpha = 0.08f)
                                else -> WarningAmber.copy(alpha = 0.08f)
                            }
                            val cardBorder = when {
                                isGood -> SuccessGreen
                                isFatal || isHarmful -> EmergencyRed
                                else -> WarningAmber
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = cardBg,
                                border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder.copy(alpha = 0.4f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "${index + 1}. ${log.actionTitle}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            if (log.isAiEvaluated) {
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = MedicalTeal40.copy(alpha = 0.15f)
                                                ) {
                                                    Text(
                                                        text = "🧠 AI",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        color = MedicalTeal40,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = cardBorder
                                        ) {
                                            Text(
                                                text = when (log.impactType) {
                                                    CitoImpactType.STABILIZED -> "+${log.timeDeltaSeconds}s"
                                                    CitoImpactType.UNINDICATED -> "0s (-3 Pts)"
                                                    CitoImpactType.HARMFUL -> "${log.timeDeltaSeconds}s (-5 Pts)"
                                                    CitoImpactType.FATAL_COLLAPSE -> "FATAL (-20 Pts)"
                                                },
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "⚡ Respon Langsung: ${log.message}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isGood) SuccessGreen else if (isFatal || isHarmful) EmergencyRed else WarningAmber,
                                        lineHeight = 15.sp
                                    )

                                    val explanation = log.detailedExplanation.ifBlank { log.message }
                                    if (explanation.isNotBlank() && explanation != log.message) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "🎓 Penjelasan Patofisiologi Konsulen: $explanation",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.padding(6.dp),
                                                lineHeight = 14.sp
                                            )
                                        }
                                    }

                                    if (log.updatedVitalsNote.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "📊 Catatan TTV: ${log.updatedVitalsNote}",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = cardBorder
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Helper function for status badges
        @Composable
        fun StatusBadge(statusText: String) {
            val badgeColor = when {
                statusText.contains("OPTIMAL") || statusText.contains("BENAR") -> SuccessGreen
                statusText.contains("KURANG") -> WarningAmber
                else -> EmergencyRed
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = badgeColor.copy(alpha = 0.15f)
            ) {
                Text(
                    text = statusText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = badgeColor,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        @Composable
        fun ScoreBadge(score: Int, maxScore: Int) {
            val color = if (score >= (maxScore * 0.8)) SuccessGreen else if (score >= (maxScore * 0.5)) WarningAmber else EmergencyRed
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = color.copy(alpha = 0.12f),
                border = BorderStroke(1.dp, color.copy(alpha = 0.4f))
            ) {
                Text(
                    text = "$score/$maxScore Pts",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 11.sp,
                    color = color,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // 1. Evaluasi Diagnosis
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "1. Evaluasi Diagnosis Kerja",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ScoreBadge(score = evaluationResult.diagnosisScore, maxScore = 35)
                        StatusBadge(statusText = evaluationResult.diagnosisStatus)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = evaluationResult.diagnosisFeedback,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "Diagnosis Sebenarnya: ${evaluationResult.trueDiagnosis}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 2. Evaluasi Pemeriksaan Fisik (Pemfis)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "2. Evaluasi Pemeriksaan Fisik",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    StatusBadge(statusText = evaluationResult.pemfisStatus)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = evaluationResult.pemfisFeedback,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 3. Evaluasi Analisis Biaya & Lab
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "3. Analisis Biaya & Lab/Radiologi",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ScoreBadge(score = evaluationResult.examScore, maxScore = 25)
                        StatusBadge(statusText = evaluationResult.costStatus)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Total Pengeluaran: ${evaluationResult.costRatioText}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = evaluationResult.examFeedback,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4. Evaluasi Pemberian Obat & Tatalaksana
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "4. Pemberian Obat & Tatalaksana",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ScoreBadge(score = evaluationResult.treatmentScore, maxScore = 25)
                        StatusBadge(statusText = evaluationResult.treatmentStatus)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = evaluationResult.treatmentFeedback,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 5. Evaluasi Konseling & Edukasi Pasien
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "5. Konseling & Edukasi Pasien",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ScoreBadge(score = evaluationResult.educationScore, maxScore = 15)
                        StatusBadge(statusText = evaluationResult.educationStatus)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = evaluationResult.educationFeedback,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Kode Download / Summary Code Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Kode Download & Ringkasan Laporan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Clinical Sim Report", evaluationResult.downloadCode)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Kode report disalin ke clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Salin Kode", fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF0F172A)
                ) {
                    Text(
                        text = evaluationResult.downloadCode,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = Color(0xFF38BDF8),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Return Home Button
        Button(
            onClick = onReturnHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Selesai & Kembali ke Beranda",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
