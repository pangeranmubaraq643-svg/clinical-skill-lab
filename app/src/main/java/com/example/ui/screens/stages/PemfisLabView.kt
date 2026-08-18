package com.example.ui.screens.stages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClinicalCase
import com.example.data.model.ExamCategory
import com.example.data.model.ExamItem
import com.example.data.model.UserExamResult
import com.example.data.remote.GeminiService
import com.example.data.repository.MasterExamsCatalog
import com.example.ui.components.ExamResultCard
import com.example.ui.theme.MedicalTeal40
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PemfisLabView(
    activeCase: ClinicalCase,
    userExams: List<UserExamResult>,
    totalSpentRupiah: Long,
    onRequestExam: (ExamItem) -> Unit,
    onRequestCustomExam: (String) -> Unit,
    onFinishExams: () -> Unit,
    onJumpToAnamnesis: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    // AI Suggestions State
    var geminiExamSuggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var isGeminiExamLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val categories = listOf("Semua", "Pemeriksaan Fisik", "Laboratorium", "Radiologi & EKG")

    val allDisplayExams = remember(activeCase.id) {
        MasterExamsCatalog.getMergedExamsForCase(activeCase.availableExams, activeCase).sortedBy { it.name.lowercase() }
    }

    // Debounced AI Exam suggestions as player types
    LaunchedEffect(searchQuery) {
        if (searchQuery.trim().length >= 2) {
            delay(350)
            isGeminiExamLoading = true
            geminiExamSuggestions = GeminiService.getSuggestionsForInput(searchQuery.trim(), "EXAM")
            isGeminiExamLoading = false
        } else {
            geminiExamSuggestions = emptyList()
        }
    }

    val localFiltered = remember(searchQuery) {
        if (searchQuery.isNotBlank()) MasterExamsCatalog.filterExams(searchQuery).map { it.name }
        else emptyList()
    }

    val displayExamSuggestions = remember(geminiExamSuggestions, localFiltered) {
        (geminiExamSuggestions + localFiltered).distinct().filter { it.isNotBlank() }
    }

    val filteredExams = allDisplayExams.filter { exam ->
        val categoryMatch = when (selectedCategoryIndex) {
            1 -> exam.category == ExamCategory.PEMFIS
            2 -> exam.category == ExamCategory.LAB
            3 -> exam.category == ExamCategory.IMAGING
            else -> true
        }
        val searchMatch = searchQuery.isBlank() || exam.name.contains(searchQuery, ignoreCase = true)
        categoryMatch && searchMatch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (onJumpToAnamnesis != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = onJumpToAnamnesis,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("💬 Kembali ke Stage Anamnesis (Chat Pasien)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        // Header Card with Running Cost Summary
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = "System Lab",
                            tint = MedicalTeal40
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sistem Lab & Pemeriksa [STAGE: PEMFIS & LAB]",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = "Cost",
                                tint = MedicalTeal40,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Rp ${formatRupiah(totalSpentRupiah)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Pilihlah pemeriksaan penunjang yang tepat dan efisien (Cost-Effective) sesuai indikasi medis.",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Search & Request Bar
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Cari atau ketik pemeriksaan kustom...", fontSize = 13.sp) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MedicalTeal40) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MedicalTeal40)
                )

                if (searchQuery.isNotBlank()) {
                    Button(
                        onClick = {
                            onRequestCustomExam(searchQuery)
                            searchQuery = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Request",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Minta (AI)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // AI Suggestion Box when typing or searching
            if (searchQuery.isNotBlank() || displayExamSuggestions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (searchQuery.isNotBlank())
                                    "💡 Saran Pemeriksaan Berdasarkan Ketikan '${searchQuery.trim()}':"
                                else
                                    "💡 Saran Pemeriksaan AI:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedicalTeal40
                            )
                            if (isGeminiExamLoading) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CircularProgressIndicator(modifier = Modifier.size(12.dp), strokeWidth = 1.5.dp, color = MedicalTeal40)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("AI mencari...", fontSize = 10.sp, color = MedicalTeal40)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        if (displayExamSuggestions.isNotEmpty()) {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(displayExamSuggestions) { examName ->
                                    val isAlreadyRequested = userExams.any { it.examName.equals(examName, ignoreCase = true) }
                                    
                                    SuggestionChip(
                                        onClick = {
                                            if (!isAlreadyRequested) {
                                                val matched = allDisplayExams.find { it.name.equals(examName, ignoreCase = true) }
                                                if (matched != null) {
                                                    onRequestExam(matched)
                                                } else {
                                                    onRequestCustomExam(examName)
                                                }
                                                searchQuery = ""
                                            }
                                        },
                                        label = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                if (isAlreadyRequested) {
                                                    Icon(
                                                        imageVector = Icons.Default.CheckCircle,
                                                        contentDescription = "Done",
                                                        tint = MedicalTeal40,
                                                        modifier = Modifier.size(12.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                }
                                                Text(
                                                    text = if (isAlreadyRequested) "$examName (Diminta)" else "+ $examName",
                                                    fontSize = 11.sp,
                                                    fontWeight = if (isAlreadyRequested) FontWeight.Normal else FontWeight.Medium
                                                )
                                            }
                                        },
                                        colors = SuggestionChipDefaults.suggestionChipColors(
                                            containerColor = if (isAlreadyRequested) MedicalTeal40.copy(alpha = 0.08f) else MedicalTeal40.copy(alpha = 0.18f),
                                            labelColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = "Ketik beberapa huruf nama pemeriksaan atau gejala klinis untuk memicu koreksi typo dan saran AI.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Category Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedCategoryIndex,
            edgePadding = 0.dp,
            divider = {}
        ) {
            categories.forEachIndexed { index, title ->
                Tab(
                    selected = selectedCategoryIndex == index,
                    onClick = { selectedCategoryIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedCategoryIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Available Exams List
        Text(
            text = "Pemeriksaan Tersedia (${filteredExams.size}):",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            filteredExams.forEach { exam ->
                val isRequested = userExams.any { it.examName.equals(exam.name, ignoreCase = true) }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isRequested) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = exam.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Estimasi Biaya: ${if (exam.costRupiah == 0L) "GRATIS (Pemfis)" else "Rp ${formatRupiah(exam.costRupiah)}"}",
                                fontSize = 11.sp,
                                color = if (exam.costRupiah == 0L) MedicalTeal40 else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (isRequested) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MedicalTeal40.copy(alpha = 0.2f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Done",
                                        tint = MedicalTeal40,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Diminta", fontSize = 11.sp, color = MedicalTeal40, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else {
                            Button(
                                onClick = { onRequestExam(exam) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Minta", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Results Section (Dynamic Laboratory Output)
        Text(
            text = "Hasil Pemeriksaan yang Telah Dilakukan (${userExams.size}):",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (userExams.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Text(
                    text = "Belum ada pemeriksaan yang diminta. Pilihlah pemeriksaan di atas atau ketik jenis pemeriksaan khusus untuk melihat hasil spesifik dan biayanya.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                userExams.forEach { result ->
                    ExamResultCard(result = result)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Prominent Button: "Selesai Pemeriksaan"
        Button(
            onClick = onFinishExams,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Finish", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Selesai Pemeriksaan (Ke Diagnosis & Tatalaksana)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

private fun formatRupiah(amount: Long): String {
    return String.format("%,d", amount).replace(',', '.')
}

