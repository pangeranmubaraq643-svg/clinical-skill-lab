package com.example.data.repository

import com.example.data.model.ClinicalCase
import com.example.data.model.ExamCategory
import com.example.data.model.ExamItem

object BuiltInCases {

    private val rawCases = listOf(
        // 1. Kardiologi: STEMI Inferior / Anteroseptal
        ClinicalCase(
            id = "CARDIO-001",
            organSystem = "Kardiologi",
            title = "Nyeri Dada Hebat Menjalar ke Lengan Kiri",
            patientAge = 54,
            patientGender = "Laki-laki",
            patientOccupation = "Pegawai Negeri Sipil (PNS)",
            generalAppearance = "Tampak sakit berat, memegang dada kiri (Levine sign (+)), berkeringat dingin (diaphoresis), gelisah",
            chiefComplaint = "Nyeri dada sebelah kiri sejak 2 jam lalu.",
            td = "145/90 mmHg",
            nadi = 104,
            rr = 24,
            suhu = 36.8,
            spO2 = 96,
            trueDiagnosis = "Infark Miokard Akut dengan ST Elevasi (STEMI) Anteroseptal",
            differentialDiagnoses = listOf("NSTEMI / Angina Pektoris Tak Stabil", "Diseksi Aorta", "Emboli Paru", "Pneumothorax", "Gastroesophageal Reflux Disease (GERD)"),
            patientPersonaInstruction = "Anda adalah Bpk. Bambang, usia 54 tahun. Dada terasa amat berat seperti ditindih beban 100kg. Keringat dingin mengucur deras. Mual ada. Merokok 2 bungkus sehari selama 30 tahun. Punya riwayat darah tinggi tapi jarang minum obat.",
            availableExams = listOf(
                ExamItem("E1", "Auskultasi Jantung & Paru", ExamCategory.PEMFIS, "S1 S2 tunggal ireguler takikardi, murmur (-), gallop (-), rhonchi basah halus di basal paru dextra et sinistra (-)", 0),
                ExamItem("E2", "Palpasi & Auskultasi Abdomen", ExamCategory.PEMFIS, "Supel, nyeri tekan epigastrium ringan (-), bising usus normal", 0),
                ExamItem("E3", "EKG 12-Lead", ExamCategory.IMAGING, "Sinus Takikardi 105 x/mnt, ST Elevasi > 2 mm di sandapan V1, V2, V3, V4 dengan Q patologis di V1-V2. Reciprocal ST Depresi di II, III, aVF.", 150000),
                ExamItem("E4", "Troponin I / T Kuantitatif", ExamCategory.LAB, "Troponin I: 4.8 ng/mL (N: < 0.04 ng/mL) - POSITIF MENINGKAT", 350000),
                ExamItem("E5", "CK-MB", ExamCategory.LAB, "CK-MB: 48 U/L (N: < 24 U/L)", 180000),
                ExamItem("E6", "Darah Rutin (CBC)", ExamCategory.LAB, "Hb 14.2 g/dL, Leukosit 12.800/uL, Trombosit 280.000/uL, Ht 42%", 95000),
                ExamItem("E7", "Gula Darah Sewaktu (GDS)", ExamCategory.LAB, "GDS: 168 mg/dL", 35000),
                ExamItem("E8", "Elektrolit (Na/K/Cl)", ExamCategory.LAB, "Na 138 mmol/L, K 4.1 mmol/L, Cl 101 mmol/L", 160000),
                ExamItem("E9", "Rontgen Thorax AP/PA", ExamCategory.IMAGING, "Cor konfigurasi normal, CTR 52%. Pulmo tak tampak infiltrat maupun edema paru.", 180000),
                ExamItem("E10", "CT Scan Thorax Kontras", ExamCategory.IMAGING, "Tidak tampak diseksi aorta. Arteri pulmonalis patent.", 1800000),
                ExamItem("E11", "Profil Lipid Lengkap", ExamCategory.LAB, "Kolesterol Total 240 mg/dL, LDL 175 mg/dL, HDL 38 mg/dL, TG 210 mg/dL", 250000),
                ExamItem("E12", "Echocardiography Bedside", ExamCategory.IMAGING, "Hypokinetic anteroseptal wall. EF 48%. Katup-katup intakt.", 650000)
            ),
            optimalExamNames = listOf("Auskultasi Jantung & Paru", "EKG 12-Lead", "Troponin I / T Kuantitatif", "Darah Rutin (CBC)", "Gula Darah Sewaktu (GDS)", "Rontgen Thorax AP/PA"),
            optimalCostEstimate = 810000,
            recommendedTreatment = "MONA / Fibrinolitik / PPCI: Oksigen nasal kanul 2-4 Lpm jika SpO2 < 90%, Aspirin kunyah (loading 160-325 mg), Clopidogrel (loading 300-600 mg), Nitrogliserin / ISDN 5mg sublingual (jika TD sistolik > 90 mmHg dan tidak memakai PDE5 inhibitor), Morfin IV 2.5-5mg jika nyeri dada persisten, Statin dosis tinggi (Atorvastatin 40mg), serta konsul/rujuk segera untuk Fibrinolitik atau Primary PCI < 120 menit.",
            kemenkesGuidelines = "PPK Kardiologi Kemenkes RI: Tata laksana awal Sindrom Koroner Akut (SKA) di Faskes Primer/Sekunder mengutamakan loading dual antiplatelet (Aspirin + Clopidogrel), nitrat, serta reperfusi dini (Fibrinolitik Streptokinase/Alteplase jika PCI tidak tersedia <2 jam).",
            isEmergencyCase = true
        ),

        // 2. Neurologi: Stroke Iskemik Akut
        ClinicalCase(
            id = "NEURO-001",
            organSystem = "Neurologi",
            title = "Kelemahan Mendadak Anggota Gerak Kanan & Bicara Pelo",
            patientAge = 62,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga",
            generalAppearance = "Tampak sakit sedang, kesadaran Compos Mentis (GCS 15), wajah asimetris, bicara pelo (disartria)",
            chiefComplaint = "Lemas pada anggota gerak tubuh sebelah kanan sejak 3 jam lalu.",
            td = "170/100 mmHg",
            nadi = 88,
            rr = 18,
            suhu = 36.6,
            spO2 = 98,
            trueDiagnosis = "Stroke Iskemik Akut Hemisfer Sinistra ONSET < 4.5 Jam",
            differentialDiagnoses = listOf("Stroke Hemoragik (Perdarahan Intraserebral)", "Transient Ischemic Attack (TIA)", "Hypoglycemic Hemiparesis", "Brain Tumor / Space Occupying Lesion"),
            patientPersonaInstruction = "Anak pasien yang menjawab: Ibu saya Bpk/Ibu, tadi pagi waktu bangun tidur tiba-tiba mau ke kamar mandi kaki kanannya lemes terus jatuh. Mulutnya merot ke kiri, ngomongnya pelo sengau. Punya riwayat hipertensi dan kolesterol tinggi tapi sering lupa minum obat.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Neurologis Terarah (GCS, N. Craniales, Motorik, Refleks)", ExamCategory.PEMFIS, "GCS E4V4M6 (14-15), N. VII & XII paresis dextra tipe sentral. Motorik dextra 2/2/2, sinistra 5/5/5. Refleks fisiologis dextra meningkat, Babinski dextra (+). NIHSS Score: 9", 0),
                ExamItem("E2", "CT Scan Kepala Tanpa Kontras (Non-Contrast Brain CT)", ExamCategory.IMAGING, "Tampak area hipodens samar pada regio kortiko-subkortikal lobus parieto-temporal sinistra. Tidak tampak perdarahan intraserebral maupun subaraknoid (Perdarahan NEGATIF). ASPECT score 9.", 1200000),
                ExamItem("E3", "Gula Darah Sewaktu (GDS) Cito", ExamCategory.LAB, "GDS: 135 mg/dL (Menyingkirkan hipoglikemia yang meniru gejala stroke)", 35000),
                ExamItem("E4", "Darah Rutin & Hemostasis (PT/APTT/INR)", ExamCategory.LAB, "Hb 13.5 g/dL, Leukosit 8.200/uL, Trombosit 240.000/uL, PT 11.2 detik, APTT 28.5 detik, INR 1.02", 280000),
                ExamItem("E5", "Elektrolit & Fungsi Ginjal (Ureum/Kreatinin)", ExamCategory.LAB, "Na 140 mEq/L, K 4.0 mEq/L, Ur 28 mg/dL, Cr 0.9 mg/dL", 220000),
                ExamItem("E6", "EKG 12-Lead", ExamCategory.IMAGING, "Sinus Ritim 85 x/menit, LVH (+), tidak tampak Fibrilasi Atrium", 150000),
                ExamItem("E7", "MRI Brain + MRA Kontras", ExamCategory.IMAGING, "MRI tampak DWI restriction di MCA sinistra territory.", 3500000),
                ExamItem("E8", "Pungsi Lumbal", ExamCategory.LAB, "Jernih, tidak tampak eritrosit. (Tidak diindikasikan)", 850000)
            ),
            optimalExamNames = listOf("Pemeriksaan Neurologis Terarah (GCS, N. Craniales, Motorik, Refleks)", "CT Scan Kepala Tanpa Kontras (Non-Contrast Brain CT)", "Gula Darah Sewaktu (GDS) Cito", "Darah Rutin & Hemostasis (PT/APTT/INR)", "EKG 12-Lead"),
            optimalCostEstimate = 1665000,
            recommendedTreatment = "1. Penanganan Jalan Napas & Oksigenasi jika SpO2 < 95%. 2. Evaluasi Trombolisis IV rTPA (Alteplase 0.9 mg/kgBB) jika memenuhi kriteria inklusi onset < 4.5 jam dan tanpa kontraindikasi. 3. Jika rTPA tidak diberikan/kontraindikasi: Berikan Antiplatelet Aspirin 160-325 mg per oral. 4. Kontrol Tekanan Darah: Jangan buru-buru menurunkan TD kecuali > 220/120 mmHg (jika non-trombolisis) atau > 185/110 mmHg (jika trombolisis). Gunakan Nicardipine IV jika perlu. 5. Neuroprotektan & Perawatan di Stroke Unit / ICU.",
            kemenkesGuidelines = "Pedoman Praktik Klinis Neurologi Kemenkes RI: Onset < 4.5 jam wajib dilakukan CT Scan Cito tanpa kontras untuk membedakan perdarahan vs infark, cito GDS untuk menyingkirkan 'stroke mimic', dan persiapan trombolisis intravena rTPA."
        ),

        // 3. Pulmonologi: Asma Eksaserbasi Akut Berat
        ClinicalCase(
            id = "PULMO-001",
            organSystem = "Pulmonologi",
            title = "Sesak Napas Berat Berbunyi Ngik-Ngik (Wheezing)",
            patientAge = 28,
            patientGender = "Perempuan",
            patientOccupation = "Mahasiswi",
            generalAppearance = "Tampak sesak berat, duduk membungkuk ke depan (tripod position), hanya mampu berbicara terputus per kata, sianosis bibir (-)",
            chiefComplaint = "Sesak napas berbunyi mengi sejak tadi malam.",
            td = "130/85 mmHg",
            nadi = 118,
            rr = 32,
            suhu = 37.0,
            spO2 = 91,
            trueDiagnosis = "Asma Bronkial Eksaserbasi Akut Derajat Berat",
            differentialDiagnoses = listOf("PPOK Eksaserbasi Akut", "Edema Paru Akut", "Anafilaksis", "Pneumothorax Spontan", "Aspirasi Benda Asing"),
            patientPersonaInstruction = "Pasien menjawab dengan napas tersengal-sengal: 'Sesak... banget... dok... mengi... dari semalam... pake inhaler... di rumah... udah tak... mempan...'",
            availableExams = listOf(
                ExamItem("E1", "Auskultasi Paru & Thorax", ExamCategory.PEMFIS, "Inspeksi: Retraksi interkostal (+), suprasternal (+). Auskultasi: Wheezing ekspiratori & inspiratori nyaring di seluruh lapang paru dextra et sinistra.", 0),
                ExamItem("E2", "Analisis Gas Darah (AGD / BGA)", ExamCategory.LAB, "pH 7.32, PaCO2 48 mmHg (Retensi CO2 / Exhaustion!), PaO2 68 mmHg, HCO3 24 mEq/L, SaO2 91%", 320000),
                ExamItem("E3", "Peak Expiratory Flow (APE / Peak Flow Meter)", ExamCategory.PEMFIS, "APE < 50% nilai prediksi (Derajat Berat)", 0),
                ExamItem("E4", "Darah Rutin & Hitung Jenis (Diff Count)", ExamCategory.LAB, "Hb 12.8 g/dL, Leukosit 9.500/uL, Eosinofil 6% (Eosinofilia relative)", 95000),
                ExamItem("E5", "Rontgen Thorax PA", ExamCategory.IMAGING, "Tampak hiperinflasi paru, sela iga melebar, diafragma mendatar. Tidak tampak infiltrat / pneumothorax.", 180000),
                ExamItem("E6", "CT Scan Thorax High Resolution (HRCT)", ExamCategory.IMAGING, "Airway thickening. (Tidak diindikasikan pada kondisi emergensi)", 2100000)
            ),
            optimalExamNames = listOf("Auskultasi Paru & Thorax", "Analisis Gas Darah (AGD / BGA)", "Peak Expiratory Flow (APE / Peak Flow Meter)", "Darah Rutin & Hitung Jenis (Diff Count)", "Rontgen Thorax PA"),
            optimalCostEstimate = 595000,
            recommendedTreatment = "1. Oksigenasi target SpO2 93-95% via nasal kanul / simple mask. 2. Nebulisasi SABA (Salbutamol 2.5-5 mg) + SAMA (Ipratropium Bromida 0.5 mg) tiap 20 menit dalam 1 jam pertama. 3. Kortikosteroid Sistemik IV (Metilprednisolon 40-60 mg IV atau Deksametason 5mg IV / Prednison oral 40mg). 4. Injeksi Magnesium Sulfat (MgSO4) 2 gram IV drip dalam 20 menit jika respons buruk. 5. Pertimbangkan Rawat HInap / ICU jika tidak ada perbaikan.",
            kemenkesGuidelines = "Pedoman PDPI / Kemenkes RI: Asma eksaserbasi berat ditandai bicara per kata, posisi tripod, RR > 30, HR > 120, SpO2 < 92%. Terapi utama adalah nebulisasi SABA kombinasi Ipratropium, kortikosteroid sistemik cepat, serta oksigen tertarget."
        ),

        // 4. Gastroenterohepatologi: Demam Tifoid dengan Komplikasi / Appendisitis Akut
        ClinicalCase(
            id = "GASTRO-001",
            organSystem = "Gastroenterohepatologi",
            title = "Nyeri Perut Kanan Bawah Mendadak & Demam",
            patientAge = 22,
            patientGender = "Laki-laki",
            patientOccupation = "Mahasiswa",
            generalAppearance = "Tampak kesakitan, berjalan membungkuk sambil memegang perut kanan bawah, suhu tubuh teraba hangat",
            chiefComplaint = "Nyeri perut hebat awalnya di ulu hati berpindah ke perut kanan bawah sejak 18 jam lalu, disertai mual, muntah 2 kali, dan demam.",
            td = "120/80 mmHg",
            nadi = 98,
            rr = 20,
            suhu = 38.3,
            spO2 = 99,
            trueDiagnosis = "Appendisitis Akut Uncomplicated (Skor Alvarado 8)",
            differentialDiagnoses = listOf("Limfadenitis Mesenterika", "Divertikulitis Meckel", "Demam Tifoid", "Urolitiasis / Kolik Ureter Dextra", "Kolesistitis Akut"),
            patientPersonaInstruction = "Pasien: Kemarin sore awalnya perut tengah ulu hati rasanya melilit kayak maag Dok. Tapi tadi malam nyerinya makin parah dan pindah ke kanan bawah. Kalau ditekan atau paha kanan ditekuk rasanya sakit banget. Nggak nafsu makan sama sekali.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Abdomen Spesifik (McBurney, Rovsing, Psoas, Obturator Sign)", ExamCategory.PEMFIS, "Nyeri tekan & nyeri lepas McBurney (+), Defans muskular (+), Rovsing sign (+), Psoas sign (+), Obturator sign (+). Alvarado Score = 8.", 0),
                ExamItem("E2", "Darah Rutin (CBC) + Hitung Jenis", ExamCategory.LAB, "Hb 14.0 g/dL, Leukosit 15.400/uL (Leukositosis!), Neutrofil Batang & Segmen 84% (Shift to the left), Trombosit 260.000/uL", 95000),
                ExamItem("E3", "USG Abdomen / Appendiks", ExamCategory.IMAGING, "Tampak struktur tubular non-compressible blind-ended pada regio iliaka dextra dengan diameter outer-to-outer 8.2 mm (>6mm), target sign (+), aperistaltik (+). Kesan: Apendisitis Akut.", 450000),
                ExamItem("E4", "Urinalisis Lengkap", ExamCategory.LAB, "Eritrosit 0-1/LPB, Leukosit 1-2/LPB, Nitrit (-). Menyingkirkan ISK/Urolitiasis.", 50000),
                ExamItem("E5", "Tes Widal / IgM Anti-Salmonella (Tubex)", ExamCategory.LAB, "Tubex TF score: 2 (Negatif / Tidak signifikan)", 180000),
                ExamItem("E6", "CT Scan Abdomen Non-Kontras", ExamCategory.IMAGING, "Appendix terdistensi 9mm dengan fat stranding periappendiceal. (Diindikasi jika USG equivocal)", 1800000)
            ),
            optimalExamNames = listOf("Pemeriksaan Abdomen Spesifik (McBurney, Rovsing, Psoas, Obturator Sign)", "Darah Rutin (CBC) + Hitung Jenis", "USG Abdomen / Appendiks", "Urinalisis Lengkap"),
            optimalCostEstimate = 595000,
            recommendedTreatment = "1. Puasakan pasien (NPO - Nil Per Os) persiapan operasi. 2. Resusitasi Cairan IV Ringer Laktat / NaCl 0.9% 1500-2000 ml/24 jam. 3. Analgetik IV (Ketorolac 30mg IV / Parasetamol 1 gram IV). 4. Antibiotik Profilaksis Proksimal Operasi IV (Sefotaksim 1g IV / Seftriaxon 1g IV + Metronidazol 500mg IV drip). 5. Konsul Bedah Cito untuk Apendektomi (Cito Appendectomy).",
            kemenkesGuidelines = "Panduan Praktik Klinis Operatif Kemenkes RI: Apendisitis akut dengan Skor Alvarado >= 7 memerlukan investigasi USG abdomen konfirmasi dan tindakan bedah Apendektomi cito untuk mencegah perforasi dan peritonitis."
        ),

        // 5. Endokrinologi: Ketoasidosis Diabetik (KAD)
        ClinicalCase(
            id = "ENDO-001",
            organSystem = "Endokrinologi",
            title = "Penurunan Kesadaran, Napas Cepat & Bau Aseton (Kussmaul)",
            patientAge = 45,
            patientGender = "Perempuan",
            patientOccupation = "Wiraswasta",
            generalAppearance = "Tampak somnolen (GCS E3V3M5=11), napas dalam dan cepat (Kussmaul), tercium bau buah/aseton dari napas, tanda dehidrasi berat (mata cekung, turgor kulit lambat kembali)",
            chiefComplaint = "Badan semakin lemas, sering kencing dan haus sejak 3 hari lalu, muntah-muntah, dan mulai tidak nyambung diajak bicara sejak tadi pagi.",
            td = "90/60 mmHg",
            nadi = 124,
            rr = 34,
            suhu = 37.8,
            spO2 = 97,
            trueDiagnosis = "Ketoasidosis Diabetik (KAD) Berat ec Diabetes Melitus Tipe 2 / Tipe 1",
            differentialDiagnoses = listOf("Hyperosmolar Hyperglycemic State (HHS)", "Asidosis Laktat", "Uremia / Gagal Ginjal Akut", "Sepsis", "Stroke Iskemik"),
            patientPersonaInstruction = "Suami pasien menjawab: Istri saya punya diabetes Dok, tapi 4 hari ini obat metforminnya habis dan belum sempat beli. Kemarin mulai demam, lemas, kencing terus, terus muntah-muntah. Tadi pagi dipanggil-panggil udah merem terus dan napasnya ngos-ngosan bau harum manis ganjil.",
            availableExams = listOf(
                ExamItem("E1", "Gula Darah Sewaktu (GDS) Cito", ExamCategory.LAB, "GDS: 485 mg/dL (Hiperglikemia Tinggi > 250 mg/dL)", 35000),
                ExamItem("E2", "Analisis Gas Darah (AGD / BGA)", ExamCategory.LAB, "pH 7.12 (Asidosis berat < 7.30), PaCO2 22 mmHg (Kompensasi respiratorik), HCO3 9 mEq/L (Metabolik berat), Anion Gap = 21 mEq/L (High Anion Gap Acidosis)", 320000),
                ExamItem("E3", "Keton Urin / Keton Darah", ExamCategory.LAB, "Keton Urin: POSITIF (+++) / Beta-hydroxybutyrate darah 4.2 mmol/L", 80000),
                ExamItem("E4", "Elektrolit Lengkap (Na, K, Cl)", ExamCategory.LAB, "Na 130 mEq/L (Hyponatremia terkoreksi 136), K 5.4 mEq/L, Cl 98 mEq/L", 160000),
                ExamItem("E5", "Urinalisis & Fungsi Ginjal (Ureum/Kreatinin)", ExamCategory.LAB, "Glukosa Urin (++++), Keton (+++), Ureum 68 mg/dL, Kreatinin 1.8 mg/dL (Prerenal Azotemia)", 160000),
                ExamItem("E6", "Darah Rutin & EKG 12-Lead", ExamCategory.LAB, "Leukosit 18.200/uL, EKG Sinus Takikardi 125 x/mnt, gelombang T tinggi lancip (efek hiperkalemia tersembunyi).", 245000)
            ),
            optimalExamNames = listOf("Gula Darah Sewaktu (GDS) Cito", "Analisis Gas Darah (AGD / BGA)", "Keton Urin / Keton Darah", "Elektrolit Lengkap (Na, K, Cl)", "Urinalisis & Fungsi Ginjal (Ureum/Kreatinin)", "Darah Rutin & EKG 12-Lead"),
            optimalCostEstimate = 1000000,
            recommendedTreatment = "1. Resusitasi Cairan Cepat: NaCl 0.9% 1000 mL dalam 1 jam pertama, dilanjutkan 500-1000 mL/jam. 2. Terapi Insulin Cepat: Insulin Reguler IV bolus 0.1 IU/kgBB dilanjutkan drip kontinyu 0.1 IU/kgBB/jam. 3. Koreksi Kalium: Jika K < 3.3 mEq/L tunda insulin dan berikan KCL; jika K 3.3-5.3 mEq/L berikan 20-30 mEq KCL dalam cairan IV. 4. Koreksi Bikarbonat HANYA jika pH < 6.9. 5. Monitoring GDS tiap jam dan elektrolit/AGD tiap 2-4 jam.",
            kemenkesGuidelines = "Pedoman PERKENI / Kemenkes RI: Diagnosis KAD ditegakkan dengan trias Hiperglikemia (>250 mg/dL), Asidosis Metabolik (pH <7.3, HCO3 <18), dan Ketonemia/Ketonuria (+). Pilar utama tata laksana adalah Rehidrasi Agresif + Drip Insulin Kontinyu + Monitoring Elektrolit."
        ),

        // 6. Infeksi Tropis: Dengue Hemorrhagic Fever (DHF) / Demam Berdarah Dengue
        ClinicalCase(
            id = "TROPICAL-001",
            organSystem = "Infeksi Tropis",
            title = "Demam Tinggi Mendadak Hari Ke-4, Bintik Merah & Nyeri Sendi",
            patientAge = 19,
            patientGender = "Laki-laki",
            patientOccupation = "Pelajar",
            generalAppearance = "Tampak lemas, wajah kemerahan (flushing), tampak bintik-bintik merah di kulit lengan (ptekie), akral teraba hangat",
            chiefComplaint = "Demam tinggi mendadak terus menerus sejak 4 hari lalu, disertai nyeri kepala belakang mata, nyeri otot/sendi, mual, dan bintik merah di kulit.",
            td = "110/70 mmHg",
            nadi = 92,
            rr = 20,
            suhu = 38.5,
            spO2 = 98,
            trueDiagnosis = "Demam Berdarah Dengue (DBD / DHF) Derajat II Onset Hari Ke-4",
            differentialDiagnoses = listOf("Demam Dengue (DD / Dengue Fever)", "Demam Tifoid", "Malaria Falciparum", "Leptospirosis", "Chikungunya"),
            patientPersonaInstruction = "Pasien: Demamnya mendadak tinggi banget Dok sejak 4 hari lalu. Badan rasanya ngilu semua, kepala kayak mau pecah, mual dan kemaren sempet mimisan sekali dikit dari hidung. Bintik-bintik merah ini baru keluar kemarin.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Khas & Uji Rumple Leede (Tourniquet Test)", ExamCategory.PEMFIS, "Ptekie pada ekstremitas (+), Epistaksis spontan minimal (+), Uji Rumple Leede POSITIF (>20 ptekie dalam 1 inci persegi). Hepar teraba 1.5 cm bawah arkus kosta, nyeri tekan (+).", 0),
                ExamItem("E2", "Darah Rutin Serial (Hb, Ht, Leukosit, Trombosit)", ExamCategory.LAB, "Hb 15.8 g/dL (Meningkat), Ht 47% (Hemokonsentrasi > 20%), Leukosit 3.100/uL (Leukopenia), Trombosit 42.000/uL (Trombositopenia Berat < 100.000)", 95000),
                ExamItem("E3", "Serologi Dengue: Dengue NS1 Antigen & IgM/IgG Anti-Dengue", ExamCategory.LAB, "Dengue NS1 Ag: POSITIF, IgM Anti-Dengue: POSITIF (+), IgG Anti-Dengue: POSITIF (+)", 320000),
                ExamItem("E4", "USG Abdomen / Thorax Bedside", ExamCategory.IMAGING, "Tampak efusi pleura dextra minimal, penebalan dinding kandung empedu (gallbladder wall thickening) 4.2 mm. Menunjukkan bukti kebocoran plasma!", 450000),
                ExamItem("E5", "SGOT / SGPT", ExamCategory.LAB, "SGOT: 112 U/L, SGPT: 98 U/L (Transaminitis ringan khas Dengue)", 110000),
                ExamItem("E6", "Apusan Darah Tepi (Malaria Parasite / DDR)", ExamCategory.LAB, "Apusan darah: Plasmodium (-). Menyingkirkan malaria.", 75000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Khas & Uji Rumple Leede (Tourniquet Test)", "Darah Rutin Serial (Hb, Ht, Leukosit, Trombosit)", "Serologi Dengue: Dengue NS1 Antigen & IgM/IgG Anti-Dengue", "SGOT / SGPT"),
            optimalCostEstimate = 525000,
            recommendedTreatment = "1. Tirah baring total. 2. Terapi Cairan Isotonik IV (Ringer Laktat / NaCl 0.9%) dengan kecepatan rumatan + preservasi (6-7 ml/kgBB/jam) disesuaikan dengan kurva hematokrit & trombosit. 3. Antipiretik Parasetamol 500-1000 mg per oral (HINDARI NSAID/Aspirin/Ibuprofen karena memicu perdarahan!). 4. Pemantauan ketat TTV dan tanda bahaya (Warning Signs: nyeri perut hebat, muntah persisten, akumulasi cairan, perdarahan mukosa, gelisah/letergi) tiap 2-4 jam.",
            kemenkesGuidelines = "Pedoman Tata Laksana Dengue Kemenkes RI / WHO: Kriteria DBD mencakup demam 2-7 hari, fenomena perdarahan (ptekie/mimisan/Rumple Leede +), trombositopenia < 100.000/uL, dan bukti kebocoran plasma (hemokonsentrasi Ht naik >=20% atau efusi pleura/ascites)."
        ),

        // 7. Nefro-Urologi: Kolik Ureter / Nephrolithiasis
        ClinicalCase(
            id = "NEPHRO-001",
            organSystem = "Nefro-Urologi",
            title = "Nyeri Pinggang Kanan Menjalar ke Selangkangan",
            patientAge = 38,
            patientGender = "Laki-laki",
            patientOccupation = "Supir Truk Antar Kota",
            generalAppearance = "Tampak sangat gelisah, memegangi pinggang kanan, berguling-guling mencari posisi nyaman (kolik), keringat dingin",
            chiefComplaint = "Nyeri hebat melilit di pinggang kanan menjalar sampai ke buah zakar dan selangkangan sejak 4 jam lalu, BAK tampak keruh kemerahan.",
            td = "140/90 mmHg",
            nadi = 100,
            rr = 22,
            suhu = 36.9,
            spO2 = 99,
            trueDiagnosis = "Kolik Ureter Dextra ec Batu Ureter (Ureterolithiasis Dextra)",
            differentialDiagnoses = listOf("Pyelonefritis Akut", "Appendisitis Akut", "Nyeri Musculoskeletal (LBP)", "Aneurisma Aorta Abdominalis"),
            patientPersonaInstruction = "Pasien meliuk-liuk kesakitan: Aduh Dok, nggak tahan banget sakit pinggang kanan saya! Sakitnya kayak diplintir menjalar ke paha dan kemaluan bawah. Tadi kencing agak merah kecokelatan. Saya kurang minum air putih dan sering nahan kencing pas nyupir truk.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Pinggang & Ketok CVA", ExamCategory.PEMFIS, "Nyeri ketok Costovertebral Angle (CVA) Dextra POSITIF (+), Sinistra (-). Abdomen supel, nyeri tekan lokal regio flank dextra.", 0),
                ExamItem("E2", "Urinalisis Sedimen Cito", ExamCategory.LAB, "Warna kuning keruh, Eritrosit 20-30/LPB (Hematuria mikroskopik), Leukosit 2-4/LPB, Kristal Kalsium Oksalat (+)", 50000),
                ExamItem("E3", "BNO-IVP / CT Urografi Tanpa Kontras (NCCT Abdomen-Pelvis)", ExamCategory.IMAGING, "NCCT: Tampak gambaran radioopak/hyperdense (batu) berukuran 6.5 mm pada ureter dextra sepertiga distal dengan hidroureter dan hidronefrosis dextra derajat 1.", 1200000),
                ExamItem("E4", "USG Ginjal & Kandung Kemih (Urologi)", ExamCategory.IMAGING, "Tampak pelebaran sistem pelvokaliks (hidronefrosis) dextra ringan, tampak acoustic shadow pada vesicoureteric junction dextra.", 400000),
                ExamItem("E5", "Fungsi Ginjal (Ureum, Kreatinin) & Asam Urat", ExamCategory.LAB, "Ureum 32 mg/dL, Kreatinin 1.1 mg/dL, Asam Urat 7.8 mg/dL", 150000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Pinggang & Ketok CVA", "Urinalisis Sedimen Cito", "USG Ginjal & Kandung Kemih (Urologi)", "Fungsi Ginjal (Ureum, Kreatinin) & Asam Urat"),
            optimalCostEstimate = 600000,
            recommendedTreatment = "1. Penanganan Nyeri Akut: Antinyeri NSAID IV (Ketorolac 30mg IV atau Metamizole 1g IV) sebagai lini pertama mengurangi spasme ureter. 2. Antispasmodik IV (Hyoscine N-butylbromide / Buscopan 20mg IV). 3. Medical Expulsive Therapy (MET): Alpha-blocker (Tamsulosin 0.4mg per oral 1x1) untuk mempercepat pelepasan batu ureter < 10mm. 4. Hidrasi adekuat 2-3 liter per hari. 5. Konsul Urologi jika nyeri tidak tertangani, ada sepsis/pyelonefritis, atau batu > 10mm (rencana ESWL / URS).",
            kemenkesGuidelines = "Pedoman Panduan Praktik Klinis Urologi Indonesia / Kemenkes RI: Nyeri kolik pinggang menjalar ke groin dengan hematuria khas untuk urolitiasis. NSAID intravena adalah standar emas antinyeri kolik ureter. Tamsulosin membantu pasase batu spontan."
        ),
        // 8. Pediatri / Kesehatan Anak: Kejang Demam Kompleks
        ClinicalCase(
            id = "PEDIATRI-001",
            organSystem = "Pediatri (Kesehatan Anak)",
            title = "Anak Kejang Saat Demam Tinggi",
            patientAge = 2,
            patientGender = "Laki-laki",
            patientOccupation = "Anak Balita",
            generalAppearance = "Tampak kelelahan, pasca kejang (post-ictal), rewel, suhu tubuh sangat tinggi",
            chiefComplaint = "Anak kejang seluruh tubuh selama 3 menit saat demam tinggi 39.5 C di rumah 1 jam lalu.",
            td = "95/60 mmHg",
            nadi = 130,
            rr = 28,
            suhu = 39.2,
            spO2 = 97,
            trueDiagnosis = "Kejang Demam Sederhana (KDS) ec Infeksi Saluran Napas Atas (ISPA)",
            differentialDiagnoses = listOf("Kejang Demam Kompleks", "Meningitis Bakterialis", "Ensefalitis", "Epilepsi Pertama Kali"),
            patientPersonaInstruction = "Ibu pasien sangat cemas: Dok! Anak saya tadi kejang matanya mendelik ke atas, tangan kakinya kelojotan 3 menit waktu badannya panas banget! Sekarang udah sadar tapi lemes dan nangis terus. Tolong Dok!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Terarah & Tanda Rangsang Meningeal", ExamCategory.PEMFIS, "GCS E4V5M6 (Sadar penuh setelah kejang). Kaku kuduk (-), Brudzinski I/II (-), Kernig (-). Farings hiperemis (+), TONSIL T2/T2 hiperemis.", 0),
                ExamItem("E2", "Darah Rutin (CBC)", ExamCategory.LAB, "Hb 12.1 g/dL, Leukosit 13.500/uL, Trombosit 290.000/uL", 95000),
                ExamItem("E3", "Gula Darah Sewaktu (GDS) Cito", ExamCategory.LAB, "GDS: 102 mg/dL (Menyingkirkan hipoglikemia)", 35000),
                ExamItem("E4", "Elektrolit (Na, K, Cl)", ExamCategory.LAB, "Na 137 mEq/L, K 4.1 mEq/L, Cl 100 mEq/L", 160000),
                ExamItem("E5", "Lumbal Pungsi (LP)", ExamCategory.LAB, "Jernih, sel 2/uL. (Hanya diindikasi jika ada kaku kuduk / curiga meningitis)", 850000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Terarah & Tanda Rangsang Meningeal", "Darah Rutin (CBC)", "Gula Darah Sewaktu (GDS) Cito"),
            optimalCostEstimate = 130000,
            recommendedTreatment = "1. Penanganan Kejang Saat Ini: Diazepam supp/rektal 5mg jika kejang berulang. 2. Antipiretik: Parasetamol sirup 10-15 mg/kgBB per kali pemberian (tiap 4-6 jam) atau Ibuprofen 10 mg/kgBB. 3. Kompres hangat pada lipat paha & ketiak. 4. Edukasi Orang Tua: Kejang demam sederhana tidak menyebabkan kerusakan otak, sediakan Diazepam rektal di rumah.",
            kemenkesGuidelines = "Pedoman Ikatan Dokter Anak Indonesia (IDAI) / Kemenkes RI: Kejang demam sederhana berlangsung < 15 menit, umum (tonik-klonik), tidak berulang dalam 24 jam. Kunci utama adalah mengatasi demam dan edukasi tenang untuk orang tua."
        ),

        // 9. Obstetri & Ginekologi: Preeklamsia Berat (PEB)
        ClinicalCase(
            id = "OBGYN-001",
            organSystem = "Obstetri & Ginekologi (Obgyn)",
            title = "Ibu Hamil Tua Sakit Kepala Hebat & Pandangan Kabur",
            patientAge = 32,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga (G1P0A0 Hamil 34 Minggu)",
            generalAppearance = "Tampak kesakitan memegang dahi, edematous pada wajah dan kedua kaki (Edema anasarka/pretibial +3), TD sangat tinggi",
            chiefComplaint = "Sakit kepala hebat di dahi, nyeri ulu hati, dan pandangan mata kabur sejak tadi pagi pada kehamilan 8 bulan.",
            td = "170/110 mmHg",
            nadi = 96,
            rr = 22,
            suhu = 36.7,
            spO2 = 98,
            trueDiagnosis = "Preeklamsia Berat (PEB) pada Hamil 34 Minggu",
            differentialDiagnoses = listOf("Eklamsia", "Superimposed Preeclampsia", "Hipertensi Gestasional", "HELLP Syndrome"),
            patientPersonaInstruction = "Pasien hamil memegang kepala: Dok, kepala saya pusing banget serasa mau pecah, mata saya rada kabur kayak ada bayangan putih, dan ulu hati rasanya nyeri melilit. Kaki saya makin bengkak 2 minggu ini.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Obstetri & Refleks Patella", ExamCategory.PEMFIS, "TFU 30 cm, DJJ 144 x/menit teratur. Edema pretibial +3/+3. Refleks patella hiperrefleks (+3/+3).", 0),
                ExamItem("E2", "Dipstick Protein Urin Cito", ExamCategory.LAB, "Proteinuria: POSITIF 3 (+++) / 300 mg/dL", 35000),
                ExamItem("E3", "Trombosit & Fungsi Hati (SGOT/SGPT, LDH)", ExamCategory.LAB, "Trombosit 165.000/uL, SGOT 58 U/L, SGPT 52 U/L, Bilirubin Normal (Menyingkirkan HELLP)", 210000),
                ExamItem("E4", "Fungsi Ginjal (Ureum/Kreatinin/Asam Urat)", ExamCategory.LAB, "Ureum 32 mg/dL, Kreatinin 1.2 mg/dL, Asam Urat 7.2 mg/dL", 150000),
                ExamItem("E5", "USG Fetomaternal Bedside", ExamCategory.IMAGING, "Janin tunggal hidup intrauterin, presentasi kepala, EFW 2100 gram, AFI cukup.", 350000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Obstetri & Refleks Patella", "Dipstick Protein Urin Cito", "Trombosit & Fungsi Hati (SGOT/SGPT, LDH)", "Fungsi Ginjal (Ureum/Kreatinin/Asam Urat)"),
            optimalCostEstimate = 395000,
            recommendedTreatment = "1. Pencegahan Kejang Eklamsia: MgSO4 (Magnesium Sulfat) 20% 4 gram IV bolus pelan (10-15 menit) dilanjutkan MgSO4 40% 6 gram dalam RL 500ml drip 1 gram/jam. Sediakan kalsium glukonas 10% sebagai antidotum! 2. Antihipertensi Cito: Nifedipin 10mg per oral (dapat diulang tiap 30 menit, maks 120mg/hari) target TD < 150/100 mmHg. 3. Terminasi / Rujuk Cito ke Rumah Sederajat dengan Fasilitas NICU/ICU.",
            kemenkesGuidelines = "Pedoman POGI / Kemenkes RI: Preeklamsia Berat ditandai TD >= 160/110 mmHg dengan Proteinuria +2/+3 atau gejala impending eklamsia (sakit kepala, pandangan kabur, nyeri epigastrium). Pemberian MgSO4 IV wajib segera dilakukan untuk mencegah kejang eklamsia."
        ),

        // 10. Muskuloskeletal: Artritis Gout Akut
        ClinicalCase(
            id = "MUSCULO-001",
            organSystem = "Muskuloskeletal & Reumatologi",
            title = "Nyeri & Bengkak Kemerahan Sendi Jempol Kaki",
            patientAge = 50,
            patientGender = "Laki-laki",
            patientOccupation = "Pedagang",
            generalAppearance = "Tampak merintih kesakitan, berjalan pincang, sendi MTP-1 pedis dextra tampak bengkak, merah, dan hangat",
            chiefComplaint = "Nyeri hebat mendadak pada jempol kaki kanan sejak bangun tidur tadi pagi, tidak bisa tersentuh kain atau berjalan.",
            td = "130/80 mmHg",
            nadi = 88,
            rr = 18,
            suhu = 37.4,
            spO2 = 99,
            trueDiagnosis = "Artritis Gout Akut (Podagra) MTP-1 Dextra",
            differentialDiagnoses = listOf("Artritis Septik", "Artritis Reumatoid (RA)", "Pseudogout / CPPD", "Selulitis Pedis"),
            patientPersonaInstruction = "Pasien: Waduh Dok, tadi malam saya habis makan jeroan dan emping di selamatan. Pas bangun tidur subuh jempol kaki kanan saya rasanya kayak terbakar! Disentuh selimut aja sakitnya minta ampun!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Sendi MTP-1 (Podagra)", ExamCategory.PEMFIS, "Eritema, edema, kalor, dan nyeri tekan sangat hebat pada sendi Metatarsophalangeal-1 (MTP-1) dextra.", 0),
                ExamItem("E2", "Kadar Asam Urat Darah (Serum Uric Acid)", ExamCategory.LAB, "Asam Urat: 9.8 mg/dL (Meningkat tinggi > 7.0 mg/dL)", 45000),
                ExamItem("E3", "Aspirasi Cairan Sendi (Arthrocentesis) & Mikroskop Cahaya Terpolarisasi", ExamCategory.LAB, "Cairan sendi: Kristal Monosodium Urat (MSU) berbentuk jarum dengan refringensi negatif kuat.", 350000),
                ExamItem("E4", "Rontgen Pedis Dextra AP/Oblique", ExamCategory.IMAGING, "Tampak pembengkakan jaringan lunak (soft tissue swelling) sekitar MTP-1 dextra. Belum tampak punched-out erosion.", 180000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Sendi MTP-1 (Podagra)", "Kadar Asam Urat Darah (Serum Uric Acid)"),
            optimalCostEstimate = 45000,
            recommendedTreatment = "1. Terapi Nyeri Akut Lini Pertama: NSAID dosis tinggi (Piroksikam 20mg / Ibuprofen 600mg 3x1 / Na Diklofenak 50mg 2x1) ATAU Kolkisin (0.5mg tiap 8 jam). 2. Kortikosteroid Oral (Metilprednisolon 16mg 2x1) jika ada kontraindikasi NSAID. 3. JANGAN berikan Allopurinol pada fase akut (dapat memperparah kejang serangan gout!). Allopurinol baru diberikan 2 minggu pasca serangan akut reda.",
            kemenkesGuidelines = "Pedoman Reumatologi Indonesia / Kemenkes RI: Serangan gout akut ditandai podagra MTP-1 yang timbul cepat pasca diet purin tinggi. Tata laksana serangan akut fokus pada antinyeri NSAID/Kolkisin, dilarang memulai Allopurinol di fase akut."
        ),

        // 11. Kegawatdaruratan & Trauma: Syok Hemoragik ec Ruptur Limpa (Kecelakaan)
        ClinicalCase(
            id = "EMERGENSI-001",
            organSystem = "Kegawatdaruratan & Trauma",
            title = "Kecelakaan Motor: Syok Hemoragik Akut ec Perdarahan Intraabdominal",
            patientAge = 23,
            patientGender = "Laki-laki",
            patientOccupation = "Mahasiswa",
            generalAppearance = "EMERGENSI CITO! Pasien tampak sangat pucat, akral dingin dan basah, penurunan kesadaran (GCS E3V4M5 = 12), gelisah, meringis kesakitan, distensi abdomen (+), jejas memar di kuadran kiri atas abdomen (Kehr sign (+)).",
            chiefComplaint = "Kecelakaan lalu lintas (tabrakan motor) 20 menit lalu. Pasien dibawa warga ke IGD dalam kondisi lemas berat, pucat, dan hampir pingsan.",
            td = "75/40 mmHg",
            nadi = 138,
            rr = 30,
            suhu = 35.6,
            spO2 = 91,
            trueDiagnosis = "Syok Hemoragik Derajat III-IV ec Ruptur Limpa / Trauma Tumpul Abdomen",
            differentialDiagnoses = listOf("Syok Neurogenik ec Trauma Servikal", "Ruptur Hati / Hepar", "Tension Pneumothorax", "Perdarahan Retroperitoneal", "Fraktur Pelvis dengan Perdarahan"),
            patientPersonaInstruction = "EMERGENSI KRITIS! Pasien sangat gelisah, bicara terputus-putus dan lemah: 'Aduh... dingin... haus banget... dada dan perut kiri saya kebal rasanya Dok...'. Pasien mengeluh nyeri hebat yang menjalar ke bahu kiri (Kehr's sign). PERINGATAN BILA TERLAMBAT BISA KOMA ATAU HEPATIC/CARDIAC ARREST!",
            availableExams = listOf(
                ExamItem("E1", "FAST Scan (Focused Assessment with Sonography for Trauma) Bedside", ExamCategory.IMAGING, "FAST POSITIF KUAT: Cairan bebas masif (perdarahan anekoik) di perisplenik (spasius splenorenal) dan kavum douglas / pelvis.", 180000),
                ExamItem("E2", "Pemeriksaan Fisik Abdomen & Kehr Sign", ExamCategory.PEMFIS, "Abdomen distensi, defans muskular (+), nyeri tekan dan lepas seluruh kuadran abdomen (paling berat kuadran kiri atas), Kehr sign (+) nyeri alih ke bahu kiri.", 0),
                ExamItem("E3", "Darah Rutin & Golongan Darah/Crossmatch Cito", ExamCategory.LAB, "Hb: 6.2 g/dL (KRITIS / DROP HEBAT!), Leukosit 16.500/uL, Trombosit 110.000/uL, Ht 19%. Golongan Darah O (+).", 150000),
                ExamItem("E4", "Analisis Gas Darah (AGD / Blood Gas)", ExamCategory.LAB, "pH 7.18, pCO2 28 mmHg, HCO3 11 mmol/L, BE -14 mmol/L (Asidosis Metabolik Berat ec Hipoperfusi / Syok)", 180000),
                ExamItem("E5", "Laktat Darah (Blood Lactate)", ExamCategory.LAB, "Laktat: 6.8 mmol/L (Sangat Meningkat > 4.0 mmol/L - Tanda Hipoperfusi Berat)", 120000),
                ExamItem("E6", "Rontgen Pelvis & Thorax Bedside Cito", ExamCategory.IMAGING, "Thorax: Tidak tampak pneumothorax / efusi pleura masif. Pelvis: Kontinuitas tulang pelvis intakt, tidak tampak fraktur.", 250000),
                ExamItem("E7", "CT Scan Abdomen Kontras Cito", ExamCategory.IMAGING, "Tampak laserasi grade IV pada parenkim limpa dengan extravasasi kontras aktif (active blushing) dan hematoma intraperitoneal masif.", 1800000)
            ),
            optimalExamNames = listOf("FAST Scan (Focused Assessment with Sonography for Trauma) Bedside", "Pemeriksaan Fisik Abdomen & Kehr Sign", "Darah Rutin & Golongan Darah/Crossmatch Cito", "Analisis Gas Darah (AGD / Blood Gas)"),
            optimalCostEstimate = 510000,
            recommendedTreatment = "TINDAKAN CITO RESUSITASI TRAUMA ATLS:\n1. O2 High Flow via Non-Rebreathing Mask (NRM 12-15 Lpm).\n2. Pasang 2 IV Line jarum besar (16G/18G) di vena perifer CITO -> Infus Cepat Cairan Kristaloid Hangat (RL 1000-2000 mL bolus Cito).\n3. Berikan Transfusi Darah Cito (PRC / Whole Blood) Golongan O Negatif / O Positif sampai darah matched siap.\n4. Berikan Asam Traneksamat (TXA) 1 gram IV bolus pelan diikuti 1 gram drip 8 jam.\n5. Pasang Kateter Urin (pantau urine output > 0.5 ml/kgBB/jam) & NGT.\n6. PANGGIL CITO SPESIALIS BEDAH / SP.B -> SEGERA LAPARATOMI EKSPLORASI & SPLENEKTOMI CITO!",
            kemenkesGuidelines = "Pedoman ATLS (Advanced Trauma Life Support) & Kemenkes RI: Pasien trauma tumpul abdomen dengan hemodinamik tidak stabil (syok hemoragik) dan FAST Scan positif adalah indikasi ABSOLUT Laparatomi Eksplorasi Cito tanpa menunda CT scan berlama-lama!"
        ),

        // 12. Kardiologi: Edema Paru Akut / Acute Decompensated Heart Failure (ADHF)
        ClinicalCase(
            id = "CARDIO-002",
            organSystem = "Kardiologi",
            title = "Sesak Napas Berat Saat Berbaring & Batuk Berbusa Merah Muda",
            patientAge = 60,
            patientGender = "Laki-laki",
            patientOccupation = "Pensiunan",
            generalAppearance = "Tampak sangat sesak (orthopnea), posisi duduk tegak, sianosis perifer, batuk Dahak berbusa warna kemerahan (pink frothy sputum), JVP meningkat R+4 cmH2O.",
            chiefComplaint = "Sesak napas hebat mendadak sejak 2 jam lalu saat tidur terlentang, tidak bisa tidur terlentang, disertai batuk berbusa warna merah muda.",
            td = "175/100 mmHg",
            nadi = 118,
            rr = 34,
            suhu = 36.5,
            spO2 = 85,
            trueDiagnosis = "Acute Decompensated Heart Failure (ADHF) / Edema Paru Akut ec Hypertensive Emergency",
            differentialDiagnoses = listOf("Asma Eksaserbasi Berat", "PPOK Eksaserbasi Akut", "Pneumonia Lobaris", "Emboli Paru Akut", "Sindrom Koroner Akut"),
            patientPersonaInstruction = "Pasien setengah terengah-engah: 'Dok... sesak... banget... nggak bisa... rebahan... kayak... tenggelam... batuk keluar... busa kemerahan... punya darah tinggi...'",
            availableExams = listOf(
                ExamItem("E1", "Auskultasi Jantung & Paru", ExamCategory.PEMFIS, "Paru: Rhonchi basah halus melimpah di seluruh lapang paru (basal hingga apex). Jantung: S1 S2 ireguler, gallop S3 (+). JVP R+4 cmH2O, edema pretibial (+/+).", 0),
                ExamItem("E2", "Rontgen Thorax AP Bedside", ExamCategory.IMAGING, "Kardiomegali CTR 62%, gambaran Bat-Wing appearance (edema alveolar bilateral), Kerley B lines (+), efusi pleura bilateral minimal.", 180000),
                ExamItem("E3", "EKG 12-Lead", ExamCategory.IMAGING, "Sinus Takikardi 120 x/m, LVH (Sokolow-Lyon (+)), ST Depresi di V5-V6 (Strain pattern).", 150000),
                ExamItem("E4", "NT-proBNP / BNP Kuantitatif", ExamCategory.LAB, "NT-proBNP: 4.850 pg/mL (Meningkat sangat tinggi > 300 pg/mL).", 450000),
                ExamItem("E5", "Analisis Gas Darah (AGD / ABG)", ExamCategory.LAB, "pH 7.28, PaCO2 50 mmHg, PaO2 55 mmHg, SaO2 84% (Gagal Napas Tipe 1 & 2).", 320000)
            ),
            optimalExamNames = listOf("Auskultasi Jantung & Paru", "Rontgen Thorax AP Bedside", "EKG 12-Lead", "Analisis Gas Darah (AGD / ABG)"),
            optimalCostEstimate = 650000,
            recommendedTreatment = "1. Posisikan pasien duduk tegak (High Fowler 90 derajat). 2. Oksigenasi High Flow via Non-Rebreathing Mask (NRM 10-15 Lpm) / CPAP-BiPAP jika SpO2 < 90%. 3. Furosemid IV 40-80 mg bolus cito. 4. Isosorbid Dinitrat (ISDN) / Nitroglisrin IV drip 10-200 mcg/mnt jika Sistolik > 110 mmHg. 5. Morfin IV 2.5 mg pelan jika cemas berat dan nyeri dada.",
            kemenkesGuidelines = "PPK Kardiologi PERKI / Kemenkes RI: Edema Paru Akut merupakan kegawatdaruratan kardiologi. Pilar utama adalah Oksigenasi, Furosemid IV, Vasodilator Nitrat IV (bila TD aman), dan posisi duduk tegak."
        ),

        // 13. Pulmonologi: Tuberkulosis (TB) Paru Aktif dengan Batuk Darah (Hemoptisis)
        ClinicalCase(
            id = "PULMO-002",
            organSystem = "Pulmonologi",
            title = "Batuk Berdarah, Demam Subfebris & Penurunan Berat Badan",
            patientAge = 35,
            patientGender = "Laki-laki",
            patientOccupation = "Buruh Pabrik",
            generalAppearance = "Tampak kurus (kaheksia), tampak pucat, batuk-batuk mengeluarkan dahak bercampur darah segar ± 50 cc.",
            chiefComplaint = "Batuk berdarah sejak 2 hari lalu.",
            td = "110/70 mmHg",
            nadi = 92,
            rr = 22,
            suhu = 37.8,
            spO2 = 96,
            trueDiagnosis = "Tuberkulosis (TB) Paru Terkonfirmasi Bakteriologis (BTA Positif) dengan Hemoptisis Ringan-Sedang",
            differentialDiagnoses = listOf("Bronkiektasis Terinfeksi", "Abses Paru", "Karsinoma Bronkogenik (Kanker Paru)", "Pneumonia Bakterialis Akut"),
            patientPersonaInstruction = "Pasien: Saya udah batuk 1 bulan lebih Dok, makin hari dahaknya bercak darah, dan kemarin keluar darah segar setengah gelas kecil. Badan makin kurus, baju jadi longgar, tiap malam keringatan dingin padahal nggak pakai kipas.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Paru & Thorax", ExamCategory.PEMFIS, "Inspeksi: Asimetris dada dextra agak tertinggal. Auskultasi: Rhonchi basah kasar di apeks paru dextra (lobus superior).", 0),
                ExamItem("E2", "TCM TB / GeneXpert MTB/RIF Dahak Cito", ExamCategory.LAB, "GeneXpert: Mycobacterium tuberculosis DETECTED HIGH, Rifampicin Resistance NOT DETECTED.", 250000),
                ExamItem("E3", "Pemeriksaan Sputum BTA Sewaktu-Pagi (SP)", ExamCategory.LAB, "Mikroskopis BTA: Sewaktu (+2), Pagi (+3) - POSITIF SANGAT KUAT.", 60000),
                ExamItem("E4", "Rontgen Thorax PA", ExamCategory.IMAGING, "Infiltrat dan kavitas berdinding tebal pada lapangan atas paru dextra (apeks), tampak bercak fibroinfiltrat bilateral.", 180000),
                ExamItem("E5", "Darah Rutin & LED (Laju Endap Darah)", ExamCategory.LAB, "Hb 10.2 g/dL (Anemia ringan), Leukosit 11.200/uL, LED 85 mm/jam (Meningkat tinggi).", 95000)
            ),
            optimalExamNames = listOf("Pemeriksaan Paru & Thorax", "TCM TB / GeneXpert MTB/RIF Dahak Cito", "Rontgen Thorax PA", "Darah Rutin & LED (Laju Endap Darah)"),
            optimalCostEstimate = 525000,
            recommendedTreatment = "1. Posisikan pasien miring ke sisi paru yang sakit (Dextra) agar darah tidak masuk ke paru sehat. 2. Berikan Antihemoragik Asam Traneksamat 500mg 3x1 oral / IV. 3. Kodein 10-20 mg oral untuk penekan batuk ringan. 4. Pengobatan OAT Kategori 1 (RHZE / Rifampisin, Isoniazid, Pirazinamid, Etambutol) fase intensif 2 bulan dilanjutkan fase lanjutan 4 bulan (RH). 5. Edukasi etika batuk & pemakaian masker.",
            kemenkesGuidelines = "Pedoman Penanggulangan Tuberkulosis Kemenkes RI: Diagnosis TB Paru diprioritaskan menggunakan Tes Cepat Molekuler (TCM GeneXpert). Batuk darah diatasi dengan penanganan suportif dan dimulainya regimen OAT standar."
        ),

        // 14. Gastroenterohepatologi: Perdarahan Saluran Cerna Atas (PSCA) / Varises Esofagus
        ClinicalCase(
            id = "GASTRO-002",
            organSystem = "Gastroenterohepatologi",
            title = "Muntah Darah Hitam (Melena) & Perut Membesar (Sirosis Hati)",
            patientAge = 52,
            patientGender = "Laki-laki",
            patientOccupation = "Petani",
            generalAppearance = "Tampak pucat konjungtiva anemis, spider naevi pada dada (+), eritema palmaris (+), sklera ikterik, perut membuncit (asites), tampak melena hitam seperti kopi saat BAB.",
            chiefComplaint = "Muntah darah kehitaman sejak tadi pagi.",
            td = "90/60 mmHg",
            nadi = 112,
            rr = 24,
            suhu = 36.8,
            spO2 = 96,
            trueDiagnosis = "Perdarahan Saluran Cerna Atas (PSCA) ec Ruptur Varises Esofagus pada Sirosis Hati Decompensated",
            differentialDiagnoses = listOf("Perdarahan ec Gastritis Erosif / Ulkus Peptikum", "Mallory-Weiss Tear", "Karsinoma Lambung", "Ruptur Varises Fundus"),
            patientPersonaInstruction = "Keluarga pasien: Bapak punya riwayat sakit liver/hepatitis B lama Dok. Tadi pagi tiba-tiba muntah darah hitam kayak warna kopi 2 gelas, terus buang air besarnya warnanya hitam pekat baunya amis menyengat.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Stigmata Sirosis & Colok Dubur (RT)", ExamCategory.PEMFIS, "Sklera ikterik (+), Spider naevi (+), Asites (+), Caput medusae (+). RT: Feses warna hitam lengket melena (+), darah segar (-).", 0),
                ExamItem("E2", "Darah Rutin & Hemostasis (PT/APTT/INR) Cito", ExamCategory.LAB, "Hb: 5.8 g/dL (ANEMIA BERAT CITO!), Leukosit 4.500/uL, Trombosit 68.000/uL (Trombositopenia), PT 18.5 dtk, INR 1.65 (Meningkat).", 280000),
                ExamItem("E3", "Endoskopi Saluran Cerna Atas (EGD Cito)", ExamCategory.IMAGING, "Esofagus: Tampak varises esofagus grade III dengan red whale mark dan perdarahan aktif merembes (oozing). Dilakukan LIGASI VARISES.", 1800000),
                ExamItem("E4", "Fungsi Hati & Albumin", ExamCategory.LAB, "SGOT 88 U/L, SGPT 64 U/L, Bilirubin Total 4.2 mg/dL, Albumin 2.1 g/dL (Hipoalbuminemia berat).", 220000)
            ),
            optimalExamNames = listOf("Pemeriksaan Stigmata Sirosis & Colok Dubur (RT)", "Darah Rutin & Hemostasis (PT/APTT/INR) Cito", "Fungsi Hati & Albumin"),
            optimalCostEstimate = 500000,
            recommendedTreatment = "1. Resusitasi Cairan Kristaloid & Transfusi Darah Cito (PRC) target Hb 7-8 g/dL. 2. Vasokontraktor Splanchnic IV: Somatostatin / Octreotide 50 mcg bolus IV dilanjutkan drip 50 mcg/jam ATAU Vasopressin/Terlipressin. 3. Injeksi Vitamin K 10mg IV. 4. Antibiotik Profilaksis Sepsis: Seftriaxon 1g IV / hari. 5. Persiapan Endoskopi Cito untuk Ligasi Varises Esofagus (EVL).",
            kemenkesGuidelines = "Pedoman PHOI / Kemenkes RI: PSCA pada sirosis hati dicurigai kuat ruptur varises. Terapi darurat memerlukan resusitasi transfusi darah, obat vasoaktif (Octreotide/Somatostatin), antibiotik profilaksis, dan ligasi endoskopi dini < 12-24 jam."
        ),

        // 15. Infeksi Tropis: Malaria Falciparum Berat
        ClinicalCase(
            id = "TROPICAL-002",
            organSystem = "Infeksi Tropis",
            title = "Demam Gigil Berkala, Konjungtiva Pucat & Urine Warna Teh Pekat",
            patientAge = 29,
            patientGender = "Laki-laki",
            patientOccupation = "Pekerja Tambang",
            generalAppearance = "Tampak sakit berat, somnolen, anemis berat, sklera ikterik, urine berwarna gelap kehitaman (blackwater fever).",
            chiefComplaint = "Demam tinggi menggigil sejak 5 hari lalu.",
            td = "95/60 mmHg",
            nadi = 110,
            rr = 24,
            suhu = 39.4,
            spO2 = 95,
            trueDiagnosis = "Malaria Falciparum Berat dengan Anemia Berat & Blackwater Fever",
            differentialDiagnoses = listOf("Leptospirosis Berat (Weil Disease)", "Demam Berdarah Dengue (DBD)", "Demam Tifoid Berat", "Sepsis ec Pielonefritis"),
            patientPersonaInstruction = "Istri pasien: Suami saya baru pulang dinas dari pedalaman Papua seminggu lalu Dok. Langsung demam tinggi menggigil tiap 2 hari sekali, matanya kuning, lemas banget nggak sanggup berdiri, kencingnya warnanya cokelat hitam pekat.",
            availableExams = listOf(
                ExamItem("E1", "Apusan Darah Tepi Tetes Tebal & Tipis (Malaria DDR)", ExamCategory.LAB, "Tetes Tebal/Tipis: Ditemukan bentuk cincin (trofozoit) Plasmodium falciparum densitas tinggi (parasitemia > 4%), gametosit bentuk pisang (+).", 75000),
                ExamItem("E2", "RDT Malaria (Rapid Diagnostic Test)", ExamCategory.LAB, "RDT Pf (HRP-2): POSITIF KUAT.", 85000),
                ExamItem("E3", "Darah Rutin & Bilirubin Total/Direk", ExamCategory.LAB, "Hb: 5.2 g/dL (Anemia Berat!), Leukosit 8.900/uL, Trombosit 55.000/uL, Bilirubin Total 5.8 mg/dL.", 180000),
                ExamItem("E4", "Fungsi Ginjal & Urinalisis", ExamCategory.LAB, "Ureum 82 mg/dL, Kreatinin 2.4 mg/dL. Urine: Hemoglobinuria (+).", 150000)
            ),
            optimalExamNames = listOf("Apusan Darah Tepi Tetes Tebal & Tipis (Malaria DDR)", "RDT Malaria (Rapid Diagnostic Test)", "Darah Rutin & Bilirubin Total/Direk"),
            optimalCostEstimate = 340000,
            recommendedTreatment = "1. Artesunat IV Cito: Dosis 2.4 mg/kgBB IV pada jam ke-0, jam ke-12, jam ke-24, kemudian 1x sehari sampai pasien bisa minum obat oral. 2. Setelah pasien sadar dan toleransi oral: Berikan DHP (Dihidroartemisinin-Piperakuin) oral 3 hari + Primakuin. 3. Transfusi Darah PRC Cito target Hb > 8-10 g/dL. 4. Monitoring ketat fungsi ginjal dan balans cairan.",
            kemenkesGuidelines = "Pedoman Tata Laksana Malaria Kemenkes RI: Malaria berat ditandai infeksi P. falciparum dengan salah satu komplikasi (anemia berat Hb < 7, malaria serebral, jaundice, atau hemoglobinuria). Obat pilihan utama adalah Injeksi Artesunat IV."
        ),

        // 16. Pediatri: Diare Akut Dehidrasi Berat ec Rotavirus
        ClinicalCase(
            id = "PEDIATRI-002",
            organSystem = "Pediatri (Kesehatan Anak)",
            title = "Balita Diare Cair Berulang, Mata Sangat Cekung & Tidak Sadar",
            patientAge = 1,
            patientGender = "Perempuan",
            patientOccupation = "Anak Balita (14 Bulan)",
            generalAppearance = "EMERGENSI PEDIATRI! Tampak letargis/sangat lemas (GCS E2V2M4), mata sangat cekung, air mata tidak keluar saat menangis, turgor kulit perut kembali sangat lambat (>2 detik), akral dingin.",
            chiefComplaint = "Mencret cair berulang sejak kemarin.",
            td = "75/45 mmHg",
            nadi = 148,
            rr = 36,
            suhu = 38.2,
            spO2 = 96,
            trueDiagnosis = "Diare Akut Dehidrasi Berat ec Gastroenteritis Akut (Suspek Rotavirus)",
            differentialDiagnoses = listOf("Diare Akut Dehidrasi Sedang", "Disentri Amoeba / Shigellosis", "Intususepsi / Invaginasi", "Sepsis Neonatorum / Infant"),
            patientPersonaInstruction = "Ibu pasien menangis histeris: Dok tolong anak saya! Dari kemarin mencret cair terus kayak air cucian beras lebih dari 10 kali. Tadi pagi muntah-muntah terus sekarang merem aja lemes nggak mau minum sama sekali!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Tanda Dehidrasi Menurut WHO", ExamCategory.PEMFIS, "Mata sangat cekung (+), Turgor kulit sangat lambat (>2 dtk) (+), Letargis/penurunan kesadaran (+), Ubun-ubun besar cekung (+). Kategori: DEHIDRASI BERAT.", 0),
                ExamItem("E2", "Feses Rutin & Leukosit Feses", ExamCategory.LAB, "Konsistensi cair, Lendir (+), Darah (-), Leukosit 0-1/LPB, Amoeba (-). Tes Rotavirus Feses: POSITIF.", 85000),
                ExamItem("E3", "Elektrolit (Na, K, Cl) & GDS Cito", ExamCategory.LAB, "Na 132 mEq/L, K 2.8 mEq/L (Hipokalemia ringan), Cl 98 mEq/L, GDS 62 mg/dL (Hipoglikemia ringan).", 195000)
            ),
            optimalExamNames = listOf("Pemeriksaan Tanda Dehidrasi Menurut WHO", "Feses Rutin & Leukosit Feses", "Elektrolit (Na, K, Cl) & GDS Cito"),
            optimalCostEstimate = 280000,
            recommendedTreatment = "1. RESUSITASI CAIRAN IV CITO (Rencana T WHO): Berikan Cairan Ringer Laktat / Asetat IV 100 ml/kgBB. (Untuk usia 14 bulan: 30 ml/kg dalam 30 menit pertama, dilanjutkan 70 ml/kg dalam 2.5 jam berikutnya). 2. Suplementasi Zinc Sirup 20mg 1x1 selama 10 hari berturut-turut. 3. Berikan Oralit sedikit demi sedikit setelah pasien sadar. 4. Nutrisi ASI / Makanan tetap dilanjutkan.",
            kemenkesGuidelines = "LIRIK / Pedoman Diare Anak Kemenkes RI: Diare dengan Dehidrasi Berat adalah indikasi mutlak Rehidrasi Intravena Cito Rencana T (100 ml/kgBB RL). 5 Pilar Diare Anak: Oralit + Zinc 10 hari + ASI/Nutrisi + Antibiotik selektif + Edukasi."
        ),

        // 17. Obgyn: Kehamilan Ektopik Terganggu (KET) Syok Hipovolemik
        ClinicalCase(
            id = "OBGYN-002",
            organSystem = "Obstetri & Ginekologi (Obgyn)",
            title = "Nyeri Perut Bawah Mendadak, Keterlambatan Haid & Pingsan",
            patientAge = 27,
            patientGender = "Perempuan",
            patientOccupation = "Karyawan Swasta",
            generalAppearance = "EMERGENSI CITO OBGYN! Pasien tampak sangat pucat, kesadaran menurun somnolen, akral dingin basah, nyeri tekan tajam seluruh perut bawah, flek perdarahan pervaginam (+).",
            chiefComplaint = "Nyeri perut bawah mendadak sejak 1 jam lalu.",
            td = "80/50 mmHg",
            nadi = 126,
            rr = 28,
            suhu = 36.2,
            spO2 = 94,
            trueDiagnosis = "Kehamilan Ektopik Terganggu (KET) dengan Syok Hemoragik / Hipovolemik",
            differentialDiagnoses = listOf("Ruptur Kista Ovarium", "Appendisitis Akut", "Salpingitis / Pelvic Inflammatory Disease (PID)", "Abortus Inkomplit"),
            patientPersonaInstruction = "Suami pasien cemas: Istri saya tadi melilit banget perut bawah kanannya Dok terus pingsan di kamar mandi. Dia telat haid sekitar 7 minggu. Keluar flek-flek darah dikit dari kemaluannya.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Ginekologi (VT / Vaginal Toucher)", ExamCategory.PEMFIS, "Nyeri goyang porsio (Cervical motion tenderness / Slap sign) POSITIF SANGAT NYERI (+), Cavum douglas teraba menonjol & nyeri (+).", 0),
                ExamItem("E2", "Tes Kehamilan Urin (Beta-hCG) Cito", ExamCategory.LAB, "Beta-hCG Urin: POSITIF (+).", 40000),
                ExamItem("E3", "USG Transvaginal / TVS Bedside", ExamCategory.IMAGING, "Uterus kosong (empty uterus), tampak massa kistik di adneksa dextra berukuran 4.5 cm dengan kantung gestasi, cairan bebas masif pada Cavum Douglas (hemoperitoneum).", 350000),
                ExamItem("E4", "Darah Rutin & Golongan Darah Crossmatch", ExamCategory.LAB, "Hb: 6.0 g/dL (ANEMIA BERAT CITO!), Leukosit 12.000/uL, Trombosit 210.000/uL. Golongan Darah A (+).", 150000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Ginekologi (VT / Vaginal Toucher)", "Tes Kehamilan Urin (Beta-hCG) Cito", "USG Transvaginal / TVS Bedside", "Darah Rutin & Golongan Darah Crossmatch"),
            optimalCostEstimate = 540000,
            recommendedTreatment = "1. RESUSITASI ATLS: 2 IV Line jarum besar + Infus cepat Kristaloid hangat + Transfusi Darah PRC Cito. 2. PANGGIL CITO SP.OG -> LAPAROTOMI EKSPLORASI CITO / SALPINGEKTOMI DEXTRA CITO untuk menghentikan sumber perdarahan aktif di tuba.",
            kemenkesGuidelines = "Pedoman POGI / Kemenkes RI: KET dengan syok hemoragik adalah kegawatdaruratan bedah kebidanan. Tanda khas: nyeri goyang porsio, kavum douglas menonjol, tes hamil (+). Tindakan definitif adalah Laparotomi Cito."
        ),

        // 18. Dermatovenerologi: Selulitis Pedis / Dermatitis
        ClinicalCase(
            id = "DERMA-001",
            organSystem = "Dermatovenerologi (Kulit)",
            title = "Kaki Bawah Bengkah, Kemerahan Hangat & Nyeri Nuntut",
            patientAge = 55,
            patientGender = "Laki-laki",
            patientOccupation = "Petani",
            generalAppearance = "Tampak kesakitan saat berjalan, kruris dextra tampak eritema cerah batas tidak tegas, edematous, hangat pada perabaan, nyeri tekan (+).",
            chiefComplaint = "Bengkak kemerahan pada kaki kanan sejak 3 hari lalu.",
            td = "130/80 mmHg",
            nadi = 88,
            rr = 18,
            suhu = 38.1,
            spO2 = 98,
            trueDiagnosis = "Selulitis Kruris Dextra ec Infeksi Streptokokus / Stafilokokus",
            differentialDiagnoses = listOf("Erisipelas", "Deep Vein Thrombosis (DVT)", "Dermatitis Stasis", "Gouty Arthritis Foot"),
            patientPersonaInstruction = "Pasien: Kaki kanan saya 3 hari lalu lecet kena air genangan Dok. Terus besoknya makin bengkak, merah banget, rasanya panas kayak terbakar dan nyut-nyutan. Ada demamnya juga.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Efloresensi Kulit Kruris", ExamCategory.PEMFIS, "Makula eritematosa batas tidak tegas (diffuse), edema (+), kalor (+), nyeri tekan (+), bula (-), krepitasi (-).", 0),
                ExamItem("E2", "Darah Rutin (CBC)", ExamCategory.LAB, "Hb 13.5 g/dL, Leukosit 14.800/uL (Leukositosis), Trombosit 260.000/uL.", 95000),
                ExamItem("E3", "Gula Darah Sewaktu (GDS)", ExamCategory.LAB, "GDS: 185 mg/dL.", 35000)
            ),
            optimalExamNames = listOf("Pemeriksaan Efloresensi Kulit Kruris", "Darah Rutin (CBC)", "Gula Darah Sewaktu (GDS)"),
            optimalCostEstimate = 130000,
            recommendedTreatment = "1. Elevasi tungkai kaki kanan yang sakit lebih tinggi dari jantung. 2. Antibiotik Sistemik: Flukloxasilin 500mg 4x1 oral ATAU Sefaleksin 500mg 4x1 oral ATAU Injeksi Seftriaxon 1g IV/24 jam bila berat. 3. Analgetik/Antipiretik Parasetamol 500mg 3x1. 4. Kompres dingin pada luka.",
            kemenkesGuidelines = "PPK Dermatologi PERDOSKI / Kemenkes RI: Selulitis adalah infeksi bakterial dermal & subkutan. Tanda khas batas eritema tidak tegas, hangat, dan nyeri. Terapi utama antibiotik antistafilokokus/streptokokus dan elevasi ekstremitas."
        ),

        // 19. THT-KL: Otitis Media Akut (OMA)
        ClinicalCase(
            id = "ENT-001",
            organSystem = "THT-KL",
            title = "Nyeri Telinga Hebat, Keluar Cairan Kuning & Pendengaran Berkurang",
            patientAge = 8,
            patientGender = "Laki-laki",
            patientOccupation = "Siswa SD",
            generalAppearance = "Tampak memegangi telinga kanan, menangis kesakitan, keluar cairan sekret mukopurulen kuning dari liang telinga kanan.",
            chiefComplaint = "Nyeri dan keluar cairan dari telinga kanan sejak kemarin.",
            td = "100/65 mmHg",
            nadi = 94,
            rr = 20,
            suhu = 37.9,
            spO2 = 99,
            trueDiagnosis = "Otitis Media Akut (OMA) Dextra Stadium Perforasi ec ISPA",
            differentialDiagnoses = listOf("Otitis Eksterna Akut", "Membran Timpani Perforasi Traumatik", "Otitis Media Supuratif Kronik (OMSK) Eksaserbasi Akut"),
            patientPersonaInstruction = "Ibu pasien: Anak saya seminggu ini batuk pilek Dok. Kemarin malam menjerit-jerit kesakitan telinga kanannya. Tadi subuh bantalnya ada bercak cairan kuning kental baunya agak amis, terus anaknya dibilang sakitnya mendingan.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Otoskopi THT Dextra et Sinistra", ExamCategory.PEMFIS, "Otoskopi Dextra: Liang telinga terisi sekret mukopurulen, tampak perforasi kecil pada membran timpani pars tensa dengan sekret berdenyut (pulsatile discharge). Otoskopi Sinistra: Membran timpani intakt utuh.", 0),
                ExamItem("E2", "Pemeriksaan Hidung & Tenggorokan (Rhinoskopi & Farings)", ExamCategory.PEMFIS, "Konka nasalis inferior edema dan hiperemis, sekret serosa (+). Farings hiperemis (+).", 0)
            ),
            optimalExamNames = listOf("Pemeriksaan Otoskopi THT Dextra et Sinistra", "Pemeriksaan Hidung & Tenggorokan (Rhinoskopi & Farings)"),
            optimalCostEstimate = 0,
            recommendedTreatment = "1. Cuci liang telinga kanan dengan H2O2 3% 3-5 tetes. 2. Tetes Telinga Antibiotik (Ofloksasin Tetes Telinga 2x3 tetes). 3. Antibiotik Oral Sistemik: Amoksisilin 40-50 mg/kgBB/hari dibagi 3 dosis selama 7-10 hari (atau Amoksisilin-Garam Klavulanat). 4. Dekongestan Oral (Pseudoefrin) & Analgetik Parasetamol sirup.",
            kemenkesGuidelines = "PPK THT PERHATI-KL / Kemenkes RI: OMA stadium perforasi ditandai rupturnya membran timpani akibat tekanan pus. Terapi mencakup H2O2 3% ear wash, antibiotik oral lini pertama Amoksisilin, dan antibiotik tetes non-ototoksik."
        ),

        // 20. Oftalmologi: Glaukoma Akut Sudut Tertutup
        ClinicalCase(
            id = "OPHTHA-001",
            organSystem = "Oftalmologi (Mata)",
            title = "Mata Merah Mendadak, Nyeri Kepala Hebat & Melihat Pelangi (Halo)",
            patientAge = 58,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga",
            generalAppearance = "Tampak merintih memegang dahi dan mata kiri, mata kiri injeksi siliaris berat, kornea keruh (edema), pupil midriasis non-reaktif.",
            chiefComplaint = "Nyeri pada mata kiri dan pusing sejak tadi sore.",
            td = "150/90 mmHg",
            nadi = 90,
            rr = 20,
            suhu = 36.6,
            spO2 = 98,
            trueDiagnosis = "Glaukoma Akut Sudut Tertutup (Acute Angle-Closure Glaucoma) Sinistra",
            differentialDiagnoses = listOf("Uveitis Anterior Akut", "Keratitis Fungal / Bakterial", "Endoftalmitis", "Migrain dengan Aura"),
            patientPersonaInstruction = "Pasien: Cekot-cekot banget mata kiri sama kepala sebelah kiri saya Dok! Penglihatan buram kayak ada kabut tebal, kalau ngeliat lampu kayak ada lingkaran warna-warni pelangi. Mual banget sampai muntah sekali.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Mata & Tonometri Palpasi / Schiotz", ExamCategory.PEMFIS, "Mata Sinistra: Injeksi siliar (+), kornea kusam keruh, bilik mata depan dangkal, pupil midriasis 5mm terfiksir non-reaktif. Tonometri Palpasi: TIO Sinistra sangat keras seperti papan (TIO > 50 mmHg). Visus OD 6/6, OS 1/60.", 0),
                ExamItem("E2", "Tonometri Schiotz / Non-Contact Tonometer", ExamCategory.PEMFIS, "TIO OD: 15 mmHg (Normal), TIO OS: 54 mmHg (SANGAT TINGGI!).", 0)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Mata & Tonometri Palpasi / Schiotz", "Tonometri Schiotz / Non-Contact Tonometer"),
            optimalCostEstimate = 0,
            recommendedTreatment = "1. Penurunan Tekanan Intraokular (TIO) Cito: Asetazolamid Oral 500mg dilanjutkan 250mg 4x1. 2. Tetes Mata Beta-blocker: Timolol 0.5% 2x1 tetes pada mata kiri. 3. Tetes Mata Miotik: Pilokarpin 2% 1 tetes tiap 15 menit dalam 1 jam pertama. 4. Manitol 20% IV drip 1-2 gram/kgBB dalam 30 menit bila TIO belum turun. 5. Konsul Cito Spesialis Mata untuk Laser Iridotomi.",
            kemenkesGuidelines = "PPK Mata PERDAMI / Kemenkes RI: Glaukoma akut sudut tertutup adalah kegawatdaruratan mata penyebab kebutaan permanen. Penanganan cito menurunkan TIO dengan Asetazolamid, Timolol, Pilokarpin, dan rujukan laser iridotomi."
        ),

        // 21. Psikiatri: Gangguan Anxietas Panik (Panic Attack)
        ClinicalCase(
            id = "PSYCH-001",
            organSystem = "Psikiatri & Jiwa",
            title = "Dada Berdebar Kencang, Nyeri Dada & Takut Mati Mendadak",
            patientAge = 30,
            patientGender = "Perempuan",
            patientOccupation = "Arsitek",
            generalAppearance = "Tampak sangat cemas, gemetaran (tremor), hiperventilasi, berkeringat dingin di telapak tangan, gelisah memegang dada.",
            chiefComplaint = "Dada berdebar kencang mendadak sejak 20 menit lalu.",
            td = "135/85 mmHg",
            nadi = 112,
            rr = 28,
            suhu = 36.7,
            spO2 = 99,
            trueDiagnosis = "Gangguan Panik (Panic Attack / Serangan Panik) dengan Agorafobia",
            differentialDiagnoses = listOf("Sindrom Koroner Akut (SKA)", "Hipertiroidisme / Krisis Tiroid", "SVTI (Supraventricular Tachycardia)", "Gangguan Anxietas Menyeluruh (GAD)"),
            patientPersonaInstruction = "Pasien dalam kondisi panik bergetar: 'Dok... jantung saya mau copot rasanya... dada saya sempit kayak kehabisan napas... saya takut mati Dok... tolong... apa saya kena serangan jantung?!'",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Terarah & Status Mental", ExamCategory.PEMFIS, "Kesadaran Compos Mentis, Afek cemas/anxietas tinggi, Orientasi W/T/O baik, Waham (-), Halusinasi (-). Tremor halus kedua tangan (+). Jantung: BJ I-II murni takikardi, murmur (-).", 0),
                ExamItem("E2", "EKG 12-Lead Cito", ExamCategory.IMAGING, "Sinus Takikardi 110 x/menit, aksis normal, ST segmen & Wave T DAlam batas normal (Menyingkirkan Iskemik Jantung).", 150000),
                ExamItem("E3", "Gula Darah Sewaktu & FT4/TSHs", ExamCategory.LAB, "GDS: 105 mg/dL, FT4: 1.2 ng/dL, TSHs: 1.8 mIU/L (Fungsi Tiroid Normal).", 280000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Terarah & Status Mental", "EKG 12-Lead Cito"),
            optimalCostEstimate = 150000,
            recommendedTreatment = "1. Reassurance & Teknik Latihan Napas Lambat (Slow Deep Breathing / Diaphragmatic breathing) dengan kantong kertas untuk mengatasi alkalosis respiratorik ec hiperventilasi. 2. Terapi Ansiolitik Akut: Alprazolam 0.25 - 0.5 mg oral ATAU Lorazepam 1 mg oral. 3. Terapi jangka panjang: SSRI (Sertraline 50mg 1x1) + Psikoterapi Kognitif Perilaku (CBT).",
            kemenkesGuidelines = "PPDGJ-III / Kemenkes RI: Serangan panik ditandai onset mendadak gejala otonomik berat (palpitasi, sesak, pusing) disertai rasa takut mati/hilang kendali. Penanganan awal menyingkirkan etiologi organik (EKG) lalu berikan ansiolitik dan relaksasi napas."
        ),

        // 22. Kegawatdaruratan: Syok Anafilaksis
        ClinicalCase(
            id = "EMERGENSI-002",
            organSystem = "Kegawatdaruratan & Trauma",
            title = "Sesak Napas Berat, Bentol-Bentol & Tekanan Darah Anjlok Pasca Minum Obat",
            patientAge = 26,
            patientGender = "Perempuan",
            patientOccupation = "Karyawan Bank",
            generalAppearance = "EMERGENSI KRITIS! Tampak sesak hebat, bibir & kelopak mata bengkak (angioedema), urtikaria merah gatal seluruh tubuh, akral dingin, suara serak (stridor inspiratori).",
            chiefComplaint = "Sesak napas dan bentol-bentol gatal sejak 15 menit lalu.",
            td = "70/40 mmHg",
            nadi = 135,
            rr = 32,
            suhu = 36.4,
            spO2 = 88,
            trueDiagnosis = "Syok Anafilaksis ec Reaksi Alergi Obat Akut",
            differentialDiagnoses = listOf("Asma Eksaserbasi Berat", "Urtikaria Akut Tanpa Syok", "Sinkop Vasovagal", "Edema Laring Herediter"),
            patientPersonaInstruction = "Pasien setengah sadar terengah-engah: 'Dok... tenggorokan saya gatal tercekik... napas saya ngos-ngosan... pusing banget pandangan gelap...'",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Kulit & Auskultasi Paru", ExamCategory.PEMFIS, "Urtikaria generalisata (+), Angioedema bibir & fasialis (+), Wheezing inspiratori/ekspiratori bilateral, Stridor laring (+).", 0),
                ExamItem("E2", "Gula Darah Sewaktu (GDS) Cito", ExamCategory.LAB, "GDS: 110 mg/dL (Menyingkirkan syok hipoglikemia).", 35000),
                ExamItem("E3", "EKG 12-Lead Cito", ExamCategory.IMAGING, "Sinus Takikardi 138 x/menit, tidak ada ST elevasi.", 150000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Kulit & Auskultasi Paru", "Gula Darah Sewaktu (GDS) Cito"),
            optimalCostEstimate = 35000,
            recommendedTreatment = "1. INJEKSI EPINEFRIN / ADRENALIN 1:1000 Dosis 0.3-0.5 mg IM (Intramuskular) pada paha anterolateral CITO! Dapat diulang tiap 5-15 menit jika belum responsik. 2. Posisikan pasien terlentang dengan kaki ditinggikan (Trendelenburg). 3. Oksigen High Flow via NRM 10-15 Lpm. 4. Resusitasi Cairan IV Infus Cepat NaCl 0.9% / RL 1000-2000 mL. 5. Injeksi Deksametason 10mg IV & Diphenhydramine 50mg IV.",
            kemenkesGuidelines = "Pedoman Kegawatdaruratan Kemenkes RI / WAO: Injeksi Epinefrin IM paha anterolateral adalah Lini Pertama dan Utama pada syok anafilaksis. Menunda Epinefrin berisiko fatalitas obstruksi jalan napas dan kolaps kardiovaskular!"
        ),

        // 23. Endokrinologi: Krisis Tiroid / Tirotoksikosis
        ClinicalCase(
            id = "ENDO-002",
            organSystem = "Endokrinologi",
            title = "Demam Tinggi, Jantung Berdebar Sangat Cepat & Benjolan Leher",
            patientAge = 36,
            patientGender = "Perempuan",
            patientOccupation = "Guru",
            generalAppearance = "Tampak sangat gelisah, delirium ringan, mata menonjol (exophthalmos), benjolan tiroid diffus di leher, tremor halus tangan, keringat berlebihan.",
            chiefComplaint = "Demam tinggi dan dada berdebar sejak 1 hari lalu.",
            td = "160/90 mmHg",
            nadi = 148,
            rr = 28,
            suhu = 39.8,
            spO2 = 97,
            trueDiagnosis = "Krisis Tiroid (Thyroid Storm) / Tirotoksikosis ec Graves Disease (Skor Burch-Wartofsky > 45)",
            differentialDiagnoses = listOf("Sepsis Berat", "Feokromositoma", "Meningitis", "Heat Stroke"),
            patientPersonaInstruction = "Pasien sangat gelisah bergetar: 'Dok... jantung saya mau copot rasanya... badan panas banget keringatan terus... pusing melayang dan mual...'",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Leher & Eksoftalmus (Burch-Wartofsky Score)", ExamCategory.PEMFIS, "Eksoftalmus (+/+), Struma tiroid difus teraba hangat dengan bruit (+). Tremor halus (+). Skor Burch-Wartofsky = 55 (Sangat Tinggi / Krisis Tiroid!).", 0),
                ExamItem("E2", "Hormon Tiroid Cito (FT4 & TSHs)", ExamCategory.LAB, "FT4: > 6.0 ng/dL (Sangat Tinggi!), TSHs: < 0.005 uIU/mL (Sangat Tertekan / Terdepresi).", 380000),
                ExamItem("E3", "EKG 12-Lead Cito", ExamCategory.IMAGING, "Sinus Takikardi / Fibrilasi Atrial Rapid Response 150 x/menit.", 150000),
                ExamItem("E4", "Darah Rutin & Elektrolit", ExamCategory.LAB, "Leukosit 12.500/uL, Na 136 mEq/L, K 3.8 mEq/L.", 255000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Leher & Eksoftalmus (Burch-Wartofsky Score)", "Hormon Tiroid Cito (FT4 & TSHs)", "EKG 12-Lead Cito"),
            optimalCostEstimate = 530000,
            recommendedTreatment = "1. Obat Antitiroid Dosis Tinggi: Propiltiourasil (PTU) 200mg per oral tiap 4 jam (Loading 600-1000mg). 2. Beta Blocker: Propranolol 40-80mg oral tiap 6 jam atau Esmolol IV untuk mengontrol takikardi. 3. Blok pelepasan hormon: Lugol Solution / Kalium Yodida 5 tetes tiap 8 jam (diberikan 1 jam setelah PTU). 4. Kortikosteroid: Deksametason 2mg IV tiap 6 jam. 5. Antipiretik Parasetamol (HINDARI Aspirin karena melepaskan hormon tiroid terikat!).",
            kemenkesGuidelines = "Pedoman PERKENI / Kemenkes RI: Krisis tiroid adalah kondisi darurat mengancam jiwa. Empat pilar utama: Hambat sintesis tiroid (PTU), hambat pelepasan hormon (Lugol), beralihkan reseptor sympathetic (Propranolol), dan steroid (Deksametason)."
        ),

        // 24. Neurologi: BPPV (Vertigo Perifer)
        ClinicalCase(
            id = "NEURO-002",
            organSystem = "Neurologi",
            title = "Pusing Berputar Hebat Saat Miring Kanan & Mual Muntah",
            patientAge = 48,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga",
            generalAppearance = "Tampak memejamkan mata, takut membuka mata atau menggerakkan kepala, merintih mual, muntah 2 kali.",
            chiefComplaint = "Pusing berputar mendadak sejak tadi pagi.",
            td = "130/80 mmHg",
            nadi = 82,
            rr = 18,
            suhu = 36.6,
            spO2 = 99,
            trueDiagnosis = "Benign Paroxysmal Positional Vertigo (BPPV) Kanalis Posterior Dextra",
            differentialDiagnoses = listOf("Meniere Disease", "Neuritis Vestibularis", "Stroke Batang Otak / Serebelar", "Vertigo Servikogenik"),
            patientPersonaInstruction = "Pasien memejamkan mata rapat-rapat: 'Aduh Dok, jangan suruh saya tengok kanan... rumah rasanya kayak diputer 360 derajat! Mual banget mau muntah kalau kepala saya gerak...'",
            availableExams = listOf(
                ExamItem("E1", "Maneuver Dix-Hallpike Dextra et Sinistra", ExamCategory.PEMFIS, "Dix-Hallpike Dextra POSITIF: Nistagmus vertikal-rotatori latensi 3 detik, durasi 20 detik, disertai vertigo hebat. Dix-Hallpike Sinistra Negatif.", 0),
                ExamItem("E2", "Pemeriksaan Neurologis Kranial & Serebelar", ExamCategory.PEMFIS, "N. II-XII intakt, tes Romberg dengan mata terbuka stabil, tes tunjuk hidung presisi. Tidak ada defisit neurologis fokal.", 0),
                ExamItem("E3", "CT Scan Kepala Non-Kontras", ExamCategory.IMAGING, "Parenkim serebrum & serebelum normal. (Tidak diindikasikan bila BPPV tipikal)", 1200000)
            ),
            optimalExamNames = listOf("Maneuver Dix-Hallpike Dextra et Sinistra", "Pemeriksaan Neurologis Kranial & Serebelar"),
            optimalCostEstimate = 0,
            recommendedTreatment = "1. Maneuver Reposisi Otolit: Maneuver Epley Dextra (Repositioning Maneuver) di tempat tidur. 2. Antivertigo Simptomatik: Betahistin Mesilat 6-12 mg 3x1 oral ATAU Flunarizin 5mg 1x1. 3. Antiemetik: Ondansetron 4mg / Metoklopramid 10mg bila mual muntah berat. 4. Edukasi hindari gerakan kepala mendadak.",
            kemenkesGuidelines = "Pedoman Praktik Klinis PERDOSSI / Kemenkes RI: BPPV dikonfirmasi dengan tes Dix-Hallpike. Terapi definitif dan paling efektif adalah Maneuver Reposisi Otolit Epley/Semont, obat antivertigo hanya sebagai terapi pendamping singkat."
        ),

        // 25. Gastroenterohepatologi: Kolesistitis Akut
        ClinicalCase(
            id = "GASTRO-003",
            organSystem = "Gastroenterohepatologi",
            title = "Nyeri Perut Kanan Atas Menjalar ke Punggung Pasca Makan Makanan Bersantan/Berlemak",
            patientAge = 45,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga",
            generalAppearance = "Tampak kesakitan memegang perut kanan atas, meringis saat bernapas dalam, suhu hangat.",
            chiefComplaint = "Nyeri perut kanan atas sejak semalam.",
            td = "125/80 mmHg",
            nadi = 96,
            rr = 20,
            suhu = 38.4,
            spO2 = 98,
            trueDiagnosis = "Kolesistitis Akut ec Kolelitiasis (Batu Empedu) (Murphy Sign Positif)",
            differentialDiagnoses = listOf("Kolelitiasis Simptomatik / Kolik Bilier", "Abses Hati / Hepar", "Gastritis / Tukak Peptikum", "Pankreatitis Akut"),
            patientPersonaInstruction = "Pasien: Nyeri banget perut kanan atas saya Dok, makin sakit kalau napas panjang. Tadi malam habis makan soto santan gurih, terus perut rasanya mual dan mulas hebat tembus ke belikat kanan.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Abdomen & Tanda Murphy (Murphy's Sign)", ExamCategory.PEMFIS, "Murphy Sign POSITIF (Nyeri tekan mendadak dan penghentian napas inspirasi saat palpasi Kuadran Kanan Atas Abdomen). Sklera subikterik (+).", 0),
                ExamItem("E2", "USG Abdomen Kuadran Kanan Atas", ExamCategory.IMAGING, "Penebalan dinding kandung empedu > 4mm (Gallbladder wall thickening), tampak acoustic shadow kolelitiasis (batu empedu multiple), sonographic Murphy sign (+).", 400000),
                ExamItem("E3", "Darah Rutin & Fungsi Hati (Bilirubin, SGOT/SGPT, Alk Phos)", ExamCategory.LAB, "Leukosit 15.200/uL (Leukositosis), Bilirubin Total 2.8 mg/dL, Bilirubin Direk 2.1 mg/dL, Alkaline Phosphatase 210 U/L.", 280000),
                ExamItem("E4", "Amilase & Lipase Darah", ExamCategory.LAB, "Amilase 65 U/L, Lipase 42 U/L (Normal / Menyingkirkan Pankreatitis Akut).", 220000)
            ),
            optimalExamNames = listOf("Pemeriksaan Abdomen & Tanda Murphy (Murphy's Sign)", "USG Abdomen Kuadran Kanan Atas", "Darah Rutin & Fungsi Hati (Bilirubin, SGOT/SGPT, Alk Phos)"),
            optimalCostEstimate = 680000,
            recommendedTreatment = "1. Puasakan pasien (NPO) dan pasang NGT bila muntah hebat. 2. Terapi Cairan IV Ringer Laktat rumatan. 3. Antinyeri NSAID / Opioid IV: Ketorolac 30mg IV / Hyoscine 20mg IV. 4. Antibiotik Sistemik: Seftriaxon 1g IV tiap 12 jam + Metronidazol 500mg IV tiap 8 jam. 5. Konsul Spesialis Bedah untuk Kolesistektomi Laparoskopi.",
            kemenkesGuidelines = "Pedoman Tokyo Guidelines / Kemenkes RI: Kolesistitis akut ditandai Nyeri Kuadran Kanan Atas, Murphy Sign positif, demam, dan leukositosis. Konfirmasi dengan USG Abdomen dan penanganan antibiotik IV + bedah kolesistektomi."
        ),

        // 26. Infeksi Tropis: Leptospirosis Berat
        ClinicalCase(
            id = "TROPICAL-003",
            organSystem = "Infeksi Tropis",
            title = "Demam Tinggi Pasca Banjir, Nyeri Otot Betis Hebat & Mata Kuning",
            patientAge = 42,
            patientGender = "Laki-laki",
            patientOccupation = "Petugas Kebersihan",
            generalAppearance = "Tampak lemas berat, sklera ikterik kemerahan (suffusion konjungtiva), nyeri hebat saat betis ditekan, urine pekat sedikit (oliguria).",
            chiefComplaint = "Demam tinggi dan nyeri betis sejak 4 hari lalu.",
            td = "100/65 mmHg",
            nadi = 108,
            rr = 24,
            suhu = 39.1,
            spO2 = 96,
            trueDiagnosis = "Leptospirosis Berat (Weil's Disease) dengan Trias Ikterik, Gagal Ginjal & Perdarahan",
            differentialDiagnoses = listOf("Hepatitis A Akut", "Demam Berdarah Dengue", "Malaria Falciparum", "Demam Tifoid Berat"),
            patientPersonaInstruction = "Pasien: Kaki betis saya rasanya sakit banget kayak dipukulin Dok! Kemarin habis ikut bersihin sampah banjir. Sekarang matanya kuning, badan panas dingin dan kencingnya dikit warna kayak teh pekat.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Otot Betis & Konjungtiva", ExamCategory.PEMFIS, "Conjunctival Suffusion (+/+), Sklera Ikterik (+/+), Nyeri tekan M. Gastrocnemius (betis) SANGAT HEBAT (+/+). Ptekie di kulit (+).", 0),
                ExamItem("E2", "Serologi Leptospira / MAT (Microscopic Agglutination Test) / IgM Rapid", ExamCategory.LAB, "IgM Leptospira Rapid: POSITIF KUAT (+).", 220000),
                ExamItem("E3", "Darah Rutin & Fungsi Ginjal (Ureum/Kreatinin)", ExamCategory.LAB, "Hb 11.2 g/dL, Leukosit 16.800/uL, Trombosit 62.000/uL, Ureum 115 mg/dL, Kreatinin 3.4 mg/dL (Gagal Ginjal Akut!).", 245000),
                ExamItem("E4", "Bilirubin Total & SGOT/SGPT", ExamCategory.LAB, "Bilirubin Total 8.4 mg/dL (Direk 6.1), SGOT 110 U/L, SGPT 95 U/L.", 180000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Otot Betis & Konjungtiva", "Serologi Leptospira / MAT (Microscopic Agglutination Test) / IgM Rapid", "Darah Rutin & Fungsi Ginjal (Ureum/Kreatinin)", "Bilirubin Total & SGOT/SGPT"),
            optimalCostEstimate = 645000,
            recommendedTreatment = "1. Injeksi Penisilin G Prokan 1.5 Juta UI IV/IM tiap 6 jam ATAU Seftriaxon 1g IV tiap 12 jam selama 7 hari. 2. Rehidrasi cairan IV hati-hati pemantauan balans cairan. 3. Evaluasi Dialisis / Hemodialisis Cito bila anuria / uremia berat. 4. Doksisisiklin 100mg 2x1 oral bila derajat ringan.",
            kemenkesGuidelines = "Pedoman Tata Laksana Leptospirosis Kemenkes RI: Trias Weil's disease (Ikterus, Gagal Ginjal Akut, Perdarahan) berisiko fatal. Antibiotik utama Injeksi Penisilin G IV atau Seftriaxon IV secepatnya."
        ),

        // 27. Pediatri: Bronkiolitis Akut
        ClinicalCase(
            id = "PEDIATRI-003",
            organSystem = "Pediatri (Kesehatan Anak)",
            title = "Bayi 6 Bulan Batuk Pilek, Napas Cepat & Mengi",
            patientAge = 1,
            patientGender = "Laki-laki",
            patientOccupation = "Bayi 6 Bulan",
            generalAppearance = "Bayi tampak rewel, napas cepat (tachypnea), napas cuping hidung (+), retraksi interkostal & subkostal (+), mengi ekspiratori.",
            chiefComplaint = "Sesak napas dan batuk mengi sejak 2 hari lalu.",
            td = "85/55 mmHg",
            nadi = 142,
            rr = 54,
            suhu = 37.8,
            spO2 = 91,
            trueDiagnosis = "Bronkiolitis Akut pada Bayi ec Infeksi Respiratory Syncytial Virus (RSV)",
            differentialDiagnoses = listOf("Asma Infantil Pertama Kali", "Pneumonia Lobaris pada Bayi", "Aspirasi Benda Asing", "Croup (Laringotrakeobronkitis)"),
            patientPersonaInstruction = "Ibu bayi menangis cemas: Dok anak saya umur 6 bulan dari kemarin batuk pilek, tadi malam napasnya ngos-ngosan dada bawahnya legok ke dalam dan bunyinya ngik-ngik. Susah menyusu Dok!",
            availableExams = listOf(
                ExamItem("E1", "Auskultasi Paru & Dinding Dada Bayi", ExamCategory.PEMFIS, "Inspeksi: Retraksi interkostal/subkostal (+), napas cuping hidung (+). Auskultasi: Wheezing ekspiratori halus nyaring dan ronkhi basah halus di kedua lapang paru.", 0),
                ExamItem("E2", "Swab Rapid Antigen RSV / PCR Respiratorik", ExamCategory.LAB, "RSV Antigen: POSITIF (+).", 180000),
                ExamItem("E3", "Rontgen Thorax AP Bayi", ExamCategory.IMAGING, "Tampak hiperinflasi paru, diafragma mendatar, bercak atelektasis subsegmental.", 180000)
            ),
            optimalExamNames = listOf("Auskultasi Paru & Dinding Dada Bayi", "Swab Rapid Antigen RSV / PCR Respiratorik", "Rontgen Thorax AP Bayi"),
            optimalCostEstimate = 360000,
            recommendedTreatment = "1. Suction / pembersihan lendir hidung dan jalan napas teratur. 2. Oksigenasi Humidifikasi Nasal Kanul 1-2 Lpm target SpO2 > 92%. 3. Rehidrasi cairan rumatan IV bila bayi kesulitan menyusu. 4. Nebulisasi NaCl 3% Hipertonik. (Catatan: Antibiotik dan Steroid TIDAK diindikasikan rutin pada Bronkiolitis RSV).",
            kemenkesGuidelines = "Pedoman IDAI / Kemenkes RI: Bronkiolitis paling sering disebabkan RSV pada bayi < 2 tahun. Penanganan utama bersifat suportif: Suction jalan napas, Oksigen humidifikasi, rehidrasi cairan, dan nebulisasi NaCl 3%."
        ),

        // 28. Nefro-Urologi: Pyelonefritis Akut
        ClinicalCase(
            id = "NEPHRO-002",
            organSystem = "Nefro-Urologi",
            title = "Demam Tinggi Menggigil, Nyeri Pinggang Kanan & Kencing Keruh",
            patientAge = 31,
            patientGender = "Perempuan",
            patientOccupation = "Pegawai Kantor",
            generalAppearance = "Tampak sakit sedang, menggigil teraba panas tinggi, memegang pinggang kanan.",
            chiefComplaint = "Demam tinggi dan nyeri pinggang kanan sejak 2 hari lalu.",
            td = "115/75 mmHg",
            nadi = 104,
            rr = 22,
            suhu = 39.2,
            spO2 = 98,
            trueDiagnosis = "Pyelonefritis Akut Dextra ec Infeksi Saluran Kemih Komplikata",
            differentialDiagnoses = listOf("Sistitis Akut Uncomplicated", "Batu Ureter Dextra (Kolik Ureter)", "Appendisitis Akut", "Pelvic Inflammatory Disease (PID)"),
            patientPersonaInstruction = "Pasien: Badan saya panas menggigil banget Dok, pinggang kanan rasanya pegal dan sakit banget ditotok. Kencing saya keruh baunya menyengat dan rasanya perih panas.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Pinggang & Ketok CVA Dextra", ExamCategory.PEMFIS, "Nyeri ketok Costovertebral Angle (CVA) Dextra POSITIF SANGAT NYERI (+), Sinistra (-).", 0),
                ExamItem("E2", "Urinalisis Sedimen & Dipstick Cito", ExamCategory.LAB, "Warna keruh, Leukosit Esterase (+3), Nitrit POSITIF (+), Leukosit Sedimen > 50/LPB (Piuria Masif!), Bakteri (+).", 65000),
                ExamItem("E3", "Kultur Urin & Uji Sensitivitas Antibioram", ExamCategory.LAB, "Escherichia coli > 10^5 CFU/mL, sensitif terhadap Seftriaxon dan Ciprofloksasin.", 280000),
                ExamItem("E4", "Darah Rutin (CBC)", ExamCategory.LAB, "Leukosit 17.500/uL (Leukositosis tinggi dengan pergeseran ke kiri).", 95000)
            ),
            optimalExamNames = listOf("Pemeriksaan Pinggang & Ketok CVA Dextra", "Urinalisis Sedimen & Dipstick Cito", "Darah Rutin (CBC)"),
            optimalCostEstimate = 160000,
            recommendedTreatment = "1. Injeksi Antibiotik Parenteral: Seftriaxon 1-2 gram IV tiap 24 jam ATAU Ciprofloksasin 400mg IV/12 jam. 2. Terapi Antipiretik: Parasetamol 500-1000mg per oral. 3. Hidrasi cairan IV / oral 2.5-3 liter/hari. 4. Lanjutkan antibiotik oral setelah bebas demam 24-48 jam hingga total 10-14 hari.",
            kemenkesGuidelines = "Pedoman IDU / Kemenkes RI: Pyelonefritis akut adalah infeksi parenkim ginjal ditandai demam tinggi, menggigil, dan CVA tenderness. Penanganan awal memerlukan antibiotik parenteral parenteral iv spektrum luas."
        ),

        // 29. Obgyn: Abortus Inkomplit
        ClinicalCase(
            id = "OBGYN-003",
            organSystem = "Obstetri & Ginekologi (Obgyn)",
            title = "Keluar Darah Kental Berumpal dari Kemaluan & Mulas Saat Hamil Muda",
            patientAge = 25,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga (G1P0A0 Hamil 10 Minggu)",
            generalAppearance = "Tampak pucat, meringis mulas perut bawah, bercak darah segar bercampur gumpalan jaringan pada pembalut.",
            chiefComplaint = "Keluar darah dari kemaluan dan mulas sejak tadi pagi.",
            td = "100/65 mmHg",
            nadi = 98,
            rr = 20,
            suhu = 36.8,
            spO2 = 99,
            trueDiagnosis = "Abortus Inkomplit pada Kehamilan 10 Minggu dengan Perdarahan Pervaginam",
            differentialDiagnoses = listOf("Abortus Imminens", "Abortus Insipiens", "Kehamilan Ektopik Terganggu", "Mola Hidatidosa"),
            patientPersonaInstruction = "Pasien memegang perut bawah: Dok saya hamil 10 minggu, tadi pagi perut mulas banget terus keluar gumpalan kayak daging darah segar dari kemaluan saya. Darahnya masih ngalir terus.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Inspekulo & Vaginal Toucher (VT)", ExamCategory.PEMFIS, "Inspekulo: Serviks kanalis servisis terbuka 1 jari, teraba sisa jaringan konsepsi di ostium uteri externum, perdarahan aktif (+).", 0),
                ExamItem("E2", "Tes Kehamilan Urin (hCG)", ExamCategory.LAB, "Beta-hCG Urin: POSITIF (+).", 40000),
                ExamItem("E3", "USG Kebidanan Bedside (Transabdominal / Transvaginal)", ExamCategory.IMAGING, "Uterus membesar sesuai kehamilan 8-10 minggu, tampak gambaran kistik heterogen sisa jaringan konsepsi di dalam kavum uteri, kantong gestasi tidak utuh.", 320000),
                ExamItem("E4", "Darah Rutin & Hb Cito", ExamCategory.LAB, "Hb 9.2 g/dL (Anemia ringan-sedang), Leukosit 10.500/uL, Trombosit 240.000/uL.", 95000)
            ),
            optimalExamNames = listOf("Pemeriksaan Inspekulo & Vaginal Toucher (VT)", "Tes Kehamilan Urin (hCG)", "USG Kebidanan Bedside (Transabdominal / Transvaginal)", "Darah Rutin & Hb Cito"),
            optimalCostEstimate = 455000,
            recommendedTreatment = "1. Evakuasi sisa jaringan konsepsi: Aspirasi Vakum Manual (AVM) / Kuretase Tajam Cito. 2. Uterotonika: Oksitosin 10 UI IV drip / Methylergometrine 0.2mg IM. 3. Antibiotik Profilaksis: Doksisiklin 100mg 2x1 oral selama 5 hari. 4. Suplemen Asam Traneksamat & Sulfas Ferosus oral.",
            kemenkesGuidelines = "Pedoman POGI / Kemenkes RI: Abortus inkomplit ditandai kanalis servikalis terbuka dengan sisa jaringan konsepsi teraba. Penanganan definitif adalah evakuasi AVM/kuretase serta profilaksis antibiotik."
        ),

        // 30. Psikiatri: Krisis Gaduh Gelisah Skizofrenia
        ClinicalCase(
            id = "PSYCH-002",
            organSystem = "Psikiatri & Jiwa",
            title = "Pasien Mengamuk, Bicara Sendiri & Mengaku Dikejar-kejar Suara Gaib",
            patientAge = 28,
            patientGender = "Laki-laki",
            patientOccupation = "Tidak Bekerja",
            generalAppearance = "EMERGENSI PSIKIATRI! Pasien tampak sangat agitated/gaduh gelisah, berteriak-teriak, mata melotot, curiga pada petugas, bicara tidak koheren.",
            chiefComplaint = "Gelisah dan mengamuk sejak kemarin.",
            td = "140/90 mmHg",
            nadi = 115,
            rr = 24,
            suhu = 36.9,
            spO2 = 98,
            trueDiagnosis = "Skizofrenia Paranoik (F20.0) dengan Krisis Gaduh Gelisah & Halusinasi Akustik",
            differentialDiagnoses = listOf("Gangguan Psikotik Akut", "Psikosis ec Intoksikasi Napza/Amphetamine", "Delirium ec Organik", "Bipolar Fase Manik dengan Psikotik"),
            patientPersonaInstruction = "Pasien berteriak curiga: 'Jangan dekat-dekat! Kalian utusan agen rahasia yang mau bunuh saya kan?! Bisikan suara itu nyuruh saya lawan kalian!'",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Status Mental Psikiatri Cito", ExamCategory.PEMFIS, "Sikap tidak koheren, Afek tidak sesuai, Waham Kejar (+), Halusinasi Akustik/Dengar (+), Inisial insight/tilikan 1 (Sangat buruk).", 0),
                ExamItem("E2", "Tes Toksikologi Urin Napza 6 Parameter", ExamCategory.LAB, "Amphetamine, Methamphetamine, THC, Morphine, Benzodiazepine: SEMUA NEGATIF.", 180000),
                ExamItem("E3", "Gula Darah Sewaktu (GDS) Cito", ExamCategory.LAB, "GDS: 98 mg/dL (Menyingkirkan hipoglikemia/delirium).", 35000)
            ),
            optimalExamNames = listOf("Pemeriksaan Status Mental Psikiatri Cito", "Tes Toksikologi Urin Napza 6 Parameter", "Gula Darah Sewaktu (GDS) Cito"),
            optimalCostEstimate = 215000,
            recommendedTreatment = "1. Injeksi Antipsikotik Dosis Cito: Haloperidol 5mg IM (Intramuskular) + Diphenhydramine 50mg IM (pencegahan efek ekstrapyramidal/EPS) ATAU Olanzapine 10mg IM. 2. Jika pasien membahayakan diri/orang lain: Restrain/Fiksasi Fisik mekanik sementara dengan prosedur standar etik. 3. Lanjutkan Antipsikotik Oral: Risperidone 2mg 2x1 oral atau Olanzapine 10mg 1x1.",
            kemenkesGuidelines = "Pedoman PPDGJ-III / PDSKJI / Kemenkes RI: Gaduh gelisah psikiatri merupakan kegawatdaruratan. Injeksi Haloperidol 5mg IM kombinasi Diphenhydramine IM efektif menenangkan pasien tanpa menekan pusat napas."
        ),

        // 31. THT-KL: Abses Peritonsil (Quinsy)
        ClinicalCase(
            id = "ENT-002",
            organSystem = "THT-KL",
            title = "Sulit Membuka Mulut (Trismus), Sakit Menelan Hebat & Suara Bergumam",
            patientAge = 24,
            patientGender = "Laki-laki",
            patientOccupation = "Mahasiswa",
            generalAppearance = "Tampak kesakitan, trismus (hanya bisa buka mulut 2 jari), liur menetes (drooling), suara bergumam seperti memegang kentang panas ('hot potato voice').",
            chiefComplaint = "Nyeri menelan dan mulut sulit dibuka sejak 3 hari lalu.",
            td = "120/80 mmHg",
            nadi = 102,
            rr = 20,
            suhu = 38.6,
            spO2 = 98,
            trueDiagnosis = "Abses Peritonsil (Quinsy) Dextra ec Komplikasi Tonsilitis Akut",
            differentialDiagnoses = listOf("Tonsilofaringitis Akut Berat", "Abses Retrofaring", "Infiltrat Peritonsil", "Angina Ludwig"),
            patientPersonaInstruction = "Pasien dengan suara bergumam terhalang liur: 'Dok... sssakit bbanget menelan... buka mulut aja susah... liur saya nggak bisa ditelan...'",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Mulut & Tonsil THT", ExamCategory.PEMFIS, "Trismus (+ 2 jari). Tonsil Dextra T3 hiperemis, palatum mole Dextra menonjol & fluktuasi (+), uvula terdorong keras ke Sinistra.", 0),
                ExamItem("E2", "Pungsi Aspirasi Abses Peritonsil Bedside", ExamCategory.PEMFIS, "Aspirasi jarum pada daerah fluktuasi palatum Dextra: Keluar nanah/pus mukopurulen 4 cc.", 0),
                ExamItem("E3", "Darah Rutin (CBC)", ExamCategory.LAB, "Leukosit 18.200/uL (Leukositosis berat).", 95000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Mulut & Tonsil THT", "Pungsi Aspirasi Abses Peritonsil Bedside", "Darah Rutin (CBC)"),
            optimalCostEstimate = 95000,
            recommendedTreatment = "1. Insisi & Drainase / Pungsi Aspirasi Abses Peritonsil Cito. 2. Injeksi Antibiotik Parenteral: Ampisilin-Sulbaktam 1.5g IV tiap 8 jam ATAU Seftriaxon 1g IV tiap 12 jam + Metronidazol 500mg IV drip. 3. Analgetik & Antiinflamasi: Deksametason 5mg IV & Parasetamol IV. 4. Kumur larutan antiseptik povidone iodine.",
            kemenkesGuidelines = "Pedoman PERHATI-KL / Kemenkes RI: Abses peritonsil ditandai trismus, hot potato voice, fluktuasi peritonsil, dan deviasi uvula. Tata laksana definitif adalah Insisi & Drainase pus + Antibiotik parenteral IV."
        ),

        // 32. Oftalmologi: Ulkus Kornea Bakterial
        ClinicalCase(
            id = "OPHTHA-002",
            organSystem = "Oftalmologi (Mata)",
            title = "Mata Kanan Merah, Berair, Nyeri & Bercak Putih di Kornea Pasca Pakai Softlens",
            patientAge = 22,
            patientGender = "Perempuan",
            patientOccupation = "Mahasiswi",
            generalAppearance = "Tampak memegangi mata kanan, fotofobia (silau), blefarospasme, lakrimasi berlebihan, kornea tampak bercak keruh keputihan (infiltrat/defek epitel).",
            chiefComplaint = "Mata kanan merah dan perih sejak 2 hari lalu.",
            td = "115/75 mmHg",
            nadi = 78,
            rr = 18,
            suhu = 36.7,
            spO2 = 99,
            trueDiagnosis = "Ulkus Kornea Bakterial Dextra ec Trauma Penggunaan Lensa Kontak (Pseudomonas / Stafilokokus)",
            differentialDiagnoses = listOf("Keratitis Herpetik / Fungal", "Erosi Kornea Traumatik", "Uveitis Anterior", "Konjungtivitis Bakterial Berat"),
            patientPersonaInstruction = "Pasien: Mata kanan saya rasanya perih dan silau banget Dok! Kemarin lupa ngelepas softlens pas tidur. Pas bangun ada titik putih di hitam-hitam mata saya dan mata merah berair terus.",
            availableExams = listOf(
                ExamItem("E1", "Tes Fluoresens Kornea & Slit-Lamp Examination", ExamCategory.PEMFIS, "Tes Fluoresens POSITIF (+): Tampak defek epitel kornea berukuran 3x2 mm berwarna hijau fluoresens dengan infiltrat stromal purulen, hipopion (+) 1mm di bilik mata depan.", 0),
                ExamItem("E2", "Visus OD/OS (Tajam Penglihatan)", ExamCategory.PEMFIS, "Visus OD: 3/60 (Sangat Menurun!), Visus OS: 6/6 (Normal).", 0),
                ExamItem("E3", "Usap Kornea / Gram & Kultur Bakteri", ExamCategory.LAB, "Apusan Gram usap kornea: Batang Gram Negatif (Curiga Pseudomonas aeruginosa).", 120000)
            ),
            optimalExamNames = listOf("Tes Fluoresens Kornea & Slit-Lamp Examination", "Visus OD/OS (Tajam Penglihatan)", "Usap Kornea / Gram & Kultur Bakteri"),
            optimalCostEstimate = 120000,
            recommendedTreatment = "1. Tetes Mata Antibiotik Spektrum Luas Intensif: Levofloksasin 0.5% / Tobramisin Fortified Tetes Mata tiap 1 jam pada mata kanan. 2. Tetes Mata Sikloplegik: Atropin 1% 2x1 tetes untuk mengurangi spasme siliaris dan nyeri. 3. Peringatan Ketat: HINDARI Tetes Mata Steroid/Kortikosteroid (berisiko perforasi kornea!). 4. Stop penggunaan lensa kontak.",
            kemenkesGuidelines = "Pedoman PERDAMI / Kemenkes RI: Ulkus kornea adalah kegawatdaruratan mata berisiko kebutaan permanen & perforasi. Tetes antibiotik spektrum luas jam-jaman wajib segera diberikan. Steroid Kontraindikasi Mutlak di fase akut!"
        ),

        // 33. Dermatovenerologi: Herpes Zoster
        ClinicalCase(
            id = "DERMA-002",
            organSystem = "Dermatovenerologi (Kulit)",
            title = "Bintil-Bintil Berisi Cairan Panas Panas Menjalar di Dada Sebelah Kanan",
            patientAge = 56,
            patientGender = "Laki-laki",
            patientOccupation = "Pensiunan",
            generalAppearance = "Tampak meringis memegangi dada kanan, tampak vesikel berkelompok dengan dasar eritematosa unilateral sesuai dermatom T5-T6 dextra.",
            chiefComplaint = "Bintil-bintil berair dan nyeri panas di dada kanan sejak 3 hari lalu.",
            td = "135/85 mmHg",
            nadi = 84,
            rr = 18,
            suhu = 37.2,
            spO2 = 98,
            trueDiagnosis = "Herpes Zoster Thorakalis Dextra (Dermatom T5-T6) dengan Nyeri Neuropatik Akut",
            differentialDiagnoses = listOf("Herpes Simpleks Generalisata", "Dermatitis Kontak Alergika", "Luka Bakar", "Neuralgia Interkostal"),
            patientPersonaInstruction = "Pasien: Kulit dada kanan saya rasanya panas banget kayak disulut api Dok! Awalnya perih terus mendadak keluar bintil-bintil isi air melingkar setengah badan di sebelah kanan.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Efloresensi Kulit Dermatom Thorakalis", ExamCategory.PEMFIS, "Vesikel berkelompok berdinding tebal berisi cairan jernih dengan dasar eritematosa, berbatas tegas unilateral tidak melewati garis tengah (midline) Dermatom T5-T6 Dextra.", 0),
                ExamItem("E2", "Tzanck Smear / Mikroskopis Sediaan Kerokan", ExamCategory.LAB, "Tzanck Smear: Ditemukan sel datia berinti banyak (multinucleated giant cells) POSITIF Virus Herpes.", 85000),
                ExamItem("E3", "Gula Darah Sewaktu (GDS)", ExamCategory.LAB, "GDS: 145 mg/dL.", 35000)
            ),
            optimalExamNames = listOf("Pemeriksaan Efloresensi Kulit Dermatom Thorakalis", "Tzanck Smear / Mikroskopis Sediaan Kerokan"),
            optimalCostEstimate = 85000,
            recommendedTreatment = "1. Antiviral Sistemik: Acyclovir 800 mg per oral 5 kali sehari selama 7 hari (diberikan dalam 72 jam pertama onset). 2. Analgetik Nyeri Neuropatik: Gabapentin 300mg 1x1 oral ATAU Pregabalin 75mg 2x1 + Parasetamol 500mg. 3. Topikal: Bedak Salisil 2% / Kompres NaCl 0.9% bila vesikel basah.",
            kemenkesGuidelines = "Pedoman PERDOSKI / Kemenkes RI: Herpes Zoster khas ditandai vesikel bergerombol unilateral sesuai dermatom. Pemberian Acyclovir 5x800mg dini < 72 jam mencegah komplikasi Neuralgia Pasca Herpes (NPH)."
        ),

        // 34. Kardiologi: Fibrilasi Atrial Rapid Response (AF RVR)
        ClinicalCase(
            id = "CARDIO-003",
            organSystem = "Kardiologi",
            title = "Jantung Berdebar Sangat Cepat, Ireguler & Lemas",
            patientAge = 64,
            patientGender = "Perempuan",
            patientOccupation = "Pensiunan",
            generalAppearance = "Tampak lemas, gelisah, nadi teraba sangat cepat dan ireguler (tak teratur).",
            chiefComplaint = "Dada berdebar kencang sejak 3 jam lalu.",
            td = "110/70 mmHg",
            nadi = 154,
            rr = 22,
            suhu = 36.6,
            spO2 = 97,
            trueDiagnosis = "Fibrilasi Atrial (AF) Rapid Ventricular Response (RVR) HR 150-160x/m",
            differentialDiagnoses = listOf("Supraventricular Tachycardia (SVT)", "Atrial Flutter dengan Conduction Variable", "Sinus Takikardi", "Ventricular Tachycardia (VT)"),
            patientPersonaInstruction = "Pasien: Jantung saya dari tadi siang ketukan dadanya acak-acakan banget Dok, cepet banget kayak mau lompat! Badan rasanya lemes melayang...",
            availableExams = listOf(
                ExamItem("E1", "Auskultasi Jantung & Pulse Deficit", ExamCategory.PEMFIS, "BJ I S2 ireguler (irregularly irregular), HR 152 x/menit, Defisit nadi (+) (frekuensi nadi perifer < frekuensi auskultasi jantung).", 0),
                ExamItem("E2", "EKG 12-Lead Cito", ExamCategory.IMAGING, "EKG: Gelombang P tidak tampak (Absent P Wave), digantikan f-wave (fibrillatory wave) ireguler, R-R interval ireguler, Ventricular Rate 154 x/menit.", 150000),
                ExamItem("E3", "Troponin I & Elektrolit", ExamCategory.LAB, "Troponin I: 0.02 ng/mL (Negatif), K 4.1 mEq/L, Na 139 mEq/L.", 350000),
                ExamItem("E4", "Echocardiography Bedside", ExamCategory.IMAGING, "Dimensi atrium kiri membesar (LA dilation 4.2 cm), EF 55%, tidak tampak trombus intrakardiak.", 650000)
            ),
            optimalExamNames = listOf("Auskultasi Jantung & Pulse Deficit", "EKG 12-Lead Cito", "Troponin I & Elektrolit"),
            optimalCostEstimate = 500000,
            recommendedTreatment = "1. Control Rate (Pengontrol Laju Jantung): Diltiazem 0.25 mg/kgBB IV bolus pelan ATAU Digoxin 0.5 mg IV / Bisoprolol 5mg oral. 2. Evaluasi Antikoagulan (Skor CHA2DS2-VASc): Berikan LMWH Enoxaparin / Rivaroxaban untuk pencegahan stroke emboli. 3. Kardioversi Elektrik (DC Shock) HANYA jika hemodinamik tidak stabil (syok/penurunan kesadaran).",
            kemenkesGuidelines = "Pedoman PERKI / Kemenkes RI: AF RVR stabil mengutamakan Rate Control dengan CCB non-dihidropiridin (Diltiazem) atau Beta Blocker, serta penentuan indikasi antikoagulan oral."
        ),

        // 35. Pulmonologi: Pneumonia Komunitas (CAP) Berat
        ClinicalCase(
            id = "PULMO-003",
            organSystem = "Pulmonologi",
            title = "Demam Tinggi, Batuk Berdahak Hijau & Sesak Napas",
            patientAge = 67,
            patientGender = "Laki-laki",
            patientOccupation = "Pensiunan",
            generalAppearance = "Tampak sesak napas, kesadaran agak bingung/konfusi ringan, tampak pucat, sianosis bibir ringan.",
            chiefComplaint = "Demam tinggi dan batuk berdahak sejak 3 hari lalu.",
            td = "100/60 mmHg",
            nadi = 112,
            rr = 30,
            suhu = 39.3,
            spO2 = 89,
            trueDiagnosis = "Community-Acquired Pneumonia (CAP) Lobus Inferior Dextra Derajat Berat (Skor CURB-65 = 3)",
            differentialDiagnoses = listOf("PPOK Eksaserbasi Akut", "TB Paru Aktif", "Edema Paru Akut", "Efusi Pleura Terinfeksi"),
            patientPersonaInstruction = "Keluarga pasien: Bapak batuk dahak hijau kental 3 hari ini Dok. Badannya panas tinggi menggigil, napasnya sesak dan mulai ngomongnya agak bingung kacau.",
            availableExams = listOf(
                ExamItem("E1", "Auskultasi Paru & Skor CURB-65", ExamCategory.PEMFIS, "Auskultasi: Suara napas bronkial, Ronki basah kasar & Stem fremitus meningkat pada lapang bawah paru dextra. CURB-65 Score = 3 (Confusion, Urea, Respiratory rate >=30, Age >=65) -> Kategori BERAT.", 0),
                ExamItem("E2", "Rontgen Thorax PA", ExamCategory.IMAGING, "Tampak infiltrat / konsolidasi lobaris padat dengan air bronchogram pada lobus inferior pulmo dextra.", 180000),
                ExamItem("E3", "Darah Rutin, Ureum & Procalcitonin", ExamCategory.LAB, "Leukosit 21.000/uL (Leukositosis berat!), Ureum 52 mg/dL (Meningkat), Procalcitonin 4.8 ng/mL (Sepsis paru!).", 380000),
                ExamItem("E4", "Analisis Gas Darah (AGD)", ExamCategory.LAB, "pH 7.31, PaCO2 44 mmHg, PaO2 58 mmHg, SaO2 89% (Gagal Napas Hipoksemik Tipe 1).", 320000)
            ),
            optimalExamNames = listOf("Auskultasi Paru & Skor CURB-65", "Rontgen Thorax PA", "Darah Rutin, Ureum & Procalcitonin", "Analisis Gas Darah (AGD)"),
            optimalCostEstimate = 880000,
            recommendedTreatment = "1. Terapi Oksigenasi via Simple Mask / NRM 6-10 Lpm target SpO2 >= 92%. 2. Terapi Antibiotik Kombinasi Parenteral Cito: Seftriaxon 2g IV tiap 24 jam + Azitromisin 500mg IV tiap 24 jam (atau Levofloksasin 750mg IV/24 jam). 3. Rehidrasi Kristaloid IV. 4. Mukolitik & Antipiretik Parasetamol IV. 5. Rawat Inap Ruang HCU/ICU.",
            kemenkesGuidelines = "Pedoman PDPI / Kemenkes RI: CAP berat (CURB-65 >= 3) memerlukan rawat inap intensif dan pemberian antibiotik kombinasi Beta-laktam IV + Makrolida IV secepatnya dalam 1-2 jam pertama kedatangan."
        ),

        // 7. Kegawatdaruratan & Trauma: Perdarahan Masif & Syok Hipovolemik
        ClinicalCase(
            id = "TRAUMA-001",
            organSystem = "Kegawatdaruratan & Trauma",
            title = "Kecelakaan Lalu Lintas: Perdarahan Masif Fraktur Femur Terbuka & Syok Hipovolemik",
            patientAge = 26,
            patientGender = "Laki-laki",
            patientOccupation = "Karyawan Swasta",
            generalAppearance = "Tampak sangat pucat, kesadaran Apatis/Somnolen (GCS E3V3M5=11), akral sangat dingin, basah & pucat, CRT > 4 detik, terdapat perdarahan arterial aktif menyemprot dari luka terbuka paha kanan proksimal.",
            chiefComplaint = "Nyeri dan perdarahan pada paha kanan pasca kecelakaan 30 menit lalu.",
            td = "70/40 mmHg",
            nadi = 142,
            rr = 32,
            suhu = 35.1,
            spO2 = 88,
            trueDiagnosis = "Syok Hipovolemik Berat (Grade IV) ec Perdarahan Masif e.c. Fraktur Femur Dextra Terbuka Grade IIIB & Kecelakaan Lalu Lintas",
            differentialDiagnoses = listOf("Syok Neurogenik ec Trauma Medula Spinalis", "Syok Kardiogenik ec Tamponade Jantung / Contusio Cordis", "Tension Pneumothorax Dextra", "Perdarahan Intraabdomen ec Ruptur Spleen / Hati"),
            patientPersonaInstruction = "Petugas Medis Ambulans / Saksi: Pasien ini korban tabrakan motor vs truk tronton 30 menit lalu Dok. Paha kanan patah, tulang menyembul keluar dan darah menyemprot deras banget. Tadi sempat menjerit sakit tapi sekarang lemas pucat dingin dan ngomongnya racau ngelantur.",
            availableExams = listOf(
                ExamItem("E1", "Primary Survey ABCDE & Status Lokalis Femur Dextra", ExamCategory.PEMFIS, "Airway: Paten gurgling (-). Breathing: RR 32x/m, vesikuler kanan-kiri simetris, rhonchi (-). Circulation: TD 70/40 mmHg, Nadi 142x/m sangat lemah teraba di karotis, CRT >4d. Status Lokalis: Deformitas angulasi paha kanan, wound 12 cm dengan bone exposed (tulang menembus kulit), perdarahan arterial aktif mengucur masif.", 0),
                ExamItem("E2", "Focused Assessment with Sonography for Trauma (FAST) Bedside", ExamCategory.IMAGING, "FAST Ultrasound: RUQ (Cairan bebas (-)), LUQ (Cairan bebas (-)), Pericardial Window (Cairan (-)), Pelvic Window (Cairan bebas minimal di cavum douglas). Menyingkirkan perdarahan intraperitoneal masif lain.", 350000),
                ExamItem("E3", "Darah Rutin (CBC), Golongan Darah & Crossmatch Cito", ExamCategory.LAB, "Hb 6.1 g/dL (Anemia Berat ec Perdarahan Akut!), Leukosit 14.200/uL, Trombosit 110.000/uL, Ht 18%. Golongan Darah: O Rh (+). Prepared 4 Bag PRC + 4 Bag FFP Cito!", 180000),
                ExamItem("E4", "Analisis Gas Darah (AGD) & Laktat Cito", ExamCategory.LAB, "pH 7.18 (Asidosis Metabolik Berat), PaCO2 28 mmHg, PaO2 65 mmHg, HCO3 11 mEq/L, Base Excess -14 mEq/L, Laktat Darah: 7.2 mmol/L (Hipoperfusi jaringan berat!).", 320000),
                ExamItem("E5", "Profil Koagulasi (PT / APTT / Fibrinogen / D-Dimer)", ExamCategory.LAB, "PT 18.5 detik (Memanjang), APTT 48.0 detik (Memanjang), Fibrinogen 120 mg/dL (Menurun - Tanda Trauma-Induced Coagulopathy / TIC).", 280000),
                ExamItem("E6", "Rontgen Femur Dextra & Pelvis AP Bedside", ExamCategory.IMAGING, "Tampak fraktur kominutif bergeser (displaced comminuted fracture) pada 1/3 tengah diaphysis os femur dextra. Ring pelvis utuh tidak tampak fraktur.", 250000),
                ExamItem("E7", "Rontgen Thorax AP Bedside", ExamCategory.IMAGING, "Cor dan pulmo dalam batas normal. Tidak tampak pneumothorax maupun hemothorax.", 180000),
                ExamItem("E8", "CT Scan Whole Body Trauma / Pan-scan Kontras", ExamCategory.IMAGING, "Pan-scan: Tidak ada fraktur servikal atau kranial. (Kontraindikasi dilakukan saat hemodinamik belum stabil/TD 70/40!)", 3200000)
            ),
            optimalExamNames = listOf("Primary Survey ABCDE & Status Lokalis Femur Dextra", "Focused Assessment with Sonography for Trauma (FAST) Bedside", "Darah Rutin (CBC), Golongan Darah & Crossmatch Cito", "Analisis Gas Darah (AGD) & Laktat Cito", "Profil Koagulasi (PT / APTT / Fibrinogen / D-Dimer)", "Rontgen Femur Dextra & Pelvis AP Bedside"),
            optimalCostEstimate = 1260000,
            recommendedTreatment = "1. AIRWAY & BREATHING: Berikan Oksigen NRM 15 Lpm, persiapkan intubasi jika GCS < 8. 2. HENTIKAN PERDARAHAN CITO: Pasang Tourniquet di paha kanan proksimal luka / Bebat Tekan Steril Cito untuk menghentikan perdarahan arterial masif. 3. AKSES VENA & RESUSITASI CAIRAN: Pasang 2 line IV Canula jarum besar (16G/14G), grojok Kristaloid Hangat (Warm RL / NaCl 0.9%) 1000 mL dengan prinsip Permissive Hypotension (target TD sistolik 80-90 mmHg sebelum perdarahan terkontrol). 4. HEMOSTATIK & TRANSFUSI CITO: Berikan Injeksi Asam Traneksamat (TXA) 1 gram IV bolus cito < 3 jam pasca trauma, aktifkan Protokol Transfusi Masif (MTP) transfusi PRC + FFP + Trombosit rasio 1:1:1. 5. IMOBILISASI & BEDAH CITO: Pasang Spalk Imobilisasi / Thomas Splint pada ekstremitas kanan, jaga suhu pasien dengan selimut thermal (cegah Trias Kematian Trauma), serta konsul Cito Bedah Ortopedi untuk Cito Debridement & Fiksasi External di OK.",
            kemenkesGuidelines = "Pedoman ATLS / PNPK Trauma & Perdarahan Masif Kemenkes RI: Penanganan syok hipovolemik trauma mengutamakan kontrol perdarahan eksternal cito (Tourniquet/Bebat Tekan), pencegahan Trias Kematian Trauma (Hipotermia, Asidosis, Koagulopati), pemberian Asam Traneksamat < 3 jam, resusitasi cairan hangat, serta Protokol Transfusi Masif (MTP)."
        ),

        // 8. Trauma Dada Terbuka & Sucking Chest Wound
        ClinicalCase(
            id = "TRAUMA-002",
            organSystem = "Kegawatdaruratan & Trauma",
            title = "Kecelakaan Kerja: Open Pneumothorax (Sucking Chest Wound) & Gagal Napas ec Trauma Dada Terbuka",
            patientAge = 32,
            patientGender = "Laki-laki",
            patientOccupation = "Pekerja Konstruksi Bangunan",
            generalAppearance = "Tampak sianosis (kebiruan), gelisah hebat, napas cepat dan dangkal (distress respirasi berat RR 36x/m), terdapat luka robek menganga diameter 5 cm di dada kanan lateral bawah yang berbunyi mendesis ('sucking sound') tersedot udara tiap kali bernapas.",
            chiefComplaint = "Sesak napas dan luka robek di dada kanan sejak 20 menit lalu.",
            td = "85/55 mmHg",
            nadi = 128,
            rr = 36,
            suhu = 36.4,
            spO2 = 82,
            trueDiagnosis = "Open Pneumothorax Dextra (Sucking Chest Wound) ec Vulnus Laceratum Thorax Dextra & Gagal Napas Akut",
            differentialDiagnoses = listOf("Tension Pneumothorax Dextra", "Massive Hemothorax Dextra", "Contusio Pulmonum Dextra", "Tamponade Jantung ec Trauma Tumpul/Tembus"),
            patientPersonaInstruction = "Rekan Kerja Korban: Dok! Teman saya ini dada kanannya tertusuk seng tajam di proyek proyek bangunan! Dadanya bolong menganga sekitar 5 cm dan tiap dia tarik napas bunyinya mendesis tersedot udara Dok! Mukanya biru banget dan napasnya megap-megap tolong!",
            availableExams = listOf(
                ExamItem("E1", "Primary Survey ABCDE & Status Lokalis Dada Dextra", ExamCategory.PEMFIS, "Airway: Paten. Breathing: RR 36x/m, gerak dada kanan tertinggal, perkusi hipersonor dextra, suara napas dada kanan sangat menurun. Status Lokalis Thorax Dextra: Vulnus laceratum 5x3 cm di ICS V Linea Axillaris Anterior dextra dengan sucking chest wound aktif (+). Circulation: TD 85/55 mmHg, Nadi 128x/m lemah.", 0),
                ExamItem("E2", "Rontgen Thorax AP Bedside / Portable Cito", ExamCategory.IMAGING, "Tampak kolaps paru kanan > 60% dengan garis pleura terpisah (pneumothorax dextra masif) serta emfisema subkutan di dinding dada kanan. Tidak ada pendorongan mediastinum ekstrim.", 220000),
                ExamItem("E3", "Analisis Gas Darah (AGD) & Laktat Cito", ExamCategory.LAB, "pH 7.24 (Asidosis Respiratorik-Metabolik Campuran), PaCO2 55 mmHg (Retensi CO2), PaO2 52 mmHg (Hipoksemia berat), HCO3 19 mEq/L, SpO2 82%. Laktat 4.8 mmol/L.", 320000),
                ExamItem("E4", "Darah Rutin (CBC), Golongan Darah & Crossmatch", ExamCategory.LAB, "Hb 11.8 g/dL, Leukosit 12.500/uL, Hematokrit 36%, Trombosit 210.000/uL. Golongan Darah B Rh(+).", 180000),
                ExamItem("E5", "EKG 12 Lead Bedside Cito", ExamCategory.PEMFIS, "Sinus Takikardia 128 x/menit, S1Q3T3 pattern (-), tidak ada elevasi ST segmen.", 120000)
            ),
            optimalExamNames = listOf("Primary Survey ABCDE & Status Lokalis Dada Dextra", "Rontgen Thorax AP Bedside / Portable Cito", "Analisis Gas Darah (AGD) & Laktat Cito", "Darah Rutin (CBC), Golongan Darah & Crossmatch"),
            optimalCostEstimate = 840000,
            recommendedTreatment = "1. BREATHING CITO: Pasang Kassa Vaselin 3 Sisi (Three-Sided Occlusive Dressing) Cito pada luka dada menganga untuk menghentikan efek sucking chest wound tanpa menyebabkan Tension Pneumothorax! 2. TERAPI OKSIGEN: Berikan Oksigen NRM 15 Lpm konsentrasi tinggi. Jika pasien tetap distres berat/GCS turun, persiapkan Intubasi ETT Cito. 3. CHEST TUBE / WSD CITO: Konsul Bedah Thorax / Bedah Umum Cito untuk pemasangan Chest Tube (Water Seal Drainage / WSD) di ICS V Linea Axillaris Media Dextra. 4. RESUSITASI CAIRAN: Pasang IV line 16G, infus Kristaloid RL 500 mL. 5. ANTIBIOTIK & PROFILAKSIS TETANUS: Berikan Anti-Tetanus Serum (ATS) 1500 IU IM + Tetanus Toksoid (TT) 0.5 mL IM + Injeksi Antibiotik Parenteral (Ampisilin-Sulbaktam 1.5g IV / Seftriaxon 2g IV) + Analgetik Opioid IV.",
            kemenkesGuidelines = "Pedoman ATLS / Trauma Dada Kemenkes RI: Penanganan awal Open Pneumothorax mengutamakan penutupan luka menganga dengan Three-Sided Occlusive Dressing diikuti dengan pemasangan Chest Tube / WSD cito dan pencegahan sepsis/tetanus."
        ),

        // 9. Amputasi Traumatik & Vulnus Laceratum Luas
        ClinicalCase(
            id = "TRAUMA-003",
            organSystem = "Kegawatdaruratan & Trauma",
            title = "Kecelakaan Mesin Industri: Amputasi Traumatik Jari & Vulnus Laceratum Luas Lengan Dextra",
            patientAge = 40,
            patientGender = "Laki-laki",
            patientOccupation = "Operator Mesin Pabrik Kayu",
            generalAppearance = "Tampak erangan kesakitan hebat, gelisah, balutan kain di tangan kanan bersimbah darah deras, teraba potongan organ jari terpisah dalam kantong plastik yang dibawa rekan kerja.",
            chiefComplaint = "Luka robek dan nyeri pada tangan kanan pasca kecelakaan kerja 45 menit lalu.",
            td = "90/60 mmHg",
            nadi = 118,
            rr = 24,
            suhu = 36.2,
            spO2 = 96,
            trueDiagnosis = "Amputasi Traumatik Digiti II & IV Manus Dextra & Vulnus Laceratum Luas Antebrachii ec Trauma Mesin Pemotong",
            differentialDiagnoses = listOf("Crush Injury Extremity Superior Dextra", "Compartment Syndrome Antebrachii Dextra", "Avulsion Wound & Partial Amputation Manus"),
            patientPersonaInstruction = "Pasien & Pengantar Pabrik: Aduh Dok!! Tangan kanan saya terjepit gergaji mesin pabrik! Dua jari saya putus terlepas dan darah menyembur kencang dari pergelangan tangan! Tolong Dok sakittt sekali tangan saya pucat dingin!",
            availableExams = listOf(
                ExamItem("E1", "Status Lokalis & Perdarahan Arterial Manus/Antebrachii", ExamCategory.PEMFIS, "Status Lokalis Manus & Antebrachii Dextra: Vulnus laceratum ternganga 15x6 cm di regio antebrachii ventralis dengan exposed tendon flexor, amputasi komplit digiti II & IV manus dextra di level PIP joint dengan perdarahan aktif arterial menyemprot. Pulsi a.radialis teraba lemah.", 0),
                ExamItem("E2", "Rontgen Manus & Antebrachii Dextra AP/Lateral Bedside", ExamCategory.IMAGING, "Tampak kontinuitas tulang os phalanx media & distalis digiti II & IV terputus total (amputasi komplit). Tampak pula fraktur kominutif os radius dextra distal 1/3 tanpa fraktur ulna.", 260000),
                ExamItem("E3", "Darah Rutin (CBC), Golongan Darah & Crossmatch", ExamCategory.LAB, "Hb 9.2 g/dL (Anemia ec Perdarahan Akut), Leukosit 15.100/uL, Trombosit 220.000/uL, Ht 28%. Golongan A Rh(+).", 180000),
                ExamItem("E4", "Profil Koagulasi (PT/APTT/Fibrinogen)", ExamCategory.LAB, "PT 13.2 detik, APTT 34.5 detik, Fibrinogen 240 mg/dL (Dalam batas normal).", 280000)
            ),
            optimalExamNames = listOf("Status Lokalis & Perdarahan Arterial Manus/Antebrachii", "Rontgen Manus & Antebrachii Dextra AP/Lateral Bedside", "Darah Rutin (CBC), Golongan Darah & Crossmatch", "Profil Koagulasi (PT/APTT/Fibrinogen)"),
            optimalCostEstimate = 720000,
            recommendedTreatment = "1. KONTROL PERDARAHAN CITO: Pasang Tourniquet di lengan atas proksimal luka / Bebat Tekan Steril Kassa Dep untuk menghentikan perdarahan arterial aktif. 2. PRESERVASI JARI AMPUTAT: Bungkus organ potongan jari dengan kassa steril yang dilembabkan NaCl 0.9%, masukkan ke dalam kantong plastik kedap air, lalu masukkan ke wadah berisi es (Cold Ischemia / Indirect Cooling) untuk potensi Re-implantasi Microvascular! 3. RESUSITASI & ANALGETIK: Akses IV line 18G, infus RL 500-1000 mL, Injeksi Asam Traneksamat (TXA) 1g IV + Analgetik Opioid IV (Morfin 5mg / Fentanyl 50mcg IV). 4. IRIGASI & TETANUS: Irigasi awal luka dengan NaCl 0.9% steril 2-3 Liter, berikan ATS 1500 IU IM + Serum Tetanus Toksoid (TT) 0.5 mL IM + Antibiotik Broad Spectrum IV (Cefazolin 2g IV + Gentamisin). 5. BEDAH CITO: Konsul Cito Bedah Ortopedi / Bedah Plastik untuk Debridement & Re-implantasi / Re-konstruksi di OK.",
            kemenkesGuidelines = "Pedoman ATLS & Bedah Rekonstruksi Kemenkes RI: Penanganan amputasi traumatik berfokus pada hemostasis cepat, resusitasi cairan, preservasi jaringan amputat dingin indirek (bukan kontak langsung es), profilaksis Tetanus/Antibiotik, serta rujukan Cito Bedah."
        ),

        // 10. Trauma Kepala & Luka Robek Scalp Profus
        ClinicalCase(
            id = "TRAUMA-004",
            organSystem = "Kegawatdaruratan & Trauma",
            title = "Kecelakaan Lalu Lintas Motor: Vulnus Laceratum Scalp Profus & Fraktur Depresi Os Frontal ec CKS",
            patientAge = 22,
            patientGender = "Laki-laki",
            patientOccupation = "Mahasiswa",
            generalAppearance = "Tampak kesadaran Somnolen (GCS E3V4M5=12), luka robek ternganga luas dari dahi hingga puncak kepala (scalp) dengan perdarahan arterial profus membasahi wajah dan pakaian, teraba tulang dahi melesak (depressed fracture).",
            chiefComplaint = "Luka robek di kepala dan pingsan pasca kecelakaan 35 menit lalu.",
            td = "110/70 mmHg",
            nadi = 104,
            rr = 22,
            suhu = 36.6,
            spO2 = 97,
            trueDiagnosis = "Vulnus Laceratum Scalp Profus & Fraktur Depresi Os Frontal ec Cidera Kepala Sedang (CKS) / Trauma Maxillofacial",
            differentialDiagnoses = listOf("Epidural Hematoma (EDH) Frontalis Dextra", "Subdural Hematoma (SDH) Akut Frontal", "Perdarahan Subaraknoid (SAH) Trauma", "Fraktur Basis Kranii Anterior"),
            patientPersonaInstruction = "Petugas Kepolisian / Penolong: Dok! Pasien ini tabrakan motor tunggal nabrak trotoar beton kencang banget pas tidak pakai helm. Kepala depan dahi robek lebar menyembur darah banyak sampai bajunya basah kuyup darah. Tadi sempat pingsan 10 menit terus bangun muntah-muntah!",
            availableExams = listOf(
                ExamItem("E1", "Primary Survey & Status Lokalis Scalp / Wajah", ExamCategory.PEMFIS, "Airway: Paten, jejas servikal (+). Breathing: RR 22x/m, SpO2 97%. Disability: GCS E3V4M5=12 (Somnolen), pupil isokor 3mm/3mm, RC +/+. Status Lokalis Scalp: Vulnus laceratum ternganga 12x4 cm di regio frontoparietal dengan perdarahan profus aktif dari a.temporalis superfisialis, teraba step-off / fraktur depresi os frontal.", 0),
                ExamItem("E2", "CT Scan Kepala Non-Kontras Bedside Cito", ExamCategory.IMAGING, "Tampak fraktur depresi os frontal dextra melesak > 5 mm ke dalam cavum kranii, hematoma epidural (EDH) tipis volum 10 cc di regio frontal dextra tanpa midline shift bermakna.", 1100000),
                ExamItem("E3", "Rontgen Servikal AP/Lateral Bedside Cito", ExamCategory.IMAGING, "Kontinuitas os servikal C1-C7 baik, tidak tampak Alignment fraktur maupun dislokasi vertebra servikalis.", 220000),
                ExamItem("E4", "Darah Rutin (CBC), GDS & Electrolyte Cito", ExamCategory.LAB, "Hb 10.4 g/dL, Leukosit 13.800/uL, GDS 135 mg/dL, Natrium 138 mEq/L, Kalium 4.1 mEq/L.", 160000)
            ),
            optimalExamNames = listOf("Primary Survey & Status Lokalis Scalp / Wajah", "CT Scan Kepala Non-Kontras Bedside Cito", "Rontgen Servikal AP/Lateral Bedside Cito", "Darah Rutin (CBC), GDS & Electrolyte Cito"),
            optimalCostEstimate = 1480000,
            recommendedTreatment = "1. AIRWAY & CERVICAL CONTROL: Pasang Rigid Cervical Collar Cito! Jaga elevasi kepala 30 derajat (Head-Up 30°). 2. HEMOSTASIS SCALP CITO: Lakukan Balut Tekan Steril / Penjahitan Situasi (Hefting Suture) pada scalp untuk menghentikan perdarahan masif a.temporalis. 3. RESUSITASI CAIRAN: Infus Isotonis NaCl 0.9% 500 mL (Hindari cairan hipotonis / Dextrose karena dapat memperberat edema serebri). 4. OBAT & PROFILAKSIS: Injeksi Asam Traneksamat (TXA) 1g IV + Injeksi Seftriaxon 2g IV + Anti-Tetanus Serum (ATS) 1500 IU IM + Tetanus Toksoid (TT) 0.5 mL IM + Injeksi Parasetamol 1g IV (Hindari NSAID untuk cegah perdarahan). 5. BEDAH SARAF CITO: Konsul Bedah Saraf Cito untuk Debridement Scalp & Cito Elevasi Fraktur Depresi Os Frontal di OK.",
            kemenkesGuidelines = "Pedoman PNPK Trauma Kepala & Scalp Kemenkes RI: Penanganan CKS dengan fraktur depresi dan luka robek scalp mengutamakan imobilisasi servikal, hemostasis luka scalp cito, pencegahan edema serebri (Head up 30° & NaCl 0.9%), CT Scan Cito, dan elevasi fraktur depresi oleh Bedah Saraf.",
            isEmergencyCase = true
        ),

        // 11. Nefro-Urologi: AKI Stage III & Hiperkalemia Cito
        ClinicalCase(
            id = "NEFRO-001",
            organSystem = "Nefro-Urologi",
            title = "Anuria 24 Jam, Mual Muntah & Hiperkalemia Berat Cito (Peaked T)",
            patientAge = 58,
            patientGender = "Laki-laki",
            patientOccupation = "Pensiunan",
            generalAppearance = "Tampak sakit berat, lemas, Kussmaul breathing (napas dalam dan cepat), mual muntah berulang, edema periorbita (+)",
            chiefComplaint = "Tidak bisa buang air kecil sama sekali sejak 24 jam lalu, badan lemas hebat hingga tidak bisa duduk, mual muntah berulang.",
            td = "165/95 mmHg",
            nadi = 108,
            rr = 28,
            suhu = 37.1,
            spO2 = 94,
            trueDiagnosis = "Acute Kidney Injury (AKI) Stage III ec Dehidrasi/Sepsis & Hiperkalemia Berat Cito (K: 7.2 mEq/L)",
            differentialDiagnoses = listOf("Acute on Chronic Kidney Disease (CKD)", "Uremic Encephalopathy", "Glomerulonefritis Akut", "Obstruksi Saluran Kemih ec Batu Ureter Bilateral"),
            patientPersonaInstruction = "Pasien & Istri: Dok, suami saya dari kemarin tidak keluar kencing sama sekali. Badan lemas tidak bisa berdiri, mual muntah terus tiap kemasukan air, napasnya memburu cepat dalam.",
            availableExams = listOf(
                ExamItem("E1", "EKG 12-Lead Cito", ExamCategory.PEMFIS, "Tall Peaked T Wave di V2-V6, PR Interval memanjang 0.24 dtk, QRS kompleks melebar (Gambaran Toksisitas Hiperkalemia Berat pada Jantung!).", 120000),
                ExamItem("E2", "Fungsi Ginjal Cito (Ureum & Kreatinin Serum)", ExamCategory.LAB, "Ureum: 184 mg/dL, Kreatinin Serum: 8.4 mg/dL (Sangat Meningkat > 3x baseline / Anuria!).", 180000),
                ExamItem("E3", "Elektrolit Cito (Na/K/Cl)", ExamCategory.LAB, "Kalium: 7.2 mEq/L (CRITICAL HIGH!), Natrium: 132 mEq/L, Klorida: 98 mEq/L.", 160000),
                ExamItem("E4", "Analisis Gas Darah (AGD) Cito", ExamCategory.LAB, "pH: 7.18 (Asidosis Metabolik Berat), HCO3: 11 mEq/L, PaCO2: 28 mmHg, BE: -14 mEq/L.", 320000),
                ExamItem("E5", "USG Ginjal & Vesika Urinaria Bedside", ExamCategory.IMAGING, "Ukuran kedua ginjal normal, diferensiasi kortikomedular jelas, tidak ada hidronefrosis maupun batu sumbatan.", 350000),
                ExamItem("E6", "Darah Rutin (CBC) Cito", ExamCategory.LAB, "Hb 10.8 g/dL, Leukosit 16.500/uL, Trombosit 210.000/uL.", 95000)
            ),
            optimalExamNames = listOf("EKG 12-Lead Cito", "Fungsi Ginjal Cito (Ureum & Kreatinin Serum)", "Elektrolit Cito (Na/K/Cl)", "Analisis Gas Darah (AGD) Cito", "USG Ginjal & Vesika Urinaria Bedside"),
            optimalCostEstimate = 1130000,
            recommendedTreatment = "1. KOREKSI HIPERKALEMIA CITO: Kalsium Glukonas 10% 10-20 mL IV pelan 5-10 menit (Stabilisasi Membran Kardia) + Insulin Reguler 10 IU dalam Dextrose 50% 50 mL IV (Shift Kalium) + Nebulisasi Salbutamol 10-20 mg. 2. KOREKSI ASIDOSIS: Natrium Bikarbonat 8.4% 50-100 mEq IV drip. 3. DIURESIS: Furosemid IV 80-160 mg bolus. 4. HEMODIALISIS CITO: Konsul Nefrologi Cito untuk persiapakan Dialisis Darah / Cito Hemodialisis.",
            kemenkesGuidelines = "PPK Nefrologi Kemenkes RI: AKI Stage 3 dengan Hiperkalemia berat (K > 6.5 mEq/L + Peaked T EKG) merupakan kegawatdaruratan medis mutlak yang membutuhkan stabilisasi membran jantung dengan Kalsium Glukonas IV, insuflasi insulin-dextrose, dan Cito Hemodialisis.",
            isEmergencyCase = true
        ),

        // 12. Infeksi Tropis: DHF Grade III / Dengue Shock Syndrome
        ClinicalCase(
            id = "INF-001",
            organSystem = "Infeksi Tropis & Parasit",
            title = "Demam Berdarah Dengue: Syok Dengue (DHF Grade III / DSS) Akral Cold Clammy",
            patientAge = 19,
            patientGender = "Perempuan",
            patientOccupation = "Mahasiswi",
            generalAppearance = "Tampak sakit berat, gelisah, akral dingin lembab (cold clammy extremities), CRT > 3 detik, nadi cepat dan halus",
            chiefComplaint = "Demam tinggi 4 hari mendadak turun hari ini tapi badan menjadi sangat lemas, dingin, gelisah, dan muntah kehitaman.",
            td = "85/65 mmHg (Tekanan Nadi < 20 mmHg)",
            nadi = 132,
            rr = 28,
            suhu = 36.1,
            spO2 = 95,
            trueDiagnosis = "Dengue Hemorrhagic Fever (DHF) Grade III / Dengue Shock Syndrome (DSS)",
            differentialDiagnoses = listOf("DHF Grade IV (Profound Shock)", "Sepsis ec Infeksi Bakterial", "Demam Tifoid dengan Perforasi", "Leptospirosis Berat / Weil Disease"),
            patientPersonaInstruction = "Ibu Pasien: Dok tolong anak saya! Semalam demamnya tinggi tapi tadi pagi badannya malah dingin banget, tangannya basah kedinginan, gelisah merintih haus terus muntah cairan kehitaman!",
            availableExams = listOf(
                ExamItem("E1", "Darah Rutin (CBC) Cito", ExamCategory.LAB, "Hb 17.8 g/dL (Hemokonsentrasi!), Leukosit 2.100/uL, Trombosit 18.000/uL (Trombositopenia Berat!), Ht 52% (Meningkat > 20%).", 95000),
                ExamItem("E2", "Dengue Serology (NS1 Ag & IgG/IgM Dengue)", ExamCategory.LAB, "NS1 Ag (+), IgM Dengue (+), IgG Dengue (+).", 280000),
                ExamItem("E3", "USG Abdomen & Rontgen Thorax Bedside", ExamCategory.IMAGING, "Tampak efusi pleura dextra minimal & cairan bebas asites di cavum abdomen (Kebocoran Plasma!).", 380000),
                ExamItem("E4", "Fungsi Hati (SGOT & SGPT)", ExamCategory.LAB, "SGOT 280 U/L, SGPT 195 U/L.", 110000)
            ),
            optimalExamNames = listOf("Darah Rutin (CBC) Cito", "Dengue Serology (NS1 Ag & IgG/IgM Dengue)", "USG Abdomen & Rontgen Thorax Bedside"),
            optimalCostEstimate = 755000,
            recommendedTreatment = "1. RESUSITASI CAIRAN PRESISI CITO: Infus IV Ringer Laktat / Ringer Asetat 10-20 mL/kgBB bolus cepat dalam 30-60 menit. 2. EVALUASI VITAL & HEMATOKRIT: Bila syok teratasi, turunkan bertahap 7-10 mL/kgBB/jam. Bila syok persisten pasca 2x bolus kristaloid, berikan Koloid 10-20 mL/kgBB/jam. 3. URINATE MONITOR: Target produksi urin 0.5-1 mL/kgBB/jam. 4. TRANSFUSI PRC jika Hb turun tapi syok persisten.",
            kemenkesGuidelines = "Pedoman Tatalaksana Dengue Kemenkes RI: DHF Grade III/DSS ditandai kegagalan sirkulasi dengan tekanan nadi menyempit (≤ 20 mmHg) dan akral dingin. Penanganan utama adalah resusitasi cairan kristaloid isotonis 10-20 mL/kgBB secepatnya.",
            isEmergencyCase = true
        ),

        // 13. Obgyn: Eklamsia Kejang Cito
        ClinicalCase(
            id = "OBGYN-001",
            organSystem = "Obstetri & Ginekologi (Obgyn)",
            title = "Kehamilan 34 Minggu: Preeklamsia Berat (PEB) & Eklamsia dengan Kejang Cito",
            patientAge = 29,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga",
            generalAppearance = "Hamil tua 8 bulan, pasca kejang tonik-klonik 1 kali di rumah, gelisah, pandangan kabur, edema pretibial bilat (+3)",
            chiefComplaint = "Kejang mendadak selama 2 menit pada usia kehamilan 34 minggu, mata mendelik ke atas, disertai pusing berat dan penglihatan kabur.",
            td = "180/110 mmHg",
            nadi = 112,
            rr = 24,
            suhu = 37.0,
            spO2 = 96,
            trueDiagnosis = "Eklamsia pada Kehamilan 34 Minggu & Preeklamsia Berat (PEB)",
            differentialDiagnoses = listOf("Epilepsi dalam Kehamilan", "Meningitis / Ensefalitis", "Stroke Hemoragik ec Krisis Hipertensi", "HELLP Syndrome"),
            patientPersonaInstruction = "Suami Pasien: Dok tolong istri saya hamil 8 bulan! Tadi di rumah tiba-tiba kejang jerit-jerit, matanya mendelik ke atas sama tangannya kaku 2 menit! Sekarang lemes ngeluh pusing hebat sama matanya kabur buram!",
            availableExams = listOf(
                ExamItem("E1", "Proteinuria Urin Dipstick Cito", ExamCategory.LAB, "Dipstick Urin Protein: +3 (Positif Tiga).", 35000),
                ExamItem("E2", "Darah Lengkap & Trombosit", ExamCategory.LAB, "Hb 12.1 g/dL, Trombosit 110.000/uL, Ht 38%.", 95000),
                ExamItem("E3", "Fungsi Hati & Ginjal Cito", ExamCategory.LAB, "SGOT 88 U/L, SGPT 76 U/L, Ureum 32 mg/dL, Kreatinin 1.1 mg/dL.", 220000),
                ExamItem("E4", "USG Kebidanan Bedside", ExamCategory.IMAGING, "Janin tunggal hidup intrauterin, DJJ 144 x/m, persentasi kepala, estimasi BBJ 2100 gram.", 250000)
            ),
            optimalExamNames = listOf("Proteinuria Urin Dipstick Cito", "Darah Lengkap & Trombosit", "Fungsi Hati & Ginjal Cito", "USG Kebidanan Bedside"),
            optimalCostEstimate = 600000,
            recommendedTreatment = "1. CEGAH KEJANG ULANG CITO: Magnesium Sulfat (MgSO4) Loading Dose: 4 gram MgSO4 40% (10 cc) dalam 10 cc Aquabidest IV pelan 5-10 menit + Maintenance Dose: 6 gram MgSO4 40% dalam 500 mL RL drip 28 tpm. (Siapkan Antidotum Kalsium Glukonas 10%!). 2. ANTIHIPERTENSI CITO: Nicardipine IV drip atau Nifedipin 10 mg oral. 3. TERMINASI KEHAMILAN: Konsul Cito Sp.OG untuk terminasi kehamilan (Seksio Sesarea).",
            kemenkesGuidelines = "PNPK Obgyn Kemenkes RI: Eklamsia adalah kegawatdaruratan obstetri utama. Pemberian MgSO4 dosis muat dan pemeliharaan wajib diberikan segera untuk menghentikan dan mencegah kejang berulang, disertai penurunan TD terukur dan persediaan antidotum Kalsium Glukonas.",
            isEmergencyCase = true
        ),

        // 14. Pediatri: Status Epileptikus Anak
        ClinicalCase(
            id = "PED-001",
            organSystem = "Pediatri (Kesehatan Anak)",
            title = "Anak Usia 2 Tahun: Kejang Demam Kompleks / Status Epileptikus Pediatrik",
            patientAge = 2,
            patientGender = "Laki-laki",
            patientOccupation = "Anak Balita",
            generalAppearance = "Anak dalam kondisi kejang tonik-klonik umum aktif di UGD, mulut berbusa, mata mendelik, demam tinggi 39.5°C",
            chiefComplaint = "Anak kejang seluruh tubuh sudah 12 menit belum berhenti, badan panas tinggi sejak tadi siang.",
            td = "95/60 mmHg",
            nadi = 145,
            rr = 32,
            suhu = 39.5,
            spO2 = 92,
            trueDiagnosis = "Status Epileptikus Pediatrik ec Kejang Demam Kompleks",
            differentialDiagnoses = listOf("Meningitis / Ensefalitis Bakterial", "Epilepsi Anak First Episode", "Gangguan Elektrolit (Hiponatremia)", "Abses Otak"),
            patientPersonaInstruction = "Ibu Pasien: Dok tolong anak saya kejang dari tadi nggak berhenti-berhenti di jalan! Tangan kakinya kelojotan matanya mendelik! Panas badannya tinggi banget Dok tolong!",
            availableExams = listOf(
                ExamItem("E1", "Gula Darah Sewaktu (GDS) Cito", ExamCategory.LAB, "GDS: 108 mg/dL (Menyingkirkan Hipoglikemia).", 35000),
                ExamItem("E2", "Darah Rutin & Elektrolit Cito", ExamCategory.LAB, "Leukosit 18.200/uL (Leukositosis Reaktif), Na 137, K 4.0, Cl 100.", 180000)
            ),
            optimalExamNames = listOf("Gula Darah Sewaktu (GDS) Cito", "Darah Rutin & Elektrolit Cito"),
            optimalCostEstimate = 215000,
            recommendedTreatment = "1. AIRWAY & OKSIGENASI CITO: Miringkan posisi anak, bebaskan jalan napas, Oksigen 2-4 Lpm. 2. HENTIKAN KEJANG CITO (LINE 1): Diazepam Rektal 5 mg / Diazepam IV 0.3-0.5 mg/kgBB pelan 2 menit. 3. JIKA KEJANG PERSISTEN > 5 MENIT (LINE 2): Fenitoin IV 20 mg/kgBB dilarutkan NaCl 0.9% drip pelan 20 menit. 4. ANTIPIRETIK: Parasetamol Suppositoria 125 mg / IV Parasetamol 15 mg/kgBB.",
            kemenkesGuidelines = "Pedoman IDAI / Kemenkes RI: Kejang > 5 menit pada anak adalah kegawatdaruratan status epileptikus. Lini pertama Diazepam IV/Rektal, diikuti Fenitoin IV jika kejang berlanjut > 10 menit, serta koreksi demam dan pencarian fokus infeksi.",
            isEmergencyCase = true
        ),

        // 15. Kardiologi: Edema Paru Akut
        ClinicalCase(
            id = "CARDIO-002",
            organSystem = "Kardiologi",
            title = "Gagal Jantung Akut (ADHF / Edema Paru Akut) ec Krisis Hipertensi",
            patientAge = 65,
            patientGender = "Laki-laki",
            patientOccupation = "Pensiunan",
            generalAppearance = "Gagal napas berat, duduk orthopnea 90°, batuk berdahak merah muda berbusa (pink frothy sputum), ronki basah halus meluas",
            chiefComplaint = "Mendadak sesak napas sangat berat hingga tidak bisa berbaring sejak 2 jam lalu, disertai batuk dahak berbusa merah muda.",
            td = "200/120 mmHg",
            nadi = 122,
            rr = 34,
            suhu = 36.6,
            spO2 = 85,
            trueDiagnosis = "Acute Decompensated Heart Failure (ADHF) / Edema Paru Akut ec Krisis Hipertensi",
            differentialDiagnoses = listOf("PPOK Eksaserbasi Akut", "Pneumonia Bilateral Berat", "ARDS", "STEMI / SKA dengan Komplikasi Pomp"),
            patientPersonaInstruction = "Anak Pasien: Bapak saya sesak banget Dok! Tidak bisa tidur terlentang serasa mau tenggelam, batuk keluar busa warna merah muda! Punya darah tinggi tua tidak rutin minum obat.",
            availableExams = listOf(
                ExamItem("E1", "Auskultasi Paru & Jantung", ExamCategory.PEMFIS, "Ronki basah halus meluas di 2/3 basal paru kanan-kiri, gallop S3 (+), JVP R+4 cmH2O.", 0),
                ExamItem("E2", "Rontgen Thorax AP Bedside", ExamCategory.IMAGING, "Kardiomegali CTR 65%, Infiltrat perihilar butterfly pattern khas Edema Paru Akut (Bat Wing Appearance).", 180000),
                ExamItem("E3", "EKG 12-Lead", ExamCategory.PEMFIS, "Sinus Takikardi 120 x/m, LVH dengan Strain Pattern.", 120000),
                ExamItem("E4", "NT-proBNP / BNP", ExamCategory.LAB, "NT-proBNP: 4.800 pg/mL (Sangat Tinggi POSITIF Heart Failure).", 480000),
                ExamItem("E5", "Analisis Gas Darah (AGD)", ExamCategory.LAB, "pH 7.28, PaCO2 50, PaO2 55, SaO2 85% (Gagal Napas Tipe I & II).", 320000)
            ),
            optimalExamNames = listOf("Auskultasi Paru & Jantung", "Rontgen Thorax AP Bedside", "EKG 12-Lead", "NT-proBNP / BNP", "Analisis Gas Darah (AGD)"),
            optimalCostEstimate = 1100000,
            recommendedTreatment = "1. OKSIGENASI & POSISI CITO: Duduk tegak (High Fowlers 90°), Oksigen NRM 12-15 Lpm / CPAP / NIPV. 2. DIURETIK CITO: Furosemid IV 80-120 mg (2-3 ampul) IV bolus cito. 3. VASODILATOR CITO: Nitroglycerin (NTG) IV infusion mulai 10-20 mcg/menit atau ISDN 5mg Sublingual. 4. RESTRIKSI CAIRAN & ICU.",
            kemenkesGuidelines = "PPK Kardiologi Kemenkes RI: Edema Paru Akut dengan Krisis Hipertensi mengutamakan penanganan posisi tegak, Oksigenasi NRM/NIPV, Furosemid dosis tinggi IV, dan Vasodilator IV (Nitroglycerin) untuk mengedrop preload & afterload jantung.",
            isEmergencyCase = true
        ),

        // 16. Gastro: SCBA Melena
        ClinicalCase(
            id = "GASTRO-002",
            organSystem = "Gastroenterologi & Hepatologi",
            title = "Perdarahan Saluran Cerna Bagian Atas (SCBA) & Melena Masif ec Ruptur Varisus Esofagus",
            patientAge = 52,
            patientGender = "Laki-laki",
            patientOccupation = "Wiraswasta",
            generalAppearance = "Anemis pucat berat, akral dingin basah, hematemesis (muntah darah hitam seperti kopi) & melena (BAB hitam lempung), stigmata sirosis (+)",
            chiefComplaint = "Muntah darah kehitaman 3 kali sebanyak 2 gelas dan BAB warna hitam pekat seperti aspal cair, badan pucat lemas hampir pingsan.",
            td = "80/50 mmHg (Syok Hemoragik)",
            nadi = 126,
            rr = 26,
            suhu = 36.2,
            spO2 = 94,
            trueDiagnosis = "Perdarahan Saluran Cerna Bagian Atas (SCBA) Masif ec Ruptur Varisus Esofagus & Syok Hemoragik pada Sirosis Hati",
            differentialDiagnoses = listOf("Gastropati Hipertensi Portal", "Perdarahan Ulkus Peptikum (Mallory-Weiss)", "Karsinoma Lambung", "Erosi Lambung ec NSAID"),
            patientPersonaInstruction = "Istri Pasien: Dok, suami saya muntah darah hitam banyak banget kaya ter kopi, terus BAB-nya hitam legam kaya aspal baunya busuk sekali! Suami saya punya penyakit liver/sirosis hati lama.",
            availableExams = listOf(
                ExamItem("E1", "Darah Rutin (CBC) Cito", ExamCategory.LAB, "Hb 5.2 g/dL (Anemia Berat ec Perdarahan Masif!), Leukosit 11.200, Trombosit 65.000/uL, Ht 16%.", 95000),
                ExamItem("E2", "Profil Hemostasis & Fungsi Hati", ExamCategory.LAB, "PT 18.5 dtk, INR 1.65, SGOT 112 U/L, SGPT 84 U/L, Albumin 2.1 g/dL.", 280000),
                ExamItem("E3", "Golongan Darah & Crossmatch Cito", ExamCategory.LAB, "Golongan O Rh(+), Persiapan Transfusi PRC 4 kolf Cito!", 120000),
                ExamItem("E4", "Endoskopi SCBA (EGD Emergency)", ExamCategory.IMAGING, "Tampak Varisus Esofagus Grade III dengan active spurting bleeding di distal esofagus.", 1200000)
            ),
            optimalExamNames = listOf("Darah Rutin (CBC) Cito", "Profil Hemostasis & Fungsi Hati", "Golongan Darah & Crossmatch Cito", "Endoskopi SCBA (EGD Emergency)"),
            optimalCostEstimate = 1695000,
            recommendedTreatment = "1. RESUSITASI CAIRAN & AIRWAY CITO: Miringkan pasien, pasang 2 IV line 16G, infus RL / NaCl 0.9% cepat target TD sistolik ~90 mmHg. 2. OBAT VASOAKTIF CITO: Somatostatin (Loading 250 mcg IV bolus dilanjutkan Drip 250 mcg/jam) atau Octreotide. 3. TRANSFUSI PRC Cito target Hb 7-8 g/dL + FFP. 4. PPI IV Drip Omeprazole 80mg. 5. ANTIBIOTIK PROFILAKSIS Seftriaxon 1g IV. 6. ENDOSKOPI LIGASI CITO.",
            kemenkesGuidelines = "PPK Gastroenterologi Kemenkes RI: Perdarahan Varisus Esofagus pada Sirosis Hati adalah kegawatdaruratan mematikan. Penanganan utama meliputi resusitasi cairan terbatas (target Hb 7-8 g/dL), vasoaktif (Somatostatin/Octreotide) secepatnya, PPI IV drip, antibiotik profilaksis, dan ligasi endoskopi Cito.",
            isEmergencyCase = true
        ),

        // 17. Endokrin: Krisis Tiroid
        ClinicalCase(
            id = "ENDO-002",
            organSystem = "Endokrinologi & Metabolik",
            title = "Krisis Tiroid (Thyroid Storm) ec Morbus Basedow Graves",
            patientAge = 36,
            patientGender = "Perempuan",
            patientOccupation = "Karyawan Swasta",
            generalAppearance = "Sangat gelisah, delirium / agitasi hebat, hipertermia (suhu 39.8°C), profuse sweating, eksoftalmus (+), tremor halus",
            chiefComplaint = "Demam sangat tinggi 40°C, jantung berdebar-debar sangat kencang, gelisah mengamuk dan bingung sejak tadi siang.",
            td = "165/85 mmHg",
            nadi = 154,
            rr = 30,
            suhu = 39.8,
            spO2 = 95,
            trueDiagnosis = "Krisis Tiroid (Thyroid Storm) ec Morbus Basedow Graves (Burch-Wartofsky Score > 45)",
            differentialDiagnoses = listOf("Sepsis ec Infeksi Berat", "Feokromositoma", "Ensefalitis / Meningitis", "Sintesis Amfetamin / Intoksikasi Sympathomimetic"),
            patientPersonaInstruction = "Suami Pasien: Dok! Istri saya punya penyakit gondok/tiroid tapi 3 hari ini putus obat. Tadi siang mendadak panasnya tinggi banget hampir 40, jantungnya berdebar kencang serasa mau melompat, terus mengamuk gelisah tidak kenal orang!",
            availableExams = listOf(
                ExamItem("E1", "Thyroid Panel (Free T3, Free T4, TSHs) Cito", ExamCategory.LAB, "FT4 > 7.5 ng/dL, FT3 > 20 pg/mL, TSHs < 0.005 uIU/mL.", 450000),
                ExamItem("E2", "EKG 12-Lead", ExamCategory.PEMFIS, "Atrial Fibrillation (AF) Rapid Ventricular Response (RVR) HR 152 x/menit.", 120000),
                ExamItem("E3", "Skor Burch-Wartofsky", ExamCategory.PEMFIS, "Skor 65 (Skor > 45 = KEKHUSUSAN KRISIS TIROID!).", 0),
                ExamItem("E4", "Darah Rutin & SGOT/SGPT", ExamCategory.LAB, "Leukosit 14.200/uL, SGOT 72 U/L, SGPT 68 U/L.", 205000)
            ),
            optimalExamNames = listOf("Thyroid Panel (Free T3, Free T4, TSHs) Cito", "EKG 12-Lead", "Skor Burch-Wartofsky", "Darah Rutin & SGOT/SGPT"),
            optimalCostEstimate = 775000,
            recommendedTreatment = "1. BLOK SINTESIS HORMON CITO: Propiltiourasil (PTU) 600-1000 mg loading dose oral/NGT, dilanjutkan 200 mg tiap 4 jam. 2. BLOK PELEPASAN HORMON (1 jam pasca PTU): Lugol Iodine 8-10 tetes tiap 6 jam. 3. BLOK SIMPATIS: Propranolol 60-80 mg oral tiap 4 jam. 4. KORTIKOSTEROID: Deksametason 2 mg IV tiap 6 jam / Hidrokortison 100 mg IV. 5. PENDINGINAN: Parasetamol 1g IV (Avoid Aspirin!) & Cooling Blanket.",
            kemenkesGuidelines = "PPK Endokrinologi Kemenkes RI: Krisis Tiroid adalah kedaruratan endokrin dengan mortalitas tinggi. Penanganan sistematis mencakup 4 blok: Blok Sintesis (PTU/Methimazole), Blok Pelepasan (Lugol/Iodine), Blok Simpatis (Propranolol), dan Blok Konversi (Kortikosteroid) serta antipiretik non-aspirin.",
            isEmergencyCase = true
        ),

        // 18. Pulmo: TB Hemoptisis Masif
        ClinicalCase(
            id = "PULMO-003",
            organSystem = "Pulmonologi",
            title = "TB Paru Kasus Baru dengan Hemoptisis Masif (Batuk Darah 300 cc) & Threatening Airway",
            patientAge = 45,
            patientGender = "Laki-laki",
            patientOccupation = "Buruh Pabrik",
            generalAppearance = "Tampak sangat cemas ketakutan, batuk darah segar berbuih membual dari mulut ~300 mL, ronki basah kasar di paru kanan, sianosis bibir",
            chiefComplaint = "Batuk menyemprotkan darah segar berwarna merah terang sebanyak lebih dari 1 gelas (300 mL) dalam 1 jam, terasa sesak tersedak darah.",
            td = "95/60 mmHg",
            nadi = 118,
            rr = 30,
            suhu = 37.8,
            spO2 = 88,
            trueDiagnosis = "Tuberkulosis (TB) Paru Kasus Baru dengan Hemoptisis Masif & Gagal Napas Akut",
            differentialDiagnoses = listOf("Karsinoma Paru dengan Perdarahan", "Bronkiektasis Terinfeksi", "Abses Paru Dextra", "Pneumonia Nekrotikan"),
            patientPersonaInstruction = "Pasien & Adik: Dok tolong kakak saya! Dari tadi batuk-batuk seminggu ini, tapi barusan batuknya keluar darah segar bergelas-gelas merah kental! Kakak saya tersedak darah napasnya sesak banget!",
            availableExams = listOf(
                ExamItem("E1", "Auskultasi Paru & Status Respirasi", ExamCategory.PEMFIS, "Sianosis bibir (+), ronki basah kasar masif di hemithorax dextra, suara napas menurun di apeks-medius dextra.", 0),
                ExamItem("E2", "Tes Cepat Molekuler (TCM TB / GeneXpert Sputum)", ExamCategory.LAB, "MTB Detected, Rifampicin Resistance NOT Detected (TB Sensitif Obat).", 350000),
                ExamItem("E3", "Rontgen Thorax AP Bedside", ExamCategory.IMAGING, "Infiltrat luas & kavitas berdinding tebal di lobus superior paru kanan (Apeks Dextra).", 180000),
                ExamItem("E4", "Darah Rutin & Hemostasis Cito", ExamCategory.LAB, "Hb 8.1 g/dL, Leukosit 13.500/uL, Trombosit 310.000/uL, PT 11.8, APTT 30.2 dtk.", 280000)
            ),
            optimalExamNames = listOf("Auskultasi Paru & Status Respirasi", "Tes Cepat Molekuler (TCM TB / GeneXpert Sputum)", "Rontgen Thorax AP Bedside", "Darah Rutin & Hemostasis Cito"),
            optimalCostEstimate = 810000,
            recommendedTreatment = "1. AIRWAY & POSISI CITO: Miringkan Pasien ke Sisi Paru yang Sakit (Dekubitus Kanan) untuk mencegah aspirasi darah ke paru sehat! Bebaskan jalan napas. 2. OKSIGENASI NRM 10-12 Lpm. 3. HEMOSTATIK: Asam Traneksamat (TXA) 500-1000 mg IV tiap 8 jam + Vit K 10mg IV. 4. SEDATIF RINGAN & ANTIBIOTIK: Injeksi Codein / Sedatif ringan penekan batuk + Seftriaxon 2g IV. 5. OAT Kategori 1 bila hemostasis terkontrol.",
            kemenkesGuidelines = "PPK Pulmonologi Kemenkes RI: Batuk darah masif (> 200-600 mL/24 jam) adalah kedaruratan paru dengan risiko kematian utama akibat asfiksia tersedak. Posisikan miring ke sisi sakit, berikan hemostatik IV, bebaskan jalan napas, dan tatalaksana etiologi TB Paru.",
            isEmergencyCase = true
        ),

        // 19. Dermatovenerologi: TEN / SJS
        ClinicalCase(
            id = "DERMA-001",
            organSystem = "Dermatovenerologi (Kulit & Kelamin)",
            title = "Toxic Epidermal Necrolysis (TEN) / Sindrom Stevens-Johnson (SJS) ec Erupsi Obat Allopurinol",
            patientAge = 50,
            patientGender = "Laki-laki",
            patientOccupation = "Wiraswasta",
            generalAppearance = "Epidermolisis / kulit melepuh terkelupas luas > 30% BSA, Tanda Nikolsky (+), krusta kehitaman erosif di bibir, mata, dan genitalia",
            chiefComplaint = "Kulit seluruh tubuh melepuh seperti tersiram air panas, mengelupas perih luar biasa, mata merah berair, dan mulut sariawan parah sulit menelan pasca minum obat asam urat.",
            td = "95/60 mmHg",
            nadi = 115,
            rr = 24,
            suhu = 38.9,
            spO2 = 96,
            trueDiagnosis = "Toxic Epidermal Necrolysis (TEN) / Sindrom Stevens-Johnson (SJS) Overlap ec Erupsi Obat Alopurinol",
            differentialDiagnoses = listOf("Staphylococcal Scalded Skin Syndrome (SSSS)", "Eritema Multiforme Mayor", "Pemfigus Vulgaris", "Generalized Bullous Fixed Drug Eruption"),
            patientPersonaInstruction = "Pasien & Anak: Dok tolong papa saya! 5 hari lalu minum obat asam urat Allopurinol dari warung/apotek, terus badannya merah-merah bentol lalu melepuh kulitnya mengelupas perih banget seperti terbakar! Bibirnya pecah-pecah berdarah tidak bisa makan!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Dermatologis (BSA & Nikolsky Sign)", ExamCategory.PEMFIS, "Nikolsky Sign (+). Epidermolisis & makula eritematosa konfluen dengan bula kendor luas mencakup 35% BSA. Erosi mukosa bibir, konjungtiva, dan uretra.", 0),
                ExamItem("E2", "Biopsi Kulit Histopatologi", ExamCategory.LAB, "Nekrosis epidermis penuh (full-thickness epidermal necrosis) dengan pembentukan bula subepidermal.", 450000),
                ExamItem("E3", "Darah Rutin & Elektrolit Cito", ExamCategory.LAB, "Hb 13.8 g/dL, Leukosit 14.500/uL, Trombosit 190.000/uL, Na 130, K 3.8.", 255000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Dermatologis (BSA & Nikolsky Sign)", "Darah Rutin & Elektrolit Cito", "Biopsi Kulit Histopatologi"),
            optimalCostEstimate = 705000,
            recommendedTreatment = "1. STOP SEMUA OBAT TERSANGKA CITO (Hentikan Allopurinol segera!). 2. RESUSITASI CAIRAN & ELEKTROLIT: Infus RL hangat sesuai kalkulasi luas luka bakar (SJS/TEN Fluid Protocol). 3. KORTIKOSTEROID SISTEMIK / IVIG: Metilprednisolon 1-2 mg/kgBB/hari IV atau Deksametason 10-20 mg IV/hari. 4. PERAWATAN LUKA STERIL: Kompres NaCl 0.9% / Kassa Vaselin Steril. 5. PERAWATAN MUKOSA MATA & MULUT: Tetes mata artifisial / salep mata antibiotik tiap 2 jam & Kumut Chlorhexidine.",
            kemenkesGuidelines = "PPK Dermatovenerologi Kemenkes RI: SJS/TEN adalah kegawatdaruratan kulit mengancam jiwa ec reaksi hipersensitivitas obat. Penanganan utama: hentikan obat pencetus segera, resusitasi cairan hangat, kortikosteroid sistemik IV, perawatan mukosa mata cegah kebutaan, dan isolasi steril.",
            isEmergencyCase = true
        ),

        // 20. THT-KL: Abses Peritonsil Quinsy
        ClinicalCase(
            id = "THT-001",
            organSystem = "THT-KL",
            title = "Abses Peritonsil (Quinsy) Dextra dengan Trismus & Threatening Airway",
            patientAge = 31,
            patientGender = "Laki-laki",
            patientOccupation = "Karyawan Kantor",
            generalAppearance = "Tampak sakit berat, trismus (sulit membuka mulut < 2 cm), suara 'hot potato voice', hipersalivasi (air liur menetes), deviasi uvula ke sinistra",
            chiefComplaint = "Nyeri tenggorokan hebat sebelah kanan hingga tidak bisa menelan air ludah, mulut tidak bisa dibuka lebar, dan leher kanan membengkak nyeri.",
            td = "130/80 mmHg",
            nadi = 104,
            rr = 22,
            suhu = 38.7,
            spO2 = 97,
            trueDiagnosis = "Abses Peritonsil (Quinsy) Dextra dengan Trismus & Ancaman Obstruksi Jalan Napas",
            differentialDiagnoses = listOf("Abses Retrofaring", "Abses Submandibula (Ludwig Angina)", "Tonsilitis Akut Parenkimatosa", "Infiltrat Peritonsil"),
            patientPersonaInstruction = "Istri Pasien: Dok, suami saya sakit tenggorokan parah sebelah kanan dari 4 hari lalu. Sekarang mulutnya kaku tidak bisa dibuka lebar sama sekali, bicaranya sengau seperti ngulum kentang panas, ludahnya menetes terus karena tidak bisa ditelan!",
            availableExams = listOf(
                ExamItem("E1", "Orofaringologis & Laringoskopi Indirek", ExamCategory.PEMFIS, "Trismus 1.5 cm. Tonsil Dextra T4 membesar mendorong uvula ke kiri, terdapat pembengkakan fluktuatif hiperemis di arkus anterior dextra.", 0),
                ExamItem("E2", "Pungsi Aspirasi Jarum Halus Peritonsil (Needle Aspiration)", ExamCategory.PEMFIS, "Aspirasi spuit keluar pus/nanah kekuningan berbau busuk 4 cc (Konfirmasi Abses!).", 150000),
                ExamItem("E3", "Darah Rutin & CRP Cito", ExamCategory.LAB, "Leukosit 18.900/uL (Leukositosis Berat), CRP 85 mg/L.", 195000)
            ),
            optimalExamNames = listOf("Orofaringologis & Laringoskopi Indirek", "Pungsi Aspirasi Jarum Halus Peritonsil (Needle Aspiration)", "Darah Rutin & CRP Cito"),
            optimalCostEstimate = 345000,
            recommendedTreatment = "1. INSISI & DRAINASE ABSES CITO: Insisi & Drainase Abses Peritonsil di titik paling fluktuatif (arkus anterior dextra) pasca anestesi lokal Lidokain spray/injeksi! 2. ANTIBIOTIK PARENTERAL: Ampisilin-Sulbaktam 1.5g IV / Seftriaxon 2g IV + Metronidazol 500mg IV. 3. KORTIKOSTEROID: Deksametason 10 mg IV. 4. ANALGETIK & KUMUR ANTISEPTIK.",
            kemenkesGuidelines = "PPK THT-KL Kemenkes RI: Abses Peritonsil dengan trismus dan Hot Potato Voice memerlukan koreksi Cito Insisi Drainase abses untuk mencegah komplikasi penyebaran ke ruang fasial dalam (Abses Leher Dalam) dan obstruksi jalan napas.",
            isEmergencyCase = true
        ),

        // 21. Oftalmologi: Glaukoma Akut Cito
        ClinicalCase(
            id = "MATA-001",
            organSystem = "Oftalmologi (Mata)",
            title = "Glaukoma Akut Sudut Tertutup (Acute Angle-Closure Glaucoma) OS Cito",
            patientAge = 58,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga",
            generalAppearance = "Meringis kesakitan memegang mata kiri, injeksi siliar masif, kornea keruh udematosa, pupil mid-midriasis non-reaktif, TIO sangat tinggi",
            chiefComplaint = "Mata kiri mendadak sangat nyeri tembus ke kepala, melihat lingkaran pelangi (halo) di sekitar lampu, mual muntah, dan penglihatan mata kiri buram mendadak.",
            td = "150/90 mmHg",
            nadi = 98,
            rr = 20,
            suhu = 36.8,
            spO2 = 98,
            trueDiagnosis = "Glaukoma Akut Sudut Tertutup (Acute Angle-Closure Glaucoma) Oculi Sinistra (OS)",
            differentialDiagnoses = listOf("Uveitis Akut Anterior", "Endoftalmitis", "Ulkus Kornea dengan Perforasi", "Migrain Atypical"),
            patientPersonaInstruction = "Pasien: Dok, mata kiri saya rasanya mau pecah sakit sekali tembus sampai ke belakang kepala! Kalau lihat lampu kaya ada lingkaran pelangi rainbow, mata kiri langsung buram gelap. Saya sampai mual muntah karena sakit kepalanya!",
            availableExams = listOf(
                ExamItem("E1", "Tonometri Schiotz / Non-Contact Tonometry OS Cito", ExamCategory.PEMFIS, "Tekanan Intraokular (TIO) OS: 58 mmHg (SANGAT TINGGI! Normal: 10-21 mmHg), OD: 15 mmHg.", 80000),
                ExamItem("E2", "Slit-Lamp Biomicroscopy OS", ExamCategory.PEMFIS, "Injeksi silia (+), kornea udem keruh, bilik mata depan (COA) sangat dangkal, pupil midriasis 5mm fixed non-reaktif.", 120000),
                ExamItem("E3", "Gonioskop OS", ExamCategory.PEMFIS, "Sudut bilik mata depan OS tertutup rapat Grade 0 (Shaffer).", 150000),
                ExamItem("E4", "Visus / Tajam Penglihatan", ExamCategory.PEMFIS, "Visus OS: 1/60 (Buram berat), OD: 6/6 (Normal).", 0)
            ),
            optimalExamNames = listOf("Tonometri Schiotz / Non-Contact Tonometry OS Cito", "Slit-Lamp Biomicroscopy OS", "Gonioskop OS", "Visus / Tajam Penglihatan"),
            optimalCostEstimate = 350000,
            recommendedTreatment = "1. TURUNKAN TIO CITO: Asetazolamid (Diamox) 500 mg oral loading dose dilanjutkan 250 mg tiap 6 jam + Injeksi Manitol 20% IV 1-2 g/kgBB drip cepat dalam 30-45 menit. 2. OBAT TETES TOPIKAL: Pilokarpin 2% tetes mata OS 1 tetes tiap 15 menit + Timolol 0.5% tetes mata OS. 3. ANTI-NYERI & ANTI-MUAL: Ketorolak 30 mg IV + Ondansetron 4 mg IV. 4. RUJUK CITO LASER IRIDOTOMI.",
            kemenkesGuidelines = "PPK Oftalmologi Kemenkes RI: Glaukoma Akut Sudut Tertutup adalah kegawatdaruratan mata mengancam kebutaan permanen dalam hitungan jam. Penanganan utama: turunkan TIO cito dengan Asetazolamid oral/Manitol IV, tetes mata Miotik (Pilokarpin 2%) & Beta Blocker (Timolol), serta iridotomi laser.",
            isEmergencyCase = true
        ),

        // 22. Psikiatri: Gaduh Gelisah
        ClinicalCase(
            id = "PSIK-001",
            organSystem = "Psikiatri & Jiwa",
            title = "Skizofrenia Paranoid Eksaserbasi Akut dengan Perilaku Gaduh Gelisah (Emergency Psychiatric)",
            patientAge = 30,
            patientGender = "Laki-laki",
            patientOccupation = "Belum Bekerja",
            generalAppearance = "Gelisah hebat, mengamuk di UGD dipagari 4 penolong, bisikan halusinasi auditorik (+), waham kejar (+), afek tumpul reaktif",
            chiefComplaint = "Pasien mengamuk membanting barang di rumah, mengancam tetangga dengan pisau karena mendengar bisikan gaib yang menyuruh membunuh musuh.",
            td = "140/90 mmHg",
            nadi = 112,
            rr = 22,
            suhu = 36.9,
            spO2 = 98,
            trueDiagnosis = "Skizofrenia Paranoid Eksaserbasi Akut dengan Perilaku Gaduh Gelisah & Risiko Perilaku Kekerasan (RPK)",
            differentialDiagnoses = listOf("Delirium ec Kondisi Medis Umum / Sepsis", "Bipolar I Episode Kini Manik dengan Gejala Psikotik", "Gangguan Psikotik Akut dan Sementara", "Intoksikasi Zat Psikoaktif / Napza"),
            patientPersonaInstruction = "Keluarga & Pasien: Dok! Dia dari semalam ngamuk-ngamuk hancurin kaca rumah sambil bawa pisau! Katanya ada bisikan bisikin suruh serang tetangga karena merasa mau dibunuh tentara rahasia! Sudah 2 minggu putus obat jiwa!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Status Mental (PANSS-EC / Psychiatric Eval)", ExamCategory.PEMFIS, "PANSS-EC Score: 28 (Sangat Gaduh Gelisah!). Waham kejar (+), Halusinasi Dengar (+), Asosiasi Longgar (+).", 0),
                ExamItem("E2", "Tes Urin Napza 6-Parameter Cito", ExamCategory.LAB, "Amphetamine (-), THC (-), Morphine (-), Benzodiazepine (-), Methamphetamine (-), Cocaine (-).", 180000),
                ExamItem("E3", "GDS & Elektrolit Cito", ExamCategory.LAB, "GDS 110 mg/dL, Na 139, K 4.1.", 195000),
                ExamItem("E4", "EKG 12-Lead Bedside", ExamCategory.PEMFIS, "Sinus Takikardia 110 x/m, QTc interval 410 ms (Aman untuk Antipsikotik IV/IM).", 120000)
            ),
            optimalExamNames = listOf("Pemeriksaan Status Mental (PANSS-EC / Psychiatric Eval)", "Tes Urin Napza 6-Parameter Cito", "GDS & Elektrolit Cito", "EKG 12-Lead Bedside"),
            optimalCostEstimate = 495000,
            recommendedTreatment = "1. PENATALAKSANAAN PERILAKU GADUH GELISAH CITO: Fiksasi Fisik Restraint (Restraint 4 Titik) secara humanis & aman. 2. INJEKSI ANTIPSIKOTIK CITO (RAPID TRANQUILIZATION): Injeksi Haloperidol 5 mg IM (ulangi tiap 30-60m max 20mg/hari) + Injeksi Diazepam 10 mg IM / Lorazepam 2 mg IM. 3. INJEKSI PREVENSI EPS: Injeksi Diphenhydramine 50 mg IM bila timbul distonia akut. 4. EVALUASI RAWAT INAP RSJ.",
            kemenkesGuidelines = "PPK Psikiatri Kemenkes RI: Gaduh Gelisah Psikotik adalah kedaruratan psikiatri. Utamakan keselamatan pasien & petugas (restraint aman), injeksi Haloperidol IM + Diazepam/Lorazepam IM untuk rapid tranquilization, serta singkirkan kausa organik (Delirium/Napza).",
            isEmergencyCase = true
        ),

        // 23. Kardiologi: Tamponade Jantung
        ClinicalCase(
            id = "CARDIO-003",
            organSystem = "Kardiologi",
            title = "Tamponade Jantung Cito ec Efusi Perikardial Masif (Trias Beck (+))",
            patientAge = 48,
            patientGender = "Laki-laki",
            patientOccupation = "Guru PNS",
            generalAppearance = "Tampak sakit sangat berat, posisi membungkuk ke depan (Tripod position), gelisah, sianosis, Trias Beck (+): Hipotensi, JVP meningkat masif, Suara jantung jauh meredup",
            chiefComplaint = "Sesak napas hebat hingga terasa tercekik sejak tadi pagi, dada terasa penuh tertekan, lemas lunglai dan keringat dingin.",
            td = "80/50 mmHg (Pulsus Paradoxus > 12 mmHg)",
            nadi = 138,
            rr = 32,
            suhu = 37.5,
            spO2 = 88,
            trueDiagnosis = "Tamponade Jantung Cito ec Efusi Perikardial Masif (Suspek Perikarditis Tuberkulosa)",
            differentialDiagnoses = listOf("Pneumothorax Ventil / Tension", "Syok Kardiogenik ec Infark Miokard", "Emboli Paru Masif", "Diseksi Aorta"),
            patientPersonaInstruction = "Pasien & Istri: Dok tolong suami saya sesak napas parah serasa dada diimpit batu besar! Napasnya megap-megap, badannya makin lemas dingin kencang, lehernya bengkak uratnya menonjol besar!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Trias Beck & Pulsus Paradoxus", ExamCategory.PEMFIS, "Trias Beck POSITIF: Suara jantung distant/meredup, JVP R+6 cmH2O dengan X-descent prominen, TD 80/50 mmHg. Pulsus paradoxus terukur penurunan sistolik 16 mmHg saat inspirasi.", 0),
                ExamItem("E2", "Ekokardiografi Bedside Cito (FOCUS Echo)", ExamCategory.IMAGING, "Efusi perikardial masif anekoik (echo-free space > 25 mm), kolaps atrium kanan (RA) saat diastol dan kolaps ventrikel kanan (RV) awal diastol (Sign of Cardiac Tamponade!).", 500000),
                ExamItem("E3", "EKG 12-Lead Bedside", ExamCategory.PEMFIS, "Low Voltage QRS Complex di semua lead + Electrical Alternans (Amplitudo QRS berganti-ganti tiap denyut khas efusi masif).", 120000),
                ExamItem("E4", "Rontgen Thorax AP Bedside", ExamCategory.IMAGING, "Kardiomegali masif berbentuk seperti kantong air / botol (Water Bottle Heart Appearance).", 180000),
                ExamItem("E5", "Perikardiosentesis & Analisis Cairan Perikard", ExamCategory.LAB, "Aspirasi perikardial keluar cairan eksudat serosanguinosa 450 cc. Tes TCM TB cairan: MTB Detected.", 450000)
            ),
            optimalExamNames = listOf("Pemeriksaan Trias Beck & Pulsus Paradoxus", "Ekokardiografi Bedside Cito (FOCUS Echo)", "EKG 12-Lead Bedside", "Rontgen Thorax AP Bedside"),
            optimalCostEstimate = 800000,
            recommendedTreatment = "1. PERIKARDIOSENTESIS CITO: Lakukan Perikardiosentesis Evakuasi Darurat di UGD / Cath Lab dengan panduan USG/ECHO untuk dekompresi kavum perikard! 2. OKSIGENASI & RESUSITASI CAIRAN: Oksigen NRM 10-15 Lpm + Infus NaCl 0.9% 500 mL cepat (Loading fluid untuk tingkatkan preload). 3. HINDARI VENTILASI TEKANAN POSITIF (Ventilator/NIPV dapat memperburuk venous return!). 4. TERAPI ETIOLOGI: OAT Kategori 1.",
            kemenkesGuidelines = "PPK Kardiologi Kemenkes RI: Tamponade Jantung adalah kegawatdaruratan struktural jantung mematikan. Penanganan mutlak adalah Perikardiosentesis Dekompresi Cito. Berikan resusitasi cairan kristaloid untuk menjaga isi sekuncup sebelum tindakan.",
            isEmergencyCase = true
        ),

        // 24. Neurologi: Meningitis Bakterialis
        ClinicalCase(
            id = "NEURO-002",
            organSystem = "Neurologi",
            title = "Meningitis Bakterialis Akut dengan Penurunan Kesadaran & Rangsang Meningeal (+)",
            patientAge = 26,
            patientGender = "Laki-laki",
            patientOccupation = "Mahasiswa",
            generalAppearance = "Somnolen-Sopor (GCS E3V2M5 = 10), febris 39.5°C, Kaku Kuduk (+), Tanda Kernig (+), Fotofobia (+), Purpura petekie di kulit (+)",
            chiefComplaint = "Panas tinggi mendadak sejak 2 hari, demam tinggi disertai sakit kepala hebat tak tertahankan, muntah menyembur, lalu linglung gelisah dan sulit dibangunkan.",
            td = "130/80 mmHg",
            nadi = 118,
            rr = 24,
            suhu = 39.6,
            spO2 = 96,
            trueDiagnosis = "Meningitis Bakterialis Akut (Suspek Neisseria meningitidis / Streptococcus pneumoniae)",
            differentialDiagnoses = listOf("Meningitis Tuberkulosa", "Ensefalitis Viral (Herpes Simplex)", "Abses Otak dengan Ruptur", "Perdarahan Subaraknoid (SAH)"),
            patientPersonaInstruction = "Keluarga Pasien: Dok! Anak saya mahasiswa dari kemarin demam tinggi mengeluh kepalanya mau pecah sakit sekali, muntah-muntah nyembur terus tadi pagi mendadak linglung tidak kenal orang terus tidak bisa dibangunkan!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Rangsang Meningeal & Neurologis", ExamCategory.PEMFIS, "Kaku Kuduk (+), Tanda Kernig (+), Tanda Brudzinski I & II (+). Refleks patologis Babinski (+/+). GCS E3V2M5.", 0),
                ExamItem("E2", "Pungsi Lumbal (Lumbar Puncture) & Analisis LCR Cito", ExamCategory.LAB, "Cairan Serebrospinal (LCR) Keruh Purulen. Tekanan pembukaan tinggi (280 mmH2O), PMN Leukosit 3.200/uL, Protein LCR 240 mg/dL (Sangat Tinggi), Glukosa LCR 15 mg/dL (Rasio LCR/GDS < 0.3). Pewarnaan Gram: Diplokokus Gram Negatif.", 380000),
                ExamItem("E3", "CT Scan Kepala Tanpa Kontras Cito", ExamCategory.IMAGING, "Tampak efasimen sulkus dan edema serebri difus, tidak ada massa atau efek desak ruang (Aman untuk Pungsi Lumbal).", 650000),
                ExamItem("E4", "Darah Rutin, Procalcitonin & Kultur Darah Cito", ExamCategory.LAB, "Leukosit 22.400/uL (Shift to the left), Procalcitonin 18.5 ng/mL (Sangat Tinggi infeksi bakteri berat).", 320000)
            ),
            optimalExamNames = listOf("Pemeriksaan Rangsang Meningeal & Neurologis", "CT Scan Kepala Tanpa Kontras Cito", "Pungsi Lumbal (Lumbar Puncture) & Analisis LCR Cito", "Darah Rutin, Procalcitonin & Kultur Darah Cito"),
            optimalCostEstimate = 1350000,
            recommendedTreatment = "1. ANTIBIOTIK EMPIRIS CITO (< 1 jam!): Seftriaxon 2 gram IV tiap 12 jam (Total 4g/hari) + Vankomisin 15-20 mg/kgBB IV tiap 8-12 jam. 2. KORTIKOSTEROID PREVENSI SEKUELAE: Deksametason 10 mg IV berikan 15-20 menit SEBELUM atau bersamaan antibiotik dosis pertama! 3. MANAJEMEN EDEMA SEREBRI: Head-Up 30°, Infus NaCl 0.9% / Manitol 20%. 4. ISOLASI DROPLET.",
            kemenkesGuidelines = "PPK Neurologi Kemenkes RI: Meningitis Bakterialis adalah kedaruratan infeksi SSP. Pemberian Antibiotik Dosis Tinggi IV (Seftriaxon + Vankomisin) HARUS dimulai < 1 jam pertama, didampingi Deksametason IV untuk mencegah komplikasi ketulian dan kerusakan otak permanen.",
            isEmergencyCase = true
        ),

        // 25. Pulmonologi: Tension Pneumothorax
        ClinicalCase(
            id = "PULMO-004",
            organSystem = "Pulmonologi",
            title = "Tension Pneumothorax Traumatik Dextra dengan Syok Obstruktif & Trachea Shifted",
            patientAge = 24,
            patientGender = "Laki-laki",
            patientOccupation = "Kurir Motor",
            generalAppearance = "Gagal napas berat, sianosis, hipoksia berat, pergerakan dada kanan tertinggal masif, trakea terdorong ke kiri, JVP meningkat, hipersonor kanan",
            chiefComplaint = "Sesak napas mendadak sangat hebat pasca kecelakaan sepeda motor benturan dada kanan 30 menit lalu, terasa dada kanan mau meledak.",
            td = "75/45 mmHg (Syok Obstruktif)",
            nadi = 142,
            rr = 38,
            suhu = 36.5,
            spO2 = 78,
            trueDiagnosis = "Tension Pneumothorax Traumatik Hemithorax Dextra & Syok Obstruktif",
            differentialDiagnoses = listOf("Hematothorax Masif Dextra", "Tamponade Jantung", "Flail Chest dengan Kontusio Paru", "Pneumothorax Simpleks"),
            patientPersonaInstruction = "Teman Pasien & Pasien: Dok tolong! Teman saya tadi tabrakan dada kanannya menghantam stang motor! Langsung sesak megap-megap bibirnya biru, lehernya bengkak, napasnya serasa mau putus!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Thorax & Trakea (Cito klinis!)", ExamCategory.PEMFIS, "Inspeksi: Hemithorax Dextra cembung tertinggal masif. Palpasi: Trakea terdorong signifikan ke Sinistra, emfisema subkutan (+). Perkusi: HIPERSONOR masif hemithorax dextra. Auskultasi: Suara napas dextra HILANG TOTAL.", 0),
                ExamItem("E2", "Needle Thoracocentesis / Dekompresi Jarum Cito", ExamCategory.PEMFIS, "Inseri IV cath 14G di ICS V Linea Axillaris Media / ICS II Linea Midclavicularis Dextra -> Keluar hembusan udara bertekanan tinggi (Hissing sound!) dan TD langsung melonjak naik.", 150000),
                ExamItem("E3", "Rontgen Thorax AP Bedside Pasca Dekompresi", ExamCategory.IMAGING, "Tampak avaskular zone hemithorax dextra luas dengan kolaps paru kanan ke hilus, pendorongan mediastinum ke sinistra.", 180000),
                ExamItem("E4", "Pemasangan Chest Tube / Water Seal Drainage (WSD)", ExamCategory.PEMFIS, "Pemasangan WSD Dextra ICS V Linea Axillaris Media -> Undulasi (+), bubble (+).", 650000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Thorax & Trakea (Cito klinis!)", "Needle Thoracocentesis / Dekompresi Jarum Cito", "Pemasangan Chest Tube / Water Seal Drainage (WSD)", "Rontgen Thorax AP Bedside Pasca Dekompresi"),
            optimalCostEstimate = 980000,
            recommendedTreatment = "1. DEKOMPRESI JARUM CITO (NEEDLE THORACOCENTESIS) IMMEDIATELY! JANGAN TUNDA TINDAKAN UNTUK FOTO RONTGEN! Tusuk IV Cath 14G di ICS V Linea Axillaris Media Dextra. 2. PEMASANGAN WSD (CHEST TUBE) CITO pasca dekompresi jarum. 3. OKSIGENASI NRM 15 Lpm. 4. RESUSITASI CAIRAN RL.",
            kemenkesGuidelines = "ATLS / PPK Pulmonologi Kemenkes RI: Tension Pneumothorax adalah Diagnosis KLINIS murni dan Kedaruratan Bedah Paru. Dilarang keras menunggu foto rontgen! Dekompresi Jarum Cito (Needle Decompression) wajib dilakukan seketika sebelum dilanjutkan pemasangan Chest Tube WSD.",
            isEmergencyCase = true
        ),

        // 26. Gastroenterologi: Peritonitis Perforasi Gaster
        ClinicalCase(
            id = "GASTRO-003",
            organSystem = "Gastroenterologi & Hepatologi",
            title = "Peritonitis Akut Generalisata ec Perforasi Ulkus Gaster / Peptikum (Abdomen Papan)",
            patientAge = 60,
            patientGender = "Laki-laki",
            patientOccupation = "Petani",
            generalAppearance = "Tampak sakit sangat berat, posisi fleksi lutut melipat (membungkuk), defans muskular / rigiditas dinding perut (Abdomen Papan), nyeri tekan & lepas seluruh perut",
            chiefComplaint = "Nyeri perut mendadak sangat tajam seperti ditusuk pisau di seluruh lapangan perut sejak 6 jam lalu pasca minum obat nyeri sendi warung.",
            td = "90/60 mmHg",
            nadi = 124,
            rr = 28,
            suhu = 38.6,
            spO2 = 94,
            trueDiagnosis = "Peritonitis Akut Generalisata ec Perforasi Ulkus Gaster / Peptikum ec NSAID Induced",
            differentialDiagnoses = listOf("Apendisitis Perforasi", "Pankreatitis Akut Necrotizing", "Kolesistitis Perforasi", "Iskemia Mesenterika Akut"),
            patientPersonaInstruction = "Pasien & Anak: Dok tolong perut bapak saya mendadak sakit sekali seperti ditusuk-tusuk pisau dari tadi sore! Perutnya kaku keras sekali kaya papan, tidak bisa disentuh sama sekali! Bapak sering minum obat jamu pegal linu.",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Abdomen (Status Lokalis)", ExamCategory.PEMFIS, "Inspeksi: Perut cembung, pernapasan abdominal tertinggal. Palpasi: Defans muskular (+), Abdomen kaku keras seperti papan (Board-like rigidity). Nyeri tekan & rebound tenderness di seluruh kuadran. Perkusi: Pekak hati menghilang (Pneumoperitoneum). Bising usus menghilang.", 0),
                ExamItem("E2", "Rontgen Abdomen 3 Posisi / Chest AP Tegak", ExamCategory.IMAGING, "Tampak gambaran Udara Bebas Subdiafragma Bilateral (Free Air Subdiaphragmatic / Crescent Sign khas Pneumoperitoneum!).", 220000),
                ExamItem("E3", "Darah Rutin & Electrolyte Cito", ExamCategory.LAB, "Leukosit 21.800/uL (Leukositosis Berat Shift to the left), Hb 12.4, Ht 42%, Na 132, K 3.6.", 180000),
                ExamItem("E4", "USG Abdomen Bedside", ExamCategory.IMAGING, "Tampak cairan bebas keruh masif di cavum Morison dan pelvis dengan peningkatan ekogenisitas peritoneum.", 320000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Abdomen (Status Lokalis)", "Rontgen Abdomen 3 Posisi / Chest AP Tegak", "Darah Rutin & Electrolyte Cito"),
            optimalCostEstimate = 400000,
            recommendedTreatment = "1. RESUSITASI CAIRAN RL/NaCl 0.9% CITO (2 IV Line G16) + Pasang NGT Deformasi Klem & Kateter Urin. 2. ANTIBIOTIK SPEKTRUM LUAS CITO: Seftriaxon 2g IV + Metronidazol 500mg IV drip. 3. ANALGETIK PARENTERAL: Ketorolak 30mg IV / Tramadol. 4. CITO LAPAROTOMI EKSPLORASI BEDAH UMUM.",
            kemenkesGuidelines = "PPK Bedah Digestif Kemenkes RI: Peritonitis Akut ec Perforasi Organ Berongga ditandai Abdomen Papan dan Free Air Subdiafragma pada Foto Polos Tegak. Penanganan definitif adalah Cito Laparotomi Eksplorasi pasca resusitasi cairan masif dan antibiotik spektrum luas.",
            isEmergencyCase = true
        ),

        // 27. Endokrinologi: Hipoglikemia Berat
        ClinicalCase(
            id = "ENDO-003",
            organSystem = "Endokrinologi & Metabolik",
            title = "Hipoglikemia Berat Cito (GDS 28 mg/dL) ec Overdosis Sulfonilurea / Insulin",
            patientAge = 62,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga",
            generalAppearance = "Penurunan kesadaran koma / stupor (GCS E2V1M4 = 7), diaphoresis masif (keringat dingin basah kuyup), tremor, kejang fokal, refleks babinski (+/+)",
            chiefComplaint = "Pasien ditemukan keluarga tidak sadarkan diri di kamar tidur, badan dingin basah kuyup berulang kali kejang ringkas.",
            td = "110/70 mmHg",
            nadi = 112,
            rr = 22,
            suhu = 35.8,
            spO2 = 97,
            trueDiagnosis = "Hipoglikemia Berat Cito (GDS 28 mg/dL) ec Induksi Obat Antidiabetes Oral (Glibenklamid/Glimepirid)",
            differentialDiagnoses = listOf("Stroke Iskemik Akut / TIA", "Ketoasidosis Diabetikum", "Status Epileptikus First Episode", "Ensefalopati Uremikum"),
            patientPersonaInstruction = "Keluarga Pasien: Dok! Ibu saya penderita kencing manis tadi pagi minum obat gula dua kali karena lupa, terus siang ini kaget tidak bangun-bangun! Badannya dingin basah kuyup keringatan keringat dingin terus tangannya kaku kejang!",
            availableExams = listOf(
                ExamItem("E1", "Gula Darah Sewaktu (GDS) Cito POCT", ExamCategory.LAB, "GDS: 28 mg/dL (CRITICAL LOW! < 40 mg/dL Hipoglikemia Berat!).", 35000),
                ExamItem("E2", "Elektrolit & Fungsi Ginjal Cito", ExamCategory.LAB, "Natrium 138 mEq/L, Kalium 4.0 mEq/L, Ureum 38 mg/dL, Kreatinin 1.1 mg/dL.", 195000),
                ExamItem("E3", "EKG 12-Lead Bedside", ExamCategory.PEMFIS, "Sinus Takikardia 112 x/m, tidak ada tanda iskemia miokard akut.", 120000)
            ),
            optimalExamNames = listOf("Gula Darah Sewaktu (GDS) Cito POCT", "Elektrolit & Fungsi Ginjal Cito", "EKG 12-Lead Bedside"),
            optimalCostEstimate = 350000,
            recommendedTreatment = "1. INJEKSI DEXTROSE 40% (D40) IV CITO: Berikan 2-4 vial (50-100 mL D40) IV bolus cepat! 2. EVALUASI GDS 15 MENIT PASCA D40: Jika GDS masih < 100 mg/dL, ulangi bolus D40 2 vial. 3. INFUS MAINTAIN DEXTROSE 10% (D10) drip 100-125 mL/jam. 4. STOP OBAT HIPOGLIKEMIK ORAL / INSULIN. 5. EVALUASI KESADARAN (Rule of 15).",
            kemenkesGuidelines = "PERKENI / PPK Endokrinologi Kemenkes RI: Hipoglikemia Berat (GDS < 40 mg/dL dengan gangguan kesadaran) adalah kedaruratan neuro-metabolik. Tatalaksana Cito: Injeksi Dextrose 40% IV bolus 2-4 ampul diikuti infus D10% maintenance dan re-evaluasi GDS tiap 15 menit.",
            isEmergencyCase = true
        ),

        // 28. Obgyn: Kehamilan Ektopik Terganggu (KET)
        ClinicalCase(
            id = "OBGYN-002",
            organSystem = "Obstetri & Ginekologi (Obgyn)",
            title = "Kehamilan Ektopik Terganggu (KET) Tuba Dextra dengan Syok Hemoragik Akut",
            patientAge = 28,
            patientGender = "Perempuan",
            patientOccupation = "Karyawan Swasta",
            generalAppearance = "Tampak pucat anemis berat, akral dingin basah, syok hemoragik, nyeri tekan abdomen bawah hebat, defans muskular (+), Cavum Douglas menonjol nyeri",
            chiefComplaint = "Nyeri perut bawah mendadak sangat hebat seperti disayat pisau, terlambat haid 2 bulan, disertai bercak perdarahan dari jalan lahir dan pingsan.",
            td = "75/45 mmHg (Syok Hemoragik)",
            nadi = 135,
            rr = 28,
            suhu = 36.2,
            spO2 = 95,
            trueDiagnosis = "Kehamilan Ektopik Terganggu (KET) Ruptur Tuba Dextra & Syok Hemoragik",
            differentialDiagnoses = listOf("Kista Ovarium Terpuntir (Torsi Kista)", "Apendisitis Akut Perforasi", "Abortus Inkomplit dengan Syok", "Pelvic Inflammatory Disease (PID) Ruptur Abses"),
            patientPersonaInstruction = "Suami Pasien: Dok tolong istri saya! Istri saya belum haid 2 bulan, tadi sore mendadak teriak sakit perut bawah sebelah kanan hebat banget terus langsung pingsan pucat dingin! Ada bercak darah juga dari kemaluan!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Ginekologi & Kuldocentesis", ExamCategory.PEMFIS, "Inspeksi: Pucat konjungtiva (+/+). Vaginal Toucher (VT): Porsio teraba lunak, nyeri goyang porsio (+ / Cervical Motion Tenderness POSITIF!), Cavum Douglas menonjol & sangat nyeri. Kuldocentesis: Aspirasi cairan hitam tidak membeku 5 cc (Konfirmasi Perdarahan Intraabdominal!).", 0),
                ExamItem("E2", "Tes Kehamilan Urin (beta-hCG Quick Test)", ExamCategory.LAB, "Beta-hCG Urin: POSITIF (+).", 35000),
                ExamItem("E3", "USG Transvaginal / Abdomen Gynecologic Bedside", ExamCategory.IMAGING, "Uterus kosong (No Intrauterine Gestational Sac), tampak massa heterogen di adneksa dextra 4x3 cm & cairan bebas masif (hemoperitoneum) di cavum Douglas.", 350000),
                ExamItem("E4", "Darah Rutin & Golongan Darah Crossmatch Cito", ExamCategory.LAB, "Hb 5.8 g/dL (Anemia Berat ec Ruptur KET!), Ht 18%, Leukosit 12.800/uL, Golongan Darah A Rh(+). Persiapan Transfusi PRC 4 kolf!", 215000)
            ),
            optimalExamNames = listOf("Pemeriksaan Ginekologi & Kuldocentesis", "Tes Kehamilan Urin (beta-hCG Quick Test)", "USG Transvaginal / Abdomen Gynecologic Bedside", "Darah Rutin & Golongan Darah Crossmatch Cito"),
            optimalCostEstimate = 600000,
            recommendedTreatment = "1. RESUSITASI CAIRAN MASIF CITO: 2 Line IV 16G, infus RL/NaCl 0.9% cepat 1000-2000 mL + Persiapan Transfusi Darah PRC Cito! 2. CITO LAPAROTOMI EKSPLORASI / SALPINGEKTOMI DEXTRA oleh Sp.OG! 3. OKSIGENASI NRM 10 Lpm. 4. INJEKSI ANALGETIK PARENTERAL.",
            kemenkesGuidelines = "PNPK Obgyn Kemenkes RI: KET Ruptur adalah kedaruratan bedah ginekologi utama penyumbang kematian ibu. Trias klasik: amenorea, nyeri perut bawah, perdarahan pervaginam + Cervical Motion Tenderness & Hemoperitoneum. Penanganan: Resusitasi cairan/darah Cito + Cito Laparotomi Salpingektomi.",
            isEmergencyCase = true
        ),

        // 29. Reumatologi: SLE Flaring Nefritis Lupus
        ClinicalCase(
            id = "REUMA-001",
            organSystem = "Reumatologi & Muskuloskeletal",
            title = "Systemic Lupus Erythematosus (SLE) Flaring Berat dengan Nefritis Lupus & Malar Rash",
            patientAge = 24,
            patientGender = "Perempuan",
            patientOccupation = "Mahasiswi",
            generalAppearance = "Ruam kemerahan berbentuk kupu-kupu di pipi & hidung (Malar/Butterfly Rash), edema pretibial (+2), alopesia, ulkus mulut non-nyeri, febris",
            chiefComplaint = "Demam hilang timbul 1 bulan, ruam merah pipi makin mencolok jika kena sinar matahari, persendian tangan bengkak nyeri, dan kedua kaki membengkak.",
            td = "150/95 mmHg",
            nadi = 102,
            rr = 22,
            suhu = 38.2,
            spO2 = 97,
            trueDiagnosis = "Systemic Lupus Erythematosus (SLE) Flaring Berat dengan Nefritis Lupus Class III/IV & Hipertensi Sekunder",
            differentialDiagnoses = listOf("Dermatitis Seboroik", "Scleroderma / Systemic Sclerosis", "Artritis Reumatoid", "Glomerulonefritis Primer"),
            patientPersonaInstruction = "Pasien & Ibu: Dok, saya wanita 24 tahun demam sudah sebulan naik turun. Pipi saya merah kaya bentuk kupu-kupu kalau kena matahari makin merah perih. Sendi jari tangan bengkak kaku pagi hari, rambut rontok parah, sama kaki saya seminggu ini bengkak!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Dermatologis & Muskoloskeletal", ExamCategory.PEMFIS, "Malar rash (+), Alopesia difus (+), Ulkus palatum durum (+), Arthritis perifer simetris di PIP & MCP digiti bilat (+), Edema pitting pretibial (+/+).", 0),
                ExamItem("E2", "Autoantibodi Panel (ANA Test & Anti-dsDNA)", ExamCategory.LAB, "ANA Test (IF): POSITIF Titer 1:1280 (Speckled Pattern), Anti-dsDNA: POSITIF Titer 380 IU/mL (SANGAT TINGGI!). C3 & C4 Komplemen: Menurun signifikan.", 650000),
                ExamItem("E3", "Urinalisis Lengkap & Protein Urin 24 Jam", ExamCategory.LAB, "Proteinuria +3, Eritrosit 20-30/LPB (Dismorfik), Cast Eritrosit (+). Protein Urin 24 Jam: 2.8 gram/24 jam (Nefritis Lupus!).", 160000),
                ExamItem("E4", "Fungsi Ginjal & Darah Rutin (CBC)", ExamCategory.LAB, "Hb 9.1 g/dL (Anemia Penyakit Kronis), Leukosit 3.400/uL (Leukopenia), Trombosit 115.000/uL, Ureum 54 mg/dL, Kreatinin 1.8 mg/dL.", 220000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Dermatologis & Muskoloskeletal", "Autoantibodi Panel (ANA Test & Anti-dsDNA)", "Urinalisis Lengkap & Protein Urin 24 Jam", "Fungsi Ginjal & Darah Rutin (CBC)"),
            optimalCostEstimate = 1030000,
            recommendedTreatment = "1. METILPREDNISOLON PULSE THERAPY: Metilprednisolon 500-1000 mg IV drip dalam D5% selama 3 hari berturut-turut untuk kontrol flare nefritis lupus berat! 2. IMMUNOSUPRESAN: Mikofenolat Mofetil (MMF) 2x1000 mg / Siklofosfamid IV. 3. HIDROKSIKLOROKUIN (HCQ): 1x200 mg oral. 4. ANTIHIPERTENSI RENOPROTEKTIF: Candesartan 8mg / Captopril. 5. EDUKASI TABIR SURYA (Sunblock SPF 50).",
            kemenkesGuidelines = "PPK Reumatologi Kemenkes RI: SLE Flaring dengan keterlibatan organ vital (Nefritis Lupus) memerlukan Pulsed Corticosteroid IV dosis tinggi dan Immunosupresan induksi (MMF/Siklofosfamid) serta evaluasi fungsi ginjal ketat.",
            isEmergencyCase = false
        ),

        // 30. Trauma & Emergensi: Syok Anafilaksis
        ClinicalCase(
            id = "EMERG-001",
            organSystem = "Kegawatdaruratan & Trauma",
            title = "Syok Anafilaksis Cito ec Injeksi Antibiotik / Reaksi Obat Berat (Threatening Airway)",
            patientAge = 35,
            patientGender = "Perempuan",
            patientOccupation = "Ibu Rumah Tangga",
            generalAppearance = "Tampak gawat darurat berat, edema fasialis & labia/lidah (Angioedema), stridor inspiratorik, wheezing di kedua lapangan paru, urtikaria eritema generalisata, syok terkompensasi",
            chiefComplaint = "Mendadak sesak napas berat tercekik, gatal-gatal bentol merah seluruh tubuh, bibir dan lidah membengkak tebal 5 menit pasca injeksi obat.",
            td = "70/40 mmHg (Syok Distributif)",
            nadi = 138,
            rr = 34,
            suhu = 36.4,
            spO2 = 82,
            trueDiagnosis = "Syok Anafilaksis Cito ec Reaksi Hipersensitivitas Tipe I (Injeksi Obat / Venom)",
            differentialDiagnoses = listOf("Asma Bronkial Eksaserbasi Berat", "Edema Laring ec Angioedema Herediter", "Syok Kardiogenik / Infark Miokard", "Aspirasi Benda Asing"),
            patientPersonaInstruction = "Keluarga & Pasien: Dok tolong! Tadi pas disuntik obat di ruang perawatan langsung teriak gatal seluruh badan merah-merah! Muka sama bibirnya membengkak tebal, napasnya berbunyi ngik-ngik tercekik terus pingsan!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Resusitasi ABCDE & Status Dermatologis", ExamCategory.PEMFIS, "Airway: Angioedema bibir & lidah, Stridor inspiratorik laring. Breathing: Wheezing ekspiratorik bilateral. Circulation: TD 70/40 mmHg, Nadi teraba cepat dan halus. Skin: Urtikaria konfluen eritematosa batas tegas seluruh tubuh.", 0),
                ExamItem("E2", "Analisis Gas Darah (AGD) Cito", ExamCategory.LAB, "pH 7.22, PaCO2 55 mmHg, PaO2 52 mmHg, HCO3 20 mEq/L, SaO2 82% (Gagal Napas Tipe I & II).", 320000),
                ExamItem("E3", "EKG 12-Lead Bedside", ExamCategory.PEMFIS, "Sinus Takikardia 138 x/menit, Iskemia miokard subendokard reaktif.", 120000)
            ),
            optimalExamNames = listOf("Pemeriksaan Resusitasi ABCDE & Status Dermatologis", "Analisis Gas Darah (AGD) Cito"),
            optimalCostEstimate = 320000,
            recommendedTreatment = "1. INJEKSI EPINEFRIN / ADRENALIN (1:1000) 0.3-0.5 mL IM CITO di Anterolateral Paha Mid-Thigh! Ulangi tiap 5-15 menit jika syok persisten! (FIRST LINE MUTLAK!). 2. POSISI TRENDELENBURG & OKSIGENASI NRM 15 Lpm / Persiapan Intubasi Cito jika edema laring progresif! 3. RESUSITASI CAIRAN KRISTALOID: Infus NaCl 0.9% / RL 1000-2000 mL guyur cepat. 4. SECOND LINE: Deksametason 10mg IV + Diphenhydramine 50mg IV + Salbutamol Nebu.",
            kemenkesGuidelines = "Resusitasi Anafilaksis / PPK Emergensi Kemenkes RI: Epinefrin IM paha anterolateral adalah Tatalaksana Utama Lini Pertama Tanpa Kontraindikasi Mutlak. Dilarang mendahulukan kortikosteroid/antihistamin sebelum Epinefrin IM diberikan!",
            isEmergencyCase = true
        ),

        // 31. Pediatri: Croup (Laringotrakeobronkitis Akut)
        ClinicalCase(
            id = "PED-002",
            organSystem = "Pediatri (Kesehatan Anak)",
            title = "Croup (Laringotrakeobronkitis Akut) dengan Stridor Inspiratorik & Barking Cough (Westley Score > 6)",
            patientAge = 2,
            patientGender = "Laki-laki",
            patientOccupation = "Anak Balita",
            generalAppearance = "Anak gelisah, batuk menggonggong khas (Barking Cough), stridor inspiratorik terdengar jelas saat istirahat, retraksi epigastrium & interkostal (+)",
            chiefComplaint = "Anak batuk keras berbunyi seperti menggonggong guk-guk sejak semalam, napas berbunyi melengking kasar (stridor) dan sesak napas.",
            td = "90/55 mmHg",
            nadi = 138,
            rr = 36,
            suhu = 38.3,
            spO2 = 91,
            trueDiagnosis = "Croup (Laringotrakeobronkitis Akut) Derajat Sedang-Berat ec Infeksi Parainfluenza Virus",
            differentialDiagnoses = listOf("Epiglottitis Akut (Supraglottitis)", "Benda Asing di Saluran Napas", "Abses Retrofaring", "Diphtheri Laring (Membranous Croup)"),
            patientPersonaInstruction = "Ibu Pasien: Dok tolong anak saya usia 2 tahun dari semalam batuknya aneh banget bunyinya keras kaya anjing menggonggong! Kalau narik napas ada bunyi melengking kencang terus dadanya ngos-ngosan cekung ke dalam!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Evaluasi Westley Croup Score", ExamCategory.PEMFIS, "Westley Score = 7 (Croup Sedang-Berat): Stridor saat istirahat (+2), Retraksi Sedang (+2), Udara masuk menurun (+1), Cyanosis saat gelisah (+1), Kesadaran gelisah (+1).", 0),
                ExamItem("E2", "Rontgen Leher / Servikal AP/Lateral", ExamCategory.IMAGING, "Tampak penyempitan subglotis khas berbentuk menara gereja (Steeple Sign / Pencil Point Sign POSITIF!).", 180000),
                ExamItem("E3", "Darah Rutin & Swab Nasofaring", ExamCategory.LAB, "Leukosit 9.200/uL (Limfositosis dominan viral), Hb 12.1 g/dL.", 150000)
            ),
            optimalExamNames = listOf("Pemeriksaan Evaluasi Westley Croup Score", "Rontgen Leher / Servikal AP/Lateral"),
            optimalCostEstimate = 180000,
            recommendedTreatment = "1. INJEKSI / ORAL DEKSAMETASON: Deksametason 0.6 mg/kgBB (Max 16mg) IM/IV/Oral Single Dose! 2. NEBULISASI EPINEFRIN RASEMIK / L-EPINEFRIN: Nebulisasi Epinefrin (1:1000) 2-5 mL dilarutkan NaCl 0.9% untuk vasokonstriksi mukosa glotis cito! 3. OKSIGENASI HUMIDIFIKASI (Moistened Oxygen) 2-4 Lpm. 4. HINDARI MEMBUAT ANAK MANIS/MENANGIS (Dapat memperberat obstruksi glotis!).",
            kemenkesGuidelines = "Pedoman IDAI / PPK Anak Kemenkes RI: Croup ditandai Trias: Batuk Menggonggong, Stridor Inspiratorik, dan Hoarseness. Pemberian Deksametason Single Dose 0.6 mg/kgBB + Nebulisasi Epinefrin adalah terapi baku emas untuk mencegah intubasi.",
            isEmergencyCase = true
        ),

        // 32. THT-KL: Epistaksis Posterior Masif
        ClinicalCase(
            id = "THT-002",
            organSystem = "THT-KL",
            title = "Epistaksis Posterior Masif ec Krisis Hipertensi (Pleksus Woodruff / A. Sphenopalatina)",
            patientAge = 62,
            patientGender = "Laki-laki",
            patientOccupation = "Pensiunan",
            generalAppearance = "Tampak cemas, darah segar mengalir deras membual dari kedua lubang hidung dan dinding posterior tenggorokan (post-nasal drip darah masif), mual meludah darah",
            chiefComplaint = "Pendarahan hidung sangat banyak mengalir deras tidak berhenti sejak 1 jam lalu, darah tertelan mengalir ke tenggorokan belakang.",
            td = "210/120 mmHg (Krisis Hipertensi)",
            nadi = 108,
            rr = 24,
            suhu = 36.7,
            spO2 = 96,
            trueDiagnosis = "Epistaksis Posterior Masif ec Krisis Hipertensi (Pendarahan Pleksus Woodruff / Arterial)",
            differentialDiagnoses = listOf("Epistaksis Anterior ec Pleksus Kiesselbach", "Karsinoma Nasofaring (KNF) dengan Perdarahan", "Angiofibroma Nasofaring Belia", "Gangguan Koagulasi / Trombositopenia"),
            patientPersonaInstruction = "Pasien & Anak: Dok tolong hidung bapak saya ngucur darah deras banget merah segar dari tadi tidak stop-stop! Darahnya juga ngalir meluncur ke tenggorokan belakang sampai bapak keciuman darah dan muntah darah tertelan! Darah tingginya lagi kumat.",
            availableExams = listOf(
                ExamItem("E1", "Rinoskopi Anterior & Posterior (Evaluasi Sumber Perdarahan)", ExamCategory.PEMFIS, "Rinoskopi Anterior: Pasca pembersihan bekuan darah, tidak ditemukan sumber perdarahan aktif di Kiesselbach plexus. Rinoskopi Posterior: Darah aktif mengalir deras dari kavum nasi posterior dinding posterior nasofaring.", 0),
                ExamItem("E2", "Pemasangan Tampon Posterior (Tampon Bellocq / Kateter Foley Balon)", ExamCategory.PEMFIS, "Pemasangan Kateter Foley No 14/16 melalui hidung ke nasofaring -> Kembangkan balon dengan 10-15 cc aquabidest -> Tarik ke anterior hingga mengunci nasofaring + pasang tampon anterior padat. Perdarahan BERHENTI sempurna.", 250000),
                ExamItem("E3", "Darah Rutin & Hemostasis (PT/APTT) Cito", ExamCategory.LAB, "Hb 10.2 g/dL, Trombosit 240.000/uL, PT 11.5 dtk, APTT 29.8 dtk.", 180000)
            ),
            optimalExamNames = listOf("Rinoskopi Anterior & Posterior (Evaluasi Sumber Perdarahan)", "Pemasangan Tampon Posterior (Tampon Bellocq / Kateter Foley Balon)", "Darah Rutin & Hemostasis (PT/APTT) Cito"),
            optimalCostEstimate = 430000,
            recommendedTreatment = "1. PEMASANGAN TAMPON POSTERIOR (TAMPON BELLOCQ / KATETER FOLEY BALON NASOFARING) CITO! 2. TURUNKAN TEKANAN DARAH CITO: Nicardipine IV drip / Captopril 25mg Sublingual. 3. ANTIBIOTIK PROFILAKSIS: Amoxicillin-Clavulanate / Seftriaxon IV (Prevensi Otitis Media / Sinusitis / Toxic Shock Syndrome ec Tampon). 4. HEMOSTATIK: Asam Traneksamat 500mg IV.",
            kemenkesGuidelines = "PPK THT-KL Kemenkes RI: Epistaksis Posterior tidak dapat dihentikan dengan penekanan cuping hidung sederhana. Wajib dipasang Tampon Posterior (Bellocq / Foley Balon) 2x24 jam disertai kontrol hipertensi sistemik ketat dan antibiotik pencegah infeksi sekunder.",
            isEmergencyCase = true
        ),

        // 33. Oftalmologi: Endoftalmitis Akut
        ClinicalCase(
            id = "MATA-002",
            organSystem = "Oftalmologi (Mata)",
            title = "Endoftalmitis Akut Supuratif OS Pasca Trauma Tembus Mata (Hipopion (+))",
            patientAge = 42,
            patientGender = "Laki-laki",
            patientOccupation = "Tukang Las",
            generalAppearance = "Meringis kesakitan memegang mata kiri, edematosa palpebra, injeksi siliar & konjungtiva berat, Hipopion (+) di COA OS, vtreous haze, visus Light Perception",
            chiefComplaint = "Mata kiri sangat nyeri menusuk, merah, bengkak, dan penglihatan hampir buta total (hanya bisa melihat cahaya) pasca terkena serpihan besi 2 hari lalu.",
            td = "130/80 mmHg",
            nadi = 90,
            rr = 20,
            suhu = 37.6,
            spO2 = 98,
            trueDiagnosis = "Endoftalmitis Akut Supuratif Oculi Sinistra (OS) Pasca Trauma Tembus Mata / Benda Asing Intraokular",
            differentialDiagnoses = listOf("Uveitis Akut Anterior Berat dengan Hipopion", "Ulkus Kornea Bakterial Perforasi", "Panoftalmitis", "Glaukoma Sekunder Trauma"),
            patientPersonaInstruction = "Pasien: Dok, mata kiri saya 2 hari lalu kena percikan serpihan besi pas ngelas. Sekarang matanya sakit sekali serasa mau copot, merah bengkak, bawah matanya ada lapisan nanah putih (hipopion), sama penglihatan mata kiri gelap cuma bisa lihat sinar lampu!",
            availableExams = listOf(
                ExamItem("E1", "Biomikroskopi Slit-Lamp & Pemeriksaan Visus OS", ExamCategory.PEMFIS, "Visus OS: Light Perception (LP +) / 1/~. Slit Lamp: Injeksi siliar masif, defek luka tembus kornea-sklera OS 2mm, COA dangkal dengan HIPOPION (endapan nanah) setinggi 2.5 mm, refleks fundus negatif (Vitreous Haze masif).", 0),
                ExamItem("E2", "USG Mata (Ophthalmic B-Scan Ultrasound) OS", ExamCategory.IMAGING, "Tampak opasitas membranosa & dbris intensitas tinggi difus di dalam korpus vitreum OS (Vitreitis berat khas Endoftalmitis!).", 350000),
                ExamItem("E3", "Kultur & Pewarnaan Gram Usap / Aspirat Vitreus", ExamCategory.LAB, "Pewarnaan Gram: Kokus Gram Positif berderet (Suspek Staphylococcus epidermidis / Streptococcus).", 180000)
            ),
            optimalExamNames = listOf("Biomikroskopi Slit-Lamp & Pemeriksaan Visus OS", "USG Mata (Ophthalmic B-Scan Ultrasound) OS", "Kultur & Pewarnaan Gram Usap / Aspirat Vitreus"),
            optimalCostEstimate = 530000,
            recommendedTreatment = "1. INJEKSI INTRAVITREAL ANTIBIOTIK CITO: Injeksi Intravitreal Vankomisin (1 mg/0.1 mL) + Ceftazidime (2.25 mg/0.1 mL) oleh Dokter Spesialis Mata! 2. ANTIBIOTIK SISTEMIK & TETES MATA FORTIFIED: Levofloxacin / Moxifloxacin tetes mata fortified tiap 1 jam + Ciprofloxacin 750mg BD oral / Ceftriaxone 2g IV. 3. KORTIKOSTEROID TOPIKAL/INTRAVITREAL pasca antibiotik. 4. KONSUL VITREORETINA CITO FOR VITRECTOMY.",
            kemenkesGuidelines = "PPK Oftalmologi Kemenkes RI: Endoftalmitis Akut adalah kedaruratan mata paling destruktif yang dapat menghancurkan bola mata dalam 24-48 jam. Terapi definitif Cito adalah Injeksi Antibotik Intravitreal + Vitrektomi Evakuasi Nanah Vitreus.",
            isEmergencyCase = true
        ),

        // 34. Dermatovenerologi: Lepra Reaksi ENL
        ClinicalCase(
            id = "DERMA-002",
            organSystem = "Dermatovenerologi (Kulit & Kelamin)",
            title = "Morbus Hansen (Lepra / Kusta) Tipe Multibasiler (MB) dengan Reaksi Erythema Nodosum Leprosum (ENL) Grade Berat",
            patientAge = 38,
            patientGender = "Laki-laki",
            patientOccupation = "Buruh Bangunan",
            generalAppearance = "Nodus-nodus eritematosa multiple nyeri tekan di kedua ekstremitas & muka, Facies Leonina (+), penebalan N. Ulnaris Dextra (+), anhidrosis & hipoestesi makula",
            chiefComplaint = "Muncul benjolan-benjolan merah terasa sangat nyeri di kedua tangan dan kaki sejak 3 hari, demam menggigil, nyeri sendi hebat, sedang rutin minum obat kulit dari puskesmas sejak 4 bulan lalu.",
            td = "120/80 mmHg",
            nadi = 104,
            rr = 20,
            suhu = 38.7,
            spO2 = 98,
            trueDiagnosis = "Morbus Hansen (Lepra / Kusta) Tipe Multibasiler (MB) dalam Reaksi Tipe II (Erythema Nodosum Leprosum / ENL) Derajat Berat",
            differentialDiagnoses = listOf("Reaksi Lepra Tipe I (Reaksi Reversal)", "Eritema Nodosum Idiopatik", "Vaskulitis Sistemik", "Eritema Multiforme"),
            patientPersonaInstruction = "Pasien: Dok, saya sedang berobat bercak kulit minum paket obat dari puskesmas berjalan 4 bulan. Tapi 3 hari ini badan saya demam tinggi, tangan sama kaki tumbuh benjolan merah keras terasa sakit banget kalau tersenggol! Sendi-sendi saya linu semua!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik Dermatologis & Saraf Perifer", ExamCategory.PEMFIS, "Kulit: Multiple nodus eritematosa diskret bersuhu hangat & sangat nyeri tekan di regio ekstensor ekstremitas atas & bawah. Makula hipopigmentasi anestesi (+). Saraf: Penebalan N. Ulnaris Dextra (+) nyeri tekan (+), Claw Hand digiti IV-V dextra minimal.", 0),
                ExamItem("E2", "Pemeriksaan BTA Kerokan Jaringan Kulit (Slit-Skin Smear)", ExamCategory.LAB, "Index Bakteri (IB): +4, Index Morphologi (IM): 15% (Bakteri Mycobacterium leprae utuh & terfragmentasi).", 120000),
                ExamItem("E3", "Darah Rutin & LED Cito", ExamCategory.LAB, "Leukosit 15.800/uL, LED 85 mm/jam (Reaksi Inflamasi Akut Berat), Hb 11.2 g/dL.", 95000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik Dermatologis & Saraf Perifer", "Pemeriksaan BTA Kerokan Jaringan Kulit (Slit-Skin Smear)", "Darah Rutin & LED Cito"),
            optimalCostEstimate = 215000,
            recommendedTreatment = "1. KORTIKOSTEROID SISTEMIK REAKSI ENL BERAT: Prednisolon oral dosis awal 40 mg/hari (0.5-1 mg/kgBB/hari) diturunkan bertahap (tapering off) tiap 2 minggu. 2. TERUSKAN OBAT MDT-MB KUSTA! (JANGAN STOP REJIMEN MDT CUSTA!). 3. ANALGETIK & ANTIINFLAMASI: Parasetamol 3x500mg / NSAID. 4. ISTIRAHAT KAN SARAF TERLESI (Imobilisasi ekstremitas).",
            kemenkesGuidelines = "Program P2 Kusta / PPK Kemenkes RI: Reaksi ENL (Tipe II) adalah Reaksi Hipersensitivitas Kompleks Imun pada Kusta MB. Pengobatan MDT Kusta HARUS TETAP DILANJUTKAN, ditambah Prednisolon Tapering Off dosis terukur untuk mencegah cacat permanent saraf (neuritis).",
            isEmergencyCase = false
        ),

        // 35. Psikiatri: Serangan Panik (Panic Attack)
        ClinicalCase(
            id = "PSIK-002",
            organSystem = "Psikiatri & Jiwa",
            title = "Gangguan Ansietas Panik (Panic Attack) dengan Sindrom Hiperventilasi & Agorafobia",
            patientAge = 28,
            patientGender = "Perempuan",
            patientOccupation = "Karyawan Bank",
            generalAppearance = "Tampak sangat ketakutan, napas cepat dangkal (Hiperventilasi), gemetaran hebat (tremor), parestesia / kesemutan di kedua telapak tangan & sekitar mulut (Carpopedal Spasm)",
            chiefComplaint = "Serangan ketakutan hebat mendadak seperti merasa mau pingsan atau tidak bisa bernapas, dada berdebar kencang, sesak napas rasa tercekik, dan tangan kaku kesemutan.",
            td = "145/90 mmHg",
            nadi = 126,
            rr = 34,
            suhu = 36.6,
            spO2 = 100,
            trueDiagnosis = "Gangguan Ansietas Panik (Panic Attack / Panic Disorder) dengan Sindrom Hiperventilasi Akut & Alkalosis Respiratorik Reaktif",
            differentialDiagnoses = listOf("Infark Miokard Akut / SKA", "Pneumothorax Simpleks", "Emboli Paru Akut", "Hipertiroidisme / Tirotoksikosis"),
            patientPersonaInstruction = "Pasien: Dok tolong saya! Dada saya mendadak berdebar kencang sekali serasa mau meledak, sesak napas tercekik tidak bisa hirup udara! Tangan dan bibir saya kesemutan kaku! Saya takut sekali Dok serasa mau pingsan!",
            availableExams = listOf(
                ExamItem("E1", "Pemeriksaan Fisik & Status Mental Ansietas", ExamCategory.PEMFIS, "Hiperventilasi (RR 34 x/m), Carpopedal Spasm (+), Tremor halus kedua tangan. Status Mental: Ketakutan mendalam akan kematian/kehilangan kontrol (Fear of dying/going crazy), tidak ada waham atau halusinasi.", 0),
                ExamItem("E2", "EKG 12-Lead Bedside (Menyingkirkan SKA)", ExamCategory.PEMFIS, "Sinus Takikardia 126 x/menit, gelombang ST-T normal tanpa deviasi iskemia.", 120000),
                ExamItem("E3", "Analisis Gas Darah (AGD) Cito", ExamCategory.LAB, "pH 7.52 (Alkalosis Respiratorik ec Hiperventilasi!), PaCO2 24 mmHg (Hypocapnia), PaO2 108 mmHg, HCO3 22 mEq/L, SaO2 100%.", 320000),
                ExamItem("E4", "Enzim Jantung (Troponin I / T) & GDS Cito", ExamCategory.LAB, "Troponin I: < 0.01 ng/mL (NEGATIF), GDS: 105 mg/dL.", 250000)
            ),
            optimalExamNames = listOf("Pemeriksaan Fisik & Status Mental Ansietas", "EKG 12-Lead Bedside (Menyingkirkan SKA)", "Analisis Gas Darah (AGD) Cito", "Enzim Jantung (Troponin I / T) & GDS Cito"),
            optimalCostEstimate = 690000,
            recommendedTreatment = "1. REASSURANCE & BREATHING RETRAINING CITO: Tenangkan pasien secara empati, latih pernapasan lambat diafragma (Slow Paper Bag Breathing / Diaphragmatic Breathing) untuk koreksi alkalosis respiratorik! 2. ANSIOLITIK SHORT-ACTING CITO: Alprazolam 0.5 mg oral / Lorazepam 1-2 mg oral/IM. 3. TERAPI RENCANA JANGKA PANJANG: SSRI (Sertraline 50mg / Fluoxetine 20mg) + Cognitive Behavioral Therapy (CBT).",
            kemenkesGuidelines = "PPDGJ-III / PPK Psikiatri Kemenkes RI: Serangan Panik ditandai serangan ansietas berat mendadak puncaknya dalam 10 menit dengan gejala somatik hebat (dada berdebar, tercekik, takut mati). Penanganan Cito: Reassurance, Breathing retraining, dan Ansiolitik benzodiazepin dosis rendah.",
            isEmergencyCase = false
        )
    )
    val cases: List<ClinicalCase> by lazy {
        rawCases.map { MedicalVitalsValidator.validateAndCalibrateCase(it) }
    }

    fun createDynamicCaseFromCatalog(diag: String, isEmergency: Boolean = false): ClinicalCase {
        val existing = cases.find { it.trueDiagnosis.equals(diag, ignoreCase = true) || it.trueDiagnosis.lowercase().contains(diag.lowercase()) }
        if (existing != null) {
            val base = if (isEmergency) existing.copy(isEmergencyCase = true) else existing
            return MedicalVitalsValidator.validateAndCalibrateCase(base)
        }

        val lower = diag.lowercase()
        val organSystem = when {
            lower.contains("stemi") || lower.contains("nstemi") || lower.contains("angina") || lower.contains("jantung") || lower.contains("hipertensi") || lower.contains("af") || lower.contains("perikard") -> "Kardiologi"
            lower.contains("stroke") || lower.contains("kejang") || lower.contains("epilepsi") || lower.contains("meningitis") || lower.contains("headache") || lower.contains("vertigo") || lower.contains("palsy") || lower.contains("cts") -> "Neurologi"
            lower.contains("asma") || lower.contains("ppok") || lower.contains("pneumonia") || lower.contains("tb") || lower.contains("pneumothorax") || lower.contains("pleura") || lower.contains("pleurit") || lower.contains("plurit") || lower.contains("paru") || lower.contains("bronk") || lower.contains("efusi") || lower.contains("hemoptisis") || lower.contains("empiema") || lower.contains("atelektasis") -> "Pulmonologi"
            lower.contains("apendisitis") || lower.contains("gerd") || lower.contains("gastritis") || lower.contains("peritonitis") || lower.contains("kolesistitis") || lower.contains("tifoid") || lower.contains("hepatitis") || lower.contains("diare") || lower.contains("sirosis") || lower.contains("pankreatitis") || lower.contains("ulkus peptikum") || lower.contains("divertikulitis") -> "Gastroenterologi & Hepatologi"
            lower.contains("kad") || lower.contains("hiperglikemia") || lower.contains("tiroid") || lower.contains("hipoglikemia") || lower.contains("diabetes") || lower.contains("adrenal") || lower.contains("cushing") -> "Endokrinologi & Metabolik"
            lower.contains("herpes") || lower.contains("dermatitis") || lower.contains("sifilis") || lower.contains("gonore") || lower.contains("lepra") || lower.contains("sjs") || lower.contains("ten") || lower.contains("prurit") || lower.contains("prurigo") || lower.contains("gatal") || lower.contains("eksem") || lower.contains("psoriasis") || lower.contains("urtikaria") || lower.contains("tinea") || lower.contains("panu") || lower.contains("skabies") || lower.contains("pemfigus") || lower.contains("pemfigoid") || lower.contains("alopecia") || lower.contains("vitiligo") || lower.contains("akne") || lower.contains("acne") || lower.contains("melanoma") || lower.contains("erisipelas") || lower.contains("selulitis") || lower.contains("karbunkel") || lower.contains("furunkel") || lower.contains("impetigo") || lower.contains("lichen") -> "Dermatovenerologi (Kulit & Kelamin)"
            lower.contains("batu") || lower.contains("urolitiasis") || lower.contains("nefrolitiasis") || lower.contains("ureter") || lower.contains("isk") || lower.contains("pielonefritis") || lower.contains("pyelonefritis") || lower.contains("ginjal") || lower.contains("bph") || lower.contains("prostat") || lower.contains("nefrotik") || lower.contains("glomerulo") || lower.contains("torsi testis") || lower.contains("varikokel") || lower.contains("hidrokel") || lower.contains("fimosis") || lower.contains("parafimosis") || lower.contains("priapismus") || lower.contains("uretra") || lower.contains("sistitis") || lower.contains("vesika") -> "Nefro-Urologi"
            lower.contains("dbd") || lower.contains("dengue") || lower.contains("malaria") || lower.contains("leptospirosis") || lower.contains("covid") || lower.contains("tetanus") || lower.contains("rabies") || lower.contains("filariasis") || lower.contains("chikungunya") || lower.contains("toksoplasmosis") -> "Infeksi Tropis & Parasit"
            lower.contains("bayi") || lower.contains("anak") || lower.contains("balita") || lower.contains("campak") || lower.contains("morbili") || lower.contains("croup") || lower.contains("cacar") || lower.contains("neonat") -> "Pediatri (Kesehatan Anak)"
            lower.contains("preeklamsia") || lower.contains("eklamsia") || lower.contains("abortus") || lower.contains("kehamilan") || lower.contains("postpartum") || lower.contains("plasenta") || lower.contains("kista") || lower.contains("pid") || lower.contains("mioma") || lower.contains("endometriosis") || lower.contains("ketuban") -> "Obstetri & Ginekologi (Obgyn)"
            lower.contains("gaduh") || lower.contains("skizofrenia") || lower.contains("panik") || lower.contains("depresi") || lower.contains("bipolar") || lower.contains("delirium") || lower.contains("ansietas") -> "Psikiatri & Jiwa"
            lower.contains("abses peritonsil") || lower.contains("otitis") || lower.contains("sinusitis") || lower.contains("epistaksis") || lower.contains("tht") || lower.contains("faringitis") || lower.contains("tonsilitis") -> "THT-KL"
            lower.contains("ulkus kornea") || lower.contains("glaukoma") || lower.contains("konjungtivitis") || lower.contains("mata") || lower.contains("katarak") || lower.contains("keratitis") -> "Oftalmologi (Mata)"
            lower.contains("syok") || lower.contains("luka bakar") || lower.contains("trauma") || lower.contains("fraktur") || lower.contains("dislokasi") || lower.contains("cedera") -> "Kegawatdaruratan & Trauma"
            lower.contains("anemia") || lower.contains("leukemia") || lower.contains("limfoma") || lower.contains("itp") || lower.contains("hemofilia") || lower.contains("talasemia") || lower.contains("thalassemia") -> "Hematologi & Onkologi"
            lower.contains("artritis") || lower.contains("gout") || lower.contains("lupus") || lower.contains("sle") || lower.contains("osteoartritis") || lower.contains("reumatoid") || lower.contains("rheumatoid") -> "Reumatologi & Muskuloskeletal"
            else -> "Kedokteran Umum & Penyakit Dalam"
        }

        val age = if (organSystem.contains("Pediatri") || lower.contains("bayi") || lower.contains("anak")) (1..12).random() else (20..68).random()
        val gender = if (organSystem.contains("Obgyn") || lower.contains("preeklamsia") || lower.contains("abortus")) "Perempuan" else if ((1..2).random() == 1) "Laki-laki" else "Perempuan"

        val availableExams = mutableListOf<ExamItem>()
        availableExams.add(ExamItem("EX_P1", "Pemeriksaan Fisik Spesifik & Status Lokalis", ExamCategory.PEMFIS, "Ditemukan tanda patognomonis khas dan sesuai klinis $diag.", 0))
        availableExams.add(ExamItem("EX_P2", "Pemeriksaan Tanda Vital Lengkap", ExamCategory.PEMFIS, "Tekanan darah, frekuensi nadi, laju napas, dan suhu tubuh terukur akurat.", 0))
        availableExams.add(ExamItem("EX_L1", "Pemeriksaan Laboratorium Penunjang Utama", ExamCategory.LAB, "Hasil laboratorium konfirmasi positif konsisten dengan diagnosis $diag.", 120000))
        availableExams.add(ExamItem("EX_L2", "Darah Rutin & Kimia Darah Cito", ExamCategory.LAB, "Profil leukosit, hemoglobin, dan marka inflamasi terdeteksi abnormal.", 95000))
        availableExams.add(ExamItem("EX_I1", "Pemeriksaan Radiologi / Pencitraan Khas", ExamCategory.IMAGING, "Hasil radiologi/imaging menunjukkan konfirmasi lesi patologis $diag.", 250000))

        val guide = PpkGuideRepository.getOrCreateGuideline(diag, organSystem)

        val complaintText = if (guide.symptomsAndAnamnesis.isNotBlank()) {
            guide.symptomsAndAnamnesis.split(". ").firstOrNull() ?: "Pasien mengeluhkan rasa tidak nyaman pada tubuh sejak beberapa hari lalu."
        } else if (isEmergency) "Pasien datang dalam kondisi kegawatdaruratan akut dengan nyeri dan keluhan fisik hebat!" else "Pasien mengeluhkan gejala tidak nyaman pada tubuh sejak 1-3 hari lalu."

        val appearanceText = if (isEmergency) "Tampak sakit berat, gelisah, pucat, menahan sakit hebat, membutuhkan tindakan stabilisasi ABCDE Cito!" else "Tampak sakit sedang, lemas, gelisah, menahan rasa tidak nyaman pada tubuh."

        val vitals = MedicalVitalsValidator.getAccurateVitalsForDisease(
            diagnosis = diag,
            organSystem = organSystem,
            age = age,
            gender = gender,
            chiefComplaint = complaintText,
            generalAppearance = appearanceText,
            isEmergency = isEmergency
        )

        val occupation = when {
            age <= 4 -> "Anak Balita"
            age < 18 -> "Pelajar"
            age in 18..24 -> if (gender.contains("Perempuan", ignoreCase = true)) "Mahasiswi" else "Mahasiswa"
            age >= 60 -> if (gender.contains("Perempuan", ignoreCase = true)) "Ibu Rumah Tangga" else "Pensiunan"
            gender.contains("Perempuan", ignoreCase = true) -> "Ibu Rumah Tangga"
            else -> "Karyawan Swasta"
        }

        val uncalibratedCase = ClinicalCase(
            id = "CASES-DYN-${System.currentTimeMillis() % 10000}",
            organSystem = organSystem,
            title = if (isEmergency) "Gawat Darurat Cito: $organSystem" else "Kasus Klinis: $organSystem",
            patientAge = age,
            patientGender = gender,
            patientOccupation = occupation,
            generalAppearance = appearanceText,
            chiefComplaint = complaintText,
            td = vitals.td,
            nadi = vitals.nadi,
            rr = vitals.rr,
            suhu = vitals.suhu,
            spO2 = vitals.spO2,
            trueDiagnosis = diag,
            differentialDiagnoses = listOf("Kondisi Reaktif Lain", "Infeksi Sistemik Sekunder", "Kelainan Organik Relevan"),
            patientPersonaInstruction = "Pasien berusia $age tahun ($gender) mengeluhkan: ${guide.symptomsAndAnamnesis}. Pasien adalah orang awam tanpa pengetahuan medis. DILARANG KERAS MENYEBUTKAN DIAGNOSIS MEDIS PASTI.",
            availableExams = availableExams,
            optimalExamNames = listOf("Pemeriksaan Fisik Spesifik & Status Lokalis", "Pemeriksaan Laboratorium Penunjang Utama", "Pemeriksaan Radiologi / Pencitraan Khas"),
            optimalCostEstimate = 370000,
            recommendedTreatment = guide.treatmentAndMedication,
            kemenkesGuidelines = guide.kemenkesRef,
            isEmergencyCase = isEmergency,
            pathophysiology = guide.pathophysiology
        )
        return MedicalVitalsValidator.validateAndCalibrateCase(uncalibratedCase)
    }

    fun getCaseByOrgan(
        organ: String,
        isEmergencyOnly: Boolean = false,
        levelFilter: com.example.data.model.CaseLevel = com.example.data.model.CaseLevel.ALL
    ): ClinicalCase {
        val cleanOrgan = organ.lowercase().trim()
        val isRandom = cleanOrgan.contains("random") || cleanOrgan.contains("acak") || cleanOrgan.contains("semua") || cleanOrgan.isBlank()

        val levelMatches = { caseItem: ClinicalCase ->
            if (levelFilter == com.example.data.model.CaseLevel.ALL) true
            else CaseLevelEvaluator.evaluate(caseItem) == levelFilter
        }

        // Ambil diagnosis acak yang adil dan merata dari MedicalCatalog dengan anti-repetition history buffer
        val selectedDiagnosis = MedicalCatalog.pickTrulyRandomDiagnosis(
            organSystem = if (isRandom) "Acak" else organ,
            isEmergencyOnly = isEmergencyOnly
        )

        val builtInCandidates = cases.filter { caseItem ->
            val emergencyOk = if (isEmergencyOnly) caseItem.isEmergencyCase else true
            emergencyOk && levelMatches(caseItem)
        }

        // Cek apakah diagnosis terpilih memiliki BuiltIn case yang cocok
        val matchedBuiltIn = builtInCandidates.find {
            it.trueDiagnosis.equals(selectedDiagnosis, ignoreCase = true) ||
            it.title.contains(selectedDiagnosis, ignoreCase = true) ||
            selectedDiagnosis.contains(it.trueDiagnosis, ignoreCase = true)
        }

        val chosenCase = matchedBuiltIn ?: createDynamicCaseFromCatalog(selectedDiagnosis, isEmergency = isEmergencyOnly)
        return MedicalVitalsValidator.validateAndCalibrateCase(chosenCase)
    }
}
