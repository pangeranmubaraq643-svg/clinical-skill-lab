package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.PpkGuideRepository
import com.example.data.repository.PpkGuidelineItem
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTeal40

@Composable
fun PpkGuideScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }

    val categories = listOf(
        "Semua",
        "Kardiologi",
        "Neurologi",
        "Pulmonologi",
        "Gastroenterohepatologi",
        "Endokrinologi & Metabolik",
        "Nefro-Urologi",
        "Infeksi Tropis",
        "Pediatri",
        "Obgyn",
        "Dermatovenerologi",
        "THT-KL",
        "Oftalmologi",
        "Kegawatdaruratan & Trauma"
    )

    val allGuidelines = remember { PpkGuideRepository.getAllExtendedGuidelines() }

    val filteredGuidelines = allGuidelines.filter { item ->
        val matchesCategory = if (selectedCategory == "Semua") true else {
            item.organSystem.contains(selectedCategory, ignoreCase = true) ||
                    selectedCategory.contains(item.organSystem, ignoreCase = true)
        }

        val matchesSearch = searchQuery.isBlank() ||
                item.title.contains(searchQuery, ignoreCase = true) ||
                item.organSystem.contains(searchQuery, ignoreCase = true) ||
                item.symptomsAndAnamnesis.contains(searchQuery, ignoreCase = true) ||
                item.treatmentAndMedication.contains(searchQuery, ignoreCase = true) ||
                item.kemenkesRef.contains(searchQuery, ignoreCase = true)

        matchesCategory && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MedicalTeal40.copy(alpha = 0.15f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = "PPK",
                        tint = MedicalTeal40,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Panduan PPK & PNPK Kemenkes RI",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Pedoman Praktik Klinis & Standar Terapi Nasional (${allGuidelines.size}+ Topik)",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Cari penyakit, obat, atau pedoman Kemenkes...", fontSize = 12.sp) },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MedicalTeal40) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MedicalTeal40)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Category Filter Chips (Horizontal Scroll)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                val isSelected = category == selectedCategory
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) MedicalTeal40 else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.clickable { selectedCategory = category }
                ) {
                    Text(
                        text = category,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Info Banner
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MedicalTeal40.copy(alpha = 0.08f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Verified",
                    tint = MedicalTeal40,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Menampilkan ${filteredGuidelines.size} pedoman klinis terverifikasi PPK & PNPK Kemenkes RI sebagai pedoman standar diagnostik & tatalaksana.",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of PPK Cards
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            filteredGuidelines.forEach { item ->
                PpkCardItem(
                    item = item
                )
            }
        }
    }
}

@Composable
fun PpkCardItem(
    item: PpkGuidelineItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = item.computedLevel.color
                        ) {
                            Text(
                                text = "${item.computedLevel.badgeLabel} (${item.computedLevel.durationBadge})",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MedicalTeal40.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = item.organSystem,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedicalTeal40,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(10.dp))

            // 1. Anamnesis & Gejala
            Text(
                text = "🩺 Anamnesis & Gejala Khas:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MedicalTeal40
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.symptomsAndAnamnesis,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 2. Pemfis & Lab Penunjang
            Text(
                text = "🔬 Pemeriksaan Fisik & Penunjang Gold Standard:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MedicalTeal40
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.physicalAndLabExams,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Tatalaksana & Obat
            Text(
                text = "💊 Tatalaksana & Terapi Farmakologi (PPK Kemenkes):",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MedicalTeal40
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.treatmentAndMedication,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 4. Landasan PNPK / Ref Kemenkes
            Text(
                text = "📜 Landasan Regulasi Kemenkes RI:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.kemenkesRef,
                fontSize = 10.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 15.sp
            )

            if (item.redFlagsAndReferral.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EmergencyRed.copy(alpha = 0.06f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, EmergencyRed.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Red Flags",
                            tint = EmergencyRed,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item.redFlagsAndReferral,
                            fontSize = 10.sp,
                            color = EmergencyRed,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}
