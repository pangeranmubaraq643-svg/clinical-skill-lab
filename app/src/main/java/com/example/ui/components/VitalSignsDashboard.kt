package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CitoActionFeedback
import com.example.data.model.CitoImpactType
import com.example.data.model.ClinicalCase
import com.example.data.model.SimulationStage
import com.example.data.model.UserExamResult
import com.example.data.repository.ComputedVitals
import com.example.data.repository.VitalSignsManager
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@Composable
fun VitalSignsDashboard(
    activeCase: ClinicalCase,
    currentStage: SimulationStage,
    userExams: List<UserExamResult>,
    treatmentInput: String,
    remainingSeconds: Int,
    isEmergencyMode: Boolean,
    citoActionLogs: List<CitoActionFeedback> = emptyList(),
    modifier: Modifier = Modifier
) {
    // 0 = Mini (Sangat Ringkas), 1 = Sedang (Normal Monitor), 2 = Besar (Lengkap dengan Detail Kasus)
    var uiSizeMode by remember { mutableStateOf(1) }
    var refreshKey by remember { mutableStateOf(0) }
    var lastCheckedMessage by remember { mutableStateOf<String?>(null) }
    var showDebugLogSheet by remember { mutableStateOf(false) }

    val vitals = remember(
        activeCase.id,
        currentStage,
        userExams.size,
        treatmentInput,
        citoActionLogs.size,
        remainingSeconds <= 60,
        refreshKey
    ) {
        computeDynamicVitals(
            case = activeCase,
            userExams = userExams,
            treatmentInput = treatmentInput,
            citoActionLogs = citoActionLogs,
            remainingSeconds = remainingSeconds,
            isEmergencyMode = isEmergencyMode
        )
    }

    val safeHr = vitals.hr.coerceAtLeast(20)
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_anim")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = if (vitals.hr > 100 || vitals.hr < 60) 1.25f else 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (60000 / safeHr.coerceIn(40, 180)),
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartbeat"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)), // Sleek Dark Monitor Theme
        border = androidx.compose.foundation.BorderStroke(1.5.dp, vitals.statusColor.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Header Row with Size Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(vitals.statusColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MonitorHeart,
                            contentDescription = "Patient Monitor",
                            tint = vitals.statusColor,
                            modifier = Modifier
                                .size(20.dp)
                                .scale(if (vitals.hr == 0) 1.0f else pulseScale)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "KASUS & MONITOR",
                                color = Color(0xFF94A3B8),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = vitals.statusColor
                            ) {
                                Text(
                                    text = vitals.statusText,
                                    color = Color.White,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                        }

                        Text(
                            text = "Pasien ${activeCase.patientAge} thn (${activeCase.patientGender}) • ${activeCase.chiefComplaint}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Size Control Selector: [ Mini | Sedang | Besar ]
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF1E293B),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
                ) {
                    Row(
                        modifier = Modifier.padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Button Mini (0)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (uiSizeMode == 0) Color(0xFF0284C7) else Color.Transparent,
                            modifier = Modifier.clickable { uiSizeMode = 0 }
                        ) {
                            Text(
                                text = "Mini",
                                color = if (uiSizeMode == 0) Color.White else Color(0xFF94A3B8),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        // Button Sedang (1)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (uiSizeMode == 1) Color(0xFF0284C7) else Color.Transparent,
                            modifier = Modifier.clickable { uiSizeMode = 1 }
                        ) {
                            Text(
                                text = "Sedang",
                                color = if (uiSizeMode == 1) Color.White else Color(0xFF94A3B8),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        // Button Besar (2)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (uiSizeMode == 2) Color(0xFF0284C7) else Color.Transparent,
                            modifier = Modifier.clickable { uiSizeMode = 2 }
                        ) {
                            Text(
                                text = "Besar",
                                color = if (uiSizeMode == 2) Color.White else Color(0xFF94A3B8),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        // Refresh button
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Re-check Vitals",
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier
                                .padding(start = 4.dp, end = 2.dp)
                                .size(16.dp)
                                .clickable {
                                    refreshKey++
                                    lastCheckedMessage = "Vital diperbarui pada ${currentStage.displayName}"
                                }
                        )

                        // 🐞 Debug Log Medis Button
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF0284C7).copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                            modifier = Modifier
                                .padding(start = 4.dp, end = 2.dp)
                                .clickable { showDebugLogSheet = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BugReport,
                                    contentDescription = "Log Debug Medis",
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "Log",
                                    color = Color(0xFF38BDF8),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            if (showDebugLogSheet) {
                MedicalDebugLogSheet(
                    onDismissRequest = { showDebugLogSheet = false }
                )
            }

            // --- MODE 0: MINI (Sangat Ringkas) ---
            if (uiSizeMode == 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF020617))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "HR: ${vitals.hr} bpm",
                        color = if (vitals.hr > 100 || vitals.hr < 60) EmergencyRed else Color(0xFF10B981),
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "BP: ${vitals.systolic}/${vitals.diastolic}",
                        color = Color(0xFF38BDF8),
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "SpO2: ${vitals.spO2}%",
                        color = if (vitals.spO2 < 95) WarningAmber else Color(0xFF3B82F6),
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "RR: ${vitals.rr}x",
                        color = Color(0xFFA78BFA),
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${String.format("%.1f", vitals.temp)}°C",
                        color = Color(0xFF34D399),
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                // Animated ECG Waveform line strip for Sedang (1) and Besar (2)
                Spacer(modifier = Modifier.height(6.dp))
                EcgWaveformCanvas(
                    pulseColor = vitals.statusColor,
                    hr = vitals.hr,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF020617))
                )

                // 5 Vital Tiles
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        VitalTile(
                            title = "NADI",
                            value = "${vitals.hr}",
                            unit = "bpm",
                            icon = Icons.Default.Favorite,
                            accentColor = if (vitals.hr > 100 || vitals.hr < 60) EmergencyRed else Color(0xFF10B981),
                            subText = when {
                                vitals.hr == 0 -> "Asistol"
                                vitals.hr > 100 -> "Takikardi"
                                vitals.hr < 60 -> "Bradikardi"
                                else -> "Normal"
                            },
                            modifier = Modifier.weight(1f)
                        )

                        VitalTile(
                            title = "TENSI",
                            value = "${vitals.systolic}/${vitals.diastolic}",
                            unit = "mmHg",
                            icon = Icons.Default.FlashOn,
                            accentColor = if (vitals.systolic < 90 || vitals.systolic > 140) EmergencyRed else Color(0xFF38BDF8),
                            subText = when {
                                vitals.systolic == 0 -> "Kolaps"
                                vitals.systolic < 90 -> "Syok"
                                vitals.systolic > 140 -> "Hipertensi"
                                else -> "Normal"
                            },
                            modifier = Modifier.weight(1f)
                        )

                        VitalTile(
                            title = "SpO2",
                            value = "${vitals.spO2}",
                            unit = "%",
                            icon = Icons.Default.Air,
                            accentColor = if (vitals.spO2 < 92) EmergencyRed else if (vitals.spO2 < 95) WarningAmber else Color(0xFF3B82F6),
                            subText = when {
                                vitals.spO2 < 90 -> "Hipoksia"
                                vitals.spO2 < 95 -> "Ringan"
                                else -> "Optimal"
                            },
                            modifier = Modifier.weight(1f)
                        )

                        VitalTile(
                            title = "SUHU",
                            value = String.format("%.1f", vitals.temp),
                            unit = "°C",
                            icon = Icons.Default.Thermostat,
                            accentColor = if (vitals.temp >= 38.0) WarningAmber else Color(0xFF34D399),
                            subText = if (vitals.temp >= 38.0) "Demam" else "Normal",
                            modifier = Modifier.weight(1f)
                        )

                        VitalTile(
                            title = "RR",
                            value = "${vitals.rr}",
                            unit = "x/m",
                            icon = Icons.Default.WaterDrop,
                            accentColor = if (vitals.rr > 22 || vitals.rr < 12) WarningAmber else Color(0xFFA78BFA),
                            subText = if (vitals.rr > 20) "Takipneu" else "Normal",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // --- MODE 2: BESAR (Full Case Detail Panel) ---
                    if (uiSizeMode == 2) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF1E293B),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "📋 RINGKASAN BRIEFING KASUS PASIEN",
                                    color = Color(0xFF38BDF8),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "• Keluhan Utama: ${activeCase.chiefComplaint}",
                                    color = Color.White,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "• Keadaan Umum: ${activeCase.generalAppearance}",
                                    color = Color(0xFFCBD5E1),
                                    fontSize = 10.sp,
                                    maxLines = 3
                                )
                                Text(
                                    text = "• Sistem Organ: ${activeCase.organSystem}",
                                    color = Color(0xFF94A3B8),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }

                    // Active Interventions & Trend Banner for Sedang and Besar
                    if (vitals.trendNote.isNotBlank() || vitals.activeInterventions.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF1E293B),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
                        ) {
                            Column(modifier = Modifier.padding(6.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (vitals.trendNote.contains("Membaik") || vitals.trendNote.contains("⚡")) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                                        contentDescription = "Trend",
                                        tint = if (vitals.trendNote.contains("Membaik") || vitals.trendNote.contains("⚡")) SuccessGreen else WarningAmber,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = vitals.trendNote,
                                        color = Color(0xFFE2E8F0),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                if (vitals.activeInterventions.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Tindakan Cito:",
                                            color = Color(0xFF94A3B8),
                                            fontSize = 9.sp
                                        )
                                        vitals.activeInterventions.forEach { tag ->
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = if (tag.contains("⚠️") || tag.contains("🚨")) EmergencyRed.copy(alpha = 0.25f) else Color(0xFF0284C7).copy(alpha = 0.25f)
                                            ) {
                                                Text(
                                                    text = tag,
                                                    color = if (tag.contains("⚠️") || tag.contains("🚨")) EmergencyRed else Color(0xFF38BDF8),
                                                    fontSize = 8.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (lastCheckedMessage != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "✓ " + lastCheckedMessage!!,
                            color = Color(0xFF38BDF8),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VitalTile(
    title: String,
    value: String,
    unit: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    subText: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF020617),
        border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.35f))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color(0xFF64748B),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    color = accentColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = unit,
                    color = Color(0xFF94A3B8),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            Text(
                text = subText,
                color = Color(0xFFCBD5E1),
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun EcgWaveformCanvas(
    pulseColor: Color,
    hr: Int,
    modifier: Modifier = Modifier
) {
    val safeHr = hr.coerceIn(20, 220)
    val cycleMs = (60000 / safeHr).coerceIn(300, 2000)
    // Slower, realistic medical monitor sweep (3.5s to 5.0s per full sweep across screen)
    val durationMillis = (cycleMs * 4).coerceIn(3500, 5000)
    val beatsOnScreen = (durationMillis.toFloat() / cycleMs.toFloat())

    val infiniteTransition = rememberInfiniteTransition(label = "ecg_anim")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val centerY = height / 2f

        // 1. Medical Grid Background Lines (5mm style faint grid)
        val gridStepPx = 12.dp.toPx()
        var gx = 0f
        while (gx < width) {
            drawLine(
                color = Color(0xFF1E293B).copy(alpha = 0.5f),
                start = Offset(gx, 0f),
                end = Offset(gx, height),
                strokeWidth = 0.5.dp.toPx()
            )
            gx += gridStepPx
        }
        var gy = 0f
        while (gy < height) {
            drawLine(
                color = Color(0xFF1E293B).copy(alpha = 0.5f),
                start = Offset(0f, gy),
                end = Offset(width, gy),
                strokeWidth = 0.5.dp.toPx()
            )
            gy += gridStepPx
        }

        // 2. Asystole / Flatline for cardiac arrest (HR = 0)
        if (hr == 0) {
            val flatPath = Path()
            val stepPx = 2.dp.toPx()
            var x = 0f
            var first = true
            while (x <= width) {
                val noise = ((x.toInt() * 17 + (phase * 100).toInt()) % 3 - 1) * 0.8f
                val y = centerY + noise
                if (first) {
                    flatPath.moveTo(x, y)
                    first = false
                } else {
                    flatPath.lineTo(x, y)
                }
                x += stepPx
            }
            drawPath(
                path = flatPath,
                color = Color(0xFFEF4444),
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
            val headX = phase * width
            drawCircle(
                color = Color(0xFFEF4444),
                radius = 3.dp.toPx(),
                center = Offset(headX, centerY)
            )
            return@Canvas
        }

        // 3. Authentic Lead II ECG P-QRS-T Waveform
        val path = Path()
        val stepPx = 1.5.dp.toPx()

        var x = 0f
        var first = true
        var leadingHeadPos = Offset(0f, centerY)
        val sweepX = phase * width

        while (x <= width) {
            val beatPos = ((x / width) * beatsOnScreen + phase * beatsOnScreen) % 1.0f

            val waveOffset: Float = when {
                // P Wave: smooth rounded arch (0.12 .. 0.24)
                beatPos in 0.12f..0.24f -> {
                    val pNorm = (beatPos - 0.12f) / 0.12f
                    -kotlin.math.sin(pNorm * Math.PI).toFloat() * (height * 0.16f)
                }
                // PR Segment: baseline (0.24 .. 0.32)
                beatPos in 0.24f..0.32f -> 0f

                // Q Wave: small sharp downward dip (0.32 .. 0.35)
                beatPos in 0.32f..0.35f -> {
                    val qNorm = (beatPos - 0.32f) / 0.03f
                    kotlin.math.sin(qNorm * Math.PI).toFloat() * (height * 0.12f)
                }

                // R Peak: tall sharp upward spike (0.35 .. 0.40)
                beatPos in 0.35f..0.375f -> {
                    val rNorm = (beatPos - 0.35f) / 0.025f
                    - (rNorm * (height * 0.82f))
                }
                beatPos in 0.375f..0.40f -> {
                    val rNorm = (beatPos - 0.375f) / 0.025f
                    - (height * 0.82f) + rNorm * (height * 1.10f)
                }

                // S Wave recovery: return to baseline (0.40 .. 0.44)
                beatPos in 0.40f..0.44f -> {
                    val sNorm = (beatPos - 0.40f) / 0.04f
                    (1f - sNorm) * (height * 0.28f)
                }

                // ST Segment: baseline (0.44 .. 0.52)
                beatPos in 0.44f..0.52f -> 0f

                // T Wave: smooth rounded arch (0.52 .. 0.72)
                beatPos in 0.52f..0.72f -> {
                    val tNorm = (beatPos - 0.52f) / 0.20f
                    -kotlin.math.sin(tNorm * Math.PI).toFloat() * (height * 0.25f)
                }

                // TP Baseline (0.72 .. 1.00)
                else -> 0f
            }

            val y = centerY + waveOffset

            if (first) {
                path.moveTo(x, y)
                first = false
            } else {
                path.lineTo(x, y)
            }

            if (kotlin.math.abs(x - sweepX) < stepPx * 1.5f) {
                leadingHeadPos = Offset(x, y)
            }

            x += stepPx
        }

        // Draw primary ECG line
        drawPath(
            path = path,
            color = pulseColor,
            style = Stroke(width = 2.2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Glowing leading head dot at sweep position
        drawCircle(
            color = Color.White,
            radius = 3.5.dp.toPx(),
            center = leadingHeadPos
        )
        drawCircle(
            color = pulseColor.copy(alpha = 0.5f),
            radius = 7.dp.toPx(),
            center = leadingHeadPos
        )
    }
}

private fun computeDynamicVitals(
    case: ClinicalCase,
    userExams: List<UserExamResult>,
    treatmentInput: String,
    citoActionLogs: List<CitoActionFeedback>,
    remainingSeconds: Int,
    isEmergencyMode: Boolean
): ComputedVitals {
    return VitalSignsManager.computeVitals(
        case = case,
        citoActionLogs = citoActionLogs,
        userExams = userExams,
        treatmentInput = treatmentInput,
        isEmergencyMode = isEmergencyMode,
        remainingSeconds = remainingSeconds
    )
}
