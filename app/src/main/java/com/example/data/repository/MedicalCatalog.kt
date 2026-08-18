package com.example.data.repository

import com.example.data.model.AvailableCaseChip
import com.example.data.model.OrganSystem

data class DrugItem(
    val name: String,
    val categoryTag: String, // e.g. "nsaid", "analgesik", "antiplatelet", "antihipertensi", "antibiotik"
    val defaultForm: String = "Tablet",
    val defaultRoute: String = "Oral",
    val defaultFreq: String = "3x1"
)

object MedicalCatalog {

    val organCasesMap: Map<OrganSystem, List<AvailableCaseChip>> = mapOf(
        OrganSystem.KARDIOLOGI to listOf(
            AvailableCaseChip("STEMI Anteroseptal", isEmergency = true),
            AvailableCaseChip("STEMI Inferior", isEmergency = true),
            AvailableCaseChip("STEMI Anterior Ekstensi", isEmergency = true),
            AvailableCaseChip("STEMI Posterior", isEmergency = true),
            AvailableCaseChip("STEMI Ventrikel Kanan", isEmergency = true),
            AvailableCaseChip("NSTEMI Akut", isEmergency = true),
            AvailableCaseChip("Unstable Angina Pectoris (UAP)", isEmergency = true),
            AvailableCaseChip("Angina Pektoris Stabil (APS)", isEmergency = false),
            AvailableCaseChip("Angina Prinzmetal", isEmergency = false),
            AvailableCaseChip("Gagal Jantung Akut (ADHF)", isEmergency = true),
            AvailableCaseChip("Gagal Jantung Kronik (CHF NYHA IV)", isEmergency = false),
            AvailableCaseChip("Hipertensi Urgensi / Krisis", isEmergency = true),
            AvailableCaseChip("Hipertensi Emergensi", isEmergency = true),
            AvailableCaseChip("Perikarditis Akut", isEmergency = false),
            AvailableCaseChip("Tamponade Jantung", isEmergency = true),
            AvailableCaseChip("Fibrilasi Atrium (AF RVR)", isEmergency = true),
            AvailableCaseChip("Ventricular Tachycardia (VT)", isEmergency = true),
            AvailableCaseChip("Supraventricular Tachycardia (SVT)", isEmergency = true),
            AvailableCaseChip("Ventricular Fibrillation (VF)", isEmergency = true),
            AvailableCaseChip("Flutter Atrium", isEmergency = true),
            AvailableCaseChip("Sinus Bradycardia Symptomatic", isEmergency = true),
            AvailableCaseChip("Total AV Block", isEmergency = true),
            AvailableCaseChip("Sick Sinus Syndrome", isEmergency = true),
            AvailableCaseChip("Endokarditis Infektif", isEmergency = true),
            AvailableCaseChip("Syok Kardiogenik ec STEMI", isEmergency = true),
            AvailableCaseChip("Aneurisma Aorta Ruptur", isEmergency = true),
            AvailableCaseChip("Diseksi Aorta Akut Type A/B", isEmergency = true),
            AvailableCaseChip("Iskemik Tungkai Akut (PAD)", isEmergency = true),
            AvailableCaseChip("Deep Vein Thrombosis (DVT)", isEmergency = false),
            AvailableCaseChip("Miokarditis Akut Viral", isEmergency = false),
            AvailableCaseChip("Efusi Perikardial Masif", isEmergency = true),
            AvailableCaseChip("Hipertensi Pulmonal", isEmergency = false),
            AvailableCaseChip("Penyakit Jantung Rematik", isEmergency = false),
            AvailableCaseChip("Stenosis Mitral", isEmergency = false),
            AvailableCaseChip("Insufisiensi Aorta", isEmergency = false),
            AvailableCaseChip("Insufisiensi Mitral", isEmergency = false),
            AvailableCaseChip("Kardiomiopati Dilatasi", isEmergency = false),
            AvailableCaseChip("Kardiomiopati Hipertrofik", isEmergency = false),
            AvailableCaseChip("Brugada Syndrome", isEmergency = true),
            AvailableCaseChip("Long QT Syndrome", isEmergency = true),
            AvailableCaseChip("Trombosis Vena Sentral", isEmergency = true),
            AvailableCaseChip("Coarctation of Aorta", isEmergency = false)
        ),
        OrganSystem.NEUROLOGI to listOf(
            AvailableCaseChip("Stroke Iskemik Akut", isEmergency = true),
            AvailableCaseChip("Stroke Hemoragik (ICH)", isEmergency = true),
            AvailableCaseChip("Perdarahan Subaraknoid (SAH)", isEmergency = true),
            AvailableCaseChip("TIA / Stroke Ringan", isEmergency = false),
            AvailableCaseChip("Kejang Demam Sederhana", isEmergency = true),
            AvailableCaseChip("Kejang Demam Kompleks", isEmergency = true),
            AvailableCaseChip("Status Epileptikus", isEmergency = true),
            AvailableCaseChip("Meningitis Bakterial Akut", isEmergency = true),
            AvailableCaseChip("Meningitis Tuberkulosa", isEmergency = true),
            AvailableCaseChip("Ensefalitis Viral Akut", isEmergency = true),
            AvailableCaseChip("Tension Type Headache (TTH)", isEmergency = false),
            AvailableCaseChip("Migren Tanpa Aura", isEmergency = false),
            AvailableCaseChip("Migren dengan Aura", isEmergency = false),
            AvailableCaseChip("Cluster Headache", isEmergency = false),
            AvailableCaseChip("BPPV (Vertigo Posisi)", isEmergency = false),
            AvailableCaseChip("Meniere Disease", isEmergency = false),
            AvailableCaseChip("Bell's Palsy Akut", isEmergency = false),
            AvailableCaseChip("Carpal Tunnel Syndrome (CTS)", isEmergency = false),
            AvailableCaseChip("Trigeminal Neuralgia", isEmergency = false),
            AvailableCaseChip("Myasthenia Gravis Crisis", isEmergency = true),
            AvailableCaseChip("Guillain-Barré Syndrome (GBS)", isEmergency = true),
            AvailableCaseChip("Penyakit Parkinson", isEmergency = false),
            AvailableCaseChip("Neuropati Diabetik Perifer", isEmergency = false),
            AvailableCaseChip("HNP Lumbal L4-L5", isEmergency = false),
            AvailableCaseChip("HNP Servikal", isEmergency = false),
            AvailableCaseChip("Demensia Alzheimer", isEmergency = false),
            AvailableCaseChip("Demensia Vaskular", isEmergency = false),
            AvailableCaseChip("Neuralgia Pasca Herpes (NPH)", isEmergency = false),
            AvailableCaseChip("Abses Serebri", isEmergency = true),
            AvailableCaseChip("Hematoma Subdural Kronik", isEmergency = false),
            AvailableCaseChip("Multiple Sclerosis Relaps", isEmergency = true),
            AvailableCaseChip("Spinal Cord Compression", isEmergency = true),
            AvailableCaseChip("Ensefalopati Uremikum", isEmergency = true),
            AvailableCaseChip("Ensefalopati Hepatikum", isEmergency = true),
            AvailableCaseChip("Ensefalopati Hipertensi", isEmergency = true),
            AvailableCaseChip("Tremor Esensial", isEmergency = false),
            AvailableCaseChip("Cerebral Palsy Spastik", isEmergency = false),
            AvailableCaseChip("Locked-in Syndrome", isEmergency = true),
            AvailableCaseChip("Amyotrophic Lateral Sclerosis", isEmergency = false),
            AvailableCaseChip("Huntington Disease", isEmergency = false),
            AvailableCaseChip("Transverse Myelitis", isEmergency = true),
            AvailableCaseChip("Horner Syndrome", isEmergency = false)
        ),
        OrganSystem.PULMONOLOGI to listOf(
            AvailableCaseChip("Asma Akut Eksaserbasi Berat", isEmergency = true),
            AvailableCaseChip("Asma Bronkial Terkontrol", isEmergency = false),
            AvailableCaseChip("Status Asmatikus", isEmergency = true),
            AvailableCaseChip("PPOK Eksaserbasi Akut", isEmergency = true),
            AvailableCaseChip("PPOK Stabil Derajat III", isEmergency = false),
            AvailableCaseChip("Community-Acquired Pneumonia (CAP)", isEmergency = true),
            AvailableCaseChip("Hospital-Acquired Pneumonia (HAP)", isEmergency = true),
            AvailableCaseChip("TB Paru Kasus Baru BTA(+)", isEmergency = false),
            AvailableCaseChip("TB Paru Relaps / MDR-TB", isEmergency = false),
            AvailableCaseChip("TB Milier Akut", isEmergency = true),
            AvailableCaseChip("Tension Pneumothorax", isEmergency = true),
            AvailableCaseChip("Pneumothorax Spontan Primer", isEmergency = true),
            AvailableCaseChip("Pneumothorax Spontan Sekunder", isEmergency = true),
            AvailableCaseChip("Efusi Pleura Masif", isEmergency = true),
            AvailableCaseChip("Empiema Toraks Akut", isEmergency = true),
            AvailableCaseChip("Emboli Paru Akut", isEmergency = true),
            AvailableCaseChip("Abses Paru Bakterial", isEmergency = true),
            AvailableCaseChip("Bronkiektasis Terinfeksi", isEmergency = false),
            AvailableCaseChip("Atelektasis Paru Total", isEmergency = true),
            AvailableCaseChip("Karsinoma Paru SCLC/NSCLC", isEmergency = false),
            AvailableCaseChip("Pneumonia Aspirasi", isEmergency = true),
            AvailableCaseChip("Sindrom Distres Pernapasan (ARDS)", isEmergency = true),
            AvailableCaseChip("Edema Paru Kardiogenik", isEmergency = true),
            AvailableCaseChip("Edema Paru Non-Kardiogenik", isEmergency = true),
            AvailableCaseChip("Hemoptisis Masif Cito", isEmergency = true),
            AvailableCaseChip("Sleep Apnea Syndrome (OSA)", isEmergency = false),
            AvailableCaseChip("Silikosis / Pneumokoniosis", isEmergency = false),
            AvailableCaseChip("Asbestosis Kronik", isEmergency = false),
            AvailableCaseChip("Pleuritis Tuberkulosa", isEmergency = false),
            AvailableCaseChip("Pneumocystis Jirovecii (PJP)", isEmergency = true),
            AvailableCaseChip("Karsinoma Bronkogenik Hemoptisis", isEmergency = true),
            AvailableCaseChip("Cor Pulmonale Kronik", isEmergency = false),
            AvailableCaseChip("Cor Pulmonale Dekompensata", isEmergency = true),
            AvailableCaseChip("Benda Asing Trakeobronkial", isEmergency = true),
            AvailableCaseChip("Bronkitis Kronik Eksaserbasi", isEmergency = false),
            AvailableCaseChip("Trakeitis Bakterial Akut", isEmergency = true),
            AvailableCaseChip("Fistula Bronkopleural", isEmergency = true),
            AvailableCaseChip("Paru Parut / Fibrosis Paru", isEmergency = false),
            AvailableCaseChip("Sarkoidosis Paru", isEmergency = false),
            AvailableCaseChip("Pneumonia COVID-19 Berat", isEmergency = true),
            AvailableCaseChip("Pneumomediastinum Spontan", isEmergency = true),
            AvailableCaseChip("Hipoventilasi Obesitas (Pickwick)", isEmergency = false)
        ),
        OrganSystem.GASTROENTEROHEPATOLOGI to listOf(
            AvailableCaseChip("Apendisitis Akut Non-Perforasi", isEmergency = true),
            AvailableCaseChip("Apendisitis Perforasi", isEmergency = true),
            AvailableCaseChip("GERD Derajat Berat", isEmergency = false),
            AvailableCaseChip("Gastritis Erosif Akut", isEmergency = false),
            AvailableCaseChip("Ulkus Peptikum Perdarahan", isEmergency = true),
            AvailableCaseChip("Peritonitis ec Perforasi Gaster", isEmergency = true),
            AvailableCaseChip("Ileus Obstruktif Letak Tinggi", isEmergency = true),
            AvailableCaseChip("Ileus Obstruktif Letak Rendah", isEmergency = true),
            AvailableCaseChip("Ileus Paralitik", isEmergency = true),
            AvailableCaseChip("Kolesistitis Akut Kalkulus", isEmergency = true),
            AvailableCaseChip("Kolangitis Akut (Trias Charcot)", isEmergency = true),
            AvailableCaseChip("Kolelitiasis / Batu Empedu", isEmergency = false),
            AvailableCaseChip("Demam Tifoid Perforasi", isEmergency = true),
            AvailableCaseChip("Hepatitis A Akut Ikterik", isEmergency = false),
            AvailableCaseChip("Hepatitis B Kronik Eksaserbasi", isEmergency = false),
            AvailableCaseChip("Hepatitis C Kronik", isEmergency = false),
            AvailableCaseChip("Sirosis Hepatis + SBP", isEmergency = true),
            AvailableCaseChip("Perdarahan SCBA (Varises)", isEmergency = true),
            AvailableCaseChip("Perdarahan SCBB (Divertikulitis)", isEmergency = true),
            AvailableCaseChip("Disentri Amoeba / Shigella", isEmergency = false),
            AvailableCaseChip("Karsinoma Hepatoseluler", isEmergency = false),
            AvailableCaseChip("Pankreatitis Akut", isEmergency = true),
            AvailableCaseChip("Inflammatory Bowel Disease (IBD)", isEmergency = false),
            AvailableCaseChip("Colitis Ulserativa", isEmergency = false),
            AvailableCaseChip("Crohn's Disease", isEmergency = false),
            AvailableCaseChip("Abses Hati Amoeba", isEmergency = true),
            AvailableCaseChip("Abses Hati Pyogenik", isEmergency = true),
            AvailableCaseChip("Hemoroid Interna Terjepit", isEmergency = true),
            AvailableCaseChip("Dispepsia Fungsional", isEmergency = false),
            AvailableCaseChip("Dengue Gastrointestinal", isEmergency = true),
            AvailableCaseChip("Intususepsi / Invaginasi", isEmergency = true),
            AvailableCaseChip("Iskemik Mesenterika", isEmergency = true),
            AvailableCaseChip("Karsinoma Kolorektal Obstruktif", isEmergency = true),
            AvailableCaseChip("Divertikulitis Akut", isEmergency = true),
            AvailableCaseChip("Karsinoma Gaster", isEmergency = false),
            AvailableCaseChip("Gastroparesis Diabetik", isEmergency = false),
            AvailableCaseChip("Sindrom Zollinger-Ellison", isEmergency = false),
            AvailableCaseChip("Volvulus Sigmoid", isEmergency = true),
            AvailableCaseChip("Hernia Inkarserata", isEmergency = true),
            AvailableCaseChip("Megakolon Toksik", isEmergency = true),
            AvailableCaseChip("Fistula Ani Kompleks", isEmergency = false),
            AvailableCaseChip("Abses Perianal", isEmergency = true),
            AvailableCaseChip("Fisura Ani Akut", isEmergency = false),
            AvailableCaseChip("Sindrom Irritable Bowel (IBS)", isEmergency = false),
            AvailableCaseChip("Peritonitis Tuberkulosa", isEmergency = false)
        ),
        OrganSystem.ENDOKRINOLOGI to listOf(
            AvailableCaseChip("Ketoasidosis Diabetikum (KAD)", isEmergency = true),
            AvailableCaseChip("Status Hiperglikemia Hiperosmolar (HNS)", isEmergency = true),
            AvailableCaseChip("Krisis Tiroid (Thyroid Storm)", isEmergency = true),
            AvailableCaseChip("Graves Disease Hipertiroid", isEmergency = false),
            AvailableCaseChip("Tiroiditis Hashimoto / Hipotiroid", isEmergency = false),
            AvailableCaseChip("Hipoglikemia Berat", isEmergency = true),
            AvailableCaseChip("Diabetes Melitus Tipe 2 Tak Terkontrol", isEmergency = false),
            AvailableCaseChip("Diabetes Melitus Tipe 1 KAD", isEmergency = true),
            AvailableCaseChip("Krisis Adrenal Akut", isEmergency = true),
            AvailableCaseChip("Insufisiensi Adrenal Kronik (Addison)", isEmergency = false),
            AvailableCaseChip("Penyakit Cushing", isEmergency = false),
            AvailableCaseChip("Sindrom Cushing Iatrogenik", isEmergency = false),
            AvailableCaseChip("Hiperparatiroidisme", isEmergency = false),
            AvailableCaseChip("Hipoparatiroidisme / Tetani", isEmergency = true),
            AvailableCaseChip("Diabetes Insipidus Central", isEmergency = false),
            AvailableCaseChip("Diabetes Insipidus Nefrogenik", isEmergency = false),
            AvailableCaseChip("Sindrom Metabolik & Dislipidemia", isEmergency = false),
            AvailableCaseChip("Gout Arthritis Akut", isEmergency = false),
            AvailableCaseChip("Osteoporosis Risiko Fraktur", isEmergency = false),
            AvailableCaseChip("Feokromositoma Crisis", isEmergency = true),
            AvailableCaseChip("Tetani ec Hipokalsemia", isEmergency = true),
            AvailableCaseChip("Struma Nodosa Tiroid", isEmergency = false),
            AvailableCaseChip("Struma Diffusa Nontoksik", isEmergency = false),
            AvailableCaseChip("Akromegali / Adenoma Hipofisis", isEmergency = false),
            AvailableCaseChip("Defisiensi Vitamin D Berat", isEmergency = false),
            AvailableCaseChip("Hiperkalemia Berat", isEmergency = true),
            AvailableCaseChip("Hiponatremia Kronik SIADH", isEmergency = false),
            AvailableCaseChip("Koma Myxedema", isEmergency = true),
            AvailableCaseChip("Obesitas Morbid + Resistensi Insulin", isEmergency = false),
            AvailableCaseChip("Polycystic Ovary Syndrome (PCOS)", isEmergency = false),
            AvailableCaseChip("Hiperaldosteronisme Primer (Conn)", isEmergency = false),
            AvailableCaseChip("Hiperprolaktinemia", isEmergency = false),
            AvailableCaseChip("Ginekomastia Idiopatik", isEmergency = false),
            AvailableCaseChip("Hiperlipidemia Familial", isEmergency = false),
            AvailableCaseChip("Hipokalemia ec Paralisis Periodik", isEmergency = true),
            AvailableCaseChip("Hirsutisme Idiopatik", isEmergency = false),
            AvailableCaseChip("Defisiensi Hormon Pertumbuhan (GH)", isEmergency = false),
            AvailableCaseChip("Tiroiditis Subakut de Quervain", isEmergency = false),
            AvailableCaseChip("Hipopituitarisme Panhypopituitarism", isEmergency = false),
            AvailableCaseChip("Menopause Syndrome", isEmergency = false),
            AvailableCaseChip("Sindrom Polyglandular Autoimmune", isEmergency = false),
            AvailableCaseChip("Osteomalasia", isEmergency = false)
        ),
        OrganSystem.NEFRO_UROLOGI to listOf(
            AvailableCaseChip("Kolik Ureter / Urolitiasis", isEmergency = true),
            AvailableCaseChip("Nefrolitiasis / Batu Ginjal", isEmergency = false),
            AvailableCaseChip("Sistitis Akut Bawah", isEmergency = false),
            AvailableCaseChip("Pielonefritis Akut", isEmergency = true),
            AvailableCaseChip("Gagal Ginjal Akut (AKI)", isEmergency = true),
            AvailableCaseChip("Penyakit Ginjal Kronik (CKD) Stage 5", isEmergency = false),
            AvailableCaseChip("BPH Retensi Urin Akut", isEmergency = true),
            AvailableCaseChip("Glomerulonefritis Akut (GNAPS)", isEmergency = false),
            AvailableCaseChip("Sindrom Nefrotik Relaps", isEmergency = false),
            AvailableCaseChip("Torsi Testis", isEmergency = true),
            AvailableCaseChip("Prostatitis Akut Bakterial", isEmergency = true),
            AvailableCaseChip("Striktur Uretra", isEmergency = false),
            AvailableCaseChip("Varikokel Grade III", isEmergency = false),
            AvailableCaseChip("Hidrokel Skrotum Masif", isEmergency = false),
            AvailableCaseChip("Epididimo-Orkitis Akut", isEmergency = true),
            AvailableCaseChip("Kanker Kandung Kemih", isEmergency = false),
            AvailableCaseChip("Karsinoma Ginjal (RCC)", isEmergency = false),
            AvailableCaseChip("Parafimosis Emergensi", isEmergency = true),
            AvailableCaseChip("Ruptur Uretra Trauma", isEmergency = true),
            AvailableCaseChip("Ruptur Kandung Kemih", isEmergency = true),
            AvailableCaseChip("Nefropati Diabetik Stage IV", isEmergency = false),
            AvailableCaseChip("Nefropati Lupus", isEmergency = false),
            AvailableCaseChip("Ginjal Polikistik (ADPKD)", isEmergency = false),
            AvailableCaseChip("Priapismus Iskemik", isEmergency = true),
            AvailableCaseChip("Inkontinensia Urin", isEmergency = false),
            AvailableCaseChip("Batu Vesika Urinaria", isEmergency = false),
            AvailableCaseChip("Sistitis Interstisial", isEmergency = false),
            AvailableCaseChip("Karsinoma Prostat", isEmergency = false),
            AvailableCaseChip("Nefropati IgA (Berger)", isEmergency = false),
            AvailableCaseChip("Renal Tubular Acidosis (RTA)", isEmergency = false),
            AvailableCaseChip("Sindrom Alport", isEmergency = false),
            AvailableCaseChip("Orkitis Mumps", isEmergency = false),
            AvailableCaseChip("Spermatokel Epididim", isEmergency = false),
            AvailableCaseChip("Phimosis Berat Terinfeksi", isEmergency = false),
            AvailableCaseChip("Thrombosis Vena Renalis", isEmergency = true),
            AvailableCaseChip("Nefroksisitas Aminoglikosida", isEmergency = true),
            AvailableCaseChip("Sindrom Hemolitik Uremik (HUS)", isEmergency = true),
            AvailableCaseChip("Wilms Tumor (Nefroblastoma)", isEmergency = false),
            AvailableCaseChip("Vesicoureteral Reflux (VUR)", isEmergency = false),
            AvailableCaseChip("Neurogenic Bladder", isEmergency = false),
            AvailableCaseChip("Ureterokel Obstruktif", isEmergency = false),
            AvailableCaseChip("Abses Perinefrik", isEmergency = true)
        ),
        OrganSystem.INFEKSI_TROPIS to listOf(
            AvailableCaseChip("Demam Berdarah Dengue (DBD)", isEmergency = true),
            AvailableCaseChip("Dengue Shock Syndrome (DSS)", isEmergency = true),
            AvailableCaseChip("Demam Dengue (DF)", isEmergency = false),
            AvailableCaseChip("Demam Tifoid Uncomplicated", isEmergency = false),
            AvailableCaseChip("Demam Tifoid Ensefalopati", isEmergency = true),
            AvailableCaseChip("Malaria Falciparum Berat", isEmergency = true),
            AvailableCaseChip("Malaria Vivax Relaps", isEmergency = false),
            AvailableCaseChip("Malaria Ovale", isEmergency = false),
            AvailableCaseChip("Malaria Malariae", isEmergency = false),
            AvailableCaseChip("Leptospirosis Berat (Weil)", isEmergency = true),
            AvailableCaseChip("COVID-19 Berat / ARDS", isEmergency = true),
            AvailableCaseChip("Tetanus Berat", isEmergency = true),
            AvailableCaseChip("Filariasis Akut", isEmergency = false),
            AvailableCaseChip("Ensefalitis Rabies", isEmergency = true),
            AvailableCaseChip("Chikungunya Artralgia", isEmergency = false),
            AvailableCaseChip("Toksoplasmosis Serebral", isEmergency = true),
            AvailableCaseChip("Schistosomiasis Japonicum", isEmergency = false),
            AvailableCaseChip("Anthrax Kulit / GI", isEmergency = true),
            AvailableCaseChip("Difteri Laring / Faring", isEmergency = true),
            AvailableCaseChip("Amoebiasis Intestinal", isEmergency = false),
            AvailableCaseChip("Giardiasis Intestinal", isEmergency = false),
            AvailableCaseChip("Helminthiasis (Cacingan)", isEmergency = false),
            AvailableCaseChip("Varicella Zoster (Cacar Air)", isEmergency = false),
            AvailableCaseChip("Herpes Zoster Thorakalis", isEmergency = false),
            AvailableCaseChip("Morbili (Campak) Akut", isEmergency = false),
            AvailableCaseChip("Sepsis Gram Negatif/Positif", isEmergency = true),
            AvailableCaseChip("Flu Burung (H5N1)", isEmergency = true),
            AvailableCaseChip("Avian Influenza H7N9", isEmergency = true),
            AvailableCaseChip("Zika Virus Fever", isEmergency = false),
            AvailableCaseChip("Torch Infection (CMV/HSV)", isEmergency = false),
            AvailableCaseChip("Brucellosis Akut", isEmergency = false),
            AvailableCaseChip("Pes (Yersinia Pestis)", isEmergency = true),
            AvailableCaseChip("Melioidosis (Burkholderia)", isEmergency = true),
            AvailableCaseChip("Hookworm Anemia (Ankilostomiasis)", isEmergency = false),
            AvailableCaseChip("Ascariasis Intestinal", isEmergency = false),
            AvailableCaseChip("Strongyloidiasis", isEmergency = false),
            AvailableCaseChip("Taeniasis Solium", isEmergency = false),
            AvailableCaseChip("Cysticercosis Neuro", isEmergency = true),
            AvailableCaseChip("Lepra / Morbus Hansen MB", isEmergency = false),
            AvailableCaseChip("Lepra PB Reaksi ENL", isEmergency = true),
            AvailableCaseChip("Sepsis Meningokokus", isEmergency = true),
            AvailableCaseChip("Pertusis Dewasa", isEmergency = false)
        ),
        OrganSystem.HEMATOLOGI_ONKOLOGI to listOf(
            AvailableCaseChip("Anemia Defisiensi Besi", isEmergency = false),
            AvailableCaseChip("Anemia Hemolitik Autoimun (AIHA)", isEmergency = true),
            AvailableCaseChip("ITP (Idiopathic Thrombocytopenic)", isEmergency = true),
            AvailableCaseChip("Leukemia Limfoblastik Akut (ALL)", isEmergency = true),
            AvailableCaseChip("Leukemia Mieloblastik Akut (AML)", isEmergency = true),
            AvailableCaseChip("Leukemia Mieloid Kronik (CML)", isEmergency = false),
            AvailableCaseChip("Leukemia Limfositik Kronik (CLL)", isEmergency = false),
            AvailableCaseChip("Limfoma Hodgkin", isEmergency = false),
            AvailableCaseChip("Limfoma Non-Hodgkin (NHL)", isEmergency = false),
            AvailableCaseChip("Multiple Myeloma", isEmergency = false),
            AvailableCaseChip("Hemofilia A / B Perdarahan", isEmergency = true),
            AvailableCaseChip("DIC (Disseminated Coagulation)", isEmergency = true),
            AvailableCaseChip("Aplastic Anemia Berat", isEmergency = true),
            AvailableCaseChip("Thalassemia Beta Major", isEmergency = false),
            AvailableCaseChip("Thalassemia Alpha Trait", isEmergency = false),
            AvailableCaseChip("Polycythemia Vera", isEmergency = false),
            AvailableCaseChip("Von Willebrand Disease", isEmergency = false),
            AvailableCaseChip("Neutropenia Febril", isEmergency = true),
            AvailableCaseChip("Anemia Megaloblastik", isEmergency = false),
            AvailableCaseChip("Sindrom Lisis Tumor", isEmergency = true),
            AvailableCaseChip("Trombositosis Esensial", isEmergency = false),
            AvailableCaseChip("Sferositosis Herediter", isEmergency = false),
            AvailableCaseChip("Porfiria Akut Intermiten", isEmergency = true),
            AvailableCaseChip("Anemia Penyakit Kronis", isEmergency = false),
            AvailableCaseChip("Sindrom Myelodysplastic (MDS)", isEmergency = false),
            AvailableCaseChip("Deep Vein Thrombosis Hypercoag", isEmergency = false),
            AvailableCaseChip("Kanker Payudara Metastase", isEmergency = false),
            AvailableCaseChip("Limfadenopati Maligna", isEmergency = false),
            AvailableCaseChip("Hemofilia Acquired", isEmergency = true),
            AvailableCaseChip("Defisiensi Vitamin K Coagulopathy", isEmergency = true),
            AvailableCaseChip("Purpura Henoch-Schonlein", isEmergency = false),
            AvailableCaseChip("Thrombotik Thrombocytopenic Purpura (TTP)", isEmergency = true),
            AvailableCaseChip("Thrombophilia Factor V Leiden", isEmergency = false),
            AvailableCaseChip("Anemia Sideroblastik", isEmergency = false),
            AvailableCaseChip("Hemoglobinuria Paroksismal Nokturnal (PNH)", isEmergency = true),
            AvailableCaseChip("Myelofibrosis Primer", isEmergency = false),
            AvailableCaseChip("Karsinoma Nasofaring Metastasis", isEmergency = false),
            AvailableCaseChip("Osteosarcoma Femur", isEmergency = false),
            AvailableCaseChip("Ewing Sarcoma", isEmergency = false),
            AvailableCaseChip("Rhabdomyosarcoma", isEmergency = false),
            AvailableCaseChip("Neuroblastoma", isEmergency = false),
            AvailableCaseChip("Wilms Tumor", isEmergency = false)
        ),
        OrganSystem.PEDIATRI to listOf(
            AvailableCaseChip("Kejang Demam Sederhana", isEmergency = true),
            AvailableCaseChip("Kejang Demam Kompleks", isEmergency = true),
            AvailableCaseChip("Diare Akut Dehidrasi Berat", isEmergency = true),
            AvailableCaseChip("Bronkiolitis Akut RSV", isEmergency = true),
            AvailableCaseChip("Croup / Laringotrakeobronkitis", isEmergency = true),
            AvailableCaseChip("Morbili dengan Pneumonia", isEmergency = true),
            AvailableCaseChip("Varicella pada Anak", isEmergency = false),
            AvailableCaseChip("Pertusis (Batuk 100 Hari)", isEmergency = false),
            AvailableCaseChip("Marasmus-Kwashiorkor", isEmergency = true),
            AvailableCaseChip("Thalassemia Major Anemia Berat", isEmergency = true),
            AvailableCaseChip("Asfiksia Neonatorum Berat", isEmergency = true),
            AvailableCaseChip("Ikterus Neonatorum Patologis", isEmergency = true),
            AvailableCaseChip("Respiratory Distress Syndrome (RDS)", isEmergency = true),
            AvailableCaseChip("Penyakit Hirschsprung", isEmergency = true),
            AvailableCaseChip("Atresia Ani / Anus Imperforata", isEmergency = true),
            AvailableCaseChip("Invaginasi / Intususepsi Bayi", isEmergency = true),
            AvailableCaseChip("Stenosis Pilorus Hipertrofik", isEmergency = false),
            AvailableCaseChip("Kawasaki Disease", isEmergency = true),
            AvailableCaseChip("Sepsis Neonatorum", isEmergency = true),
            AvailableCaseChip("Demam Rematik Akut", isEmergency = false),
            AvailableCaseChip("Tetralogy of Fallot (ToF)", isEmergency = true),
            AvailableCaseChip("Defek Septum Ventrikel (VSD)", isEmergency = false),
            AvailableCaseChip("Defek Septum Atrium (ASD)", isEmergency = false),
            AvailableCaseChip("Patent Ductus Arteriosus (PDA)", isEmergency = false),
            AvailableCaseChip("Henoch-Schönpura (HSP)", isEmergency = false),
            AvailableCaseChip("Flu Singapura (HFMD)", isEmergency = false),
            AvailableCaseChip("Meningitis TB Anak", isEmergency = true),
            AvailableCaseChip("Ensefalitis Viral Anak", isEmergency = true),
            AvailableCaseChip("Phimosis Anak", isEmergency = false),
            AvailableCaseChip("Hernia Inguinalis Lateralis Anak", isEmergency = false),
            AvailableCaseChip("Undescended Testis (Cryptorchidism)", isEmergency = false),
            AvailableCaseChip("Atresia Biliaris", isEmergency = true),
            AvailableCaseChip("Omphalocele / Gastroschisis", isEmergency = true),
            AvailableCaseChip("Retinopathy of Prematurity (ROP)", isEmergency = false),
            AvailableCaseChip("Sepsis Puerperal Neonatus", isEmergency = true),
            AvailableCaseChip("Sindrom Nefrotik Anak", isEmergency = false),
            AvailableCaseChip("Glomerulonefritis Akut Anak", isEmergency = false),
            AvailableCaseChip("Stunting + Gizi Buruk", isEmergency = false),
            AvailableCaseChip("Rakitis Defisiensi Vitamin D", isEmergency = false),
            AvailableCaseChip("Otitis Media Supuratif Anak", isEmergency = false),
            AvailableCaseChip("Asma Anak Eksaserbasi", isEmergency = true),
            AvailableCaseChip("Cacingan Trichuris/Ascaris", isEmergency = false)
        ),
        OrganSystem.OBGYN to listOf(
            AvailableCaseChip("Preeklamsia Berat (PEB)", isEmergency = true),
            AvailableCaseChip("Eklamsia dengan Kejang", isEmergency = true),
            AvailableCaseChip("Plasenta Previa Totalis", isEmergency = true),
            AvailableCaseChip("Solusio Plasenta", isEmergency = true),
            AvailableCaseChip("Kehamilan Ektopik Terganggu (KET)", isEmergency = true),
            AvailableCaseChip("Perdarahan Postpartum (Atonia)", isEmergency = true),
            AvailableCaseChip("Perdarahan Postpartum (Laserasi)", isEmergency = true),
            AvailableCaseChip("Abortus Inkomplit Perdarahan", isEmergency = true),
            AvailableCaseChip("Abortus Imminens", isEmergency = false),
            AvailableCaseChip("Abortus Komplit", isEmergency = false),
            AvailableCaseChip("Abortus Insipiens", isEmergency = true),
            AvailableCaseChip("Abortus Septik", isEmergency = true),
            AvailableCaseChip("Hyperemesis Gravidarum", isEmergency = false),
            AvailableCaseChip("Karsinoma Serviks Uteri", isEmergency = false),
            AvailableCaseChip("Torsi Kista Ovarium", isEmergency = true),
            AvailableCaseChip("Pelvic Inflammatory Disease (PID)", isEmergency = false),
            AvailableCaseChip("Mioma Uteri Menometroragia", isEmergency = false),
            AvailableCaseChip("Endometriosis Nyeri Pelvis", isEmergency = false),
            AvailableCaseChip("Ketuban Pecah Dini (KPD)", isEmergency = true),
            AvailableCaseChip("Mola Hidatidosa", isEmergency = false),
            AvailableCaseChip("Choriocarcinoma", isEmergency = false),
            AvailableCaseChip("Ruptur Uteri Imminens", isEmergency = true),
            AvailableCaseChip("Distosia Bahu Persalinan", isEmergency = true),
            AvailableCaseChip("Prolaps Tali Pusat", isEmergency = true),
            AvailableCaseChip("Sepsis Puerperalis / Nifas", isEmergency = true),
            AvailableCaseChip("Mastitis / Abses Payudara", isEmergency = false),
            AvailableCaseChip("Vulvovaginitis Candidiasis", isEmergency = false),
            AvailableCaseChip("Vaginosis Bakterialis", isEmergency = false),
            AvailableCaseChip("Trichomoniasis Vaginalis", isEmergency = false),
            AvailableCaseChip("Abses Bartholin", isEmergency = true),
            AvailableCaseChip("Prolaps Uteri Grade III", isEmergency = false),
            AvailableCaseChip("Infertilitas Tuba / Anovulasi", isEmergency = false),
            AvailableCaseChip("Kanker Ovarium", isEmergency = false),
            AvailableCaseChip("Kanker Endometrium", isEmergency = false),
            AvailableCaseChip("Inversio Uteri Akut", isEmergency = true),
            AvailableCaseChip("Hematometra / Hymen Imperforata", isEmergency = false),
            AvailableCaseChip("Polip Endometrium", isEmergency = false),
            AvailableCaseChip("Polikistik Ovarium (PCOS)", isEmergency = false),
            AvailableCaseChip("Sindrom HELLP", isEmergency = true),
            AvailableCaseChip("Inkompetensi Serviks", isEmergency = false),
            AvailableCaseChip("Kehamilan Lewat Waktu (Post-term)", isEmergency = true),
            AvailableCaseChip("Gawat Janin (Fetal Distress)", isEmergency = true)
        ),
        OrganSystem.DERMATOVENEROLOGI to listOf(
            AvailableCaseChip("Stevens-Johnson Syndrome (SJS)", isEmergency = true),
            AvailableCaseChip("Toxic Epidermal Necrolysis (TEN)", isEmergency = true),
            AvailableCaseChip("Dermatitis Atopi Eksaserbasi", isEmergency = false),
            AvailableCaseChip("Dermatitis Kontak Alergi/Iritan", isEmergency = false),
            AvailableCaseChip("Dermatitis Seboroik", isEmergency = false),
            AvailableCaseChip("Psoriasis Vulgaris Plak", isEmergency = false),
            AvailableCaseChip("Urtikaria Akut & Angioedema", isEmergency = true),
            AvailableCaseChip("Herpes Zoster Thorakalis", isEmergency = false),
            AvailableCaseChip("Herpes Simpleks Labialis", isEmergency = false),
            AvailableCaseChip("Tinea Corporis / Cruris / Capitis", isEmergency = false),
            AvailableCaseChip("Tinea Versicolor (Panu)", isEmergency = false),
            AvailableCaseChip("Morbus Hansen (Kusta) MB", isEmergency = false),
            AvailableCaseChip("Morbus Hansen PB Reaksi ENL", isEmergency = true),
            AvailableCaseChip("Skabies Terinfeksi Sekunder", isEmergency = false),
            AvailableCaseChip("Sifilis Primer (Ulkus Durum)", isEmergency = false),
            AvailableCaseChip("Sifilis Sekunder", isEmergency = false),
            AvailableCaseChip("Uretritis Gonore (Gonorrhea)", isEmergency = false),
            AvailableCaseChip("Uretritis Non-Gonore", isEmergency = false),
            AvailableCaseChip("Condyloma Acuminata (Kutil)", isEmergency = false),
            AvailableCaseChip("Impetigo Krustosa / Bulosa", isEmergency = false),
            AvailableCaseChip("Erisipelas / Selulitis Tungkai", isEmergency = true),
            AvailableCaseChip("Pemfigus Vulgaris Bulosa", isEmergency = true),
            AvailableCaseChip("Pemfigoid Bulosa", isEmergency = true),
            AvailableCaseChip("Acne Vulgaris Grade III-IV", isEmergency = false),
            AvailableCaseChip("Vitiligo Generalized", isEmergency = false),
            AvailableCaseChip("Alopecia Areata", isEmergency = false),
            AvailableCaseChip("Fixed Drug Eruption (FDE)", isEmergency = false),
            AvailableCaseChip("Pityriasis Rosea Gibert", isEmergency = false),
            AvailableCaseChip("Candidiasis Cutis", isEmergency = false),
            AvailableCaseChip("Ulkus Mole (Chancroid)", isEmergency = false),
            AvailableCaseChip("Herpes Simpleks Genitalis", isEmergency = false),
            AvailableCaseChip("Pruritus Senilis / Pruritus Generalisata", isEmergency = false),
            AvailableCaseChip("Prurigo Nodularis / Prurigo Hebra", isEmergency = false),
            AvailableCaseChip("Lichen Simplex Chronicus", isEmergency = false),
            AvailableCaseChip("Granuloma Inguinale", isEmergency = false),
            AvailableCaseChip("Lymphogranuloma Venereum (LGV)", isEmergency = false),
            AvailableCaseChip("Ektima Pyoderma", isEmergency = false),
            AvailableCaseChip("Furunkulosis / Karbunkel", isEmergency = false),
            AvailableCaseChip("Hidradenitis Suppurativa", isEmergency = false),
            AvailableCaseChip("Keratosis Seboroik", isEmergency = false),
            AvailableCaseChip("Melanoma Maligna Kulit", isEmergency = false),
            AvailableCaseChip("Karsinoma Sel Basal (BCC)", isEmergency = false),
            AvailableCaseChip("Karsinoma Sel Skuamosa (SCC)", isEmergency = false),
            AvailableCaseChip("Erythema Multiforme", isEmergency = false),
            AvailableCaseChip("Lichen Planus", isEmergency = false)
        ),
        OrganSystem.PSIKIATRI to listOf(
            AvailableCaseChip("Skizofrenia Paranoid & Agitasi", isEmergency = true),
            AvailableCaseChip("Skizofrenia Katatonia", isEmergency = true),
            AvailableCaseChip("Skizofrenia Hebephrenic", isEmergency = true),
            AvailableCaseChip("Gangguan Bipolar Episode Manik", isEmergency = true),
            AvailableCaseChip("Gangguan Bipolar Episode Depresi", isEmergency = false),
            AvailableCaseChip("Depresi Berat & Risiko Bunuh Diri", isEmergency = true),
            AvailableCaseChip("Gangguan Cemas Menyeluruh", isEmergency = false),
            AvailableCaseChip("Gangguan Panik & Agorafobia", isEmergency = true),
            AvailableCaseChip("Obsessive Compulsive Disorder", isEmergency = false),
            AvailableCaseChip("Delirium Akut Organik", isEmergency = true),
            AvailableCaseChip("Delirium Tremens Putus Alkohol", isEmergency = true),
            AvailableCaseChip("Gangguan Ansietas Sosial", isEmergency = false),
            AvailableCaseChip("Post-Traumatic Stress Disorder", isEmergency = false),
            AvailableCaseChip("Gangguan Somatoform", isEmergency = false),
            AvailableCaseChip("Demensia dengan Gejala BPSD", isEmergency = false),
            AvailableCaseChip("Insomnia Kronik", isEmergency = false),
            AvailableCaseChip("Intoksikasi Opioid / Amfetamin", isEmergency = true),
            AvailableCaseChip("Sindrom Neuroleptik Maligna", isEmergency = true),
            AvailableCaseChip("Anoreksia Nervosa Berat", isEmergency = true),
            AvailableCaseChip("Bulimia Nervosa", isEmergency = false),
            AvailableCaseChip("Borderline Personality Disorder", isEmergency = false),
            AvailableCaseChip("Gangguan Penyesuaian", isEmergency = false),
            AvailableCaseChip("Gangguan Distimik", isEmergency = false),
            AvailableCaseChip("Katatonia ec Skizofrenia", isEmergency = true),
            AvailableCaseChip("ADHD Remaja", isEmergency = false),
            AvailableCaseChip("Akatisia Akut Obat", isEmergency = true),
            AvailableCaseChip("Krisis Okulogirik Dystonia", isEmergency = true),
            AvailableCaseChip("Parasomnia Night Terror", isEmergency = false),
            AvailableCaseChip("Fobia Spesifik", isEmergency = false),
            AvailableCaseChip("Gangguan Bipolar II Hypomanic", isEmergency = false),
            AvailableCaseChip("Depresi Sedang Tanpa Psikotik", isEmergency = false),
            AvailableCaseChip("Cyclothymic Disorder", isEmergency = false),
            AvailableCaseChip("Agorafobia Tanpa Panik", isEmergency = false),
            AvailableCaseChip("Dissociative Identity Disorder", isEmergency = false),
            AvailableCaseChip("Gangguan Konversi (Histeria)", isEmergency = false),
            AvailableCaseChip("Anoreksia Nervosa Restriktif", isEmergency = false),
            AvailableCaseChip("Binge Eating Disorder", isEmergency = false),
            AvailableCaseChip("Insomnia Onset Bebas", isEmergency = false),
            AvailableCaseChip("Gangguan Kepribadian Antisosial", isEmergency = false),
            AvailableCaseChip("Gangguan Kepribadian Histrionik", isEmergency = false),
            AvailableCaseChip("Gangguan Kepribadian Narsistik", isEmergency = false),
            AvailableCaseChip("Trikotilomania", isEmergency = false)
        ),
        OrganSystem.THT_KL to listOf(
            AvailableCaseChip("Otitis Media Akut Supuratif", isEmergency = true),
            AvailableCaseChip("Otitis Media Supuratif Kronik", isEmergency = false),
            AvailableCaseChip("Otitis Eksterna Diffusa", isEmergency = false),
            AvailableCaseChip("Otitis Eksterna Nekrotikans", isEmergency = true),
            AvailableCaseChip("Tonsilitis Akut Bakterial", isEmergency = false),
            AvailableCaseChip("Tonsilitis Kronik Hypertrofi", isEmergency = false),
            AvailableCaseChip("Abses Peritonsil (Quinsy)", isEmergency = true),
            AvailableCaseChip("Sinusitis Maksilaris Akut", isEmergency = false),
            AvailableCaseChip("Sinusitis Frontalis / Ethmoidalis", isEmergency = false),
            AvailableCaseChip("Rhinitis Alergi Persisten", isEmergency = false),
            AvailableCaseChip("Rhinitis Vasomotor", isEmergency = false),
            AvailableCaseChip("Rhinitis Atrofika (Ozaena)", isEmergency = false),
            AvailableCaseChip("Epistaksis Anterior", isEmergency = true),
            AvailableCaseChip("Epistaksis Posterior Masif", isEmergency = true),
            AvailableCaseChip("Benda Asing Saluran Napas", isEmergency = true),
            AvailableCaseChip("Benda Asing Telinga / Hidung", isEmergency = true),
            AvailableCaseChip("Presbiakusis / Tuli Sensorineural", isEmergency = false),
            AvailableCaseChip("Perforasi Membran Timpani", isEmergency = false),
            AvailableCaseChip("Laringitis Akut Serak", isEmergency = false),
            AvailableCaseChip("Polip Hidung Bilateral", isEmergency = false),
            AvailableCaseChip("Abses Retrofaring", isEmergency = true),
            AvailableCaseChip("Abses Parafaring", isEmergency = true),
            AvailableCaseChip("Meniere's Disease Vertigo", isEmergency = false),
            AvailableCaseChip("Mastoiditis Akut Komplikasi", isEmergency = true),
            AvailableCaseChip("Karsinoma Nasofaring (KNF)", isEmergency = false),
            AvailableCaseChip("Serumen Prop Telinga", isEmergency = false),
            AvailableCaseChip("Neuronitis Vestibularis Vertigo", isEmergency = false),
            AvailableCaseChip("Otomikosis Jamur Telinga", isEmergency = false),
            AvailableCaseChip("Papiloma Laring", isEmergency = false),
            AvailableCaseChip("Parotitis Akut (Mumps)", isEmergency = false),
            AvailableCaseChip("Fraktur Os Nasal Trauma", isEmergency = true),
            AvailableCaseChip("Kista Duktus Tiroglosus", isEmergency = false),
            AvailableCaseChip("Kista Celah Brankial", isEmergency = false),
            AvailableCaseChip("Labirinitis Akut", isEmergency = true),
            AvailableCaseChip("Trauma Akustik / Noise Loss", isEmergency = false),
            AvailableCaseChip("Paralisis Pita Suara", isEmergency = false),
            AvailableCaseChip("Benda Asing Esofagus", isEmergency = true),
            AvailableCaseChip("Abses Submandibula (Ludwig)", isEmergency = true),
            AvailableCaseChip("Angiofibroma Nasofaring Belia", isEmergency = false),
            AvailableCaseChip("Otosclerosis Telinga Tengah", isEmergency = false),
            AvailableCaseChip("Sinusitis Jamur Invasif", isEmergency = true),
            AvailableCaseChip("Laringomalasia Anak", isEmergency = false)
        ),
        OrganSystem.OFTALMOLOGI to listOf(
            AvailableCaseChip("Glaukoma Akut Sudut Tertutup", isEmergency = true),
            AvailableCaseChip("Glaukoma Sudut Terbuka Kronik", isEmergency = false),
            AvailableCaseChip("Ulkus Kornea Pseudomonas", isEmergency = true),
            AvailableCaseChip("Ulkus Kornea Jamur", isEmergency = true),
            AvailableCaseChip("Konjungtivitis Bakteri Supuratif", isEmergency = false),
            AvailableCaseChip("Konjungtivitis Alergi Vernal", isEmergency = false),
            AvailableCaseChip("Konjungtivitis Viral Epidemik", isEmergency = false),
            AvailableCaseChip("Keratitis Herpes Simpleks", isEmergency = true),
            AvailableCaseChip("Keratitis Pungtata", isEmergency = false),
            AvailableCaseChip("Ablasio Retina Regmatogen", isEmergency = true),
            AvailableCaseChip("Endoftalmitis Pasca Bedah", isEmergency = true),
            AvailableCaseChip("Endoftalmitis Endogen", isEmergency = true),
            AvailableCaseChip("Trauma Tumpul Mata (Hifema)", isEmergency = true),
            AvailableCaseChip("Trauma Kimia Mata (Asam/Basa)", isEmergency = true),
            AvailableCaseChip("Katarak Senilis Matur", isEmergency = false),
            AvailableCaseChip("Katarak Komplikata", isEmergency = false),
            AvailableCaseChip("Hordeolum Eksternum", isEmergency = false),
            AvailableCaseChip("Kalazion Kronik", isEmergency = false),
            AvailableCaseChip("Pterigium Grade III-IV", isEmergency = false),
            AvailableCaseChip("Retinopati Diabetik Proliferatif", isEmergency = false),
            AvailableCaseChip("Retinopati Hipertensi Grade IV", isEmergency = false),
            AvailableCaseChip("Neuritis Optik", isEmergency = true),
            AvailableCaseChip("Oklusi Arteri Retina (CRAO)", isEmergency = true),
            AvailableCaseChip("Oklusi Vena Retina (CRVO)", isEmergency = true),
            AvailableCaseChip("Skleritis Akut", isEmergency = false),
            AvailableCaseChip("Uveitis Anterior Akut", isEmergency = true),
            AvailableCaseChip("Uveitis Posterior / Panuveitis", isEmergency = true),
            AvailableCaseChip("Selulitis Orbita Emergency", isEmergency = true),
            AvailableCaseChip("Blefaritis Anterior", isEmergency = false),
            AvailableCaseChip("Dry Eye Syndrome", isEmergency = false),
            AvailableCaseChip("Strabismus Eisotropia", isEmergency = false),
            AvailableCaseChip("Ambliopia Lazy Eye", isEmergency = false),
            AvailableCaseChip("Benda Asing Kornea", isEmergency = true),
            AvailableCaseChip("Trauma Tembus Bola Mata", isEmergency = true),
            AvailableCaseChip("Skleromalasia Perforans", isEmergency = false),
            AvailableCaseChip("Keratokonus Progressif", isEmergency = false),
            AvailableCaseChip("Degenerasi Makula (AMD)", isEmergency = false),
            AvailableCaseChip("Retinitis Pigmentosa", isEmergency = false),
            AvailableCaseChip("Distrofi Kornea", isEmergency = false),
            AvailableCaseChip("Entropion / Ektropion Palpebra", isEmergency = false),
            AvailableCaseChip("Dakriosistitis Akut", isEmergency = true),
            AvailableCaseChip("Pterigium Recurrent", isEmergency = false)
        ),
        OrganSystem.MUSKULOSKELETAL to listOf(
            AvailableCaseChip("Artritis Gout Akut", isEmergency = false),
            AvailableCaseChip("Rheumatoid Arthritis (RA) Akut", isEmergency = false),
            AvailableCaseChip("Osteoartritis Genu Grade III-IV", isEmergency = false),
            AvailableCaseChip("Systemic Lupus Erythematosus (SLE)", isEmergency = true),
            AvailableCaseChip("Septic Arthritis Genu", isEmergency = true),
            AvailableCaseChip("Ankylosing Spondylitis", isEmergency = false),
            AvailableCaseChip("Tendinitis Achilles / Plantar Fasciitis", isEmergency = false),
            AvailableCaseChip("Frozen Shoulder (Adhesive Capsulitis)", isEmergency = false),
            AvailableCaseChip("Low Back Pain (LBP) Spondylosis", isEmergency = false),
            AvailableCaseChip("Fraktur Kompresi Vertebra", isEmergency = true),
            AvailableCaseChip("Osteomielitis Akut Bakterial", isEmergency = true),
            AvailableCaseChip("Ruptur Ligamen ACL Tear", isEmergency = true),
            AvailableCaseChip("Ruptur Meniskus Medialis", isEmergency = false),
            AvailableCaseChip("Lateral Epicondylitis (Tennis Elbow)", isEmergency = false),
            AvailableCaseChip("Medial Epicondylitis (Golfer Elbow)", isEmergency = false),
            AvailableCaseChip("De Quervain Tenosynovitis", isEmergency = false),
            AvailableCaseChip("Fibromyalgia Syndrome", isEmergency = false),
            AvailableCaseChip("Polymyalgia Rheumatica", isEmergency = false),
            AvailableCaseChip("Scleroderma Sistemik", isEmergency = false),
            AvailableCaseChip("Pseudogout (CPPD)", isEmergency = false),
            AvailableCaseChip("Dislokasi Sendi Bahu", isEmergency = true),
            AvailableCaseChip("Spondilitis TB (Pott's Disease)", isEmergency = false),
            AvailableCaseChip("Ganglion Cyst", isEmergency = false),
            AvailableCaseChip("Trigger Finger", isEmergency = false),
            AvailableCaseChip("Flat Foot Simptomatik", isEmergency = false),
            AvailableCaseChip("Osteosarcoma Femur", isEmergency = false),
            AvailableCaseChip("Ewing Sarcoma", isEmergency = false),
            AvailableCaseChip("Rhabdomyosarcoma", isEmergency = false),
            AvailableCaseChip("Osteomalasia Dewasa", isEmergency = false),
            AvailableCaseChip("Paget's Disease of Bone", isEmergency = false),
            AvailableCaseChip("Spondilolistesis Lumbal", isEmergency = false),
            AvailableCaseChip("Scoliosis Idiopatik", isEmergency = false),
            AvailableCaseChip("Dupuytren Contracture", isEmergency = false),
            AvailableCaseChip("Kyphosis Senilis", isEmergency = false),
            AvailableCaseChip("Osteomielitis Kronik Fistula", isEmergency = false),
            AvailableCaseChip("Bursitis Olekranon", isEmergency = false),
            AvailableCaseChip("Tarsal Tunnel Syndrome", isEmergency = false),
            AvailableCaseChip("Rotator Cuff Tear", isEmergency = false),
            AvailableCaseChip("Osgood-Schlatter Disease", isEmergency = false),
            AvailableCaseChip("Calcaneal Spur", isEmergency = false),
            AvailableCaseChip("Hallux Valgus", isEmergency = false)
        ),
        OrganSystem.TRAUMA_EMERGENCY to listOf(
            AvailableCaseChip("Syok Anafilaktik Injeksi/Sengatan", isEmergency = true),
            AvailableCaseChip("Tension Pneumothorax Trauma", isEmergency = true),
            AvailableCaseChip("Open Pneumothorax (Sucking Chest)", isEmergency = true),
            AvailableCaseChip("Massive Hemothorax", isEmergency = true),
            AvailableCaseChip("Flail Chest Kontusi Paru", isEmergency = true),
            AvailableCaseChip("Fraktur Femur Terbuka + Syok", isEmergency = true),
            AvailableCaseChip("Fraktur Pelvis Unstable", isEmergency = true),
            AvailableCaseChip("Epidural Hematoma (EDH) Lucid", isEmergency = true),
            AvailableCaseChip("Subdural Hematoma (SDH) Akut", isEmergency = true),
            AvailableCaseChip("Trauma Kapitis Berat GCS < 8", isEmergency = true),
            AvailableCaseChip("Syok Septik Intraabdominal", isEmergency = true),
            AvailableCaseChip("Syok Hipovolemik Perdarahan", isEmergency = true),
            AvailableCaseChip("Combustio Luka Bakar > 30%", isEmergency = true),
            AvailableCaseChip("Trauma Inhalasi Saluran Napas", isEmergency = true),
            AvailableCaseChip("Compartment Syndrome Tungkai", isEmergency = true),
            AvailableCaseChip("Ruptur Lien / Hepar Trauma", isEmergency = true),
            AvailableCaseChip("Syok Neurogenik Medulla Spinalis", isEmergency = true),
            AvailableCaseChip("Tenggelam / Near Drowning", isEmergency = true),
            AvailableCaseChip("Intoksikasi Organofosfat", isEmergency = true),
            AvailableCaseChip("Gigitan Ular Berbisa Venomous", isEmergency = true),
            AvailableCaseChip("Heat Stroke / Hipertermia", isEmergency = true),
            AvailableCaseChip("Frostbite & Hipotermia Berat", isEmergency = true),
            AvailableCaseChip("Cardiac Arrest Asistol / PEA", isEmergency = true),
            AvailableCaseChip("Tersedak Benda Asing Choking", isEmergency = true),
            AvailableCaseChip("Trauma Servikal Laminektomi", isEmergency = true),
            AvailableCaseChip("Barotrauma Paru", isEmergency = true),
            AvailableCaseChip("Sengatan Listrik Tegangan Tinggi", isEmergency = true),
            AvailableCaseChip("Trauma Tumpul Abdomen Ruptur Usus", isEmergency = true),
            AvailableCaseChip("Dislokasi Panggul Posterior", isEmergency = true),
            AvailableCaseChip("Amputasi Traumatik Tungkai", isEmergency = true),
            AvailableCaseChip("Ruptur Aorta Traumatik", isEmergency = true),
            AvailableCaseChip("Trauma Mata Tembus", isEmergency = true),
            AvailableCaseChip("Luka Tembak Abdomen", isEmergency = true),
            AvailableCaseChip("Luka Tusuk Dada Thoracotomy", isEmergency = true),
            AvailableCaseChip("Intoksikasi Karbon Monoksida (CO)", isEmergency = true),
            AvailableCaseChip("Intoksikasi Sianida", isEmergency = true),
            AvailableCaseChip("Intoksikasi Alkohol Metanol", isEmergency = true),
            AvailableCaseChip("Sindrom Crush Muscle Necrosis", isEmergency = true),
            AvailableCaseChip("Sengatan Lebah Anafilaksis", isEmergency = true),
            AvailableCaseChip("Gigitan Anjing Suspek Rabies", isEmergency = true),
            AvailableCaseChip("Trauma Wajah Le Fort III", isEmergency = true),
            AvailableCaseChip("Fractured Clavicle & Ribs", isEmergency = true)
        )
    )

    val legacyDiagnoses = listOf(
        // === KARDIOLOGI & VASKULAR ===
        "Infark Miokard Akut dengan ST Elevasi (STEMI) Anteroseptal",
        "Infark Miokard Akut dengan ST Elevasi (STEMI) Inferior",
        "Non-ST Elevation Myocardial Infarction (NSTEMI)",
        "Unstable Angina Pectoris (UAP) / Angina Tak Stabil",
        "Angina Pektoris Stabil (APS)",
        "Gagal Jantung Kongestif (CHF / Acute Decompensated Heart Failure)",
        "Hipertensi Urgensi / Krisis Hipertensi",
        "Perikarditis Akut",
        "Tamponade Jantung",
        "Fibrilasi Atrial (AF) Rapid Ventricular Response (RVR)",
        "Endokarditis Infektif Akut",
        "Syok Kardiogenik ec STEMI Massive",
        "Aneurisma Aorta Abdominalis (AAA) Ruptur",
        "Penyakit Arteri Perifer (PAD) / Iskemik Tungkai Akut",
        "Trombosis Vena Dalam (Deep Vein Thrombosis / DVT) Femoralis",
        "Miokarditis Akut ec Infeksi Viral",
        "Efusi Perikardial Masif dengan Ancam Kritis",
        "Hipertensi Pulmonal Primer / Sekunder",

        // === NEUROLOGI ===
        "Stroke Iskemik Akut (Serangan Otak Iskemik)",
        "Stroke Hemoragik (Perdarahan Intraserebral / ICH / SAH)",
        "Transient Ischemic Attack (TIA) / Stroke Ringan",
        "Kejang Demam Sederhana",
        "Kejang Demam Kompleks",
        "Epilepsi / Status Epileptikus",
        "Meningitis Bakterialis / Ensefalitis Viral",
        "Tension Type Headache (TTH)",
        "Migren tanpa Aura",
        "Benign Paroxysmal Positional Vertigo (BPPV)",
        "Bell's Palsy (Paresis N. VII Perifer)",
        "Carpal Tunnel Syndrome (CTS) Dextra",
        "Trigeminal Neuralgia Dextra",
        "Myasthenia Gravis Krisis Miastenik",
        "Guillain-Barré Syndrome (GBS) / Polineuropati Demielinisasi",
        "Penyakit Parkinson (Parkinsonism)",
        "Polineuropati Diabetik / Neuropati Perifer",
        "Hernia Nucleus Pulposus (HNP) Lumbal L4-L5",
        "Demensia Alzheimer / Demensia Vaskular",
        "Neuralgia Pasca Herpes (NPH) Thorakalis",
        "Ensefalopati Uremikum / Hepatikum",

        // === PULMONOLOGI ===
        "Asma Bronkial Eksaserbasi Akut Berat",
        "PPOK (Penyakit Paru Obstruktif Kronik) Eksaserbasi Akut",
        "Community-Acquired Pneumonia (CAP) Derajat Berat",
        "Tuberkulosis (TB) Paru Kasus Baru BTA Positif",
        "TB Ekstra Paru / Meningitis Tuberkulosa",
        "Tension Pneumothorax ec Trauma Dada",
        "Efusi Pleura Masif Dextra",
        "Emboli Paru Akut (Pulmonary Embolism)",
        "Abses Paru Bakterial",
        "Bronkiektasis Terinfeksi Akut",
        "Atelektasis Paru Dextra ec Sumbatan Mukus",
        "Karsinoma Paru (Bronchogenic Carcinoma) SCLC/NSCLC",
        "Pneumonia Aspirasi pada Pasien Stroke",
        "Obstructive Sleep Apnea (OSA) / Sindrom Apnea Tidur",

        // === GASTROENTEROLOGI & HEPATOLOGI ===
        "Apendisitis Akut (Apendisitis Perforasi)",
        "Gastroesophageal Reflux Disease (GERD)",
        "Gastritis Erosif / Tukak Peptikum dengan Perdarahan",
        "Peritonitis Akut ec Perforasi Gaster",
        "Kolesistitis Akut ec Kolelitiasis",
        "Demam Tifoid dengan Komplikasi Perforasi",
        "Hepatitis A / B Akut dengan Ikterik",
        "Ileus Obstruktif ec Volvulus / Hernia Inkarserata",
        "Ileus Paralitik ec Hipokalemia / Post-Op",
        "Sirosis Hepatis Dekompensata dengan Asites & SBP",
        "Perdarahan Saluran Cerna Atas (PSCA) ec Varises Esofagus",
        "Perdarahan Saluran Cerna Bawah (PSCB) ec Divertikulitis/Hemoroid",
        "Diare Akut Disentri Amoeba / Shigellosis",
        "Karsinoma Hepatoseluler (HCC) / Kanker Hati",
        "Pankreatitis Akut ec Kolelitiasis / Alkohol",
        "Colitis Ulserativa / Crohn's Disease (Inflammatory Bowel Disease)",
        "Abses Hati Amoeba / Bakterial",
        "Hemoroid Interna Grade III-IV Terjepit",

        // === ENDOKRINOLOGI & METABOLIK ===
        "Ketoasidosis Diabetikum (KAD)",
        "Status Hiperglikemia Hiperosmolar (HNS)",
        "Krisis Tiroid (Thyroid Storm) / Tirotoksikosis",
        "Graves Disease dengan Hipertiroidisme",
        "Tiroiditis Hashimoto / Hipotiroidisme",
        "Hipoglikemia Berat ec Sulfonilurea / Insulin",
        "Diabetes Melitus Tipe 2 Terkontrol / Tak Terkontrol",
        "Krisis Adrenal Akut (Adrenal Insufficiency)",
        "Penyakit Cushing (Cushing Syndrome)",
        "Hiperparatiroidisme Primer / Sekunder",
        "Diabetes Insipidus Central / Nephrogenic",
        "Sindrom Metabolik / Dislipidemia Kombinasi Berat",

        // === NEFRO-UROLOGI ===
        "Urolitiasis / Batu Ureter (Kolik Ureter Akut)",
        "Nefrolitiasis / Batu Ginjal Dextra",
        "Infeksi Saluran Kemih (ISK) / Pyelonefritis Akut",
        "Gagal Ginjal Akut (Acute Kidney Injury / AKI)",
        "Penyakit Ginjal Kronik (CKD) Stage 5 on HD",
        "Benign Prostatic Hyperplasia (BPH) dengan Retensio Urin Akut",
        "Glomerulonefritis Akut Pasca Streptokokus (GNAPS)",
        "Sindrom Nefrotik Relaps Akut pada Anak",
        "Torsi Testis Dextra (Kedaruratan Urologi)",
        "Prostatitis Akut Bakterial",
        "Striktur Uretra Pasca Trauma / Infeksi",
        "Varikokel / Hidrokel Skrotum Dextra",

        // === INFEKSI TROPIS & PARASIT ===
        "Demam Berdarah Dengue (DBD) Grade I-IV / Dengue Shock Syndrome",
        "Malaria Falciparum Berat dengan Komplikasi Otak",
        "Malaria Vivax / Ovale Relaps",
        "Leptospirosis Berat (Weil's Disease)",
        "COVID-19 Derajat Berat dengan ARDS",
        "Tetanus Berat (Grade III-IV)",
        "Filariasis Akut (Limfadenitis Filaria)",
        "Rabies Akut (Ensefalitis Rabies)",
        "Chikungunya dengan Artralgia Akut",
        "Toksoplasmosis Serebral pada Pasien Immunocompromised",
        "Schistosomiasis Japonicum / Mansoni",
        "Anthrax Kulit / Gastrointestinal",
        "Difteri Faring/Laring pada Anak",

        // === PEDIATRI (KESEHATAN ANAK) ===
        "Kejang Demam Kompleks pada Anak",
        "Diare Akut Dehidrasi Sedang-Berat pada Anak",
        "Bronkiolitis Akut ec Infeksi RSV pada Bayi",
        "Croup (Laringotrakeobronkitis Akut) pada Balita",
        "Morbili (Campak) dengan Komplikasi Bronkopneumonia",
        "Varicella (Cacar Air) Tanpa Komplikasi",
        "Pertusis (Batuk 100 Hari)",
        "Marasmus-Kwashiorkor (Gizi Buruk Berat dengan Hipoglikemia)",
        "Thalassemia Major Beta dengan Anemia Berat",
        "Asfiksia Neonatorum Berat",
        "Ikterus Neonatorum Patologis (Hyperbilirubinemia)",
        "Respiratory Distress Syndrome (RDS) Neonatus",
        "Penyakit Hirschsprung (Megakolon Kongenital)",
        "Atresia Ani / Anus Imperforata",

        // === OBSTETRI & GINEKOLOGI (OBGYN) ===
        "Preeklamsia Berat (PEB) pada Kehamilan",
        "Eklamsia dengan Kejang Terkontrol",
        "Kehamilan Ektopik Terganggu (KET)",
        "Abortus Imminens",
        "Abortus Inkomplit dengan Perdarahan Pervaginam",
        "Perdarahan Postpartum (PPP) ec Atonia Uteri",
        "Plasenta Previa Totalis dengan Perdarahan Aktif",
        "Solusio Plasenta dengan Gawat Janin",
        "Kista Ovarium Terpuntir (Torsi Kista Ovarium)",
        "Pelvic Inflammatory Disease (PID) / Salpingitis Akut",
        "Hyperemesis Gravidarum Grade II-III",
        "Mastitis Puerperalis / Abses Payudara Postpartum",
        "Mioma Uteri Intramural dengan Menometroragia",
        "Endometritis Akut Pasca Persalinan",
        "Vaginosis Bakterialis / Trikomoniasis Vaginalis",

        // === KEGAWATDARURATAN & TRAUMA ===
        "Syok Hemoragik Derajat III-IV ec Ruptur Limpa / Trauma Tumpul Abdomen",
        "Syok Neurogenik ec Trauma Servikal",
        "Fraktur Pelvis dengan Hemoragi Intraabdominal",
        "Syok Anafilaksis ec Reaksi Alergi Obat / Makanan Akut",
        "Luka Bakar Grade IIB-III 40% BSA dengan Trauma Inhalasi",
        "Trauma Kepala Berat (TKB) / Epidural Hematoma (EDH)",
        "Subdural Hematoma (SDH) Akut Traumatik",
        "Tension Pneumothorax / Open Pneumothorax Traumatik",
        "Contusio Serebri / Cedera Otak Ringan-Sedang",
        "Dislokasi Sendi Bahu (Anterior Shoulder Dislocation)",
        "Fraktur Colles / Radius Distal Dextra",
        "Heat Stroke / Exhaustion ec Sengatan Panas",

        // === REUMATOLOGI & MUSKULOSKELETAL ===
        "Gout / Podagra Akut (Artritis Gout)",
        "Osteoartritis Genu Dextra/Sinistra Grade III-IV",
        "Rheumatoid Arthritis Eksaserbasi Akut",
        "Systemic Lupus Erythematosus (SLE) Flaring dengan Nefritis Lupus",
        "Ankylosing Spondylitis",
        "Osteomielitis Akut Femur Dextra",
        "Spondilitis Tuberkulosa (Pott's Disease)",
        "Osteoporosis Senilis dengan Fraktur Kompresi Vertebra",
        "Artritis Septik Sendi Lutut",
        "Fibromialgia / Sindrom Nyeri Otot Kronik",

        // === PSIKIATRI & JIWA ===
        "Skizofrenia Paranoik dengan Krisis Gaduh Gelisah",
        "Gangguan Ansietas Panik (Panic Attack) dengan Hiperventilasi",
        "Gangguan Depresi Berat dengan Ide Bunuh Diri Aktif",
        "Bipolar Fase Manik dengan Gejala Psikotik",
        "Delirium ec Kondisi Medis Umum / Sepsis",
        "Gangguan Obsesif Kompulsif (OCD) Berat",
        "Post-Traumatic Stress Disorder (PTSD)",
        "Anoreksia Nervosa / Bulimia Nervosa",
        "Delirium Tremens ec Putus Zat Alkohol",

        // === THT-KL ===
        "Abses Peritonsil (Quinsy) Dextra",
        "Otitis Media Akut (OMA) Fase Perforasi Dextra",
        "Otitis Eksterna Difus Akut / Otomikosis",
        "Sinusitis Maksilaris Akut Bakterial",
        "Epistaksis Anterior / Posterior Masif",
        "Benda Asing di Saluran Napas / Esofagus",
        "Presbiakusis (Tuli Saraf Usia Lanjut)",
        "Polip Nasi Bilateral dengan Obstruksi Hidung",
        "Karsinoma Nasofaring (KNF) Stadium Lanjut",

        // === OFTALMOLOGI (MATA) ===
        "Ulkus Kornea Bakterial Dextra",
        "Glaukoma Akut Sudut Tertutup OD/OS",
        "Konjungtivitis Gonore Neonatorum",
        "Erosi Kornea Traumatik OD",
        "Endoftalmitis Akut Pasca Trauma Tembus Mata",
        "Katarak Senilis Matur OD/OS",
        "Pterigium Grade III OD Menutup Pupil",
        "Ablasio Retina Regmatogen OD",
        "Hordeolum Eksternum / Kalazion Palpebra Superior",
        "Retinopati Diabetika Non-Proliferatif / Proliferatif",

        // === DERMATOVENEROLOGI (KULIT & KELAMIN) ===
        "Herpes Zoster Thorakalis Dextra",
        "Dermatitis Atopik Eksaserbasi Akut dengan Infeksi Sekunder",
        "Sifilis Sekunder (Lues II)",
        "Uretritis Gonore (Gonore Akut pada Pria)",
        "Lepra / Morbus Hansen Tipe Multibasiler (MB) Reaksi Erythema Nodosum Leprosum",
        "Steven-Johnson Syndrome (SJS) / Toxic Epidermal Necrolysis (TEN)",
        "Pemfigus Vulgaris dengan Bulae Luas",
        "Skabies dengan Infeksi Sekunder Pyoderma",
        "Tinea Cruris / Corporis / Versicolor",
        "Psoriasis Vulgaris Plak Kronik",
        "Pruritus Senilis / Pruritus Generalisata",
        "Prurigo Nodularis / Prurigo Hebra",
        "Akne Vulgaris Grade IV (Kistik/Konglobata)",
        "Condyloma Acuminata (Kutil Kelamin HPV)",
        "Melanoma Maligna / Basal Cell Carcinoma Kulit"
    )

    // Gabungan seluruh penyakit (851 kasus terintegrasi) yang ada pada aplikasi
    val all851CaseChips: List<AvailableCaseChip> = (
        organCasesMap.values.flatten() + legacyDiagnoses.map { diag ->
            val dl = diag.lowercase()
            val isEmerg = dl.contains("stemi") || dl.contains("stroke") || dl.contains("asma") || dl.contains("syok") ||
                    dl.contains("kad") || dl.contains("eklamsia") || dl.contains("kejang") || dl.contains("dbd") ||
                    dl.contains("perdarahan") || dl.contains("abses") || dl.contains("glaukoma") || dl.contains("sjs") ||
                    dl.contains("krisis") || dl.contains("trauma") || dl.contains("pneumothorax") || dl.contains("anafilaksis")
            AvailableCaseChip(title = diag, isEmergency = isEmerg)
        }
    ).distinctBy { it.title.lowercase().trim() }

    val allDiagnoses: List<String> = all851CaseChips.map { it.title }

    fun getAvailableCasesForOrgan(organ: OrganSystem): List<AvailableCaseChip> {
        return if (organ == OrganSystem.RANDOM) {
            all851CaseChips
        } else {
            organCasesMap[organ] ?: all851CaseChips
        }
    }

    val allDrugs = listOf(
        // NSAID & Analgesik
        DrugItem("Ibuprofen 400mg", "nsaid antiinflamasi analgesik anti-nyeri demam", "Tablet", "Oral", "3x1"),
        DrugItem("Natrium Diklofenak 50mg", "nsaid antiinflamasi analgesik anti-nyeri", "Tablet", "Oral", "2x1"),
        DrugItem("Ketorolac 30mg", "nsaid analgesik anti-nyeri injeksi iv", "Ampul", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Piroksikam 20mg", "nsaid analgesik gout reumatik", "Kapsul", "Oral", "1x1"),
        DrugItem("Metamizole 1 gram", "nsaid analgesik antipiretik iv anti-nyeri", "Ampul", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Paracetamol 500mg", "analgesik antipiretik penurun demam anti-nyeri", "Tablet", "Oral", "3x1"),
        DrugItem("Paracetamol 1000mg IV", "analgesik antipiretik iv infus demam", "Infus", "Drip Infus", "Setiap 8 Jam"),
        DrugItem("Aspirin 160mg (Loading)", "nsaid antiplatelet ska stemi jantung", "Tablet", "Oral", "Dosis Tunggal"),
        DrugItem("Morphine 2.5mg IV", "analgesik opiat anti-nyeri berat stemi", "Ampul", "IV (Intravena)", "Saat Serangan"),
        DrugItem("Tramadol 50mg", "analgesik opiat anti-nyeri", "Kapsul", "Oral", "3x1"),
        DrugItem("Tramadol 100mg IV", "analgesik opiat iv anti-nyeri", "Ampul", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Meloxicam 15mg", "nsaid antiinflamasi analgesik oa gout", "Tablet", "Oral", "1x1"),
        DrugItem("Celecoxib 200mg", "nsaid cox2 analgesik anti-nyeri", "Kapsul", "Oral", "1x1"),

        // Gastrointestinal & Anti-Emetik & H2-Blocker / PPI
        DrugItem("Omeprazole 20mg", "ppi gaster lambung gerd gastritis", "Kapsul", "Oral", "2x1"),
        DrugItem("Omeprazole 40mg IV", "ppi gaster iv lambung perdarahan", "Vial", "IV (Intravena)", "1x1"),
        DrugItem("Lansoprazole 30mg", "ppi gaster lambung gerd", "Kapsul", "Oral", "1x1"),
        DrugItem("Ranitidine 150mg", "h2 blocker lambung gerd gastritis", "Tablet", "Oral", "2x1"),
        DrugItem("Ranitidine 50mg IV", "h2 blocker iv lambung gastritis", "Ampul", "IV (Intravena)", "Setiap 12 Jam"),
        DrugItem("Ondansetron 4mg", "antiemetik mual muntah", "Tablet", "Oral", "3x1"),
        DrugItem("Ondansetron 8mg IV", "antiemetik iv mual muntah gizi", "Ampul", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Metoklopramid 10mg IV", "antiemetik prokinetik mual muntah", "Ampul", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Sucralfate Sirup 500mg/5ml", "mukoprotektor gaster lambung maag", "Sirup", "Oral", "3x1"),
        DrugItem("Antasida Doen", "netralisir asam lambung maag", "Tablet", "Oral", "3x1"),
        DrugItem("Hyoscine N-butylbromide 10mg", "antispasmodik kolik lambung perut", "Tablet", "Oral", "3x1"),
        DrugItem("Hyoscine N-butylbromide 20mg IV", "antispasmodik iv kolik abdomen ureter", "Ampul", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Domperidon 10mg", "antiemetik prokinetik mual", "Tablet", "Oral", "3x1"),

        // Antiplatelet & Jantung / SKA / Aritmia / Antikoagulan
        DrugItem("Clopidogrel 300mg (Loading)", "antiplatelet ska stemi jantung", "Tablet", "Oral", "Dosis Tunggal"),
        DrugItem("Clopidogrel 75mg", "antiplatelet ska stemi stroke jantung", "Tablet", "Oral", "1x1"),
        DrugItem("ISDN 5mg (Isosorbid Dinitrat)", "antiangina nitrat jantung nyeri dada", "Tablet", "Sublingual", "Saat Serangan"),
        DrugItem("Atorvastatin 40mg", "antidislipidemia kolesterol stemi", "Tablet", "Oral", "1x1"),
        DrugItem("Simvastatin 20mg", "antidislipidemia kolesterol", "Tablet", "Oral", "1x1"),
        DrugItem("Nitrogliserin IV Drip", "antiangina nitrat iv jantung", "Vial", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Heparin Bolus 5000 UI IV", "antikoagulan heparin iv stemi emboli", "Vial", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Enoxaparin (Lovenox) 0.6ml SC", "antikoagulan lmwh stemi emboli", "Ampul", "SC (Subkutan)", "1x2"),
        DrugItem("Amiodaron 150mg IV", "antiaritmia af vf vt iv jantung", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Bisoprolol 5mg", "beta blocker antihipertensi jantung chf", "Tablet", "Oral", "1x1"),
        DrugItem("Digoxin 0.25mg", "inotropik af chf gagal jantung", "Tablet", "Oral", "1x1"),

        // Antihipertensi & Diuretik
        DrugItem("Nicardipine IV Drip", "antihipertensi iv krisis hipertensi stroke", "Vial", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Amlodipin 10mg", "antihipertensi ccb darah tinggi", "Tablet", "Oral", "1x1"),
        DrugItem("Amlodipin 5mg", "antihipertensi ccb darah tinggi", "Tablet", "Oral", "1x1"),
        DrugItem("Captopril 25mg", "antihipertensi acei krisis hipertensi", "Tablet", "Sublingual", "2x1"),
        DrugItem("Candesartan 8mg", "arb antihipertensi darah tinggi", "Tablet", "Oral", "1x1"),
        DrugItem("Valsartan 80mg", "arb antihipertensi darah tinggi", "Tablet", "Oral", "1x1"),
        DrugItem("Nifedipin 10mg", "antihipertensi ccb preeklamsia", "Tablet", "Oral", "3x1"),
        DrugItem("Furosemid 20mg IV", "diuretik edema chf gagal jantung", "Ampul", "IV (Intravena)", "1x1"),
        DrugItem("Furosemid 40mg", "diuretik edema chf oral", "Tablet", "Oral", "1x1"),
        DrugItem("Spironolakton 25mg", "diuretik hemat kalium chf sirosis", "Tablet", "Oral", "1x1"),

        // Anti-Kejang & Sedatif & Psikotropika
        DrugItem("Diazepam 5mg IV", "antikejang sedatif iv kejang demam", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Diazepam Suppositoria 5mg", "antikejang rektal anak kejang demam", "Suppositoria", "Rektal", "Saat Serangan"),
        DrugItem("Phenytoin 100mg IV", "antikejang epilepsi iv", "Ampul", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Midazolam 5mg IV", "sedatif antikejang iv", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Phenobarbital 100mg IV", "antikejang epilepsi iv anak", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Haloperidol 5mg IM/IV", "antipsikotik gelisah gaduh gelisah", "Ampul", "IM (Intramuskular)", "Saat Serangan"),
        DrugItem("Betahistin Mesilat 6mg", "antivertigo bppv pusing berputar", "Tablet", "Oral", "3x1"),

        // Obat Paru / Asma / Steroid / Anti-histamin
        DrugItem("Salbutamol Nebulizer 2.5mg", "bronkodilator asma ppok nebu inhalasi", "Inhaler/Nebulizer", "Inhalasi", "Saat Serangan"),
        DrugItem("Ipratropium Nebulizer 0.5mg", "bronkodilator antikolinergik asma ppok nebu", "Inhaler/Nebulizer", "Inhalasi", "Saat Serangan"),
        DrugItem("Metilprednisolon 62.5mg IV", "kortikosteroid antiinflamasi iv asma", "Vial", "IV (Intravena)", "Setiap 12 Jam"),
        DrugItem("Metilprednisolon 8mg", "kortikosteroid antiinflamasi oral", "Tablet", "Oral", "2x1"),
        DrugItem("Deksametason 5mg IV", "kortikosteroid iv antiinflamasi", "Ampul", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Acetylcysteine 200mg", "mukolitik batuk berdahak pso", "Kapsul", "Oral", "3x1"),
        DrugItem("Ambroxol 30mg", "mukolitik batuk berdahak", "Tablet", "Oral", "3x1"),
        DrugItem("Cetirizine 10mg", "antihistamin alergi gatal", "Tablet", "Oral", "1x1"),
        DrugItem("Epinefrin / Adrenalin 1mg IV/IM", "vasopresor anafilaksis henti jantung", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),

        // Antibiotik & Antijamur & Antiviral
        DrugItem("Ceftriaxon 1 gram IV", "antibiotik sefalosporin iv infeksi", "Vial", "IV (Intravena)", "1x2"),
        DrugItem("Cefotaxim 1 gram IV", "antibiotik sefalosporin iv", "Vial", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Metronidazol 500mg IV", "antibiotik anaerob iv peritonitis", "Infus", "Drip Infus", "Setiap 8 Jam"),
        DrugItem("Metronidazol 500mg", "antibiotik anaerob amebiasis oral", "Tablet", "Oral", "3x1"),
        DrugItem("Azitromisin 500mg", "antibiotik makrolida pneumonia", "Tablet", "Oral", "1x1"),
        DrugItem("Amoxicillin 500mg", "antibiotik penisilin ispa", "Tablet", "Oral", "3x1"),
        DrugItem("Amoxicillin-Klavulanat 625mg", "antibiotik penisilin ispa", "Tablet", "Oral", "3x1"),
        DrugItem("Ciprofloxacin 500mg", "antibiotik kuinolon isk saluran kemih", "Tablet", "Oral", "2x1"),
        DrugItem("Levofloxacin 500mg IV", "antibiotik kuinolon pneumonia iv", "Infus", "Drip Infus", "1x1"),
        DrugItem("Gentamisin 80mg IV", "antibiotik aminoglikosida iv", "Ampul", "IV (Intravena)", "1x1"),
        DrugItem("Fluconazole 150mg", "antijamur candidiasis", "Kapsul", "Oral", "Dosis Tunggal"),
        DrugItem("Acyclovir 400mg", "antiviral herpes zoster varicella", "Tablet", "Oral", "4x1"),
        DrugItem("Oseltamivir 75mg", "antiviral influenza flu", "Kapsul", "Oral", "2x1"),

        // Endokrin & Diabetik
        DrugItem("Insulin Reguler 10 UI IV", "insulin hipoglikemik kad diabetes", "Vial", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Insulin Lantus / Glargine 10 UI SC", "insulin basal diabetes t2", "Vial", "SC (Subkutan)", "1x1"),
        DrugItem("Dextrose 40% (D40) 25ml IV", "hipoglikemia dextrose iv cito", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Metformin 500mg", "antidiabetik oral diabetes dm2", "Tablet", "Oral", "3x1"),
        DrugItem("Glimepiride 2mg", "sulfonilurea antidiabetik dm2", "Tablet", "Oral", "1x1"),
        DrugItem("Propiltiourasil (PTU) 100mg", "antitiroid tirotoksikosis krisis tiroid", "Tablet", "Oral", "3x1"),

        // Resusitasi, Cairan & Elektrolit / Obgyn / Khusus
        DrugItem("Cairan Ringer Laktat (RL) 500ml", "cairan resusitasi kristaloid syok iv", "Infus", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Cairan NaCl 0.9% 500ml", "cairan resusitasi kristaloid iv kad", "Infus", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Cairan Dextrose 5% 500ml", "cairan pemeliharaan infus", "Infus", "Drip Infus", "Setiap 8 Jam"),
        DrugItem("Oksigen NRM 10-15 Lpm", "oksigenasi resusitasi hipoksia syok", "Inhaler/Nebulizer", "Inhalasi", "Segera (Cito/Stat)"),
        DrugItem("Oksigen Nasal Kanul 3 Lpm", "oksigenasi kanul nasal", "Inhaler/Nebulizer", "Inhalasi", "Segera (Cito/Stat)"),
        DrugItem("Asam Traneksamat 1g IV", "hemostatik perdarahan trauma syok", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("MgSO4 20% 4 gram IV", "antikejang magnesium eklamsia peb asma", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Oksitosin 10 UI IM/IV", "uterotonika perdarahan postpartum obgyn", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Methylergometrine 0.2mg", "uterotonika perdarahan obgyn", "Ampul", "IM (Intramuskular)", "Segera (Cito/Stat)"),
        DrugItem("KCl 7.46% 25ml IV Drip", "koreksi kalium hipokalemia", "Ampul", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Calcium Gluconate 10% 10ml IV", "kalsium hiperkalemia mgso4 antidote", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Zinc 20mg", "suplemen diare anak", "Tablet", "Oral", "1x1"),
        DrugItem("Oralit (ORS)", "rehidrasi diare rehidrasi", "Tablet", "Oral", "Bila Perlu (PRN)"),
        DrugItem("Tamsulosin 0.4mg", "alpha-blocker met urolitiasis batu ureter", "Kapsul", "Oral", "1x1"),
        DrugItem("Colchicine / Kolkisin 0.5mg", "antigout gout asam urat", "Tablet", "Oral", "Setiap 8 Jam"),
        DrugItem("Epinefrin 1:1000 0.3mg IM", "anafilaksis syok alergi epinefrin", "Ampul", "IM (Intramuskular)", "Segera (Cito/Stat)"),
        DrugItem("Propranolol 10mg", "beta blocker tirotoksikosis tiroid tremor", "Tablet", "Oral", "3x1"),
        DrugItem("Lugol Solution 5 tetes", "kalium yodida krisis tiroid", "Cairan", "Oral", "3x1"),
        DrugItem("Diphenhydramine 50mg IV/IM", "antihistamin iv anafilaksis gaduh gelisah", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Flunarizin 5mg", "antivertigo pusing vertigo", "Tablet", "Oral", "1x1"),
        DrugItem("Penicillin G Procaine 1.5 Juta UI IM", "antibiotik penisilin leptospirosis", "Vial", "IM (Intramuskular)", "1x1"),
        DrugItem("Doxycycline 100mg", "antibiotik tetracycline leptospirosis", "Kapsul", "Oral", "2x1"),
        DrugItem("Misoprostol 400mcg", "prostaglandin abortus perdarahan obgyn", "Tablet", "Sublingual", "Dosis Tunggal"),
        DrugItem("Risperidone 2mg", "antipsikotik skizofrenia", "Tablet", "Oral", "2x1"),
        DrugItem("Olanzapine 10mg IM/Oral", "antipsikotik gaduh gelisah skizofrenia", "Vial", "IM (Intramuskular)", "Segera (Cito/Stat)"),
        DrugItem("Levofloxacin Tetes Mata 0.5%", "antibiotik tetes mata ulkus kornea", "Tetes Mata", "Tetes Mata", "Setiap 1 Jam"),
        DrugItem("Atropin Tetes Mata 1%", "sikloplegik tetes mata ulkus kornea", "Tetes Mata", "Tetes Mata", "3x1"),
        DrugItem("Acyclovir 800mg", "antiviral herpes zoster dermatom", "Tablet", "Oral", "5x1"),
        DrugItem("Gabapentin 300mg", "antikonvulsan nyeri neuropatik herpes zoster", "Kapsul", "Oral", "1x1"),
        DrugItem("Diltiazem 10mg IV", "ccb antiaritmia rate control af rvr", "Ampul", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Vitamin A 200.000 IU", "vitamin dosis tinggi morbili campak anak", "Kapsul", "Oral", "Dosis Tunggal"),

        // Obat OAT (Tuberkulosis) & Antiparasit / Antimalaria
        DrugItem("Rifampisin 450mg", "oat tb tuberkulosis antibiotik", "Kapsul", "Oral", "1x1 (Pagi)"),
        DrugItem("Isoniazid (INH) 300mg", "oat tb tuberkulosis profilaksis", "Tablet", "Oral", "1x1"),
        DrugItem("Pirazinamid 500mg", "oat tb tuberkulosis", "Tablet", "Oral", "1x1"),
        DrugItem("Etambutol 400mg", "oat tb tuberkulosis", "Tablet", "Oral", "1x1"),
        DrugItem("Artesunat 60mg IV", "antimalaria falciparum berat iv", "Vial", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Klorokuin 250mg", "antimalaria vivax oral", "Tablet", "Oral", "3x1"),
        DrugItem("Primakuin 15mg", "antimalaria radikal hypnozoite", "Tablet", "Oral", "1x1"),

        // Dermatologi & Antijamur & Parasit Kulit
        DrugItem("Krim Permetrin 5%", "skabies kudis anti-parasit topikal", "Salep/Krim", "Topikal", "1x1 (Malam Hari)"),
        DrugItem("Ketokonazol Krim 2%", "antijamur topikal tinea panu", "Salep/Krim", "Topikal", "2x1"),
        DrugItem("Krim Hidrokortison 1%", "kortikosteroid topikal gatal dermatitis", "Salep/Krim", "Topikal", "2x1"),
        DrugItem("Salep Mupirosin 2%", "antibiotik topikal pyoderma luka", "Salep/Krim", "Topikal", "3x1"),

        // Neuro / Spesifik Organs & Antidotum
        DrugItem("Levodopa / Benserazide 100/25mg", "antiparkinson dopaminergic parkinson", "Tablet", "Oral", "3x1"),
        DrugItem("Pyridostigmine 60mg", "antikolinesterase myasthenia gravis", "Tablet", "Oral", "3x1"),
        DrugItem("Mannitol 20% 200ml IV", "diuretik osmotik edema serebri tkb", "Infus", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Allopurinol 100mg", "antigout asam urat hiperurikemia", "Tablet", "Oral", "1x1"),
        DrugItem("Febuxostat 40mg", "antigout asam urat", "Tablet", "Oral", "1x1"),

        // Psikiatri & Sedatif
        DrugItem("Sertraline 50mg", "ssri antidepresan depresi panik ocd", "Tablet", "Oral", "1x1"),
        DrugItem("Fluoxetine 20mg", "ssri antidepresan depresi", "Kapsul", "Oral", "1x1"),
        DrugItem("Alprazolam 0.5mg", "benzodiazepin anksiolitik panik cemas", "Tablet", "Oral", "2x1"),
        DrugItem("Lorazepam 2mg", "benzodiazepin sedatif gaduh gelisah", "Tablet", "Oral", "1x1"),
        DrugItem("Clozapine 25mg", "antipsikotik atipikal skizofrenia refrakter", "Tablet", "Oral", "2x1"),

        // Oftalmologi & THT Spesifik
        DrugItem("Timolol Tetes Mata 0.5%", "beta-blocker tetes mata glaukoma", "Tetes Mata", "Tetes Mata", "2x1"),
        DrugItem("Asetazolamid 250mg", "inhibitor karbonik anhydrase glaukoma", "Tablet", "Oral", "3x1"),
        DrugItem("Tetes Telinga Ofloxacin 0.3%", "antibiotik tetes telinga oma otitis", "Tetes Telinga", "Tetes Telinga", "2x2 Tetes"),

        // Reumatologi & Imunologi & Intravitreal
        DrugItem("Prednisolon 5mg", "kortikosteroid lepra enl enflamasi lupus", "Tablet", "Oral", "3x1"),
        DrugItem("Mycophenolate Mofetil (MMF) 500mg", "immunosupresan nefritis lupus sle", "Tablet", "Oral", "2x2"),
        DrugItem("Hydroxychloroquine (HCQ) 200mg", "imunomodulator sle reumatoid", "Tablet", "Oral", "1x1"),
        DrugItem("Vancomycin 1g IV / Intravitreal", "antibiotik iv intravitreal meningitis endoftalmitis", "Vial", "IV (Intravena)", "Setiap 12 Jam"),
        DrugItem("Ceftazidime 1g IV / Intravitreal", "antibiotik iv intravitreal endoftalmitis", "Vial", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Epinefrin Nebulizer 1:1000", "nebulisasi bronkodilator vasokonstriktor croup laringitis", "Inhaler/Nebulizer", "Inhalasi", "Saat Serangan"),

        // Sediaan Pediatrik (Sirup, Drops, Suspensi, Saset)
        DrugItem("Paracetamol Sirup 120mg/5ml", "analgesik antipiretik demam anak sirup", "Sirup", "Oral", "3x1 Cth"),
        DrugItem("Paracetamol Drops 100mg/ml", "analgesik antipiretik demam bayi drops tetes", "Drops (Tetes)", "Oral", "3x0.6ml"),
        DrugItem("Ibuprofen Sirup 100mg/5ml", "nsaid antipiretik analgesik anak sirup suspensi", "Sirup/Suspensi", "Oral", "3x1 Cth"),
        DrugItem("Amoxicillin Sirup Kering 125mg/5ml", "antibiotik ispa anak sirup kering", "Sirup Kering", "Oral", "3x1 Cth"),
        DrugItem("Cefadroxil Sirup Kering 125mg/5ml", "antibiotik sefalosporin anak sirup kering", "Sirup Kering", "Oral", "2x1 Cth"),
        DrugItem("Erythromycin Sirup 200mg/5ml", "antibiotik makrolida anak sirup", "Sirup", "Oral", "4x1 Cth"),
        DrugItem("Ambroxol Sirup 15mg/5ml", "mukolitik batuk berdahak anak sirup", "Sirup", "Oral", "3x1 Cth"),
        DrugItem("Cetirizine Sirup 5mg/5ml", "antihistamin alergi gatal anak sirup", "Sirup", "Oral", "1x1 Cth"),
        DrugItem("Domperidone Sirup 5mg/5ml", "antiemetik mual muntah anak sirup", "Sirup", "Oral", "3x1 Cth"),
        DrugItem("Zinc Sirup 20mg/5ml", "suplemen diare anak sirup", "Sirup", "Oral", "1x1 Cth"),
        DrugItem("Lactulose Sirup 3.3g/5ml", "laksatif sembelit konstipasi sirup", "Sirup", "Oral", "2x1 Cth"),
        DrugItem("Antasida Doen Suspensi", "netralisir asam lambung maag suspensi cair", "Suspensi", "Oral", "3x1 Cth"),
        DrugItem("Sucralfate Suspensi 500mg/5ml", "mukoprotektor lambung gastritis suspensi", "Suspensi", "Oral", "4x1 Cth"),
        DrugItem("Diosmectite (Smecta) Saset", "antidiare absorben saset serbuk", "Serbuk/Saset", "Oral", "3x1 Saset"),

        // Gastrointestinal & Rektal (Suppositoria, Enema, Gel)
        DrugItem("Loperamide 2mg", "antidiare diare akut", "Tablet", "Oral", "Bila Perlu (PRN)"),
        DrugItem("Bisacodyl 10mg Suppositoria", "laksatif sembelit konstipasi suppositoria rektal", "Suppositoria", "Rektal", "1x1 Rektal"),
        DrugItem("Bisacodyl 5mg Tablet", "laksatif sembelit konstipasi tablet", "Tablet", "Oral", "1x1"),
        DrugItem("Microlax Enema 5ml", "laksatif sembelit enema gel rektal tube", "Enema / Gel Rektal", "Rektal", "1x1 Tube"),
        DrugItem("Ursodeoxycholic Acid (UDCA) 250mg", "hepatoprotektor batu empedu sirosis", "Kapsul", "Oral", "2x1"),
        DrugItem("Rebamipide 100mg", "mukoprotektor lambung gastritis ulkus", "Tablet", "Oral", "3x1"),
        DrugItem("Esomeprazole 40mg IV", "ppi lambung gerd ppi iv", "Vial", "IV (Intravena)", "1x1"),
        DrugItem("Pantoprazole 40mg IV", "ppi lambung perdarahan ppi iv", "Vial", "IV (Intravena)", "1x1"),

        // Respiratori & Alergi (Inhaler, Nasal Spray, Nebulizer, Semprot Hidung)
        DrugItem("Fluticasone/Salmeterol (Seretide) 50/250mcg", "inhaler asma ppok mdi inhalasi", "MDI Inhaler", "Inhalasi", "2x1 Isapan"),
        DrugItem("Budesonide Nebulizer 0.25mg/ml (Pulmicort)", "kortikosteroid inhalasi nebu asma ppok croup", "Respules Nebulizer", "Inhalasi", "2x1 Respule"),
        DrugItem("Budesonide/Formoterol (Symbicort Turbuhaler)", "inhaler kombinasi asma ppok dpi", "DPI Inhaler", "Inhalasi", "2x1 Isapan"),
        DrugItem("Oxymetazoline Nasal Spray 0.05%", "dekongestan hidung tersumbat nasal spray semprot hidung", "Nasal Spray (Semprot Hidung)", "Nasal", "2x2 Semprot"),
        DrugItem("Fluticasone Furoate Nasal Spray", "kortikosteroid nasal rhinitis alergi nasal spray semprot hidung", "Nasal Spray (Semprot Hidung)", "Nasal", "1x2 Semprot"),
        DrugItem("Loratadine 10mg", "antihistamin rhinitis alergi gatal", "Tablet", "Oral", "1x1"),
        DrugItem("CTM (Chlorpheniramine) 4mg", "antihistamin sedatif alergi gatal", "Tablet", "Oral", "3x1"),
        DrugItem("Codeine 10mg", "antitusif batuk kering opiat", "Tablet", "Oral", "3x1"),
        DrugItem("Dextromethorphan HBr 15mg", "antitusif batuk kering", "Tablet", "Oral", "3x1"),

        // Kardiovaskular & Antikoagulan (Patch, Prefilled Syringe, Drip)
        DrugItem("Hydrochlorothiazide (HCT) 25mg", "diuretik thiazide antihipertensi", "Tablet", "Oral", "1x1"),
        DrugItem("Ramipril 5mg", "acei antihipertensi gagal jantung", "Tablet", "Oral", "1x1"),
        DrugItem("Lisinopril 10mg", "acei antihipertensi", "Tablet", "Oral", "1x1"),
        DrugItem("Losartan 50mg", "arb antihipertensi", "Tablet", "Oral", "1x1"),
        DrugItem("Telmisartan 80mg", "arb antihipertensi", "Tablet", "Oral", "1x1"),
        DrugItem("Nitroglycerin Transdermal Patch 5mg", "antiangina nitrat tempel patch transdermal", "Patch Transdermal", "Transdermal", "1x1 Patch"),
        DrugItem("Warfarin 2mg", "antikoagulan oral af dvt emboli", "Tablet", "Oral", "1x1"),
        DrugItem("Rivaroxaban 15mg", "noac antikoagulan dvt af", "Tablet", "Oral", "1x1"),
        DrugItem("Fondaparinux 2.5mg SC", "antikoagulan antithrombotik prefilled syringe sc", "Prefilled Syringe", "SC (Subkutan)", "1x1"),
        DrugItem("Norepinephrine (Vascon) 4mg IV Drip", "vasopresor syok septik syok distributif iv drip", "Ampul", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Dopamine 200mg IV Drip", "inotropik vasopresor syok kardiogenik iv drip", "Ampul", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Dobutamine 250mg IV Drip", "inotropik positif syok kardiogenik chf iv drip", "Ampul", "Drip Infus", "Segera (Cito/Stat)"),

        // Antiinfeksi Berat & Antibiotik Kunci (Vial, Kapsul, Sirup Kering)
        DrugItem("Cefixime 100mg", "antibiotik sefalosporin oral", "Kapsul", "Oral", "2x1"),
        DrugItem("Cefixime Sirup Kering 100mg/5ml", "antibiotik sefalosporin anak sirup kering", "Sirup Kering", "Oral", "2x1 Cth"),
        DrugItem("Clindamycin 300mg", "antibiotik anaerob abses kulit", "Kapsul", "Oral", "3x1"),
        DrugItem("Cotrimoxazole 480mg", "antibiotik sulfanamida isk pneumonia", "Tablet", "Oral", "2x1"),
        DrugItem("Cotrimoxazole Sirup 240mg/5ml", "antibiotik sulfanamida anak sirup", "Sirup", "Oral", "2x1 Cth"),
        DrugItem("Meropenem 1 gram IV", "antibiotik karbapenem sepsiss iv", "Vial", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Piperacillin/Tazobactam 4.5g IV", "antibiotik pso pneumonia sepsis iv", "Vial", "IV (Intravena)", "Setiap 6 Jam"),
        DrugItem("Itraconazole 100mg", "antijamur sistemik candidiasis tinea", "Kapsul", "Oral", "2x1"),
        DrugItem("Valacyclovir 500mg", "antiviral herpes zoster HSV", "Tablet", "Oral", "3x1"),

        // Neurologi, Psikofarmaka & Nyeri Neuropatik
        DrugItem("Carbamazepine 200mg", "antikonvulsan epilepsi trigeminal neuralgia", "Tablet", "Oral", "2x1"),
        DrugItem("Sodium Valproate (Depakene) Sirup 250mg/5ml", "antikonvulsan epilepsi anak sirup", "Sirup", "Oral", "2x1 Cth"),
        DrugItem("Sodium Valproate 250mg", "antikonvulsan epilepsi bipolar", "Tablet", "Oral", "2x1"),
        DrugItem("Pregabalin 75mg", "analgesik neuropatik gabapentinoid", "Kapsul", "Oral", "2x1"),
        DrugItem("Amitriptyline 25mg", "antidepresan trisiklik tth nyeri neuropatik", "Tablet", "Oral", "1x1 (Malam)"),
        DrugItem("Eperisone HCl 50mg", "muscle relaxant pelemas otot spasme", "Tablet", "Oral", "3x1"),
        DrugItem("Trihexyphenidyl (THP) 2mg", "antikolinergik parkinsonism eps antipsikotik", "Tablet", "Oral", "2x1"),
        DrugItem("Chlorpromazine 100mg", "antipsikotik tipikal skizofrenia", "Tablet", "Oral", "2x1"),
        DrugItem("Quetiapine 200mg", "antipsikotik atipikal skizofrenia bipolar", "Tablet", "Oral", "1x1"),

        // Oftalmologi, Otology & Dermatologi (Mata, Telinga, Salep, Krim, Gel, Bedak, Shampo)
        DrugItem("Tobramycin/Dexamethasone Tetes Mata (TobraDex)", "antibiotik steroid tetes mata konjungtivitis uveitis", "Tetes Mata", "Tetes Mata", "4x1 Tetes"),
        DrugItem("Chloramphenicol Salep Mata 1%", "antibiotik salep mata erosi kornea konjungtivitis", "Salep Mata", "Topikal Mata", "3x1 Aplikasi"),
        DrugItem("Artificial Tears Tetes Mata", "air mata buatan dry eyes tetes mata", "Tetes Mata", "Tetes Mata", "4x1 Tetes"),
        DrugItem("Latanoprost Tetes Mata 0.005%", "prostaglandin glaukoma tetes mata", "Tetes Mata", "Tetes Mata", "1x1 (Malam)"),
        DrugItem("Chloramphenicol Tetes Telinga 3%", "antibiotik tetes telinga otitis eksterna oma", "Tetes Telinga", "Tetes Telinga", "3x2 Tetes"),
        DrugItem("Carboperoxide Tetes Telinga (Serumenolitik)", "pelunak serumen tetes telinga", "Tetes Telinga", "Tetes Telinga", "3x3 Tetes"),
        DrugItem("Betamethasone Valerate Krim 0.1%", "kortikosteroid topikal dermatitis krim gatal", "Krim", "Topikal", "2x1"),
        DrugItem("Desoximetasone Krim 0.25%", "kortikosteroid topikal kuat dermatitis psoriasis", "Krim", "Topikal", "2x1"),
        DrugItem("Bioplacenton Gel", "placenta extract neomycin gel luka bakar galo", "Gel", "Topikal", "3x1"),
        DrugItem("Silver Sulfadiazine Salep 1% (Burnazin)", "antibiotik salep luka bakar", "Salep", "Topikal", "2x1"),
        DrugItem("Ketoconazole Shampo 2%", "antijamur shampo ketombe tinea versicolor", "Shampo Medicated", "Topikal", "2x Seminggu"),
        DrugItem("Bedak Salicyl 2% (Salisilat)", "bedak gatal biang keringat gatal", "Bedak/Serbuk", "Topikal", "Bila Perlu (PRN)"),
        DrugItem("Calamine Lotion", "lotion antipruritus gatal cacar varicella", "Lotion", "Topikal", "3x1"),

        // Endokrinologi & Metabolik Tambahan
        DrugItem("Levothyroxine 100mcg", "hormon tiroid hipotiroidisme hashimoto", "Tablet", "Oral", "1x1 (Pagi)"),
        DrugItem("Methimazole 10mg", "antitiroid hipertiroidisme graves", "Tablet", "Oral", "1x1"),
        DrugItem("Propylthiouracil (PTU) 100mg", "antitiroid hipertiroidisme krisis tiroid", "Tablet", "Oral", "3x1"),
        DrugItem("Empagliflozin 10mg", "sglt2 inhibitor diabetes mellitus tipe 2 ckd", "Tablet", "Oral", "1x1"),
        DrugItem("Sitagliptin 50mg", "dpp4 inhibitor diabetes mellitus tipe 2", "Tablet", "Oral", "1x1"),
        DrugItem("Pioglitazone 30mg", "thiazolidinedione diabetes mellitus tipe 2", "Tablet", "Oral", "1x1"),
        DrugItem("Allopurinol 100mg", "inhibitor xantin oksidase asam urat gout kronis", "Tablet", "Oral", "1x1"),
        DrugItem("Febuxostat 40mg", "hipourisemia gout hiperurisemia", "Tablet", "Oral", "1x1"),
        DrugItem("Fenofibrate 160mg", "fibrat hipertrigliseridemia dislipidemia", "Kapsul", "Oral", "1x1"),
        DrugItem("Gemfibrozil 300mg", "fibrat hipertrigliseridemia", "Kapsul", "Oral", "2x1"),
        DrugItem("Calcitriol 0.25mcg", "vitamin d3 aktif hiperparatiroidisme ckd renal", "Kapsul", "Oral", "1x1"),
        DrugItem("Kalsium Karbonat (CaCO3) 500mg", "suplemen kalsium pengikat fosfat ckd", "Tablet", "Oral", "3x1"),

        // Obstetri & Ginekologi (Obgyn) & Kesehatan Reproduksi
        DrugItem("Oksitosin (Oxytocin) 10 IU/ml Ampul", "uterotonika induksi persalinan pph iv im", "Ampul (Injeksi)", "IM (Intramuskular)", "Dosis Tunggal"),
        DrugItem("Metilergometrin (Methylergometrine) 0.2mg", "uterotonika pph perdarahan pasca salin ampul", "Ampul (Injeksi)", "IM (Intramuskular)", "Dosis Tunggal"),
        DrugItem("Metilergometrin 0.125mg Tablet", "uterotonika pph perdarahan pasca salin tablet", "Tablet", "Oral", "3x1"),
        DrugItem("Magnesium Sulfat (MgSO4) 40% 25ml", "antikejang preeklampsia eklampsia iv drip", "Vial (Injeksi)", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Misoprostol 200mcg Tablet", "prostaglandin e1 induksi pph abortus", "Tablet", "Sublingual", "Dosis Tunggal"),
        DrugItem("Nifedipine 10mg", "tokolitik cegah partus prematurus antihipertensi", "Tablet", "Oral", "3x1"),
        DrugItem("Sulfas Ferosus 300mg + Asam Folat (Fe)", "suplemen zat besi anemia kehamilan ibu hamil", "Tablet", "Oral", "1x1"),
        DrugItem("Nystatin Ovula 100.000 IU", "antijamur vagina candidiasis vulvovaginalis ovula", "Ovula (Vaginal)", "Vaginal", "1x1 (Malam)"),

        // Psikiatri, Neuropsikiatri & Sedasi Khusus
        DrugItem("Diazepam Suppositoria 5mg/10mg (Stesolid)", "antikejang rektal kejang demam anak suppositoria", "Suppositoria", "Rektal", "Bila Perlu (PRN)"),
        DrugItem("Midazolam 5mg/5ml Ampul", "sedatif antikejang pra-medikasi iv im", "Ampul (Injeksi)", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Sertraline 50mg", "ssri antidepresan panic disorder depresi", "Tablet", "Oral", "1x1"),
        DrugItem("Fluoxetine 20mg", "ssri antidepresan bulimia obsesif kompulsif", "Kapsul", "Oral", "1x1"),
        DrugItem("Risperidone 2mg", "antipsikotik atipikal skizofrenia bipolar", "Tablet", "Oral", "2x1"),
        DrugItem("Olanzapine 10mg", "antipsikotik atipikal skizofrenia mania", "Tablet", "Oral", "1x1"),
        DrugItem("Lorazepam 2mg", "benzodiazepin ansiolitik gaduh gelisah psikotik", "Tablet", "Oral", "2x1"),
        DrugItem("Clobazam 10mg", "benzodiazepin ansiolitik adjoin antikejang", "Tablet", "Oral", "2x1"),

        // Tropik, Paru & Antiinfeksi Tambahan (OAT & Antimalaria)
        DrugItem("Oseltamivir 75mg", "antiviral influenza flu burung h1n1", "Kapsul", "Oral", "2x1"),
        DrugItem("Acyclovir 400mg", "antiviral herpes zoster varicella hsv", "Tablet", "Oral", "5x1"),
        DrugItem("Acyclovir Salep 5%", "antiviral topikal herpes zoster labialis", "Salep", "Topikal", "5x1"),
        DrugItem("Pyrazinamide 500mg", "oat lini pertama tuberkulosis tb piritzinamid", "Tablet", "Oral", "1x3"),
        DrugItem("Ethambutol 400mg", "oat lini pertama tuberkulosis tb etambutol", "Tablet", "Oral", "1x3"),
        DrugItem("Streptomycin 1 gram Vial", "oat lini pertama tb kategori 2 im", "Vial (Injeksi)", "IM (Intramuskular)", "1x1"),
        DrugItem("Artemether/Lumefantrine (Coartem)", "antimalaria act falciparum vivax", "Tablet", "Oral", "2x1"),
        DrugItem("Dihydroartemisinin-Piperaquine (DHP)", "antimalaria act program Kemenkes", "Tablet", "Oral", "1x3"),

        // Nefrologi, Urologi & Elektrolit Khusus
        DrugItem("Tamsulosin 0.4mg", "alpha blocker bph pembesaran prostat batu ureter", "Kapsul", "Oral", "1x1 (Malam)"),
        DrugItem("Finasteride 5mg", "5-alpha reductase inhibitor bph prostat", "Tablet", "Oral", "1x1"),
        DrugItem("Spironolactone 25mg", "diuretik hemat kalium sirosis ascites chf", "Tablet", "Oral", "1x1"),
        DrugItem("Sodium Bicarbonate (Bicnat) 500mg", "alkalisir asidosis metabolik ckd", "Tablet", "Oral", "3x1"),
        DrugItem("Calcium Resonium (Kayexalate) Saset", "penurun kalium hiperkalemia ckd saset", "Saset / Puyer", "Oral", "3x1 Saset"),
        DrugItem("Infus D5% 1/4 NS (KA-EN 3B)", "cairan rehidrasi pemeliharaan anak infus", "Botol Infus / Drip", "Drip Infus", "Sesuai Kebutuhan"),

        // Kardiologi & Pembuluh Darah Lanjutan
        DrugItem("Sacubitril/Valsartan (Entresto) 50mg", "arni gagal jantung chf hfref", "Tablet", "Oral", "2x1"),
        DrugItem("Ivabradine 5mg", "inhibitor if kanal sinus gagal jantung angina", "Tablet", "Oral", "2x1"),
        DrugItem("Diltiazem 30mg", "ccb non-dihidropiridin angina hipertensi af", "Tablet", "Oral", "3x1"),
        DrugItem("Verapamil 80mg", "ccb non-dihidropiridin svt hipertensi angina", "Tablet", "Oral", "3x1"),
        DrugItem("Clonidine 0.15mg", "alpha-2 agonis krisis hipertensi", "Tablet", "Oral", "3x1"),
        DrugItem("Hydralazine 25mg", "vasodilator direk hipertensi kehamilan preeklampsia", "Tablet", "Oral", "3x1"),
        DrugItem("Sodium Nitroprusside 50mg IV", "vasodilator krisis hipertensi iv drip", "Vial (Injeksi)", "Drip Infus", "Segera (Cito/Stat)"),
        DrugItem("Sildenafil 50mg", "pde5 inhibitor hipertensi pulmonal pph", "Tablet", "Oral", "3x1"),

        // Pulmonologi & Respiratori Lanjutan
        DrugItem("Tiotropium (Spiriva Respimat) 2.5mcg", "lama anticholinergic ppok bronkodilator", "Inhaler MDI", "Inhalasi", "1x2 Isapan"),
        DrugItem("Montelukast 10mg", "leukotriene receptor antagonist asma rhinitis", "Tablet", "Oral", "1x1 (Malam)"),
        DrugItem("Acetylcysteine 600mg Effervescent", "mukolitik ekspektoran antioksidanppok effervescent", "Saset / Puyer", "Oral", "1x1"),
        DrugItem("Erdosteine 300mg", "mukolitik batuk berdahak bronkitis", "Kapsul", "Oral", "2x1"),
        DrugItem("Aminophylline 240mg Ampul", "bronkodilator teofilin IV asma eksaserbasi", "Ampul (Injeksi)", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Terbutaline 0.5mg/ml Ampul", "beta-2 agonis iv sc asma bronkospasme", "Ampul (Injeksi)", "SC (Subkutan)", "Saat Serangan"),

        // Gastroenterologi & Hepatologi Lanjutan
        DrugItem("Octreotide 0.1mg/ml Ampul", "somatostatin analog perdarahan varises esofagus", "Ampul (Injeksi)", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Mesalazine (5-ASA) 500mg", "antiinflamasi usus ibd colitis ulcerosa crohn", "Tablet", "Oral", "3x2"),
        DrugItem("Mesalazine Suppositoria 1 gram", "antiinflamasi proktitis ulcerative colitis rektal", "Suppositoria", "Rektal", "1x1 (Malam)"),
        DrugItem("Azathioprine 50mg", "immunosupresan ibd autoimmune hepatitis", "Tablet", "Oral", "1x1"),
        DrugItem("Docusate Sodium 100mg", "pelunak feses konstipasi sembelit", "Kapsul", "Oral", "2x1"),
        DrugItem("Ondansetron Sirup 4mg/5ml", "antiemetik mual muntah anak kemoterapi sirup", "Sirup", "Oral", "3x1 Cth"),
        DrugItem("Metoclopramide Drops 0.1mg/drop", "antiemetik bayi mual muntah drops tetes", "Drops (Tetes Oral)", "Oral", "3x5 Tetes"),
        DrugItem("Pancreatin (Enzim Pencernaan)", "enzim pankreas insufisiensi pankreatitis", "Kapsul", "Oral", "3x1 (Bersama Makan)"),

        // Neurologi, Nyeri Kepala & Anestesi / Emergensi
        DrugItem("Levetiracetam 500mg", "antikejang epilepsi kejang fokal", "Tablet", "Oral", "2x1"),
        DrugItem("Oxcarbazepine 300mg", "antikejang epilepsi trigeminal neuralgia", "Tablet", "Oral", "2x1"),
        DrugItem("Sumatriptan 50mg", "agonist 5-HT1 migrain akut abortif", "Tablet", "Oral", "Saat Serangan"),
        DrugItem("Ergotamine/Caffeine (Ericaf) 1mg/100mg", "anti-migrain vasokonstriktor pembuluh otak", "Tablet", "Oral", "Saat Serangan"),
        DrugItem("Citicoline 500mg", "neuroprotektor stroke iskemik trauma kepala", "Tablet", "Oral", "2x1"),
        DrugItem("Citicoline 500mg/4ml Ampul", "neuroprotektor iv im stroke akut trauma", "Ampul (Injeksi)", "IV (Intravena)", "2x1"),
        DrugItem("Donepezil 5mg", "acetylcholinesterase inhibitor demensia alzheimer", "Tablet", "Oral", "1x1 (Malam)"),
        DrugItem("Memantine 10mg", "nmda receptor antagonist demensia alzheimer sedang-berat", "Tablet", "Oral", "1x1"),
        DrugItem("Baclofen 10mg", "pelemas otot spastisitas pasca stroke trauma medula", "Tablet", "Oral", "3x1"),
        DrugItem("Ketamine 50mg/ml Vial", "anestetik disosiatif analgesik emergensi", "Vial (Injeksi)", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Propofol 1% (10mg/ml) Ampul", "anestetik sedasi cepat intubasi ventilator", "Ampul (Injeksi)", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Atropine Sulfate 0.25mg/ml Ampul", "antikolinergik bradikardia asistol keracunan organofosfat", "Ampul (Injeksi)", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Fentanyl 0.05mg/ml Ampul", "analgesik opioid kuat nyeri berat intraoperatif", "Ampul (Injeksi)", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Morphine Sulfate 10mg Ampul", "analgesik opioid edema paru akut nyeri kanker", "Ampul (Injeksi)", "IV (Intravena)", "Setiap 4 Jam"),

        // Reumatologi, Imunologi & Endokrin Lanjutan
        DrugItem("Methotrexate 2.5mg", "dmard rheumatoid arthritis psoriasis lupus", "Tablet", "Oral", "1x Seminggu"),
        DrugItem("Leflunomide 20mg", "dmard immunomodulator rheumatoid arthritis", "Tablet", "Oral", "1x1"),
        DrugItem("Sulfasalazine 500mg", "dmard spondiloartritis ra ibd", "Tablet", "Oral", "2x2"),
        DrugItem("Colchicine 0.5mg", "anti-gout serangan gout akut", "Tablet", "Oral", "Saat Serangan"),
        DrugItem("Triamcinolone Acetonide 10mg/ml Vial", "kortikosteroid injeksi intraartikular keloid", "Vial (Injeksi)", "IM (Intramuskular)", "Dosis Tunggal"),
        DrugItem("Hydrocortisone 100mg Vial", "kortikosteroid iv krisis adrenal syok anafilaksis", "Vial (Injeksi)", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Semaglutide 0.5mg Subkutan", "glp-1 receptor agonist diabetes obesity", "Prefilled Syringe", "SC (Subkutan)", "1x Seminggu"),

        // Antimikroba, Antijamur & Antiviral Lanjutan
        DrugItem("Fosfomycin 3 gram Saset", "antibiotik dosis tunggal sistitis isk akut saset", "Saset / Puyer", "Oral", "Dosis Tunggal"),
        DrugItem("Nitrofurantoin 100mg", "antibiotik antiseptik saluran kemih isk", "Kapsul", "Oral", "2x1"),
        DrugItem("Fluconazole 150mg", "antijamur oral candidiasis vaginitis tinea", "Kapsul", "Oral", "Dosis Tunggal"),
        DrugItem("Fluconazole 2mg/ml Infus 100ml", "antijamur iv candidiasis sistemik meningitis kriptokokus", "Botol Infus / Drip", "Drip Infus", "1x1"),
        DrugItem("Voriconazole 200mg", "antijamur aspergillosis sistemik", "Tablet", "Oral", "2x1"),
        DrugItem("Colistin (Colistimethate) 1 Juta IU Vial", "antibiotik mrsa mdr pseudomonas acinetobacter", "Vial (Injeksi)", "IV (Intravena)", "Setiap 8 Jam"),

        // Pediatri, Vitamin & Suplemen Khusus
        DrugItem("Oralit Saset (Gula Garam Rehidrasi)", "oral rehydration salt diare rehidrasi saset", "Saset / Puyer", "Oral", "Setiap BAB Cair"),
        DrugItem("Zinc Drops 10mg/ml", "suplemen zinc bayi diare drops tetes", "Drops (Tetes Oral)", "Oral", "1x1ml"),
        DrugItem("Vitamin D3 Drops 400 IU/drop", "vitamin d3 bayi anak pertumbuhan tetes", "Drops (Tetes Oral)", "Oral", "1x1 Drop"),
        DrugItem("Vitamin D3 5000 IU", "vitamin d3 dosis tinggi defisiensi autoimmune", "Kapsul", "Oral", "1x1"),
        DrugItem("Vitamin K1 (Phytomenadione) 2mg/ml Ampul", "vitamin k1 pabi hdnf perdarahan bayi baru lahir", "Ampul (Injeksi)", "IM (Intramuskular)", "Dosis Tunggal"),
        DrugItem("Vitamin A 200.000 IU (Kapsul Merah)", "vitamin a suplementasi balita xerophthalmia", "Kapsul", "Oral", "Dosis Tunggal"),
        DrugItem("Vitamin A 100.000 IU (Kapsul Biru)", "vitamin a suplementasi bayi 6-11 bulan", "Kapsul", "Oral", "Dosis Tunggal"),
        DrugItem("Iron Drops (Ferrous Sulfate) 15mg Fe/ml", "suplemen zat besi bayi anemia mikrositik drops", "Drops (Tetes Oral)", "Oral", "1x1ml"),

        // Oftalmologi, THT & Dermatologi Lanjutan
        DrugItem("Prednisolone Acetate Tetes Mata 1%", "kortikosteroid tetes mata uveitis keratitis", "Tetes Mata", "Tetes Mata", "4x1 Tetes"),
        DrugItem("Atropine Sulfate Tetes Mata 1%", "mideriatik sikloplegik uveitis iritis tetes mata", "Tetes Mata", "Tetes Mata", "2x1 Tetes"),
        DrugItem("Polymyxin B/Neomycin Tetes Telinga", "antibiotik tetes telinga otitis eksterna supuratif", "Tetes Telinga", "Tetes Telinga", "3x3 Tetes"),
        DrugItem("Hydrogen Peroxide 3% Ear Drops", "serumenolitik cuci telinga otitis eksterna", "Tetes Telinga", "Tetes Telinga", "2x5 Tetes"),
        DrugItem("Triamcinolone Nasal Spray", "kortikosteroid nasal spray rhinitis alergika", "Nasal Spray (Semprot Hidung)", "Nasal", "1x2 Semprot"),
        DrugItem("Permethrin Krim 5% (Scabimite)", "skabisida kudis skabies krem topikal", "Krim", "Topikal", "1x Pemakaian (8-12 Jam)"),
        DrugItem("Crotamiton Krim 10%", "antipruritus skabisida gatal", "Krim", "Topikal", "2x1"),
        DrugItem("Mupirocin Salep 2%", "antibiotik topikal impetigo folikulitis", "Salep", "Topikal", "3x1"),

        // Lengkap: Golongan Anti Nyeri & Analgesik (NSAID, Opioid, Kombinasi & Neuropatik)
        DrugItem("Meloxicam 7.5mg", "nsaid analgesik antiinflamasi oa ra gout", "Tablet", "Oral", "1x1"),
        DrugItem("Meloxicam 15mg", "nsaid analgesik antiinflamasi osteoartritis", "Tablet", "Oral", "1x1"),
        DrugItem("Celecoxib 100mg", "nsaid selektif cox2 analgesik aman lambung", "Kapsul", "Oral", "2x1"),
        DrugItem("Celecoxib 200mg", "nsaid selektif cox2 analgesik ra oa", "Kapsul", "Oral", "1x1"),
        DrugItem("Etoricoxib 60mg", "nsaid selektif cox2 analgesik osteoartritis", "Tablet", "Oral", "1x1"),
        DrugItem("Etoricoxib 90mg", "nsaid selektif cox2 analgesik reumatoid gout", "Tablet", "Oral", "1x1"),
        DrugItem("Etoricoxib 120mg", "nsaid selektif cox2 analgesik gout akut", "Tablet", "Oral", "1x1"),
        DrugItem("Dexketoprofen 25mg", "nsaid analgesik cepat nyeri akut dismenorea", "Tablet", "Oral", "3x1"),
        DrugItem("Dexketoprofen 50mg/2ml Ampul", "nsaid analgesik iv im pasca operasi kolik", "Ampul (Injeksi)", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Diclofenac Sodium 50mg", "nsaid natrium diklofenak analgesik nyeri sendi", "Tablet", "Oral", "2x1"),
        DrugItem("Ketoprofen 100mg Suppositoria", "nsaid analgesik rektal pasca operasi", "Suppositoria", "Rektal", "2x1"),
        DrugItem("Parecoxib 40mg IV Vial", "nsaid selektif cox2 iv pasca bedah", "Vial (Injeksi)", "IV (Intravena)", "Setiap 12 Jam"),
        DrugItem("Tramadol 50mg Kapsul", "analgesik opioid sedang nyeri akut kronis", "Kapsul", "Oral", "3x1"),
        DrugItem("Tramadol 100mg/2ml Ampul", "analgesik opioid iv im pasca bedah kolik", "Ampul (Injeksi)", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Paracetamol 325mg + Tramadol 37.5mg (Ultracet)", "analgesik kombinasi nyeri sedang berat", "Tablet", "Oral", "3x1"),
        DrugItem("Pethidine 50mg/ml Ampul", "analgesik opioid kuat spasme visceral persalinan", "Ampul (Injeksi)", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Oxycodone 10mg CR", "analgesik opioid lepas lambat nyeri kanker", "Tablet", "Oral", "2x1"),
        DrugItem("Gabapentin 100mg", "analgesik neuropatik antikonvulsan dnp phn", "Kapsul", "Oral", "3x1"),
        DrugItem("Gabapentin 300mg", "analgesik neuropatik nyeri saraf post herpetik", "Kapsul", "Oral", "3x1"),

        // Lengkap: Golongan Beta Blocker (Kardiologi & Hipertensi)
        DrugItem("Bisoprolol 2.5mg", "beta blocker selektif antihipertensi gagal jantung chf", "Tablet", "Oral", "1x1"),
        DrugItem("Bisoprolol 5mg", "beta blocker selektif antihipertensi angina", "Tablet", "Oral", "1x1"),
        DrugItem("Bisoprolol 10mg", "beta blocker selektif dosis tinggi hipertensi af", "Tablet", "Oral", "1x1"),
        DrugItem("Carvedilol 6.25mg", "beta alfa blocker vasodilatator gagal jantung chf", "Tablet", "Oral", "2x1"),
        DrugItem("Carvedilol 12.5mg", "beta alfa blocker gagal jantung hipertensi", "Tablet", "Oral", "2x1"),
        DrugItem("Carvedilol 25mg", "beta alfa blocker gagal jantung dosis tatalaksana", "Tablet", "Oral", "2x1"),
        DrugItem("Metoprolol Succinate 25mg ER", "beta blocker selektif angina cad chf", "Tablet", "Oral", "1x1"),
        DrugItem("Metoprolol Succinate 50mg ER", "beta blocker selektif hipertensi angina", "Tablet", "Oral", "1x1"),
        DrugItem("Atenolol 50mg", "beta blocker selektif kardioselektif hipertensi", "Tablet", "Oral", "1x1"),
        DrugItem("Atenolol 100mg", "beta blocker selektif hipertensi angina", "Tablet", "Oral", "1x1"),
        DrugItem("Propranolol 10mg", "beta blocker non-selektif tremor migrain tirotoksikosis", "Tablet", "Oral", "3x1"),
        DrugItem("Propranolol 40mg", "beta blocker non-selektif hipertensi portal varises esofagus", "Tablet", "Oral", "2x1"),
        DrugItem("Nebivolol 5mg", "beta blocker generasi 3 vasodilatasi nitrat oksida", "Tablet", "Oral", "1x1"),
        DrugItem("Labetalol 100mg", "beta alfa blocker hipertensi kehamilan preeklampsia", "Tablet", "Oral", "2x1"),
        DrugItem("Labetalol 5mg/ml Ampul IV", "beta alfa blocker iv krisis hipertensi eklampsia", "Ampul (Injeksi)", "IV (Intravena)", "Segera (Cito/Stat)"),
        DrugItem("Esmolol 10mg/ml Ampul IV", "beta blocker ultra short acting svt krisis", "Ampul (Injeksi)", "IV (Intravena)", "Segera (Cito/Stat)"),

        // Lengkap: Golongan ARB (Angiotensin Receptor Blocker)
        DrugItem("Candesartan 8mg", "arb antihipertensi renoprotektif ckd diabetes", "Tablet", "Oral", "1x1"),
        DrugItem("Candesartan 16mg", "arb antihipertensi gagal jantung chf", "Tablet", "Oral", "1x1"),
        DrugItem("Valsartan 80mg", "arb antihipertensi pasca infark miokard", "Tablet", "Oral", "1x1"),
        DrugItem("Valsartan 160mg", "arb antihipertensi gagal jantung hfref", "Tablet", "Oral", "1x1"),
        DrugItem("Telmisartan 40mg", "arb antihipertensi long acting pencegahan cvd", "Tablet", "Oral", "1x1"),
        DrugItem("Telmisartan 80mg", "arb antihipertensi hipertensi resisten", "Tablet", "Oral", "1x1"),
        DrugItem("Losartan 50mg", "arb antihipertensi asam urat urikosurik", "Tablet", "Oral", "1x1"),
        DrugItem("Losartan 100mg", "arb antihipertensi nefropati diabetik", "Tablet", "Oral", "1x1"),
        DrugItem("Irbesartan 150mg", "arb antihipertensi nefropati dm tipe 2", "Tablet", "Oral", "1x1"),
        DrugItem("Irbesartan 300mg", "arb antihipertensi hipertrofi ventrikel kiri", "Tablet", "Oral", "1x1"),
        DrugItem("Olmesartan 20mg", "arb antihipertensi potensial tinggi", "Tablet", "Oral", "1x1"),
        DrugItem("Olmesartan 40mg", "arb antihipertensi esensial", "Tablet", "Oral", "1x1"),
        DrugItem("Azilsartan 40mg", "arb generasi terbaru kontrol 24 jam", "Tablet", "Oral", "1x1"),
        DrugItem("Azilsartan 80mg", "arb generasi terbaru hipertensi derajat 2", "Tablet", "Oral", "1x1"),

        // Lengkap: Golongan Antibiotik Kunci (Lini 1 - Lini 3 & Spektrum Luas)
        DrugItem("Ampicillin 500mg", "antibiotik penisilin oral isk ispa", "Kapsul", "Oral", "4x1"),
        DrugItem("Ampicillin 1 gram Vial", "antibiotik iv im meningitis sepsis", "Vial (Injeksi)", "IV (Intravena)", "Setiap 6 Jam"),
        DrugItem("Ampicillin-Sulbactam 1.5g Vial", "antibiotik penisilin beta laktamase abses pneumonia", "Vial (Injeksi)", "IV (Intravena)", "Setiap 6 Jam"),
        DrugItem("Oxacillin 1 gram Vial", "antibiotik anti-stafilokokus selulitis", "Vial (Injeksi)", "IV (Intravena)", "Setiap 6 Jam"),
        DrugItem("Cefazolin 1 gram Vial", "antibiotik sefalosporin gen 1 profilaksis bedah", "Vial (Injeksi)", "IV (Intravena)", "Dosis Tunggal"),
        DrugItem("Cefuroxime 500mg", "antibiotik sefalosporin gen 2 ispa pneumonia", "Tablet", "Oral", "2x1"),
        DrugItem("Cefuroxime 750mg Vial", "antibiotik sefalosporin gen 2 iv im", "Vial (Injeksi)", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Cefotaxime 1 gram Vial", "antibiotik sefalosporin gen 3 iv sepsis peritonitis", "Vial (Injeksi)", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Cefepime 1 gram Vial", "antibiotik sefalosporin gen 4 iv pseudomonas mdr", "Vial (Injeksi)", "IV (Intravena)", "Setiap 8 Jam"),
        DrugItem("Ceftaroline 600mg Vial", "antibiotik sefalosporin gen 5 mrsa pneumonia", "Vial (Injeksi)", "IV (Intravena)", "Setiap 12 Jam"),
        DrugItem("Imipenem-Cilastatin 500mg Vial", "antibiotik karbapenem infeksi berat sepsis", "Vial (Injeksi)", "IV (Intravena)", "Setiap 6 Jam"),
        DrugItem("Ertapenem 1 gram Vial", "antibiotik karbapenem dosis 1x sehari intraabdomen", "Vial (Injeksi)", "IV (Intravena)", "1x1"),
        DrugItem("Moxifloxacin 400mg", "antibiotik kuinolon respiratori pneumonia cap", "Tablet", "Oral", "1x1"),
        DrugItem("Moxifloxacin 400mg/250ml Infus", "antibiotik kuinolon iv cap berat", "Botol Infus / Drip", "Drip Infus", "1x1"),
        DrugItem("Clarithromycin 500mg", "antibiotik makrolida eradikasi h pylori pneuomonia", "Tablet", "Oral", "2x1"),
        DrugItem("Gentamicin 80mg/2ml Ampul", "antibiotik aminoglikosida iv im isk pyelonefritis", "Ampul (Injeksi)", "IV (Intravena)", "Setiap 12 Jam"),
        DrugItem("Amikacin 500mg/2ml Vial", "antibiotik aminoglikosida mdr gram negatif sepsis", "Vial (Injeksi)", "IV (Intravena)", "Setiap 12 Jam"),
        DrugItem("Doxycycline 100mg", "antibiotik tetrasiklin leptospirosis malaria rickettsia", "Kapsul", "Oral", "2x1"),
        DrugItem("Tigecycline 50mg Vial", "antibiotik glycylcycline mrsa vre intraabdomen", "Vial (Injeksi)", "IV (Intravena)", "Setiap 12 Jam"),
        DrugItem("Linezolid 600mg", "antibiotik oxazolidinone mrsa vre pneumonia mdr", "Tablet", "Oral", "2x1"),

        // Lengkap: Obat Antidiabetes, Kardiovaskular, Lipid & Hematologi Lanjutan
        DrugItem("Insulin Glargine (Lantus SoloStar Pen)", "insulin basal diabetes mellitus tipe 1 2 pen", "Prefilled Syringe", "SC (Subkutan)", "1x1 (Malam)"),
        DrugItem("Insulin Aspart (NovoRapid FlexPen)", "insulin prandial cepat diabetes mellitus pen", "Prefilled Syringe", "SC (Subkutan)", "3x1 (Sebelum Makan)"),
        DrugItem("Gliclazide MR 60mg", "sulfonilurea modified release diabetes tipe 2", "Tablet", "Oral", "1x1"),
        DrugItem("Linagliptin 5mg", "dpp4 inhibitor aman gangguan ginjal ckd dm", "Tablet", "Oral", "1x1"),
        DrugItem("Dapagliflozin 10mg", "sglt2 inhibitor diabetes hfref ckd", "Tablet", "Oral", "1x1"),
        DrugItem("Atorvastatin 20mg", "statin intensitas sedang hiperkolesterolemia cad", "Tablet", "Oral", "1x1 (Malam)"),
        DrugItem("Atorvastatin 40mg", "statin intensitas tinggi pasca ska stemi stroke", "Tablet", "Oral", "1x1 (Malam)"),
        DrugItem("Rosuvastatin 10mg", "statin potensi tinggi dislipidemia", "Tablet", "Oral", "1x1 (Malam)"),
        DrugItem("Rosuvastatin 20mg", "statin intensitas tinggi sindrom koroner akut", "Tablet", "Oral", "1x1 (Malam)"),
        DrugItem("Ticagrelor 90mg", "antiplatelet p2y12 inhibitor ska stemi nstemi", "Tablet", "Oral", "2x1"),
        DrugItem("Prasugrel 10mg", "antiplatelet p2y12 inhibitor pci ptkd", "Tablet", "Oral", "1x1"),
        DrugItem("Enoxaparin 0.4ml SC (Lovenox 4000 Anti-Xa)", "lmwh antikoagulan dvt ska stemi", "Prefilled Syringe", "SC (Subkutan)", "2x1"),
        DrugItem("Enoxaparin 0.6ml SC (Lovenox 6000 Anti-Xa)", "lmwh antikoagulan dosis terapeutik dvt pe", "Prefilled Syringe", "SC (Subkutan)", "2x1"),
        DrugItem("Dabigatran 110mg", "doac noac thrombin inhibitor fibrilasi atrium", "Kapsul", "Oral", "2x1"),
        DrugItem("Apixaban 2.5mg", "doac noac factor xa inhibitor pencegahan stroke af", "Tablet", "Oral", "2x1"),
        DrugItem("Apixaban 5mg", "doac noac factor xa inhibitor terapi dvt pe af", "Tablet", "Oral", "2x1"),
        DrugItem("Amlodipine 5mg", "ccb dihidropiridin antihipertensi angina", "Tablet", "Oral", "1x1"),
        DrugItem("Amlodipine 10mg", "ccb dihidropiridin antihipertensi derajat 2", "Tablet", "Oral", "1x1"),
        DrugItem("Nifedipine GITS 30mg", "ccb dihidropiridin kontrol 24 jam hipertensi", "Tablet", "Oral", "1x1")
    )

    // --- FUZZY / TYPO MATCHING UTILITIES ---
    fun normalizeForFuzzy(text: String): String {
        return text.lowercase()
            .replace(Regex("[^a-z0-9]"), "")
            .replace("ph", "f")
            .replace("ch", "k")
            .replace("kh", "k")
            .replace("sy", "s")
            .replace("sh", "s")
            .replace("ck", "k")
            .replace("x", "ks")
            .replace("y", "i")
            .replace("z", "s")
            .replace("v", "f")
            .replace("oe", "u")
            .replace(Regex("(.)\\1+"), "$1") // Collapse duplicate consecutive characters (e.g. mm -> m)
    }

    fun levenshteinDistance(s1: String, s2: String): Int {
        if (s1 == s2) return 0
        if (s1.isEmpty()) return s2.length
        if (s2.isEmpty()) return s1.length

        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,      // deletion
                    dp[i][j - 1] + 1,      // insertion
                    dp[i - 1][j - 1] + cost // substitution
                )
            }
        }
        return dp[s1.length][s2.length]
    }

    /**
     * Calculates a similarity score between 0.0 and 1.0 (1.0 = exact match).
     * Handles substring matching, phonetic normalization, and typo edit distances.
     */
    fun calculateTypoSimilarity(query: String, target: String): Double {
        val qClean = query.trim().lowercase()
        val tClean = target.trim().lowercase()
        if (qClean.isEmpty() || tClean.isEmpty()) return 0.0
        if (tClean.contains(qClean)) return 1.0

        val qNorm = normalizeForFuzzy(qClean)
        val tNorm = normalizeForFuzzy(tClean)
        if (qNorm.isEmpty() || tNorm.isEmpty()) return 0.0
        if (tNorm.contains(qNorm)) return 0.95

        // Token-level inspection against words in target
        val tokens = tClean.split(Regex("[^a-zA-Z0-9]+")).filter { it.length >= 2 }
        var maxTokenScore = 0.0

        for (token in tokens) {
            if (token.startsWith(qClean) || qClean.startsWith(token)) {
                val ratio = minOf(qClean.length, token.length).toDouble() / maxOf(qClean.length, token.length)
                maxTokenScore = maxOf(maxTokenScore, 0.85 * ratio + 0.1)
            }

            val tokenNorm = normalizeForFuzzy(token)
            if (tokenNorm.contains(qNorm) || qNorm.contains(tokenNorm)) {
                val ratio = minOf(qNorm.length, tokenNorm.length).toDouble() / maxOf(qNorm.length, tokenNorm.length)
                maxTokenScore = maxOf(maxTokenScore, 0.80 * ratio + 0.1)
            }

            val dist = levenshteinDistance(qNorm, tokenNorm)
            val maxLen = maxOf(qNorm.length, tokenNorm.length)
            if (maxLen > 0) {
                val sim = 1.0 - (dist.toDouble() / maxLen)
                maxTokenScore = maxOf(maxTokenScore, sim)
            }
        }

        // Whole phrase distance
        val fullDist = levenshteinDistance(qNorm, tNorm)
        val fullMax = maxOf(qNorm.length, tNorm.length)
        val fullSim = if (fullMax > 0) 1.0 - (fullDist.toDouble() / fullMax) else 0.0

        return maxOf(maxTokenScore, fullSim)
    }

    val recentDiagnosisHistory = java.util.Collections.synchronizedList(mutableListOf<String>())

    fun getDiagnosesForOrgan(organSystem: String, isEmergencyOnly: Boolean = false): List<String> {
        val clean = organSystem.trim().lowercase()
        val isRandom = clean.contains("acak") || clean.contains("random") || clean.contains("semua") || clean.isBlank()

        val rawList = if (isRandom) {
            allDiagnoses
        } else when {
            clean.contains("kardio") || clean.contains("jantung") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("stemi") || d.contains("angina") || d.contains("jantung") || d.contains("hipertensi") ||
                        d.contains("fibrilasi") || d.contains("endokarditis") || d.contains("aneurisma") || d.contains("pad") ||
                        d.contains("dvt") || d.contains("miokarditis") || d.contains("efusi perikardial")
            }
            clean.contains("neuro") || clean.contains("saraf") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("stroke") || d.contains("kejang") || d.contains("epilepsi") || d.contains("meningitis") ||
                        d.contains("headache") || d.contains("migren") || d.contains("vertigo") || d.contains("palsy") ||
                        d.contains("cts") || d.contains("neuralgia") || d.contains("myasthenia") || d.contains("gbs") ||
                        d.contains("parkinson") || d.contains("neuropati") || d.contains("hnp") || d.contains("demensia")
            }
            clean.contains("pulmo") || clean.contains("paru") || clean.contains("respirasi") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("asma") || d.contains("ppok") || d.contains("pneumonia") || d.contains("tb") ||
                        d.contains("pneumothorax") || d.contains("pleura") || d.contains("emboli paru") || d.contains("abses paru") ||
                        d.contains("bronkiektasis") || d.contains("atelektasis") || d.contains("karsinoma paru") || d.contains("apnea")
            }
            clean.contains("gastro") || clean.contains("saluran cerna") || clean.contains("lambung") || clean.contains("hati") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("apendisitis") || d.contains("gerd") || d.contains("gastritis") || d.contains("peritonitis") ||
                        d.contains("kolesistitis") || d.contains("tifoid") || d.contains("hepatitis") || d.contains("ileus") ||
                        d.contains("sirosis") || d.contains("psca") || d.contains("pscb") || d.contains("disentri") ||
                        d.contains("pankreatitis") || d.contains("colitis") || d.contains("abses hati") || d.contains("hemoroid")
            }
            clean.contains("endokrin") || clean.contains("metabolik") || clean.contains("tiroid") || clean.contains("diabetes") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("ketoasidosis") || d.contains("kad") || d.contains("hns") || d.contains("krisis tiroid") ||
                        d.contains("graves") || d.contains("tiroiditis") || d.contains("hipoglikemia") || d.contains("diabetes") ||
                        d.contains("adrenal") || d.contains("cushing") || d.contains("paratiroid") || d.contains("insipidus") || d.contains("metabolik")
            }
            clean.contains("nefro") || clean.contains("ginjal") || clean.contains("uro") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("urolitiasis") || d.contains("nefrolitiasis") || d.contains("isk") || d.contains("pyelonefritis") ||
                        d.contains("ginjal") || d.contains("aki") || d.contains("ckd") || d.contains("bph") ||
                        d.contains("gnaps") || d.contains("sindrom nefrotik") || d.contains("torsi testis") || d.contains("prostatitis")
            }
            clean.contains("tropis") || clean.contains("infeksi") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("dbd") || d.contains("dengue") || d.contains("malaria") || d.contains("leptospirosis") ||
                        d.contains("covid") || d.contains("tetanus") || d.contains("filariasis") || d.contains("rabies") ||
                        d.contains("chikungunya") || d.contains("toksoplasmosis") || d.contains("schistosomiasis") ||
                        d.contains("anthrax") || d.contains("difteri") || d.contains("tifoid")
            }
            clean.contains("pediatri") || clean.contains("anak") || clean.contains("bayi") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("anak") || d.contains("balita") || d.contains("bayi") || d.contains("neonatus") ||
                        d.contains("bronkiolitis") || d.contains("croup") || d.contains("morbili") || d.contains("varicella") ||
                        d.contains("pertusis") || d.contains("gizi buruk") || d.contains("thalassemia") || d.contains("asfiksia") ||
                        d.contains("ikterus") || d.contains("hirschsprung") || d.contains("kejang demam")
            }
            clean.contains("obgyn") || clean.contains("obstetri") || clean.contains("ginekologi") || clean.contains("kandungan") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("preeklamsia") || d.contains("eklamsia") || d.contains("ket") || d.contains("abortus") ||
                        d.contains("postpartum") || d.contains("plasenta") || d.contains("kista ovarium") || d.contains("pid") ||
                        d.contains("hyperemesis") || d.contains("mastitis") || d.contains("mioma") || d.contains("endometritis") || d.contains("vaginosis")
            }
            clean.contains("trauma") || clean.contains("emergensi") || clean.contains("gawat") || clean.contains("kedaruratan") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("syok") || d.contains("fraktur") || d.contains("luka bakar") || d.contains("trauma") ||
                        d.contains("edh") || d.contains("sdh") || d.contains("pneumothorax") || d.contains("dislokasi") || d.contains("heat stroke")
            }
            clean.contains("muskulo") || clean.contains("reuma") || clean.contains("tulang") || clean.contains("sendi") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("gout") || d.contains("osteoartritis") || d.contains("rheumatoid") || d.contains("sle") ||
                        d.contains("spondylitis") || d.contains("osteomielitis") || d.contains("pott") || d.contains("osteoporosis") ||
                        d.contains("artritis septik") || d.contains("fibromialgia")
            }
            clean.contains("jiwa") || clean.contains("psikiatri") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("skizofrenia") || d.contains("panik") || d.contains("depresi") || d.contains("bipolar") ||
                        d.contains("delirium") || d.contains("ocd") || d.contains("ptsd") || d.contains("anoreksia")
            }
            clean.contains("tht") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("abses peritonsil") || d.contains("otitis") || d.contains("sinusitis") || d.contains("epistaksis") ||
                        d.contains("benda asing") || d.contains("presbiakusis") || d.contains("polip") || d.contains("nasofaring")
            }
            clean.contains("mata") || clean.contains("oftalmo") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("ulkus kornea") || d.contains("glaukoma") || d.contains("konjungtivitis") || d.contains("erosi kornea") ||
                        d.contains("endoftalmitis") || d.contains("katarak") || d.contains("pterigium") || d.contains("ablasio") ||
                        d.contains("hordeolum") || d.contains("retinopati")
            }
            clean.contains("kulit") || clean.contains("derma") -> allDiagnoses.filter {
                val d = it.lowercase()
                d.contains("herpes") || d.contains("dermatitis") || d.contains("sifilis") || d.contains("uretritis") ||
                        d.contains("lepra") || d.contains("sjs") || d.contains("pemfigus") || d.contains("skabies") ||
                        d.contains("tinea") || d.contains("psoriasis") || d.contains("pruritus") || d.contains("akne")
            }
            else -> allDiagnoses
        }

        val filtered = if (isEmergencyOnly) {
            rawList.filter { d ->
                val dl = d.lowercase()
                dl.contains("stemi") || dl.contains("nstemi") || dl.contains("stroke") || dl.contains("syok") ||
                        dl.contains("shock") || dl.contains("eklamsia") || dl.contains("perforasi") || dl.contains("ventil") ||
                        dl.contains("krisis") || dl.contains("berat") || dl.contains("kejang") || dl.contains("anafilaksis") ||
                        dl.contains("ket") || dl.contains("sjs") || dl.contains("perdarahan") || dl.contains("masif") ||
                        dl.contains("tamponade") || dl.contains("akut")
            }
        } else {
            rawList
        }

        return if (filtered.isNotEmpty()) filtered else (if (rawList.isNotEmpty()) rawList else allDiagnoses)
    }

    /**
     * Memilih satu diagnosis secara adil, merata, dan benar-benar acak dari katalog medis.
     * Menggunakan buffer riwayat untuk mencegah repetisi kasus yang sama berturut-turut.
     */
    fun pickTrulyRandomDiagnosis(organSystem: String = "Acak", isEmergencyOnly: Boolean = false): String {
        val candidates = getDiagnosesForOrgan(organSystem, isEmergencyOnly)

        // Filter kandidat yang baru saja muncul di riwayat terakhir agar tidak ada repetisi
        val freshCandidates = synchronized(recentDiagnosisHistory) {
            candidates.filterNot { recentDiagnosisHistory.contains(it) }
        }

        val pool = if (freshCandidates.isNotEmpty()) freshCandidates else candidates
        val chosen = pool.random()

        synchronized(recentDiagnosisHistory) {
            recentDiagnosisHistory.add(chosen)
            // Simpan hingga 25 riwayat terakhir (FIFO)
            while (recentDiagnosisHistory.size > 25) {
                recentDiagnosisHistory.removeAt(0)
            }
        }

        return chosen
    }

    fun filterDiagnoses(query: String): List<String> {
        if (query.isBlank()) return allDiagnoses.take(6)
        val clean = query.trim().lowercase()

        // 1. Direct contains match
        val directMatches = allDiagnoses.filter { it.lowercase().contains(clean) }

        // 2. Rich semantic / symptom / category matching
        val extraMatches = mutableListOf<String>()

        if (clean.contains("perut") || clean.contains("lambung") || clean.contains("maag") ||
            clean.contains("mual") || clean.contains("muntah") || clean.contains("diare") || clean.contains("abdomen")) {
            extraMatches.addAll(listOf(
                "Apendisitis Akut (Apendisitis Perforasi)",
                "Gastritis Erosif / Tukak Peptikum dengan Perdarahan",
                "Kolesistitis Akut ec Kolelitiasis",
                "Pankreatitis Akut ec Kolelitiasis / Alkohol",
                "Peritonitis Akut ec Perforasi Gaster",
                "Gastroesophageal Reflux Disease (GERD)",
                "Ileus Obstruktif ec Volvulus / Hernia Inkarserata",
                "Colitis Ulserativa / Crohn's Disease",
                "Demam Tifoid dengan Komplikasi Perforasi"
            ))
        }

        if (clean.contains("aterosklerosis") || clean.contains("sklerosis") || clean.contains("plak") ||
            clean.contains("koroner") || clean.contains("vaskular") || clean.contains("pembuluh")) {
            extraMatches.addAll(listOf(
                "Infark Miokard Akut dengan ST Elevasi (STEMI) Anteroseptal",
                "Non-ST Elevation Myocardial Infarction (NSTEMI)",
                "Unstable Angina Pectoris (UAP) / Angina Tak Stabil",
                "Angina Pektoris Stabil (APS)",
                "Penyakit Arteri Perifer (PAD) / Iskemik Tungkai Akut",
                "Stroke Iskemik Akut (Serangan Otak Iskemik)",
                "Aneurisma Aorta Abdominalis (AAA) Ruptur",
                "Transient Ischemic Attack (TIA) / Stroke Ringan"
            ))
        }

        if (clean.contains("dada") || clean.contains("jantung") || clean.contains("angina") || clean.contains("debar")) {
            extraMatches.addAll(listOf(
                "Infark Miokard Akut dengan ST Elevasi (STEMI) Anteroseptal",
                "Infark Miokard Akut dengan ST Elevasi (STEMI) Inferior",
                "Non-ST Elevation Myocardial Infarction (NSTEMI)",
                "Unstable Angina Pectoris (UAP) / Angina Tak Stabil",
                "Angina Pektoris Stabil (APS)",
                "Gagal Jantung Kongestif (CHF / Acute Decompensated Heart Failure)",
                "Perikarditis Akut",
                "Tamponade Jantung",
                "Fibrilasi Atrial (AF) Rapid Ventricular Response (RVR)"
            ))
        }

        if (clean.contains("sesak") || clean.contains("napas") || clean.contains("paru") || clean.contains("batuk") || clean.contains("mengi")) {
            extraMatches.addAll(listOf(
                "Asma Bronkial Eksaserbasi Akut Berat",
                "PPOK (Penyakit Paru Obstruktif Kronik) Eksaserbasi Akut",
                "Community-Acquired Pneumonia (CAP) Derajat Berat",
                "Tuberkulosis (TB) Paru Kasus Baru BTA Positif",
                "Tension Pneumothorax ec Trauma Dada",
                "Efusi Pleura Masif Dextra",
                "Emboli Paru Akut (Pulmonary Embolism)",
                "Abses Paru Bakterial"
            ))
        }

        if (clean.contains("kepala") || clean.contains("pusing") || clean.contains("vertigo") || clean.contains("migren") ||
            clean.contains("kebas") || clean.contains("lumpuh") || clean.contains("pelo") || clean.contains("merot")) {
            extraMatches.addAll(listOf(
                "Stroke Iskemik Akut (Serangan Otak Iskemik)",
                "Stroke Hemoragik (Perdarahan Intraserebral / ICH / SAH)",
                "Transient Ischemic Attack (TIA) / Stroke Ringan",
                "Benign Paroxysmal Positional Vertigo (BPPV)",
                "Tension Type Headache (TTH)",
                "Migren tanpa Aura",
                "Bell's Palsy (Paresis N. VII Perifer)",
                "Meningitis Bakterialis / Ensefalitis Viral"
            ))
        }

        if (clean.contains("demam") || clean.contains("panas") || clean.contains("menggigil") || clean.contains("infeksi")) {
            extraMatches.addAll(listOf(
                "Demam Berdarah Dengue (DBD) Grade I-IV / Dengue Shock Syndrome",
                "Demam Tifoid dengan Komplikasi Perforasi",
                "Malaria Falciparum Berat dengan Komplikasi Otak",
                "Leptospirosis Berat (Weil's Disease)",
                "Community-Acquired Pneumonia (CAP) Derajat Berat",
                "Infeksi Saluran Kemih (ISK) / Pyelonefritis Akut",
                "Meningitis Bakterialis / Ensefalitis Viral"
            ))
        }

        if (clean.contains("kencing") || clean.contains("kemih") || clean.contains("ginjal") || clean.contains("pinggang") || clean.contains("batu")) {
            extraMatches.addAll(listOf(
                "Urolitiasis / Batu Ureter (Kolik Ureter Akut)",
                "Nefrolitiasis / Batu Ginjal Dextra",
                "Infeksi Saluran Kemih (ISK) / Pyelonefritis Akut",
                "Gagal Ginjal Akut (Acute Kidney Injury / AKI)",
                "Penyakit Ginjal Kronik (CKD) Stage 5 on HD",
                "Benign Prostatic Hyperplasia (BPH) dengan Retensio Urin Akut"
            ))
        }

        if (clean.contains("kejang") || clean.contains("kaku") || clean.contains("epilepsi")) {
            extraMatches.addAll(listOf(
                "Kejang Demam Sederhana",
                "Kejang Demam Kompleks",
                "Epilepsi / Status Epileptikus",
                "Eklamsia dengan Kejang Terkontrol",
                "Tetanus Berat (Grade III-IV)"
            ))
        }

        if (clean.contains("gatal") || clean.contains("kulit") || clean.contains("ruam") || clean.contains("bintik") || clean.contains("bentol")) {
            extraMatches.addAll(listOf(
                "Herpes Zoster Thorakalis Dextra",
                "Dermatitis Atopik Eksaserbasi Akut dengan Infeksi Sekunder",
                "Skabies dengan Infeksi Sekunder Pyoderma",
                "Steven-Johnson Syndrome (SJS) / Toxic Epidermal Necrolysis (TEN)",
                "Psoriasis Vulgaris Plak Kronik",
                "Tinea Cruris / Corporis / Versicolor"
            ))
        }

        if (clean.contains("mata") || clean.contains("merah") || clean.contains("penglihatan") || clean.contains("kabur")) {
            extraMatches.addAll(listOf(
                "Glaukoma Akut Sudut Tertutup OD/OS",
                "Ulkus Kornea Bakterial Dextra",
                "Konjungtivitis Gonore Neonatorum",
                "Erosi Kornea Traumatik OD",
                "Katarak Senilis Matur OD/OS",
                "Ablasio Retina Regmatogen OD"
            ))
        }

        if (clean.contains("hamil") || clean.contains("kandungan") || clean.contains("darah pervaginam") || clean.contains("obgyn")) {
            extraMatches.addAll(listOf(
                "Preeklamsia Berat (PEB) pada Kehamilan",
                "Eklamsia dengan Kejang Terkontrol",
                "Kehamilan Ektopik Terganggu (KET)",
                "Abortus Inkomplit dengan Perdarahan Pervaginam",
                "Perdarahan Postpartum (PPP) ec Atonia Uteri",
                "Plasenta Previa Totalis dengan Perdarahan Aktif"
            ))
        }

        // 3. Typo / Fuzzy Matches (Levenshtein & Phonetic Similarity)
        val typoMatches = allDiagnoses
            .map { diag -> diag to calculateTypoSimilarity(clean, diag) }
            .filter { it.second >= 0.45 }
            .sortedByDescending { it.second }
            .map { it.first }

        return (directMatches + typoMatches + extraMatches).distinct()
    }

    fun filterDrugs(query: String): List<DrugItem> {
        if (query.isBlank()) return allDrugs.take(12)
        val clean = query.trim().lowercase()

        // 1. Direct contains match
        val directMatches = allDrugs.filter { drug ->
            drug.name.lowercase().contains(clean) ||
                    drug.categoryTag.lowercase().contains(clean) ||
                    drug.defaultForm.lowercase().contains(clean) ||
                    drug.defaultRoute.lowercase().contains(clean)
        }

        // 2. Typo / Fuzzy Matches for Drug Names and Category Tags
        val typoMatches = allDrugs
            .map { drug ->
                val nameScore = calculateTypoSimilarity(clean, drug.name)
                val catScore = calculateTypoSimilarity(clean, drug.categoryTag)
                drug to maxOf(nameScore, catScore * 0.9)
            }
            .filter { it.second >= 0.48 }
            .sortedByDescending { it.second }
            .map { it.first }

        // 3. Semantic matches
        val semanticMatches = mutableListOf<DrugItem>()

        if (clean.contains("aterosklerosis") || clean.contains("plak") || clean.contains("kolesterol") || clean.contains("statin")) {
            semanticMatches.addAll(allDrugs.filter {
                it.name.contains("Statin", ignoreCase = true) ||
                        it.name.contains("Aspirin", ignoreCase = true) ||
                        it.name.contains("Clopidogrel", ignoreCase = true) ||
                        it.name.contains("Ticagrelor", ignoreCase = true)
            })
        }

        if (clean.contains("perut") || clean.contains("maag") || clean.contains("lambung") || clean.contains("mual") || clean.contains("asam")) {
            semanticMatches.addAll(allDrugs.filter {
                it.name.contains("prazole", ignoreCase = true) ||
                        it.name.contains("Antasida", ignoreCase = true) ||
                        it.name.contains("Sukralfat", ignoreCase = true) ||
                        it.name.contains("Ondansetron", ignoreCase = true) ||
                        it.name.contains("Domperidone", ignoreCase = true) ||
                        it.name.contains("Ranitidine", ignoreCase = true) ||
                        it.name.contains("Hiosin", ignoreCase = true)
            })
        }

        if (clean.contains("nyeri") || clean.contains("sakit") || clean.contains("demam") || clean.contains("panas")) {
            semanticMatches.addAll(allDrugs.filter {
                it.name.contains("Paracetamol", ignoreCase = true) ||
                        it.name.contains("Ibuprofen", ignoreCase = true) ||
                        it.name.contains("Diklofenak", ignoreCase = true) ||
                        it.name.contains("Mefenamat", ignoreCase = true) ||
                        it.name.contains("Ketorolac", ignoreCase = true) ||
                        it.name.contains("Tramadol", ignoreCase = true)
            })
        }

        if (clean.contains("hipertensi") || clean.contains("tensi") || clean.contains("tekanan darah")) {
            semanticMatches.addAll(allDrugs.filter {
                it.name.contains("Amlodipine", ignoreCase = true) ||
                        it.name.contains("Captopril", ignoreCase = true) ||
                        it.name.contains("Candesartan", ignoreCase = true) ||
                        it.name.contains("Bisoprolol", ignoreCase = true) ||
                        it.name.contains("Nifedipine", ignoreCase = true) ||
                        it.name.contains("Furosemide", ignoreCase = true)
            })
        }

        if (clean.contains("antibiotik") || clean.contains("bakteri") || clean.contains("infeksi")) {
            semanticMatches.addAll(allDrugs.filter {
                it.name.contains("Amoxi", ignoreCase = true) ||
                        it.name.contains("Cipro", ignoreCase = true) ||
                        it.name.contains("Ceftri", ignoreCase = true) ||
                        it.name.contains("Azithro", ignoreCase = true) ||
                        it.name.contains("Cefix", ignoreCase = true) ||
                        it.name.contains("Metro", ignoreCase = true)
            })
        }

        if (clean.contains("sesak") || clean.contains("asma") || clean.contains("batuk")) {
            semanticMatches.addAll(allDrugs.filter {
                it.name.contains("Salbutamol", ignoreCase = true) ||
                        it.name.contains("Combivent", ignoreCase = true) ||
                        it.name.contains("Deksametason", ignoreCase = true) ||
                        it.name.contains("Metilprednisolon", ignoreCase = true) ||
                        it.name.contains("Asetilsistein", ignoreCase = true)
            })
        }

        return (directMatches + typoMatches + semanticMatches).distinct()
    }
}
