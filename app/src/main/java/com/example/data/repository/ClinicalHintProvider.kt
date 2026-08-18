package com.example.data.repository

import com.example.data.model.ClinicalCase

data class ClinicalHintItem(
    val level: Int,
    val title: String,
    val content: String,
    val pointCost: Int,
    val iconEmoji: String
)

object ClinicalHintProvider {

    fun generateHintsForCase(case: ClinicalCase): List<ClinicalHintItem> {
        val guide = PpkGuideRepository.getOrCreateGuideline(case.trueDiagnosis)

        // Level 1: Keadaan Umum / Inspeksi Fisik, Gejala Terlewat & Pemfis Kunci
        val level1Content = buildString {
            append("• Kategori Kasus: Sistem ${case.organSystem}\n\n")

            // 1. Keadaan Umum & Temuan Inspeksi Fisik (Observasi Langsung Pasien)
            append("• Keadaan Umum & Inspeksi Fisik Pasien (Observasi Langsung):\n")
            if (case.generalAppearance.isNotBlank()) {
                val cleanAppearance = case.generalAppearance
                    .replace(case.trueDiagnosis, "kondisi pasien", ignoreCase = true)
                append("  - ").append(cleanAppearance).append("\n\n")
            } else {
                append("  - Pasien tampak sakit sedang-berat dengan perburukan kondisi klinis akut.\n\n")
            }

            // 2. Gejala & Anamnesis Terlewat
            append("• Gejala & Anamnesis Terlewat yang Harus Digali:\n")
            
            // Extract key symptoms directly from persona, chief complaint, and guidelines
            val symptomPoints = mutableListOf<String>()
            if (case.patientPersonaInstruction.isNotBlank()) {
                val cleanPersona = case.patientPersonaInstruction
                    .replace(case.trueDiagnosis, "kondisi ini", ignoreCase = true)
                    .replace(Regex("(?i)dokter|dok|anda adalah|pasien:"), "")
                    .trim()
                if (cleanPersona.isNotBlank()) {
                    symptomPoints.add(cleanPersona.take(200))
                }
            }
            if (guide.symptomsAndAnamnesis.isNotBlank()) {
                val cleanGuideSymptoms = guide.symptomsAndAnamnesis
                    .replace(case.trueDiagnosis, "kondisi medis ini", ignoreCase = true)
                    .trim()
                symptomPoints.add(cleanGuideSymptoms.take(200))
            }
            
            if (symptomPoints.isNotEmpty()) {
                append("  - ").append(symptomPoints.joinToString("\n  - "))
            } else {
                append("  - Perhatikan onset keluhan, faktor pencetus (seperti riwayat benturan, banjir, obat, makanan), serta gejala lokal & sistemik.")
            }

            // 3. Pemeriksaan Fisik (Pemfis) Kunci
            append("\n\n• Temuan Pemeriksaan Fisik (Pemfis) Kunci:\n")
            val pemfisItems = case.availableExams.filter { it.category == com.example.data.model.ExamCategory.PEMFIS }
            if (pemfisItems.isNotEmpty()) {
                pemfisItems.take(5).forEach { exam ->
                    append("  - ${exam.name}: ${exam.result}\n")
                }
            } else if (guide.physicalAndLabExams.isNotBlank()) {
                val cleanPemfis = guide.physicalAndLabExams.replace(case.trueDiagnosis, "kondisi ini", ignoreCase = true)
                append("  - ").append(cleanPemfis.take(220))
            } else {
                append("  - Evaluasi tanda vital lengkap, auskultasi, palpasi organ terkait, serta uji provokasi fisik spesifik.")
            }
        }

        // Level 2: Rincian Intervensi Cito Rekomendasi & Penunjang Kunci
        val level2Content = buildString {
            val citoGuide = VitalSignsManager.getCitoStabilizationGuide(case, emptyList())

            append("• ⚡ RINCIAN INTERVENSI CITO & MEDIKAMENTOSA REKOMENDASI:\n")
            append("  - Profil Patologi: ${citoGuide.pathologyName}\n")
            citoGuide.primaryCitoActions.forEach { action ->
                append("  - $action\n")
            }
            append("  - Mekanisme Fisiologis: ${citoGuide.rationale}\n")

            if (case.recommendedTreatment.isNotBlank()) {
                append("  - Terapi Definitive Utama: ${case.recommendedTreatment}\n")
            }

            append("\n• 🔬 PEMERIKSAAN LAB & PENUNJANG KUNCI (GOLD STANDARD):\n")

            // Explicitly list all optimal/recommended lab & imaging exams
            val optimalExams = case.availableExams.filter { exam ->
                case.optimalExamNames.any { opt -> opt.contains(exam.name, ignoreCase = true) || exam.name.contains(opt, ignoreCase = true) } ||
                exam.category == com.example.data.model.ExamCategory.LAB ||
                exam.category == com.example.data.model.ExamCategory.IMAGING
            }

            if (optimalExams.isNotEmpty()) {
                optimalExams.forEach { exam ->
                    val catLabel = if (exam.category == com.example.data.model.ExamCategory.LAB) "LAB" else "IMAGING/PENCITRAAN"
                    append("  - [$catLabel] ${exam.name}\n")
                    append("    └ Hasil khas: ${exam.result}\n")
                }
            } else {
                append("  - [LAB] Darah Rutin Total (Hb, Ht, Leukosit, Trombosit)\n")
                append("  - [LAB] Pemeriksaan Spesifik (GDS, Elektrolit, atau Biomarker)\n")
                append("  - [IMAGING] Radiologi / EKG / USG Sesuai Organ Terlibat\n")
            }

            if (case.optimalCostEstimate > 0) {
                append("\n• Estimasi Biaya Optimal: Rp ${String.format("%,d", case.optimalCostEstimate).replace(',', '.')}")
            }
        }

        // Level 3: Patofisiologi Dasar, Diagnosis Pasti & Tatalaksana PPK
        val level3Content = buildString {
            append("• Patofisiologi Ringkas:\n")
            if (case.pathophysiology.isNotBlank()) {
                append("  - ").append(case.pathophysiology).append("\n\n")
            } else if (guide.pathophysiology.isNotBlank()) {
                append("  - ").append(guide.pathophysiology).append("\n\n")
            } else {
                append("  - Gangguan patologis pada sistem ${case.organSystem} terkait ${case.chiefComplaint.lowercase()}.\n\n")
            }

            append("• Petunjuk Diagnosis Pasti & Banding:\n")
            append("  - Diagnosis Utama: ${case.trueDiagnosis}\n")
            if (case.differentialDiagnoses.isNotEmpty()) {
                append("  - Diagnosis Banding: ${case.differentialDiagnoses.joinToString(", ")}\n")
            }

            append("\n• Panduan Tatalaksana Utama (PPK):\n")
            if (guide.treatmentAndMedication.isNotBlank()) {
                val cleanTreatment = guide.treatmentAndMedication.replace(case.trueDiagnosis, "kondisi ini", ignoreCase = true)
                append("  - ").append(cleanTreatment.take(240))
                if (cleanTreatment.length > 240) append("...")
            } else if (case.recommendedTreatment.isNotBlank()) {
                append("  - ").append(case.recommendedTreatment)
            } else {
                append("  - Tatalaksana suportif, medikamentosa spesifik, dan edukasi pasien.")
            }
        }

        return listOf(
            ClinicalHintItem(
                level = 1,
                title = "Level 1: Petunjuk Anamnesis Terlewat & Pemfis Kunci",
                content = level1Content,
                pointCost = 5,
                iconEmoji = "🧩"
            ),
            ClinicalHintItem(
                level = 2,
                title = "Level 2: Rincian Intervensi Cito & Penunjang Kunci",
                content = level2Content,
                pointCost = 10,
                iconEmoji = "⚡"
            ),
            ClinicalHintItem(
                level = 3,
                title = "Level 3: Patofisiologi, Diagnosis Pasti & Tatalaksana PPK",
                content = level3Content,
                pointCost = 15,
                iconEmoji = "🎯"
            )
        )
    }
}
