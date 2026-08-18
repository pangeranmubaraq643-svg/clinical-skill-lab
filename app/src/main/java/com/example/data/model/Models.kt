package com.example.data.model

enum class SimulationStage(val displayName: String, val stepIndex: Int) {
    SETUP("Setup & Init", 1),
    BRIEFING("Briefing Pasien", 2),
    ANAMNESIS("Anamnesis (Chat Pasien)", 3),
    STABILISASI_AWAL("Stabilisasi Awal (Cito)", 4),
    PEMFIS_LAB("Pemfis & Lab Penunjang", 5),
    DIAGNOSIS_TATALAKSANA("Diagnosis & Tatalaksana", 6),
    EVALUASI("Hasil & Evaluasi Konsulen", 7)
}

data class AvailableCaseChip(
    val title: String,
    val isEmergency: Boolean = false
)

enum class OrganSystem(val displayName: String) {
    KARDIOLOGI("Kardiologi (Jantung)"),
    NEUROLOGI("Neurologi (Saraf)"),
    PULMONOLOGI("Pulmonologi (Paru)"),
    GASTROENTEROHEPATOLOGI("Gastroenterohepatologi"),
    ENDOKRINOLOGI("Endokrinologi & Metabolik"),
    NEFRO_UROLOGI("Nefro-Urologi (Ginjal)"),
    INFEKSI_TROPIS("Infeksi Tropis"),
    HEMATOLOGI_ONKOLOGI("Hematologi & Onkologi"),
    PEDIATRI("Pediatri (Kesehatan Anak)"),
    OBGYN("Obstetri & Ginekologi (Obgyn)"),
    DERMATOVENEROLOGI("Dermatovenerologi (Kulit)"),
    PSIKIATRI("Psikiatri & Jiwa"),
    THT_KL("THT-KL"),
    OFTALMOLOGI("Oftalmologi (Mata)"),
    MUSKULOSKELETAL("Muskuloskeletal & Reumatologi"),
    TRAUMA_EMERGENCY("Kegawatdaruratan & Trauma"),
    RANDOM("Random (Sistem Acak)")
}

enum class CaseLevel(
    val levelNumber: Int,
    val title: String,
    val timeMinutes: Int,
    val badgeLabel: String,
    val description: String,
    val criteriaDescription: String,
    val colorHex: String
) {
    ALL(
        levelNumber = 0,
        title = "Semua Level",
        timeMinutes = 0,
        badgeLabel = "🌐 Semua Level",
        description = "Seluruh tingkat kasus klinis tanpa batasan level",
        criteriaDescription = "Menampilkan semua variasi penyakit.",
        colorHex = "#0D9488"
    ),
    LEVEL_1(
        levelNumber = 1,
        title = "Level 1 (17 Mnt)",
        timeMinutes = 17,
        badgeLabel = "🟢 Level 1",
        description = "Batas waktu 17 menit. Gejala sangat khas & sering ditemukan di masyarakat.",
        criteriaDescription = "Penyakit dengan gejala yang sangat khas (patognomonik) serta sering ditemukan pada masyarakat.",
        colorHex = "#16A34A"
    ),
    LEVEL_2(
        levelNumber = 2,
        title = "Level 2 (12 Mnt)",
        timeMinutes = 12,
        badgeLabel = "🟡 Level 2",
        description = "Batas waktu 12 menit. Gejala umum & membutuhkan pemeriksaan lab spesifik.",
        criteriaDescription = "Penyakit dengan gejala umum serta membutuhkan pemeriksaan lab yang spesifik untuk mendiagnosis.",
        colorHex = "#D97706"
    ),
    LEVEL_3(
        levelNumber = 3,
        title = "Level 3 (7 Mnt)",
        timeMinutes = 7,
        badgeLabel = "🔴 Level 3",
        description = "Batas waktu 7 menit. Membutuhkan penanganan segera (cito) & berisiko tinggi.",
        criteriaDescription = "Penyakit-penyakit yang membutuhkan penanganan segera serta berisiko tinggi.",
        colorHex = "#DC2626"
    );

    val color: androidx.compose.ui.graphics.Color
        get() = androidx.compose.ui.graphics.Color(android.graphics.Color.parseColor(colorHex))

    val durationBadge: String
        get() = if (timeMinutes > 0) "$timeMinutes Mnt" else "Bebas"
}

enum class TimeMode(
    val displayName: String,
    val minutes: Int,
    val isEmergency: Boolean = false,
    val description: String = ""
) {
    STANDARD("⏱️ Standar Waktu SKDI", 17, false, "Waktu disesuaikan dengan Level Kasus SKDI (17, 12, atau 7 Menit)")
}

enum class DifficultyLevel(
    val id: String,
    val displayName: String,
    val badgeLabel: String,
    val subtitle: String,
    val description: String,
    val promptInstruction: String,
    val colorHex: String
) {
    BASIC(
        id = "BASIC",
        displayName = "Basic (Awam)",
        badgeLabel = "🟢 Basic",
        subtitle = "Bahasa sehari-hari & keluhan awam",
        description = "Pasien orang awam, hanya paham bahasa sehari-hari sederhana. Menjelaskan gejala secara deskriptif tanpa terminologi medis.",
        promptInstruction = "TINGKAT KESULITAN: BASIC. Pasien adalah orang awam murni. Pasien HANYA menggunakan bahasa sehari-hari sederhana dan perumpamaan awam (misal: 'dada rasanya ditindih beban berat', 'kepala berputar tujuh keliling', 'kencing terasa panas'). Pasien TIDAK PERNAH menggunakan istilah medis ilmiah. Respon ramah, polos, dan tidak berbelit.",
        colorHex = "#16A34A"
    ),
    INTERMEDIATE(
        id = "INTERMEDIATE",
        displayName = "Intermediate (Menengah)",
        badgeLabel = "🟡 Intermediate",
        subtitle = "Keluhan bercampur terminologi umum",
        description = "Pasien memiliki wawasan kesehatan umum, menyebutkan istilah umum (tensi, maag, asam urat, kolesterol), menuntut anamnesis kronologis terarah.",
        promptInstruction = "TINGKAT KESULITAN: INTERMEDIATE. Pasien adalah orang yang cukup teredukasi. Pasien dapat menyebutkan riwayat umum (seperti 'ada riwayat hipertensi', 'sering maag/asam lambung', 'gula darah') namun tetap mendeskripsikan gejala utama secara natural dan menuntut dokter menggali kronologi onset, radiasi nyeri, faktor peringan/pemberat.",
        colorHex = "#D97706"
    ),
    ADVANCED(
        id = "ADVANCED",
        displayName = "Advanced (Kompleks / Nakes)",
        badgeLabel = "🔴 Advanced",
        subtitle = "Terminologi medis & kasus atipikal",
        description = "Pasien/keluarga berpendidikan medis atau presentasi kasus atipikal/komorbid. Menggunakan terminologi spesifik, riwayat obat & komplikasi.",
        promptInstruction = "TINGKAT KESULITAN: ADVANCED. Pasien/keluarga memiliki pemahaman medis lebih mendalam atau kasus memiliki presentasi atipikal/komorbid. Pasien dapat menyebutkan riwayat obat spesifik, riwayat operasi, atau istilah medis tertentu, namun anamnesis tetap menantang karena gejala bisa tumpang tindih atau memerlukan penalaran klinis lanjutan.",
        colorHex = "#DC2626"
    );

    val color: androidx.compose.ui.graphics.Color
        get() = androidx.compose.ui.graphics.Color(android.graphics.Color.parseColor(colorHex))
}

enum class ExamCategory(val displayName: String) {
    PEMFIS("Pemeriksaan Fisik"),
    LAB("Laboratorium"),
    IMAGING("Radiologi & EKG"),
    OTHER("Lainnya")
}

data class ExamItem(
    val id: String,
    val name: String,
    val category: ExamCategory,
    val result: String,
    val costRupiah: Long
)

data class UserExamResult(
    val examName: String,
    val category: ExamCategory,
    val result: String,
    val costRupiah: Long,
    val requestedAt: Long = System.currentTimeMillis(),
    val isAiGenerated: Boolean = false,
    val isLoading: Boolean = false
)

enum class ChatSender {
    PASIEN,
    DOKTER,
    SYSTEM
}

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: ChatSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class ClinicalCase(
    val id: String,
    val organSystem: String,
    val title: String,
    val patientAge: Int,
    val patientGender: String,
    val patientOccupation: String,
    val generalAppearance: String,
    val chiefComplaint: String,
    val td: String,
    val nadi: Int,
    val rr: Int,
    val suhu: Double,
    val spO2: Int,
    val trueDiagnosis: String,
    val differentialDiagnoses: List<String>,
    val patientPersonaInstruction: String,
    val availableExams: List<ExamItem>,
    val optimalExamNames: List<String>,
    val optimalCostEstimate: Long,
    val recommendedTreatment: String,
    val kemenkesGuidelines: String,
    val isEmergencyCase: Boolean = false,
    val pathophysiology: String = ""
)

data class EvaluationResult(
    val diagnosisStatus: String, // BENAR, KURANG TEPAT, SALAH
    val diagnosisFeedback: String,
    val trueDiagnosis: String,
    val pemfisStatus: String, // OPTIMAL, KURANG, TIDAK TELITI
    val pemfisFeedback: String,
    val costStatus: String, // OPTIMAL, KURANG, TIDAK TELITI
    val examFeedback: String,
    val totalSpent: Long,
    val optimalCost: Long,
    val costRatioText: String,
    val treatmentStatus: String, // OPTIMAL, KURANG, TIDAK TELITI
    val treatmentFeedback: String,
    val educationStatus: String, // OPTIMAL, KURANG, TIDAK TELITI
    val educationFeedback: String,
    val diagnosisScore: Int = 0, // Nilai Diagnosis (0 - 35 Pts)
    val examScore: Int = 0, // Nilai Pemfis & Penunjang (0 - 25 Pts)
    val treatmentScore: Int = 0, // Nilai Tatalaksana (0 - 25 Pts)
    val educationScore: Int = 0, // Nilai Edukasi Pasien (0 - 15 Pts)
    val totalScore: Int,
    val downloadCode: String,
    val isAiEvaluated: Boolean = false
)

enum class CitoImpactType {
    STABILIZED,        // +Time (e.g. +30s), 0 Pts penalty
    UNINDICATED,       // 0s Time, -3 Pts penalty (No medical indication or TTV already stable)
    HARMFUL,           // -60s Time, -5 Pts penalty (Harmful / contraindicated)
    FATAL_COLLAPSE     // Patient Death / Immediate 0s Timer
}

data class CitoActionFeedback(
    val actionTitle: String,
    val impactType: CitoImpactType,
    val timeDeltaSeconds: Int,
    val pointPenalty: Int = 0,
    val message: String,
    val detailedExplanation: String = "",
    val updatedVitalsNote: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isAiEvaluated: Boolean = false,
    val targetSystolic: Int? = null,
    val targetDiastolic: Int? = null,
    val targetHr: Int? = null,
    val targetRr: Int? = null,
    val targetSpO2: Int? = null,
    val targetTemp: Double? = null
)

