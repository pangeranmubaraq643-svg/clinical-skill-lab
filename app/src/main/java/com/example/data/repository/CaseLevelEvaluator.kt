package com.example.data.repository

import com.example.data.model.CaseLevel
import com.example.data.model.ClinicalCase

object CaseLevelEvaluator {

    fun evaluate(case: ClinicalCase): CaseLevel {
        return evaluateDisease(case.title, case.isEmergencyCase)
    }

    fun evaluateDisease(title: String, isEmergency: Boolean = false): CaseLevel {
        val lower = title.lowercase().trim()

        val isMildOrChronic = lower.contains("kronis") || lower.contains("kronik") ||
                lower.contains("ringan") || lower.contains("terkontrol") ||
                lower.contains("stabil") || lower.contains("simpel") ||
                lower.contains("derajat 1") || lower.contains("grade 1") ||
                lower.contains("grade i") || lower.contains("derajat i ") ||
                lower.contains("tanpa komplikasi")

        // 1. LEVEL 3: Kegawatdaruratan akut, kondisi kritis, trauma berat, komplikasi akut, resusitasi & cito. Batas waktu 5 menit.
        if (!isMildOrChronic) {
            if (
                lower.contains("stemi") || lower.contains("nstemi") || lower.contains("infark") ||
                lower.contains("syok") || lower.contains("anafilak") || lower.contains("stroke") ||
                lower.contains("status") || lower.contains("kejang") || lower.contains("epilep") ||
                lower.contains("kad") || lower.contains("ketoasidosis") || lower.contains("eklamsia") ||
                lower.contains("peb") || lower.contains("preeklamsia berat") || lower.contains("sjs") ||
                lower.contains("stevens") || lower.contains("peritonitis") || lower.contains("perforasi") ||
                lower.contains("glaukoma") || lower.contains("krisis") || lower.contains("tension") ||
                lower.contains("gagal napas") || lower.contains("edema paru") || lower.contains("tamponade") ||
                lower.contains("ruptur") || lower.contains("atonia") || lower.contains("pph") ||
                lower.contains("cedera kepala") || lower.contains("trauma") || lower.contains("luka bakar") ||
                lower.contains("dislokasi") || lower.contains("benda asing") || lower.contains("intoksikasi") ||
                lower.contains("keracunan") || lower.contains("perdarahan") || lower.contains("abses") ||
                lower.contains("ileus") || lower.contains("hernia inkarserata") || lower.contains("torsi") ||
                lower.contains("torsio") || lower.contains("emboli") || lower.contains("pneumothorax") ||
                lower.contains("gagal ginjal") || lower.contains("aki ") || lower.contains("koma") ||
                lower.contains("sinkop") || lower.contains("vt ") || lower.contains("vf ") ||
                lower.contains("av block") || lower.contains("diseksi") || lower.contains("aneurisma") ||
                lower.contains("dss") || lower.contains("dengue shock") || lower.contains("emergensi") ||
                lower.contains("cito") || lower.contains("masif") || lower.contains("kritis") ||
                lower.contains("gigitan") || lower.contains("sengatan") || lower.contains("septik") ||
                lower.contains("terjepit") || lower.contains("combustio") || lower.contains("asfiksia") ||
                lower.contains("dehidrasi berat") || lower.contains("kolelitiasis") || lower.contains("kolesistitis") ||
                lower.contains("pankreatitis") || lower.contains("retensi urine") || lower.contains("partus macet") ||
                lower.contains("distosia") || lower.contains("solusio") || lower.contains("reaksi lepra") ||
                lower.contains("gagal jantung") || lower.contains("adhf") || lower.contains("astmatikus") ||
                lower.contains("eksaserbasi") || lower.contains("apendisitis") || lower.contains("akut") ||
                lower.contains("fraktur") || lower.contains("vulnus") || lower.contains("terbakar") ||
                lower.contains("kolik") || lower.contains("spasme") || lower.contains("isemia") ||
                lower.contains("iskemik") || lower.contains("hipoksia") || lower.contains("asidosis") ||
                lower.contains("asites masif") || lower.contains("sesak hebat")
            ) {
                return CaseLevel.LEVEL_3
            }
        }

        // 2. LEVEL 2: Kompleksitas sedang & membutuhkan pemeriksaan laboratorium / penunjang spesifik / rujukan sub-spesialis. Batas waktu 10 menit.
        if (
            lower.contains("dengue") || lower.contains("dbd") || lower.contains("dhf") ||
            lower.contains("tifoid") || lower.contains("typhoid") || lower.contains("hepatitis") ||
            lower.contains("tb ") || lower.contains("tuberkulosis") || lower.contains("malaria") ||
            lower.contains("leptospirosis") || lower.contains("kusta") || lower.contains("hansen") ||
            lower.contains("tetanus") || lower.contains("sifilis") || lower.contains("gonore") ||
            lower.contains("chlamydia") || lower.contains("hiv") || lower.contains("aids") ||
            lower.contains("diabetes") || lower.contains("dm ") || lower.contains("hiperglikemia") ||
            lower.contains("hipoglikemia") || lower.contains("anemia") || lower.contains("dislipidemia") ||
            lower.contains("tiroid") || lower.contains("tirotoksikosis") || lower.contains("gizi buruk") ||
            lower.contains("stunting") || lower.contains("gout") || lower.contains("hiperurisemia") ||
            lower.contains("elektrolit") || lower.contains("obesitas") || lower.contains("pneumonia") ||
            lower.contains("ppok") || lower.contains("bronkitis") || lower.contains("asma") ||
            lower.contains("pleuritis") || lower.contains("efusi") || lower.contains("chf") ||
            lower.contains("angina") || lower.contains("palpitasi") || lower.contains("koroner") ||
            lower.contains("sirosis") || lower.contains("hemoroid") || lower.contains("kolitis") ||
            lower.contains("ginjal") || lower.contains("ckd") || lower.contains("pgk") ||
            lower.contains("isk") || lower.contains("sistitis") || lower.contains("pielonefritis") ||
            lower.contains("urolitiasis") || lower.contains("batu") || lower.contains("bph") ||
            lower.contains("prostatitis") || lower.contains("nefrotik") || lower.contains("glomerulo") ||
            lower.contains("vertigo") || lower.contains("bppv") || lower.contains("skizofrenia") ||
            lower.contains("bipolar") || lower.contains("depresi") || lower.contains("ansietas") ||
            lower.contains("panik") || lower.contains("somatoform") || lower.contains("hyperemesis") ||
            lower.contains("abortus") || lower.contains("kista") || lower.contains("mioma") ||
            lower.contains("endometriosis") || lower.contains("mastitis") || lower.contains("keratitis") ||
            lower.contains("uveitis") || lower.contains("otitis media") || lower.contains("oma") ||
            lower.contains("omsk") || lower.contains("rhinosinusitis") || lower.contains("sinusitis") ||
            lower.contains("tonsilitis") || lower.contains("dermatitis") || lower.contains("psoriasis") ||
            lower.contains("erisipelas") || lower.contains("selulitis") || lower.contains("rheumatoid") ||
            lower.contains("osteoartritis") || lower.contains("lupus") || lower.contains("sle") ||
            lower.contains("osteomielitis") || lower.contains("urtikaria") || lower.contains("eksem") ||
            lower.contains("tinea") || lower.contains("karsinoma") || lower.contains("kanker") ||
            lower.contains("tumor") || lower.contains("neoplasma") || lower.contains("kronis") ||
            lower.contains("kronik") || lower.contains("persisten") || lower.contains("sedang") ||
            lower.contains("luka") || lower.contains("pembengkakan") || lower.contains("nodul") ||
            lower.contains("mual") || lower.contains("muntah") || lower.contains("diare") ||
            lower.contains("gastritis") || lower.contains("gerd") || lower.contains("ulkus") ||
            lower.contains("hnp") || lower.contains("ischialgia") || lower.contains("neuropati") ||
            lower.contains("bell") || lower.contains("pusing") || lower.contains("sakit kepala") ||
            lower.contains("hipertensi") || lower.contains("varicella") || lower.contains("morbili") ||
            lower.contains("parotitis") || lower.contains("scabies") || lower.contains("pedikulosis") ||
            lower.contains("filariasis") || lower.contains("taeniasis") || lower.contains("ankilostomiasis") ||
            lower.contains("amoebiasis") || lower.contains("giardiasis") || lower.contains("skabies") ||
            lower.contains("verruca") || lower.contains("clavus") || lower.contains("miliaria") ||
            lower.contains("acne") || lower.contains("alopecia") || lower.contains("vitiligo") ||
            lower.contains("melasma") || lower.contains("sprain") || lower.contains("strain") ||
            lower.contains("kontusio") || lower.contains("hematoma") || lower.contains("faringitis") ||
            lower.contains("laringitis") || lower.contains("konjungtivitis") || lower.contains("hordeolum") ||
            lower.contains("chalazion") || lower.contains("pterygium") || lower.contains("strabismus") ||
            lower.contains("ambliopia") || lower.contains("katarak") || lower.contains("glaukoma") ||
            lower.contains("presbiopia") || lower.contains("myopia") || lower.contains("astigmatisme") ||
            lower.contains("furunkel") || lower.contains("karbunkel") || lower.contains("impetigo") ||
            lower.contains("folikulitis") || lower.contains("hidradenitis") || lower.contains("paronikia") ||
            lower.contains("inguinal") || lower.contains("femoral") || lower.contains("umbilikalis") ||
            lower.contains("hernia")
        ) {
            return CaseLevel.LEVEL_2
        }

        // 3. LEVEL 1: Gejala sangat khas (patognomonik) & penyakit rawat jalan umum Faskes 1 (Anamnesis & Pemfis Klinis). Batas waktu 15 menit. Capped <= 450 penyakit.
        return CaseLevel.LEVEL_1
    }
}
