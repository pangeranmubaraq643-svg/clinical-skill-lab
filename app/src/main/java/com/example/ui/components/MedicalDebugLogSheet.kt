package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.LogCategory
import com.example.data.repository.LogLevel
import com.example.data.repository.MedicalDebugLogger
import com.example.data.repository.MedicalLogEntry
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTeal40
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalDebugLogSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val allLogs by MedicalDebugLogger.logs.collectAsState()

    var selectedFilter by remember { mutableStateOf<LogCategory?>(null) }
    var filterOnlyErrors by remember { mutableStateOf(false) }

    val filteredLogs = remember(allLogs, selectedFilter, filterOnlyErrors) {
        allLogs.filter { log ->
            val matchesCategory = selectedFilter == null || log.category == selectedFilter
            val matchesError = !filterOnlyErrors || log.level == LogLevel.ERROR || log.level == LogLevel.WARN
            matchesCategory && matchesError
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color(0xFF0F172A),
        contentColor = Color.White,
        modifier = modifier.testTag("medical_debug_log_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MedicalTeal40.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.BugReport,
                            contentDescription = "Debug Logger",
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Log Medis & Debugger TTV / CITO",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Tracking Real-time Skor Waktu & Status Pasien (${allLogs.size} Entri)",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                Row {
                    IconButton(
                        onClick = { MedicalDebugLogger.clearLogs() },
                        modifier = Modifier.testTag("clear_debug_logs_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Hapus Log",
                            tint = Color(0xFFF87171)
                        )
                    }
                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.testTag("close_debug_log_sheet_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Filter Chips Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = selectedFilter == null && !filterOnlyErrors,
                    onClick = {
                        selectedFilter = null
                        filterOnlyErrors = false
                    },
                    label = { Text("Semua (${allLogs.size})", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF2563EB),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFF1E293B),
                        labelColor = Color(0xFFCBD5E1)
                    )
                )

                FilterChip(
                    selected = selectedFilter == LogCategory.TTV_MANAGER,
                    onClick = {
                        selectedFilter = if (selectedFilter == LogCategory.TTV_MANAGER) null else LogCategory.TTV_MANAGER
                    },
                    label = { Text("TTV Manager", fontSize = 11.sp) },
                    leadingIcon = { Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(14.dp)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF0284C7),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFF1E293B),
                        labelColor = Color(0xFFCBD5E1)
                    )
                )

                FilterChip(
                    selected = selectedFilter == LogCategory.CITO_STAGE,
                    onClick = {
                        selectedFilter = if (selectedFilter == LogCategory.CITO_STAGE) null else LogCategory.CITO_STAGE
                    },
                    label = { Text("Stage CITO", fontSize = 11.sp) },
                    leadingIcon = { Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(14.dp)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFD97706),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFF1E293B),
                        labelColor = Color(0xFFCBD5E1)
                    )
                )

                FilterChip(
                    selected = filterOnlyErrors,
                    onClick = { filterOnlyErrors = !filterOnlyErrors },
                    label = { Text("Warn/Error", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmergencyRed,
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFF1E293B),
                        labelColor = Color(0xFFCBD5E1)
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Log List
            if (filteredLogs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Belum ada log medis tercatat.",
                            color = Color(0xFF94A3B8),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Lakukan intervensi CITO atau ubah kondisi TTV untuk melihat log.",
                            color = Color(0xFF64748B),
                            fontSize = 11.sp
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredLogs, key = { it.id }) { logItem ->
                        LogEntryCard(logItem = logItem)
                    }
                }
            }
        }
    }
}

@Composable
private fun LogEntryCard(logItem: MedicalLogEntry) {
    var expanded by remember { mutableStateOf(false) }

    val categoryColor = when (logItem.category) {
        LogCategory.TTV_MANAGER -> Color(0xFF38BDF8)
        LogCategory.CITO_STAGE -> Color(0xFFF59E0B)
        LogCategory.MEDICAL_LOGIC -> Color(0xFFA855F7)
    }

    val levelBgColor = when (logItem.level) {
        LogLevel.ERROR -> EmergencyRed.copy(alpha = 0.2f)
        LogLevel.WARN -> WarningAmber.copy(alpha = 0.2f)
        LogLevel.INFO -> SuccessGreen.copy(alpha = 0.15f)
        LogLevel.DEBUG -> Color(0xFF3B82F6).copy(alpha = 0.15f)
    }

    val levelTextColor = when (logItem.level) {
        LogLevel.ERROR -> EmergencyRed
        LogLevel.WARN -> WarningAmber
        LogLevel.INFO -> SuccessGreen
        LogLevel.DEBUG -> Color(0xFF60A5FA)
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Header line: Timestamp | Category Badge | Level Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = logItem.timestampFormatted,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        color = Color(0xFF94A3B8)
                    )
                    Spacer(modifier = Modifier.width(6.dp))

                    // Category Tag
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = categoryColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = logItem.category.displayName,
                            color = categoryColor,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Level Tag
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = levelBgColor
                    ) {
                        Text(
                            text = logItem.level.name,
                            color = levelTextColor,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Message text
            Text(
                text = logItem.message,
                color = Color.White,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                fontFamily = FontFamily.Monospace
            )

            // Metrics Summary Bar if available
            if (logItem.timeScoreSeconds != null || logItem.patientStatus != null || logItem.mapValue != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF0F172A))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (logItem.timeScoreSeconds != null) {
                        Text(
                            text = "⏱️ Waktu: ${logItem.timeScoreSeconds}s",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFBBF24)
                        )
                    }

                    if (logItem.patientStatus != null) {
                        val statusColor = when {
                            logItem.patientStatus.contains("ARREST") || logItem.patientStatus.contains("HENTI") -> EmergencyRed
                            logItem.patientStatus.contains("KRITIS") || logItem.patientStatus.contains("SYOK") -> WarningAmber
                            else -> SuccessGreen
                        }
                        Text(
                            text = "❤️ Status: ${logItem.patientStatus}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                        )
                    }

                    if (logItem.mapValue != null) {
                        Text(
                            text = "📊 MAP: ${logItem.mapValue} mmHg",
                            fontSize = 10.sp,
                            color = Color(0xFF38BDF8)
                        )
                    }
                }
            }

            // Expanded detail JSON / details
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF020617),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "🔍 RAW PARAMETERS & PAYLOAD:",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = logItem.detailsJson ?: "Category: ${logItem.category.name}\nTimeDelta: ${logItem.timeDeltaSeconds ?: 0}s\nShockIndex: ${logItem.shockIndex ?: 0.0}",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = Color(0xFF34D399)
                            )
                        }
                    }
                }
            }
        }
    }
}
