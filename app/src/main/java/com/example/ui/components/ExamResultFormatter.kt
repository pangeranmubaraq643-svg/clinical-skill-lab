package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExamCategory
import com.example.data.model.UserExamResult
import com.example.ui.theme.MedicalTeal40

/**
 * Checks if the clinical exam result contains abnormal, pathological, or critical markers.
 */
fun detectAbnormalExamResult(result: String): Boolean {
    if (result.isBlank()) return false
    val lower = result.lowercase()

    // Explicit abnormal keywords
    val abnormalKeywords = listOf(
        "abnormal", "patologis", "kritis", "st-elevasi", "st elevasi", "t-inversi", "t inversi",
        "leukositosis", "trombositopenia", "anemia", "hipokalemia", "hiperkalemia", "hiponatremia",
        "hiperglikemia", "hipoglikemia", "asidosis", "alkalosis", "kardiomegali", "infiltrat",
        "konsolidasi", "pneumothorax", "edema paru", "fraktur", "ruptur", "perforasi",
        "kaku kuduk (+)", "defans", "wheezing", "rhonchi", "murmur", "gallop", "stridor",
        "hipopion", "hifema", "bta positif", "reaktif", "mtb detected", "tubex tf score",
        "hematuria", "proteinuria", "ketonuria", "bakteriuria", "sangat tinggi", "sangat rendah",
        "meningkat", "batu empedu", "batu ureter", "hidronefrosis", "apendisitis", "kolesistitis"
    )

    for (kw in abnormalKeywords) {
        if (lower.contains(kw)) {
            // Guard against "rhonchi (-/-)", "wheezing (-/-)", "murmur (-)", "kaku kuduk (-)"
            if (kw == "rhonchi" && (lower.contains("rhonchi (-/-)") || lower.contains("rhonchi (-)"))) continue
            if (kw == "wheezing" && (lower.contains("wheezing (-/-)") || lower.contains("wheezing (-)"))) continue
            if (kw == "murmur" && (lower.contains("murmur (-)") || lower.contains("murmur (-/-)"))) continue
            if (kw == "stridor" && (lower.contains("stridor (-)") || lower.contains("stridor (-/-)"))) continue
            if (kw == "defans" && (lower.contains("defans muskular (-)") || lower.contains("defans (-)"))) continue
            if (kw == "infiltrat" && (lower.contains("infiltrat (-)") || lower.contains("tak tampak infiltrat"))) continue
            if (kw == "pneumothorax" && lower.contains("tak tampak") && lower.contains("pneumothorax")) continue
            if (kw == "kardiomegali" && lower.contains("tidak tampak kardiomegali")) continue
            if (kw == "anemia" && lower.contains("anemis (-/-)")) continue
            if (kw == "reaktif" && lower.contains("non-reaktif")) continue
            return true
        }
    }

    // Check for positive flags like "positif (+)" or "(+3)" or "(+4)" or "(h)" or "(l)"
    if (Regex("(?i)\\bpositif\\b").containsMatchIn(lower) && !lower.contains("gram positif flora normal")) return true
    if (Regex("(?i)\\(\\s*[hl]\\s*\\)").containsMatchIn(lower)) return true
    if (Regex("\\(\\+[1-4]\\)").containsMatchIn(lower)) return true
    if (Regex("(?i)\\b(tinggi|rendah)\\b").containsMatchIn(lower) && !lower.contains("normal")) return true

    return false
}

/**
 * Builds an AnnotatedString that parses Markdown bold (**text**) cleanly, removes all raw asterisks,
 * and highlights abnormal medical keywords in Bold Red / Accent so the doctor can read effortlessly.
 */
fun parseExamResultToAnnotatedString(
    rawText: String,
    abnormalColor: Color = Color(0xFFDC2626), // Crimson Red
    normalColor: Color = Color(0xFF166534),   // Deep Forest Green
    boldTextColor: Color = Color(0xFF1E293B), // Slate 800
    defaultTextColor: Color = Color(0xFF334155) // Slate 700
): AnnotatedString {
    // 1. Sanitize raw text: convert bullet points and unclosed asterisks
    var cleanText = rawText.replace("\r\n", "\n")
        .replace(Regex("(?m)^[\\*\\-]\\s+"), "• ")
        .replace("***", "**")
        .trim()

    // If odd number of asterisks, strip stray ones
    if (cleanText.count { it == '*' } % 2 != 0) {
        cleanText = cleanText.replace("*", "")
    }

    // List of keywords to highlight when encountered
    val abnormalWords = listOf(
        "abnormal", "patologis", "kritis", "st-elevasi", "st elevasi", "t-inversi",
        "leukositosis", "trombositopenia", "anemia", "hipokalemia", "hiperkalemia", "hiponatremia",
        "hiperglikemia", "hipoglikemia", "asidosis", "alkalosis", "kardiomegali", "infiltrat",
        "konsolidasi", "pneumothorax", "edema paru", "fraktur", "ruptur", "perforasi",
        "kaku kuduk (+)", "defans", "wheezing (+)", "rhonchi (+)", "murmur (+)", "gallop (+)", "stridor (+)",
        "hipopion", "hifema", "bta positif", "reaktif", "mtb detected",
        "hematuria", "proteinuria", "ketonuria", "bakteriuria", "sangat tinggi", "sangat rendah",
        "meningkat", "batu empedu", "batu ureter", "hidronefrosis", "positif", "(h)", "(l)"
    )

    val normalWords = listOf(
        "dalam batas normal", "dbn", "negatif", "non-reaktif", "mtb not detected",
        "eutiroid", "jernih", "intakt", "simetris", "supel", "vesikuler (+/+)", "isokor"
    )

    // Split by Markdown bold markers "**"
    val parts = cleanText.split("**")

    return buildAnnotatedString {
        for (i in parts.indices) {
            val part = parts[i]
            if (part.isEmpty()) continue

            val isMarkdownBold = (i % 2 == 1) // Odd indices were inside **...**

            // Process internal text tokens to highlight abnormalities
            var lastIdx = 0
            val lowerPart = part.lowercase()

            // Find matches for abnormal keywords
            val matches = mutableListOf<Triple<Int, Int, Color>>()

            for (kw in abnormalWords) {
                var start = lowerPart.indexOf(kw)
                while (start >= 0) {
                    val end = start + kw.length
                    // Avoid overlapping
                    if (matches.none { (s, e, _) -> (start in s until e) || (end in (s + 1)..e) }) {
                        matches.add(Triple(start, end, abnormalColor))
                    }
                    start = lowerPart.indexOf(kw, start + 1)
                }
            }

            for (kw in normalWords) {
                var start = lowerPart.indexOf(kw)
                while (start >= 0) {
                    val end = start + kw.length
                    if (matches.none { (s, e, _) -> (start in s until e) || (end in (s + 1)..e) }) {
                        matches.add(Triple(start, end, normalColor))
                    }
                    start = lowerPart.indexOf(kw, start + 1)
                }
            }

            matches.sortBy { it.first }

            if (matches.isEmpty()) {
                withStyle(
                    SpanStyle(
                        fontWeight = if (isMarkdownBold) FontWeight.Bold else FontWeight.Normal,
                        color = if (isMarkdownBold) boldTextColor else defaultTextColor
                    )
                ) {
                    append(part)
                }
            } else {
                var cur = 0
                for ((s, e, col) in matches) {
                    if (s > cur) {
                        withStyle(
                            SpanStyle(
                                fontWeight = if (isMarkdownBold) FontWeight.Bold else FontWeight.Normal,
                                color = if (isMarkdownBold) boldTextColor else defaultTextColor
                            )
                        ) {
                            append(part.substring(cur, s))
                        }
                    }
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = col
                        )
                    ) {
                        append(part.substring(s, e))
                    }
                    cur = e
                }
                if (cur < part.length) {
                    withStyle(
                        SpanStyle(
                            fontWeight = if (isMarkdownBold) FontWeight.Bold else FontWeight.Normal,
                            color = if (isMarkdownBold) boldTextColor else defaultTextColor
                        )
                    ) {
                        append(part.substring(cur))
                    }
                }
            }
        }
    }
}

/**
 * Modern Card Composable for displaying Examination Results with:
 * - Proper markdown bold rendering (No raw asterisks)
 * - Highlighted abnormal results in Bold Crimson
 * - High-contrast clean abnormal badge vs normal badge
 * - Animated shimmer / loading state for AI-generated custom queries
 */
@Composable
fun ExamResultCard(
    result: UserExamResult,
    modifier: Modifier = Modifier
) {
    val isAbnormal = detectAbnormalExamResult(result.result)

    val cardBorderColor = when {
        result.isLoading -> MedicalTeal40.copy(alpha = 0.5f)
        isAbnormal -> Color(0xFFEF4444).copy(alpha = 0.4f)
        else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    }

    val headerBgColor = when {
        isAbnormal -> Color(0xFFFEF2F2) // Light rose red
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, cardBorderColor, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isAbnormal) 2.dp else 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBgColor)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Text(
                        text = result.examName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (result.isAiGenerated) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MedicalTeal40.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "AI Generated",
                                    tint = MedicalTeal40,
                                    modifier = Modifier.size(10.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "AI",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MedicalTeal40
                                )
                            }
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Status Badge
                    if (result.isLoading) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MedicalTeal40.copy(alpha = 0.12f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(11.dp),
                                    strokeWidth = 1.5.dp,
                                    color = MedicalTeal40
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Menganalisis...",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MedicalTeal40
                                )
                            }
                        }
                    } else if (isAbnormal) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFEE2E2)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WarningAmber,
                                    contentDescription = "Abnormal",
                                    tint = Color(0xFFDC2626),
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "ABNORMAL",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFDC2626)
                                )
                            }
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFDCFCE7)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Normal",
                                    tint = Color(0xFF16A34A),
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "NORMAL",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (result.costRupiah == 0L) "Gratis" else "Rp ${formatRupiah(result.costRupiah)}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Body Result Text
            Column(modifier = Modifier.padding(14.dp)) {
                if (result.isLoading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = MedicalTeal40
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "AI Medis sedang memproses hasil pemeriksaan sesuai kondisi dan patofisiologi penyakit pasien...",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                } else {
                    val formattedText = parseExamResultToAnnotatedString(
                        rawText = result.result,
                        abnormalColor = Color(0xFFDC2626),
                        normalColor = Color(0xFF15803D),
                        boldTextColor = MaterialTheme.colorScheme.onSurface,
                        defaultTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    )

                    Text(
                        text = formattedText,
                        fontSize = 12.5.sp,
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}

private fun formatRupiah(amount: Long): String {
    return String.format("%,d", amount).replace(',', '.')
}
