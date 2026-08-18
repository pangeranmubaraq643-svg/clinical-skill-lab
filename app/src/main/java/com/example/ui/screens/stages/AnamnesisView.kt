package com.example.ui.screens.stages

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChatMessage
import com.example.data.model.ChatSender
import com.example.data.model.ClinicalCase
import com.example.data.repository.PatientAvatarProvider
import com.example.ui.theme.MedicalTeal40
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.SuccessGreen

@Composable
fun AnamnesisView(
    activeCase: ClinicalCase,
    chatHistory: List<ChatMessage>,
    isPatientTyping: Boolean,
    onSendMessage: (String) -> Unit,
    onNextStage: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("RPS & Keluhan") }
    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Patient Title
    val patientTitle = "Saya"
    val displayKU = remember(activeCase.chiefComplaint) {
        val dual = com.example.data.remote.GeminiService.extractDualChiefComplaints(activeCase.chiefComplaint, activeCase)
        dual.fullSummary
    }
    val patientAvatarResId = remember(activeCase.id) {
        PatientAvatarProvider.getAvatarResId(activeCase)
    }

    // Scroll to bottom when new chat arrives
    LaunchedEffect(chatHistory.size) {
        if (chatHistory.isNotEmpty()) {
            listState.animateScrollToItem(chatHistory.size - 1)
        }
    }

    val categories = listOf("RPS & Gejala Utama", "Penyerta & Vaskular", "RPD, RPK & Alergi", "Empati Dokter")

    val questionMap = remember(activeCase.id) {
        mapOf(
            "RPS & Gejala Utama" to listOf(
                "Sejak kapan Anda merasakan gejala ini?",
                "Bisa dijelaskan seperti apa sifat nyerinya?",
                "Apakah nyerinya tembus atau menjalar ke tempat lain?",
                "Faktor apa yang membuat nyerinya makin berat atau berkurang?",
                "Apakah nyerinya datang tiba-tiba atau bertahap?"
            ),
            "Penyerta & Vaskular" to listOf(
                "Apakah ada terasa mual, muntah, atau pusing melayang?",
                "Apakah keluar keringat dingin atau badan terasa lemas?",
                "Apakah ada merasa sesak napas atau dada berdebar?",
                "Apakah ada demam, menggigil, atau nyeri di tempat lain?"
            ),
            "RPD, RPK & Alergi" to listOf(
                "Apakah Anda punya riwayat darah tinggi, kencing manis, atau jantung?",
                "Apakah pernah mengalami sakit seperti ini sebelumnya?",
                "Apakah ada alergi obat-obatan?",
                "Apakah sudah sempat minum obat apa di rumah?",
                "Apakah ada riwayat penyakit serupa di keluarga?"
            ),
            "Empati Dokter" to listOf(
                "Tenang ya, kami langsung siapkan pertolongan awal...",
                "Tidak usah khawatir, tim medis kami siap menangani kondisi Anda.",
                "Apakah ada keluarga yang mendampingi di luar ruangan?",
                "Tarik napas perlahan ya, kami bantu perlahan."
            )
        )
    }

    if (isLandscape) {
        // LANDSCAPE LAYOUT: Two-Column Split Screen
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // LEFT COLUMN: Patient Profile Card & Quick Question Triggers
            Column(
                modifier = Modifier
                    .weight(0.44f)
                    .fillMaxHeight()
            ) {
                // Patient Header Card (compact)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            Image(
                                painter = painterResource(id = patientAvatarResId),
                                contentDescription = "Avatar Pasien",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(Color(0xFF22C55E), CircleShape)
                                    .align(Alignment.BottomEnd)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Pasien (${activeCase.patientAge} th, ${activeCase.patientGender})",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Button(
                                    onClick = onNextStage,
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.height(26.dp)
                                ) {
                                    Text(text = "Ke Stabilisasi", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next", modifier = Modifier.size(10.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "KU: $displayKU",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            ) {
                                Text(
                                    text = "TD ${activeCase.td} | Nadi ${activeCase.nadi}x | SpO2 ${activeCase.spO2}% | S ${activeCase.suhu}°C",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MedicalTeal40,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Question Category Filter Tabs
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(vertical = 1.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = { Text(category, fontSize = 10.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Pertanyaan Anamnesis Cepat:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))

                val currentQuestions = questionMap[selectedCategory] ?: emptyList()
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(currentQuestions) { q ->
                        SuggestionChip(
                            onClick = { onSendMessage(q) },
                            label = { Text(text = q, fontSize = 10.sp, lineHeight = 13.sp) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // RIGHT COLUMN: Chat Thread & Input
            Column(
                modifier = Modifier
                    .weight(0.56f)
                    .fillMaxHeight()
            ) {
                // Chat Conversation Thread
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(chatHistory, key = { it.id }) { msg ->
                        ChatBubble(message = msg, avatarResId = patientAvatarResId)
                    }

                    if (isPatientTyping) {
                        item {
                            Row(
                                modifier = Modifier.padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(14.dp),
                                    color = MedicalTeal40,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "🌐 Live Gemini AI Pasien $patientTitle merespons (Mode Online)...",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MedicalTeal40
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Doctor Custom Input Field
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = { Text("Ketik pertanyaan dokter...", fontSize = 11.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp),
                        shape = RoundedCornerShape(20.dp),
                        maxLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MedicalTeal40
                        )
                    )

                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                onSendMessage(inputText)
                                inputText = ""
                            }
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(MedicalTeal40, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    } else {
        // PORTRAIT LAYOUT: Vertical Stack
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Patient Profile & Vitals Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            Image(
                                painter = painterResource(id = patientAvatarResId),
                                contentDescription = "Avatar Pasien",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(Color(0xFF22C55E), CircleShape)
                                    .align(Alignment.BottomEnd)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Pasien: $patientTitle (${activeCase.patientAge} th, ${activeCase.patientGender})",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MedicalTeal40.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "🌐 Live Gemini AI (Online)",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MedicalTeal40,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Text(
                                text = "Pekerjaan: ${activeCase.patientOccupation} | KU: $displayKU",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                        }

                        Button(
                            onClick = onNextStage,
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Ke Stabilisasi", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next", modifier = Modifier.size(14.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Patient Appearance & Vital Quick Bar
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🩺 Keadaan: ${activeCase.generalAppearance}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "TD ${activeCase.td} | Nadi ${activeCase.nadi}x/m | SpO2 ${activeCase.spO2}%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedicalTeal40
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Chat Conversation Thread
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(chatHistory, key = { it.id }) { msg ->
                    ChatBubble(message = msg, avatarResId = patientAvatarResId)
                }

                if (isPatientTyping) {
                    item {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MedicalTeal40,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "🌐 Live Gemini AI Pasien $patientTitle sedang merespons (Mode Online)...",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MedicalTeal40
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Question Category Filter Tabs
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(vertical = 2.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category, fontSize = 11.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Quick Anamnesis Question Suggestion Chips
            val currentQuestions = questionMap[selectedCategory] ?: emptyList()
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(vertical = 2.dp)
            ) {
                items(currentQuestions) { q ->
                    SuggestionChip(
                        onClick = {
                            onSendMessage(q)
                        },
                        label = { Text(text = q, fontSize = 11.sp) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Doctor Custom Input Field
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Ketik pertanyaan dokter untuk pasien...", fontSize = 12.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MedicalTeal40
                    )
                )

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(46.dp)
                        .background(MedicalTeal40, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage, avatarResId: Int) {
    val isDoctor = message.sender == ChatSender.DOKTER
    val isSystem = message.sender == ChatSender.SYSTEM

    val isWarningMessage = message.text.startsWith("⚠️")

    if (isSystem) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
                Text(
                    text = message.text,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    } else if (isWarningMessage) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = EmergencyRed.copy(alpha = 0.1f)),
            border = androidx.compose.foundation.BorderStroke(1.dp, EmergencyRed)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "🌐 Status Gemini AI Online",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmergencyRed
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = message.text,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 16.sp
                )
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
            horizontalArrangement = if (isDoctor) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            if (!isDoctor) {
                Image(
                    painter = painterResource(id = avatarResId),
                    contentDescription = "Avatar Pasien",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Surface(
                shape = RoundedCornerShape(
                    topStart = if (isDoctor) 16.dp else 4.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = if (isDoctor) 4.dp else 16.dp
                ),
                color = if (isDoctor) MedicalTeal40 else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.widthIn(max = 300.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Text(
                        text = if (isDoctor) "👨‍⚕️ Dokter (Anda)" else "😷 Pasien",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDoctor) Color.White.copy(alpha = 0.85f) else MedicalTeal40
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = message.text,
                        fontSize = 13.sp,
                        color = if (isDoctor) Color.White else MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
