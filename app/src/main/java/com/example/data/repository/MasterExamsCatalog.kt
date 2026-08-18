package com.example.data.repository

import com.example.data.model.ClinicalCase
import com.example.data.model.ExamCategory
import com.example.data.model.ExamItem

object MasterExamsCatalog {

    val allMasterExams = listOf(
        // === PEMERIKSAAN FISIK (PEMFIS - FREE - HEAD TO TOE) ===
        // KEPALA & MATA
        ExamItem("M_P1", "Inspeksi & Palpasi Kepala Wajah (Deformitas & Hematoma)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P2", "Pemeriksaan Mata (Konjungtiva, Sklera, Injeksi Siliar)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P3", "Pemeriksaan Pupil & Refleks Cahaya (RC)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P4", "Fundoskopi Mata (Retina & Papil N. II)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P5", "Tes Fluoresens Kornea & Slit-Lamp (Oftalmo)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P6", "Pemeriksaan Tekanan Intraokular (Tonometer Schiotz)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P7", "Tes Schirmer & Sensitivitas Kornea", ExamCategory.PEMFIS, "", 0),

        // THT & LEHER
        ExamItem("M_P8", "Otoskopis THT & Membran Timpani", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P9", "Rhinoskopi & Pemeriksaan Kavum Nasi", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P10", "Pemeriksaan Faring, Tonsil, Uvula & Trismus", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P11", "Tes Garputala THT (Rinne, Weber, Schwabach)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P12", "Pemeriksaan Kelenjar Tiroid & JVP (Venous Pressure)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P13", "Pemeriksaan Kelenjar Getah Bening (KGB) Leher & Aksila", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P14", "Tanda Rangsang Meningeal (Kaku Kuduk, Brudzinski, Kernig)", ExamCategory.PEMFIS, "", 0),

        // DADA, PARU & JANTUNG
        ExamItem("M_P15", "Inspeksi & Palpasi Dada (Gerak Dada, Emfisema, Trakea)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P16", "Auskultasi Paru & Suara Napas (Vesikuler, Rhonchi, Wheezing, Stridor)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P17", "Auskultasi Jantung (S1 S2, Murmur, Gallop, Friction Rub, Suara Jauh)", ExamCategory.PEMFIS, "", 0),

        // ABDOMEN & GINJAL
        ExamItem("M_P18", "Inspeksi & Auskultasi Abdomen (Bising Usus)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P19", "Palpasi Abdomen, Nyeri Tekan & Defans Muskular", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P20", "Tanda McBurney / Murphy / Rovsing / Blumberg Sign", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P21", "Palpasi Organomegali (Hati/Lien) & Ascites (Shifting Dullness)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P22", "Nyeri Ketok CVA (Costovertebral Angle / Ginjal)", ExamCategory.PEMFIS, "", 0),

        // ANOREKTAL, KEBIDANAN & GENITALIA
        ExamItem("M_P23", "Colok Dubur / Rectal Toucher (RT / DRE)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P24", "Pemeriksaan VT / Vaginal Toucher & Inspekulo (Obgyn)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P25", "Pemeriksaan Leopold I-IV & DJJ (Obgyn)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P26", "Pemeriksaan Skrotum, Refleks Kremaster & Sign Phahn", ExamCategory.PEMFIS, "", 0),

        // VASKULAR, KULIT & MUSKULOSKELETAL
        ExamItem("M_P27", "Pemeriksaan CRT, Akral Perifer & Pulsasi Arteri", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P28", "Pemeriksaan Kulit, Turgor & Efloresensi (Petechiae/Vesikel)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P29", "Pemeriksaan Tes Auspitz & Fenomena Tetesan Lilin", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P30", "Pemeriksaan Kekuatan Otot 1-5 & Deformitas Ekstremitas", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P31", "Tes Stabilitas Lutut & Meniskus (Lachman, McMurray)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P32", "Tes Provokasi Nyeri (Phalen, Tinel, Lasegue, Patrick)", ExamCategory.PEMFIS, "", 0),

        // NEUROLOGIS, PSIKIATRI & OTOT
        ExamItem("M_P33", "Pemeriksaan Saraf Kranial (N. I - XII)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P34", "Pemeriksaan Refleks Fisiologis & Patologis (Babinski)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P35", "Tes Romberg & Koordinasi Serebelar", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P36", "Tes Sensibilitas Kulit (Nyeri, Raba, Suhu)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P37", "Maneuver Dix-Hallpike & Tes Nistagmus", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P38", "Antropometri & Status Gizi (BB, TB, IMT)", ExamCategory.PEMFIS, "", 0),
        ExamItem("M_P39", "Pemeriksaan Status Mental & Psikiatri (PANSS / HAM-A)", ExamCategory.PEMFIS, "", 0),

        // === LABORATORIUM (LAB) ===
        ExamItem("M_L1", "Darah Rutin (CBC / Hemogram)", ExamCategory.LAB, "", 95000),
        ExamItem("M_L2", "Troponin I / T Kuantitatif", ExamCategory.LAB, "", 350000),
        ExamItem("M_L3", "CK-MB Kuantitatif", ExamCategory.LAB, "", 180000),
        ExamItem("M_L4", "D-Dimer Kuantitatif", ExamCategory.LAB, "", 450000),
        ExamItem("M_L5", "Analisis Gas Darah (AGD / ABG)", ExamCategory.LAB, "", 180000),
        ExamItem("M_L6", "Elektrolit Lengkap (Na/K/Cl)", ExamCategory.LAB, "", 160000),
        ExamItem("M_L7", "Gula Darah Sewaktu (GDS) & Keton Urin", ExamCategory.LAB, "", 35000),
        ExamItem("M_L8", "Profil Lipid Lengkap", ExamCategory.LAB, "", 250000),
        ExamItem("M_L9", "Fungsi Ginjal (Ureum, Kreatinin & eGFR)", ExamCategory.LAB, "", 120000),
        ExamItem("M_L10", "Fungsi Hati (SGOT, SGPT, Bilirubin & Albumin)", ExamCategory.LAB, "", 150000),
        ExamItem("M_L11", "Urinalisis Lengkap & Sedimen Urin", ExamCategory.LAB, "", 65000),
        ExamItem("M_L12", "Kadar Asam Urat Darah (Serum Uric Acid)", ExamCategory.LAB, "", 45000),
        ExamItem("M_L13", "Tes Kehamilan Urin (beta-hCG Urin)", ExamCategory.LAB, "", 40000),
        ExamItem("M_L14", "Serologi Dengue (NS1 Ag & IgM/IgG Anti-Dengue)", ExamCategory.LAB, "", 220000),
        ExamItem("M_L15", "Tes Widal / Tubex TF (Anti-Salmonella)", ExamCategory.LAB, "", 180000),
        ExamItem("M_L16", "Analisis Cairan Otak (LCS / Lumbal Pungsi)", ExamCategory.LAB, "", 650000),
        ExamItem("M_L17", "Laktat Darah (Blood Lactate)", ExamCategory.LAB, "", 120000),
        ExamItem("M_L18", "Gula Darah Puasa (GDP) & 2 Jam PP", ExamCategory.LAB, "", 70000),
        ExamItem("M_L19", "HbA1c (Glycated Hemoglobin)", ExamCategory.LAB, "", 210000),
        ExamItem("M_L20", "Profil Pembekuan (PT, APTT, INR)", ExamCategory.LAB, "", 230000),
        ExamItem("M_L21", "CRP Kuantitatif & Laju Endap Darah (LED)", ExamCategory.LAB, "", 140000),
        ExamItem("M_L22", "Tes Cepat Molekuler TB (TCM GeneXpert MTB)", ExamCategory.LAB, "", 280000),
        ExamItem("M_L23", "Pemeriksaan BTA Sputum / Dahak SPS (3x)", ExamCategory.LAB, "", 120000),
        ExamItem("M_L24", "Analisis Feses Rutin, Rotavirus & FOBT", ExamCategory.LAB, "", 85000),
        ExamItem("M_L25", "Hormon Tiroid (FT4, TSHs & T3)", ExamCategory.LAB, "", 380000),
        ExamItem("M_L26", "HBsAg & Anti-HCV Rapid Test", ExamCategory.LAB, "", 190000),
        ExamItem("M_L27", "Anti-HIV Rapid Test 3 Metode", ExamCategory.LAB, "", 150000),
        ExamItem("M_L28", "Procalcitonin (PCT) Sepsis Marker", ExamCategory.LAB, "", 420000),
        ExamItem("M_L29", "Swab Rapid Antigen / PCR Respiratorik", ExamCategory.LAB, "", 150000),
        ExamItem("M_L30", "Serologi Leptospira / MAT (Microscopic Agglutination)", ExamCategory.LAB, "", 220000),
        ExamItem("M_L31", "Sediaan Apusan Gram / Usap Kornea / Tzanck Smear", ExamCategory.LAB, "", 120000),
        ExamItem("M_L32", "Kultur Urin & Uji Sensitivitas Antibrogram", ExamCategory.LAB, "", 280000),
        ExamItem("M_L33", "Kadar Kalsium, Magnesium & Fosfat Darah", ExamCategory.LAB, "", 110000),
        ExamItem("M_L34", "Pemeriksaan BTA Kerokan Kulit (Lepra / Morbus Hansen)", ExamCategory.LAB, "", 95000),
        ExamItem("M_L35", "Serum Ferritin & SI / TIBC (Panel Anemia Iron)", ExamCategory.LAB, "", 310000),
        ExamItem("M_L36", "Tes KOH 10% Sediaan Jamur Kulit/Kuku", ExamCategory.LAB, "", 65000),
        ExamItem("M_L37", "Tes Autoimun ANA Test & Anti-dsDNA", ExamCategory.LAB, "", 580000),
        ExamItem("M_L38", "Pemeriksaan Toksikologi Urin 6 Parameter", ExamCategory.LAB, "", 180000),
        ExamItem("M_L39", "Pemeriksaan PSA (Prostate Specific Antigen)", ExamCategory.LAB, "", 260000),
        ExamItem("M_L40", "Analisis Cairan Sendi (Arthrocentesis / Synovial Fluid)", ExamCategory.LAB, "", 350000),
        ExamItem("M_L41", "Pemeriksaan Tetes Tebal & Apusan Darah Malaria", ExamCategory.LAB, "", 95000),
        ExamItem("M_L42", "Kultur Dahak / Sputum & Sensitivitas Antimikroba", ExamCategory.LAB, "", 280000),
        ExamItem("M_L43", "Kultur Darah & Uji Resistensi Antibiotik (Bactec)", ExamCategory.LAB, "", 350000),
        ExamItem("M_L44", "Keton Darah (Beta-Hydroxybutyrate) / Urin Cito", ExamCategory.LAB, "", 50000),
        ExamItem("M_L45", "Biopsi Jaringan & Pemeriksaan Patologi Anatomi (PA)", ExamCategory.LAB, "", 550000),
        ExamItem("M_L46", "Pap Smear Sitologi Serviks / ThinPrep", ExamCategory.LAB, "", 150000),

        // === RADIOLOGI & EKG (IMAGING) ===
        ExamItem("M_I1", "EKG 12-Lead", ExamCategory.IMAGING, "", 150000),
        ExamItem("M_I2", "Rontgen Thorax AP/PA", ExamCategory.IMAGING, "", 180000),
        ExamItem("M_I3", "FAST Scan Bedside (Trauma Sonography)", ExamCategory.IMAGING, "", 180000),
        ExamItem("M_I4", "USG Abdomen 3 Vaskular/Parenkim", ExamCategory.IMAGING, "", 350000),
        ExamItem("M_I5", "CT Scan Kepala Tanpa Kontras", ExamCategory.IMAGING, "", 1200000),
        ExamItem("M_I6", "CT Scan Thorax / Abdomen Kontras", ExamCategory.IMAGING, "", 1800000),
        ExamItem("M_I7", "MRI Otak / Brain Non-Kontras", ExamCategory.IMAGING, "", 2500000),
        ExamItem("M_I8", "Echocardiography Bedside", ExamCategory.IMAGING, "", 650000),
        ExamItem("M_I9", "Rontgen Ekstremitas / Pelvis / Servikal / Neck", ExamCategory.IMAGING, "", 220000),
        ExamItem("M_I10", "Rontgen BNO / Abdomen 3 Posisi", ExamCategory.IMAGING, "", 250000),
        ExamItem("M_I11", "USG Kebidanan / Transvaginal (Gynecological)", ExamCategory.IMAGING, "", 320000),
        ExamItem("M_I12", "Spirometri Test (Kapasitas Paru)", ExamCategory.IMAGING, "", 220000),
        ExamItem("M_I13", "USG Doppler Vaskular Ekstremitas (DVT/PAD)", ExamCategory.IMAGING, "", 450000),
        ExamItem("M_I14", "Densitometri Tulang (DEXA Scan)", ExamCategory.IMAGING, "", 600000),
        ExamItem("M_I15", "Holter Monitoring EKG 24 Jam", ExamCategory.IMAGING, "", 550000),
        ExamItem("M_I16", "EKG Sandapan Tambahan (V7-V9, V3R-V4R)", ExamCategory.IMAGING, "", 150000),
        ExamItem("M_I17", "Elektroensefalografi (EEG)", ExamCategory.IMAGING, "", 450000),
        ExamItem("M_I18", "Elektromiografi (EMG)", ExamCategory.IMAGING, "", 500000),
        ExamItem("M_I19", "Endoskopi Saluran Cerna Atas (Gastroskopi / EGD)", ExamCategory.IMAGING, "", 1500000),
        ExamItem("M_I20", "Kolonoskopi Saluran Cerna Bawah", ExamCategory.IMAGING, "", 1800000),
        ExamItem("M_I21", "Kateterisasi Jantung / Angiografi Koroner (CAG)", ExamCategory.IMAGING, "", 3500000)
    )

    fun getMergedExamsForCase(availableInCase: List<ExamItem>, activeCase: ClinicalCase? = null): List<ExamItem> {
        val resultMap = LinkedHashMap<String, ExamItem>()

        // Add case-specific exams with synchronized master pricing
        for (item in availableInCase) {
            val key = normalizeExamKey(item.name)
            val matchedMaster = findMasterExamMatch(item.name)
            val finalItem = if (matchedMaster != null) {
                item.copy(costRupiah = matchedMaster.costRupiah, category = matchedMaster.category)
            } else {
                item
            }
            resultMap[key] = finalItem
        }

        // Add remaining master exams
        for (master in allMasterExams) {
            val masterKey = normalizeExamKey(master.name)
            if (!resultMap.containsKey(masterKey)) {
                val isAlreadyCovered = resultMap.keys.any { existingKey ->
                    isSimilarExamKey(existingKey, masterKey)
                }
                if (!isAlreadyCovered) {
                    resultMap[masterKey] = master
                }
            }
        }

        return resultMap.values.toList()
    }

    fun findMasterExamMatch(name: String): ExamItem? {
        val clean = name.trim().lowercase()
        val key = normalizeExamKey(name)
        
        // Exact normalized match
        val exact = allMasterExams.find { normalizeExamKey(it.name) == key }
        if (exact != null) return exact

        // Name contains match
        val containsMatch = allMasterExams.find { 
            it.name.contains(clean, ignoreCase = true) || clean.contains(it.name.lowercase()) 
        }
        if (containsMatch != null) return containsMatch

        // Similar key match
        return allMasterExams.find { isSimilarExamKey(key, normalizeExamKey(it.name)) }
    }

    /**
     * Resolves the exact category and standardized price for any user query or AI suggestion.
     * Prevents pricing discrepancies between the database and user custom requests.
     */
    fun resolveExam(queryText: String): ExamItem {
        val clean = queryText.trim()
        val matched = findMasterExamMatch(clean)
        if (matched != null) {
            return matched
        }

        val qLower = clean.lowercase()
        
        // Physical exam detection (Free)
        val isPemfis = qLower.contains("inspeksi") || qLower.contains("palpasi") ||
                qLower.contains("perkusi") || qLower.contains("auskultasi") ||
                qLower.contains("pemeriksaan fisik") || qLower.contains("pemfis") ||
                qLower.contains("tanda") || qLower.contains("sign") ||
                qLower.contains("refleks") || qLower.contains("manuver") ||
                qLower.contains("tes ") && (qLower.contains("romberg") || qLower.contains("dix") || qLower.contains("tinel") || qLower.contains("phalen") || qLower.contains("lachman") || qLower.contains("mcmurray") || qLower.contains("provokasi"))

        if (isPemfis) {
            return ExamItem("C_P_${System.currentTimeMillis()}", clean, ExamCategory.PEMFIS, "", 0L)
        }

        // Imaging / Radiology / EKG detection
        val isImaging = qLower.contains("rontgen") || qLower.contains("foto") || qLower.contains("usg") ||
                qLower.contains("ct") || qLower.contains("mri") || qLower.contains("ekg") ||
                qLower.contains("ecg") || qLower.contains("echo") || qLower.contains("radiologi") ||
                qLower.contains("x-ray") || qLower.contains("endoskopi") || qLower.contains("kolonoskopi") ||
                qLower.contains("eeg") || qLower.contains("emg") || qLower.contains("angiografi") ||
                qLower.contains("kateterisasi") || qLower.contains("dexa") || qLower.contains("spirometri") ||
                qLower.contains("holter")

        if (isImaging) {
            val cost = resolveImagingPrice(qLower)
            return ExamItem("C_I_${System.currentTimeMillis()}", clean, ExamCategory.IMAGING, "", cost)
        }

        // Laboratory test
        val cost = resolveLabPrice(qLower)
        return ExamItem("C_L_${System.currentTimeMillis()}", clean, ExamCategory.LAB, "", cost)
    }

    private fun resolveImagingPrice(qLower: String): Long {
        return when {
            qLower.contains("kateterisasi") || qLower.contains("angiografi koroner") || qLower.contains("cag") -> 3500000L
            qLower.contains("mri") -> if (qLower.contains("kontras") || qLower.contains("spine")) 2800000L else 2500000L
            qLower.contains("ct") -> if (qLower.contains("kontras") || qLower.contains("angiografi")) 1800000L else 1200000L
            qLower.contains("kolonoskopi") -> 1800000L
            qLower.contains("endoskopi") || qLower.contains("gastroskopi") || qLower.contains("egd") -> 1500000L
            qLower.contains("echo") -> 650000L
            qLower.contains("dexa") || qLower.contains("densitometri") -> 600000L
            qLower.contains("holter") -> 550000L
            qLower.contains("emg") -> 500000L
            qLower.contains("eeg") -> 450000L
            qLower.contains("doppler") -> 450000L
            qLower.contains("usg") -> if (qLower.contains("kebidanan") || qLower.contains("transvaginal")) 320000L else 350000L
            qLower.contains("bno") || qLower.contains("3 posisi") || qLower.contains("ivp") -> 250000L
            qLower.contains("spirometri") || qLower.contains("audiometri") -> 220000L
            qLower.contains("ekstremitas") || qLower.contains("pelvis") || qLower.contains("servikal") || qLower.contains("femur") || qLower.contains("tulang") -> 220000L
            qLower.contains("rontgen") || qLower.contains("thorax") || qLower.contains("torak") || qLower.contains("x-ray") -> 180000L
            qLower.contains("fast") -> 180000L
            qLower.contains("ekg") || qLower.contains("ecg") -> 150000L
            else -> 200000L
        }
    }

    private fun resolveLabPrice(qLower: String): Long {
        return when {
            qLower.contains("lumbal") || qLower.contains("lcs") || qLower.contains("cairan otak") -> 650000L
            qLower.contains("dsdna") || qLower.contains("ana") || qLower.contains("autoimun") -> 580000L
            qLower.contains("biopsi") || qLower.contains("patologi") || qLower.contains("pa") -> 550000L
            qLower.contains("d-dimer") || qLower.contains("ddimer") -> 450000L
            qLower.contains("procalcitonin") || qLower.contains("pct") -> 420000L
            qLower.contains("tiroid") || qLower.contains("ft4") || qLower.contains("tsh") || qLower.contains("t3") -> 380000L
            qLower.contains("troponin") || qLower.contains("hs-ctnt") || qLower.contains("hsctn") -> 350000L
            qLower.contains("sendi") || qLower.contains("synovial") || qLower.contains("arthrocentesis") -> 350000L
            qLower.contains("kultur darah") || qLower.contains("bactec") -> 350000L
            qLower.contains("ferritin") || qLower.contains("tibc") || qLower.contains("besi") -> 310000L
            qLower.contains("genexpert") || qLower.contains("tcm") || qLower.contains("pcr") -> 280000L
            qLower.contains("kultur") -> 280000L
            qLower.contains("psa") -> 260000L
            qLower.contains("lipid") || qLower.contains("kolesterol") || qLower.contains("trigliserida") -> 250000L
            qLower.contains("hemostasis") || qLower.contains("pt") || qLower.contains("aptt") || qLower.contains("inr") || qLower.contains("pembekuan") -> 230000L
            qLower.contains("dengue") || qLower.contains("ns1") || qLower.contains("leptospira") -> 220000L
            qLower.contains("hba1c") -> 210000L
            qLower.contains("hbsag") || qLower.contains("hcv") || qLower.contains("hiv") -> 190000L
            qLower.contains("ck-mb") || qLower.contains("ckmb") -> 180000L
            qLower.contains("agd") || qLower.contains("abg") || qLower.contains("gas darah") -> 180000L
            qLower.contains("widal") || qLower.contains("tubex") || qLower.contains("salmonella") -> 180000L
            qLower.contains("toksikologi") || qLower.contains("narkoba") || qLower.contains("drug test") -> 180000L
            qLower.contains("elektrolit") || qLower.contains("natrium") || qLower.contains("kalium") || qLower.contains("klorida") -> 160000L
            qLower.contains("hati") || qLower.contains("sgot") || qLower.contains("sgpt") || qLower.contains("bilirubin") || qLower.contains("albumin") -> 150000L
            qLower.contains("swab") || qLower.contains("antigen") || qLower.contains("pap smear") -> 150000L
            qLower.contains("crp") || qLower.contains("led") -> 140000L
            qLower.contains("ginjal") || qLower.contains("ureum") || qLower.contains("kreatinin") || qLower.contains("egfr") -> 120000L
            qLower.contains("laktat") || qLower.contains("lactate") -> 120000L
            qLower.contains("bta") || qLower.contains("sputum") || qLower.contains("dahak") || qLower.contains("gram") || qLower.contains("tzanck") -> 120000L
            qLower.contains("kalsium") || qLower.contains("magnesium") || qLower.contains("fosfat") -> 110000L
            qLower.contains("darah rutin") || qLower.contains("cbc") || qLower.contains("hemogram") || qLower.contains("darah lengkap") || qLower.contains("malaria") -> 95000L
            qLower.contains("feses") || qLower.contains("tinja") || qLower.contains("rotavirus") || qLower.contains("fobt") -> 85000L
            qLower.contains("gdp") || qLower.contains("2 jam pp") || qLower.contains("puasa") -> 70000L
            qLower.contains("urin") || qLower.contains("sedimen") || qLower.contains("koh") || qLower.contains("jamur") -> 65000L
            qLower.contains("keton") -> 50000L
            qLower.contains("asam urat") || qLower.contains("uric acid") -> 45000L
            qLower.contains("kehamilan") || qLower.contains("hcg") || qLower.contains("pp test") -> 40000L
            qLower.contains("gds") || qLower.contains("gula darah sewaktu") -> 35000L
            else -> 95000L
        }
    }

    fun filterExams(query: String): List<ExamItem> {
        val clean = query.trim().lowercase()
        if (clean.isBlank()) return allMasterExams
        return allMasterExams.filter { 
            it.name.lowercase().contains(clean) || 
            normalizeExamKey(it.name).contains(normalizeExamKey(clean))
        }
    }

    private fun normalizeExamKey(name: String): String {
        return name.lowercase()
            .replace(Regex("[^a-z0-9]"), "")
            .trim()
    }

    private fun isSimilarExamKey(key1: String, key2: String): Boolean {
        if (key1 == key2) return true
        if (key1.length > 5 && key2.length > 5) {
            if (key1.contains(key2) || key2.contains(key1)) return true
        }
        val isDengue1 = key1.contains("dengue") || key1.contains("ns1")
        val isDengue2 = key2.contains("dengue") || key2.contains("ns1")
        if (isDengue1 && isDengue2) return true

        val isWidal1 = key1.contains("widal") || key1.contains("tubex")
        val isWidal2 = key2.contains("widal") || key2.contains("tubex")
        if (isWidal1 && isWidal2) return true

        val isEkg1 = key1.contains("ekg") || key1.contains("ecg")
        val isEkg2 = key2.contains("ekg") || key2.contains("ecg")
        if (isEkg1 && isEkg2) return true

        val isCbc1 = key1.contains("darahrutin") || key1.contains("cbc") || key1.contains("hemogram") || key1.contains("darahlengkap")
        val isCbc2 = key2.contains("darahrutin") || key2.contains("cbc") || key2.contains("hemogram") || key2.contains("darahlengkap")
        if (isCbc1 && isCbc2) return true

        val isAgd1 = key1.contains("agd") || key1.contains("abg") || key1.contains("analisisgasdarah")
        val isAgd2 = key2.contains("agd") || key2.contains("abg") || key2.contains("analisisgasdarah")
        if (isAgd1 && isAgd2) return true

        val isRontgen1 = key1.contains("rontgen") || key1.contains("xray") || key1.contains("foto")
        val isRontgen2 = key2.contains("rontgen") || key2.contains("xray") || key2.contains("foto")
        if (isRontgen1 && isRontgen2 && (key1.contains("thorax") || key1.contains("torak")) && (key2.contains("thorax") || key2.contains("torak"))) return true

        return false
    }
}

