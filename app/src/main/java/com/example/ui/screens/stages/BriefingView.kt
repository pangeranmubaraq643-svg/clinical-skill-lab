package com.example.ui.screens.stages

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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClinicalCase
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTeal40
import com.example.ui.theme.WarningAmber

@Composable
fun BriefingView(
    activeCase: ClinicalCase,
    onNextStage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dualComplaints = remember(activeCase.chiefComplaint) {
        com.example.data.remote.GeminiService.extractDualChiefComplaints(activeCase.chiefComplaint, activeCase)
    }
    val cleanGeneralAppearance = remember(activeCase.generalAppearance) {
        val raw = com.example.data.remote.GeminiService.humanizeMedicalTerms(
            com.example.data.remote.GeminiService.sanitizePatientResponse(activeCase.generalAppearance, activeCase)
        )
        simplifyGeneralAppearanceForBriefing(raw)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Stage Title Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Assignment,
                contentDescription = "Briefing",
                tint = MedicalTeal40,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Briefing Pasien (Informasi Awal)",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // 1. Skenario Kasus Klinis (Kasus Soal OSCE 1 Paragraf)
        val osceVignetteStory = remember(activeCase, dualComplaints, cleanGeneralAppearance) {
            generateOsceCaseVignette(activeCase, dualComplaints, cleanGeneralAppearance)
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f))
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
                            color = EmergencyRed.copy(alpha = 0.15f)
                        ) {
                            Box(modifier = Modifier.padding(6.dp)) {
                                Icon(
                                    imageVector = Icons.Default.Assignment,
                                    contentDescription = "OSCE Case",
                                    tint = EmergencyRed,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Skenario Kasus Klinis (Soal OSCE)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = EmergencyRed.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "VIGNETTE OSCE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = EmergencyRed,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Single-Paragraph Narrative Story Container
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Vignette",
                                tint = EmergencyRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Deskripsi Keluhan Pasien:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmergencyRed
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = osceVignetteStory,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Quick Fact Badges
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Onset",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Onset: ${dualComplaints.onsetTimeline}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Badge,
                                contentDescription = "Triage",
                                tint = MedicalTeal40,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Instalasi: ${if (activeCase.isEmergencyCase) "IGD (Gawat Darurat)" else "Poliklinik"}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Deskripsi Pasien Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Deskripsi & Profil Pasien",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoTile(icon = Icons.Default.Person, label = "Usia & Gender", value = "${activeCase.patientAge} Thn (${activeCase.patientGender})")
                    InfoTile(icon = Icons.Default.Badge, label = "Pekerjaan", value = activeCase.patientOccupation)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Keadaan Umum (Triage Awal):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = cleanGeneralAppearance,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                ) {
                    Text(
                        text = "💡 Observasi & temuan fisik lengkap dapat diakses pada Clinician Hint Level 1.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Tanda-Tanda Vital (TTV) Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tanda-Tanda Vital (TTV)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Vitals Grid
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        VitalChip(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Speed,
                            label = "Tekanan Darah",
                            value = activeCase.td,
                            unit = ""
                        )
                        VitalChip(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.MonitorHeart,
                            label = "Laju Nadi",
                            value = "${activeCase.nadi}",
                            unit = "x/menit"
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        VitalChip(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Air,
                            label = "Laju Napas (RR)",
                            value = "${activeCase.rr}",
                            unit = "x/menit"
                        )
                        VitalChip(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Thermostat,
                            label = "Suhu Tubuh",
                            value = "${activeCase.suhu}",
                            unit = "°C"
                        )
                    }

                    VitalChip(
                        modifier = Modifier.fillMaxWidth(),
                        icon = Icons.Default.MonitorHeart,
                        label = "Saturasi Oksigen (SpO2)",
                        value = "${activeCase.spO2}%",
                        unit = "pemasangan oximetry"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Next Stage Button
        Button(
            onClick = onNextStage,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Lanjut ke Stage 3: Anamnesis Pasien",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun InfoTile(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MedicalTeal40,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun VitalChip(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    value: String,
    unit: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MedicalTeal40,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    if (unit.isNotBlank()) {
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = unit, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

private fun simplifyGeneralAppearanceForBriefing(appearance: String): String {
    // Strip out content in parentheses like (Levine sign (+)), (diaphoresis), (GCS E3V2M5)
    val noParentheses = appearance.replace(Regex("""\([^)]*\)"""), "").trim()
    val parts = noParentheses.split(",", ";").map { it.trim() }.filter { it.isNotBlank() }
    
    val triageKeywords = listOf("tampak", "kesadaran", "lemas", "pucat", "gelisah", "kesakitan", "merintih", "sesak", "kejang", "demam", "rewel", "mengamuk", "letargis", "sakit", "emergensi", "pingsan", "bayi", "hamil", "anak", "agitated", "somnolen", "apatis")
    val filtered = parts.filter { part ->
        triageKeywords.any { kw -> part.contains(kw, ignoreCase = true) }
    }
    
    val briefSummary = when {
        filtered.isNotEmpty() -> filtered.take(2).joinToString(", ")
        parts.isNotEmpty() -> parts.first()
        else -> "Tampak sakit sedang, kesadaran compos mentis"
    }
    return briefSummary.trimEnd('.', ' ')
}

private fun simplifyChiefComplaintForBriefing(chiefComplaint: String): String {
    if (chiefComplaint.isBlank()) return chiefComplaint
    val core = com.example.data.remote.GeminiService.extractCoreChiefComplaint(chiefComplaint)
    if (core.isNotBlank() && !core.equals("dok", ignoreCase = true) && !core.equals("dokter", ignoreCase = true)) {
        return core
    }
    return chiefComplaint
}

private fun generateOsceCaseVignette(
    case: ClinicalCase,
    dualComplaints: com.example.data.remote.GeminiService.DualComplaintResult,
    cleanGeneralAppearance: String
): String {
    val genderStr = if (case.patientGender.contains("L", ignoreCase = true) ||
        case.patientGender.contains("Pria", ignoreCase = true) ||
        case.patientGender.contains("Laki", ignoreCase = true)
    ) "laki-laki" else "perempuan"
    
    val ageStr = "${case.patientAge} tahun"

    val jobClean = com.example.data.repository.MedicalVitalsValidator.sanitizeOccupation(
        case.patientOccupation,
        case.patientAge,
        case.patientGender
    ).trim()
    val jobSegment = if (jobClean.isNotBlank() &&
        !jobClean.equals("Tidak bekerja", ignoreCase = true) &&
        !jobClean.equals("-", ignoreCase = true) &&
        !jobClean.equals("Belum Bekerja", ignoreCase = true)
    ) {
        ", seorang ${jobClean.lowercase()},"
    } else {
        ""
    }

    val isEmergency = case.isEmergencyCase ||
            case.chiefComplaint.contains("dada", ignoreCase = true) ||
            case.chiefComplaint.contains("sesak", ignoreCase = true) ||
            case.chiefComplaint.contains("kejang", ignoreCase = true) ||
            case.chiefComplaint.contains("pingsan", ignoreCase = true) ||
            case.chiefComplaint.contains("stroke", ignoreCase = true) ||
            case.chiefComplaint.contains("perdarahan", ignoreCase = true) ||
            case.chiefComplaint.contains("perforasi", ignoreCase = true) ||
            case.chiefComplaint.contains("trauma", ignoreCase = true) ||
            case.chiefComplaint.contains("kolik", ignoreCase = true)

    val facility = if (isEmergency) "Instalasi Gawat Darurat (IGD)" else "Poliklinik Rawat Jalan"
    val arrivalAction = if (isEmergency) "datang diantar ke $facility" else "datang ke $facility"

    var primary = dualComplaints.primaryComplaint.trim().trimEnd('.', ',')
    if (primary.startsWith("pasien mengeluh", ignoreCase = true)) {
        primary = primary.substring(14).trim()
    } else if (primary.startsWith("mengeluh", ignoreCase = true)) {
        primary = primary.substring(8).trim()
    }
    val primaryPhrase = primary.replaceFirstChar { if (it.isUpperCase()) it.lowercase() else it.toString() }

    val onset = dualComplaints.onsetTimeline.trim().trimEnd('.', ',')
    val onsetPhrase = when {
        onset.startsWith("sejak", ignoreCase = true) ||
                onset.startsWith("selama", ignoreCase = true) ||
                onset.startsWith("pasca", ignoreCase = true) -> onset.lowercase()
        onset.isNotBlank() && !onset.contains("akut", ignoreCase = true) -> "sejak $onset"
        else -> "yang dirasakan secara mendadak"
    }

    var appearance = cleanGeneralAppearance.trim().trimEnd('.', ',')
    if (appearance.startsWith("tampak", ignoreCase = true)) {
        appearance = appearance.substring(6).trim()
    }
    val appearancePhrase = if (appearance.isNotBlank()) "tampak $appearance" else "tampak sakit sedang dengan kesadaran compos mentis"

    return "Seorang $genderStr berusia $ageStr$jobSegment $arrivalAction dengan keluhan utama $primaryPhrase $onsetPhrase. Saat pertama kali tiba di ruang periksa, pasien $appearancePhrase. Anda sebagai dokter jaga diminta untuk melakukan anamnesis terarah, pemeriksaan fisik yang relevan, serta menentukan tatalaksana awal yang tepat."
}
