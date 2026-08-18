package com.example.data.repository

import com.example.data.model.CitoActionFeedback
import com.example.data.model.CitoImpactType
import com.example.data.model.ClinicalCase

object CitoEvaluator {

    suspend fun evaluateActionWithAI(
        actionTitle: String,
        case: ClinicalCase,
        citoLogs: List<CitoActionFeedback> = emptyList()
    ): CitoActionFeedback {
        return com.example.data.remote.GeminiService.evaluateCitoActionWithAI(
            case = case,
            actionName = actionTitle,
            priorCitoLogs = citoLogs
        )
    }

    fun evaluateActionFallback(
        actionTitle: String,
        case: ClinicalCase,
        citoLogs: List<CitoActionFeedback> = emptyList()
    ): CitoActionFeedback {
        val title = actionTitle.lowercase().trim()
        val diag = case.trueDiagnosis.lowercase().trim()
        val complaint = case.chiefComplaint.lowercase().trim()
        val recTreatment = case.recommendedTreatment.lowercase().trim()

        // -------------------------------------------------------------
        // 1. FATAL CONTRAINDICATIONS (Sangat Fatal / Mematikan)
        // -------------------------------------------------------------

        // Case A: Tension Pneumothorax + ETT/Positive Pressure Ventilation (BVM/Ambu Bag) without prior needle decompression
        if ((diag.contains("pneumothorax") || complaint.contains("pneumothorax") || complaint.contains("suara napas menghilang")) &&
            (title.contains("intubasi") || title.contains("bvm") || title.contains("ambu bag") || title.contains("ventilasi bag")) &&
            !recTreatment.contains("needle")
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.FATAL_COLLAPSE,
                timeDeltaSeconds = -999,
                pointPenalty = 20,
                message = "🚨 HENTI JANTUNG FATAL (PEA Arrest): Ventilasi tekanan positif masif meningkatkan tekanan intratorakal hebat, menghentikan aliran darah balik ke jantung, dan memicu henti jantung mendadak!",
                detailedExplanation = "Pada kecurigaan Tension Pneumothorax, ventilasi tekanan positif (BVM/ETT) sebelum dekompresi jarum sangat kontraindikasi karena meningkatkan tekanan intratorakal masif yang menekan vena cava, meniadakan venous return (preload), dan memicu PEA Cardiac Arrest.",
                updatedVitalsNote = "TD: 0/0 mmHg, Nadi: Tidak Teraba, SpO2: 0%"
            )
        }

        // Case B: Hypoglycemia + Insulin IV
        if ((diag.contains("hipoglikemia") || complaint.contains("hipoglikemia") || complaint.contains("gds 35") || complaint.contains("koma hipoglikemik")) &&
            title.contains("insulin")
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.FATAL_COLLAPSE,
                timeDeltaSeconds = -999,
                pointPenalty = 20,
                message = "🚨 KOMA METABOLIK DALAM & HENTI JANTUNG: Injeksi insulin intravena memicu penurunan substrat glukosa otak secara kritis hingga kerusakan neuron ireversibel!",
                detailedExplanation = "Pemberian insulin pada pasien yang mengalami krisis hipoglikemia berat memicu neuroglikopenia masif, koma dalam, dan henti jantung fatal.",
                updatedVitalsNote = "GDS: < 10 mg/dL, Henti Jantung"
            )
        }

        // Case C: Hypotension / Cardiogenic Shock + ISDN / Nitrates
        if ((diag.contains("stemi") || diag.contains("syok kardiogenik") || complaint.contains("td 80") || case.td.contains("80") || case.td.contains("70")) &&
            (title.contains("isdn") || title.contains("nitrogli"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.FATAL_COLLAPSE,
                timeDeltaSeconds = -999,
                pointPenalty = 20,
                message = "🚨 KOLAPS SIRKULASI SEGERA: Pemberian vasodilator pada kondisi hipotensi berat/syok memicu venodilatasi perifer mendadak dan asistol!",
                detailedExplanation = "Vasodilator nitrat/ISDN dikontraindikasikan mutlak pada pasien dengan hipotensi berat (TD sistolik < 90 mmHg) karena memotong venous return dan memicu kolaps sirkulasi refrakter.",
                updatedVitalsNote = "TD: 30/0 mmHg, Nadi: 25 bpm (Agonal), Kolaps"
            )
        }

        // Case D: Anaphylactic Shock + Beta-Blockers or delay of Epinephrine
        if ((diag.contains("anafilak") || complaint.contains("anafilak") || complaint.contains("gatal bengkak bibir")) &&
            title.contains("sedatif")
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.FATAL_COLLAPSE,
                timeDeltaSeconds = -999,
                pointPenalty = 20,
                message = "🚨 KOLAPS JALAN NAPAS TOTAL: Pemberian sedatif pada pasien dengan distres napas berat menekan refleks napas spontan hingga henti napas!",
                detailedExplanation = "Pemberian sedatif saat terjadi obstruksi jalan napas atas dan distres respirasi akut menekan kendali pernapasan spontan pasien, memicu asfiksia total.",
                updatedVitalsNote = "Apnea total, SpO2: 0%"
            )
        }

        // -------------------------------------------------------------
        // 2. CHECK IF TTV IS ALREADY STABILIZED
        // -------------------------------------------------------------
        val vitals = VitalSignsManager.computeVitals(case = case, citoActionLogs = citoLogs, isEmergencyMode = true)
        val hasPriorStabilized = citoLogs.any { it.impactType == CitoImpactType.STABILIZED }
        val isTtvAlreadyStabilized = hasPriorStabilized && vitals.systolic >= 90 && vitals.spO2 >= 92 && vitals.hr > 0 && vitals.hr in 60..100

        if (isTtvAlreadyStabilized) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.UNINDICATED,
                timeDeltaSeconds = 0,
                pointPenalty = 3,
                message = "⚠️ TTV PASIEN SUDAH STABIL (-3 Pts, +0 Detik WAKTU CITO): Tanda vital pasien telah berada dalam rentang stabil (${vitals.systolic}/${vitals.diastolic} mmHg, Nadi ${vitals.hr}x/mnt, SpO2 ${vitals.spO2}%). Intervensi tambahan '$actionTitle' tidak diperlukan saat ini.",
                detailedExplanation = "Intervensi '$actionTitle' dilakukan saat parameter hemodinamik pasien telah stabil. Pada tatalaksana kegawatdaruratan, intervensi berlebih non-indikasi berisiko menimbulkan komplikasi iatrogenik.",
                updatedVitalsNote = "TTV Stabil: TD ${vitals.systolic}/${vitals.diastolic} mmHg, SpO2 ${vitals.spO2}%"
            )
        }

        // -------------------------------------------------------------
        // 3. HARMFUL / CONTRAINDICATED / DANGEROUS PROCEDURES (-60s WAKTU, -5 PTS PENALTY)
        // -------------------------------------------------------------

        // Irrelevant Defibrillation / Cardioversion on non-arrhythmia / normal rhythm cases
        if ((title.contains("defibrilasi") || title.contains("kardioversi") || title.contains("adenosin")) &&
            !diag.contains("vf") && !diag.contains("vt") && !diag.contains("svt") && !diag.contains("henti jantung") && !complaint.contains("tidak teraba")
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.HARMFUL,
                timeDeltaSeconds = -60,
                pointPenalty = 5,
                message = "⚠️ PROSEDUR KONTRAINDIKASI (-60 Detik, -5 Pts): Pemberian kejut listrik / obat anti-aritmia tanpa indikasi mengacaukan sistem konduksi miokard!",
                detailedExplanation = "Defibrilasi / kardioversi listrik hanya diindikasikan pada aritmia letal (VF / pulseless VT). Pemberian pada irama normal berisiko memicu disritmia letal sekunder.",
                updatedVitalsNote = "Spasme miokard iatrogenik, irama ireguler sementara, TD tidak stabil"
            )
        }

        // Irrelevant Needle Thoracocentesis / Chest Tube on non-pneumothorax cases
        if ((title.contains("needle") || title.contains("wsd") || title.contains("chest tube") || title.contains("cricothyroidotomy")) &&
            !diag.contains("pneumothorax") && !diag.contains("hematotoraks") && !diag.contains("efusi") && !complaint.contains("pneumothorax")
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.HARMFUL,
                timeDeltaSeconds = -60,
                pointPenalty = 5,
                message = "⚠️ TINDAKAN INVASIF BERBAHAYA (-60 Detik, -5 Pts): Penusukan dinding dada/leher tanpa indikasi memicu trauma jaringan dan perdarahan lokal!",
                detailedExplanation = "Tindakan dekompresi toraks invasif pada rongga pleura normal berisiko merusak parenkim paru dan pembuluh darah interkostal, menimbulkan pneumotoraks/hemotoraks iatrogenik.",
                updatedVitalsNote = "Trauma dinding dada iatrogenik, pasien gelisah"
            )
        }

        // Irrelevant Tourniquet / Pelvic Binder on non-trauma / non-bleeding cases
        if ((title.contains("tourniquet") || title.contains("pelvic") || title.contains("spalk")) &&
            !diag.contains("trauma") && !diag.contains("fraktur") && !diag.contains("perdarahan") && !complaint.contains("darah") && !complaint.contains("trauma")
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.HARMFUL,
                timeDeltaSeconds = -60,
                pointPenalty = 5,
                message = "⚠️ KONTRAINDIKASI (-60 Detik, -5 Pts): Pemasangan fiksasi/torniket tanpa perdarahan mengganggu perfusi sirkulasi perifer!",
                detailedExplanation = "Tourniquet hanya diindikasikan pada perdarahan eksternal ekstremitas masif yang mengancam nyawa. Pemasangan tanpa indikasi menyebabkan iskemia jaringan distal.",
                updatedVitalsNote = "Restriksi vaskular lokal, waktu resusitasi terbuang"
            )
        }

        // ADHF / Edema Paru Akut + Fluid Bolus 1000ml or Trendelenburg
        if ((diag.contains("gagal jantung") || diag.contains("adhf") || complaint.contains("edema paru") || complaint.contains("rhonchi")) &&
            (title.contains("kristaloid") || title.contains("fluid") || title.contains("trendelenburg"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.HARMFUL,
                timeDeltaSeconds = -60,
                pointPenalty = 5,
                message = "⚠️ MEMPERBERAT DISTRES PERNAPASAN (-60 Detik, -5 Pts): Pemberian beban cairan berlebih memperparah penumpukan cairan di rongga alveoli paru!",
                detailedExplanation = "Pemberian cairan kristaloid cepat pada kondisi edema paru meningkatkan tekanan kapiler baji paru (PCWP), memperburuk transudasi cairan ke alveolus dan memperparah hipoksemia.",
                updatedVitalsNote = "SpO2: 78%, Rhonchi basah kasar meningkat (+/+)"
            )
        }

        // Acute Ischemic Stroke + Rapid Aggressive Antihypertensive
        if ((diag.contains("stroke iskemik") || complaint.contains("stroke")) &&
            (title.contains("isdn") || title.contains("nitrogli"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.HARMFUL,
                timeDeltaSeconds = -60,
                pointPenalty = 5,
                message = "⚠️ MEMPERBURUK PERFUSI OTAK (-60 Detik, -5 Pts): Menurunkan tekanan darah secara agresif menurunkan perfusi jaringan serebral!",
                detailedExplanation = "Penurunan tekanan darah secara drastis pada fase akut stroke iskemik menurunkan Cerebral Perfusion Pressure (CPP) dan memperluas zona penumbra infark serebri.",
                updatedVitalsNote = "Defisit neurologis memburuk, GCS 13 -> 10"
            )
        }

        // General Asthma / Respiratory Distress + Sedatives / Diazepam
        if ((diag.contains("asma") || diag.contains("ppok") || complaint.contains("sesak")) &&
            (title.contains("diazepam") || title.contains("sedatif"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.HARMFUL,
                timeDeltaSeconds = -60,
                pointPenalty = 5,
                message = "⚠️ DEPRESI PERNAPASAN (-60 Detik, -5 Pts): Pemberian obat penenang pada sesak napas akut menekan dorongan napas spontan!",
                detailedExplanation = "Sedatif golongan benzodiazepin menekan pusat respirasi di batang otak, menghilangkan dorongan kompensasi pernapasan pada pasien yang sedang mengalami kelelahan otot napas.",
                updatedVitalsNote = "RR: 8x/menit, SpO2: 84%"
            )
        }

        // -------------------------------------------------------------
        // 4. HIGHLY EFFECTIVE STABILIZATION PROCEDURES (+60s to +120s, 0 PTS PENALTY)
        // -------------------------------------------------------------

        // Tension Pneumothorax + Needle Decompression / WSD
        if ((diag.contains("pneumothorax") || complaint.contains("pneumothorax")) &&
            (title.contains("needle") || title.contains("dekompresi") || title.contains("wsd") || title.contains("chest tube"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 120,
                pointPenalty = 0,
                message = "⚡ DEKOMPRESI TORAKS BERHASIL (+120 Detik / +2 Menit)! Udara bertekanan positif terlepas, dinding dada mengembang simetris, dan sirkulasi kembali pulih!",
                detailedExplanation = "Dekompresi jarum cito menusuk spatium intercostale melepaskan akumulasi udara bertekanan, memulihkan venous return ke jantung dan menstabilkan hemodinamik pada Tension Pneumothorax.",
                updatedVitalsNote = "SpO2: 96%, TD: 110/70 mmHg, RR: 20x/mnt"
            )
        }

        // Anaphylaxis + Epinephrine IM
        if ((diag.contains("anafilak") || complaint.contains("anafilak") || complaint.contains("gatal bengkak bibir")) &&
            (title.contains("epinefrin") || title.contains("adrenalin"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 120,
                pointPenalty = 0,
                message = "⚡ RESUSITASI FARMAKOLOGIS BERHASIL (+120 Detik / +2 Menit)! Spasme saluran napas mereda, suara stridor menghilang, dan tekanan darah meningkat!",
                detailedExplanation = "Epinefrin IM bekerja sebagai agonis alfa-1 (vasokonstriksi perifer membalikkan hipotensi) dan beta-2 (bronkodilatasi kuat meredakan edema laring), tatalaksana lini pertama pada syok anafilaksis.",
                updatedVitalsNote = "TD: 110/70 mmHg, SpO2: 98%, Stridor mereda"
            )
        }

        // Hypoglycemia + Dextrose 40%
        if ((diag.contains("hipoglikemia") || complaint.contains("hipoglikemia") || complaint.contains("gds")) &&
            (title.contains("dextrose") || title.contains("gds"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 90,
                pointPenalty = 0,
                message = "⚡ PEMULIHAN SUBSTRAT METABOLIK (+90 Detik / +1.5 Menit)! Kadar glukosa darah terisi kembali, kesadaran pasien membaik secara progresif!",
                detailedExplanation = "Injeksi bolus Dextrose 40% intravena menyediakan glukosa cepat bagi parenkim otak, mencegah kerusakan neurologis permanen akibat krisis hipoglikemia.",
                updatedVitalsNote = "GDS: 130 mg/dL, Pasien sadar penuh (GCS 15)"
            )
        }

        // Cardiac Arrest / Lethal Arrhythmia + Defib / CPR / Amiodarone
        if ((diag.contains("vf") || diag.contains("vt") || diag.contains("henti jantung") || complaint.contains("tidak teraba")) &&
            (title.contains("defibrilasi") || title.contains("rjp") || title.contains("cpr") || title.contains("amiodaron"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 120,
                pointPenalty = 0,
                message = "⚡ RESUSITASI JANTUNG PARU BERHASIL (+120 Detik / +2 Menit)! Sirkulasi spontan kembali (ROSC), denyut nadi karotis teraba kuat!",
                detailedExplanation = "Kombinasi kompresi dada berkualitas tinggi dan defibrilasi dini menghentikan aritmia letal (VF/VT) dan mengembalikan denyut sirkulasi spontan yang efektif.",
                updatedVitalsNote = "Nadi teraba 88 bpm, TD: 105/65 mmHg"
            )
        }

        // Status Epilepticus + Diazepam IV
        if ((diag.contains("epilepsi") || diag.contains("kejang") || complaint.contains("kejang")) &&
            (title.contains("diazepam") || title.contains("suppositoria"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 90,
                pointPenalty = 0,
                message = "⚡ KEJANG TERKENDALI (+90 Detik / +1.5 Menit)! Spasme otot dan bangkitan motorik berhasil dihentikan, jalan napas kini paten!",
                detailedExplanation = "Diazepam IV bekerja memperkuat neurotransmiter inhibisi GABA di susunan saraf pusat untuk memutus cetusan epileptogenik dan mencegah hipoksia serebral berkepanjangan.",
                updatedVitalsNote = "Kejang berhenti, RR: 18x/mnt, SpO2: 97%"
            )
        }

        // STEMI / Acute Coronary + O2 / ISDN / Dual Antiplatelet / Morphin
        if ((diag.contains("stemi") || diag.contains("infark") || complaint.contains("nyeri dada")) &&
            (title.contains("oksigen") || title.contains("isdn") || title.contains("iv line") || title.contains("morphin"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 90,
                pointPenalty = 0,
                message = "⚡ OPTIMALISASI OKSIGENASI MIOKARD (+90 Detik / +1.5 Menit)! Beban awal jantung menurun, perfusi mikrovaskular membaik, nyeri dada berkurang!",
                detailedExplanation = "Terapi oksigenasi terarah dan vasodilator koroner menurunkan konsumsi oksigen miokardium (MVO2) serta memperbaiki perfusi pada sindrom koroner akut.",
                updatedVitalsNote = "SpO2: 98%, Nyeri dada mereda (VAS 8 -> 4)"
            )
        }

        // Massive Bleeding / Shock + Tourniquet / Fluids / Transfusion / TXA
        if ((diag.contains("syok") || diag.contains("perdarahan") || complaint.contains("pendarahan") || complaint.contains("fraktur") || complaint.contains("trauma")) &&
            (title.contains("tourniquet") || title.contains("txa") || title.contains("transfusi") || title.contains("kristaloid") || title.contains("2 line"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 90,
                pointPenalty = 0,
                message = "⚡ PENGENDALIAN PERDARAHAN & RESUSITASI VOLUME (+90 Detik / +1.5 Menit)! Kehilangan darah terhenti, volume sirkulasi efektif terisi, akral mulai hangat!",
                detailedExplanation = "Hemostasis cepat dan resusitasi cairan kristaloid/transfusi memulihkan volume intravaskular, mengoreksi syok hipovolemik, dan menjaga perfusi organ target.",
                updatedVitalsNote = "TD: 105/65 mmHg, Nadi: 90 bpm, Akral hangat"
            )
        }

        // Asthma / COPD + Nebulizer / Steroid
        if ((diag.contains("asma") || diag.contains("ppok") || complaint.contains("mengi") || complaint.contains("sesak")) &&
            (title.contains("nebulizer") || title.contains("steroid") || title.contains("nrm"))
        ) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 90,
                pointPenalty = 0,
                message = "⚡ RELAKSASI SALURAN NAPAS (+90 Detik / +1.5 Menit)! Spasme bronkus mereda, hambatan aliran udara berkurang, retraksi dada menurun!",
                detailedExplanation = "Bronkodilator inhalasi kerja cepat (SABA) merelaksasi otot polos bronkus dan kortikosteroid menurunkan inflamasi mukosa pada eksaserbasi asma/PPOK.",
                updatedVitalsNote = "SpO2: 97%, Wheezing berkurang signifikan"
            )
        }

        // Check if action matches case recommendedTreatment keywords
        val recommendedKeywords = recTreatment.split(",", ";", " ", "\n").filter { it.length > 3 }
        val matchesRecommended = recommendedKeywords.any { kw -> title.contains(kw) || kw.contains(title.take(8)) }

        if (matchesRecommended) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 90,
                pointPenalty = 0,
                message = "⚡ RESUSITASI TERINDIKASI TEPAT (+90 Detik / +1.5 Menit): Intervensi $actionTitle memberikan respon klinis positif dan menstabilkan kondisi umum!",
                detailedExplanation = "Tindakan '$actionTitle' merupakan bagian penting dari protokol resusitasi awal yang direkomendasikan untuk kasus klinis ini.",
                updatedVitalsNote = "Tanda vital pasien dalam pemulihan bertahap."
            )
        }

        // Generic supportive procedure (Airway, O2, IV line, Neck Collar, Blanket)
        if (title.contains("oksigen") || title.contains("iv line") || title.contains("head-tilt") || title.contains("suction") || title.contains("collar") || title.contains("selimut") || title.contains("opa")) {
            return CitoActionFeedback(
                actionTitle = actionTitle,
                impactType = CitoImpactType.STABILIZED,
                timeDeltaSeconds = 60,
                pointPenalty = 0,
                message = "👍 PROSEDUR SUPORTIF TEPAT (+60 Detik / +1 Menit): Intervensi $actionTitle membantu menjaga patensi jalan napas dan kestabilan awal pasien.",
                detailedExplanation = "Prosedur suportif ABCDE '$actionTitle' memberikan dasar proteksi jalan napas dan oksigenasi sebelum intervensi definitif diaplikasikan.",
                updatedVitalsNote = "Tanda vital pasien stabil."
            )
        }

        // Default: Irrelevant or unindicated procedure for this specific diagnosis
        return CitoActionFeedback(
            actionTitle = actionTitle,
            impactType = CitoImpactType.UNINDICATED,
            timeDeltaSeconds = 0,
            pointPenalty = 3,
            message = "⚠️ TINDAKAN TIDAK DIINDIKASIKAN (-3 Pts, +0 Detik): Intervensi '$actionTitle' tidak memiliki indikasi medis pada kondisi klinis pasien saat ini.",
            detailedExplanation = "Intervensi '$actionTitle' tidak memiliki indikasi patofisiologis pada kasus ini dan berisiko menunda penanganan kegawatdaruratan utama.",
            updatedVitalsNote = "Kondisi umum pasien belum menunjukkan perbaikan nyata."
        )
    }
}
