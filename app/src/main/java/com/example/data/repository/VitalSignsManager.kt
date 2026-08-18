package com.example.data.repository

import androidx.compose.ui.graphics.Color
import com.example.data.model.CitoActionFeedback
import com.example.data.model.CitoImpactType
import com.example.data.model.ClinicalCase
import com.example.data.model.UserExamResult
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

/**
 * Hemodynamic Pathology Profile representing simulated patient physiology.
 */
enum class PathologyProfile(val displayName: String) {
    ANAPHYLAXIS("Syok Anafilaktik (Vasodilatasi & Capillary Leak)"),
    CARDIOGENIC_SHOCK_ADHF("Gagal Jantung Akut / Edema Paru / Syok Kardiogenik"),
    HYPOVOLEMIC_SHOCK("Syok Hipovolemik / Perdarahan Masif"),
    SEPTIC_SHOCK("Syok Septik (Vasodilatasi Sistemik)"),
    TENSION_PNEUMOTHORAX("Tension Pneumothorax (Obstruktif)"),
    ASTHMA_COPD("Eksaserbasi Asma / PPOK (Bronkospasme Berat)"),
    HYPOGLYCEMIA("Koma Hipoglikemia"),
    HYPERTENSIVE_CRISIS("Krisis Hipertensi / Ensefalopati"),
    SEIZURE_ECLAMPSIA("Preeklampsia Berat / Eklampsia / Kejang"),
    ARREST_ARRHYTHMIA("Henti Jantung / Aritmia Lethal (VF/VT/Asystole)"),
    NEUROLOGIC_TKB("Cedera Kepala / TIK Meningkat"),
    NEUTRAL("Kondisi Hemodinamik Standar")
}

/**
 * Full computed hemodynamic and vital sign output structure.
 */
data class ComputedVitals(
    val systolic: Int,
    val diastolic: Int,
    val hr: Int,
    val rr: Int,
    val temp: Double,
    val spO2: Int,
    val map: Int,                   // Mean Arterial Pressure (mmHg)
    val shockIndex: Double,         // HR / Systolic BP ratio
    val statusText: String,
    val statusColor: Color,
    val trendNote: String,
    val activeInterventions: List<String>,
    val pathologyProfile: PathologyProfile,
    val physiologicalResponseNote: String
)

data class CitoStabilizationGuide(
    val pathologyName: String,
    val isTtvStabilized: Boolean,
    val summaryTtvText: String,
    val primaryCitoActions: List<String>,
    val rationale: String
)

/**
 * Centralized Service Manager for calculating real-time dynamic hemodynamic responses
 * and physiological vital sign adjustments based on simulated pathologies and interventions.
 */
object VitalSignsManager {

    /**
     * Get Cito stabilization guide and recommended interventions for a clinical case.
     */
    fun getCitoStabilizationGuide(
        case: ClinicalCase,
        citoLogs: List<CitoActionFeedback> = emptyList()
    ): CitoStabilizationGuide {
        val vitals = computeVitals(case = case, citoActionLogs = citoLogs, isEmergencyMode = true)
        val pathology = detectPathologyProfile(case)
        val hasStabilizedAction = citoLogs.any { it.impactType == CitoImpactType.STABILIZED }
        val isTtvStabilized = hasStabilizedAction && vitals.systolic > 0 && vitals.hr > 0 && vitals.spO2 >= 92 && vitals.systolic >= 90

        val (primaryActions, rationale) = when (pathology) {
            PathologyProfile.ANAPHYLAXIS -> Pair(
                listOf(
                    "⚡ Epinefrin / Adrenalin 0.3 - 0.5 mg IM Stat (paha anterolateral, ulangi tiap 5-15 mnt)",
                    "🫁 Oksigenasi High Flow NRM 10-15 L/m (target SpO2 > 95%)",
                    "💧 Resusitasi Cairan Kristaloid (RL / NaCl 0.9%) 1000 ml IV grojok cepat",
                    "💉 Diphenhydramine 50 mg IV & Deksametason / Metilprednisolon IV Cito"
                ),
                "Epinefrin merangsang alfa-1 vasokonstriksi (naikkan TD & MAP) & beta-2 bronkodilatasi (pulihkan SpO2 & turunkan RR/HR)."
            )

            PathologyProfile.CARDIOGENIC_SHOCK_ADHF -> Pair(
                listOf(
                    "🫁 Oksigenasi High Flow NRM 10-15 L/m / CPAP",
                    "💉 Furosemid 40-80 mg IV Stat untuk dekongesti edema paru",
                    "💊 Inotropik / Vasopresor (Dobutamin 2.5-20 mcg/kg/m atau Vascon IV Drip)",
                    "🧘 Posisi Fowler (duduk 60-90 derajat) kurangi venous return ke jantung"
                ),
                "Dekongesti paru & inotropik kurangi beban miokard, tingkatkan cardiac output, perbaiki SpO2, RR, TD, dan Nadi."
            )

            PathologyProfile.TENSION_PNEUMOTHORAX -> Pair(
                listOf(
                    "📌 Needle Thoracocentesis / Dekompresi Jarum Cito (ICS II Linea Midklavikula)",
                    "🩺 Pemasangan Chest Tube / WSD (Water Sealed Drainage) Cito",
                    "🫁 Oksigenasi High Flow NRM 10-15 L/m",
                    "⚠️ JANGAN intubasi BVM tekanan positif sebelum dekompresi jarum!"
                ),
                "Dekompresi jarum rilis udara intratorakal bertekanan, membebaskan vena kava, melonjakkan TD, SpO2 & menormalkan Nadi/RR."
            )

            PathologyProfile.HYPOVOLEMIC_SHOCK -> Pair(
                listOf(
                    "💧 Resusitasi Cairan Kristaloid (RL / NaCl 0.9%) 1000-2000 ml IV grojok cepat (2 jalur IV 14-16G)",
                    "🩸 Transfusi Darah Cito (PRC / Whole Blood) jika perdarahan masif",
                    "🩸 Injeksi Asam Traneksamat (TXA) 1 Gram IV Cito + Balut Tekan / Tourniquet",
                    "🫁 Oksigenasi NRM 10-15 L/m & Posisi Trendelenburg"
                ),
                "Mengganti intravascular volume untuk mengembalikan preload, menaikkan TD/MAP, menurunkan Nadi takikardia & memulihkan SpO2."
            )

            PathologyProfile.ASTHMA_COPD -> Pair(
                listOf(
                    "💨 Nebulisasi Salbutamol (Ventolin) + Ipratropium Bromida Cito",
                    "💉 Kortikosteroid IV (Metilprednisolon 62.5 mg / Deksametason 10 mg IV)",
                    "🫁 Oksigenasi NRM / Kanul Nasal (target SpO2 93-95%)",
                    "💊 Magnesium Sulfat (MgSO4) 2 Gram IV Drip pelan jika berat"
                ),
                "Mereversi bronkospasme hebat, melonjakkan SpO2, menurunkan frekuensi napas (RR), serta menormalkan Nadi & Tekanan Darah."
            )

            PathologyProfile.HYPOGLYCEMIA -> Pair(
                listOf(
                    "💉 Bolus Dextrose 40% (D40) 2-4 Ampul (25-50 ml) IV Cito",
                    "💧 Maintenance Infus Dextrose 10% Drip 12-20 tpm",
                    "📊 Re-evaluasi Gula Darah Sewaktu (GDS) 15 menit pasca injeksi",
                    "🧠 Posisikan miring mantap jika penurunan kesadaran"
                ),
                "Mengembalikan euglikemia otak, menghentikan reaktif adrenergik, menormalkan Nadi, RR, SpO2, dan Tekanan Darah."
            )

            PathologyProfile.HYPERTENSIVE_CRISIS -> Pair(
                listOf(
                    "💉 Antihipertensi Parenteral IV Drip Cito (Nicardipine 5 mg/jam / Labetalol / Diltiazem)",
                    "📊 Target Penurunan MAP 15-20% bertahap dalam 1 jam pertama",
                    "🧠 Monitor tanda ensefalopati & defisit neurologis"
                ),
                "Penurunan TD & MAP secara terkontrol mengurangi afterload jantung, menormalkan Nadi, RR, dan perfusi organ."
            )

            PathologyProfile.SEIZURE_ECLAMPSIA -> Pair(
                listOf(
                    "💉 Injeksi MgSO4 20%/40% 4 Gram IV Drip Cito (pada eklampsia) / Diazepam 10mg IV",
                    "🛡️ Proteksi jalan napas, pasang OPA, miringkan tubuh",
                    "🫁 Oksigenasi Kanul Nasal 3-4 L/m",
                    "💊 Antihipertensi IV jika TD Sistolik >= 160 mmHg"
                ),
                "Menghentikan kejang & eksitasi CNS, memulihkan ventilasi (RR & SpO2), serta mengendalikan Nadi & Tekanan Darah."
            )

            PathologyProfile.ARREST_ARRHYTHMIA -> Pair(
                listOf(
                    "❤️ Resusitasi Jantung Paru (RJP / CPR High Quality 30:2) Cito",
                    "⚡ Defibrilasi / Kejut Listrik 200 Joule Bifasik jika VF / Pulseless VT",
                    "💉 Injeksi Epinefrin 1 mg IV Cito tiap 3-5 menit + Amiodaron 300 mg IV",
                    "🫁 Amankan jalan napas definitif (ETT / LMA) + Bagging"
                ),
                "CPR & Defibrilasi mengembalikan Irama Sinus Normal (ROSC), memicu kembali TD, Nadi, SpO2, dan Napas otomatis."
            )

            PathologyProfile.NEUROLOGIC_TKB -> Pair(
                listOf(
                    "📐 Head-Up 30 Derajat & Cervical Collar / Neck Collar Cito",
                    "💉 Osmo-terapi Mannitol 20% 200 ml IV Drip Cito untuk edema otak",
                    "🫁 Intubasi ETT Cito & Oksigenasi jika GCS <= 8",
                    "💧 Pertahankan MAP > 80 mmHg dengan cairan kristaloid"
                ),
                "Menurunkan Tekanan Intra Kranial (TIK), mereversi Trias Cushing (menormalkan Nadi bradikardia, TD & RR)."
            )

            else -> Pair(
                listOf(
                    "💧 Akses Vena Perifer 2 Line & Infus Kristaloid RL / NaCl 0.9%",
                    "🫁 Suplementasi Oksigen Nasal Kanul / NRM sesuai saturasi",
                    "📊 Pemantauan Tanda-Tanda Vital (TTV) kontinu & Tirah baring"
                ),
                "Menjaga kecukupan perfusi jaringan dan stabilitas kardiovaskular dasar."
            )
        }

        val summaryTtv = "TD: ${vitals.systolic}/${vitals.diastolic} mmHg | Nadi: ${vitals.hr} x/mnt | RR: ${vitals.rr} x/mnt | Suhu: ${vitals.temp}°C | SpO2: ${vitals.spO2}% | MAP: ${vitals.map} mmHg | Shock Index: ${String.format("%.2f", vitals.shockIndex)}"

        return CitoStabilizationGuide(
            pathologyName = pathology.displayName,
            isTtvStabilized = isTtvStabilized,
            summaryTtvText = summaryTtv,
            primaryCitoActions = primaryActions,
            rationale = rationale
        )
    }

    private fun isFluidResuscitation(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("kristaloid") || t.contains("fluid") || t.contains("infus") ||
                t.contains("rl") || t.contains("ringer") || t.contains("nacl") ||
                t.contains("cairan") || t.contains("resusitasi") || t.contains("transfusi") ||
                t.contains("prc") || t.contains("blood") || t.contains("darah")
    }

    private fun isEpinephrine(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("epinefrin") || t.contains("adrenalin") || t.contains("epinephrine")
    }

    private fun isOxygen(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("oksigen") || t.contains("o2") || t.contains("nrm") ||
                t.contains("nasal") || t.contains("kanul") || t.contains("cpap") ||
                t.contains("masker") || t.contains("inhalasi") || t.contains("ventilasi")
    }

    private fun isDecompression(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("needle") || t.contains("dekompresi") || t.contains("jarum") ||
                t.contains("wsd") || t.contains("chest tube") || t.contains("torasentesis")
    }

    private fun isBronchodilatorOrSteroid(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("nebulizer") || t.contains("salbutamol") || t.contains("ventolin") ||
                t.contains("combivent") || t.contains("ipratropium") || t.contains("steroid") ||
                t.contains("metilprednisolon") || t.contains("deksametason") || t.contains("dexamethasone") ||
                t.contains("mgso4") || t.contains("magnesium")
    }

    private fun isDextrose(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("dextrose") || t.contains("d40") || t.contains("d10") ||
                t.contains("d5") || t.contains("glukosa") || t.contains("gds")
    }

    private fun isAntihypertensive(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("nicardipine") || t.contains("labetalol") || t.contains("diltiazem") ||
                t.contains("nifedipine") || t.contains("nitroprusside") || t.contains("esmolol")
    }

    private fun isAnticonvulsant(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("mgso4") || t.contains("magnesium") || t.contains("diazepam") ||
                t.contains("midazolam") || t.contains("phenobarbital") || t.contains("phenytoin")
    }

    private fun isInotropeVasopressorDiuretic(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("vascon") || t.contains("norepinephrine") || t.contains("norepinefrin") ||
                t.contains("dopamine") || t.contains("dobutamine") || t.contains("inotropik") ||
                t.contains("vasopresor") || t.contains("furosemid") || t.contains("lasix") || t.contains("diuretik")
    }

    private fun isArrestProcedure(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("defibrilasi") || t.contains("kejut") || t.contains("rjp") ||
                t.contains("cpr") || t.contains("amiodaron")
    }

    private fun isOsmotherapy(title: String): Boolean {
        val t = title.lowercase()
        return t.contains("mannitol") || t.contains("manitol") || t.contains("hypertonic")
    }

    /**
     * Parse blood pressure string format "120/80" safely.
     */
    fun parseTd(tdString: String): Pair<Int, Int> {
        return try {
            val digits = Regex("""\d+""").findAll(tdString).map { it.value.toInt() }.toList()
            if (digits.size >= 2) Pair(digits[0], digits[1]) else Pair(120, 80)
        } catch (e: Exception) {
            Pair(120, 80)
        }
    }

    /**
     * Determine the underlying physiological pathology profile for a clinical case.
     */
    fun detectPathologyProfile(case: ClinicalCase): PathologyProfile {
        val diag = case.trueDiagnosis.lowercase()
        val complaint = case.chiefComplaint.lowercase()
        val title = case.title.lowercase()
        val sys = parseTd(case.td).first

        return when {
            diag.contains("anafilak") || complaint.contains("anafilak") || title.contains("anafilak") ->
                PathologyProfile.ANAPHYLAXIS

            diag.contains("gagal jantung") || diag.contains("adhf") || complaint.contains("edema paru") ||
                    diag.contains("kardiogenik") || complaint.contains("rhonchi basah") ->
                PathologyProfile.CARDIOGENIC_SHOCK_ADHF

            diag.contains("pneumothorax") || complaint.contains("pneumothorax") || complaint.contains("suara napas menghilang") ->
                PathologyProfile.TENSION_PNEUMOTHORAX

            diag.contains("perdarahan") || diag.contains("hipovolemik") || complaint.contains("pendarahan") ||
                    diag.contains("dengue") || diag.contains("dbd") || complaint.contains("syok") && sys < 90 ->
                PathologyProfile.HYPOVOLEMIC_SHOCK

            diag.contains("sepsis") || diag.contains("septik") ->
                PathologyProfile.SEPTIC_SHOCK

            diag.contains("asma") || diag.contains("ppok") || complaint.contains("mengi") || complaint.contains("wheezing") ->
                PathologyProfile.ASTHMA_COPD

            diag.contains("hipoglikemia") || complaint.contains("hipoglikemia") || complaint.contains("keringat dingin") ->
                PathologyProfile.HYPOGLYCEMIA

            diag.contains("krisis hipertensi") || diag.contains("hipertensi emergensi") || sys >= 180 ->
                PathologyProfile.HYPERTENSIVE_CRISIS

            diag.contains("eklampsia") || diag.contains("preeklampsia") || complaint.contains("kejang") ->
                PathologyProfile.SEIZURE_ECLAMPSIA

            diag.contains("henti jantung") || diag.contains("vf") || diag.contains("vt") || diag.contains("asistol") ->
                PathologyProfile.ARREST_ARRHYTHMIA

            diag.contains("tkb") || diag.contains("trauma kepala") || diag.contains("edema serebri") ->
                PathologyProfile.NEUROLOGIC_TKB

            else -> PathologyProfile.NEUTRAL
        }
    }

    /**
     * Calculate comprehensive real-time vitals and hemodynamic responses.
     */
    fun computeVitals(
        case: ClinicalCase,
        citoActionLogs: List<CitoActionFeedback>,
        userExams: List<UserExamResult> = emptyList(),
        treatmentInput: String = "",
        isEmergencyMode: Boolean = false,
        remainingSeconds: Int = 180
    ): ComputedVitals {
        val (baseSystolic, baseDiastolic) = parseTd(case.td)
        var sys = baseSystolic
        var dia = baseDiastolic
        var hr = case.nadi
        var rr = case.rr
        var temp = case.suhu
        var spO2 = case.spO2

        val pathology = detectPathologyProfile(case)
        val activeInterventions = mutableListOf<String>()
        var trendNote = ""
        var responseNote = ""

        // 1. Process explicit CITO Actions and apply pathology-aware dynamic hemodynamic physics
        for (log in citoActionLogs) {
            val titleLower = log.actionTitle.lowercase()

            if (log.isAiEvaluated && log.targetSystolic != null) {
                when (log.impactType) {
                    CitoImpactType.FATAL_COLLAPSE -> {
                        sys = 0
                        dia = 0
                        hr = 0
                        spO2 = 0
                        rr = 0
                        activeInterventions.add("🚨 ${log.actionTitle}")
                        trendNote = "🚨 FATAL CARDIAC ARREST: Pasien henti sirkulasi!"
                        responseNote = log.updatedVitalsNote.ifBlank { "Henti jantung dan nafas total (Asystole/PEA)." }
                    }
                    CitoImpactType.STABILIZED -> {
                        sys = log.targetSystolic
                        dia = log.targetDiastolic ?: dia
                        hr = log.targetHr ?: hr
                        rr = log.targetRr ?: rr
                        spO2 = log.targetSpO2 ?: spO2
                        temp = log.targetTemp ?: temp
                        activeInterventions.add("⚡ ${log.actionTitle}")
                        trendNote = "⚡ AI: Vital sign membaik signifikan (${log.updatedVitalsNote})"
                        responseNote = log.updatedVitalsNote.ifBlank { "TTV distabilkan secara manual oleh perhitungan AI." }
                    }
                    CitoImpactType.HARMFUL -> {
                        sys = log.targetSystolic
                        dia = log.targetDiastolic ?: dia
                        hr = log.targetHr ?: hr
                        rr = log.targetRr ?: rr
                        spO2 = log.targetSpO2 ?: spO2
                        temp = log.targetTemp ?: temp
                        activeInterventions.add("⚠️ ${log.actionTitle}")
                        trendNote = "⚠️ AI: Vital sign memburuk (${log.updatedVitalsNote})"
                        responseNote = log.updatedVitalsNote.ifBlank { "TTV memburuk akibat komplikasi/kontraindikasi." }
                    }
                    CitoImpactType.UNINDICATED -> {
                        activeInterventions.add("ℹ️ ${log.actionTitle}")
                        trendNote = "ℹ️ AI: Tatalaksana tidak diindikasikan."
                        responseNote = log.updatedVitalsNote.ifBlank { "Intervensi tidak mengubah hemodinamik secara bermakna." }
                    }
                }
                continue
            }

            when (log.impactType) {
                CitoImpactType.STABILIZED -> {
                    activeInterventions.add("⚡ ${log.actionTitle}")

                    when (pathology) {
                        PathologyProfile.ANAPHYLAXIS -> {
                            if (isEpinephrine(titleLower)) {
                                sys = (sys + 35).coerceAtMost(120)
                                dia = (dia + 22).coerceAtMost(80)
                                hr = (hr - 25).coerceAtLeast(76)
                                rr = (rr - 10).coerceAtLeast(16)
                                spO2 = (spO2 + 15).coerceAtMost(99)
                                responseNote = "Epinefrin vasokonstriksi alfa-1 & bronkodilatasi beta-2: memulihkan TD, SpO2, Nadi, dan RR."
                            } else if (isFluidResuscitation(titleLower)) {
                                sys = (sys + 22).coerceAtMost(115)
                                dia = (dia + 14).coerceAtMost(75)
                                hr = (hr - 18).coerceAtLeast(78)
                                rr = (rr - 6).coerceAtLeast(18)
                                spO2 = (spO2 + 8).coerceAtMost(98)
                                responseNote = "Resusitasi cairan kristaloid mengisi intravascular volume yang bocor."
                            } else if (isOxygen(titleLower)) {
                                spO2 = (spO2 + 10).coerceAtMost(99)
                                rr = (rr - 6).coerceAtLeast(18)
                                hr = (hr - 12).coerceAtLeast(80)
                                sys = (sys + 8).coerceAtMost(110)
                                dia = (dia + 5).coerceAtMost(70)
                            } else {
                                sys = (sys + 12).coerceAtMost(115)
                                dia = (dia + 8).coerceAtMost(75)
                                hr = (hr - 12).coerceAtLeast(78)
                                rr = (rr - 6).coerceAtLeast(18)
                                spO2 = (spO2 + 8).coerceAtMost(98)
                            }
                        }

                        PathologyProfile.CARDIOGENIC_SHOCK_ADHF -> {
                            if (isInotropeVasopressorDiuretic(titleLower)) {
                                spO2 = (spO2 + 14).coerceAtMost(98)
                                rr = (rr - 8).coerceAtLeast(16)
                                hr = (hr - 20).coerceAtLeast(76)
                                sys = if (sys < 90) (sys + 22).coerceAtMost(120) else (sys - 20).coerceAtLeast(120)
                                dia = if (dia < 60) (dia + 12).coerceAtMost(80) else (dia - 12).coerceAtLeast(80)
                                responseNote = "Dekongesti paru & inotropik meningkatkan cardiac output serta kurangi dekompensasi."
                            } else if (isOxygen(titleLower)) {
                                spO2 = (spO2 + 10).coerceAtMost(98)
                                rr = (rr - 6).coerceAtLeast(18)
                                hr = (hr - 12).coerceAtLeast(80)
                                sys = (sys + 8).coerceIn(90, 130)
                                dia = (dia + 5).coerceIn(60, 85)
                            } else {
                                spO2 = (spO2 + 8).coerceAtMost(96)
                                rr = (rr - 4).coerceAtLeast(18)
                                hr = (hr - 10).coerceAtLeast(80)
                            }
                        }

                        PathologyProfile.TENSION_PNEUMOTHORAX -> {
                            if (isDecompression(titleLower)) {
                                sys = (sys + 45).coerceAtMost(120)
                                dia = (dia + 28).coerceAtMost(80)
                                hr = (hr - 35).coerceAtLeast(76)
                                rr = (rr - 14).coerceAtLeast(16)
                                spO2 = (spO2 + 22).coerceAtMost(99)
                                responseNote = "Dekompresi toraks membebaskan vena kava superior/inferior, memulihkan venous return!"
                            } else if (isOxygen(titleLower)) {
                                spO2 = (spO2 + 10).coerceAtMost(95)
                                rr = (rr - 6).coerceAtLeast(22)
                                hr = (hr - 14).coerceAtLeast(90)
                                sys = (sys + 10).coerceAtMost(95)
                                dia = (dia + 6).coerceAtMost(60)
                            }
                        }

                        PathologyProfile.HYPOVOLEMIC_SHOCK -> {
                            if (isFluidResuscitation(titleLower) || titleLower.contains("txa") || titleLower.contains("tourniquet")) {
                                sys = (sys + 30).coerceAtMost(118)
                                dia = (dia + 18).coerceAtMost(78)
                                hr = (hr - 30).coerceAtLeast(72)
                                rr = (rr - 10).coerceAtLeast(16)
                                spO2 = (spO2 + 10).coerceAtMost(99)
                                responseNote = "Resusitasi cairan/darah mengembalikan volume intravaskular, preload jantung, dan MAP."
                            } else if (isOxygen(titleLower)) {
                                spO2 = (spO2 + 8).coerceAtMost(96)
                                rr = (rr - 4).coerceAtLeast(20)
                                hr = (hr - 12).coerceAtLeast(88)
                                sys = (sys + 8).coerceAtMost(95)
                                dia = (dia + 5).coerceAtMost(62)
                            } else {
                                sys = (sys + 15).coerceAtMost(110)
                                dia = (dia + 10).coerceAtMost(72)
                                hr = (hr - 15).coerceAtLeast(80)
                                rr = (rr - 6).coerceAtLeast(18)
                                spO2 = (spO2 + 6).coerceAtMost(98)
                            }
                        }

                        PathologyProfile.ASTHMA_COPD -> {
                            if (isBronchodilatorOrSteroid(titleLower) || isOxygen(titleLower)) {
                                spO2 = (spO2 + 16).coerceAtMost(99)
                                rr = (rr - 12).coerceAtLeast(16)
                                hr = (hr - 22).coerceAtLeast(76)
                                sys = (sys + 12).coerceIn(105, 125)
                                dia = (dia + 8).coerceIn(65, 82)
                                responseNote = "Bronkodilator merelaksasikan otot polos bronkus, meningkatkan ventilasi alveolus."
                            }
                        }

                        PathologyProfile.HYPOGLYCEMIA -> {
                            if (isDextrose(titleLower)) {
                                hr = (hr - 25).coerceAtLeast(72)
                                sys = (sys + 15).coerceIn(105, 125)
                                dia = (dia + 10).coerceIn(70, 82)
                                rr = (rr - 8).coerceAtLeast(16)
                                spO2 = (spO2 + 8).coerceAtMost(99)
                                responseNote = "Pemberian Dextrose D40 IV mengembalikan euglikemia otak & nada otonom."
                            }
                        }

                        PathologyProfile.HYPERTENSIVE_CRISIS -> {
                            if (isAntihypertensive(titleLower)) {
                                sys = (sys - 35).coerceAtLeast(135)
                                dia = (dia - 20).coerceAtLeast(85)
                                hr = (hr - 16).coerceAtLeast(72)
                                rr = (rr - 6).coerceAtLeast(16)
                                spO2 = (spO2 + 5).coerceAtMost(99)
                                responseNote = "Vasodilator parenteral menurunkan MAP 15-20% secara terkontrol tanpa hipoperfusi."
                            }
                        }

                        PathologyProfile.SEIZURE_ECLAMPSIA -> {
                            if (isAnticonvulsant(titleLower)) {
                                rr = (rr - 12).coerceAtLeast(16)
                                spO2 = (spO2 + 15).coerceAtMost(99)
                                sys = (sys - 25).coerceAtLeast(125)
                                dia = (dia - 15).coerceAtLeast(80)
                                hr = (hr - 22).coerceAtLeast(74)
                                responseNote = "Magnesium Sulfat memblok eksitasi NMDA & mencegah kejang berulang."
                            }
                        }

                        PathologyProfile.ARREST_ARRHYTHMIA -> {
                            if (isArrestProcedure(titleLower) || isEpinephrine(titleLower)) {
                                sys = 110
                                dia = 70
                                hr = 80
                                rr = 18
                                spO2 = 98
                                responseNote = "Kardioversi/CPR berhasil mengembalikan Irama Sinus Normal (ROSC)."
                            }
                        }

                        PathologyProfile.NEUROLOGIC_TKB -> {
                            if (isOsmotherapy(titleLower) || isDecompression(titleLower) || isOxygen(titleLower)) {
                                sys = (sys - 28).coerceAtLeast(125)
                                dia = (dia - 16).coerceAtLeast(80)
                                hr = (hr + 25).coerceAtMost(80)
                                rr = (rr + 6).coerceAtMost(18)
                                spO2 = (spO2 + 12).coerceAtMost(99)
                                responseNote = "Menurunkan Tekanan Intra Kranial (TIK) & mereversi Cushing Triad."
                            }
                        }

                        else -> {
                            if (isOxygen(titleLower)) {
                                spO2 = (spO2 + 10).coerceAtMost(99)
                                rr = (rr - 6).coerceAtLeast(16)
                                hr = (hr - 10).coerceAtLeast(76)
                                sys = (sys + 6).coerceAtMost(120)
                                dia = (dia + 4).coerceAtMost(80)
                            }
                            if (isFluidResuscitation(titleLower)) {
                                sys = (sys + 22).coerceAtMost(120)
                                dia = (dia + 14).coerceAtMost(80)
                                hr = (hr - 20).coerceAtLeast(74)
                                rr = (rr - 6).coerceAtLeast(16)
                                spO2 = (spO2 + 8).coerceAtMost(99)
                            }
                        }
                    }

                    if (titleLower.contains("parasetamol") || titleLower.contains("pct") || titleLower.contains("antipiretik")) {
                        temp = (temp - 1.5).coerceAtLeast(36.6)
                    }

                    // Universal Safety Normalizer Pass: ensure every STABILIZED action pulls abnormal vitals towards normal
                    if (sys < 90) sys = (sys + 15).coerceAtMost(110)
                    if (dia < 60) dia = (dia + 10).coerceAtMost(75)
                    if (hr > 100) hr = (hr - 15).coerceAtLeast(80)
                    if (rr > 22) rr = (rr - 5).coerceAtLeast(18)
                    if (spO2 < 92) spO2 = (spO2 + 8).coerceAtMost(98)

                    trendNote = "⚡ VITAL SIGN MEMBAIK SIGNIFIKAN pasca penanganan: ${log.actionTitle}"
                }

                CitoImpactType.UNINDICATED -> {
                    activeInterventions.add("ℹ️ ${log.actionTitle}")
                    trendNote = "ℹ️ Tatalaksana (${log.actionTitle}) tidak diindikasikan atau TTV sudah stabil."
                    responseNote = "Intervensi tidak mengubah hemodinamik secara bermakna karena tanda vital sudah terkompensasi."
                }

                CitoImpactType.HARMFUL -> {
                    activeInterventions.add("⚠️ Komplikasi: ${log.actionTitle}")

                    if (pathology == PathologyProfile.CARDIOGENIC_SHOCK_ADHF && (titleLower.contains("kristaloid") || titleLower.contains("fluid") || titleLower.contains("trendelenburg"))) {
                        spO2 = (spO2 - 16).coerceAtLeast(72)
                        rr = (rr + 10).coerceAtMost(38)
                        hr = (hr + 22).coerceAtMost(145)
                        responseNote = "⚠️ FATAL FLUID OVERLOAD: Bolus cairan pada edema paru memperberat eksudat alveolus & gagal napas!"
                    } else if (titleLower.contains("sedatif") || titleLower.contains("diazepam") || titleLower.contains("morphine")) {
                        rr = (rr - 8).coerceAtLeast(8)
                        spO2 = (spO2 - 12).coerceAtLeast(80)
                        responseNote = "⚠️ DEPRESI NAPAS: Sedatif memicu hipoventilasi & desaturasi oksigen."
                    } else if (titleLower.contains("isdn") || titleLower.contains("nitrogli")) {
                        sys = (sys - 30).coerceAtLeast(55)
                        dia = (dia - 18).coerceAtLeast(35)
                        hr = (hr + 24).coerceAtMost(150)
                        responseNote = "⚠️ HIPOTENSI BERAT: Nitrat memicu kolaps pembuluh darah pada pasien preload-dependent."
                    } else {
                        sys = (sys - 12).coerceAtLeast(60)
                        hr = (hr + 14).coerceAtMost(140)
                        spO2 = (spO2 - 6).coerceAtLeast(80)
                        responseNote = "⚠️ INTERVENSI TIDAK RELEVAN: Membuang waktu resusitasi & memperberat distress pasien."
                    }

                    trendNote = "⚠️ VITAL SIGN MEMBURUK akibat intervensi kurang tepat: ${log.actionTitle}"
                }

                CitoImpactType.FATAL_COLLAPSE -> {
                    sys = 0
                    dia = 0
                    hr = 0
                    spO2 = 0
                    rr = 0
                    activeInterventions.add("🚨 FATAL CARDIAC ARREST")
                    trendNote = "🚨 FATAL CARDIAC ARREST AKIBAT KONTRAINDIKASI MUTLAK / WAKTU CITO HABIS!"
                    responseNote = "Henti jantung dan nafas total (Asystole/PEA)."
                }
            }
        }

        // 2. Fallback checks for routine non-cito interventions
        if (citoActionLogs.isEmpty()) {
            val examConcat = userExams.joinToString(" ") { it.examName.lowercase() }
            val treatmentConcat = treatmentInput.lowercase()

            val hasO2 = examConcat.contains("oksimetri") || examConcat.contains("oksigen") ||
                    treatmentConcat.contains("oksigen") || treatmentConcat.contains("o2") ||
                    treatmentConcat.contains("nebulizer") || treatmentConcat.contains("ventolin")
            if (hasO2) {
                spO2 = (spO2 + 6).coerceAtMost(99)
                rr = (rr - 4).coerceAtLeast(14)
                activeInterventions.add("O2 / Inhalasi")
            }

            val hasFluid = examConcat.contains("infus") || treatmentConcat.contains("infus") ||
                    treatmentConcat.contains("nacl") || treatmentConcat.contains("rl") ||
                    treatmentConcat.contains("resusitasi")
            if (hasFluid && sys < 100 && pathology != PathologyProfile.CARDIOGENIC_SHOCK_ADHF) {
                sys = (sys + 20).coerceAtMost(120)
                dia = (dia + 12).coerceAtMost(80)
                hr = (hr - 15).coerceAtLeast(70)
                activeInterventions.add("Resusitasi Cairan")
            }
        }

        // 3. Emergency Decay Mode (Critical deterioration when time runs out without stabilization)
        if (isEmergencyMode && remainingSeconds in 1..60 && citoActionLogs.none { it.impactType == CitoImpactType.STABILIZED }) {
            if (hr > 0) hr = (hr + 12).coerceAtMost(160)
            if (sys > 0) sys = (sys - 10).coerceAtLeast(55)
            if (spO2 > 0) spO2 = (spO2 - 4).coerceAtLeast(75)
            if (trendNote.isEmpty()) {
                trendNote = "Pasien mengalami deteriorasi cepat! Butuh penanganan Cito segera."
            }
        }

        // 4. Mathematical Hemodynamic Indices Calculations
        val map = if (sys > 0 && dia > 0) dia + (sys - dia) / 3 else 0
        val shockIndex = if (sys > 0) (hr.toDouble() / sys.toDouble()) else 0.0

        // 5. Classification of Status and Theme
        val isFatal = sys == 0 || hr == 0
        val isCritical = sys < 90 || spO2 < 90 || hr > 130 || temp >= 40.0 || map < 65
        val isWarning = hr > 100 || temp >= 38.0 || rr > 22 || spO2 in 90..94

        val statusText = when {
            isFatal -> "HENTI JANTUNG (ARREST)"
            isCritical -> "KRITIS / SYOK (MAP $map mmHg)"
            isWarning -> "WASPADA / FEBRIS"
            else -> "STABIL (MAP $map mmHg)"
        }

        val statusColor = when {
            isFatal || isCritical -> EmergencyRed
            isWarning -> WarningAmber
            else -> SuccessGreen
        }

        if (trendNote.isBlank()) {
            trendNote = when {
                activeInterventions.isNotEmpty() -> "Membaik pasca intervensi (${activeInterventions.joinToString()})"
                isCritical -> "Hemodinamik belum stabil (Shock Index ${String.format("%.2f", shockIndex)}). Butuh resusitasi secepatnya."
                isWarning -> "Tanda vital menunjukkan kompensasi inflamasi / hipoksia ringan."
                else -> "Tanda vital stabil dalam rentang normal."
            }
        }

        val resultVitals = ComputedVitals(
            systolic = sys,
            diastolic = dia,
            hr = hr,
            rr = rr,
            temp = temp,
            spO2 = spO2,
            map = map,
            shockIndex = shockIndex,
            statusText = statusText,
            statusColor = statusColor,
            trendNote = trendNote,
            activeInterventions = activeInterventions,
            pathologyProfile = pathology,
            physiologicalResponseNote = responseNote
        )

        // TTV Manager Workflow Logging: Log every status shift and vital sign calculation
        val previousStatus = MedicalDebugLogger.getLastRecordedStatus()
        val lastTrigger = when {
            citoActionLogs.isNotEmpty() -> "CITO Intervention: '${citoActionLogs.last().actionTitle}' (${citoActionLogs.last().impactType})"
            userExams.isNotEmpty() -> "User Exam Requested: '${userExams.last().examName}'"
            treatmentInput.isNotBlank() -> "Treatment Input Updated"
            else -> "Initial Base Assessment"
        }

        MedicalDebugLogger.logPatientConditionStatusChange(
            caseTitle = case.title,
            oldStatus = previousStatus,
            newStatus = statusText,
            map = map,
            shockIndex = shockIndex,
            sys = sys,
            dia = dia,
            hr = hr,
            spO2 = spO2,
            pathology = pathology.displayName,
            triggerEvent = lastTrigger
        )

        return resultVitals
    }
}
