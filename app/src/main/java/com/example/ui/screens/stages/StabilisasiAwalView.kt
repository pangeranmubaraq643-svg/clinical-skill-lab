package com.example.ui.screens.stages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CitoActionFeedback
import com.example.data.model.CitoImpactType
import com.example.data.model.ClinicalCase
import com.example.data.remote.GeminiService
import com.example.data.repository.VitalSignsManager
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTeal40
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import kotlinx.coroutines.delay

data class AbcdeOption(
    val id: String,
    val category: String, // AIRWAY, BREATHING, CIRCULATION, DISABILITY, EXPOSURE
    val title: String,
    val description: String,
    val icon: ImageVector,
    val isCito: Boolean = true
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StabilisasiAwalView(
    activeCase: ClinicalCase,
    treatmentInput: String,
    isEmergencyMode: Boolean,
    lastCitoFeedback: CitoActionFeedback? = null,
    citoActionLogs: List<CitoActionFeedback> = emptyList(),
    isEvaluatingCitoAction: Boolean = false,
    onDismissFeedback: () -> Unit = {},
    onApplyAction: (String) -> Unit,
    onNextStage: () -> Unit,
    onPreviousStage: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("SEMUA") }
    var customCitoText by remember { mutableStateOf("") }
    var showCitoQuickSuggestions by remember { mutableStateOf(false) }

    // Live AI Suggestions for Cito Actions (with Typo & Fuzzy Matching)
    var citoGeminiSuggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var isCitoGeminiLoading by remember { mutableStateOf(false) }

    LaunchedEffect(customCitoText, showCitoQuickSuggestions) {
        if (customCitoText.trim().length >= 2 || showCitoQuickSuggestions) {
            val query = if (customCitoText.isNotBlank()) customCitoText.trim() else "resusitasi"
            delay(350)
            isCitoGeminiLoading = true
            citoGeminiSuggestions = GeminiService.getSuggestionsForInput(query, "CITO")
            isCitoGeminiLoading = false
        } else {
            citoGeminiSuggestions = emptyList()
        }
    }

    val abcdeCategories = listOf("SEMUA", "AIRWAY", "BREATHING", "CIRCULATION", "DISABILITY", "EXPOSURE")

    val stabilizationOptions = remember {
        listOf(
            // AIRWAY
            AbcdeOption(
                id = "airway_headtilt",
                category = "AIRWAY",
                title = "Head-Tilt Chin-Lift / Jaw-Thrust",
                description = "Bebaskan jalan napas dari pangkal lidah yang jatuh",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "airway_suction",
                category = "AIRWAY",
                title = "Suction Lendir & Darah Cito",
                description = "Bersihkan sekret atau sumbatan cairan di jalan napas",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "airway_opa",
                category = "AIRWAY",
                title = "Pasang OPA (Guedel) / NPA",
                description = "Ganjal jalan napas pada pasien penurunan kesadaran",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "airway_ett",
                category = "AIRWAY",
                title = "Intubasi Endotrakeal (ETT) Cito",
                description = "Amankan jalan napas definitif pada gagal napas/GCS < 8",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "airway_lma",
                category = "AIRWAY",
                title = "Pasang Laryngeal Mask Airway (LMA) Cito",
                description = "Alat jalan napas supraglotik jika intubasi sulit / gagal",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "airway_crico",
                category = "AIRWAY",
                title = "Needle Cricothyroidotomy / Krikotirotomi Cito",
                description = "Akses jalan napas darurat bedah pada sumbatan total jalan napas atas",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "airway_magill",
                category = "AIRWAY",
                title = "Magill Forceps Foreign Body Extraction",
                description = "Evakuasi benda asing penyumbat laring/faring dengan cunam Magill",
                icon = Icons.Default.Air
            ),

            // BREATHING
            AbcdeOption(
                id = "breath_nrm",
                category = "BREATHING",
                title = "Oksigen NRM (Non-Rebreathing) 10-15 Lpm",
                description = "Oksigenasi konsentrasi tinggi untuk hipoksia berat/sesak hebat",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "breath_nasal",
                category = "BREATHING",
                title = "Oksigen Nasal Kanul 3-4 Lpm",
                description = "Oksigenasi suportif untuk sesak ringan-sedang",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "breath_nebu",
                category = "BREATHING",
                title = "Nebulizer Ventolin (Salbutamol) + Pulmicort",
                description = "Inhalasi bronkodilator untuk bronkospasme/asma/PPOK",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "breath_bvm",
                category = "BREATHING",
                title = "Ventilasi Bag-Valve-Mask (BVM) / Ambu Bag",
                description = "Bantuan napas tekanan positif pada apneu/hambatan napas",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "breath_needle",
                category = "BREATHING",
                title = "Needle Thoracocentesis Decompression Cito",
                description = "Dekompresi jarum ICS II Midklavikula pada Tension Pneumothorax",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "breath_wsd",
                category = "BREATHING",
                title = "Pasang Chest Tube / WSD (Water Sealed Drainage) Cito",
                description = "Drainase toraks untuk hematotoraks / pneumotoraks masif",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "breath_hfnc",
                category = "BREATHING",
                title = "High Flow Nasal Cannula (HFNC) 40-60 Lpm",
                description = "Oksigenasi arus tinggi & PEEP suportif pada gagal napas hipoksemik",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "breath_valve",
                category = "BREATHING",
                title = "Occlusive Dressing 3-Sided Valve Cito",
                description = "Kasa tiga sisi untuk tutup luka dada terbuka (Open Pneumothorax)",
                icon = Icons.Default.Air
            ),
            AbcdeOption(
                id = "breath_steroid",
                category = "BREATHING",
                title = "Injeksi Deksametason 10mg / Metilprednisolon IV Cito",
                description = "Antiinflamasi steroid cepat untuk serangan asma berat/edema laring",
                icon = Icons.Default.Medication
            ),

            // CIRCULATION
            AbcdeOption(
                id = "circ_tourniquet",
                category = "CIRCULATION",
                title = "Pasang Tourniquet & Bebat Tekan Steril Cito",
                description = "Hentikan sumber perdarahan arterial masif eksternal pada trauma/fraktur terbuka",
                icon = Icons.Default.FlashOn
            ),
            AbcdeOption(
                id = "circ_txa",
                category = "CIRCULATION",
                title = "Injeksi Asam Traneksamat (TXA) 1 Gram IV Cito",
                description = "Antifibrinolitik cito untuk tekan angka kematian akibat perdarahan trauma < 3 jam",
                icon = Icons.Default.Medication
            ),
            AbcdeOption(
                id = "circ_transfusion",
                category = "CIRCULATION",
                title = "Transfusi Darah Cito (PRC / Whole Blood / MTP)",
                description = "Resusitasi transfusi masif cito pada syok hipovolemik grade III-IV & anemia berat",
                icon = Icons.Default.FlashOn
            ),
            AbcdeOption(
                id = "circ_ivline",
                category = "CIRCULATION",
                title = "Pasang 2 Line IV Canula Abocath 14G/16G",
                description = "Akses vena perifer jarum besar untuk resusitasi cairan cepat",
                icon = Icons.Default.FlashOn
            ),
            AbcdeOption(
                id = "circ_io",
                category = "CIRCULATION",
                title = "Pasang Akses Intraosseous (IO) Cito",
                description = "Akses tulang darurat jika vena perifer tidak dapat diakses < 90 detik",
                icon = Icons.Default.FlashOn
            ),
            AbcdeOption(
                id = "circ_fluid",
                category = "CIRCULATION",
                title = "Resusitasi Cairan Kristaloid Hangat (RL / NaCl 0.9%) 1000 ml Cito",
                description = "Grojok cairan infus hangat cepat untuk atasi hipovolemia/syok",
                icon = Icons.Default.FlashOn
            ),
            AbcdeOption(
                id = "circ_pos",
                category = "CIRCULATION",
                title = "Posisi Syok Trendelenburg / Elevasi Tungkai",
                description = "Tingkatkan aliran darah balik vena ke organ vital",
                icon = Icons.Default.Favorite
            ),
            AbcdeOption(
                id = "circ_pressor",
                category = "CIRCULATION",
                title = "Inotropik / Vasopresor (Norepinefrin / Dopamin IV)",
                description = "Dukungan inotropik/vasopresor untuk hipotensi persisten/syok kardiogenik",
                icon = Icons.Default.Medication
            ),
            AbcdeOption(
                id = "circ_epi",
                category = "CIRCULATION",
                title = "Injeksi Epinefrin 1mg Cito (Anafilaksis / CPR)",
                description = "Injeksi vasopresor cito pada syok anafilaktik atau cardiac arrest",
                icon = Icons.Default.FlashOn
            ),
            AbcdeOption(
                id = "circ_cpr",
                category = "CIRCULATION",
                title = "Resusitasi Jantung Paru (RJP / CPR) Kualitas Tinggi",
                description = "Kompresi dada 100-120x/menit kedalaman 5-6 cm pada henti jantung",
                icon = Icons.Default.MonitorHeart
            ),
            AbcdeOption(
                id = "circ_defib",
                category = "CIRCULATION",
                title = "Defibrilasi Cito 200 Joule Bifasik",
                description = "Kejut listrik cito pada aritmia lethal Ventricular Fibrillation (VF) / pulseless VT",
                icon = Icons.Default.FlashOn
            ),
            AbcdeOption(
                id = "circ_cardioversion",
                category = "CIRCULATION",
                title = "Kardioversi Tersinkronisasi Cito 50-100 Joule",
                description = "Kardioversi cito pada SVT / VT dengan nadi yang tidak stabil",
                icon = Icons.Default.FlashOn
            ),
            AbcdeOption(
                id = "circ_adenosine",
                category = "CIRCULATION",
                title = "Injeksi Adenosin 6mg IV Bolus Cepat + Flush",
                description = "Terminasi takikardia supraventrikular (SVT) paroksismal",
                icon = Icons.Default.Medication
            ),
            AbcdeOption(
                id = "circ_atropine",
                category = "CIRCULATION",
                title = "Injeksi Sulfas Atropin 1mg IV Cito",
                description = "Antikolinergik penanganan bradikardia berat bergejala / AV Block",
                icon = Icons.Default.Medication
            ),
            AbcdeOption(
                id = "circ_amiodarone",
                category = "CIRCULATION",
                title = "Injeksi Amiodaron 300mg IV Bolus Cito",
                description = "Antiartimia untuk henti jantung VF/VT refrakter pasca defibrilasi",
                icon = Icons.Default.Medication
            ),
            AbcdeOption(
                id = "circ_pericardio",
                category = "CIRCULATION",
                title = "Perikardiosentesis Cito (Aspirasi Jarum)",
                description = "Evakuasi cairan perikard darurat pada Tamponade Jantung (Trias Beck)",
                icon = Icons.Default.FlashOn
            ),

            // DISABILITY
            AbcdeOption(
                id = "disabil_collar",
                category = "DISABILITY",
                title = "Pasang Cervical Collar (Neck Collar)",
                description = "Imobilisasi servikal cito pada kecelakaan lalu lintas & trauma kepala/leher",
                icon = Icons.Default.Psychology
            ),
            AbcdeOption(
                id = "disabil_gds",
                category = "DISABILITY",
                title = "Cek Gula Darah Sewaktu (GDS) Cito + Dextrose 40%",
                description = "Singkirkan dan tatalaksana hipoglikemia berat secara cepat",
                icon = Icons.Default.Psychology
            ),
            AbcdeOption(
                id = "disabil_recovery",
                category = "DISABILITY",
                title = "Posisi Miring Mantap (Recovery Position)",
                description = "Cegah aspirasi lambung pada pasien penurunan kesadaran/post-kejang",
                icon = Icons.Default.Psychology
            ),
            AbcdeOption(
                id = "disabil_diazepam",
                category = "DISABILITY",
                title = "Injeksi Diazepam 10mg IV / Suppositoria Cito",
                description = "Hentikan kejang berulang / status epileptikus aktif",
                icon = Icons.Default.Medication
            ),
            AbcdeOption(
                id = "disabil_mannitol",
                category = "DISABILITY",
                title = "Injeksi Mannitol 20% 1g/kgBB IV Cito",
                description = "Diuretik osmotik penurun tekanan intrakranial (TIK) / edema otak",
                icon = Icons.Default.Medication
            ),
            AbcdeOption(
                id = "disabil_naloxone",
                category = "DISABILITY",
                title = "Injeksi Nalokson (Naloxone) 0.4mg - 2mg IV Cito",
                description = "Antidotum antagonis spesifik overdosis opioid / depresi napas",
                icon = Icons.Default.Medication
            ),
            AbcdeOption(
                id = "disabil_mgso4",
                category = "DISABILITY",
                title = "Injeksi Magnesium Sulfat (MgSO4) 4 Gram IV Pelan",
                description = "Pencegahan & penanganan kejang eklampsia / Torsades de Pointes",
                icon = Icons.Default.Medication
            ),

            // EXPOSURE
            AbcdeOption(
                id = "expo_splint",
                category = "EXPOSURE",
                title = "Pasang Spalk Imobilisasi / Pelvic Binder Cito",
                description = "Fiksasi fraktur ekstremitas/pelvis, kurangi nyeri & hematoma perdarahan",
                icon = Icons.Default.Healing
            ),
            AbcdeOption(
                id = "expo_blanket",
                category = "EXPOSURE",
                title = "Selimut Penghangat / Selimut Thermal",
                description = "Atasi hipotermia dan cegah Trias Kematian Trauma (Hypothermia, Coagulopathy, Acidosis)",
                icon = Icons.Default.Healing
            ),
            AbcdeOption(
                id = "expo_paracetamol",
                category = "EXPOSURE",
                title = "Antipiretik Parasetamol 1000mg IV Cito",
                description = "Turunkan suhu tubuh cito pada febris tinggi/kejang demam",
                icon = Icons.Default.Thermostat
            ),
            AbcdeOption(
                id = "expo_catheter",
                category = "EXPOSURE",
                title = "Pemasangan Kateter Urin (Folley Catheter) + Urine Bag",
                description = "Monitoring diuresis / produksi urin jam-jaman pada resusitasi syok",
                icon = Icons.Default.Healing
            ),
            AbcdeOption(
                id = "expo_ngt",
                category = "EXPOSURE",
                title = "Pemasangan NGT (Nasogastric Tube) Dekompresi",
                description = "Dekompresi lambung pada distensi, ilues, atau perdarahan lambung",
                icon = Icons.Default.Healing
            ),
            AbcdeOption(
                id = "expo_decontam",
                category = "EXPOSURE",
                title = "Bilas Lambung (Gastric Lavage) + Arang Aktif",
                description = "Dekontaminasi intoksikasi / keracunan zat oral akut < 1-2 jam",
                icon = Icons.Default.Healing
            ),
            AbcdeOption(
                id = "expo_atropinization",
                category = "EXPOSURE",
                title = "Injeksi Sulfas Atropin Dosis Tinggi (2-5mg IV Ulang)",
                description = "Atropinisasi cito pada keracunan pestisida organofosfat / karbamat",
                icon = Icons.Default.Medication
            ),
            AbcdeOption(
                id = "expo_uterotonic",
                category = "EXPOSURE",
                title = "Injeksi Oksitosin 10 IU IM + Ergometrin Cito",
                description = "Uterotonika cito untuk kram uteri & atasi perdarahan pasca persalinan (PPH)",
                icon = Icons.Default.Medication
            )
        )
    }

    val filteredOptions = remember(selectedCategory) {
        if (selectedCategory == "SEMUA") stabilizationOptions
        else stabilizationOptions.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // AI Evaluation Progress Indicator
        if (isEvaluatingCitoAction) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MedicalTeal40.copy(alpha = 0.12f)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, MedicalTeal40)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MedicalTeal40,
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "AI Computing",
                                    tint = MedicalTeal40,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "AI Menghitung Efek Fisiologis & TTV...",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MedicalTeal40
                                )
                            }
                            Text(
                                text = "Menganalisis patofisiologi, kalkulasi hemodinamik, dan respon pasien secara manual...",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Realtime Cito Action Impact Alert Banner
        lastCitoFeedback?.let { feedback ->
            item {
                val bannerBg = when (feedback.impactType) {
                    CitoImpactType.STABILIZED -> SuccessGreen.copy(alpha = 0.15f)
                    CitoImpactType.UNINDICATED -> WarningAmber.copy(alpha = 0.15f)
                    CitoImpactType.HARMFUL -> EmergencyRed.copy(alpha = 0.15f)
                    CitoImpactType.FATAL_COLLAPSE -> EmergencyRed.copy(alpha = 0.25f)
                }
                val borderColor = when (feedback.impactType) {
                    CitoImpactType.STABILIZED -> SuccessGreen
                    CitoImpactType.UNINDICATED -> WarningAmber
                    CitoImpactType.HARMFUL -> EmergencyRed
                    CitoImpactType.FATAL_COLLAPSE -> EmergencyRed
                }
                val iconVector = when (feedback.impactType) {
                    CitoImpactType.STABILIZED -> Icons.Default.CheckCircle
                    CitoImpactType.UNINDICATED -> Icons.Default.Warning
                    CitoImpactType.HARMFUL -> Icons.Default.Warning
                    CitoImpactType.FATAL_COLLAPSE -> Icons.Default.FlashOn
                }
                val iconTint = when (feedback.impactType) {
                    CitoImpactType.STABILIZED -> SuccessGreen
                    CitoImpactType.UNINDICATED -> WarningAmber
                    else -> EmergencyRed
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = bannerBg),
                    border = androidx.compose.foundation.BorderStroke(2.dp, borderColor)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Icon(
                                    imageVector = iconVector,
                                    contentDescription = "Impact",
                                    tint = iconTint,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = when (feedback.impactType) {
                                            CitoImpactType.STABILIZED -> "⚡ HASIL TATALAKSANA: TERSTABILISASI (+${feedback.timeDeltaSeconds} Detik)"
                                            CitoImpactType.UNINDICATED -> "ℹ️ HASIL TATALAKSANA: TIDAK DIINDIKASIKAN (${feedback.timeDeltaSeconds} Detik)"
                                            CitoImpactType.HARMFUL -> "⚠️ HASIL TATALAKSANA: KOMPLIKASI (${feedback.timeDeltaSeconds} Detik)"
                                            CitoImpactType.FATAL_COLLAPSE -> "🚨 KESALAHAN FATAL: HENTI JANTUNG / PASIEN MENINGGAL"
                                        },
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 12.sp,
                                        color = iconTint
                                    )
                                    if (feedback.isAiEvaluated) {
                                        Text(
                                            text = "🧠 Dihitung Real-Time oleh Gemini AI",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MedicalTeal40
                                        )
                                    }
                                }
                            }

                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { onDismissFeedback() }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("✕", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = feedback.message,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 16.sp
                        )

                        if (feedback.updatedVitalsNote.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                            ) {
                                Text(
                                    text = "📊 Respon Fisiologis TTV: ${feedback.updatedVitalsNote}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = iconTint,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Banner Header Stage
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isEmergencyMode) EmergencyRed.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (isEmergencyMode) EmergencyRed.copy(alpha = 0.5f) else MedicalTeal40.copy(alpha = 0.4f)
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isEmergencyMode) EmergencyRed else MedicalTeal40,
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.LocalHospital,
                                    contentDescription = "Stabilisasi Icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "STAGE STABILISASI & RESUSITASI CITO",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    color = if (isEmergencyMode) EmergencyRed else MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = EmergencyRed
                                ) {
                                    Text(
                                        text = "ABCDE",
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Text(
                                text = "Lakukan penanganan awal untuk stabilisasi keadaan umum & vital sign pasien (${activeCase.patientAge} th, KU: ${activeCase.chiefComplaint})",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Info",
                                tint = WarningAmber,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Pilihan tatalaksana yang tepat memberikan tambahan waktu resusitasi (+sec). Efek fisiologis dihitung manual oleh AI berdasarkan patofisiologi penyakit.",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // Custom Cito Action Input Card with Smart AI Autocomplete & Typo Detection (Mirrors Diagnosis Stage)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MedicalTeal40.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI Action",
                                tint = MedicalTeal40,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Tatalaksana / Obat Cito Bebas:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Ketik tindakan/obat emergensi apa saja. AI akan membaca ketikan Anda untuk melengkapi nama, mengoreksi typo, atau memberikan variasi dosis baku.",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = customCitoText,
                        onValueChange = { 
                            customCitoText = it 
                            if (it.isNotBlank()) showCitoQuickSuggestions = true
                        },
                        placeholder = { Text("Ketik obat/prosedur (misal: epinep, intubasi, d40, torako, dc syok...)", fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MedicalTeal40,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MedicalTeal40,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        trailingIcon = {
                            if (customCitoText.isNotEmpty()) {
                                IconButton(onClick = { customCitoText = "" }, modifier = Modifier.size(24.dp)) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                }
                            }
                        },
                        enabled = !isEvaluatingCitoAction
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Buttons Row (Mirroring Diagnosis stage: AI Suggestion Button + Apply Button)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                showCitoQuickSuggestions = true
                            },
                            modifier = Modifier.weight(1.3f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40),
                            enabled = !isCitoGeminiLoading && !isEvaluatingCitoAction
                        ) {
                            if (isCitoGeminiLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp, color = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Menganalisis Ketikan...", fontSize = 11.sp)
                            } else {
                                Icon(imageVector = Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("🤖 Saran AI Sesuai Ketikan", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Button(
                            onClick = {
                                if (customCitoText.isNotBlank()) {
                                    val textToApply = customCitoText.trim()
                                    customCitoText = ""
                                    onApplyAction(textToApply)
                                }
                            },
                            modifier = Modifier.weight(0.9f),
                            enabled = customCitoText.isNotBlank() && !isEvaluatingCitoAction,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Terapkan", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("⚡ Terapkan", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Live AI Suggestions Card (Based directly on player typing)
                    if (showCitoQuickSuggestions || customCitoText.isNotBlank()) {
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
                                        text = if (customCitoText.isNotBlank())
                                            "💡 Saran Tindakan/Obat Berdasarkan Ketikan '$customCitoText':"
                                        else
                                            "💡 Saran Cepat Tindakan Resusitasi:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MedicalTeal40
                                    )
                                    if (isCitoGeminiLoading) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            CircularProgressIndicator(modifier = Modifier.size(12.dp), strokeWidth = 1.5.dp, color = MedicalTeal40)
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("AI membaca...", fontSize = 10.sp, color = MedicalTeal40)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                if (citoGeminiSuggestions.isNotEmpty()) {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        contentPadding = PaddingValues(vertical = 2.dp)
                                    ) {
                                        items(citoGeminiSuggestions) { suggestion ->
                                            SuggestionChip(
                                                onClick = {
                                                    customCitoText = suggestion
                                                },
                                                label = {
                                                    Text(
                                                        text = suggestion,
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                },
                                                icon = {
                                                    Icon(
                                                        imageVector = Icons.Default.FlashOn,
                                                        contentDescription = null,
                                                        tint = MedicalTeal40,
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                },
                                                colors = SuggestionChipDefaults.suggestionChipColors(
                                                    containerColor = MedicalTeal40.copy(alpha = 0.15f),
                                                    labelColor = MaterialTheme.colorScheme.onSurface
                                                ),
                                                border = SuggestionChipDefaults.suggestionChipBorder(
                                                    enabled = true,
                                                    borderColor = MedicalTeal40.copy(alpha = 0.4f)
                                                ),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                        }
                                    }
                                } else if (!isCitoGeminiLoading) {
                                    Text(
                                        text = "Ketik potongan nama obat/prosedur (misal: 'adrenal', 'torako', 'd40', 'lasik') untuk melihat saran AI.",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Applied Interventions Log Summary
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "📋 Tatalaksana & Resusitasi Terpasang:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    if (citoActionLogs.isEmpty() && treatmentInput.isBlank()) {
                        Text(
                            text = "Belum ada intervensi stabilisasi awal yang dipilih. Klik opsi ABCDE di bawah ini atau ketik tindakan kustom untuk mengaplikasikan penanganan cito.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            citoActionLogs.forEach { log ->
                                val chipBg = when (log.impactType) {
                                    CitoImpactType.STABILIZED -> SuccessGreen.copy(alpha = 0.12f)
                                    CitoImpactType.UNINDICATED -> WarningAmber.copy(alpha = 0.12f)
                                    else -> EmergencyRed.copy(alpha = 0.12f)
                                }
                                val chipBorder = when (log.impactType) {
                                    CitoImpactType.STABILIZED -> SuccessGreen
                                    CitoImpactType.UNINDICATED -> WarningAmber
                                    else -> EmergencyRed
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = chipBg,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, chipBorder.copy(alpha = 0.5f))
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = chipBorder
                                        ) {
                                            Text(
                                                text = if (log.timeDeltaSeconds >= 0) "+${log.timeDeltaSeconds}s" else "${log.timeDeltaSeconds}s",
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = log.actionTitle,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                if (log.isAiEvaluated) {
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Surface(
                                                        shape = RoundedCornerShape(4.dp),
                                                        color = MedicalTeal40.copy(alpha = 0.15f)
                                                    ) {
                                                        Text(
                                                            text = "🧠 AI",
                                                            fontSize = 8.sp,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = MedicalTeal40,
                                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                        )
                                                    }
                                                }
                                            }
                                            Text(
                                                text = log.message,
                                                fontSize = 10.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                lineHeight = 13.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Category Selector Chips (ABCDE)
        item {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                abcdeCategories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = {
                            Text(
                                text = when (cat) {
                                    "AIRWAY" -> "🅰️ Airway"
                                    "BREATHING" -> "🅱️ Breathing"
                                    "CIRCULATION" -> "🅒 Circulation"
                                    "DISABILITY" -> "🅹 Disability"
                                    "EXPOSURE" -> "🅔 Exposure"
                                    else -> "Semua ABCDE"
                                },
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MedicalTeal40,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // ABCDE Stabilization Action Options List
        items(filteredOptions, key = { it.id }) { option ->
            val isApplied = treatmentInput.contains(option.title, ignoreCase = true) ||
                    treatmentInput.contains(option.title.take(15), ignoreCase = true)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onApplyAction(option.title) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isApplied) SuccessGreen.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = if (isApplied) 1.5.dp else 1.dp,
                    color = if (isApplied) SuccessGreen else MaterialTheme.colorScheme.outlineVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (isApplied) SuccessGreen else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (isApplied) Icons.Default.Check else option.icon,
                                contentDescription = option.category,
                                tint = if (isApplied) Color.White else MedicalTeal40,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = when (option.category) {
                                    "AIRWAY" -> Color(0xFF3B82F6).copy(alpha = 0.15f)
                                    "BREATHING" -> Color(0xFF06B6D4).copy(alpha = 0.15f)
                                    "CIRCULATION" -> EmergencyRed.copy(alpha = 0.15f)
                                    "DISABILITY" -> Color(0xFFA855F7).copy(alpha = 0.15f)
                                    else -> WarningAmber.copy(alpha = 0.15f)
                                }
                            ) {
                                Text(
                                    text = option.category,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = when (option.category) {
                                        "AIRWAY" -> Color(0xFF2563EB)
                                        "BREATHING" -> Color(0xFF0891B2)
                                        "CIRCULATION" -> EmergencyRed
                                        "DISABILITY" -> Color(0xFF9333EA)
                                        else -> WarningAmber
                                    },
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = option.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = option.description,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isApplied) SuccessGreen else MedicalTeal40,
                        modifier = Modifier.clickable { onApplyAction(option.title) }
                    ) {
                        Text(
                            text = if (isApplied) "Terpasang ✓" else "+ Pasang",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        // Navigation Footer Buttons
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onPreviousStage,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Ke Anamnesis", fontSize = 12.sp)
                }

                Button(
                    onClick = onNextStage,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalTeal40)
                ) {
                    Text("Ke Pemfis & Lab", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next", modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
