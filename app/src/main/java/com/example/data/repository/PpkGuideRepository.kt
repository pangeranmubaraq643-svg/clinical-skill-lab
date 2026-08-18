package com.example.data.repository

data class PpkGuidelineItem(
    val id: String,
    val title: String,
    val organSystem: String,
    val skdiLevel: String, // e.g. "4A", "3B", "3A"
    val isEmergency: Boolean = false,
    val symptomsAndAnamnesis: String,
    val physicalAndLabExams: String,
    val treatmentAndMedication: String,
    val kemenkesRef: String,
    val redFlagsAndReferral: String,
    val pathophysiology: String = ""
) {
    val computedLevel: com.example.data.model.CaseLevel
        get() = CaseLevelEvaluator.evaluateDisease(title, isEmergency)
}

object PpkGuideRepository {

    val guidelines = listOf(
        PpkGuidelineItem(
            id = "PPK-CARDIO-01",
            title = "Infark Miokard Akut dengan ST Elevasi (STEMI)",
            organSystem = "Kardiologi",
            skdiLevel = "3B (Gawat Darurat)",
            isEmergency = true,
            symptomsAndAnamnesis = "Nyeri dada substernal khas (Levine sign +) terasa ditindih beban berat > 20 menit, menjalar ke lengan kiri, leher, atau rahang. Disertai diaphoresis (keringat dingin), mual, dan sesak napas.",
            physicalAndLabExams = "EKG 12-lead Cito: ST Elevasi >= 1mm di dua sandapan berdekatan, Q patologis. Laboratorium: Troponin I/T atau CK-MB meningkat. Auskultasi: S1 S2, evaluasi murmur / gallop S3.",
            treatmentAndMedication = "Loading Dual Antiplatelet (Aspirin 160-325 mg + Clopidogrel 300-600 mg kunyah), ISDN 5mg sublingual (jika TD > 90 mmHg & tanpa PDE5 inhibitor), Oksigen jika SpO2 < 90%, Morfin 2.5-5 mg IV jika nyeri dada berat persisten. Reperfusi Cito: Primary PCI < 120 mnt atau Fibrinolitik (Streptokinase) jika PCI tidak tersedia < 2 jam.",
            kemenkesRef = "PNPK Kardiologi Kemenkes RI / PERKI: Reperfusi dini adalah kunci penyelamatan miokardium. Fibrinolitik ideal diberikan < 30 menit door-to-needle di Faskes sekunder.",
            redFlagsAndReferral = "Red flags: Aritmia lethal (VF/VT), syok kardiogenik (TD < 90 mmHg, akral dingin), edema paru akut. Rujuk segera ke faskes dengan fasilitas Cath Lab."
        ),
        PpkGuidelineItem(
            id = "PPK-CARDIO-02",
            title = "Gagal Jantung Akut (Acute Decompensated Heart Failure)",
            organSystem = "Kardiologi",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Sesak napas mendadak memburuk saat berbaring (ortopnea), terbangun malam hari karena sesak (PND), pembengkakan kedua kaki (edema pretibial), cepat lelah.",
            physicalAndLabExams = "JVP meningkat (> 5+2 cmH2O), rhonchi basah halus di kedua basal paru, S3 gallop. Rontgen Thorax: Kardiomegali (CTR > 50%), infiltrat perihiler (bat-wing appearance). EKG: LVH / sinus takikardia.",
            treatmentAndMedication = "Oksigenasi via non-rebreathing mask / HFNC target SpO2 > 95%. Furosemid IV 20-40 mg bolus (dapat diulang/drip). ISDN IV drip atau Nitroglisrin IV jika TD sistolik > 110 mmHg. Posisikan setengah duduk (Fowler). Posisi restriksi cairan & garam.",
            kemenkesRef = "Pedoman Tata Laksana Gagal Jantung Kemenkes RI: Terapi utama fokus pada dekompresi kongesti dengan diuretik loop IV dan vasodilator jika tekanan darah adekuat.",
            redFlagsAndReferral = "Red flags: Hipotensi/Syok kardiogenik, distres napas berat requiring intubasi, gagal ginjal akut kardiorenal."
        ),
        PpkGuidelineItem(
            id = "PPK-NEURO-01",
            title = "Stroke Iskemik Akut (Serangan Otak Iskemik)",
            organSystem = "Neurologi",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Defisit neurologis fokal mendadak: kelemahan separuh tubuh (hemiparesis), bibir merot (paresis N. VII sentral), bicara pelo (disartria) atau tidak bisa bicara (afasia). Onset waktu sangat penting (< 4.5 jam).",
            physicalAndLabExams = "Pemeriksaan Neurologis: Skor NIHSS, tes GDS Cito (menyingkirkan hipoglikemia). CT Scan Kepala Tanpa Kontras Cito: Membedakan infark vs perdarahan (perdarahan negatif).",
            treatmentAndMedication = "Evaluasi Trombolisis IV rTPA (Alteplase 0.9 mg/kgBB) jika onset < 4.5 jam tanpa kontraindikasi. Jika trombolisis tidak diberikan: Berikan Antiplatelet Aspirin 160-325 mg oral. TD jangan diturunkan kecuali > 220/120 mmHg (atau > 185/110 jika trombolisis).",
            kemenkesRef = "PPK Neurologi Kemenkes RI / PERDOSSI: CT scan cito tanpa kontras wajib dilakukan sebelum pemberian antiplatelet/trombolitik.",
            redFlagsAndReferral = "Red flags: Penurunan kesadaran cepat, kejang berulang, tanda herniasi otak (pupil anisokor). Rujuk cito ke Stroke Unit."
        ),
        PpkGuidelineItem(
            id = "PPK-NEURO-02",
            title = "Status Epileptikus & Kejang Berulang",
            organSystem = "Neurologi",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Kejang berulang atau berlangsung kontinyu > 5 menit tanpa pemulihan kesadaran di antara episode kejang. Kejang dapat berupa tonik-klonik umum.",
            physicalAndLabExams = "Airway, Breathing, Circulation, GDS Cito (hipoglikemia?), elektrolit (hiponatremia?). Rekam EEG pasca stabilisasi.",
            treatmentAndMedication = "Menit 0-5: Amankan jalan napas + O2. Menit 5-10: Diazepam IV 10 mg (0.2 mg/kgBB) pelan 2-5 mg/menit atau Diazepam rektal 10 mg. Jika belum berhenti ulang 1x dalam 5 menit. Menit 10-20: Fenitoin IV loading 15-20 mg/kgBB dilarutkan NaCl 0.9%. Menit > 30: Propofol/Midazolam drip ICU.",
            kemenkesRef = "Pedoman Tata Laksana Epilepsi Kemenkes RI: Penghentian kejang < 30 menit sangat krusial mencegah kerusakan neuron permanen.",
            redFlagsAndReferral = "Red flags: Kejang refrakter > 30 menit, hipoksia persisten, asidosis laktat berat. Rujuk segera ke ICU."
        ),
        PpkGuidelineItem(
            id = "PPK-PULMO-01",
            title = "Asma Bronkial Eksaserbasi Akut Berat",
            organSystem = "Pulmonologi",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Sesak napas berat, mengi (ngik-ngik), posisi tripod (duduk membungkuk ke depan), hanya mampu berbicara per kata. Riwayat alergi/asma.",
            physicalAndLabExams = "Frekuensi napas > 30x/menit, Nadi > 120x/menit, Retraksi interkostal (+). Auskultasi: Wheezing ekspiratori & inspiratori nyaring. SpO2 < 92%. APE < 50% nilai prediksi.",
            treatmentAndMedication = "Oksigenasi via kanul/masker target SpO2 93-95%. Nebulisasi SABA (Salbutamol 2.5-5 mg) + SAMA (Ipratropium 0.5 mg) tiap 20 menit (3x dalam 1 jam). Kortikosteroid Sistemik IV (Metilprednisolon 40-60 mg IV). Magnesium Sulfat IV 2 gram drip jika respons buruk.",
            kemenkesRef = "PNPK Asma PDPI / Kemenkes RI: Terapi utama kombinasi nebulisasi bronkodilator kerja cepat dan steroid sistemik untuk mengatasi inflamasi saluran napas.",
            redFlagsAndReferral = "Red flags: Silent chest (suara napas hilang), sianosis, kesadaran menurun/exhaustion (PaCO2 tinggi). Rujuk ICU untuk intubasi ventiiator."
        ),
        PpkGuidelineItem(
            id = "PPK-PULMO-02",
            title = "Tuberkulosis (TB) Paru Kasus Baru",
            organSystem = "Pulmonologi",
            skdiLevel = "4A",
            isEmergency = false,
            symptomsAndAnamnesis = "Batuk berdahak >= 2 minggu, dahak dapat bercampur darah (hemoptisis), demam subfebris malam hari, keringat malam tanpa aktivitas, penurunan berat badan & nafsu makan.",
            physicalAndLabExams = "Pemeriksaan Dahak SPS / BTA mikroskopis atau Tes Cepat Molekuler (TCM / GeneXpert MTB/RIF). Rontgen Thorax PA: Infiltrat/kavitas pada apeks paru.",
            treatmentAndMedication = "Obat Anti Tuberkulosis (OAT) Kategori 1 FDC (Fixed Dose Combination): Fase Intensif 2 bulan (2HRZE: Isoniazid, Rifampisin, Pirazinamid, Etambutol) + Fase Lanjutan 4 bulan (4HR: Isoniazid, Rifampisin). Pengawasan oleh PMO (Pengawas Menelan Obat).",
            kemenkesRef = "Pedoman Nasional Pelayanan Kedokteran Tata Laksana TB Kemenkes RI: Diagnosis konfirmasi berbasis TCM/BTA. Penanganan wajib tuntas 6 bulan mencegah resistensi OAT (MDR-TB).",
            redFlagsAndReferral = "Red flags: Batuk darah masif (> 600 ml/24 jam), efek samping OAT berat (ikterik/hepatotoksik, SJS), resistensi rifampisin (MDR-TB)."
        ),
        PpkGuidelineItem(
            id = "PPK-GASTRO-01",
            title = "Appendisitis Akut Uncomplicated / Perforated",
            organSystem = "Gastroenterohepatologi",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Nyeri perut khas bermigrasi: berawal dari periumbilikal/epigastrium lalu berpindah dan menetap di perut kanan bawah (McBurney). Mual, muntah 1-2x, anoraksia (tidak nafsu makan), demam subfebris.",
            physicalAndLabExams = "Pemeriksaan Abdomen: Nyeri tekan & lepas McBurney (+), Rovsing sign (+), Psoas sign (+), Defans muskular (+). Skor Alvarado >= 7. Lab: Leukositosis (> 10.000/uL) dengan shift to the left. USG Abdomen: Diameter appendiks > 6mm.",
            treatmentAndMedication = "Puasakan pasien (NPO). Rehidrasi Cairan IV RL/NaCl 0.9%. Analgetik IV (Ketorolac / Parasetamol IV). Antibiotik Profilaksis IV (Sefotaksim 1g / Seftriaxon 1g IV + Metronidazol 500mg IV). Tatalaksana definitif: Apendektomi Cito.",
            kemenkesRef = "PPK Bedah Kemenkes RI: Apendisitis akut dengan indikasi peritonitis / Alvarado >= 7 merupakan indikasi tindakan bedah cito untuk mencegah peritonitis generalisata.",
            redFlagsAndReferral = "Red flags: Peritonitis generalisata (perut papan, nyeri di seluruh kuadran), syok septik (TD turun, takikardia)."
        ),
        PpkGuidelineItem(
            id = "PPK-GASTRO-02",
            title = "Gastroesophageal Reflux Disease (GERD)",
            organSystem = "Gastroenterohepatologi",
            skdiLevel = "4A",
            isEmergency = false,
            symptomsAndAnamnesis = "Rasa terbakar di dada menjalar ke leher (heartburn), rasa asam/pahit di mulut (regurgitasi), rasa mengganjal di tenggorokan (globus sensation), batuk kronis malam hari.",
            physicalAndLabExams = "Anamnesis terarah menggunakan Kuesioner GERD-Q (Skor >= 8 mengarah ke GERD). Pemeriksaan fisik abdomen umumnya dalam batas normal. Endoskopi SCBA diindikasikan jika ada alarm symptoms.",
            treatmentAndMedication = "Proton Pump Inhibitor (PPI) dosis standar: Omeprazole 20 mg 2x1 atau Lansoprazole 30 mg 2x1 sebelum makan selama 4-8 minggu. Edukasi Modifikasi Gaya Hidup: Hindari makan 3 jam sebelum tidur, kurangi kopi/cokelat/makanan berlemak, turunkan BB, tinggikan kepala saat tidur.",
            kemenkesRef = "PPK Gastroenterologi PGI / Kemenkes RI: Lini pertama terapi GERD adalah edukasi gaya hidup dan pemberian PPI dosis ganda selama 4-8 minggu.",
            redFlagsAndReferral = "Red flags (Alarm Symptoms): Disfagia (kesulitan menelan), odinafagia (nyeri menelan), hematemesis/melena, anemia, BB turun drastis tanpa sebab jelas."
        ),
        PpkGuidelineItem(
            id = "PPK-ENDO-01",
            title = "Ketoasidosis Diabetikum (KAD)",
            organSystem = "Endokrinologi & Metabolik",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Poliuria, polidipsia, lemas berat, mual, muntah, nyeri perut melilit. Napas cepat dan dalam (Kussmaul) dengan bau khas aseton/buah. Penurunan kesadaran.",
            physicalAndLabExams = "Tanda dehidrasi berat (turgor lambat, mata cekung). GDS Cito > 250 mg/dL. Analisis Gas Darah (AGD): pH < 7.30, HCO3 < 18 mEq/L, Anion gap tinggi (>12). Ketonemia / Ketonuria (+). Elektrolit: Evaluasi Kalium.",
            treatmentAndMedication = "1. Rehidrasi Cairan Agresif: NaCl 0.9% 1000 mL dalam jam I, lalu 500 mL/jam. 2. Insulin Kontinyu IV: Bolus Insulin Reguler 0.1 U/kgBB IV, lanjut drip 0.1 U/kgBB/jam. 3. Koreksi Kalium: Jjka K < 3.3 tunda insulin; jika K 3.3-5.3 berikan KCL 20-30 mEq/L cairan.",
            kemenkesRef = "Pedoman PERKENI / Kemenkes RI: Trias Hiperglikemia + Asidosis + Ketonuria. Penanganan utama rehidrasi cepat, insulin drip kontinyu, dan pemantauan kalium ketat.",
            redFlagsAndReferral = "Red flags: Edema serebral (sakit kepala hebat, penurunan GCS tiba-tiba pasca rehidrasi), hipokalemia berat, syok refrakter. Rujuk ICU."
        ),
        PpkGuidelineItem(
            id = "PPK-ENDO-02",
            title = "Diabetes Melitus Tipe 2 (DM Tipe 2)",
            organSystem = "Endokrinologi & Metabolik",
            skdiLevel = "4A",
            isEmergency = false,
            symptomsAndAnamnesis = "Gejala klasik 3P: Polifagia (sering lapar), Polidipsia (sering haus), Poliuria (sering kencing malam). Disertai penurunan berat badan tanpa sebab jelas, luka sulit sembuh, kesemutan di ujung jari.",
            physicalAndLabExams = "Pemeriksaan Glukosa: GDS >= 200 mg/dL dengan gejala klasik, atau GDP >= 126 mg/dL, atau GD2PP >= 200 mg/dL, atau HbA1c >= 6.5%. Profil lipid, fungsi ginjal (ureum/creatinin), urinalisis microalbuminuria.",
            treatmentAndMedication = "1. Edukasi Terapi Nutrisi Medis (TNM) & Latihan Fisik teratur 150 mnt/minggu. 2. Obat Anti Hiperglikemia Oral (OHO): Metformin 500 mg 2-3x1 bersama makan sebagai lini pertama. Kombinasi Sulfonilurea (Glibenklamid / Glimepirid) jika HbA1c belum tercapai.",
            kemenkesRef = "Konsensus PERKENI / Kemenkes RI: Pengelolaan DM Tipe 2 mencakup 4 pilar: Edukasi, Terapi Nutrisi, Kegiatan Jasmani, dan Intervensi Farmakologis.",
            redFlagsAndReferral = "Red flags: Komplikasi akut (KAD / HHS / Hipoglikemia berulang) atau komplikasi mikrovaskular berat (Ulkus Diabetikum derajat tinggi, Nefropati Diabetik stage akhir)."
        ),
        PpkGuidelineItem(
            id = "PPK-NEPHRO-01",
            title = "Urolitiasis / Kolik Ureter (Batu Saluran Kemih)",
            organSystem = "Nefro-Urologi",
            skdiLevel = "3A",
            isEmergency = true,
            symptomsAndAnamnesis = "Nyeri kolik hebat di pinggang (flank) hilang timbul mendadak, menjalar ke lipat paha / sela paha / organ genitalia luar. Urine keruh atau berwarna kemerahan (hematuria). Mual dan muntah.",
            physicalAndLabExams = "Nyeri ketok sudut kostovertebra (CVA) (+). Urinalisis: Hematuria mikroskopik/makroskopik, kristaluria. USG Urologi: Hidronefrosis / hidroureter. CT Urografi Non-Kontras (NCCT): Standar emas konfirmasi lokasi dan ukuran batu.",
            treatmentAndMedication = "Antinyeri Akut: NSAID IV (Ketorolac 30mg IV) atau Metamizole 1g IV sebagai lini pertama spasme ureter. Antispasmodik (Hyoscine N-butylbromide 20mg IV). Medical Expulsive Therapy (MET): Alpha-blocker Tamsulosin 0.4mg 1x1 oral untuk bantu pasase batu < 10mm.",
            kemenkesRef = "Pedoman Praktik Klinis Urologi IAUI / Kemenkes RI: NSAID parenteral merupakan pilihan utama pereda nyeri kolik ureter karena menurunkan tonus otot polos ureter.",
            redFlagsAndReferral = "Red flags: Anuria / oliguria, batu terinfeksi dengan urosepsis (demam tinggi menggigil), batu bilateral, atau batu > 10mm (butuh URS / ESWL)."
        ),
        PpkGuidelineItem(
            id = "PPK-NEPHRO-02",
            title = "Infeksi Saluran Kemih (ISK) Sistitis Akut",
            organSystem = "Nefro-Urologi",
            skdiLevel = "4A",
            isEmergency = false,
            symptomsAndAnamnesis = "Nyeri / rasa terbakar saat kencing (disuria), sering kencing dalam jumlah sedikit (frekuensi), desakan kencing tidak dapat ditahan (urgensi), rasa tidak tuntas kencing, nyeri perut bawah (suprapubik).",
            physicalAndLabExams = "Nyeri tekan suprapubik (+), nyeri ketok CVA (-). Urinalisis: Leukosituria (>10/LPB), Nitrit (+), Leukosite Esterase (+), hematuria mikroskopik. Kultur urin jika ISK komplikata.",
            treatmentAndMedication = "Antibiotik Lini Pertama: Kotrimoksazol (Trimethoprim-Sulfamethoxazole) 2x960 mg selama 3-5 hari, atau Siprofloksasin 2x500 mg selama 3-5 hari, atau Sefadroksil 2x500 mg. Simptomatik: Analgetik urinary Phenazopyridine atau Parasetamol. Hidrasi air putih 2-3 Liter/hari.",
            kemenkesRef = "Pedoman ISK Kemenkes RI: ISK bawah uncomplicated pada wanita dewasa ditatalaksana dengan antibiotik oral empiris pendek 3-5 hari dan edukasi higienitas organ intim.",
            redFlagsAndReferral = "Red flags: Nyeri pinggang hebat dengan demam menggigil (Pyelonefritis Akut), kehamilan, retensi urin, urosepsis."
        ),
        PpkGuidelineItem(
            id = "PPK-TROPICAL-01",
            title = "Demam Berdarah Dengue (DBD / DHF)",
            organSystem = "Infeksi Tropis",
            skdiLevel = "4A",
            isEmergency = true,
            symptomsAndAnamnesis = "Demam tinggi mendadak terus menerus 2-7 hari, nyeri kepala, nyeri retro-orbital (belakang mata), nyeri otot dan sendi. Tanda perdarahan spontan (ptekie, mimisan, gusi berdarah). Mual, muntah.",
            physicalAndLabExams = "Tes Rumple Leede / Tourniquet (+). Darah Rutin Serial: Trombositopenia (< 100.000/uL), Hemokonsentrasi (peningkatan Hematokrit >= 20%). Serologi: Dengue NS1 Ag (+ pada hari 1-3) atau IgM/IgG Anti-Dengue.",
            treatmentAndMedication = "1. Suportif Cairan ISOTONIK IV (Ringer Laktat / NaCl 0.9%) rumatan + preservasi disesuaikan Hematokrit & Trombosit. 2. Antipiretik Parasetamol 500-1000 mg (HINDARI NSAID/Aspirin karena pemicu perdarahan!). 3. Pantau ketat TTV & Hematokrit/Trombosit tiap 6-12 jam.",
            kemenkesRef = "Pedoman Tata Laksana Dengue Kemenkes RI / WHO: Kunci keberhasilan adalah pemantauan fase kritis (hari ke 3-6) dan terapi cairan adekuat mencegah Dengue Shock Syndrome (DSS).",
            redFlagsAndReferral = "Red flags (Warning Signs): Nyeri perut hebat, muntah persisten, akumulasi cairan (efusi pleura/asites), perdarahan mukosa hebat, gelisah/letergi, hipotensi/sianosis (DSS)."
        ),
        PpkGuidelineItem(
            id = "PPK-TROPICAL-02",
            title = "Demam Tifoid / Typhoid Fever",
            organSystem = "Infeksi Tropis",
            skdiLevel = "4A",
            isEmergency = false,
            symptomsAndAnamnesis = "Demam naik bertahap seperti anak tangga (step-ladder fever) terutama sore-malam hari selama > 7 hari. Disertai gangguan saluran cerna (konstipasi atau diare), mual, lidah kotor (typhoid tongue), pusing.",
            physicalAndLabExams = "Febris, bradikardia relatif (nadi tidak naik sebanding kenaikan suhu), lidah kotor di tengah dengan tepi hiperemis (typhoid tongue), hepatosplenomegali. Lab: Tubex TF score >= 4 atau Widal O/H Titer >= 1/320. Darah rutin: Leukopenia.",
            treatmentAndMedication = "Lini Pertama Dewasa: Kloramfenikol 4x500 mg selama 10-14 hari, atau Siprofloksasin 2x500 mg selama 7 hari, atau Seftriaxon 1x2g IV selama 5 hari (pada kasus berat/rawat inap). Diet lunak rendah serat & Istirahat tirah baring.",
            kemenkesRef = "PPK Demam Tifoid Kemenkes RI: Diagnostik emas kultur Salmonella typhi darah/feses. Terapi antibiotik lini pertama adekuat mencegah komplikasi perforasi usus.",
            redFlagsAndReferral = "Red flags: Perforasi usus (nyeri perut hebat mendadak, defans muskular), perdarahan saluran cerna (melena), ensefalopati tifoid (penurunan kesadaran)."
        ),
        PpkGuidelineItem(
            id = "PPK-PEDIATRI-01",
            title = "Kejang Demam Sederhana (KDS) pada Balita",
            organSystem = "Pediatri (Kesehatan Anak)",
            skdiLevel = "4A",
            isEmergency = true,
            symptomsAndAnamnesis = "Kejang bangkitan umum (tonik-klonik) berlangsung singkat (< 15 menit), terjadi saat suhu tubuh meningkat (> 38 C) pada anak usia 6 bulan - 5 tahun. Tidak berulang dalam 24 jam.",
            physicalAndLabExams = "Evaluasi kesadaran pasca kejang (anak sadar penuh). Cari sumber infeksi pemicu demam (ISPA, Otitis Media, Diare). Tanda Rangsang Meningeal (Kaku kuduk, Brudzinski) NEGATIF. GDS Cito normal.",
            treatmentAndMedication = "Jika sedang kejang: Diazepam rektal 5 mg (BB < 12 kg) atau 10 mg (BB >= 12 kg). Pasca kejang: Antipiretik Parasetamol 10-15 mg/kgBB/kali oral tiap 4-6 jam atau Ibuprofen 10 mg/kgBB. Obati penyebab demam. Edukasi cemas orang tua.",
            kemenkesRef = "Pedoman Tata Laksana Kejang Demam IDAI / Kemenkes RI: Kejang demam sederhana tidak menyebabkan kerusakan otak/retardasi mental. Edukasi sedia Diazepam rektal di rumah.",
            redFlagsAndReferral = "Red flags: Kejang fokal/parsial, kejang > 15 menit, kejang berulang dalam 24 jam (Kejang Demam Kompleks), kaku kuduk (+), ubun-ubun menonjol (Meningitis/Ensefalitis)."
        ),
        PpkGuidelineItem(
            id = "PPK-PEDIATRI-02",
            title = "Diare Akut Dehidrasi Sedang-Berat pada Anak",
            organSystem = "Pediatri (Kesehatan Anak)",
            skdiLevel = "4A",
            isEmergency = true,
            symptomsAndAnamnesis = "Buang air besar cair > 3x sehari selama < 14 hari. Rewel, sangat haus, mata cekung, turgor kulit kembali sangat lambat (> 2 detik), tidak mau minum/lemas.",
            physicalAndLabExams = "Penentuan Derajat Dehidrasi WHO: Tanpa Dehidrasi, Dehidrasi Ringan-Sedang, Dehidrasi Berat. Pemeriksaan Feses Lengkap (bakteri/amoeba/rotavirus). Analisis Elektrolit jika dehidrasi berat.",
            treatmentAndMedication = "Lima Langkah Tuntaskan Diare (LINTAS DIARE) Kemenkes: 1. Oralit osmolaritas rendah. 2. Zink tablet 20 mg/hari (10 mg untuk bayi < 6 bln) selama 10 hari berturut-turut. 3. ASI & Makanan diteruskan. 4. Antibiotik HANYA atas indikasi (Disentri/Kolera). 5. Edukasi kebersihan. Jika Dehidrasi Berat: Rehidrasi Cairan IV RL / Asering 100 ml/kgBB.",
            kemenkesRef = "Program LINTAS DIARE Kemenkes RI / WHO: Pemberian suplementasi Zink 10 hari wajib diberikan untuk mempercepat penyembuhan & mencegah rekurensi diare 2-3 bulan ke depan.",
            redFlagsAndReferral = "Red flags: Kejang, muntah profus tidak bisa masuk cairan, letargi/koma, syok hipovolemik."
        ),
        PpkGuidelineItem(
            id = "PPK-OBGYN-01",
            title = "Preeklamsia Berat (PEB) & Impending Eclampsia",
            organSystem = "Obstetri & Ginekologi (Obgyn)",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Ibu hamil usia gestasi > 20 minggu dengan Tekanan Darah Sistolik >= 160 mmHg atau Diastolik >= 110 mmHg. Gejala Impending: Sakit kepala hebat, pandangan mata kabur, nyeri ulu hati (epigastrium).",
            physicalAndLabExams = "Pemeriksaan TD serial, Edema pretibial / anasarka, Refleks patella hiperrefleks. Proteinuria Dipstick >= +2 atau Protein urin kuantitatif >= 300 mg/24 jam. Trombosit, SGOT/SGPT, Fungsi Ginjal (rule out HELLP Syndrome).",
            treatmentAndMedication = "1. Anti-Kejang: MgSO4 (Magnesium Sulfat) 20% 4 gram IV bolus pelan 10-15 menit, lanjut MgSO4 40% 6 gram dalam RL 500 mL drip 1 g/jam. (Siapkan antidotum Kalsium Glukonas 10%!). 2. Antihipertensi: Nifedipin 10 mg oral (diulang tiap 30 mnt, maks 120 mg/hari) target TD < 150/100 mmHg. 3. Terminasi kehamilan pasca stabilisasi.",
            kemenkesRef = "PNPK POGI / Kemenkes RI: Pemberian MgSO4 IV adalah mandatory untuk pencegahan kejang eklamsia pada PEB di seluruh tingkat faskes.",
            redFlagsAndReferral = "Red flags: Kejang eklamsia, Sindrom HELLP (Hemolisis, Elevated Liver Enzymes, Low Platelet), Edema Paru, Solusio Plasenta. Rujuk Cito ke RS PONEK."
        ),
        PpkGuidelineItem(
            id = "PPK-OBGYN-02",
            title = "Perdarahan Postpartum (PPH) ec Atonia Uteri",
            organSystem = "Obstetri & Ginekologi (Obgyn)",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Perdarahan pervaginam masif (> 500 mL pasca persalinan pervaginam atau > 1000 mL pasca SC) segera setelah plasenta lahir. Ibu tampak pucat, lemas, pusing, syok.",
            physicalAndLabExams = "Palpasi Uterus: Uterus teraba lembek, tidak berkontraksi. Eksplorasi jalan lahir: Menyingkirkan laserasi serviks/vagina & sisa plasenta. Tanda syok (TD turun, nadi cepat halus). Hb Cito.",
            treatmentAndMedication = "1. Kompresi Bimanual Uterus (KBI / KBE) Cito. 2. Uterotonika: Oksitosin 10-20 IU IV drip dalam RL 500 mL + Ergometrin 0.2 mg IM + Misoprostol 800-1000 mcg rektal. 3. Resusitasi Cairan IV 2 jalur jarum besar + Transfusi Darah (PRC/WB) jika Hb < 8. 4. Pasang Kondom Kateter / Balon Tamponade Uterus.",
            kemenkesRef = "Pedoman Kegawatdaruratan Maternal POGI / Kemenkes RI: Penanganan PPH mengikuti algoritma HAEMOSTASIS. Atonia uteri adalah penyebab utama 70% PPH.",
            redFlagsAndReferral = "Red flags: Syok hemoragik berat, koagulopati (DIC), kontraksi uterus tidak membaik pasca KBI/uterotonika. Rujuk Cito untuk tindakan operatif (Laparotomi / Histerektomi)."
        ),
        PpkGuidelineItem(
            id = "PPK-DERMA-01",
            title = "Stevens-Johnson Syndrome (SJS) / TEN",
            organSystem = "Dermatovenerologi",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Erupsi mukokutan akut luas pasca konsumsi obat (misal: Alopurinol, Karbamazepin, Sulfonamida, NSAID). Lesi kulit berupa eritema, vesikel, bula kendor, kulit terkelupas (epidermolisis < 10% untuk SJS, > 30% untuk TEN). Krusta kehitaman pada bibir/mulut dan mata.",
            physicalAndLabExams = "Nikolsky Sign POSITIF (kulit terkelupas bila ditekan tergeser). Keterlibatan mukosa (mulut, mata, genitalia). Lab: Elektrolit, Fungsi Ginjal, Albumin, Darah Rutin.",
            treatmentAndMedication = "1. Stop segera seluruh obat pemicu! 2. Sediakan perawatan IV & Cairan mirip luka bakar. 3. Kortikosteroid Sistemik Dosis Tinggi IV (Deksametason 1-2 mg/kgBB/hari atau Metilprednisolon 1-2 mg/kgBB/hari). 4. Perawatan luka antiseptik steril & Salep mata antibiotik/steroid. 5. Rawat inap isolasi steril.",
            kemenkesRef = "PPK Kulit Kemenkes RI: SJS/TEN adalah reaksi simpang obat berat yang mengancam jiwa. Penghentian obat pemicu dan terapi suportif cairan + steroid IV cepat sangat krusial.",
            redFlagsAndReferral = "Red flags: Epidermolisis > 30% (TEN), gangguan napas/ARDS, sepsis, ulkus kornea mata berat. Rujuk Cito ke Burn Unit / ICU."
        ),
        PpkGuidelineItem(
            id = "PPK-THT-01",
            title = "Otitis Media Akut (OMA) Suppuratif",
            organSystem = "THT-KL",
            skdiLevel = "4A",
            isEmergency = false,
            symptomsAndAnamnesis = "Nyeri telinga hebat (otalgia), pendengaran berkurang, demam, anak gelisah / menarik-narik telinga. Jika gendang telinga pecah: Keluar cairan kuning/nanah dari telinga (otorea) dan nyeri mereda.",
            physicalAndLabExams = "Otoskopi: Membran timpani teraba bulging (menonjol) hiperemis merah membara (stadium supurasi) atau tampak perforasi dengan sekret purulen (stadium perforasi).",
            treatmentAndMedication = "1. Analgetik/Antipiretik: Parasetamol 3x500 mg atau Ibuprofen 3x400 mg. 2. Dekongestan Hidung: Tetes hidung Oxymetazoline 0.05% selama 5 hari untuk membuka tuba eustachius. 3. Antibiotik Oral (Stadium Supurasi): Amoksisilin 3x500 mg (atau 40-80 mg/kgBB/hari pada anak) selama 7-10 hari atau Amoksisilin-Garam Klavulanat.",
            kemenkesRef = "PPK THT Kemenkes RI: Tatalaksana OMA disesuaikan stadium otoskopi (oklusi, hiperemis, supurasi, perforasi, resolusi). Antibiotik adekuat mencegah komplikasi Mastoiditis.",
            redFlagsAndReferral = "Red flags: Pembengkakan dan nyeri di belakang telinga (Mastoiditis Akut), paresis wajah, vertigo berat, komplikasi intrakranial (abses otak)."
        ),
        PpkGuidelineItem(
            id = "PPK-EYE-01",
            title = "Glaukoma Akut Sudut Tertutup (Acute Angle Closure)",
            organSystem = "Oftalmologi (Mata)",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Nyeri mata hebat mendadak menjalar ke kepala/pelipis, mata merah, penglihatan buram mendadak, melihat lingkaran pelangi di sekitar lampu (halo), mual dan muntah.",
            physicalAndLabExams = "Injeksi siliar (+), Edema kornea (keruh), Pupil mid-dilatasi tetap (fixed mid-dilated pupil), Bilik mata depan dangkal. Palpasi Bola Mata: Teraba sangat keras seperti batu (Tekanan Intraokular / TIO sangat tinggi > 40-50 mmHg).",
            treatmentAndMedication = "1. Asetazolamid oral 500 mg lanjut 4x250 mg untuk menekan produksi humor akuos. 2. Tetes Mata Pilokarpin 2% 1 tetes tiap 15 menit dalam 1 jam pertama untuk miosis pupil. 3. Tetes Mata Beta-blocker (Timolol 0.5% 2x1 tetes). 4. Agen Osmotik IV (Mannitol 20% 1-2 g/kgBB IV drip cepat). 5. Rujuk Segera ke Dokter Spesialis Mata.",
            kemenkesRef = "PPK Mata Kemenkes RI / PERDAMI: Glaukoma akut adalah kegawatdaruratan mata pemicu kebutaan permanen cepat. Penurunan TIO cito wajib diberikan sebelum iridotomi laser.",
            redFlagsAndReferral = "Red flags: TIO tetap tinggi > 40 mmHg pasca obat osmotik, penurunan tajam penglihatan berat. Rujuk Cito Mata untuk Iridotomi Laser."
        ),
        PpkGuidelineItem(
            id = "PPK-EMERGENCY-01",
            title = "Syok Anafilaktik (Anaphylactic Shock)",
            organSystem = "Kegawatdaruratan & Trauma",
            skdiLevel = "3B",
            isEmergency = true,
            symptomsAndAnamnesis = "Reaksi alergi sistemik berat dan cepat pasca paparan obat/makanan/sengatan serangga. Gatal luas (urtikaria), bengkak bibir/kelopak mata (angioedema), sesak napas mengi, suara serak (edema laring), TD anjlok (hipotensi), pingsan.",
            physicalAndLabExams = "ABCDE Cito: TD < 90/60 mmHg, Nadi cepat halus (> 120x/mnt), Stridor / Wheezing, Urtikaria/Eritema difus.",
            treatmentAndMedication = "1. INJEKSI EPINEFRIN / ADRENALIN 1:1000 IM (Intramuskular) di paha anterolateral 0.3-0.5 mL (dewasa) atau 0.01 mL/kgBB (anak). Diberikan segera! Ulangi tiap 5-15 mnt jika belum stabil. 2. Posisikan kaki lebih tinggi (Trendelenburg). 3. Oksigenasi arus tinggi 10-15 Lpm. 4. Resusitasi Cairan IV RL/NaCl 0.9% 1-2 Liter cepat. 5. Injeksi Antihistamin (Diphenhydramine 50mg IV) + Deksametason 10mg IV.",
            kemenkesRef = "Pedoman Resusitasi Kemenkes RI / PAPDI: Epinefrin IM adalah obat pemicu penyelamat jiwa nomor 1 pada anafilaksis. Tatalaksana cepat di paha lateral tanpa menunda.",
            redFlagsAndReferral = "Red flags: Sumbatan jalan napas total ec edema laring (persiapkan Intubasi Cito / Krikotirotomi), syok refrakter requiring vasopresor drip."
        )
    )

    fun getSkdiLevelForDisease(title: String, isEmergency: Boolean = false): String {
        val lower = title.lowercase().trim()

        // 1. SKDI 3B (Gawat Darurat / Cito / Resusitasi / Stabilisasi Rujukan)
        if (isEmergency ||
            lower.contains("stemi") || lower.contains("nstemi") || lower.contains("infark miokard") ||
            lower.contains("stroke") || lower.contains("kejang") || lower.contains("epileptikus") ||
            lower.contains("syok") || lower.contains("anafilaksis") || lower.contains("kad") || lower.contains("ketoasidosis") ||
            lower.contains("eklamsia") || lower.contains("preeklamsia berat") || lower.contains("peritonitis") ||
            lower.contains("perforasi") || lower.contains("abses peritonsil") || lower.contains("sjs") || lower.contains("stevens") ||
            lower.contains("glaukoma akut") || lower.contains("krisis") || lower.contains("tension pneumothorax") ||
            lower.contains("gagal napas") || lower.contains("edema paru akut") || lower.contains("tenggelam") ||
            lower.contains("fraktur terbuka") || lower.contains("cedera kepala berat") || lower.contains("sol otak") ||
            lower.contains("tamponade") || lower.contains("ruptur") || lower.contains("dvt") || lower.contains("dengue shock") ||
            lower.contains("tetanus berat") || lower.contains("rabies") || lower.contains("ket") || lower.contains("atonia uteri") ||
            lower.contains("plasenta previa") || lower.contains("solusio plasenta") || lower.contains("luka bakar grade ii") ||
            lower.contains("luka bakar grade iii") || lower.contains("torsi testis") || lower.contains("dislokasi") ||
            lower.contains("gaduh gelisah") || lower.contains("delirium tremens") || lower.contains("benda asing saluran napas")
        ) {
            return "3B"
        }

        // 2. SKDI 3A (Non-Gawat Darurat Rujuk Spesialis)
        if (lower.contains("ppok") || lower.contains("karsinoma") || lower.contains("kanker") || lower.contains("tumor") ||
            lower.contains("sirosis") || lower.contains("hepatitis b") || lower.contains("hepatitis c") ||
            lower.contains("lupus") || lower.contains("sle") || lower.contains("reumatoid") || lower.contains("hnp") ||
            lower.contains("bph") || lower.contains("prostat") || lower.contains("skizofrenia") || lower.contains("bipolar") ||
            lower.contains("depresi berat") || lower.contains("ginjal kronis") || lower.contains("pgk") || lower.contains("ckd") ||
            lower.contains("nefrotik") || lower.contains("glaukoma") || lower.contains("katarak") || lower.contains("osteosarkoma") ||
            lower.contains("urolitiasis") || lower.contains("batu ureter") || lower.contains("batu ginjal") ||
            lower.contains("efusi pleura") || lower.contains("abses paru") || lower.contains("bronkiektasis") ||
            lower.contains("pankreatitis") || lower.contains("ibd") || lower.contains("colitis") || lower.contains("abses hati") ||
            lower.contains("graves") || lower.contains("hashimoto") || lower.contains("cushing") || lower.contains("hiperparatiroid") ||
            lower.contains("diabetes insipidus") || lower.contains("thalassemia") || lower.contains("gizi buruk") ||
            lower.contains("pid") || lower.contains("salpingitis") || lower.contains("hyperemesis") || lower.contains("mioma") ||
            lower.contains("gout") || lower.contains("osteoartritis") || lower.contains("spondilitis") || lower.contains("osteomielitis") ||
            lower.contains("ocd") || lower.contains("ptsd") || lower.contains("anoreksia") || lower.contains("bulimia") ||
            lower.contains("presbiakusis") || lower.contains("polip nasi") || lower.contains("ulkus kornea") ||
            lower.contains("pterigium grade iii") || lower.contains("psoriasis") || lower.contains("lepra")
        ) {
            return "3A"
        }

        // 3. SKDI 2 (Mendiagnosis & Merujuk)
        if (lower.contains("jantung bawaan") || lower.contains("pjb") || lower.contains("retinopati") ||
            lower.contains("ablasio") || lower.contains("leukemia") || lower.contains("limfoma") ||
            lower.contains("kongenital") || lower.contains("distrofi") || lower.contains("nasofaring") ||
            lower.contains("endoftalmitis") || lower.contains("hirschsprung") || lower.contains("atresia ani") ||
            lower.contains("coarctation") || lower.contains("brugada") || lower.contains("long qt") ||
            lower.contains("myasthenia") || lower.contains("guillain-barré") || lower.contains("gbs") ||
            lower.contains("parkinson") || lower.contains("dementia") || lower.contains("alzheimer")
        ) {
            return "2"
        }

        // 4. SKDI 1 (Mengenali & Menjelaskan)
        if (lower.contains("aneurisma aorta") || lower.contains("marfan") || lower.contains("huntington") ||
            lower.contains("als") || lower.contains("metabolik langka") || lower.contains("porfiria")
        ) {
            return "1"
        }

        // 5. Default SKDI 4A (Mandiri Faskes Primer)
        return "4A"
    }

    fun createDynamicGuideline(title: String, suggestedOrgan: String = ""): PpkGuidelineItem {
        val lower = title.lowercase()
        val isEmergency = lower.contains("akut") || lower.contains("krisis") || lower.contains("syok") ||
                lower.contains("ruptur") || lower.contains("gawat") || lower.contains("tension") ||
                lower.contains("status") || lower.contains("eklamsia") || lower.contains("perforasi") ||
                lower.contains("trauma") || lower.contains("torsi") || lower.contains("meningitis") ||
                lower.contains("stemi") || lower.contains("nstemi") || lower.contains("preeklamsia berat")

        val system = when {
            suggestedOrgan.isNotBlank() -> suggestedOrgan
            lower.contains("infark") || lower.contains("stemi") || lower.contains("jantung") || lower.contains("hipertensi") || lower.contains("angina") || lower.contains("aneurisma") || lower.contains("fibrilasi") || lower.contains("perikard") -> "Kardiologi & Vaskular"
            lower.contains("stroke") || lower.contains("kejang") || lower.contains("epilepsi") || lower.contains("meningitis") || lower.contains("migren") || lower.contains("vertigo") || lower.contains("palsy") || lower.contains("saraf") -> "Neurologi"
            lower.contains("asma") || lower.contains("ppok") || lower.contains("pneumonia") || lower.contains("tb ") || lower.contains("tuberkulosis") || lower.contains("paru") || lower.contains("bronk") || lower.contains("efusi") || lower.contains("pleurit") || lower.contains("plurit") || lower.contains("pneumothorax") || lower.contains("hemoptisis") || lower.contains("empiema") -> "Pulmonologi"
            lower.contains("sjs") || lower.contains("ten") || lower.contains("dermatit") || lower.contains("prurit") || lower.contains("plurit") || lower.contains("prurigo") || lower.contains("gatal") || lower.contains("eksem") || lower.contains("psoriasis") || lower.contains("urtikaria") || lower.contains("tinea") || lower.contains("panu") || lower.contains("skabies") || lower.contains("pemfigus") || lower.contains("alopecia") || lower.contains("vitiligo") || lower.contains("akne") || lower.contains("acne") || lower.contains("kusta") || lower.contains("lepra") || lower.contains("sifilis") || lower.contains("gonore") || lower.contains("herpes") || lower.contains("lichen") || lower.contains("impetigo") || lower.contains("furunkel") || lower.contains("erisipelas") || lower.contains("selulitis") -> "Dermatovenerologi (Kulit & Kelamin)"
            lower.contains("gastrit") || lower.contains("gerd") || lower.contains("diare") || lower.contains("apendisitis") || lower.contains("hepatit") || lower.contains("sirosis") || lower.contains("ileus") || lower.contains("kolesistitis") || lower.contains("tifoid") -> "Gastroenterologi & Hepatologi"
            lower.contains("diabet") || lower.contains("tiroid") || lower.contains("hipo") || lower.contains("hiper") || lower.contains("cushing") || lower.contains("metabolik") || lower.contains("ketoasidosis") -> "Endokrinologi & Metabolik"
            lower.contains("batu") || lower.contains("isk") || lower.contains("ginjal") || lower.contains("urolitiasis") || lower.contains("ureter") || lower.contains("prostat") || lower.contains("nefrotik") -> "Nefro-Urologi"
            lower.contains("dengue") || lower.contains("dbd") || lower.contains("malaria") || lower.contains("leptospira") || lower.contains("tetanus") || lower.contains("chikungunya") || lower.contains("covid") -> "Infeksi Tropis & Parasit"
            lower.contains("anak") || lower.contains("balita") || lower.contains("neonat") || lower.contains("morbili") || lower.contains("varicella") -> "Pediatri (Kesehatan Anak)"
            lower.contains("kehamilan") || lower.contains("preeklamsia") || lower.contains("abortus") || lower.contains("persalinan") || lower.contains("kista") || lower.contains("uterus") -> "Obstetri & Ginekologi (Obgyn)"
            lower.contains("fraktur") || lower.contains("luka bakar") || lower.contains("cedera") || lower.contains("dislokasi") || lower.contains("syok") -> "Kegawatdaruratan & Trauma"
            lower.contains("gout") || lower.contains("osteoartritis") || lower.contains("rheumatoid") || lower.contains("lupus") || lower.contains("spondilitis") -> "Reumatologi & Muskuloskeletal"
            lower.contains("skizofrenia") || lower.contains("depresi") || lower.contains("ansietas") || lower.contains("bipolar") || lower.contains("delirium") -> "Psikiatri & Jiwa"
            lower.contains("otitis") || lower.contains("sinusitis") || lower.contains("epistaksis") || lower.contains("tonsil") -> "THT-KL"
            lower.contains("glaukoma") || lower.contains("kornea") || lower.contains("katarak") || lower.contains("retina") || lower.contains("konjungtivitis") -> "Oftalmologi (Mata)"
            else -> "Dermatovenerologi (Kulit & Kelamin)"
        }

        val skdi = getSkdiLevelForDisease(title, isEmergency)

        val cleanAnamnesis = generateRealisticAnamnesisForDisease(title, system, isEmergency)

        val pathophysiologyText = "Patofisiologi $title melibatkan gangguan homeostasis seluler/organ target yang dipicu faktor etiopatogenesis (infeksi, inflamasi, iskemia, degenerasi, atau autoimun). Hal ini memicu pelepasan mediator inflamasi atau gangguan fungsi sirkulasi/metabolik yang menimbulkan manifestasi klinis khas pada sistem $system."

        return PpkGuidelineItem(
            id = "PPK-AUTO-${title.hashCode()}",
            title = title,
            organSystem = system,
            skdiLevel = skdi,
            isEmergency = isEmergency,
            symptomsAndAnamnesis = cleanAnamnesis,
            physicalAndLabExams = "Pemeriksaan Fisik: Evaluasi tanda vital (TD, Nadi, RR, Suhu, SpO2), pemeriksaan organ terkait secara sistematis (inspeksi, palpasi, perkusi, auskultasi). Laboratorium & Penunjang: Darah Rutin, pemeriksaan spesifik organ/marker diagnostik relevan, serta pencitraan/EKG/USG sesuai indikasi.",
            treatmentAndMedication = "Tata Laksana Utama: Tatalaksana non-farmakologis (edukasi, tirah baring/posisi) dan intervensi farmakologis lini pertama. Obat-obatan relevan disesuaikan dengan derajat keparahan dan Panduan Praktik Klinis Kemenkes RI.",
            kemenkesRef = "PPK / PNPK Kemenkes RI: Penanganan $title dilakukan secara komprehensif berstandar nasional sesuai faskes tingkat pertama / rujukan.",
            redFlagsAndReferral = "Red Flags: Tanda bahaya penurunan kesadaran, instabilitas hemodinamik, sesak berat, atau tidak ada respon pasca terapi awal. Indikasi Rujuk Segera ke Faskes Sekunder/Tersier.",
            pathophysiology = pathophysiologyText
        )
    }

    fun generateRealisticAnamnesisForDisease(title: String, system: String, isEmergency: Boolean): String {
        val lower = title.lowercase().trim()

        return when {
            // Jantung & Pembuluh Darah
            lower.contains("stemi") || lower.contains("infark") || lower.contains("koroner") || lower.contains("dada") ->
                "Nyeri dada kiri seperti ditindih beban berat sejak 2 jam lalu, menjalar ke lengan kiri dan leher, disertai keringat dingin dan dada berdebar."
            lower.contains("gagal jantung") || lower.contains("chf") || lower.contains("edema paru") ->
                "Mendadak sesak napas berat sejak 2 jam lalu terutama saat berbaring, terbangun malam hari karena sesak, dan kedua kaki bengkak."
            lower.contains("hipertensi") ->
                "Sakit kepala tegang di tengkuk leher belakang sejak kemarin, disertai rasa melayang dan pusing."
            lower.contains("aritmia") || lower.contains("palpitasi") || lower.contains("svt") || lower.contains("fibrilasi") ->
                "Dada terasa berdebar-debar sangat kencang dan tidak teratur sejak 1 jam lalu, disertai badan lemas."

            // Paru & Respirasi
            lower.contains("asma") || lower.contains("mengi") ->
                "Mendadak sesak napas berat berbunyi mengi ('ngik-ngik') sejak tadi malam pasca terpapar udara dingin."
            lower.contains("ppok") || lower.contains("emfisema") ->
                "Sesak napas makin memberat sejak 3 hari lalu, disertai batuk berdahak tebal dan cepat lelah saat beraktivitas."
            lower.contains("pneumonia") || lower.contains("paru") ->
                "Demam tinggi menggigil disertai batuk berdahak kental warna kehijauan dan nyeri dada saat bernapas dalam."
            lower.contains("tb") || lower.contains("tuberkulosis") ->
                "Batuk dahak sudah 3 minggu tak kunjung sembuh, berkeringat malam hari tanpa aktivitas, dan berat badan terus menurun."
            lower.contains("pleurit") || lower.contains("plurit") || lower.contains("efusi") || lower.contains("pneumothorax") ->
                "Nyeri dada tajam menusuk seperti tertusuk jarum saat menarik napas dalam atau batuk, disertai sesak napas saat berbaring."
            lower.contains("croup") || lower.contains("laringitis") ->
                "Anak batuk keras berbunyi menggonggong sejak semalam, napas berbunyi melengking kasar dan tampak sesak."

            // Pencernaan & Gastrointestinal
            lower.contains("apendisitis") || lower.contains("apendiks") ->
                "Nyeri perut hebat awalnya di ulu hati lalu berpindah menetap di perut kanan bawah sejak kemarin, disertai mual dan demam."
            lower.contains("gerd") || lower.contains("gastritis") || lower.contains("maag") || lower.contains("ulkus") ->
                "Rasa terbakar di dada dan ulu hati sejak kemarin, disertai asam mual naik ke tenggorokan dan perut kembung."
            lower.contains("diare") || lower.contains("gastroenteritis") || lower.contains("kolera") ->
                "Buang air besar cair berulang lebih dari 5 kali sejak semalam, disertai mual, muntah, dan lemas haus luar biasa."
            lower.contains("tifoid") || lower.contains("typhoid") ->
                "Demam makin tinggi terutama sore dan malam hari sejak 5 hari lalu, lidah terasa pahit kotor, dan perut terasa mual melilit."
            lower.contains("peritonitis") || lower.contains("perforasi") ->
                "Nyeri perut mendadak sangat hebat tajam di seluruh lapangan perut sejak 4 jam lalu, perut terasa kaku keras seperti papan."

            // Saraf & Otak
            lower.contains("stroke") || lower.contains("hemiparesis") || lower.contains("pelo") ->
                "Lemas separuh badan mendadak pada tangan dan kaki kanan, bibir perot, serta bicara pelo sejak 3 jam lalu."
            lower.contains("kejang") || lower.contains("epilep") ->
                "Kejang seluruh tubuh tidak sadarkan diri selama 3 menit, mata mendelik ke atas dan mulut berbusa."
            lower.contains("bppv") || lower.contains("vertigo") ->
                "Pusing berputar hebat mendadak saat berbalik posisi kepala sejak tadi pagi, disertai mual dan muntah."
            lower.contains("meningitis") || lower.contains("ensefalitis") ->
                "Demam tinggi mendadak disertai sakit kepala hebat tak tertahankan, kaku leher, mual muntah, dan lemas linglung."

            // Ginjal & Urologi
            lower.contains("batu") || lower.contains("urolitiasis") || lower.contains("ureter") || lower.contains("kolik") ->
                "Nyeri melilit hebat di pinggang kanan mendadak hilang timbul sejak 3 jam lalu, menjalar ke lipat paha, kencing warna kemerahan."
            lower.contains("isk") || lower.contains("sistitis") || lower.contains("pielonefritis") ->
                "Nyeri dan rasa terbakar saat buang air kecil sejak 2 hari lalu, sering kencing sedikit-sedikit, dan nyeri perut bawah."

            // Infeksi Tropis
            lower.contains("dengue") || lower.contains("dbd") ->
                "Demam tinggi mendadak terus menerus sejak 3 hari lalu, nyeri di belakang mata, linu seluruh persendian, dan mual."
            lower.contains("malaria") ->
                "Demam menggigil hebat hilang timbul setiap 2 hari sekali, disertai keringat dingin membasa baju dan badan lemas pucat."
            lower.contains("tetanus") ->
                "Mulut terasa kaku sulit dibuka (trismus) dan leher kaku kram sejak semalam pasca tertusuk paku berkarat 1 minggu lalu."

            // Endokrin & Metabolik
            lower.contains("diabetes") || lower.contains("dm ") || lower.contains("hiperglikemia") ->
                "Badan terasa sangat lemas, sering kencing terutama malam hari, selalu merasa haus dan cepat lapar sejak 1 minggu ini."
            lower.contains("kad") || lower.contains("ketoasidosis") ->
                "Napas cepat dan dalam bau buah, mual muntah berulang, lemas berat hingga linglung tidak sadarkan diri."
            lower.contains("hipoglikemia") ->
                "Badan mendadak lemas gemetaran, keluar keringat dingin membasahi baju, pandangan kabur dan hampir pingsan."

            // Kulit & Alergi
            lower.contains("sjs") || lower.contains("ten") || lower.contains("stevens") ->
                "Kulit seluruh tubuh merah melepuh perih, bibir dan mulut sariawan parah sulit menelan pasca minum obat warung 2 hari lalu."
            lower.contains("anafilaksis") ->
                "Mendadak sesak napas tercekik, bibir dan mata membengkak tebal, gatal bentol seluruh tubuh 10 menit pasca makan udang/injeksi."
            lower.contains("dermatitis") || lower.contains("eksem") || lower.contains("gatal") || lower.contains("prurit") || lower.contains("plurit") || lower.contains("prurigo") || lower.contains("skabies") ->
                "Gatal-gatal hebat di kulit terutama malam hari, timbul bintik bentol kemerahan yang sangat gatal dan perih saat digaruk."

            // Mata & THT
            lower.contains("glaukoma") ->
                "Mata mendadak sangat nyeri tembus ke kepala, penglihatan buram melihat pelangi di sekitar lampu, disertai mual muntah."
            lower.contains("konjungtivitis") ->
                "Kedua mata merah, berair, belekan kuning kental, dan terasa ganjal seperti ada pasir sejak kemarin."
            lower.contains("otitis") ->
                "Nyeri hebat pada telinga kanan, pendengaran berkurang, demam, dan keluar cairan kuning dari liang telinga."
            lower.contains("abses peritonsil") || lower.contains("tonsil") ->
                "Nyeri tenggorokan sangat hebat hingga tidak bisa menelan air ludah, mulut tidak bisa dibuka lebar, dan suara bergema."

            // Obgyn
            lower.contains("preeklamsia") || lower.contains("eklamsia") ->
                "Pusing berat di dahi, pandangan mata kabur, dan nyeri ulu hati hebat pada usia kehamilan trimester 3 sejak tadi pagi."
            lower.contains("abortus") || lower.contains("perdarahan") ->
                "Keluar darah segar menggumpal dari jalan lahir disertai mulas melilit perut bawah pada kehamilan muda."

            // Emergency Default
            isEmergency ->
                "Pasien datang dengan keluhan fisik berat yang timbul mendadak, membutuhkan pertolongan medis dan penanganan cito segera."

            // Fallback Umum
            else ->
                "Badan terasa lemas dan tidak nyaman sejak 2 hari lalu, disertai demam subfebris dan kurang nafsu makan."
        }
    }

    fun getOrCreateGuideline(title: String, organSystem: String = ""): PpkGuidelineItem {
        val existing = guidelines.find { 
            it.title.equals(title, ignoreCase = true) || it.title.contains(title, ignoreCase = true) || title.contains(it.title, ignoreCase = true)
        }
        if (existing != null) return existing
        return createDynamicGuideline(title, organSystem)
    }

    fun getAllExtendedGuidelines(): List<PpkGuidelineItem> {
        val existingTitles = guidelines.map { it.title.lowercase() }.toSet()
        val dynamicItems = MedicalCatalog.allDiagnoses
            .filter { diag -> existingTitles.none { it.contains(diag.lowercase()) || diag.lowercase().contains(it) } }
            .map { createDynamicGuideline(it) }
        return guidelines + dynamicItems
    }

    fun getGuidelinesByOrgan(organ: String): List<PpkGuidelineItem> {
        val clean = organ.lowercase().trim()
        val all = getAllExtendedGuidelines()
        if (clean == "semua" || clean.isBlank()) return all
        return all.filter {
            it.organSystem.lowercase().contains(clean) || clean.contains(it.organSystem.lowercase())
        }
    }
}

