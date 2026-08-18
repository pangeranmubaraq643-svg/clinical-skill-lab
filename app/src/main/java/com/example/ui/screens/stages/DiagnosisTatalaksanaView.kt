package com.example.ui.screens.stages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
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
import com.example.data.model.ChatMessage
import com.example.data.model.ClinicalCase
import com.example.data.model.UserExamResult
import com.example.data.remote.GeminiService
import com.example.data.repository.MedicalCatalog
import com.example.ui.components.CaseReviewModal
import com.example.ui.theme.MedicalTeal40
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DiagnosisTatalaksanaView(
    primaryDiagnosis: String,
    differentialDiagnosis: String,
    treatment: String,
    education: String,
    geminiSuggestion: String?,
    isLoadingGemini: Boolean,
    aiDiagnosisSuggestion: String? = null,
    isLoadingAiDiagnosis: Boolean = false,
    aiDiffSuggestions: List<String>? = null,
    isLoadingAiDiff: Boolean = false,
    aiMedicationSuggestion: String? = null,
    isLoadingAiMedication: Boolean = false,
    activeCase: ClinicalCase? = null,
    userExams: List<UserExamResult> = emptyList(),
    chatHistory: List<ChatMessage> = emptyList(),
    onUpdateInputs: (primary: String, diff: String, treat: String, edu: String) -> Unit,
    onRequestGeminiSuggestion: () -> Unit,
    onRequestAiDiagnosis: () -> Unit = {},
    onRequestAiDiff: () -> Unit = {},
    onRequestAiMedication: () -> Unit = {},
    onApplySuggestedPrimary: (String) -> Unit = {},
    onApplySuggestedDiff: (String) -> Unit = {},
    onApplyAllSuggestedDiff: (List<String>) -> Unit = {},
    onApplySuggestedMedication: (String) -> Unit = {},
    onSubmitAndEvaluate: () -> Unit,
    onJumpToAnamnesis: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showReviewModal by remember { mutableStateOf(false) }

    // Prescription Builder States
    var drugSearchQuery by remember { mutableStateOf("") }
    var selectedDrug by remember { mutableStateOf("") }
    var selectedForm by remember { mutableStateOf("Tablet") }
    var selectedRoute by remember { mutableStateOf("Oral") }
    var selectedFrequency by remember { mutableStateOf("1x1") }
    var selectedTiming by remember { mutableStateOf("Sesudah Makan") }

    // Preset Options
    val drugForms = listOf(
        "Tablet", "Kaplet", "Kapsul", "Sirup", "Suspensi", "Sirup Kering", "Drops (Tetes Oral)",
        "Ampul (Injeksi)", "Vial (Injeksi)", "Botol Infus / Drip", "Prefilled Syringe",
        "Salep", "Krim", "Gel", "Lotion", "Bedak / Serbuk", "Shampo Medicated",
        "Inhaler MDI", "Inhaler DPI", "Respules / Nebulizer",
        "Tetes Mata", "Salep Mata", "Tetes Telinga", "Nasal Spray (Semprot Hidung)",
        "Suppositoria (Rektal)", "Enema / Gel Rektal", "Ovula (Vaginal)",
        "Patch Transdermal", "Saset / Puyer"
    )
    val routes = listOf(
        "Oral", "IV (Intravena)", "IM (Intramuskular)", "SC (Subkutan)", "Sublingual",
        "Inhalasi", "Topikal", "Tetes Mata", "Tetes Telinga", "Nasal (Hidung)",
        "Rektal", "Vaginal", "Transdermal", "Drip Infus"
    )
    val frequencies = listOf("1x1", "1x2", "1x3", "1x4", "2x1", "3x1", "4x1", "Setiap 6 Jam", "Setiap 8 Jam", "Setiap 12 Jam", "Segera (Cito/Stat)", "Bila Perlu (PRN)", "Dosis Tunggal")
    val timings = listOf("Sesudah Makan", "Sebelum Makan", "Bersama Makanan", "Saat Serangan", "Malam Hari")

    // Gemini AI Suggestion States (Live typing filter)
    var primaryGeminiSuggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var isPrimaryGeminiLoading by remember { mutableStateOf(false) }

    var diffGeminiSuggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var isDiffGeminiLoading by remember { mutableStateOf(false) }

    var drugGeminiSuggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var isDrugGeminiLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Trigger AI suggestion lookup for Primary Diagnosis based on player's typing
    fun fetchPrimarySuggestions(query: String) {
        if (query.isBlank()) return
        coroutineScope.launch {
            isPrimaryGeminiLoading = true
            primaryGeminiSuggestions = GeminiService.getSuggestionsForInput(query.trim(), "DIAGNOSIS")
            isPrimaryGeminiLoading = false
        }
    }

    // Trigger AI suggestion lookup for Differential Diagnosis based on player's typing
    fun fetchDiffSuggestions(query: String) {
        if (query.isBlank()) return
        coroutineScope.launch {
            isDiffGeminiLoading = true
            diffGeminiSuggestions = GeminiService.getSuggestionsForInput(query.trim(), "DIFFERENTIAL")
            isDiffGeminiLoading = false
        }
    }

    // Trigger AI suggestion lookup for Medication based on player's typing
    fun fetchDrugSuggestions(query: String) {
        if (query.isBlank()) return
        coroutineScope.launch {
            isDrugGeminiLoading = true
            drugGeminiSuggestions = GeminiService.getSuggestionsForInput(query.trim(), "DRUG")
            isDrugGeminiLoading = false
        }
    }

    // Live debounced suggestions based on what the user types
    LaunchedEffect(primaryDiagnosis) {
        if (primaryDiagnosis.trim().length >= 2) {
            delay(400)
            isPrimaryGeminiLoading = true
            primaryGeminiSuggestions = GeminiService.getSuggestionsForInput(primaryDiagnosis.trim(), "DIAGNOSIS")
            isPrimaryGeminiLoading = false
        } else {
            primaryGeminiSuggestions = emptyList()
        }
    }

    val currentDiffQuery = remember(differentialDiagnosis) {
        differentialDiagnosis.split(",").lastOrNull()?.trim() ?: differentialDiagnosis.trim()
    }

    LaunchedEffect(currentDiffQuery) {
        if (currentDiffQuery.length >= 2) {
            delay(400)
            isDiffGeminiLoading = true
            diffGeminiSuggestions = GeminiService.getSuggestionsForInput(currentDiffQuery, "DIFFERENTIAL")
            isDiffGeminiLoading = false
        } else {
            diffGeminiSuggestions = emptyList()
        }
    }

    LaunchedEffect(drugSearchQuery) {
        if (drugSearchQuery.trim().length >= 2) {
            delay(400)
            isDrugGeminiLoading = true
            drugGeminiSuggestions = GeminiService.getSuggestionsForInput(drugSearchQuery.trim(), "DRUG")
            isDrugGeminiLoading = false
        } else {
            drugGeminiSuggestions = emptyList()
        }
    }

    if (showReviewModal && activeCase != null) {
        CaseReviewModal(
            activeCase = activeCase,
            userExams = userExams,
            chatHistory = chatHistory,
            onDismiss = { showReviewModal = false },
            onJumpToAnamnesis = {
                showReviewModal = false
                onJumpToAnamnesis()
            }
        )
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
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.EditNote,
                contentDescription = "Diagnosis",
                tint = MedicalTeal40,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Diagnosis & Tatalaksana [STAGE: DIAGNOSIS & TATALAKSANA]",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Action Toolbar: Review Previous Stages & Return to Anamnesis
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { showReviewModal = true },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MedicalTeal40)
            ) {
                Icon(imageVector = Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("📋 Review Briefing & Lab", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onJumpToAnamnesis,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(imageVector = Icons.Default.QuestionAnswer, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("💬 Kembali ke Anamnesis", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        // 1. Diagnosis Kerja Utama
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "1. Diagnosis Kerja Utama (Primary Diagnosis):",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = primaryDiagnosis,
            onValueChange = {
                onUpdateInputs(it, differentialDiagnosis, treatment, education)
            },
            placeholder = { Text("Ketik nama diagnosis (misal: apendisiti, stemi, kolesist...)", fontSize = 13.sp) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Diagnosis", tint = MedicalTeal40)
            },
            trailingIcon = {
                if (primaryDiagnosis.isNotBlank()) {
                    IconButton(onClick = { onUpdateInputs("", differentialDiagnosis, treatment, education) }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MedicalTeal40)
        )

        val localPrimaryFiltered = remember(primaryDiagnosis) {
            if (primaryDiagnosis.isNotBlank()) MedicalCatalog.filterDiagnoses(primaryDiagnosis)
            else emptyList()
        }
        val displayPrimarySuggestions = remember(primaryGeminiSuggestions, localPrimaryFiltered) {
            (primaryGeminiSuggestions + localPrimaryFiltered).distinct().filter { it.isNotBlank() }
        }

        if (primaryDiagnosis.isNotBlank() || displayPrimarySuggestions.isNotEmpty() || isPrimaryGeminiLoading) {
            Spacer(modifier = Modifier.height(6.dp))
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
                            text = if (primaryDiagnosis.isNotBlank())
                                "💡 Saran Diagnosis AI Berdasarkan Ketikan '${primaryDiagnosis.trim()}':"
                            else
                                "💡 Saran Diagnosis AI:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedicalTeal40
                        )
                        if (isPrimaryGeminiLoading) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(modifier = Modifier.size(12.dp), strokeWidth = 1.5.dp, color = MedicalTeal40)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("AI memproses ketikan...", fontSize = 10.sp, color = MedicalTeal40)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    if (displayPrimarySuggestions.isNotEmpty()) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(displayPrimarySuggestions) { diag ->
                                SuggestionChip(
                                    onClick = {
                                        onUpdateInputs(diag, differentialDiagnosis, treatment, education)
                                    },
                                    label = { Text(text = diag, fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = MedicalTeal40.copy(alpha = 0.15f),
                                        labelColor = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    } else if (isPrimaryGeminiLoading) {
                        Text("Menganalisis ketikan dan mencari diagnosis medis...", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        Text("Ketik minimal 2 huruf untuk mendapatkan saran AI (koreksi typo & pelengkap ketikan).", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Diagnosis Banding
        Text(
            text = "2. Diagnosis Banding / Sementara (Differential Diagnoses):",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = differentialDiagnosis,
            onValueChange = { onUpdateInputs(primaryDiagnosis, it, treatment, education) },
            placeholder = { Text("Pisahkan dengan koma (Contoh: NSTEMI, Angina Stabil, GERD)", fontSize = 13.sp) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search DD", tint = MaterialTheme.colorScheme.secondary)
            },
            trailingIcon = {
                if (differentialDiagnosis.isNotBlank()) {
                    IconButton(onClick = { onUpdateInputs(primaryDiagnosis, "", treatment, education) }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.secondary)
        )

        val localDiffFiltered = remember(currentDiffQuery) {
            if (currentDiffQuery.isNotBlank()) MedicalCatalog.filterDiagnoses(currentDiffQuery)
            else emptyList()
        }
        val displayDiffSuggestions = remember(diffGeminiSuggestions, localDiffFiltered) {
            (diffGeminiSuggestions + localDiffFiltered).distinct().filter { it.isNotBlank() }
        }

        if (differentialDiagnosis.isNotBlank() || displayDiffSuggestions.isNotEmpty() || isDiffGeminiLoading) {
            Spacer(modifier = Modifier.height(6.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (currentDiffQuery.isNotBlank())
                                "💡 Saran DD AI Berdasarkan Ketikan '$currentDiffQuery':"
                            else
                                "💡 Saran DD AI:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        if (isDiffGeminiLoading) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(modifier = Modifier.size(12.dp), strokeWidth = 1.5.dp, color = MaterialTheme.colorScheme.secondary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("AI memproses ketikan...", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    if (displayDiffSuggestions.isNotEmpty()) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(displayDiffSuggestions) { diff ->
                                SuggestionChip(
                                    onClick = {
                                        val parts = differentialDiagnosis.split(",").map { it.trim() }.filter { it.isNotBlank() }
                                        val newDiff = if (parts.isEmpty()) {
                                            diff
                                        } else {
                                            if (currentDiffQuery.isNotBlank() && parts.last().equals(currentDiffQuery, ignoreCase = true)) {
                                                (parts.dropLast(1) + diff).joinToString(", ")
                                            } else {
                                                (parts + diff).distinct().joinToString(", ")
                                            }
                                        }
                                        onUpdateInputs(primaryDiagnosis, newDiff, treatment, education)
                                    },
                                    label = { Text(text = "+ $diff", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
                                        labelColor = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    } else if (isDiffGeminiLoading) {
                        Text("Menganalisis ketikan dan mencari diagnosis banding...", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        Text("Ketik beberapa huruf setelah koma untuk mendapatkan saran diagnosis banding AI.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 3. Rencana Tatalaksana & Resep Obat
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Medication, contentDescription = null, tint = MedicalTeal40, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "3. Rencana Tatalaksana & Resep Obat:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // STRUCTURED PRESCRIPTION SELECTION BOX WITH INPUT-BASED AI SUGGESTION
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "💊 Pembuat Resep:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MedicalTeal40
                )
                Spacer(modifier = Modifier.height(6.dp))

                // A. Select Drug Name / Recommendation with Search
                Text(text = "a. Ketik / Cari Nama Obat:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = drugSearchQuery,
                    onValueChange = {
                        drugSearchQuery = it
                        selectedDrug = it
                    },
                    placeholder = { Text("Ketik nama obat (misal: parastamol, amoxilin, isdn, lasik)...", fontSize = 12.sp) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MedicalTeal40) },
                    trailingIcon = {
                        if (drugSearchQuery.isNotBlank()) {
                            IconButton(onClick = {
                                drugSearchQuery = ""
                                selectedDrug = ""
                            }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MedicalTeal40)
                )

                val localFiltered = remember(drugSearchQuery) {
                    if (drugSearchQuery.isNotBlank()) MedicalCatalog.filterDrugs(drugSearchQuery).map { it.name }
                    else emptyList()
                }
                val displayDrugs = remember(drugGeminiSuggestions, localFiltered, drugSearchQuery) {
                    val list = mutableListOf<String>()
                    if (drugSearchQuery.isNotBlank() && !localFiltered.any { it.equals(drugSearchQuery, ignoreCase = true) }) {
                        list.add(drugSearchQuery.trim())
                    }
                    list.addAll(drugGeminiSuggestions)
                    list.addAll(localFiltered)
                    list.distinct().filter { it.isNotBlank() }
                }

                if (drugSearchQuery.isNotBlank() || displayDrugs.isNotEmpty() || isDrugGeminiLoading) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f))
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (drugSearchQuery.isNotBlank())
                                        "💊 Saran Obat AI Berdasarkan Ketikan '${drugSearchQuery.trim()}':"
                                    else
                                        "💊 Saran Obat AI:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MedicalTeal40
                                )
                                if (isDrugGeminiLoading) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        CircularProgressIndicator(modifier = Modifier.size(12.dp), strokeWidth = 1.5.dp, color = MedicalTeal40)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("AI memproses ketikan...", fontSize = 10.sp, color = MedicalTeal40)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))

                            if (displayDrugs.isNotEmpty()) {
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    items(displayDrugs) { drugName ->
                                        FilterChip(
                                            selected = selectedDrug.equals(drugName, ignoreCase = true),
                                            onClick = {
                                                selectedDrug = drugName
                                                drugSearchQuery = drugName
                                                val matched = MedicalCatalog.allDrugs.firstOrNull { it.name.equals(drugName, ignoreCase = true) }
                                                if (matched != null) {
                                                    selectedForm = matched.defaultForm
                                                    selectedRoute = matched.defaultRoute
                                                    selectedFrequency = matched.defaultFreq
                                                }
                                            },
                                            label = { Text(drugName, fontSize = 11.sp) },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = MedicalTeal40,
                                                selectedLabelColor = Color.White
                                            )
                                        )
                                    }
                                }
                            } else if (isDrugGeminiLoading) {
                                Text("Menganalisis ketikan dan mencari obat medis...", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            } else {
                                Text("Ketik nama obat atau istilah farmasi untuk mencari saran obat AI.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // B. Select Drug Form (Sediaan)
                Text(text = "b. Pilih Sediaan Obat:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(drugForms) { form ->
                        FilterChip(
                            selected = selectedForm == form,
                            onClick = { selectedForm = form },
                            label = { Text(form, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // C. Select Route (Cara Pemakaian)
                Text(text = "c. Pilih Cara Pemakaian / Rute:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(routes) { route ->
                        FilterChip(
                            selected = selectedRoute == route,
                            onClick = { selectedRoute = route },
                            label = { Text(route, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // D. Select Dosage Frequency
                Text(text = "d. Pilih Frekuensi Dosis:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(frequencies) { freq ->
                        FilterChip(
                            selected = selectedFrequency == freq,
                            onClick = { selectedFrequency = freq },
                            label = { Text(freq, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // E. Select Timing / Aturan Minum
                Text(text = "e. Pilih Waktu Konsumsi:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(timings) { time ->
                        FilterChip(
                            selected = selectedTiming == time,
                            onClick = { selectedTiming = time },
                            label = { Text(time, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val currentDrugName = when {
                    drugSearchQuery.isNotBlank() -> drugSearchQuery.trim()
                    selectedDrug.isNotBlank() -> selectedDrug.trim()
                    else -> "Obat Medis"
                }

                Button(
                    onClick = {
                        val formattedPrescription = "• $currentDrugName ($selectedForm, $selectedRoute, $selectedFrequency $selectedTiming)"
                        val updatedTreatment = if (treatment.isBlank()) formattedPrescription else "$treatment\n$formattedPrescription"
                        onUpdateInputs(primaryDiagnosis, differentialDiagnosis, updatedTreatment, education)
                        drugSearchQuery = ""
                        selectedDrug = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "➕ Tambahkan ke Resep: $currentDrugName ($selectedForm, $selectedRoute, $selectedFrequency $selectedTiming)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Treatment Text Area (Shows combined text)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Draf Resep & Tatalaksana Lengkap:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            if (treatment.isNotBlank()) {
                Text(
                    text = "Hapus Semua",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.clickable {
                        onUpdateInputs(primaryDiagnosis, differentialDiagnosis, "", education)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = treatment,
            onValueChange = { onUpdateInputs(primaryDiagnosis, differentialDiagnosis, it, education) },
            placeholder = { Text("Resep obat otomatis atau saran AI akan muncul di sini (dapat juga disunting manual)...", fontSize = 12.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MedicalTeal40)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 4. Edukasi Pasien
        Text(
            text = "4. Edukasi Pasien & Keluarga:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Quick Edu Chips
        val eduChips = listOf(
            "Tirah baring total di rumah sakit",
            "Patuhi konsumsi obat secara teratur",
            "Batasi asupan garam dan lemak jenuh",
            "Waspadai tanda bahaya (nyeri bertambah, sesak, perdarahan)",
            "Segera kontrol ke poliklinik 3 hari lagi"
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(eduChips) { chip ->
                SuggestionChip(
                    onClick = {
                        val newEdu = if (education.isBlank()) "• $chip" else "$education\n• $chip"
                        onUpdateInputs(primaryDiagnosis, differentialDiagnosis, treatment, newEdu)
                    },
                    label = { Text("+ $chip", fontSize = 10.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = education,
            onValueChange = { onUpdateInputs(primaryDiagnosis, differentialDiagnosis, treatment, it) },
            placeholder = { Text("Instruksi aktivitas, pola makan, kepatuhan obat, dan tanda bahaya...", fontSize = 13.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MedicalTeal40)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Submit Button to Stage 6
        Button(
            onClick = onSubmitAndEvaluate,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Submit", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kirim & Lihat Evaluasi Konsulen (Stage 6)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
