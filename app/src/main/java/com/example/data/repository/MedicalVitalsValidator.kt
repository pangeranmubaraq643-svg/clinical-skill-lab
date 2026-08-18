package com.example.data.repository

import com.example.data.model.ClinicalCase
import kotlin.math.abs

/**
 * Data structure representing fully verified and calibrated clinical vital signs (TTV).
 */
data class ValidatedVitals(
    val td: String,
    val nadi: Int,
    val rr: Int,
    val suhu: Double,
    val spO2: Int
)

/**
 * Master Medical Vital Signs Engine & Physiological Validator.
 * Enforces 100% clinically accurate, physiologically coherent TTV
 * for any case (built-in, dynamic catalog, or AI-generated) according to
 * Indonesian Medical Competency Standards (SKDI), PPK Kemenkes RI, Harrison's Internal Medicine,
 * Nelson Pediatrics, and ATLS/ACLS guidelines.
 */
object MedicalVitalsValidator {

    /**
     * Helper to safely match a whole word without accidental substring collisions.
     */
    fun hasWord(text: String, word: String): Boolean {
        return Regex("\\b${Regex.escape(word)}\\b", RegexOption.IGNORE_CASE).containsMatchIn(text)
    }

    /**
     * Helper to test if a text matches any of the medical keywords/phrases safely.
     * Short abbreviations (<= 4 characters) require whole word boundaries.
     */
    fun matchesDiagnosis(text: String, vararg keywords: String): Boolean {
        return keywords.any { kw ->
            if (kw.length <= 4 && kw.all { it.isLetter() }) {
                hasWord(text, kw)
            } else {
                text.contains(kw, ignoreCase = true)
            }
        }
    }

    /**
     * Sanitizes any patient occupation string to ensure a single, realistic, and coherent profession.
     * Prevents multi-job strings separated by slashes (e.g. "Karyawan / Ibu Rumah Tangga / Wiraswasta")
     * and strips accidental clinical annotations from job titles.
     */
    fun sanitizeOccupation(rawOccupation: String, age: Int, gender: String): String {
        val trimmed = rawOccupation.trim()
        val isFemale = gender.contains("Perempuan", ignoreCase = true) || gender.contains("Wanita", ignoreCase = true)

        if (trimmed.isBlank() || trimmed == "-" || trimmed.equals("Tidak bekerja", ignoreCase = true) || trimmed.equals("Belum Bekerja", ignoreCase = true)) {
            return when {
                age <= 4 -> "Anak Balita"
                age < 18 -> "Pelajar"
                age in 18..24 -> if (isFemale) "Mahasiswi" else "Mahasiswa"
                age >= 60 -> if (isFemale) "Ibu Rumah Tangga" else "Pensiunan"
                isFemale -> "Ibu Rumah Tangga"
                else -> "Karyawan Swasta"
            }
        }

        // If it contains slashes, commas, or "atau" (multiple occupation choices)
        if (trimmed.contains("/") || trimmed.contains(",") || trimmed.contains(" atau ", ignoreCase = true)) {
            val parts = trimmed.split(Regex("[/,]|(\\s+atau\\s+)"))
                .map { it.replace(Regex("\\(.*?\\)"), "").trim() }
                .filter { it.isNotBlank() }

            // Prioritize matching standard Indonesian professions
            for (p in parts) {
                if (isFemale && p.contains("Ibu Rumah Tangga", ignoreCase = true)) {
                    return "Ibu Rumah Tangga"
                }
                if (p.contains("Pensiun", ignoreCase = true)) {
                    return "Pensiunan"
                }
                if (p.contains("Mahasisw", ignoreCase = true)) {
                    return if (isFemale) "Mahasiswi" else "Mahasiswa"
                }
                if (p.contains("Pelajar", ignoreCase = true) || p.contains("Siswa", ignoreCase = true)) {
                    return "Pelajar"
                }
                if (p.contains("PNS", ignoreCase = true) || p.contains("Pegawai Negeri", ignoreCase = true)) {
                    return "Pegawai Negeri Sipil (PNS)"
                }
                if (p.contains("Guru", ignoreCase = true) || p.contains("Dosen", ignoreCase = true)) {
                    return p
                }
                if (p.contains("Petani", ignoreCase = true) || p.contains("Pedagang", ignoreCase = true) || p.contains("Wiraswasta", ignoreCase = true)) {
                    return p
                }
            }

            val firstPart = parts.firstOrNull() ?: ""
            if (firstPart.isNotBlank()) {
                return firstPart
            }
        }

        // Strip extraneous parenthetical clinical notes from occupation
        if (trimmed.contains("(")) {
            val withoutParen = trimmed.replace(Regex("\\(.*?\\)"), "").trim()
            if (withoutParen.isNotBlank()) {
                return withoutParen
            }
        }

        return trimmed
    }

    /**
     * Validates and calibrates any ClinicalCase to ensure all vital signs and patient demographic metadata
     * are 100% medically accurate, physiologically coherent, and free of contradictions.
     * Preserves authentic unique TTV values if already physiologically sound.
     */
    fun validateAndCalibrateCase(case: ClinicalCase): ClinicalCase {
        val cleanOccupation = sanitizeOccupation(case.patientOccupation, case.patientAge, case.patientGender)

        // Check if existing vitals are valid, physiological, and non-empty
        val hasExistingVitals = case.td.isNotBlank() && case.nadi > 0 && case.rr > 0 && case.suhu > 30.0 && case.spO2 > 0

        if (hasExistingVitals && isVitalsPhysiologicallyPlausible(case)) {
            // Already physiologically accurate and coherent, preserve the unique values
            return if (case.patientOccupation != cleanOccupation) {
                case.copy(patientOccupation = cleanOccupation)
            } else {
                case
            }
        }

        // Generate authoritative, pathophysiology-coherent vital signs
        val calibratedVitals = getAccurateVitalsForDisease(
            diagnosis = case.trueDiagnosis,
            title = case.title,
            organSystem = case.organSystem,
            age = case.patientAge,
            gender = case.patientGender,
            chiefComplaint = case.chiefComplaint,
            generalAppearance = case.generalAppearance,
            isEmergency = case.isEmergencyCase,
            pathophysiology = case.pathophysiology,
            personaInstruction = case.patientPersonaInstruction,
            currentTd = case.td,
            currentNadi = case.nadi,
            currentRr = case.rr,
            currentSuhu = case.suhu,
            currentSpO2 = case.spO2,
            seedKey = case.id + case.title + case.trueDiagnosis
        )

        return case.copy(
            td = calibratedVitals.td,
            nadi = calibratedVitals.nadi,
            rr = calibratedVitals.rr,
            suhu = calibratedVitals.suhu,
            spO2 = calibratedVitals.spO2,
            patientOccupation = cleanOccupation
        )
    }

    /**
     * Checks if given vitals in a case are medically plausible for its pathology and age.
     */
    private fun isVitalsPhysiologicallyPlausible(case: ClinicalCase): Boolean {
        val diag = (case.trueDiagnosis + " " + case.title).lowercase()
        val systolic = parseSystolic(case.td)
        val diastolic = parseDiastolic(case.td)
        val nadi = case.nadi
        val rr = case.rr
        val suhu = case.suhu
        val spO2 = case.spO2
        val age = case.patientAge

        // Basic boundary checks
        if (systolic !in 50..260 || diastolic !in 30..150) return false
        if (nadi !in 35..220) return false
        if (rr !in 10..70) return false
        if (suhu !in 34.0..42.0) return false
        if (spO2 !in 60..100) return false

        // Pediatric heart rate & RR sanity
        if (age <= 2) {
            if (nadi < 90 || rr < 22) return false
        } else if (age <= 6) {
            if (nadi < 75 || rr < 18) return false
        }

        // Disease specific vital sign coherence checks
        val isShock = matchesDiagnosis(diag, "syok", "shock", "anafilaks", "dss") ||
                (diag.contains("dhf") && matchesDiagnosis(diag, "iv", "derajat 4", "derajat iv", "syok")) ||
                diag.contains("syok hemoragik")
        if (isShock && (systolic > 95 || nadi < 95)) return false

        val isHypertensiveCrisis = matchesDiagnosis(diag, "krisis hipertensi", "hipertensi emergensi", "ensefalopati hipertensi", "eklamsia")
        if (isHypertensiveCrisis && systolic < 160) return false

        val isPreeclampsiaSevere = matchesDiagnosis(diag, "preeklamsia berat", "peb")
        if (isPreeclampsiaSevere && systolic < 150) return false

        val isHighFeverInfection = (matchesDiagnosis(diag, "tifoid", "typhoid", "malaria", "dbd", "dhf", "meningitis", "kejang demam", "sepsis", "pielonefritis", "pneumonia")) && !diag.contains("post")
        if (isHighFeverInfection && suhu < 37.5) return false

        val isSevereRespiratoryEmergency = matchesDiagnosis(diag, "tension pneumothorax", "edema paru", "gagal napas", "ards") ||
                (diag.contains("asma") && (diag.contains("berat") || diag.contains("eksaserbasi")))
        if (isSevereRespiratoryEmergency && (spO2 > 95 || rr < 24)) return false

        return true
    }

    /**
     * Core clinical reasoning engine for determining authentic physiological vital signs based on disease pathophysiology.
     */
    fun getAccurateVitalsForDisease(
        diagnosis: String,
        title: String = "",
        organSystem: String = "",
        age: Int = 35,
        gender: String = "Laki-laki",
        chiefComplaint: String = "",
        generalAppearance: String = "",
        isEmergency: Boolean = false,
        pathophysiology: String = "",
        personaInstruction: String = "",
        currentTd: String = "",
        currentNadi: Int = 0,
        currentRr: Int = 0,
        currentSuhu: Double = 0.0,
        currentSpO2: Int = 0,
        seedKey: String = ""
    ): ValidatedVitals {
        // Diagnosis carries 95% of specific pathology weight
        val diagLower = diagnosis.lowercase().trim()
        val titleLower = title.lowercase().trim()
        val primaryTarget = if (diagLower.isNotBlank()) diagLower else titleLower
        val combinedTarget = "$diagLower $titleLower".trim()

        // Helper checking function that prioritizes exact diagnosis keywords and avoids title symptom false positives
        fun match(vararg keywords: String): Boolean {
            return matchesDiagnosis(primaryTarget, *keywords) || matchesDiagnosis(combinedTarget, *keywords)
        }

        // Deterministic micro-variance based on seedKey to give realistic clinical variation
        val hash = abs((seedKey.ifBlank { diagnosis + title + age.toString() }).hashCode())
        val varBpSys = (hash % 7) - 3     // -3 to +3 mmHg
        val varBpDia = ((hash / 7) % 5) - 2 // -2 to +2 mmHg
        val varHr = ((hash / 13) % 7) - 3  // -3 to +3 bpm
        val varRr = ((hash / 17) % 3) - 1  // -1 to +1 x/min
        val varTemp = (((hash / 19) % 5) - 2) * 0.1 // -0.2 to +0.2 °C

        // =========================================================================
        // 1. SYOK & KEGAWATDARURATAN HEMODINAMIK BERAT
        // =========================================================================
        if (match("anafilaks", "anaphylaxis", "syok anafilaktik")) {
            val sys = 70 + varBpSys
            val dia = 40 + varBpDia
            val hr = 138 + varHr
            val rr = 34 + varRr
            val temp = 36.4 + varTemp
            val spo2 = (82 + (hash % 5)).coerceIn(78, 88)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("tension pneumothorax", "pneumotoraks ventil", "pneumotoraks tensi")) {
            val sys = 75 + varBpSys
            val dia = 45 + varBpDia
            val hr = 142 + varHr
            val rr = 38 + varRr
            val temp = 36.5 + varTemp
            val spo2 = (78 + (hash % 4)).coerceIn(75, 84)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("open pneumothorax", "sucking chest")) {
            val sys = 85 + varBpSys
            val dia = 55 + varBpDia
            val hr = 128 + varHr
            val rr = 36 + varRr
            val temp = 36.4 + varTemp
            val spo2 = (82 + (hash % 4)).coerceIn(80, 86)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("tamponade", "tamponade jantung", "efusi perikardial masif")) {
            val sys = 80 + varBpSys
            val dia = 50 + varBpDia
            val hr = 138 + varHr
            val rr = 32 + varRr
            val temp = 36.8 + varTemp
            val spo2 = (88 + (hash % 4)).coerceIn(85, 92)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("syok kardiogenik", "cardiogenic shock")) {
            val sys = 75 + varBpSys
            val dia = 50 + varBpDia
            val hr = 136 + varHr
            val rr = 32 + varRr
            val temp = 36.4 + varTemp
            val spo2 = (86 + (hash % 4)).coerceIn(83, 89)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("dss", "dengue shock syndrome") || (match("dhf", "dbd") && (match("syok", "derajat iv", "derajat 4") || hasWord(combinedTarget, "iv")))) {
            val sys = if (age <= 10) 65 + varBpSys else 72 + varBpSys
            val dia = if (age <= 10) 45 + varBpDia else 50 + varBpDia
            val hr = if (age <= 10) 150 + varHr else 138 + varHr
            val rr = if (age <= 10) 40 + varRr else 32 + varRr
            val temp = 35.8 + varTemp
            val spo2 = (90 + (hash % 4)).coerceIn(88, 94)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("dhf", "dbd") && (match("pre-syok", "derajat iii", "derajat 3") || hasWord(combinedTarget, "iii"))) {
            val sys = if (age <= 10) 80 + varBpSys else 85 + varBpSys
            val dia = if (age <= 10) 65 + varBpDia else 68 + varBpDia
            val hr = if (age <= 10) 136 + varHr else 124 + varHr
            val rr = if (age <= 10) 32 + varRr else 26 + varRr
            val temp = 36.4 + varTemp
            val spo2 = (95 + (hash % 3)).coerceIn(94, 97)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("syok hemoragik", "ruptur limpa", "ruptur lien", "atonia uteri", "kehamilan ektopik terganggu", "perdarahan masif") ||
            hasWord(primaryTarget, "ket") || hasWord(primaryTarget, "hpp")
        ) {
            val sys = 75 + varBpSys
            val dia = 45 + varBpDia
            val hr = 138 + varHr
            val rr = 30 + varRr
            val temp = 35.6 + varTemp
            val spo2 = (90 + (hash % 4)).coerceIn(88, 93)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("syok septik", "septic shock")) {
            val sys = 80 + varBpSys
            val dia = 45 + varBpDia
            val hr = 134 + varHr
            val rr = 30 + varRr
            val temp = 39.3 + varTemp
            val spo2 = (93 + (hash % 3)).coerceIn(91, 95)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("combustio", "luka bakar derajat 3", "luka bakar luas")) {
            val sys = 85 + varBpSys
            val dia = 55 + varBpDia
            val hr = 128 + varHr
            val rr = 28 + varRr
            val temp = 36.0 + varTemp
            val spo2 = (94 + (hash % 3)).coerceIn(93, 97)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 2. KRISIS HIPERTENSI & GANGGUAN SEREBROVASKULAR / OBGYN TEKANAN TINGGI
        // =========================================================================
        if (match("edema paru", "pulmonary edema") || (match("adhf") && (match("hipertensi") || isEmergency))) {
            val sys = 175 + varBpSys
            val dia = 100 + varBpDia
            val hr = 118 + varHr
            val rr = 34 + varRr
            val temp = 36.5 + varTemp
            val spo2 = (84 + (hash % 4)).coerceIn(82, 88)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("krisis hipertensi", "hipertensi emergensi", "hipertensi urgensi", "ensefalopati hipertensif")) {
            val sys = 210 + varBpSys
            val dia = 120 + varBpDia
            val hr = 106 + varHr
            val rr = 24 + varRr
            val temp = 36.7 + varTemp
            val spo2 = (96 + (hash % 3)).coerceIn(95, 98)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("eklamsia", "eclampsia")) {
            val sys = 185 + varBpSys
            val dia = 115 + varBpDia
            val hr = 120 + varHr
            val rr = 26 + varRr
            val temp = 37.2 + varTemp
            val spo2 = (94 + (hash % 3)).coerceIn(93, 97)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("preeklamsia", "preeklampsia", "preeklamsia berat") || hasWord(primaryTarget, "peb")) {
            val sys = 170 + varBpSys
            val dia = 110 + varBpDia
            val hr = 96 + varHr
            val rr = 22 + varRr
            val temp = 36.7 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // Stroke hemoragik / ICH / SAH: requires strict whole word/phrase matching
        if (match("stroke hemoragik", "stroke perdarahan", "perdarahan intraserebral", "perdarahan subaraknoid") ||
            primaryTarget.contains("(ich)", ignoreCase = true) || primaryTarget.contains("(sah)", ignoreCase = true)
        ) {
            val sys = 185 + varBpSys
            val dia = 110 + varBpDia
            val hr = 88 + varHr
            val rr = 20 + varRr
            val temp = 36.9 + varTemp
            val spo2 = 97
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("stroke iskemik", "infark serebri", "transient ischemic attack") || hasWord(primaryTarget, "tia")) {
            val sys = 170 + varBpSys
            val dia = 100 + varBpDia
            val hr = 86 + varHr
            val rr = 18 + varRr
            val temp = 36.6 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("hipertensi grade 2", "hipertensi stage 2", "hipertensi derajat 2")) {
            val sys = 165 + varBpSys
            val dia = 100 + varBpDia
            val hr = 84 + varHr
            val rr = 18 + varRr
            val temp = 36.7 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("hipertensi grade 1", "hipertensi stage 1", "hipertensi derajat 1", "hipertensi primer", "hipertensi esensial", "hipertensi")) {
            val sys = 145 + varBpSys
            val dia = 95 + varBpDia
            val hr = 80 + varHr
            val rr = 18 + varRr
            val temp = 36.7 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 3. KARDIOLOGI & ARITMIA
        // =========================================================================
        if (match("stemi", "infark miokard akut", "acute myocardial infarction")) {
            val sys = 145 + varBpSys
            val dia = 90 + varBpDia
            val hr = 104 + varHr
            val rr = 24 + varRr
            val temp = 36.8 + varTemp
            val spo2 = (95 + (hash % 3)).coerceIn(94, 97)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("nstemi", "angina pektoris tak stabil", "unstable angina") || hasWord(primaryTarget, "uap")) {
            val sys = 140 + varBpSys
            val dia = 90 + varBpDia
            val hr = 98 + varHr
            val rr = 22 + varRr
            val temp = 36.7 + varTemp
            val spo2 = (96 + (hash % 3)).coerceIn(95, 98)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("angina pektoris stabil", "stable angina", "penyakit jantung koroner", "pjk") || hasWord(primaryTarget, "aps")) {
            val sys = 135 + varBpSys
            val dia = 85 + varBpDia
            val hr = 86 + varHr
            val rr = 18 + varRr
            val temp = 36.7 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("gagal jantung kongestif", "gagal jantung", "decompensatio cordis") || hasWord(primaryTarget, "chf")) {
            val sys = 150 + varBpSys
            val dia = 95 + varBpDia
            val hr = 108 + varHr
            val rr = 26 + varRr
            val temp = 36.7 + varTemp
            val spo2 = (93 + (hash % 3)).coerceIn(92, 95)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("fibrilasi atrial", "atrial fibrillation", "af rvr", "svt", "supraventrikular takikardia")) {
            val sys = 110 + varBpSys
            val dia = 70 + varBpDia
            val hr = 154 + varHr
            val rr = 22 + varRr
            val temp = 36.6 + varTemp
            val spo2 = 97
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("total av block", "bradikardia simtomatik", "sick sinus syndrome") || hasWord(primaryTarget, "tavb")) {
            val sys = 95 + varBpSys
            val dia = 60 + varBpDia
            val hr = 42 + (hash % 5)
            val rr = 18 + varRr
            val temp = 36.5 + varTemp
            val spo2 = 96
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("perikarditis", "miokarditis", "pericarditis", "myocarditis")) {
            val sys = 125 + varBpSys
            val dia = 80 + varBpDia
            val hr = 104 + varHr
            val rr = 22 + varRr
            val temp = 38.0 + varTemp
            val spo2 = 97
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 4. DEMAM TIFOID & PENYAKIT INFEKSI TROPIS
        // =========================================================================
        // Khusus Demam Tifoid: Bradikardia Relatif (Faget's Sign)
        if (match("tifoid", "typhoid", "salmonella", "tifus", "tipus", "demam tifoid")) {
            val sys = if (age <= 10) 100 + varBpSys else 115 + varBpSys
            val dia = if (age <= 10) 65 + varBpDia else 75 + varBpDia
            val hr = if (age <= 5) 96 + varHr else if (age <= 10) 84 + varHr else 76 + (hash % 7) // Bradikardia Relatif Faget!
            val rr = if (age <= 5) 24 + varRr else 20 + varRr
            val temp = 39.1 + varTemp
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = 98)
        }

        if (match("malaria", "plasmodium")) {
            val sys = if (age <= 10) 95 + varBpSys else 110 + varBpSys
            val dia = if (age <= 10) 60 + varBpDia else 70 + varBpDia
            val hr = if (age <= 5) 140 + varHr else 112 + varHr
            val rr = if (age <= 5) 30 + varRr else 22 + varRr
            val temp = 39.6 + varTemp
            val spo2 = (96 + (hash % 3)).coerceIn(95, 98)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("demam berdarah", "dengue", "dengue hemorrhagic fever") || hasWord(primaryTarget, "dbd") || hasWord(primaryTarget, "dhf")) {
            val sys = if (age <= 10) 100 + varBpSys else 110 + varBpSys
            val dia = if (age <= 10) 65 + varBpDia else 70 + varBpDia
            val hr = if (age <= 5) 130 + varHr else 92 + varHr
            val rr = if (age <= 5) 26 + varRr else 20 + varRr
            val temp = 38.6 + varTemp
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = 98)
        }

        if (match("leptospirosis", "leptospira", "morbus weil", "weil disease")) {
            val sys = 105 + varBpSys
            val dia = 65 + varBpDia
            val hr = 108 + varHr
            val rr = 22 + varRr
            val temp = 39.2 + varTemp
            val spo2 = 97
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("sepsis", "bakteremia", "urosepsis", "sepsis berat")) {
            val sys = 90 + varBpSys
            val dia = 55 + varBpDia
            val hr = 126 + varHr
            val rr = 28 + varRr
            val temp = 39.4 + varTemp
            val spo2 = (93 + (hash % 3)).coerceIn(91, 95)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("meningitis", "ensefalitis", "meningoensefalitis")) {
            val sys = if (age <= 5) 90 + varBpSys else 125 + varBpSys
            val dia = if (age <= 5) 55 + varBpDia else 80 + varBpDia
            val hr = if (age <= 5) 145 + varHr else 110 + varHr
            val rr = if (age <= 5) 34 + varRr else 24 + varRr
            val temp = 39.5 + varTemp
            val spo2 = 96
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("kejang demam", "febrile convulsion", "febrile seizure")) {
            val hr = 136 + varHr
            val rr = 28 + varRr
            val temp = 39.3 + varTemp
            return ValidatedVitals(td = "95/60 mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = 97)
        }

        if (match("campak", "morbili", "measles", "varisela", "varicella", "cacar air")) {
            val sys = if (age <= 5) 90 + varBpSys else 115 + varBpSys
            val dia = if (age <= 5) 55 + varBpDia else 75 + varBpDia
            val hr = if (age <= 5) 132 + varHr else 96 + varHr
            val rr = if (age <= 5) 26 + varRr else 20 + varRr
            val temp = 38.8 + varTemp
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = 98)
        }

        // =========================================================================
        // 5. PULMONOLOGI & GANGGUAN RESPIRATORIK
        // =========================================================================
        if (match("asma") && (match("berat", "eksaserbasi") || isEmergency)) {
            val sys = 130 + varBpSys
            val dia = 85 + varBpDia
            val hr = 118 + varHr
            val rr = 32 + varRr
            val temp = 37.0 + varTemp
            val spo2 = (90 + (hash % 3)).coerceIn(88, 92)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("asma", "asthma")) {
            val sys = 125 + varBpSys
            val dia = 80 + varBpDia
            val hr = 98 + varHr
            val rr = 24 + varRr
            val temp = 36.8 + varTemp
            val spo2 = (95 + (hash % 3)).coerceIn(94, 97)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("ppok", "chronic obstructive") || hasWord(primaryTarget, "copd")) {
            val sys = 140 + varBpSys
            val dia = 90 + varBpDia
            val hr = 110 + varHr
            val rr = 28 + varRr
            val temp = 37.3 + varTemp
            val spo2 = (88 + (hash % 4)).coerceIn(86, 91)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("pneumonia", "bronkopneumonia")) {
            if (age <= 5) {
                val sys = 90 + varBpSys
                val dia = 60 + varBpDia
                val hr = 142 + varHr
                val rr = 48 + varRr
                val temp = 38.9 + varTemp
                val spo2 = (91 + (hash % 3)).coerceIn(89, 93)
                return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
            } else {
                val sys = 115 + varBpSys
                val dia = 75 + varBpDia
                val hr = 104 + varHr
                val rr = 26 + varRr
                val temp = 38.8 + varTemp
                val spo2 = (92 + (hash % 3)).coerceIn(90, 94)
                return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
            }
        }

        if (match("tb paru", "tuberkulosis", "tbc paru") || hasWord(primaryTarget, "tb") || hasWord(primaryTarget, "tbc")) {
            val sys = 110 + varBpSys
            val dia = 70 + varBpDia
            val hr = 88 + varHr
            val rr = 20 + varRr
            val temp = 37.8 + varTemp
            val spo2 = 96
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("efusi pleura", "empiema", "pleural effusion")) {
            val sys = 120 + varBpSys
            val dia = 75 + varBpDia
            val hr = 106 + varHr
            val rr = 26 + varRr
            val temp = 38.2 + varTemp
            val spo2 = (92 + (hash % 3)).coerceIn(90, 94)
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("bronkiolitis") && age <= 2) {
            val hr = 146 + varHr
            val rr = 54 + varRr
            val temp = 38.2 + varTemp
            val spo2 = (91 + (hash % 3)).coerceIn(89, 93)
            return ValidatedVitals(td = "85/55 mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("croup", "laringotrakeobronkitis")) {
            val hr = 138 + varHr
            val rr = 36 + varRr
            val temp = 38.4 + varTemp
            val spo2 = (91 + (hash % 3)).coerceIn(90, 93)
            return ValidatedVitals(td = "90/55 mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 6. ENDOKRINOLOGI & METABOLIK
        // =========================================================================
        if (match("krisis tiroid", "thyroid storm", "badai tiroid")) {
            val sys = 165 + varBpSys
            val dia = 85 + varBpDia
            val hr = 156 + varHr
            val rr = 30 + varRr
            val temp = 39.8 + varTemp
            val spo2 = 95
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("tirotoksikosis", "graves", "hipertiroid", "struma toksik")) {
            val sys = 145 + varBpSys
            val dia = 80 + varBpDia
            val hr = 122 + varHr
            val rr = 22 + varRr
            val temp = 37.8 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("ketoasidosis", "diabetic ketoacidosis") || hasWord(primaryTarget, "kad") || hasWord(primaryTarget, "dka")) {
            val sys = 90 + varBpSys
            val dia = 60 + varBpDia
            val hr = 124 + varHr
            val rr = 34 + varRr // Kussmaul
            val temp = 37.8 + varTemp
            val spo2 = 97
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("hiperosmolar", "hyperosmolar") || hasWord(primaryTarget, "hhs") || hasWord(primaryTarget, "honk")) {
            val sys = 95 + varBpSys
            val dia = 60 + varBpDia
            val hr = 118 + varHr
            val rr = 24 + varRr
            val temp = 37.5 + varTemp
            val spo2 = 97
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("hipoglikemia", "hypoglycemia")) {
            val sys = 105 + varBpSys
            val dia = 65 + varBpDia
            val hr = 118 + varHr
            val rr = 22 + varRr
            val temp = 35.8 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("diabetes melitus", "dm tipe 2", "dm tipe 1", "dm tipe ii", "dm tipe i") || (match("diabetes") && !match("insipidus"))) {
            val sys = 130 + varBpSys
            val dia = 80 + varBpDia
            val hr = 80 + varHr
            val rr = 18 + varRr
            val temp = 36.7 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 7. GASTROENTEROLOGI & BEDAH ABDOMEN
        // =========================================================================
        if (match("peritonitis", "perforasi gaster", "perforasi usus", "defans muskular")) {
            val sys = 95 + varBpSys
            val dia = 60 + varBpDia
            val hr = 122 + varHr
            val rr = 26 + varRr
            val temp = 38.9 + varTemp
            val spo2 = 96
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("apendisitis", "appendisitis", "appendicitis")) {
            val sys = 120 + varBpSys
            val dia = 80 + varBpDia
            val hr = 98 + varHr
            val rr = 20 + varRr
            val temp = 38.3 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("kolesistitis", "kolangitis", "cholecystitis", "cholangitis")) {
            val sys = 125 + varBpSys
            val dia = 80 + varBpDia
            val hr = 106 + varHr
            val rr = 22 + varRr
            val temp = 38.8 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("pankreatitis", "pancreatitis")) {
            val sys = 110 + varBpSys
            val dia = 70 + varBpDia
            val hr = 116 + varHr
            val rr = 24 + varRr
            val temp = 38.2 + varTemp
            val spo2 = 96
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("diare", "gastroenteritis", "disentri", "kolera") && match("dehidrasi berat", "kolera")) {
            val sys = if (age <= 5) 75 + varBpSys else 85 + varBpSys
            val dia = if (age <= 5) 45 + varBpDia else 55 + varBpDia
            val hr = if (age <= 5) 150 + varHr else 128 + varHr
            val rr = if (age <= 5) 38 + varRr else 26 + varRr
            val temp = 38.4 + varTemp
            val spo2 = 96
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("diare", "gastroenteritis", "disentri", "geas")) {
            val sys = if (age <= 5) 90 + varBpSys else 115 + varBpSys
            val dia = if (age <= 5) 55 + varBpDia else 75 + varBpDia
            val hr = if (age <= 5) 124 + varHr else 94 + varHr
            val rr = if (age <= 5) 26 + varRr else 18 + varRr
            val temp = 38.3 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("abses hepar", "abses hati", "abses perianal")) {
            val sys = 120 + varBpSys
            val dia = 75 + varBpDia
            val hr = 102 + varHr
            val rr = 20 + varRr
            val temp = 38.9 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("ulkus peptikum", "gastritis", "gerd", "dispepsia", "maag")) {
            val sys = 120 + varBpSys
            val dia = 80 + varBpDia
            val hr = 82 + varHr
            val rr = 18 + varRr
            val temp = 36.7 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 8. NEFROLOGI & UROLOGI
        // =========================================================================
        if (match("pielonefritis", "pyelonefritis", "pyelonephritis")) {
            val sys = 115 + varBpSys
            val dia = 70 + varBpDia
            val hr = 108 + varHr
            val rr = 22 + varRr
            val temp = 39.2 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("kolik renal", "kolik ureter", "ureterolithiasis", "batu ginjal", "nefrolitiasis", "urolitiasis", "batu saluran kemih")) {
            val sys = 140 + varBpSys
            val dia = 90 + varBpDia
            val hr = 100 + varHr
            val rr = 22 + varRr
            val temp = 36.9 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("infeksi saluran kemih", "sistitis", "uretritis", "prostatitis") || hasWord(primaryTarget, "isk")) {
            val sys = 120 + varBpSys
            val dia = 80 + varBpDia
            val hr = 88 + varHr
            val rr = 18 + varRr
            val temp = 38.2 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("sindrom nefrotik", "glomerulonefritis", "gnaps")) {
            val sys = if (age <= 10) 125 + varBpSys else 155 + varBpSys
            val dia = if (age <= 10) 85 + varBpDia else 95 + varBpDia
            val hr = if (age <= 10) 96 + varHr else 84 + varHr
            val rr = 20 + varRr
            val temp = 36.8 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 9. OBSTETRI & GINEKOLOGI
        // =========================================================================
        if (match("mastitis", "abses payudara", "sepsis puerperalis", "endometritis")) {
            val sys = 120 + varBpSys
            val dia = 75 + varBpDia
            val hr = 106 + varHr
            val rr = 20 + varRr
            val temp = 38.8 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("pelvic inflammatory", "radang panggul", "salpingitis", "bartholinitis") || hasWord(primaryTarget, "pid")) {
            val sys = 120 + varBpSys
            val dia = 80 + varBpDia
            val hr = 98 + varHr
            val rr = 18 + varRr
            val temp = 38.4 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 10. REUMATOLOGI, MUSKULOSKELETAL & IMUNOLOGI
        // =========================================================================
        if (match("gout", "podagra", "asam urat", "artritis gout")) {
            val sys = 135 + varBpSys
            val dia = 85 + varBpDia
            val hr = 92 + varHr
            val rr = 18 + varRr
            val temp = 37.4 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("artritis septik", "septic arthritis", "osteomielitis")) {
            val sys = 120 + varBpSys
            val dia = 75 + varBpDia
            val hr = 105 + varHr
            val rr = 20 + varRr
            val temp = 39.2 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("lupus", "systemic lupus") || hasWord(primaryTarget, "sle")) {
            val sys = 145 + varBpSys
            val dia = 95 + varBpDia
            val hr = 102 + varHr
            val rr = 22 + varRr
            val temp = 38.3 + varTemp
            val spo2 = 97
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("reumatoid", "rheumatoid", "osteoartritis", "osteoarthritis")) {
            val sys = 130 + varBpSys
            val dia = 80 + varBpDia
            val hr = 82 + varHr
            val rr = 18 + varRr
            val temp = 36.8 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 11. DERMATOVENEROLOGI (KULIT & KELAMIN)
        // =========================================================================
        if (match("stevens-johnson", "nekrolisis epidermal", "toxic epidermal") || hasWord(primaryTarget, "sjs") || hasWord(primaryTarget, "ten")) {
            val sys = 95 + varBpSys
            val dia = 60 + varBpDia
            val hr = 118 + varHr
            val rr = 24 + varRr
            val temp = 39.1 + varTemp
            val spo2 = 96
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("selulitis", "erisipelas", "furunkel", "karbunkel", "abses kulit", "impetigo", "folikulitis")) {
            val sys = 125 + varBpSys
            val dia = 80 + varBpDia
            val hr = 98 + varHr
            val rr = 18 + varRr
            val temp = 38.5 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("herpes zoster", "cacar ular")) {
            val sys = 135 + varBpSys
            val dia = 85 + varBpDia
            val hr = 90 + varHr
            val rr = 18 + varRr
            val temp = 37.6 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("lepra", "morbus hansen", "eritema nodosum leprosum", "kusta") || hasWord(primaryTarget, "enl")) {
            val sys = 120 + varBpSys
            val dia = 80 + varBpDia
            val hr = 104 + varHr
            val rr = 20 + varRr
            val temp = 38.7 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 12. THT-KL & MATA
        // =========================================================================
        if (match("abses peritonsil", "quinsy")) {
            val sys = 125 + varBpSys
            val dia = 80 + varBpDia
            val hr = 106 + varHr
            val rr = 20 + varRr
            val temp = 38.9 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("otitis media", "otitis media akut", "mastoiditis") || hasWord(primaryTarget, "oma") || hasWord(primaryTarget, "omsk")) {
            val sys = if (age <= 10) 100 + varBpSys else 120 + varBpSys
            val dia = if (age <= 10) 65 + varBpDia else 80 + varBpDia
            val hr = if (age <= 10) 100 + varHr else 86 + varHr
            val rr = 20 + varRr
            val temp = 38.5 + varTemp
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = 99)
        }

        if (match("tonsilitis", "faringitis", "laringitis", "tonsilofaringitis", "ispa")) {
            val sys = if (age <= 10) 100 + varBpSys else 120 + varBpSys
            val dia = if (age <= 10) 65 + varBpDia else 80 + varBpDia
            val hr = if (age <= 10) 102 + varHr else 86 + varHr
            val rr = 18 + varRr
            val temp = 38.5 + varTemp
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = 98)
        }

        if (match("sinusitis", "rhinosinusitis")) {
            val sys = 120 + varBpSys
            val dia = 80 + varBpDia
            val hr = 86 + varHr
            val rr = 18 + varRr
            val temp = 38.0 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = 99)
        }

        if (match("glaukoma", "glaucoma")) {
            val sys = 155 + varBpSys
            val dia = 95 + varBpDia
            val hr = 98 + varHr
            val rr = 20 + varRr
            val temp = 36.8 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = 98)
        }

        if (match("ulkus kornea", "endoftalmitis", "selulitis orbita", "keratitis")) {
            val sys = 120 + varBpSys
            val dia = 80 + varBpDia
            val hr = 92 + varHr
            val rr = 18 + varRr
            val temp = 38.1 + varTemp
            val spo2 = 99
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = 99)
        }

        // =========================================================================
        // 13. PSIKIATRI & JIWA
        // =========================================================================
        if (match("serangan panik", "panic attack", "gangguan panik", "hiperventilasi")) {
            val sys = 145 + varBpSys
            val dia = 90 + varBpDia
            val hr = 128 + varHr
            val rr = 34 + varRr
            val temp = 36.6 + varTemp
            val spo2 = 100
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        if (match("skizofrenia", "gaduh gelisah", "psikotik", "psikosis", "mania", "bipolar")) {
            val sys = 142 + varBpSys
            val dia = 92 + varBpDia
            val hr = 114 + varHr
            val rr = 22 + varRr
            val temp = 37.0 + varTemp
            val spo2 = 98
            return ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(temp), spO2 = spo2)
        }

        // =========================================================================
        // 14. GENERAL REASONING FOR FEVER & SYMPTOMS IN ANY CUSTOM / UNLISTED CASE
        // =========================================================================
        val complaintLower = chiefComplaint.lowercase()
        val hasFever = combinedTarget.contains("demam") || combinedTarget.contains("febris") || combinedTarget.contains("panas") ||
                complaintLower.contains("demam") || complaintLower.contains("panas") || complaintLower.contains("menggigil")

        if (hasFever) {
            val feverTemp = roundTemp(38.6 + varTemp)
            val feverNadi = when {
                age <= 1 -> 142 + varHr
                age <= 5 -> 132 + varHr
                age <= 11 -> 106 + varHr
                else -> 98 + varHr
            }
            val feverRr = when {
                age <= 1 -> 36 + varRr
                age <= 5 -> 28 + varRr
                else -> 20 + varRr
            }
            val feverSys = when {
                age <= 1 -> 85 + varBpSys
                age <= 3 -> 92 + varBpSys
                age <= 11 -> 102 + varBpSys
                else -> 120 + varBpSys
            }
            val feverDia = when {
                age <= 1 -> 55 + varBpDia
                age <= 3 -> 60 + varBpDia
                age <= 11 -> 65 + varBpDia
                else -> 80 + varBpDia
            }
            return ValidatedVitals(td = "$feverSys/$feverDia mmHg", nadi = feverNadi, rr = feverRr, suhu = feverTemp, spO2 = 98)
        }

        val hasDyspnea = combinedTarget.contains("sesak") || combinedTarget.contains("dispnea") || combinedTarget.contains("mengi") ||
                complaintLower.contains("sesak") || complaintLower.contains("napas cepat")

        if (hasDyspnea) {
            val dyspneaRr = if (age <= 5) 44 + varRr else 28 + varRr
            val dyspneaHr = if (age <= 5) 136 + varHr else 108 + varHr
            val dyspneaSys = if (age <= 5) 90 + varBpSys else 130 + varBpSys
            val dyspneaDia = if (age <= 5) 58 + varBpDia else 85 + varBpDia
            val spo2 = (93 + (hash % 3)).coerceIn(91, 95)
            return ValidatedVitals(td = "$dyspneaSys/$dyspneaDia mmHg", nadi = dyspneaHr, rr = dyspneaRr, suhu = roundTemp(36.8 + varTemp), spO2 = spo2)
        }

        // =========================================================================
        // 15. POLIKLINIK RAWAT JALAN / PEDIATRI / NON-EMERGENSI UMUM (AGE CALIBRATED)
        // =========================================================================
        return when {
            age <= 1 -> {
                val sys = 82 + varBpSys
                val dia = 52 + varBpDia
                val hr = 124 + varHr
                val rr = 32 + varRr
                ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(36.8 + varTemp), spO2 = 98)
            }
            age <= 3 -> {
                val sys = 90 + varBpSys
                val dia = 60 + varBpDia
                val hr = 110 + varHr
                val rr = 26 + varRr
                ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(36.8 + varTemp), spO2 = 98)
            }
            age <= 11 -> {
                val sys = 100 + varBpSys
                val dia = 65 + varBpDia
                val hr = 88 + varHr
                val rr = 20 + varRr
                ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(36.7 + varTemp), spO2 = 99)
            }
            age >= 60 -> {
                val sys = 135 + varBpSys
                val dia = 85 + varBpDia
                val hr = 76 + varHr
                val rr = 18 + varRr
                ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(36.6 + varTemp), spO2 = 98)
            }
            else -> {
                val sys = 120 + varBpSys
                val dia = 80 + varBpDia
                val hr = 78 + varHr
                val rr = 18 + varRr
                ValidatedVitals(td = "$sys/$dia mmHg", nadi = hr, rr = rr, suhu = roundTemp(36.6 + varTemp), spO2 = 99)
            }
        }
    }

    private fun roundTemp(t: Double): Double {
        return (Math.round(t * 10.0) / 10.0).coerceIn(34.0, 42.0)
    }

    fun parseSystolic(tdStr: String): Int {
        val clean = tdStr.replace(Regex("[^0-9/]"), "").trim()
        val parts = clean.split("/")
        return parts.firstOrNull()?.toIntOrNull() ?: 120
    }

    fun parseDiastolic(tdStr: String): Int {
        val clean = tdStr.replace(Regex("[^0-9/]"), "").trim()
        val parts = clean.split("/")
        return if (parts.size > 1) parts[1].toIntOrNull() ?: 80 else 80
    }
}
