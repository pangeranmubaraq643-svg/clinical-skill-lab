package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.ChatMessage
import com.example.data.model.ChatSender
import com.example.data.model.CitoActionFeedback
import com.example.data.model.CitoImpactType
import com.example.data.model.ClinicalCase
import com.example.data.model.EvaluationResult
import com.example.data.model.ExamCategory
import com.example.data.model.UserExamResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiService {

    private const val TAG = "GeminiService"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun getApiKey(): String {
        return try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }
    }

    suspend fun getPatientResponse(
        case: ClinicalCase,
        history: List<ChatMessage>,
        userMessage: String,
        difficultyLevel: com.example.data.model.DifficultyLevel = com.example.data.model.DifficultyLevel.BASIC
    ): String = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext fallbackPatientResponse(case, userMessage, history)
        }

        try {
            val systemInstruction = """
                Anda adalah PASIEN (Usia ${case.patientAge} tahun, gender ${case.patientGender}, pekerjaan ${case.patientOccupation}) dalam simulasi OSCE/Anamnesis Medis Indonesia.
                Keadaan Umum & Penampilan: ${case.generalAppearance}
                Keluhan Utama: ${case.chiefComplaint}
                Detail Riwayat Penyakit & Persona Pasien: ${case.patientPersonaInstruction}
                Tanda Vital Saat Ini: TD ${case.td} mmHg, Nadi ${case.nadi}x/m, RR ${case.rr}x/m, Suhu ${case.suhu}°C, SpO2 ${case.spO2}%.
                
                PEDOMAN TINGKAT KESULITAN & TERMINOLOGI:
                ${difficultyLevel.promptInstruction}

                INGATAN KONSISTEN PERCAKAPAN:
                - Anda HARUS MEMILIKI INGATAN KONSISTEN terhadap SELURUH percakapan sebelumnya dalam `history`.
                - JANGAN PERNAH MENGUBAH ATAU MENGONTRAKDIKSI DURASI WAKTU / JUMLAH HARI / JAM / LOKASI NYERI / GEJALA yang pernah Anda sampaikan sebelumnya di awal percakapan atau di Keluhan Utama! (Contoh: Jika di percakapan awal/Keluhan Utama/jawaban lalu sudah disebutkan "5 hari", Anda HARUS SELALU MENJAWAB "5 hari", DILARANG MENGUBAH MENJADI "2-3 hari" ATAU ANGKA LAINNYA).
                - Jika Dokter menanyakan ulang hal yang sudah pernah Anda jawab sebelumnya, ingatan Anda harus 100% konsisten dengan jawaban awal Anda (misal: "Seperti yang tadi saya sampaikan Dok, sudah 5 hari yang lalu...").
                - Ceritakan gejala yang Anda rasakan secara langsung sesuai tingkat kesulitan di atas.
                - DILARANG MENGGUNAKAN ISTILAH MEDIS DOKTER ATAU MEMBOCORKAN NAMA DIAGNOSIS MEDIS ("${case.trueDiagnosis}").

                ATURAN KERAS MENJAWAB (PATIENT GUARDRAIL & RAHASIA DIAGNOSIS):
                1. DILARANG KERAS MENYEBUTKAN, MEMBOCORKAN, ATAU MENGGUNAKAN ISTILAH DIAGNOSIS MEDIS PASTI PENYAKIT ANDA (termasuk "${case.trueDiagnosis}", "${case.title}", atau istilah medis seperti STEMI, Apendisitis, Stroke, Diabetes, Ketoasidosis, DHF, Diare, dll) MESKIPUN DOKTER MENANYAKAN/MEMINTA "Sakit apa?", "Diagnosisnya apa?", atau "Apakah ini penyakit [X]?".
                2. Anda adalah PASIEN AWAM tanpa pengetahuan kedokteran. Jika Dokter bertanya "Bapak/Ibu sakit apa?", "Diagnosisnya apa?", atau "Apa nama penyakitnya?", JAWABLAH DENGAN JUJUR BAHWA ANDA TIDAK TAHU nama medis penyakitnya, dan jelaskan bahwa Anda justru datang ke dokter untuk berobat dan mencari tahu penyebab keluhan tubuh Anda.
                3. Ceritakan HANYA GEJALA FISIK AWAM yang Anda rasakan secara langsung (misal: "dada rasanya ditindih beban berat", "perut melilit di kanan bawah", "badan lemas", "pusing melayang").
                4. Jawablah SPESIFIK dan LANGSUNG terhadap pertanyaan Dokter dalam 1-3 kalimat komunikatif. JANGAN memberikan jawaban template berulang.
                5. Jika Dokter menanyakan beberapa poin sekaligus, jawablah seluruh poin tersebut secara jujur sebagai pasien.
                6. Gunakan bahasa Indonesia awam/sehari-hari yang natural. JANGAN PERNAH gunakan istilah medis dokter.
                7. PENTING - PERTANYAAN ACAK / LINGKUNGAN / HOBI / MAKANAN: Jika Dokter menanyakan hal di luar masalah medis (seperti makanan kesukaan, hobi, tempat tinggal, dsb), jawablah dengan ramah dan manusiawi sebagai pasien, lalu hubungkan secara halus bahwa saat ini Anda sedang lemas/sakit.
                8. GAYA BAHASA ALAMI PASIEN & TATA BAHASA INDONESIA:
                   - Gunakan susunan kalimat bahasa Indonesia yang mengalir luwes, alami, dan mudah dipahami.
                   - Contoh yang BENAR: "Di bagian paha kanan bawah saya ada benjolan Dok", "Perut kanan bawah saya terasa sangat sakit", "Dada saya rasanya sesak dan ampeg".
                   - DILARANG KERAS membuat kalimat aneh/rancu seperti "Saya mengalami paha kanan saya di atas lutut ada benjolan" atau pengulangan kata "saya" yang berlebihan.
                   - Gunakan frasa alami manusiawi seperti "gejala ini", "rasa sakit ini", "nyeri ini", "kondisi ini", atau sebutkan langsung rasa sakitnya. DILARANG KERAS menggunakan kata kaku/robotik seperti "keluhan ini", "keluhan utama ini", "menjelaskan keluhan ini", atau "merasakan keluhan ini".
            """.trimIndent()

            val contentsArray = JSONArray()

            // Build properly formatted alternating conversation turns from history
            val filteredHistory = history.filter { it.sender != ChatSender.SYSTEM && it.text.isNotBlank() }.takeLast(16)
            var lastRole = ""

            for (msg in filteredHistory) {
                val role = if (msg.sender == ChatSender.DOKTER) "user" else "model"
                if (role == lastRole) {
                    // Append to existing last turn to enforce strict user/model alternation
                    if (contentsArray.length() > 0) {
                        val lastObj = contentsArray.getJSONObject(contentsArray.length() - 1)
                        val partsArr = lastObj.getJSONArray("parts")
                        val prevText = partsArr.getJSONObject(0).optString("text", "")
                        partsArr.getJSONObject(0).put("text", "$prevText\n${msg.text}")
                    }
                } else {
                    val partObj = JSONObject().put("text", msg.text)
                    val contentObj = JSONObject().put("role", role).put("parts", JSONArray().put(partObj))
                    contentsArray.put(contentObj)
                    lastRole = role
                }
            }

            // If history didn't include the current userMessage or last role wasn't user
            if (contentsArray.length() == 0 || lastRole != "user") {
                val currentPart = JSONObject().put("text", userMessage)
                val currentContent = JSONObject().put("role", "user").put("parts", JSONArray().put(currentPart))
                contentsArray.put(currentContent)
            }

            val systemPart = JSONObject().put("text", systemInstruction)
            val systemContent = JSONObject().put("parts", JSONArray().put(systemPart))

            // Try supported primary Gemini models
            val modelsToTry = listOf("gemini-3.5-flash", "gemini-3.1-pro-preview", "gemini-3.1-flash-lite-preview", "gemini-flash-latest")

            for (modelName in modelsToTry) {
                try {
                    val root = JSONObject()
                    root.put("contents", contentsArray)
                    root.put("systemInstruction", systemContent)

                    if (modelName == "gemini-3.1-pro-preview") {
                        val genConfig = JSONObject()
                        val thinkingConfig = JSONObject().put("thinkingLevel", "HIGH")
                        genConfig.put("thinkingConfig", thinkingConfig)
                        root.put("generationConfig", genConfig)
                    }

                    val url = "${BASE_URL}$modelName:generateContent?key=$apiKey"
                    val body = root.toString().toRequestBody("application/json".toMediaType())
                    val request = Request.Builder().url(url).post(body).build()

                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string() ?: ""

                    if (response.isSuccessful && responseString.isNotEmpty()) {
                        val jsonResp = JSONObject(responseString)
                        val candidates = jsonResp.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val cand = candidates.getJSONObject(0)
                            val content = cand.optJSONObject("content")
                            val parts = content?.optJSONArray("parts")
                            if (parts != null && parts.length() > 0) {
                                val text = parts.getJSONObject(0).optString("text", "")
                                if (text.isNotBlank()) {
                                    return@withContext sanitizePatientResponse(text.trim(), case)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Failed model $modelName, trying next", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in getPatientResponse Gemini API", e)
        }

        return@withContext fallbackPatientResponse(case, userMessage, history)
    }

    suspend fun getGeminiTreatmentSuggestions(
        diagnosis: String
    ): String = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        val systemPrompt = "Anda adalah Konsulen Klinis dan Dokter Spesialis Senior di Indonesia yang ahli dalam Pedoman Praktik Klinis (PPK) Kemenkes RI."
        val userPrompt = """
            Berikan rekomendasi tatalaksana medis dan pilihan obat lini pertama sesuai standar Kemenkes RI / PPK Indonesia untuk diagnosis kerja: "$diagnosis".
            
            Sajikan dalam format ringkas yang siap dipelajari mahasiswa/dokter:
            1. Obat Utama & Dosis Standar (Nama generik, dosis, rute IV/oral, frekuensi).
            2. Tindakan/Terapi Awal & Pemantauan Penting.
            3. Edukasi Pasien yang perlu disampaikan.
        """.trimIndent()

        if (apiKey.isNotEmpty() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val root = JSONObject()
                val contentsArray = JSONArray()
                val partObj = JSONObject().put("text", userPrompt)
                val contentObj = JSONObject().put("parts", JSONArray().put(partObj))
                contentsArray.put(contentObj)
                root.put("contents", contentsArray)

                val sysPart = JSONObject().put("text", systemPrompt)
                root.put("systemInstruction", JSONObject().put("parts", JSONArray().put(sysPart)))

                val modelsToTry = listOf("gemini-3.5-flash", "gemini-3.1-pro-preview", "gemini-3.1-flash-lite-preview")
                for (modelName in modelsToTry) {
                    try {
                        val url = "${BASE_URL}$modelName:generateContent?key=$apiKey"
                        val body = root.toString().toRequestBody("application/json".toMediaType())
                        val request = Request.Builder().url(url).post(body).build()

                        val response = client.newCall(request).execute()
                        val responseString = response.body?.string() ?: ""

                        if (response.isSuccessful && responseString.isNotEmpty()) {
                            val jsonResp = JSONObject(responseString)
                            val candidates = jsonResp.optJSONArray("candidates")
                            if (candidates != null && candidates.length() > 0) {
                                val text = candidates.getJSONObject(0)
                                    .optJSONObject("content")
                                    ?.optJSONArray("parts")
                                    ?.getJSONObject(0)
                                    ?.optString("text", "") ?: ""
                                if (text.isNotBlank()) return@withContext text.trim()
                            }
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "Failed model $modelName in treatment suggestions, trying next", e)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching Gemini treatment suggestions", e)
            }
        }

        // Fallback standard response
        return@withContext """
            [SARAN KONSULEN / KEMENKES RI]
            Rekomendasi Terapi Standar untuk "$diagnosis":
            • Resusitasi & Oksigenasi: Berikan Oksigen jika SpO2 < 95% atau sesak berat. Pasang IV Line cairan RL/NaCl 0.9%.
            • Farmakoterapi Utama: Berikan obat lini pertama sesuai indikasi klinis dengan rute IV/oral dosis terbagi.
            • Anti-nyeri / Symptomatic: Parasetamol 500-1000 mg p.o / IV jika ada demam/nyeri.
            • Monitoring: Pantau Tanda-Tanda Vital (TD, Nadi, RR, SpO2) tiap 15-30 menit pada fase akut.
            • Edukasi: Jelaskan rencana tindakan, restriksi aktivitas, dan tanda bahaya pada pasien & keluarga.
        """.trimIndent()
    }

    private fun getConsistentOnset(case: ClinicalCase, history: List<ChatMessage>): String {
        val timeRegex = Regex("(?i)(\\b\\d+\\b\\s*(?:hari|jam|minggu|bulan|tahun)|kemarin|tadi pagi|semalam|beberapa hari|sejak tadi)")
        
        // 1. Search existing patient messages in history for explicit duration
        for (msg in history.filter { it.sender == ChatSender.PASIEN }.reversed()) {
            val match = timeRegex.find(msg.text)
            if (match != null) {
                val found = match.value.lowercase()
                return if (found.startsWith("sejak")) found else "sekitar $found yang lalu"
            }
        }

        // 2. Search case.chiefComplaint & case.patientPersonaInstruction
        val caseText = "${case.chiefComplaint} ${case.patientPersonaInstruction}"
        val matchCase = timeRegex.find(caseText)
        if (matchCase != null) {
            val found = matchCase.value.lowercase()
            return if (found.startsWith("sejak")) found else "sekitar $found yang lalu"
        }

        // 3. Fallback based on emergency vs non-emergency
        if (case.isEmergencyCase) {
            return "sekitar 1-2 jam yang lalu secara mendadak"
        }

        // Deterministic duration based on case ID hash so it never changes randomly
        val days = (Math.abs(case.id.hashCode()) % 4) + 2
        return "sekitar $days hari yang lalu"
    }

    private fun fallbackPatientResponse(case: ClinicalCase, doctorMessage: String, history: List<ChatMessage> = emptyList()): String {
        val lowerMsg = doctorMessage.lowercase().trim()
        val complaint = case.chiefComplaint.lowercase()

        val patientNameDisplay = "Pasien"

        val cleanComplaint = humanizeMedicalTerms(sanitizePatientResponse(case.chiefComplaint, case))

        // Check if doctor asked a similar question in recent history (repeated topic)
        val recentDoctorMessages = history.filter { it.sender == ChatSender.DOKTER }.takeLast(4).map { it.text.lowercase() }
        val isRepeatedQuestion = recentDoctorMessages.any { prev ->
            prev != lowerMsg && calculateSimilarity(prev, lowerMsg) >= 0.80
        }

        val prefixRepeat = if (isRepeatedQuestion) {
            listOf(
                "Seperti yang saya sampaikan tadi Dok, ",
                "Tadi kan sudah saya sebutkan Dok, ",
                "Iya Dok, seperti yang tadi saya jelaskan, ",
                "Sama seperti pertanyaan Dokter tadi, "
            ).random()
        } else ""

        // Comprehensive list of medical and anamnesis keywords
        val medicalKeywords = listOf(
            "nama", "siapa", "umur", "usia", "sakit", "nyeri", "keluhan", "sejak", "kapan", "lama",
            "lokasi", "di mana", "mual", "muntah", "pusing", "demam", "sesak", "napas", "obat", "alergi",
            "riwayat", "keluarga", "dada", "perut", "kepala", "badan", "tangan", "kaki", "pinggang",
            "makan", "minum", "tidur", "faktor", "rasa", "pemicu", "berkurang", "memberat", "sebelumnya",
            "kenapa", "ada apa", "ke sini", "berobat", "sehat", "periksa", "gejala", "darah", "jantung",
            "batuk", "dahak", "flu", "pilek", "kejang", "lumpuh", "kram", "kaku", "semutan", "kesemutan",
            "bab", "bak", "kencing", "berak", "diare", "mencret", "hipertensi", "gula", "diabetes", "asma",
            "tensi", "suhu", "panas", "gigil", "menggigil", "pucat", "kuning", "gatal", "bengkak", "luka",
            "paru", "lambung", "ulu hati", "sendi", "otot", "kulit", "mata", "telinga", "hidung", "tenggorokan"
        )
        val isRandomQuestion = medicalKeywords.none { kw -> lowerMsg.contains(kw) }

        if (isRandomQuestion) {
            val randomReply = when {
                lowerMsg.contains("opor") || lowerMsg.contains("bakso") || lowerMsg.contains("mie") || lowerMsg.contains("suka makan") || lowerMsg.contains("makanan") ->
                    "Aduh Dok, kalau makanan biasa saya suka banget... Tapi jangankan makan enak Dok, sekarang buat menelan aja rasanya mual dan lemas sekali karena $cleanComplaint..."
                lowerMsg.contains("hobi") || lowerMsg.contains("olahraga") || lowerMsg.contains("main") || lowerMsg.contains("suka apa") ->
                    "Biasanya hobi saya jalan-jalan atau kumpul sama keluarga Dok... Tapi sekarang jalan sedikit aja lemas banget karena $cleanComplaint ini Dok..."
                lowerMsg.contains("lahir") || lowerMsg.contains("tinggal") || lowerMsg.contains("rumah") || lowerMsg.contains("alamat") ->
                    "Saya tinggal di daerah dekat sini Dok... Maaf ya Dok, kepala dan badan saya rasanya makin lemas karena $cleanComplaint ini..."
                lowerMsg.contains("halo") || lowerMsg.contains("pagi") || lowerMsg.contains("siang") || lowerMsg.contains("malam") || lowerMsg.contains("kabar") ->
                    "Halo Dok... Jujur kabar saya lagi kurang baik sekali hari ini Dok, badan rasanya sangat tidak enak karena $cleanComplaint ini..."
                else ->
                    "Aduh maaf Dok, pikiran saya lagi agak acak-acakan karena nahan sakit... Jujur saat ini yang paling terasa menyiksa itu $cleanComplaint Dok. Mohon bantu periksa saya ya Dok..."
            }
            return humanizeMedicalTerms(sanitizePatientResponse("${prefixRepeat}$randomReply", case))
        }

        // Priority 1: Greetings & Identity
        if (lowerMsg.contains("nama") || lowerMsg.contains("siapa") || lowerMsg.contains("perkenalkan") || lowerMsg.contains("umur") || lowerMsg.contains("usia")) {
            return "${prefixRepeat}Halo Dok, nama saya $patientNameDisplay, usia saya ${case.patientAge} tahun dan bekerja sebagai ${case.patientOccupation}."
        }

        // Priority 2: Empathy / Reassurance
        if (lowerMsg.contains("tenang") || lowerMsg.contains("khawatir") || lowerMsg.contains("bantu") || lowerMsg.contains("napas perlahan")) {
            return "Terima kasih banyak Dok, saya sedikit lebih tenang dengarnya. Mohon bantuannya ya Dok..."
        }

        // Priority 3: Asking for Diagnosis ("Sakit apa?")
        if (lowerMsg.contains("sakit apa") || lowerMsg.contains("diagnosis") || lowerMsg.contains("menderita apa") || lowerMsg.contains("penyakit apa")) {
            return "${prefixRepeat}Jujur saya tidak tahu nama medis penyakitnya Dok. Saya datang berobat karena merasa badan sangat tidak enak dan ingin diperiksa Dokter."
        }

        // Priority 4: Duration / Onset ("Sejak kapan?")
        if (lowerMsg.contains("sejak kapan") || lowerMsg.contains("kapan mulai") || lowerMsg.contains("berapa lama") || lowerMsg.contains("onset") || lowerMsg.contains("tiba-tiba") || lowerMsg.contains("bertahap")) {
            val onsetPeriod = getConsistentOnset(case, history)
            return humanizeMedicalTerms(sanitizePatientResponse("${prefixRepeat}Gejala ini saya rasakan $onsetPeriod Dok, tapi makin ke sini terasa makin berat.", case))
        }

        // Priority 5: Character / Quality of Pain ("Sifat nyeri")
        if (lowerMsg.contains("sifat") || lowerMsg.contains("seperti apa") || lowerMsg.contains("karakter") || lowerMsg.contains("kualitas")) {
            val sifatDesc = when {
                complaint.contains("dada") -> "Dada terasa sangat berat seperti ditindih beban berat dan menusuk-nusuk."
                complaint.contains("perut") -> "Perut rasanya melilit hebat, tajam seperti diiris-iris dan kram."
                complaint.contains("kepala") || complaint.contains("pusing") -> "Kepala berputar melayang dan nyut-nyutan hebat seperti mau pecah."
                complaint.contains("sesak") -> "Napas terasa sangat sempit, dada seperti diikat kencang dan terengah-engah."
                else -> "Rasanya sangat nyeri, ngilu, dan membuat badan lemas tidak berdaya."
            }
            return humanizeMedicalTerms(sanitizePatientResponse("${prefixRepeat}Untuk rasanya, $sifatDesc", case))
        }

        // Priority 6: Location / Radiation ("Menjalar / Tembus")
        if (lowerMsg.contains("tembus") || lowerMsg.contains("menjalar") || lowerMsg.contains("lokasi") || lowerMsg.contains("sebelah") || lowerMsg.contains("di mana")) {
            val lokasiDesc = when {
                complaint.contains("dada") -> "Pusat nyerinya di dada sebelah kiri, terasa tembus ke punggung dan menjalar sampai ke lengan kiri dan leher."
                complaint.contains("perut") -> "Awalnya terasa di ulu hati, lalu nyerinya berpindah dan menetap di perut bagian bawah."
                complaint.contains("pinggang") -> "Di pinggang, lalu nyerinya terasa menjalar turun sampai ke paha."
                else -> "Terasa di bagian tubuh yang sakit ini Dok, menjalar ke area sekitarnya."
            }
            return humanizeMedicalTerms(sanitizePatientResponse("${prefixRepeat}$lokasiDesc", case))
        }

        // Priority 7: Aggravating / Relieving Factors ("Faktor pemicu / memberat")
        if (lowerMsg.contains("faktor") || lowerMsg.contains("memberat") || lowerMsg.contains("berkurang") || lowerMsg.contains("pemicu") || lowerMsg.contains("saat apa") || lowerMsg.contains("gerak") || lowerMsg.contains("istirahat")) {
            return "${prefixRepeat}Kalau dipakai bergerak, aktivitas, atau batuk rasanya makin bertambah berat Dok. Kalau istirahat tiduran cuma sedikit berkurang."
        }

        // Priority 8: Cough & Respiratory Symptoms
        if (lowerMsg.contains("batuk") || lowerMsg.contains("dahak") || lowerMsg.contains("flu") || lowerMsg.contains("pilek")) {
            return if (complaint.contains("batuk") || complaint.contains("sesak") || complaint.contains("demam")) {
                "${prefixRepeat}Iya Dok, ada batuk-batuk berdahak dan dada rasanya agak sesak."
            } else {
                "${prefixRepeat}Tidak ada batuk atau flu Dok, cuma gejala ini yang terasa sangat menyiksa."
            }
        }

        // Priority 9: Bowel & Bladder Symptoms (BAB & BAK)
        if (lowerMsg.contains("bab") || lowerMsg.contains("bak") || lowerMsg.contains("buang air") || lowerMsg.contains("kencing") || lowerMsg.contains("berak") || lowerMsg.contains("diare") || lowerMsg.contains("mencret")) {
            return if (complaint.contains("diare") || complaint.contains("perut") || complaint.contains("mual")) {
                "${prefixRepeat}Buang air besar terasa cair dan berkali-kali Dok, perut melilit tidak tahan."
            } else {
                "${prefixRepeat}Buang air besar dan kencing sejauh ini masih lumayan lancar dan normal Dok."
            }
        }

        // Priority 10: Associated Symptoms - Nausea / Vomiting / Dizziness / Cold Sweats
        if (lowerMsg.contains("mual") || lowerMsg.contains("muntah") || lowerMsg.contains("pusing") || lowerMsg.contains("keringat") || lowerMsg.contains("lemas")) {
            return if (case.isEmergencyCase || complaint.contains("dada") || complaint.contains("perut") || complaint.contains("mual")) {
                "${prefixRepeat}Iya Dok, terasa mual banget serasa mau muntah dan badan mengucur keringat dingin deras."
            } else {
                "${prefixRepeat}Badan terasa lemas sekali dan ada pusing melayang Dok."
            }
        }

        // Priority 11: Associated Symptoms - Shortness of breath / Palpitations
        if (lowerMsg.contains("sesak") || lowerMsg.contains("berdebar") || lowerMsg.contains("napas")) {
            return if (case.rr > 22 || complaint.contains("sesak") || complaint.contains("dada")) {
                "${prefixRepeat}Iya Dok, napas rasanya terengah-engah dan jantung berdebar-debar kencang."
            } else {
                "${prefixRepeat}Napas masih lumayan, tapi kalau rasa nyerinya kumat jadi terasa agak tersengal."
            }
        }

        // Priority 12: Fever / Chills
        if (lowerMsg.contains("demam") || lowerMsg.contains("panas") || lowerMsg.contains("menggigil")) {
            return if (case.suhu > 37.5 || complaint.contains("demam")) {
                "${prefixRepeat}Iya Dok, badan terasa panas demam, kadang menggigil dingin terutama kalau malam."
            } else {
                "${prefixRepeat}Badan tidak terasa demam Dok, cuma lemas dan nyeri."
            }
        }

        // Priority 13: Past Medical History / HT / DM / Heart
        if (lowerMsg.contains("riwayat") || lowerMsg.contains("darah tinggi") || lowerMsg.contains("kencing manis") || lowerMsg.contains("jantung") || lowerMsg.contains("hipertensi") || lowerMsg.contains("diabetes") || lowerMsg.contains("gula")) {
            val rpd = if (case.td.startsWith("15") || case.td.startsWith("16") || case.td.startsWith("17")) "darah tinggi" else "sakit berobat"
            return "${prefixRepeat}Saya ada riwayat $rpd Dok, tapi jujur kadang suka lupa minum obat teratur."
        }

        // Priority 14: Previous Similar Episodes
        if (lowerMsg.contains("sebelumnya") || lowerMsg.contains("serupa") || lowerMsg.contains("dulu pernah")) {
            return "${prefixRepeat}Sebelumnya belum pernah merasakan sakit seberat ini Dok. Ini baru pertama kali timbul hebat."
        }

        // Priority 15: Allergies
        if (lowerMsg.contains("alergi")) {
            return "${prefixRepeat}Setahu saya tidak ada alergi obat atau makanan gatal-gatal Dok."
        }

        // Priority 16: Home Medications
        if (lowerMsg.contains("obat") || lowerMsg.contains("minum") || lowerMsg.contains("di rumah")) {
            return "${prefixRepeat}Sempat minum obat penahan nyeri di rumah Dok, tapi nyerinya tidak hilang sama sekali."
        }

        // Priority 17: Family History
        if (lowerMsg.contains("keluarga") || lowerMsg.contains("orang tua")) {
            return "${prefixRepeat}Di keluarga tidak ada yang menderita sakit seperti ini Dok, alhamdulillah keluarga sehat."
        }

        // Priority 18: Chief Complaint / General Question
        if (lowerMsg.contains("keluhan") || lowerMsg.contains("masalah") || lowerMsg.contains("kenapa") || lowerMsg.contains("ke sini") || lowerMsg.contains("ada apa")) {
            val naturalBody = constructNaturalGreetingBody(cleanComplaint)
            return sanitizePatientResponse("${prefixRepeat}Saya datang ke sini Dok, karena $naturalBody, rasanya tidak nyaman sekali dari tadi.", case)
        }

        // Default Fallback
        val naturalBody = constructNaturalGreetingBody(cleanComplaint)
        return sanitizePatientResponse("${prefixRepeat}Mengenai hal itu Dok, yang paling saya rasakan mengganggu adalah $naturalBody, rasanya sangat tidak nyaman dan membuat saya khawatir.", case)
    }

    private fun normalizeMedicalTerm(term: String): String {
        var clean = term.lowercase()
            .replace(Regex("[^a-z0-9\\s]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()

        val replacements = listOf(
            "stemi" to "infark miokard akut st elevation",
            "nstemi" to "infark miokard akut non st elevation",
            "ima" to "infark miokard akut",
            "kad" to "ketoasidosis diabetikum",
            "dm t2" to "diabetes mellitus tipe 2",
            "dmt2" to "diabetes mellitus tipe 2",
            "dm" to "diabetes mellitus",
            "gea" to "gastroenteritis akut",
            "isk" to "infeksi saluran kemih",
            "uti" to "infeksi saluran kemih",
            "chf" to "gagal jantung kongestif",
            "bppv" to "benign paroxysmal positional vertigo",
            "dhf" to "demam berdarah dengue",
            "dbd" to "demam berdarah dengue",
            "ppok" to "penyakit paru obstruktif kronis",
            "copd" to "penyakit paru obstruktif kronis",
            "ispa" to "infeksi saluran pernapasan akut",
            "cva" to "stroke",
            "appendicitis" to "apendisitis",
            "dyspepsia" to "dispepsia",
            "anaphylaxis" to "anafilaksis",
            "pneumonia" to "pneumoni",
            "tbc" to "tuberkulosis",
            "tb" to "tuberkulosis",
            "ht" to "hipertensi",
            "fracture" to "fraktur",
            "cholelithiasis" to "kolelitiasis",
            "nephrolitiasis" to "nefrolitiasis"
        )

        for ((abbr, full) in replacements) {
            if (clean == abbr || clean.startsWith("$abbr ") || clean.endsWith(" $abbr") || clean.contains(" $abbr ")) {
                clean = clean.replace(Regex("\\b$abbr\\b"), full)
            }
        }
        return clean
    }

    private fun levenshteinDistance(s1: String, s2: String): Int {
        val m = s1.length
        val n = s2.length
        val dp = Array(m + 1) { IntArray(n + 1) }
        for (i in 0..m) dp[i][0] = i
        for (j in 0..n) dp[0][j] = j
        for (i in 1..m) {
            for (j in 1..n) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost)
            }
        }
        return dp[m][n]
    }

    private fun calculateSimilarity(s1: String, s2: String): Double {
        if (s1 == s2) return 1.0
        if (s1.isEmpty() || s2.isEmpty()) return 0.0
        val maxLen = maxOf(s1.length, s2.length)
        val dist = levenshteinDistance(s1, s2)
        return (maxLen - dist).toDouble() / maxLen
    }

    private fun isDiagnosisMatch(userDiag: String, targetDiag: String): Boolean {
        if (userDiag.isBlank() || targetDiag.isBlank()) return false

        val uNorm = normalizeMedicalTerm(userDiag)
        val tNorm = normalizeMedicalTerm(targetDiag)

        if (uNorm.contains(tNorm) || tNorm.contains(uNorm)) return true

        val sim = calculateSimilarity(uNorm, tNorm)
        if (sim >= 0.68) return true

        val uWords = uNorm.split(" ").filter { it.length >= 4 }
        val tWords = tNorm.split(" ").filter { it.length >= 4 }

        if (uWords.isNotEmpty() && tWords.isNotEmpty()) {
            val matchingWordsCount = uWords.count { uW ->
                tWords.any { tW -> uW.contains(tW) || tW.contains(uW) || calculateSimilarity(uW, tW) >= 0.72 }
            }
            if (matchingWordsCount >= 1 && (matchingWordsCount.toDouble() / minOf(uWords.size, tWords.size)) >= 0.5) {
                return true
            }
        }

        return false
    }

    fun evaluatePerformance(
        case: ClinicalCase,
        userExams: List<UserExamResult>,
        userPrimaryDiagnosis: String,
        userDifferentials: String,
        userTreatment: String,
        userEducation: String
    ): EvaluationResult {
        // Calculate Cost Analysis
        val totalSpent = userExams.sumOf { it.costRupiah }
        val optimalCost = case.optimalCostEstimate
        val costRatioText = "Rp ${formatRupiah(totalSpent)} vs Optimal Rp ${formatRupiah(optimalCost)}"

        // Diagnosis Evaluation
        val cleanUserDiag = userPrimaryDiagnosis.trim()

        val isBlankDiag = cleanUserDiag.isBlank() || cleanUserDiag == "-"
        val isExactMatch = !isBlankDiag && isDiagnosisMatch(cleanUserDiag, case.trueDiagnosis)
        val isPartialMatch = !isBlankDiag && !isExactMatch && case.differentialDiagnoses.any { diff ->
            isDiagnosisMatch(cleanUserDiag, diff)
        }

        val (diagStatus, diagScore, diagFeedback) = when {
            isBlankDiag -> Triple(
                "SALAH (KOSONG)",
                0,
                "PERINGATAN: Anda TIDAK memasukkan diagnosis kerja utama! Sebagai calon dokter/dokter, menentukan diagnosis kerja adalah kewajiban paling krusial sebelum memulai tatalaksana. Diagnosis sebenarnya: '${case.trueDiagnosis}'."
            )
            isExactMatch -> Triple(
                "BENAR",
                40,
                "Luar biasa! Diagnosis kerja Anda ('$userPrimaryDiagnosis') SANGAT TEPAT dan merujuk pada penyakit yang sama dengan kriteria klinis Kemenkes RI (${case.trueDiagnosis})."
            )
            isPartialMatch -> Triple(
                "KURANG TEPAT",
                20,
                "Diagnosis kerja Anda ('$userPrimaryDiagnosis') KURANG TEPAT / termasuk diagnosis banding. Diagnosis kerja utama yang sebenarnya adalah '${case.trueDiagnosis}'. Harap perhatikan kembali temuan patognomonik."
            )
            else -> Triple(
                "SALAH",
                0,
                "Diagnosis kerja Anda ('$userPrimaryDiagnosis') SALAH! Diagnosis yang benar untuk kasus ini adalah '${case.trueDiagnosis}'."
            )
        }

        // Exam Evaluation & Cost Effectiveness Score
        val chosenPemfisExams = userExams.filter { it.category == ExamCategory.PEMFIS }
        val chosenLabExams = userExams.filter { it.category == ExamCategory.LAB || it.category == ExamCategory.IMAGING }

        val chosenExamNames = userExams.map { it.examName }
        val optimalRequested = case.optimalExamNames.filter { opt ->
            chosenExamNames.any { ch -> ch.contains(opt, ignoreCase = true) || opt.contains(ch, ignoreCase = true) }
        }
        val missedOptimal = case.optimalExamNames.filter { opt ->
            chosenExamNames.none { ch -> ch.contains(opt, ignoreCase = true) || opt.contains(ch, ignoreCase = true) }
        }
        val wastedPemfis = chosenPemfisExams.filter { userEx ->
            case.optimalExamNames.none { opt -> userEx.examName.contains(opt, ignoreCase = true) || opt.contains(userEx.examName, ignoreCase = true) }
        }
        val wastedLab = chosenLabExams.filter { userEx ->
            case.optimalExamNames.none { opt -> userEx.examName.contains(opt, ignoreCase = true) || opt.contains(userEx.examName, ignoreCase = true) }
        }

        // 1. Pemfis Rating & Feedback
        val pemfisStatus = when {
            chosenPemfisExams.isEmpty() -> "TIDAK TELITI"
            wastedPemfis.isNotEmpty() && optimalRequested.none { opt -> chosenPemfisExams.any { it.examName.contains(opt, ignoreCase = true) } } -> "TIDAK TELITI"
            wastedPemfis.isNotEmpty() || missedOptimal.any { opt -> case.availableExams.any { it.name == opt && it.category == ExamCategory.PEMFIS } } -> "KURANG"
            else -> "OPTIMAL"
        }

        val pemfisFeedback = when (pemfisStatus) {
            "TIDAK TELITI" -> "⚠️ TIDAK TELITI: Anda tidak melakukan pemeriksaan fisik terarah / pemeriksaan fisik yang dipilih tidak sesuai dengan indikasi klinis utama pasien."
            "KURANG" -> "⚠️ KURANG: Pemeriksaan fisik yang Anda lakukan belum lengkap atau terdapat beberapa pemeriksaan fisik yang kurang relevan (${wastedPemfis.joinToString { it.examName }})."
            else -> "✅ OPTIMAL: Pemeriksaan fisik sangat teliti, terarah, dan langsung menemukan tanda patognomonik utama pasien."
        }

        // 2. Cost Rating & Feedback
        val isCostOptimal = totalSpent <= (optimalCost * 1.25)
        val costStatus = when {
            wastedLab.isNotEmpty() || totalSpent > (optimalCost * 1.35) -> "TIDAK TELITI"
            chosenLabExams.isEmpty() && case.optimalExamNames.any { opt -> case.availableExams.any { it.name == opt && (it.category == ExamCategory.LAB || it.category == ExamCategory.IMAGING) } } -> "KURANG"
            else -> "OPTIMAL"
        }

        val examFeedbackBuilder = StringBuilder()
        when (costStatus) {
            "TIDAK TELITI" -> {
                val wastedNames = wastedLab.map { it.examName }.ifEmpty { listOf("Pemeriksaan Non-Esensial") }.joinToString(", ")
                examFeedbackBuilder.append("🚨 TIDAK TELITI / OVER-INVESTIGATION: Terdapat pemborosan pada pemeriksaan penunjang ($wastedNames). Total pengeluaran Rp ${formatRupiah(totalSpent)} (Optimal: Rp ${formatRupiah(optimalCost)}). Hindari memesan lab/radiologi mahal yang tidak mengubah kriteria taktis.\n")
            }
            "KURANG" -> {
                examFeedbackBuilder.append("⚠️ KURANG: Pengeluaran sangat minim (Rp ${formatRupiah(totalSpent)}), namun Anda melewatkan laboratorium / radiologi esensial yang diperlukan untuk mengonfirmasi diagnosis.\n")
            }
            else -> {
                examFeedbackBuilder.append("✅ OPTIMAL: Analisis biaya & pemilihan lab/radiologi sangat efisien, tepat sasaran, dan cost-effective (Rp ${formatRupiah(totalSpent)}).\n")
            }
        }
        if (missedOptimal.isNotEmpty()) {
            examFeedbackBuilder.append("Pemeriksaan Esensial Terlewatkan: ${missedOptimal.joinToString(", ")}\n")
        }

        val optimalRatio = optimalRequested.size.toDouble() / case.optimalExamNames.size.coerceAtLeast(1)
        val examScore = (optimalRatio * 25).toInt() + (if (costStatus == "OPTIMAL") 10 else 0)

        // 3. Treatment Rating & Feedback
        val cleanTreatment = userTreatment.trim().lowercase()
        val treatmentStatus = when {
            cleanTreatment.isBlank() || cleanTreatment.length < 5 -> "TIDAK TELITI"
            cleanTreatment.length > 20 && case.recommendedTreatment.lowercase().split(" ").any { word -> word.length > 4 && cleanTreatment.contains(word) } -> "OPTIMAL"
            else -> "KURANG"
        }

        val treatmentScore = when (treatmentStatus) {
            "OPTIMAL" -> 20
            "KURANG" -> 10
            else -> 0
        }

        val treatmentFeedback = when (treatmentStatus) {
            "OPTIMAL" -> "✅ OPTIMAL: Regimen tatalaksana & pemberian obat sudah tepat, mencakup dosis dan rute yang sesuai. Pedoman Kemenkes: ${case.kemenkesGuidelines}"
            "KURANG" -> "⚠️ KURANG: Tatalaksana & obat yang diberikan kurang spesifik atau dosis/rute belum lengkap. Pedoman Kemenkes: ${case.kemenkesGuidelines}"
            else -> "⚠️ TIDAK TELITI: Anda tidak menyusun tatalaksana obat dengan jelas. Pedoman Kemenkes: ${case.kemenkesGuidelines}"
        }

        // 4. Education Rating & Feedback
        val cleanEdu = userEducation.trim()
        val educationStatus = when {
            cleanEdu.isBlank() || cleanEdu.length < 5 -> "TIDAK TELITI"
            cleanEdu.length > 25 -> "OPTIMAL"
            else -> "KURANG"
        }

        val eduScore = when (educationStatus) {
            "OPTIMAL" -> 15
            "KURANG" -> 8
            else -> 0
        }

        val educationFeedback = when (educationStatus) {
            "OPTIMAL" -> "✅ OPTIMAL: Konseling & edukasi pasien disampaikan dengan sangat lengkap mencakup instruksi tirah baring, batas aktivitas, serta tanda bahaya (red flags)."
            "KURANG" -> "⚠️ KURANG: Edukasi pasien masih terlalu singkat. Sertakan informasi komplikasi dan panduan kapan harus ke IGD."
            else -> "⚠️ TIDAK TELITI: Konseling & edukasi pasien diabaikan atau belum diberikan."
        }

        val totalScore = (diagScore + examScore + treatmentScore + eduScore).coerceIn(0, 100)

        // Generate download code summary format
        val downloadCode = """
            ====================================================
            CLINICAL CASE SIMULATOR - REPORT RESULT
            ====================================================
            ID Kasus: ${case.id}
            Sistem Organ: ${case.organSystem}
            Diagnosis Kerja User: $userPrimaryDiagnosis
            Diagnosis Sebenarnya: ${case.trueDiagnosis}
            Status Diagnosis: $diagStatus ($diagScore/35 Pts)
            Pemeriksaan Fisik: $pemfisStatus
            Analisis Biaya Lab: $costStatus ($examScore/25 Pts) (Rp ${formatRupiah(totalSpent)} vs Optimal Rp ${formatRupiah(optimalCost)})
            Tatalaksana Obat: $treatmentStatus ($treatmentScore/25 Pts)
            Konseling Pasien: $educationStatus ($eduScore/15 Pts)
            Skor Akhir: $totalScore / 100
            ====================================================
            Ringkasan Tatalaksana (Kemenkes/PPK):
            ${case.recommendedTreatment}
            ====================================================
        """.trimIndent()

        return EvaluationResult(
            diagnosisStatus = diagStatus,
            diagnosisFeedback = diagFeedback,
            trueDiagnosis = case.trueDiagnosis,
            pemfisStatus = pemfisStatus,
            pemfisFeedback = pemfisFeedback,
            costStatus = costStatus,
            examFeedback = examFeedbackBuilder.toString(),
            totalSpent = totalSpent,
            optimalCost = optimalCost,
            costRatioText = costRatioText,
            treatmentStatus = treatmentStatus,
            treatmentFeedback = treatmentFeedback,
            educationStatus = educationStatus,
            educationFeedback = educationFeedback,
            diagnosisScore = diagScore,
            examScore = examScore,
            treatmentScore = treatmentScore,
            educationScore = eduScore,
            totalScore = totalScore,
            downloadCode = downloadCode,
            isAiEvaluated = false
        )
    }

    suspend fun evaluatePerformanceWithGemini(
        case: ClinicalCase,
        userExams: List<UserExamResult>,
        userPrimaryDiagnosis: String,
        userDifferentials: String,
        userTreatment: String,
        userEducation: String
    ): EvaluationResult = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        val fallbackEval = evaluatePerformance(case, userExams, userPrimaryDiagnosis, userDifferentials, userTreatment, userEducation)
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext fallbackEval
        }

        try {
            val totalSpent = userExams.sumOf { it.costRupiah }
            val optimalCost = case.optimalCostEstimate

            val prompt = """
                Anda adalah Konsulen Penguji Klinis / Dosen Fakultas Kedokteran Indonesia.
                Tugas Anda adalah MENILAI DAN MENGHITUNG SKOR penanganan kasus klinis medis pasien berikut ini secara objektif dan mendalam.
                
                [DATA KASUS]
                ID Kasus: ${case.id} (${case.title})
                Sistem Organ: ${case.organSystem}
                Diagnosis Sebenarnya: ${case.trueDiagnosis}
                Diagnosis Banding Standar: ${case.differentialDiagnoses.joinToString()}
                Pemeriksaan Esensial: ${case.optimalExamNames.joinToString()}
                Estimasi Biaya Optimal: Rp ${formatRupiah(optimalCost)}
                Tatalaksana Standar Kemenkes: ${case.recommendedTreatment}
                
                [JAWABAN / PERFORMANCE DOKTER PESERTA]
                - Diagnosis Kerja: "$userPrimaryDiagnosis"
                - Diagnosis Banding: "$userDifferentials"
                - Pemeriksaan Fisik & Lab/Penunjang Ditinjau Dokter (${userExams.size} pemeriksaan, Total Biaya Rp ${formatRupiah(totalSpent)}):
                  ${userExams.joinToString { "${it.examName} (${it.category.displayName}, Rp ${formatRupiah(it.costRupiah)}): ${it.result}" }}
                - Regimen Tatalaksana / Obat yang Diberikan: "$userTreatment"
                - Edukasi & Konseling Pasien: "$userEducation"
                
                [PANDUAN RUBRIK PENILAIAN OLEH AI (TOTAL MAKSIMAL 100 POIN)]:
                1. DIAGNOSIS (Maksimal 35 Poin):
                   - Diagnosis Kerja Tepat/Sinonim/Akronim Medis Standar + Diagnosis Banding Relevan: 30 - 35 Poin (diagnosisStatus: "BENAR")
                   - Diagnosis Kurang Spesifik / Tepat sebagian: 15 - 25 Poin (diagnosisStatus: "KURANG TEPAT")
                   - Diagnosis Salah / Organ Berbeda: 0 - 10 Poin (diagnosisStatus: "SALAH")
                2. PEMERIKSAAN FISIK & PENUNJANG / BIAYA (Maksimal 25 Poin):
                   - Pemeriksaan fisik relevan lengkap & pemeriksaan penunjang/lab cost-effective dan sesuai indikasi: 20 - 25 Poin (pemfisStatus: "OPTIMAL", costStatus: "OPTIMAL")
                   - Pemeriksaan fisik/lab cukup namun ada yang terlewat atau sedikit boros biaya: 10 - 19 Poin (pemfisStatus/costStatus: "KURANG")
                   - Terlalu banyak pemeriksaan tidak perlu (pemborosan biaya besar) atau pemeriksaan esensial vital tidak dilakukan: 0 - 9 Poin (pemfisStatus/costStatus: "TIDAK TELITI")
                3. TATALAKSANA & RESEP (Maksimal 25 Poin):
                   - Terapi lini pertama tepat, dosis & rute adekuat, tatalaksana non-farmakologis sesuai PPK: 20 - 25 Poin (treatmentStatus: "OPTIMAL")
                   - Terapi tepat namun dosis/rute kurang lengkap atau obat lini kedua: 10 - 19 Poin (treatmentStatus: "KURANG")
                   - Terapi kontraindikasi / salah golongan obat / tidak rasional: 0 - 9 Poin (treatmentStatus: "TIDAK TELITI")
                4. EDUKASI & KONSELING PASIEN (Maksimal 15 Poin):
                   - Edukasi komprehensif (kepatuhan obat, pencegahan, alarm symptom/red flags, kontrol): 12 - 15 Poin (educationStatus: "OPTIMAL")
                   - Edukasi umum/singkat: 7 - 11 Poin (educationStatus: "KURANG")
                   - Edukasi sangat minim/kosong/tidak relevan: 0 - 6 Poin (educationStatus: "TIDAK TELITI")

                [OUTPUT JSON FORMAT]:
                Keluarkan HANYA JSON Object murni dengan keys:
                - diagnosisStatus: string ("BENAR", "KURANG TEPAT", atau "SALAH")
                - diagnosisFeedback: string (evaluasi mendalam ketepatan diagnosis kerja & diagnosis banding)
                - diagnosisScore: number (integer 0-35)
                - pemfisStatus: string ("OPTIMAL", "KURANG", atau "TIDAK TELITI")
                - pemfisFeedback: string (evaluasi kelengkapan & ketelitian pemeriksaan fisik yang dilakukan)
                - costStatus: string ("OPTIMAL", "KURANG", atau "TIDAK TELITI")
                - examFeedback: string (evaluasi efisiensi biaya & kesesuaian lab/radiologi)
                - examScore: number (integer 0-25)
                - treatmentStatus: string ("OPTIMAL", "KURANG", atau "TIDAK TELITI")
                - treatmentFeedback: string (evaluasi pemberian obat, dosis, rute, & ketepatan tatalaksana)
                - treatmentScore: number (integer 0-25)
                - educationStatus: string ("OPTIMAL", "KURANG", atau "TIDAK TELITI")
                - educationFeedback: string (evaluasi kualitas konseling & edukasi pasien)
                - educationScore: number (integer 0-15)
                - totalScore: number (integer 0-100, hasil penjumlahan: diagnosisScore + examScore + treatmentScore + educationScore)
                - downloadCode: string (laporan ringkas teks)

                ATURAN KHUSUS EVALUASI DIAGNOSIS KERJA:
                1. Berikan rating "BENAR" jika diagnosis kerja peserta merujuk pada penyakit yang sama dengan "Diagnosis Sebenarnya" (${case.trueDiagnosis}).
                2. WAJIB DIBENARKAN ("BENAR") apabila:
                   - Terdapat typo/salah ketik ringan (contoh: "apendisit" atau "appendisitis" -> Apendisitis Akut, "dyspepsia" -> Dispepsia, "ceftriaxon" -> Ceftriaxone).
                   - Menggunakan singkatan medis standar (contoh: "STEMI" / "NSTEMI" -> Infark Miokard Akut, "KAD" -> Ketoasidosis Diabetikum, "DM T2" -> Diabetes Mellitus Tipe 2, "GEA" -> Gastroenteritis Akut, "ISK" -> Infeksi Saluran Kemih, "CHF" -> Gagal Jantung Kongestif, "DBD" / "DHF" -> Demam Berdarah, "PPOK" / "COPD" -> Penyakit Paru Obstruktif Kronis, "CVA" -> Stroke).
                   - Menggunakan istilah medis sinonim/bahasa Indonesia/Inggris (contoh: Appendicitis vs Apendisitis, Stroke vs CVA, Fraktur vs Fracture).
                3. HANYA beri rating "SALAH" apabila diagnosis peserta merujuk pada penyakit/organ yang secara medis berbeda sama sekali.
            """.trimIndent()

            val root = JSONObject()
            val contentsArray = JSONArray()
            val partObj = JSONObject().put("text", prompt)
            val contentObj = JSONObject().put("role", "user").put("parts", JSONArray().put(partObj))
            contentsArray.put(contentObj)
            root.put("contents", contentsArray)

            val systemInstruction = "Anda adalah Konsulen Penguji OSCE Medis Indonesia. Hitunglah skor peserta berdasarkan rubrik secara cermat dan keluarkan HANYA VALID JSON OBJECT MURNI tanpa markdown."
            val systemPart = JSONObject().put("text", systemInstruction)
            val systemContent = JSONObject().put("parts", JSONArray().put(systemPart))
            root.put("systemInstruction", systemContent)

            val genConfig = JSONObject()
            genConfig.put("responseMimeType", "application/json")
            root.put("generationConfig", genConfig)

            val modelsToTry = listOf("gemini-3.5-flash", "gemini-3.1-flash-lite-preview", "gemini-flash-latest", "gemini-3.1-pro-preview")
            for (modelName in modelsToTry) {
                try {
                    val url = "${BASE_URL}$modelName:generateContent?key=$apiKey"
                    val body = root.toString().toRequestBody("application/json".toMediaType())
                    val request = Request.Builder().url(url).post(body).build()

                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string() ?: ""

                    if (response.isSuccessful && responseString.isNotEmpty()) {
                        val jsonResp = JSONObject(responseString)
                        val candidates = jsonResp.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val cand = candidates.getJSONObject(0)
                            val content = cand.optJSONObject("content")
                            val parts = content?.optJSONArray("parts")
                            if (parts != null && parts.length() > 0) {
                                val text = parts.getJSONObject(0).optString("text", "")
                                val cleanedJson = text.replace("```json", "").replace("```", "").trim()
                                val obj = JSONObject(cleanedJson)

                                val costRatioText = "Rp ${formatRupiah(totalSpent)} / Optimal Rp ${formatRupiah(optimalCost)}"

                                val aiDiagScore = obj.optInt("diagnosisScore", fallbackEval.diagnosisScore)
                                val aiExamScore = obj.optInt("examScore", fallbackEval.examScore)
                                val aiTreatScore = obj.optInt("treatmentScore", fallbackEval.treatmentScore)
                                val aiEduScore = obj.optInt("educationScore", fallbackEval.educationScore)
                                val computedAiTotal = (aiDiagScore + aiExamScore + aiTreatScore + aiEduScore).coerceIn(0, 100)
                                val aiTotalScore = if (obj.has("totalScore")) obj.optInt("totalScore", computedAiTotal) else computedAiTotal

                                return@withContext EvaluationResult(
                                    diagnosisStatus = obj.optString("diagnosisStatus", fallbackEval.diagnosisStatus),
                                    diagnosisFeedback = obj.optString("diagnosisFeedback", fallbackEval.diagnosisFeedback),
                                    trueDiagnosis = case.trueDiagnosis,
                                    pemfisStatus = obj.optString("pemfisStatus", fallbackEval.pemfisStatus),
                                    pemfisFeedback = obj.optString("pemfisFeedback", fallbackEval.pemfisFeedback),
                                    costStatus = obj.optString("costStatus", fallbackEval.costStatus),
                                    examFeedback = obj.optString("examFeedback", fallbackEval.examFeedback),
                                    totalSpent = totalSpent,
                                    optimalCost = optimalCost,
                                    costRatioText = costRatioText,
                                    treatmentStatus = obj.optString("treatmentStatus", fallbackEval.treatmentStatus),
                                    treatmentFeedback = obj.optString("treatmentFeedback", fallbackEval.treatmentFeedback),
                                    educationStatus = obj.optString("educationStatus", fallbackEval.educationStatus),
                                    educationFeedback = obj.optString("educationFeedback", fallbackEval.educationFeedback),
                                    diagnosisScore = aiDiagScore,
                                    examScore = aiExamScore,
                                    treatmentScore = aiTreatScore,
                                    educationScore = aiEduScore,
                                    totalScore = aiTotalScore,
                                    downloadCode = obj.optString("downloadCode", fallbackEval.downloadCode),
                                    isAiEvaluated = true
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Failed model $modelName in eval, trying next", e)
                }
            }
            return@withContext fallbackEval
        } catch (e: Exception) {
            Log.e(TAG, "Error evaluating with Gemini AI", e)
            return@withContext fallbackEval
        }
    }

    suspend fun getSuggestionsForInput(
        inputText: String,
        type: String // "DIAGNOSIS", "DIFFERENTIAL", "DRUG", "CITO"
    ): List<String> = withContext(Dispatchers.IO) {
        val cleanInput = inputText.trim()
        val apiKey = getApiKey()

        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getLocalFallbackSuggestions(cleanInput, type)
        }

        try {
            val systemInstruction = when (type) {
                "EXAM" -> """
                    Anda adalah asisten cerdas pencarian pemeriksaan penunjang medis (Pemeriksaan Fisik, Laboratorium, Radiologi & EKG).
                    Tugas Anda: Membaca teks ketikan dokter/pemain: "$cleanInput" dan memberikan 5-8 saran nama pemeriksaan medis yang cocok dengan apa yang sedang diketik atau dicari oleh pemain (autocomplete, deteksi typo/salah eja, singkatan klinis, sinonim, atau kata kunci), baik pemeriksaan standar maupun kustom/subspesialistik di luar database dasar.
                    
                    ATURAN PENTING:
                    1. JANGAN memberikan bocoran diagnosis atau saran terbaik kasus! Berikan MURNI nama pemeriksaan medis yang relevan dengan APA YANG DIKETIK / DICARI PEMAIN.
                    2. BEBAS DATABASE: Anda TIDAK dibatasi oleh daftar pemeriksaan standar faskes/aplikasi. Anda bebas menyarankan pemeriksaan laboratorium molekuler/genetik, penanda biologis, panel imunologi, tes mikrobiologi, modalitas radiologi/pencitraan modern (CT/MRI/Doppler/Nuklir), prosedur endoskopi/kateterisasi, maupun tes fisik/manuver klinis khusus.
                    3. DETEKSI TYPO & FUZZY MATCHING: Jika pemain salah ketik atau mengetik singkatan/potongan kata (contoh: "hemato", "rontgen torak", "usg abd", "tropo", "d-dim", "ekg", "kreatin", "sgot", "lumbal", "murphy", "rontgen", "urin", "kultur", "mri", "ct", "hb", "widal", "bta", "apendis"): Kenali maksud ketikan tersebut dan berikan 5-8 pilihan nama pemeriksaan medis baku dan lengkap.
                    4. Format Keluaran: MURNI JSON ARRAY of string tanpa markdown atau teks pengantar.
                    Contoh jika ketik "tropo": ["Troponin I / T Kuantitatif", "Troponin T High Sensitive (hs-cTnT)", "CK-MB Kuantitatif", "Troponin I Serial (0 & 3 Jam)", "Mioglobin Kuantitatif"]
                    Contoh jika ketik "torak": ["Rontgen Thorax AP/PA", "CT Scan Thorax Kontras", "USG Thorax Bedside", "Rontgen Thorax Lateral", "High-Resolution CT (HRCT) Thorax"]
                """.trimIndent()

                "CITO" -> """
                    Anda adalah asisten medis gawat darurat & resusitasi AI cerdas tingkat global dan nasional.
                    Tugas Anda: Membaca teks ketikan pemain: "$cleanInput" dan memberikan saran tindakan resusitasi / obat cito emergensi yang sesuai dengan apa yang sedang diketik atau dicari oleh pemain (bukan memberikan bocoran jawaban kasus, melainkan melengkapi atau memperbaiki ketikan pemain).
                    
                    Prinsip Pencarian Berdasarkan Ketikan Pemain:
                    1. TIDAK DIBATASI DATABASE: Anda TIDAK terbatas pada database lokal aplikasi. Gunakan seluruh basis ilmu kedokteran emergensi global (ACLS, ATLS, PALS, airway, breathing, circulation, obat vasoaktif, antidotum, antikonvulsan, bronkodilator, resusitasi cairan, defibrilasi/kardioversi, dll).
                    2. DETEKSI TYPO & PELENGKAPAN KETIKAN: Jika pemain mengetik singkatan, kata terpotong, atau salah eja (misal: "epinep", "adrenal", "intubasi", "torako", "dextros", "d40", "atrop", "dopamin", "norepi", "amiodaron", "dc syok", "kardiovers", "lasik", "furosem", "salbut", "bolus", "mgso4", "suction", "midaso", "morpin", "ett", "opa"): Kenali maksud ketikan tersebut dan berikan 5-8 pilihan nama tindakan cito / obat emergensi lengkap dan baku dengan dosis standarnya.
                    3. KELUHAN / KATA KUNCI GAWAT DARURAT: Jika pemain mengetik jenis kegawatan atau kata kunci (misal: "anafilaktik", "henti jantung", "pneumothorax", "hipoglikemia", "kejang", "stemi", "edema paru"): Berikan 5-8 opsi tindakan cito lini pertama yang relevan dengan kata kunci tersebut.
                    4. Format Keluaran: MURNI JSON ARRAY of string tanpa markdown atau teks pengantar.
                    Contoh jika ketik "epinep": ["Injeksi Epinefrin 0.5 mg IM", "Injeksi Epinefrin 1 mg IV (ACLS)", "Nebulisasi Racemic Epinefrin", "Drip Epinefrin 2-10 mcg/menit IV"]
                    Contoh jika ketik "torako": ["Dekompresi Jarum / Needle Thoracocentesis (ICS 2)", "Pemasangan Chest Tube / WSD (ICS 5)"]
                """.trimIndent()

                "DRUG" -> """
                    Anda adalah asisten farmakologi medis AI cerdas tingkat global dan nasional.
                    Tugas Anda: Mencari dan memberikan rekomendasi obat medis riil untuk input pencarian: "$cleanInput".
                    
                    Prinsip Pencarian Online & Bebas Database:
                    1. TIDAK DIBATASI DATABASE/PPK: Anda TIDAK terbatas pada daftar obat standar puskesmas/PPK lokal saja. Gunakan seluruh basis pengetahuan farmakologi medis global (obat generik, obat paten, terapi biologis, obat subspesialistik, obat langka, maupun internasional).
                    2. DETEKSI TYPO & FUZZY MATCHING: Jika pengguna mengetik nama obat dengan typo / salah eja / salah fonetik / kurang huruf (misal: "parastamol", "omeprasol", "amoxilin", "ibupropen", "ceftria", "ciprofloksasin", "statin", "aspilet", "kaptopril", "metronidasol", "salbutamol", "deksametason", "lasix", "ondanset", "remdesivir", "pembrolizumab"): Kenali kata typo tersebut dan berikan 5-8 nama obat resmi yang benar, lengkap dengan dosis standar.
                    3. NAMA OBAT / KELUHAN / INDIKASI: Jika pengguna mengetik nama keluhan, indikasi, atau patologi (misal: "aterosklerosis", "nyeri dada", "hipertensi", "stemi", "gerd", "sesak", "kanker", "artritis"): Berikan 5-8 pilihan obat lini pertama atau terapi spesifik yang paling tepat.
                    4. Format Keluaran: MURNI JSON ARRAY of string tanpa markdown atau teks pengantar.
                    Contoh: ["Atorvastatin 20mg", "Aspirin 80mg", "Clopidogrel 75mg", "Rosuvastatin 10mg", "Ticagrelor 90mg"]
                """.trimIndent()

                "DIFFERENTIAL" -> """
                    Anda adalah konsulen medis AI cerdas tingkat global dan klinis komprehensif.
                    Tugas Anda: Mencari dan memberikan rekomendasi diagnosis banding medis untuk input: "$cleanInput".
                    
                    Prinsip Pencarian Online & Bebas Database:
                    1. TIDAK DIBATASI DATABASE/PPK: Anda TIDAK dibatasi hanya pada daftar penyakit PPK FKTP tertentu. Gunakan seluruh pengetahuan nosologi kedokteran dunia, ICD-10, subspesialistik, maupun penyakit langka.
                    2. DETEKSI TYPO & FUZZY SPELL CHECK: Jika pengguna mengetikkan nama diagnosis atau istilah medis yang mengandung typo / salah eja (misal: "apendik", "angna", "hipertenci", "diabetis", "aspa", "stemy", "pnemonia", "gert", "gastritik", "meningtis", "epilepci", "kolesist", "nefrolit", "lupus", "myasthenia"): Kenali maksud pengguna dan berikan 5-8 diagnosis banding medis formal yang benar dan relevan.
                    3. KELUHAN / SINDROMA: Jika pengguna mengetik gejala/sindroma (misal: "nyeri dada tipikal", "sesak napas akut", "ikterus", "demam akut", "hemoptisis"): Berikan 5-8 diferensial diagnosis klinis yang paling relevan.
                    4. Format Keluaran: MURNI JSON ARRAY of string tanpa markdown.
                    Contoh: ["Gastritis Erosif", "Kolesistitis Akut", "Pankreatitis Akut", "Perforasi Gaster", "Gastroenteritis Akut", "Iskemia Mesenterika"]
                """.trimIndent()

                else -> """
                    Anda adalah konsulen medis AI cerdas tingkat global dan klinis komprehensif.
                    Tugas Anda: Mencari dan memberikan rekomendasi diagnosis kerja medis untuk input: "$cleanInput".
                    
                    Prinsip Pencarian Online & Bebas Database:
                    1. TIDAK DIBATASI DATABASE/PPK: Anda TIDAK dibatasi hanya pada database lokal atau panduan PPK FKTP saja. Anda bebas menyarankan SEMUA jenis diagnosis medis riil (ICD-10, sindroma langka, kasus subspesialis, kelainan metabolik, infeksi tropis, keganasan, atau penyakit kardiovaskular/saraf global).
                    2. DETEKSI TYPO & FUZZY SPELL CHECK: Jika pengguna mengetikkan nama penyakit / istilah yang typo / salah ketik / terpotong (misal: "apendik", "apendisiti", "angna", "anginapketoris", "hipertenci", "diabetis", "aspa", "stemy", "pnemonia", "gert", "gastritik", "meningtis", "epilepci", "kolesist", "nefrolit", "tb paru", "kardiomegali", "takotsubo"): Kenali maksud pengguna dan berikan 5-8 diagnosis kerja formal yang benar dan relevan.
                    3. KELUHAN / PATOLOGI / SINGKATAN: Jika pengguna mengetik keluhan, patologi, atau singkatan (misal: "nyeri ulu hati tembus belakang", "aterosklerosis", "dvt", "cad", "arf"): Berikan 5-8 diagnosis kerja spesifik yang akurat.
                    4. Format Keluaran: MURNI JSON ARRAY of string tanpa markdown atau teks pengantar.
                    Contoh: ["Penyakit Jantung Koroner (CAD)", "Penyakit Arteri Perifer (PAD)", "Stroke Iskemik Akut", "Aneurisma Aorta Abdominalis", "Angina Pektoris Stabil"]
                """.trimIndent()
            }

            val root = JSONObject()
            val contentsArray = JSONArray()
            val partObj = JSONObject().put("text", "Berikan daftar saran cerdas untuk teks input: '$cleanInput'")
            val contentObj = JSONObject().put("role", "user").put("parts", JSONArray().put(partObj))
            contentsArray.put(contentObj)
            root.put("contents", contentsArray)

            val systemPart = JSONObject().put("text", systemInstruction)
            val systemContent = JSONObject().put("parts", JSONArray().put(systemPart))
            root.put("systemInstruction", systemContent)

            val url = "${BASE_URL}gemini-3.5-flash:generateContent?key=$apiKey"
            val body = root.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder().url(url).post(body).build()

            val response = client.newCall(request).execute()
            val responseString = response.body?.string() ?: ""

            if (response.isSuccessful && responseString.isNotEmpty()) {
                val jsonResp = JSONObject(responseString)
                val candidates = jsonResp.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val cand = candidates.getJSONObject(0)
                    val content = cand.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        val text = parts.getJSONObject(0).optString("text", "")
                        val cleanedJson = text.replace("```json", "").replace("```", "").trim()
                        val jsonArray = JSONArray(cleanedJson)
                        val results = mutableListOf<String>()
                        for (i in 0 until jsonArray.length()) {
                            val str = jsonArray.getString(i).trim()
                            if (str.isNotBlank()) results.add(str)
                        }
                        if (results.isNotEmpty()) {
                            return@withContext results
                        }
                    }
                }
            }
            return@withContext getLocalFallbackSuggestions(cleanInput, type)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching Gemini suggestions: ${e.message}", e)
            return@withContext getLocalFallbackSuggestions(cleanInput, type)
        }
    }

    private fun getLocalFallbackSuggestions(cleanInput: String, type: String): List<String> {
        return when (type) {
            "EXAM" -> {
                val catalogExams = com.example.data.repository.MasterExamsCatalog.allMasterExams.map { it.name }
                val matches = catalogExams.filter { it.contains(cleanInput, ignoreCase = true) }
                if (matches.isNotEmpty()) matches.take(8)
                else {
                    val tokens = cleanInput.lowercase().split(" ").filter { it.length >= 2 }
                    val fuzzyMatches = catalogExams.filter { examName ->
                        val lower = examName.lowercase()
                        tokens.any { token -> lower.contains(token) }
                    }
                    if (fuzzyMatches.isNotEmpty()) fuzzyMatches.take(8)
                    else catalogExams.take(6)
                }
            }
            "DRUG" -> {
                com.example.data.repository.MedicalCatalog.filterDrugs(cleanInput).map { it.name }
            }
            "CITO" -> {
                val fallbackCitoList = listOf(
                    "Oksigen Kanul Nasal 2-4 Lpm",
                    "Oksigen NRM 10-15 Lpm (Non-Rebreathing Mask)",
                    "Oksigen Bag-Valve-Mask (BVM) Ventilasi",
                    "Head Tilt - Chin Lift & Jaw Thrust",
                    "Pemasangan Oropharyngeal Airway (Guedel / OPA)",
                    "Suctioning Jalan Napas & Pembersihan Lendir",
                    "Intubasi Endotrakeal (ETT) & Pemasangan Ventilator",
                    "Dekompresi Jarum / Needle Thoracocentesis (ICS 2)",
                    "Pemasangan Chest Tube / WSD (Water Sealed Drainage)",
                    "Pemasangan IV Line 2 Jalur (Large Bore 16G/18G)",
                    "Loading Cairan Kristaloid NaCl 0.9% / Ringer Laktat 500 mL IV",
                    "Resusitasi Cairan Masif Syok (20 mL/kgBB Cepat)",
                    "Injeksi Epinefrin (Adrenalin) 0.5 mg IM",
                    "Injeksi Epinefrin 1 mg IV (Protokol ACLS Henti Jantung)",
                    "Defibrilasi Cepat Asinkron 200-360 Joule (VF / Pulseless VT)",
                    "Kardioversi Tersinkronisasi 100-200 Joule",
                    "Injeksi Sulfas Atropin 0.5 mg IV (Bradikardia Simptomatis)",
                    "Injeksi Amiodaron 300 mg IV Bolus",
                    "Injeksi Dextrose 40% (D40) 2 Ampul (50 mL) IV Bolus",
                    "Injeksi Diazepam 10 mg IV / Rektal (Status Konvulsivus)",
                    "Injeksi Furosemid 40-80 mg IV (Edema Paru Akut)",
                    "Nebulisasi Salbutamol 2.5 mg + Ipratropium Bromida",
                    "Injeksi Dexamethasone 10 mg IV / Metilprednisolon",
                    "Injeksi Morfin 2-4 mg IV",
                    "Injeksi ISDN 5 mg Sublingual",
                    "Injeksi MgSO4 40% 4 gram IV (Preeklampsia / Eklampsia)",
                    "Pemberian Asam Traneksamat 1 gram IV",
                    "Drip Norepinefrin 0.05-0.5 mcg/kg/menit (Syok Septik)",
                    "Drip Dopamin 5-10 mcg/kg/menit (Inotropik Syok)"
                )
                val matches = fallbackCitoList.filter { it.contains(cleanInput, ignoreCase = true) }
                if (matches.isNotEmpty()) matches else fallbackCitoList.take(6)
            }
            else -> {
                com.example.data.repository.MedicalCatalog.filterDiagnoses(cleanInput)
            }
        }
    }

    private fun formatRupiah(amount: Long): String {
        return String.format("%,d", amount).replace(',', '.')
    }

    private fun createProceduralDynamicCase(
        organSystem: String,
        specificDiagnosis: String? = null,
        isEmergency: Boolean = false,
        difficultyLevel: com.example.data.model.DifficultyLevel = com.example.data.model.DifficultyLevel.BASIC
    ): ClinicalCase {
        val targetDiagnosis = if (!specificDiagnosis.isNullOrBlank()) {
            specificDiagnosis
        } else {
            com.example.data.repository.MedicalCatalog.pickTrulyRandomDiagnosis(
                organSystem = organSystem,
                isEmergencyOnly = isEmergency
            )
        }

        return com.example.data.repository.BuiltInCases.createDynamicCaseFromCatalog(
            diag = targetDiagnosis,
            isEmergency = isEmergency || isEmergencyDiagnosis(targetDiagnosis)
        )
    }

    private fun isEmergencyDiagnosis(diagnosis: String): Boolean {
        val dl = diagnosis.lowercase()
        return dl.contains("stemi") || dl.contains("nstemi") || dl.contains("stroke") || dl.contains("syok") ||
                dl.contains("shock") || dl.contains("eklamsia") || dl.contains("perforasi") || dl.contains("ventil") ||
                dl.contains("krisis") || dl.contains("berat") || dl.contains("kejang") || dl.contains("anafilaksis") ||
                dl.contains("ket") || dl.contains("sjs") || dl.contains("perdarahan")
    }

    suspend fun generateDynamicCaseFromGemini(
        organSystem: String,
        specificDiagnosis: String? = null,
        isEmergency: Boolean = false,
        difficultyLevel: com.example.data.model.DifficultyLevel = com.example.data.model.DifficultyLevel.BASIC
    ): ClinicalCase = withContext(Dispatchers.IO) {
        // Pilih diagnosis yang benar-benar acak merata dari seluruh katalog jika tidak ditentukan secara spesifik
        val targetDiagnosis = if (!specificDiagnosis.isNullOrBlank()) {
            specificDiagnosis
        } else {
            com.example.data.repository.MedicalCatalog.pickTrulyRandomDiagnosis(
                organSystem = organSystem,
                isEmergencyOnly = isEmergency
            )
        }

        val apiKey = getApiKey()
        if (apiKey.isNotEmpty() && apiKey != "MY_GEMINI_API_KEY") {
            val modelsToTry = listOf("gemini-3.5-flash", "gemini-3.1-pro-preview", "gemini-3.1-flash-lite-preview")
            for (model in modelsToTry) {
                try {
                    val targetOrgan = if (organSystem.equals("Acak", ignoreCase = true) || organSystem.equals("Random", ignoreCase = true) || organSystem.equals("Semua Sistem Organ", ignoreCase = true)) {
                        listOf("Kardiologi", "Neurologi", "Pulmonologi", "Gastroenterohepatologi", "Nefrologi-Urologi", "Infeksi Tropis", "Pediatri (Kesehatan Anak)", "Obstetri & Ginekologi (Obgyn)", "Dermatovenerologi (Kulit)", "THT-KL", "Oftalmologi (Mata)", "Psikiatri & Jiwa", "Bedah & Trauma").random()
                    } else {
                        organSystem
                    }

                    val emergencyPromptAddon = if (isEmergency) " [MODE CITO / EMERGENSI MEDIS AKUT BERAT - WAJIB KASUS GAWAT DARURAT DARURAT AKUT LIFE THREATENING SEPERTI SHOCK, SEPSIS, STEMI, STROKE, EKLAMSIA, ANAPHYLAXIS, ATAU RUPTUR ORGAN]" else ""
                    val specificDiagAddon = " Diagnosis PASTI yang WAJIB Anda buatkan skenario klinisnya adalah: '$targetDiagnosis'."

                    val systemInstruction = """
                        Anda adalah pembuat kasus klinis medis Indonesia ("Clinical Case Generator") berbasis standar PPK Kemenkes RI, Konsensus Nasional, Harrison's Principles of Internal Medicine, dan Nelson Pediatrics.
                        Buatlah 1 kasus klinis yang realistis dan mendalam untuk sistem organ: $targetOrgan $emergencyPromptAddon.$specificDiagAddon
                        
                        PEDOMAN TINGKAT KESULITAN & BAHASA PASIEN:
                        ${difficultyLevel.promptInstruction}
                        
                        ATURAN KETAT TANDA VITAL (TTV) HARUS SESUAI PATOFISIOLOGI PENYAKIT & USIA PASIEN:
                        - KASUS DEMAM / INFEKSI: Suhu febris (38.0°C - 39.8°C).
                        - KASUS SYOK / PERDARAHAN MASIF / ANAFILAKSIS / DSS: TD HIPOTENSI BERAT (Sistolik 70-85 mmHg, Diastolik 40-55 mmHg), Nadi Cepat & Lemah (130-145x/m), Akral Dingin, RR meningkat (28-36x/m).
                        - KASUS KRISIS HIPERTENSI / PREEKLAMSIA BERAT / EKLAMSIA / STROKE HEMORAGIK: TD TINGGI (Sistolik 170-220 mmHg, Diastolik 105-130 mmHg).
                        - KASUS GANGGUAN RESPIRATORI BERAT / ASMA BERAT / PNEUMOTHORAX / EDEMA PARU / ARDS: RR TAKIPNEU (28-40x/m pada dewasa, 45-60x/m pada bayi) dan SpO2 HIPOKSIA (78% - 91%).
                        - KASUS PEDIATRIK (Bayi/Balita): Nadi normal 100-140x/m, RR 24-45x/m (jangan beri nadi dewasa 70x/m pada bayi!).
                        - KASUS POLIKLINIK NON-EMERGENSI RINGAN: TTV normal sesuai fisiologi usia.
                        
                        Format keluaran HARUS MURNI VALID JSON OBJECT tanpa markdown atau penjelasan lain di luarnya.
                    """.trimIndent()

                    val prompt = """
                        Buatkan kasus klinis medis baru untuk sistem organ "$targetOrgan" dengan diagnosis pasti "$targetDiagnosis" dan tingkat kesulitan ${difficultyLevel.displayName}.$specificDiagAddon
                        Keluaran berupa JSON object murni dengan keys:
                        - id: string ("AI-GEN-1234")
                        - organSystem: string ("$targetOrgan")
                        - title: string (judul kasus ringkas)
                        - patientAge: int
                        - patientGender: string ("Laki-laki" / "Perempuan")
                        - patientOccupation: string (HANYA 1 profesi/pekerjaan tunggal yang spesifik dan realistis, contoh: 'Karyawan Swasta', 'Ibu Rumah Tangga', 'Guru', 'Petani', 'Mahasiswa', 'Pensiunan', 'Pedagang', atau 'Pelajar'. DILARANG KERAS menggabungkan beberapa pekerjaan dengan garis miring seperti 'Karyawan / Ibu Rumah Tangga / Wiraswasta')
                        - generalAppearance: string
                        - chiefComplaint: string (keluhan utama MURNI kalimat awam bahasa Indonesia yang alami dan luwes, contoh: 'Pada bagian paha kanan bawah muncul benjolan', 'Nyeri dada kiri seperti ditindih beban berat', 'Perut kanan bawah terasa sangat sakit'. DILARANG KERAS menyebutkan nama diagnosis medis atau membuat susunan kalimat aneh seperti 'Saya mengalami paha kanan saya di atas lutut ada benjolan'.)
                        - td: string (sesuai patofisiologi, misal "210/120 mmHg" pada krisis hipertensi, "70/40 mmHg" pada syok, "120/80 mmHg" pada kasus stabil)
                        - nadi: int (sesuai penyakit dan usia pasien)
                        - rr: int (sesuai kondisi napas pasien)
                        - suhu: double (sesuai status febris/afebris penyakit, misal 38.8 pada infeksi/demam)
                        - spO2: int (sesuai oksigenasi pasien, misal 88 pada asma/pneumonia berat, 98 pada non-respiratori)
                        - trueDiagnosis: string (wajib persis "$targetDiagnosis")
                        - differentialDiagnoses: array of string
                        - patientPersonaInstruction: string (petunjuk dialog awam)
                        - availableExams: array of object (keys: id, name, category ["PEMFIS"/"LAB"/"IMAGING"], result, costRupiah [number, 0 untuk pemfis]). PENTING: Setiap pemeriksaan HANYA berisi temuan untuk pemeriksaan itu sendiri. Jangan mencampur hasil radiologi/lab ke dalam pemeriksaan fisik, dan sebaliknya.
                        - optimalExamNames: array of string (pemeriksaan esensial)
                        - optimalCostEstimate: number
                        - recommendedTreatment: string (tatalaksana & dosis obat)
                        - kemenkesGuidelines: string (panduan PPK Kemenkes)
                        - pathophysiology: string (penjelasan lengkap mekanisme seluler & patofisiologi penyakit)
                    """.trimIndent()

                    val root = JSONObject()
                    val contentsArray = JSONArray()
                    val partObj = JSONObject().put("text", prompt)
                    val contentObj = JSONObject().put("role", "user").put("parts", JSONArray().put(partObj))
                    contentsArray.put(contentObj)
                    root.put("contents", contentsArray)

                    val systemPart = JSONObject().put("text", systemInstruction)
                    val systemContent = JSONObject().put("parts", JSONArray().put(systemPart))
                    root.put("systemInstruction", systemContent)

                    val url = "${BASE_URL}$model:generateContent?key=$apiKey"
                    val body = root.toString().toRequestBody("application/json".toMediaType())
                    val request = Request.Builder().url(url).post(body).build()

                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string() ?: ""

                    if (response.isSuccessful && responseString.isNotEmpty()) {
                        val jsonResp = JSONObject(responseString)
                        val candidates = jsonResp.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val cand = candidates.getJSONObject(0)
                            val content = cand.optJSONObject("content")
                            val parts = content?.optJSONArray("parts")
                            if (parts != null && parts.length() > 0) {
                                val rawText = parts.getJSONObject(0).optString("text", "")
                                val cleanedJson = rawText.replace("```json", "").replace("```", "").trim()
                                val obj = JSONObject(cleanedJson)

                                val diffList = mutableListOf<String>()
                                val diffArray = obj.optJSONArray("differentialDiagnoses")
                                if (diffArray != null) {
                                    for (i in 0 until diffArray.length()) {
                                        diffList.add(diffArray.getString(i))
                                    }
                                }

                                val examList = mutableListOf<com.example.data.model.ExamItem>()
                                val examArray = obj.optJSONArray("availableExams")
                                if (examArray != null) {
                                    for (i in 0 until examArray.length()) {
                                        val eObj = examArray.getJSONObject(i)
                                        val catStr = eObj.optString("category", "PEMFIS").uppercase()
                                        val cat = when {
                                            catStr.contains("LAB") -> ExamCategory.LAB
                                            catStr.contains("IMAG") || catStr.contains("RAD") -> ExamCategory.IMAGING
                                            else -> ExamCategory.PEMFIS
                                        }
                                        examList.add(
                                            com.example.data.model.ExamItem(
                                                id = eObj.optString("id", "E${i+1}"),
                                                name = eObj.optString("name", "Pemeriksaan ${i+1}"),
                                                category = cat,
                                                result = eObj.optString("result", "Dalam batas normal"),
                                                costRupiah = eObj.optLong("costRupiah", 0L)
                                            )
                                        )
                                    }
                                }

                                val optExamNames = mutableListOf<String>()
                                val optArray = obj.optJSONArray("optimalExamNames")
                                if (optArray != null) {
                                    for (i in 0 until optArray.length()) {
                                        optExamNames.add(optArray.getString(i))
                                    }
                                }

                                val rawCase = ClinicalCase(
                                    id = obj.optString("id", "AI-GEN-${System.currentTimeMillis().toString().takeLast(6)}"),
                                    organSystem = obj.optString("organSystem", targetOrgan),
                                    title = obj.optString("title", if (!specificDiagnosis.isNullOrBlank()) "Kasus $specificDiagnosis" else "Kasus $targetOrgan"),
                                    patientAge = obj.optInt("patientAge", 38),
                                    patientGender = obj.optString("patientGender", "Laki-laki"),
                                    patientOccupation = obj.optString("patientOccupation", "Karyawan Swasta"),
                                    generalAppearance = obj.optString("generalAppearance", "Tampak sakit sedang"),
                                    chiefComplaint = obj.optString("chiefComplaint", "Keluhan pasien"),
                                    td = obj.optString("td", "120/80 mmHg"),
                                    nadi = obj.optInt("nadi", 84),
                                    rr = obj.optInt("rr", 20),
                                    suhu = obj.optDouble("suhu", 36.8),
                                    spO2 = obj.optInt("spO2", 98),
                                    trueDiagnosis = obj.optString("trueDiagnosis", specificDiagnosis ?: "Diagnosis Kerja"),
                                    differentialDiagnoses = if (diffList.isNotEmpty()) diffList else listOf("Diagnosis Banding 1", "Diagnosis Banding 2"),
                                    patientPersonaInstruction = obj.optString("patientPersonaInstruction", "Pasien menjawab dengan bahasa awam"),
                                    availableExams = examList,
                                    optimalExamNames = optExamNames,
                                    optimalCostEstimate = obj.optLong("optimalCostEstimate", 250000L),
                                    recommendedTreatment = obj.optString("recommendedTreatment", "Tatalaksana standar PPK"),
                                    kemenkesGuidelines = obj.optString("kemenkesGuidelines", "Pedoman Kemenkes RI"),
                                    isEmergencyCase = isEmergency,
                                    pathophysiology = obj.optString("pathophysiology", "Patofisiologi penyakit mencakup kaskade seluler dan manifestasi organ target.")
                                )
                                return@withContext com.example.data.repository.MedicalVitalsValidator.validateAndCalibrateCase(rawCase)
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Gemini online case generation attempt with $model failed: ${e.message}")
                }
            }
        }

        return@withContext createProceduralDynamicCase(organSystem, specificDiagnosis, isEmergency, difficultyLevel)
    }

    suspend fun getLiveExamFinding(
        case: ClinicalCase,
        examName: String,
        category: ExamCategory,
        history: List<ChatMessage> = emptyList(),
        existingExams: List<UserExamResult> = emptyList()
    ): String = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            val matched = case.availableExams.find {
                it.name.contains(examName, ignoreCase = true) || examName.contains(it.name, ignoreCase = true)
            }
            if (matched != null) {
                return@withContext matched.result
            }
            return@withContext when (category) {
                ExamCategory.PEMFIS -> "Pemeriksaan fisik '$examName': Ditemukan tanda klinis yang sesuai dengan kondisi patologis ${case.trueDiagnosis} pada pasien."
                ExamCategory.LAB -> "Hasil laboratorium '$examName': Terkonfirmasi parameter abnormal konsisten dengan kondisi klinis ${case.trueDiagnosis}."
                ExamCategory.IMAGING -> "Ekspertise '$examName': Tampak gambaran khas yang mengonfirmasi kecurigaan klinis ${case.trueDiagnosis}."
                else -> "Hasil pemeriksaan '$examName' telah dianalisis sesuai kondisi klinis pasien."
            }
        }

        try {
            val categoryInstruction = when (category) {
                ExamCategory.PEMFIS -> """
                    Peran Anda: Dokter Pemeriksa Fisik (Bedside Clinical Examiner).
                    Tugas: Laporkan HANYA temuan fisik langsung / status lokalis pada regio atau manuver spesifik: '$examName'.
                    
                    ATURAN KETAT (MANDATORY):
                    1. HANYA laporkan temuan pemeriksaan fisik objektif untuk '$examName' (misal inspeksi, palpasi, perkusi, auskultasi, atau tes provokasi yang sesuai).
                    2. DILARANG KERAS menyertakan, menyinggung, atau menambahkan hasil foto Rontgen / Radiologi / CT Scan / USG / MRI / EKG!
                    3. DILARANG KERAS menyertakan hasil tes laboratorium (darah rutin, urin, feses, kimia darah, dll)!
                    4. DILARANG menyebutkan organ tubuh lain yang tidak berhubungan dengan '$examName'.
                    5. DILARANG memberikan kesimpulan diagnosis kerja, diagnosis banding, atau saran tindakan/tatalaksana/pemeriksaan lanjutan.
                    6. Tuliskan temuan secara ringkas, padat, dan murni berupa hasil inspeksi/palpasi/pemeriksaan fisik organ terkait.
                """.trimIndent()

                ExamCategory.LAB -> """
                    Peran Anda: Laboratorium Patologi Klinik Rumah Sakit.
                    Tugas: Laporkan HANYA nilai parameter laboratorium kuantitatif/kualitatif untuk tes: '$examName'.
                    
                    ATURAN KETAT (MANDATORY):
                    1. HANYA laporkan nama parameter, angka hasil, satuan, dan rentang rujukan normal untuk tes '$examName'.
                    2. DILARANG KERAS menyertakan hasil pemeriksaan fisik maupun hasil rontgen / radiologi / imaging.
                    3. DILARANG memberikan diagnosis atau saran resep terapi.
                    4. Tuliskan ringkas layaknya lembar hasil resmi laboratorium.
                """.trimIndent()

                ExamCategory.IMAGING -> """
                    Peran Anda: Dokter Spesialis Radiologi / Tim Diagnostik Imaging & EKG.
                    Tugas: Laporkan HANYA ekspertise deskripsi visual dan kesan radiologis untuk modalitas: '$examName'.
                    
                    ATURAN KETAT (MANDATORY):
                    1. HANYA laporkan temuan visual dari modalitas '$examName' dan Kesan radiologis/kardiologisnya.
                    2. DILARANG KERAS menyertakan nilai laboratorium darah/urin maupun pemeriksaan fisik umum.
                    3. Tuliskan secara ringkas dan profesional sesuai format ekspertise medis.
                """.trimIndent()

                else -> """
                    Peran Anda: Tim Pemeriksa Medis Rumah Sakit.
                    Tugas: Laporkan HANYA temuan spesifik untuk pemeriksaan: '$examName'.
                    DILARANG menambahkan hasil pemeriksaan lain, radiologi tambahan, lab tambahan, atau diagnosis.
                """.trimIndent()
            }

            val systemInstruction = """
                $categoryInstruction
                
                Data Pasien & Kasus:
                - Diagnosis Sebenarnya: ${case.trueDiagnosis}
                - Organ Terkait: ${case.organSystem}
                - Usia: ${case.patientAge} tahun, Gender: ${case.patientGender}
                - Keadaan Umum: ${case.generalAppearance}
                - Tanda Vital: TD ${case.td}, Nadi ${case.nadi}x/mnt, RR ${case.rr}x/mnt, Suhu ${case.suhu}°C, SpO2 ${case.spO2}%
                - Keluhan Utama: ${case.chiefComplaint}
                
                PENTING: Jangan menyapa atau berbasa-basi ("Halo dok", "Berikut hasil"). LANGSUNG laporkan temuan klinis hanya untuk '$examName'.
            """.trimIndent()

            val root = JSONObject()
            val contentsArray = JSONArray()
            val partObj = JSONObject().put("text", "Laporkan HANYA hasil temuan klinis spesifik untuk: '$examName' (Kategori: ${category.name}). Dilarang menambahkan pemeriksaan lain.")
            val contentObj = JSONObject().put("role", "user").put("parts", JSONArray().put(partObj))
            contentsArray.put(contentObj)
            root.put("contents", contentsArray)

            val systemPart = JSONObject().put("text", systemInstruction)
            val systemContent = JSONObject().put("parts", JSONArray().put(systemPart))
            root.put("systemInstruction", systemContent)

            val modelsToTry = listOf("gemini-3.5-flash", "gemini-3.1-pro-preview", "gemini-3.1-flash-lite-preview")
            for (model in modelsToTry) {
                try {
                    val url = "${BASE_URL}$model:generateContent?key=$apiKey"
                    val body = root.toString().toRequestBody("application/json".toMediaType())
                    val request = Request.Builder().url(url).post(body).build()
                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string() ?: ""
                    if (response.isSuccessful && responseString.isNotEmpty()) {
                        val jsonResp = JSONObject(responseString)
                        val candidates = jsonResp.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val text = candidates.getJSONObject(0)
                                .optJSONObject("content")
                                ?.optJSONArray("parts")
                                ?.getJSONObject(0)
                                ?.optString("text", "") ?: ""
                            if (text.isNotBlank()) {
                                var clean = text.trim()
                                    .replace(Regex("^(Berikut\\s+(adalah\\s+)?hasil(\\s+pemeriksaan)?\\s*:\\s*)", RegexOption.IGNORE_CASE), "")
                                    .replace(Regex("^(Halo\\s+Dokter[^\\n]*\\n+)", RegexOption.IGNORE_CASE), "")
                                    .trim()
                                
                                // Post-filter for PEMFIS to sanitize any accidentally leaked unsolicited radiology/lab sections
                                if (category == ExamCategory.PEMFIS) {
                                    val unneededRegex = Regex("(?i)\\n+\\s*(pemeriksaan\\s+penunjang|radiologi|rontgen|laboratorium|hasil\\s+lab|saran\\s+tatalaksana|anjuran\\s+pemeriksaan|diagnosis)\\s*:.*$", setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE))
                                    clean = clean.replace(unneededRegex, "").trim()
                                } else if (category == ExamCategory.LAB) {
                                    val unneededRegex = Regex("(?i)\\n+\\s*(pemeriksaan\\s+fisik|radiologi|rontgen|saran\\s+tatalaksana|diagnosis)\\s*:.*$", setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE))
                                    clean = clean.replace(unneededRegex, "").trim()
                                }

                                if (clean.isNotBlank()) {
                                    return@withContext clean
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Error in getLiveExamFinding model $model", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in getLiveExamFinding", e)
        }

        return@withContext when (category) {
            ExamCategory.PEMFIS -> "Pemeriksaan fisik '$examName': Ditemukan tanda klinis yang sesuai dengan kondisi patologis ${case.trueDiagnosis} pada pasien."
            ExamCategory.LAB -> "Hasil laboratorium '$examName': Terkonfirmasi parameter abnormal konsisten dengan kondisi klinis ${case.trueDiagnosis}."
            ExamCategory.IMAGING -> "Ekspertise '$examName': Tampak gambaran khas yang mengonfirmasi kecurigaan klinis ${case.trueDiagnosis}."
            else -> "Hasil pemeriksaan '$examName' telah dianalisis sesuai kondisi klinis pasien."
        }
    }

    suspend fun getAiDiagnosisConsultation(
        case: ClinicalCase,
        chatHistory: List<ChatMessage>,
        userExams: List<UserExamResult>
    ): String = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isNotEmpty() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val examSummary = if (userExams.isEmpty()) "Belum ada pemeriksaan penunjang/pemfis yang dilakukan." else {
                    userExams.joinToString("\n") { "- ${it.examName} (${it.category.name}): ${it.result}" }
                }

                val chatSummary = chatHistory.filter { it.sender != ChatSender.SYSTEM }.takeLast(8).joinToString("\n") {
                    "${it.sender.name}: ${it.text}"
                }

                val systemInstruction = """
                    Anda adalah Konsulen Klinis & Dokter Spesialis Senior (Clinical Decision Support AI) di Indonesia.
                    Tugas Anda: Memberikan saran diagnosis kerja (Primary Diagnosis) beserta penalaran klinis berdasarkan temuan anamnesis dan pemeriksaan pasien.
                    
                    Data Pasien:
                    - Usia: ${case.patientAge} tahun, ${case.patientGender}
                    - Keluhan Awal: ${case.chiefComplaint}
                    - Tanda Vital: TD ${case.td}, Nadi ${case.nadi}x/m, RR ${case.rr}x/m, Suhu ${case.suhu}°C, SpO2 ${case.spO2}%
                    - Riwayat Anamnesis:
                    $chatSummary
                    - Hasil Pemfis & Laboratorium yang sudah dilakukan:
                    $examSummary
                    
                    Format Output:
                    🎯 **Diagnosis Kerja Disarankan**: [Nama Penyakit Utama]
                    🔍 **Penalaran Klinis (Clinical Reasoning)**:
                    • Anamnesis Kunci: [Temuan utama]
                    • Temuan Kunci Pemfis & Lab: [Temuan objektif]
                    • Rekomendasi Langkah Lanjutan: [Tindakan kunci]
                """.trimIndent()

                val root = JSONObject()
                val contentsArray = JSONArray()
                val partObj = JSONObject().put("text", "Berikan analisis dan rekomendasi diagnosis kerja untuk kasus ini.")
                val contentObj = JSONObject().put("role", "user").put("parts", JSONArray().put(partObj))
                contentsArray.put(contentObj)
                root.put("contents", contentsArray)

                val systemPart = JSONObject().put("text", systemInstruction)
                val systemContent = JSONObject().put("parts", JSONArray().put(systemPart))
                root.put("systemInstruction", systemContent)

                val modelsToTry = listOf("gemini-3.5-flash", "gemini-3.1-pro-preview", "gemini-3.1-flash-lite-preview")
                for (model in modelsToTry) {
                    try {
                        val url = "${BASE_URL}$model:generateContent?key=$apiKey"
                        val body = root.toString().toRequestBody("application/json".toMediaType())
                        val request = Request.Builder().url(url).post(body).build()
                        val response = client.newCall(request).execute()
                        val responseString = response.body?.string() ?: ""
                        if (response.isSuccessful && responseString.isNotEmpty()) {
                            val jsonResp = JSONObject(responseString)
                            val candidates = jsonResp.optJSONArray("candidates")
                            if (candidates != null && candidates.length() > 0) {
                                val text = candidates.getJSONObject(0)
                                    .optJSONObject("content")
                                    ?.optJSONArray("parts")
                                    ?.getJSONObject(0)
                                    ?.optString("text", "") ?: ""
                                if (text.isNotBlank()) return@withContext text.trim()
                            }
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "Error in getAiDiagnosisConsultation model $model", e)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in getAiDiagnosisConsultation", e)
            }
        }

        val examFindings = if (userExams.isNotEmpty()) {
            userExams.take(3).joinToString(", ") { "${it.examName} (${it.result.take(60)}...)" }
        } else "Tanda vital menunjukkan TD ${case.td}, Nadi ${case.nadi}x/m, RR ${case.rr}x/m, Suhu ${case.suhu}°C, SpO2 ${case.spO2}%"

        val diffs = if (case.differentialDiagnoses.isNotEmpty()) case.differentialDiagnoses.take(3).joinToString(", ") else "kondisi terkait"

        return@withContext """
🎯 **Diagnosis Kerja Disarankan**: ${case.trueDiagnosis}

🔍 **Penalaran Klinis (Clinical Reasoning)**:
• Anamnesis Kunci: Keluhan "${case.chiefComplaint}" konsisten dengan manifestasi klinis pada pasien usia ${case.patientAge} tahun.
• Temuan Kunci Pemfis & Lab: $examFindings.
• Rekomendasi Langkah Lanjutan: Konfirmasi eksklusi diagnosis banding ($diffs), pastikan pemantauan hemodinamik ketat, dan inisiasi farmakoterapi standar PPK Kemenkes RI.
        """.trimIndent()
    }

    suspend fun evaluateCitoActionWithAI(
        case: ClinicalCase,
        actionName: String,
        priorCitoLogs: List<CitoActionFeedback> = emptyList()
    ): CitoActionFeedback = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext com.example.data.repository.CitoEvaluator.evaluateActionFallback(actionName, case, priorCitoLogs)
        }

        try {
            val priorActionsText = if (priorCitoLogs.isEmpty()) "Belum ada tindakan resusitasi sebelumnya."
            else priorCitoLogs.joinToString("; ") { "${it.actionTitle} [Hasil: ${it.impactType.name}, Efek: ${it.updatedVitalsNote}]" }

            val systemInstruction = """
                Anda adalah Dokter Spesialis Emergensi & Resusitasi Kritis Senior (Emergency Medicine & Critical Care Specialist).
                Tugas Anda: Mengevaluasi secara manual, dinamis, dan ilmiah efek fisiologis, perubahan tanda vital (TTV), respon hemodinamik, serta dampak klinis dari suatu tindakan tatalaksana resusitasi/kegawatdaruratan (CITO) yang diaplikasikan oleh dokter muda pada pasien kasus gawat darurat.

                DILARANG MENGGUNAKAN JAWABAN TEMPLATE ATAU KATALOG KAKU. Hitung respons fisiologis secara unik berdasarkan patofisiologi penyakit, usia, dan data klinis pasien saat ini!

                INFORMASI PASIEN:
                - Diagnosis Pasti: ${case.trueDiagnosis}
                - Keluhan Utama: ${case.chiefComplaint}
                - Usia / Gender: ${case.patientAge} tahun / ${case.patientGender}
                - Keadaan Umum: ${case.generalAppearance}
                - Tanda Vital Saat Ini: TD ${case.td}, Nadi ${case.nadi}x/m, RR ${case.rr}x/m, Suhu ${case.suhu}°C, SpO2 ${case.spO2}%
                - Patofisiologi Dasar: ${case.pathophysiology}
                - Rekomendasi Tatalaksana Ideal: ${case.recommendedTreatment}
                - Riwayat Tindakan Cito Sebelumnya: $priorActionsText

                TINDAKAN CITO YANG DILAKUKAN DOKTER SAAT INI:
                "$actionName"

                KRITERIA EVALUASI MANUAL AI:
                1. "impactType":
                   - "STABILIZED": Tindakan tepat, sesuai indikasi patofisiologi penyakit, menstabilkan jalan napas/pernapasan/sirkulasi, memperbaiki hemodinamik.
                   - "UNINDICATED": Tindakan tidak diindikasikan (overtreatment/redundant) atau tanda vital pasien saat ini tidak memerlukan tindakan tersebut.
                   - "HARMFUL": Tindakan salah/kontraindikasi yang memperburuk kondisi, menurunkan perfusi, memicu komplikasi, atau membuang waktu kritis.
                   - "FATAL_COLLAPSE": Tindakan sangat mematikan/kontraindikasi mutlak langsung memicu Cardiac Arrest/Kematian (misal ventilasi tekanan positif ETT/BVM pada tension pneumothorax sebelum dekompresi jarum, injeksi insulin pada koma hipoglikemik berat, ISDN/nitrat pada syok kardiogenik dengan TD < 90).

                2. "timeDeltaSeconds":
                   - STABILIZED: +60 s/d +120 detik (bonus waktu resusitasi yang besar untuk intervensi yang tepat & terindikasi)
                   - UNINDICATED: 0 detik
                   - HARMFUL: -30 s/d -60 detik (penalti waktu)
                   - FATAL_COLLAPSE: -999 detik (langsung habis)

                3. "pointPenalty":
                   - STABILIZED: 0
                   - UNINDICATED: 3
                   - HARMFUL: 5
                   - FATAL_COLLAPSE: 20

                4. "message": Respon fisiologis klinis langsung & hemodinamik yang teramati objektif pada pasien (misalnya: akral hangat, pengembangan dada membaik, perfusi pulih, stridor mereda, kesadaran membaik, atau terjadi kolaps).
                   ⚠️ ATURAN MUTLAK ANTI-SPOILER: DILARANG KERAS MENYEBUT ATAU MEMBOCORKAN NAMA DIAGNOSIS/PENYAKIT PASTI PADA FIELD 'message' AGAR TIDAK MEMBOCORKAN DIAGNOSIS PADA STAGE CITO! Hanya deskripsikan perubahan klinis/fisiologis pasien yang tampak nyata.

                5. "detailedExplanation": Penjelasan komprehensif, mendalam, dan berbasis patofisiologi dari konsulen mengenai mekanisme kerja obat/intervensi dan rasionalisasinya terhadap diagnosis penyakit pasien (ini akan ditampilkan pada stage Evaluasi Akhir).

                6. "updatedVitalsNote": String ringkas perubahan nilai TTV baru pasca tindakan (contoh: "TD 115/75 mmHg, Nadi 86x/m, RR 18x/m, SpO2 98%" atau "Henti Jantung / Asistol").

                7. Nilai angka target pasca tindakan:
                   - "targetSystolic": int (contoh: 115)
                   - "targetDiastolic": int (contoh: 75)
                   - "targetHr": int (contoh: 86)
                   - "targetRr": int (contoh: 18)
                   - "targetSpO2": int (contoh: 98)
                   - "targetTemp": double (contoh: 36.8)

                Format Output WAJIB JSON murni:
                {
                  "impactType": "STABILIZED",
                  "timeDeltaSeconds": 90,
                  "pointPenalty": 0,
                  "message": "Akral mulai hangat, perfusi perifer membaik, dan distres napas mereda. Pasien menunjukkan perbaikan hemodinamik.",
                  "detailedExplanation": "Intervensi ini mengatasi vasokonstriksi/spasme bronkus melalui aktivasi reseptor simpatis, memulihkan resistensi vaskular sistemik...",
                  "updatedVitalsNote": "TD 115/75 mmHg, Nadi 86x/m, RR 18x/m, SpO2 98%",
                  "targetSystolic": 115,
                  "targetDiastolic": 75,
                  "targetHr": 86,
                  "targetRr": 18,
                  "targetSpO2": 98,
                  "targetTemp": 36.8
                }
            """.trimIndent()

            val root = JSONObject()
            val contentsArray = JSONArray()
            val partObj = JSONObject().put("text", "Evaluasi efek tatalaksana cito '$actionName' untuk kasus '${case.trueDiagnosis}'.")
            val contentObj = JSONObject().put("role", "user").put("parts", JSONArray().put(partObj))
            contentsArray.put(contentObj)
            root.put("contents", contentsArray)

            val systemPart = JSONObject().put("text", systemInstruction)
            val systemContent = JSONObject().put("parts", JSONArray().put(systemPart))
            root.put("systemInstruction", systemContent)

            val genConfig = JSONObject()
            genConfig.put("responseMimeType", "application/json")
            root.put("generationConfig", genConfig)

            val modelsToTry = listOf("gemini-3.5-flash", "gemini-3.1-flash-lite-preview", "gemini-flash-latest")
            for (model in modelsToTry) {
                try {
                    val url = "${BASE_URL}$model:generateContent?key=$apiKey"
                    val body = root.toString().toRequestBody("application/json".toMediaType())
                    val request = Request.Builder().url(url).post(body).build()
                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string() ?: ""

                    if (response.isSuccessful && responseString.isNotEmpty()) {
                        val jsonResp = JSONObject(responseString)
                        val candidates = jsonResp.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val cand = candidates.getJSONObject(0)
                            val content = cand.optJSONObject("content")
                            val parts = content?.optJSONArray("parts")
                            if (parts != null && parts.length() > 0) {
                                val text = parts.getJSONObject(0).optString("text", "")
                                if (text.isNotBlank()) {
                                    val cleanJson = text.trim().removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
                                    val obj = JSONObject(cleanJson)

                                    val impactTypeStr = obj.optString("impactType", "UNINDICATED")
                                    val impactType = try {
                                        CitoImpactType.valueOf(impactTypeStr)
                                    } catch (e: Exception) {
                                        when {
                                            impactTypeStr.contains("STABILIZ", ignoreCase = true) -> CitoImpactType.STABILIZED
                                            impactTypeStr.contains("FATAL", ignoreCase = true) -> CitoImpactType.FATAL_COLLAPSE
                                            impactTypeStr.contains("HARM", ignoreCase = true) -> CitoImpactType.HARMFUL
                                            else -> CitoImpactType.UNINDICATED
                                        }
                                    }

                                    val timeDelta = obj.optInt("timeDeltaSeconds", when (impactType) {
                                        CitoImpactType.STABILIZED -> 90
                                        CitoImpactType.HARMFUL -> -45
                                        CitoImpactType.FATAL_COLLAPSE -> -999
                                        else -> 0
                                    })

                                    val penalty = obj.optInt("pointPenalty", when (impactType) {
                                        CitoImpactType.UNINDICATED -> 3
                                        CitoImpactType.HARMFUL -> 5
                                        CitoImpactType.FATAL_COLLAPSE -> 20
                                        else -> 0
                                    })

                                    val msg = obj.optString("message", "Intervensi '$actionName' telah diaplikasikan dan dihitung secara manual oleh AI.")
                                    val detailedExp = obj.optString("detailedExplanation", msg)
                                    val vitalsNote = obj.optString("updatedVitalsNote", "")

                                    val targetSys = if (obj.has("targetSystolic")) obj.optInt("targetSystolic") else null
                                    val targetDia = if (obj.has("targetDiastolic")) obj.optInt("targetDiastolic") else null
                                    val targetHr = if (obj.has("targetHr")) obj.optInt("targetHr") else null
                                    val targetRr = if (obj.has("targetRr")) obj.optInt("targetRr") else null
                                    val targetSpO2 = if (obj.has("targetSpO2")) obj.optInt("targetSpO2") else null
                                    val targetTemp = if (obj.has("targetTemp")) obj.optDouble("targetTemp") else null

                                    return@withContext CitoActionFeedback(
                                        actionTitle = actionName,
                                        impactType = impactType,
                                        timeDeltaSeconds = timeDelta,
                                        pointPenalty = penalty,
                                        message = msg,
                                        detailedExplanation = detailedExp,
                                        updatedVitalsNote = vitalsNote,
                                        isAiEvaluated = true,
                                        targetSystolic = targetSys,
                                        targetDiastolic = targetDia,
                                        targetHr = targetHr,
                                        targetRr = targetRr,
                                        targetSpO2 = targetSpO2,
                                        targetTemp = targetTemp
                                    )
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Error in evaluateCitoActionWithAI model $model", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error evaluating Cito action with Gemini", e)
        }

        return@withContext com.example.data.repository.CitoEvaluator.evaluateActionFallback(actionName, case, priorCitoLogs)
    }

    suspend fun getAiDifferentialDiagnosisConsultation(
        case: ClinicalCase,
        chatHistory: List<ChatMessage>,
        userExams: List<UserExamResult>,
        currentPrimary: String = ""
    ): List<String> = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext case.differentialDiagnoses
        }

        try {
            val examSummary = if (userExams.isEmpty()) "Belum ada tes." else userExams.joinToString(", ") { "${it.examName}: ${it.result}" }
            val systemInstruction = """
                Anda adalah Konsulen Medis AI Indonesia.
                Tugas Anda: Berikan 4 sampai 6 Diagnosis Banding (Differential Diagnosis / DD) medis Indonesia yang paling mungkin untuk pasien ini.
                
                Data Pasien:
                - Usia: ${case.patientAge}, ${case.patientGender}
                - Keluhan Utama: ${case.chiefComplaint}
                - Diagnosis Kerja yang dipertimbangkan: ${if (currentPrimary.isNotBlank()) currentPrimary else case.trueDiagnosis}
                - Temuan Pemfis & Lab: $examSummary
                
                Keluaran HARUS MURNI JSON ARRAY of string tanpa markdown atau teks lain.
                Contoh: ["NSTEMI", "Dispepsia Fungsional", "Gastroesophageal Reflux Disease (GERD)", "Pleuritis"]
            """.trimIndent()

            val root = JSONObject()
            val contentsArray = JSONArray()
            val partObj = JSONObject().put("text", "Buatkan list differential diagnosis.")
            val contentObj = JSONObject().put("role", "user").put("parts", JSONArray().put(partObj))
            contentsArray.put(contentObj)
            root.put("contents", contentsArray)

            val systemPart = JSONObject().put("text", systemInstruction)
            val systemContent = JSONObject().put("parts", JSONArray().put(systemPart))
            root.put("systemInstruction", systemContent)

            val modelsToTry = listOf("gemini-3.5-flash", "gemini-3.1-pro-preview", "gemini-3.1-flash-lite-preview")
            for (model in modelsToTry) {
                try {
                    val url = "${BASE_URL}$model:generateContent?key=$apiKey"
                    val body = root.toString().toRequestBody("application/json".toMediaType())
                    val request = Request.Builder().url(url).post(body).build()
                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string() ?: ""
                    if (response.isSuccessful && responseString.isNotEmpty()) {
                        val jsonResp = JSONObject(responseString)
                        val candidates = jsonResp.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val text = candidates.getJSONObject(0)
                                .optJSONObject("content")
                                ?.optJSONArray("parts")
                                ?.getJSONObject(0)
                                ?.optString("text", "") ?: ""
                            val cleaned = text.replace("```json", "").replace("```", "").trim()
                            val jsonArr = JSONArray(cleaned)
                            val list = mutableListOf<String>()
                            for (i in 0 until jsonArr.length()) {
                                list.add(jsonArr.getString(i))
                            }
                            if (list.isNotEmpty()) return@withContext list
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Error in getAiDifferentialDiagnosisConsultation model $model", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in getAiDifferentialDiagnosisConsultation", e)
        }

        return@withContext case.differentialDiagnoses
    }

    suspend fun getAiMedicationConsultation(
        case: ClinicalCase,
        diagnosis: String,
        userExams: List<UserExamResult>
    ): String = withContext(Dispatchers.IO) {
        val apiKey = getApiKey()
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getGeminiTreatmentSuggestions(diagnosis)
        }

        try {
            val examSummary = if (userExams.isEmpty()) "Tidak ada kelainan lab spesifik." else userExams.joinToString(", ") { "${it.examName}: ${it.result}" }
            val systemInstruction = """
                Anda adalah Dokter Spesialis Farmakologi Klinis & Konsulen PPK Kemenkes RI.
                Tugas Anda: Memberikan Rencana Resep & Farmakoterapi Lini Pertama Indonesia yang spesifik untuk pasien ini.
                
                Data Pasien:
                - Usia: ${case.patientAge} tahun, Gender: ${case.patientGender}
                - Diagnosis: $diagnosis
                - Tanda Vital: TD ${case.td}, Nadi ${case.nadi}, Suhu ${case.suhu}°C
                - Hasil Penunjang: $examSummary
                
                Format Output:
                Tuliskan resep terstruktur dalam format siap salin (bullet point dengan nama obat generik, sediaan, rute, frekuensi, dan aturan minum sesuai standar PPK):
                • [Nama Obat 1] ([Sediaan], [Rute], [Frekuensi] [Aturan Minum])
                • [Nama Obat 2] ([Sediaan], [Rute], [Frekuensi] [Aturan Minum])
                • [Cairan / Terapi Suportif jika diperlukan]
                
                Sertakan pula catatan ringkas:
                - Indikasi Utama
                - Kontraindikasi / Hal yang perlu diwaspadai
            """.trimIndent()

            val root = JSONObject()
            val contentsArray = JSONArray()
            val partObj = JSONObject().put("text", "Berikan regimen farmakoterapi dan resep obat standar PPK Kemenkes RI.")
            val contentObj = JSONObject().put("role", "user").put("parts", JSONArray().put(partObj))
            contentsArray.put(contentObj)
            root.put("contents", contentsArray)

            val systemPart = JSONObject().put("text", systemInstruction)
            val systemContent = JSONObject().put("parts", JSONArray().put(systemPart))
            root.put("systemInstruction", systemContent)

            val modelsToTry = listOf("gemini-3.5-flash", "gemini-3.1-pro-preview", "gemini-3.1-flash-lite-preview")
            for (model in modelsToTry) {
                try {
                    val url = "${BASE_URL}$model:generateContent?key=$apiKey"
                    val body = root.toString().toRequestBody("application/json".toMediaType())
                    val request = Request.Builder().url(url).post(body).build()
                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string() ?: ""
                    if (response.isSuccessful && responseString.isNotEmpty()) {
                        val jsonResp = JSONObject(responseString)
                        val candidates = jsonResp.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val text = candidates.getJSONObject(0)
                                .optJSONObject("content")
                                ?.optJSONArray("parts")
                                ?.getJSONObject(0)
                                ?.optString("text", "") ?: ""
                            if (text.isNotBlank()) return@withContext text.trim()
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Error in getAiMedicationConsultation model $model", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in getAiMedicationConsultation", e)
        }

        return@withContext getGeminiTreatmentSuggestions(diagnosis)
    }

    data class DualComplaintResult(
        val primaryComplaint: String,
        val secondaryComplaint: String,
        val onsetTimeline: String,
        val fullSummary: String
    )

    fun sanitizePatientResponse(rawText: String, case: ClinicalCase? = null): String {
        var text = rawText
        if (text.isBlank()) return text

        // 0. Intercept legacy template strings or meta-phrases
        if (text.contains("Gejala klinis khas", ignoreCase = true) ||
            text.contains("mencakup keluhan utama spesifik", ignoreCase = true) ||
            text.contains("riwayat onset timbulnya gejala", ignoreCase = true)) {
            val organ = case?.organSystem ?: "Umum"
            val title = case?.title ?: case?.trueDiagnosis ?: ""
            val isEmerg = case?.isEmergencyCase ?: false
            text = com.example.data.repository.PpkGuideRepository.generateRealisticAnamnesisForDisease(title, organ, isEmerg)
        }

        // Comprehensive set of ordinary clinical symptom, anatomy, pain, and conversational words that must NEVER be censored
        val protectedWords = setOf(
            "dengan", "tanpa", "derajat", "akut", "kronis", "kronik", "berat", "sedang", "ringan", "onset", "kiri", "kanan",
            "pada", "dextra", "sinistra", "ec", "atau", "dan", "yang", "kasus", "klinis", "gawat", "darurat", "cito",
            "dada", "perut", "kepala", "pusing", "mual", "muntah", "sesak", "batuk", "demam", "panas", "lemas", "nyeri",
            "sakit", "diare", "mencret", "gatal", "bengkak", "pegal", "lumpuh", "kejang", "pingsan", "darah", "pinggang",
            "mata", "telinga", "hidung", "tenggorokan", "kulit", "sendi", "otot", "lambung", "jantung", "paru", "hati",
            "ginjal", "kemih", "kencing", "berak", "dahak", "luka", "merah", "kuning", "pucat", "dingin", "keringat",
            "haus", "lapar", "tangan", "kaki", "leher", "punggung", "ulu", "rasa", "terasa", "bawah", "atas", "depan",
            "belakang", "badan", "tubuh", "hari", "jam", "bulan", "tahun", "pagi", "siang", "malam", "tiba", "keluhan",
            "tajam", "tumpul", "melilit", "menusuk", "terbakar", "perih", "pedih", "ngilu", "kram", "kebas", "kesemutan",
            "merot", "pelo", "kaku", "linglung", "gelisah", "mengamuk", "cemas", "takut", "tercekik", "menggigil",
            "kering", "basah", "berair", "melepuh", "bintik", "bentol", "ruam", "bercak", "lebam", "benjolan", "nanah",
            "lendir", "berbusa", "ngik", "mengi", "mengorok", "sulit", "susah", "tidak", "bisa", "hilang", "timbul",
            "menjalar", "tembus", "berpindah", "sejak", "kemarin", "tadi", "nafas", "napas", "napasnya", "berdebar",
            "batu", "anyang", "anyang-anyangan", "kencingnya", "berdarah", "terpelintir", "tergores", "tersengat"
        )

        val termsToCensor = mutableSetOf<String>()

        // Censor specific formal medical Latin diagnosis words only
        val strictMedicalJargon = listOf(
            "stemi", "nstemi", "infark miokard", "infark", "miokard", "apendisitis", "appendisitis",
            "ketoasidosis", "ketoasidosis diabetikum", "preeklamsia", "eklamsia", "glaukoma",
            "stroke", "stroke iskemik", "stroke hemoragik", "dengue hemorrhagic", "dhf",
            "ureterolithiasis", "nephrolithiasis", "urolitiasis", "gastroenteritis", "pneumonia",
            "tuberkulosis", "chf", "adhf", "gerd", "bppv", "meningitis", "peritonitis", "hepatitis",
            "sirosis", "tamponade", "pneumothorax", "morbilli", "rubella", "pertusis", "difteri",
            "stevens-johnson", "sjs", "ten", "varisela", "herpes zoster", "psoriasis", "osteoartritis",
            "pyelonefritis", "pielonefritis", "abses peritonsil", "trismus", "quinsy", "kolesistitis",
            "pankreatitis", "preeklamsia berat", "endometriosis", "karsinoma", "melanoma", "limfoma",
            "leukemia", "talasemia", "hemofilia", "spondilosis", "hidronefrosis", "nefritis"
        )
        termsToCensor.addAll(strictMedicalJargon)

        // Replace strict medical jargon with natural pain / condition phrases
        for (term in termsToCensor.filter { it.isNotBlank() }.sortedByDescending { it.length }) {
            if (term.length < 3) continue
            if (protectedWords.contains(term.lowercase())) continue

            val safeTerm = Regex.escape(term)
            val regex = Regex("(?i)\\b$safeTerm\\b")
            if (regex.containsMatchIn(text)) {
                text = regex.replace(text, "rasa sakit ini")
            }
        }

        // Clean any awkward duplicate or robot-like combinations
        text = text
            .replace(Regex("(?i)\\b(keluhan utama ini|keluhan ini|penyakit ini|sakit yang saya rasakan)\\b"), "rasa sakit ini")
            .replace(Regex("(?i)\\b(gejala ini\\s+gejala ini)\\b"), "gejala ini")
            .replace(Regex("(?i)\\b(rasa sakit ini\\s+rasa sakit ini)\\b"), "rasa sakit ini")
            .replace(Regex("(?i)\\b(nyeri\\s+rasa sakit ini)\\b"), "rasa nyeri ini")
            .replace(Regex("(?i)\\b(sakit\\s+rasa sakit ini)\\b"), "rasa sakit ini")
            .replace(Regex("(?i)\\b(merasakan\\s+penyakit ini)\\b"), "merasakan sakit ini")
            .replace(Regex("(?i)\\b(penyakit ini)\\b"), "kondisi ini")
            .replace(Regex("\\s+"), " ")
            .trim()

        return naturalizeIndonesianSentence(humanizeMedicalTerms(text))
    }

    fun naturalizeIndonesianSentence(rawText: String): String {
        if (rawText.isBlank()) return rawText
        var text = rawText

        // 1. Perbaikan susunan kata rancu pada paha / lutut / benjolan / anatomi tubuh
        val awkwardAnatomyPatterns = listOf(
            // "paha kanan saya di atas lutut ada benjolan" -> "pada bagian paha kanan bawah saya ada benjolan"
            Regex("(?i)\\bpaha\\s+(kanan|kiri)\\s+(saya\\s+)?di\\s+atas\\s+lutut\\s+(saya\\s+)?ada\\s+benjolan\\b") to "pada bagian paha $1 bawah saya ada benjolan",
            Regex("(?i)\\bpaha\\s+(kanan|kiri)\\s+(saya\\s+)?di\\s+atas\\s+lutut\\b") to "paha $1 bagian bawah dekat atas lutut",
            Regex("(?i)\\bpaha\\s+(kanan|kiri)\\s+di\\s+bawah\\b") to "bagian paha $1 bawah",
            Regex("(?i)\\bpaha\\s+di\\s+atas\\s+lutut\\s+ada\\s+benjolan\\b") to "pada paha bagian bawah dekat lutut ada benjolan",

            // "saya mengalami paha kanan saya di atas lutut ada benjolan"
            Regex("(?i)\\bsaya\\s+mengalami\\s+paha\\s+(kanan|kiri)\\s+(saya\\s+)?(di\\s+atas\\s+lutut|bagian\\s+bawah)?\\s*(ada\\s+benjolan|bengkak|nyeri|luka)\\b") to "pada bagian paha $1 saya $4",

            // "saya mengalami [bagian tubuh] saya ada [kelainan]"
            Regex("(?i)\\bsaya\\s+mengalami\\s+(paha|perut|dada|kepala|mata|kaki|tangan|leher|pinggang|tenggorokan|kulit|lutut)\\s+(kanan|kiri|bawah|atas|depan|belakang)?\\s*saya\\s+ada\\s+([a-zA-Z]+)\\b") to "pada bagian $1 $2 saya ada $3",

            // "saya mengalami [bagian tubuh] saya terasa [rasa]"
            Regex("(?i)\\bsaya\\s+mengalami\\s+(paha|perut|dada|kepala|mata|kaki|tangan|leher|pinggang|tenggorokan|kulit|lutut)\\s+(kanan|kiri|bawah|atas|depan|belakang)?\\s*saya\\s+terasa\\s+([a-zA-Z]+)\\b") to "bagian $1 $2 saya terasa $3",

            // "saya mengalami [bagian tubuh] saya..."
            Regex("(?i)\\bsaya\\s+mengalami\\s+(paha|perut|dada|kepala|mata|kaki|tangan|leher|pinggang|tenggorokan|kulit|lutut)\\s+(kanan|kiri|bawah|atas|depan|belakang)?\\s*saya\\b") to "bagian $1 $2 saya",

            // "saya mengalami ada benjolan" -> "muncul benjolan"
            Regex("(?i)\\bsaya\\s+mengalami\\s+ada\\s+benjolan\\b") to "muncul benjolan",
            Regex("(?i)\\bsaya\\s+mengalami\\s+muncul\\s+benjolan\\b") to "muncul benjolan",
            Regex("(?i)\\bsaya\\s+mengalami\\s+adanya\\b") to "ada",
            Regex("(?i)\\bsaya\\s+mengalami\\s+di\\s+bagian\\b") to "di bagian",
            Regex("(?i)\\bsaya\\s+mengalami\\s+pada\\s+bagian\\b") to "pada bagian",

            // "merasakan paha kanan saya..."
            Regex("(?i)\\bmerasakan\\s+paha\\s+(kanan|kiri)\\s+(saya\\s+)?(ada\\s+benjolan|bengkak)\\b") to "pada bagian paha $1 saya $3",

            // Duplikasi preposisi atau kata ganti ganda
            Regex("(?i)\\bdi\\s+pada\\b") to "pada",
            Regex("(?i)\\bpada\\s+di\\b") to "di",
            Regex("(?i)\\bsaya\\s+saya\\b") to "saya",
            Regex("(?i)\\bdok\\s+dok\\b") to "Dok"
        )

        for ((pat, rep) in awkwardAnatomyPatterns) {
            text = pat.replace(text, rep)
        }

        return text.replace(Regex("\\s+"), " ").trim()
    }

    fun humanizeMedicalTerms(rawText: String): String {
        if (rawText.isBlank()) return rawText
        var text = rawText

        val replacements = listOf(
            Regex("(?i)\\b(dispneu|dispnea|dyspnea)\\b") to "sesak napas",
            Regex("(?i)\\b(hematemesis)\\b") to "muntah darah",
            Regex("(?i)\\b(melena)\\b") to "buang air besar hitam berdarah",
            Regex("(?i)\\b(cephalea|cefalgia|sefalgia)\\b") to "sakit kepala",
            Regex("(?i)\\b(febris)\\b") to "demam dan badan panas",
            Regex("(?i)\\b(epistaksis|epistaxis)\\b") to "mimisan keluar darah dari hidung",
            Regex("(?i)\\b(vertigo)\\b") to "pusing berputar",
            Regex("(?i)\\b(edema)\\b") to "bengkak",
            Regex("(?i)\\b(ikterik|icteric)\\b") to "kulit dan mata tampak kuning",
            Regex("(?i)\\b(palpitasi)\\b") to "jantung berdebar-debar kencang",
            Regex("(?i)\\b(artralgia)\\b") to "nyeri pada sendi",
            Regex("(?i)\\b(mialgia)\\b") to "nyeri otot dan pegal-pegal",
            Regex("(?i)\\b(sinkop|syncope)\\b") to "pingsan dan tidak sadarkan diri",
            Regex("(?i)\\b(diarrhea)\\b") to "buang air besar cair",
            Regex("(?i)\\b(nyeri abdominal)\\b") to "nyeri perut",
            Regex("(?i)\\b(nyeri toraks)\\b") to "nyeri dada",
            Regex("(?i)\\b(apprehensive)\\b") to "tampak cemas dan tidak tenang",
            Regex("(?i)\\b(rhonchi|ronki)\\b") to "suara napas mengorok",
            Regex("(?i)\\b(wheezing|mengi)\\b") to "napas berbunyi ngik-ngik",
            Regex("(?i)\\b(stridor)\\b") to "suara napas melengking",
            Regex("(?i)\\b(cyanosis|sianosis)\\b") to "kebiruan pada bibir dan ujung jari",
            Regex("(?i)\\b(cito)\\b") to "segera / darurat"
        )

        for ((pattern, rep) in replacements) {
            text = pattern.replace(text, rep)
        }

        return text.replace(Regex("\\s+"), " ").trim()
    }

    fun extractCoreChiefComplaint(rawChiefComplaint: String): String {
        if (rawChiefComplaint.isBlank()) return rawChiefComplaint

        var text = rawChiefComplaint

        // 1. Bersihkan sapaan, kata pengantar, "keluhan", awalan "Dok" / "Dokter", serta subjek pihak ketiga
        text = text
            .replace(Regex("(?i)^(\\s*aduh\\s+|\\s*halo\\s+|\\s*pagi\\s+|\\s*siang\\s+|\\s*malam\\s+|\\s*selamat\\s+pagi\\s+|\\s*selamat\\s+siang\\s+|\\s*selamat\\s+malam\\s+|\\s*tolong\\s+)*(dok|dokter)(\\s*[,!.-]?\\s*)"), "")
            .replace(Regex("(?i)^(pasien\\s+(datang\\s+)?(dengan\\s+)?keluhan\\s+)"), "")
            .replace(Regex("(?i)^(keluhan\\s+utama\\s+(:)?\\s*)"), "")
            .replace(Regex("(?i)^(keluhan\\s+)"), "")
            .replace(Regex("(?i)^(mengeluh\\s+)"), "")
            .replace(Regex("(?i)^(pasien\\s+ditemukan\\s+(keluarga\\s+)?)"), "")
            .replace(Regex("(?i)^(pasien\\s+dibawa\\s+warga\\s+)"), "")
            .replace(Regex("(?i)^(pasien\\s+mengamuk\\s+)"), "mengamuk ")
            .replace(Regex("(?i)^(pasien\\s+)"), "")
            .replace(Regex("(?i)^(anak\\s+)"), "")
            .replace(Regex("(?i)^(bayi\\s+)"), "")
            .replace(Regex("(?i)^(ibu\\s+hamil\\s+[^,]*mengalami\\s+)"), "")
            .replace(Regex("(?i)^(saya\\s+merasakan\\s+|saya\\s+mengalami\\s+|saya\\s+merasa\\s+|ada\\s+)"), "")
            .trim()

        // Clean any remaining "dok" / "dokter"
        text = text.replace(Regex("(?i)^(dok|dokter)\\s*[,!.-]?\\s*"), "").trim()

        // 2. Pemotong penanda detail sekunder / onset / radiasi / penyerta
        val detailMarkers = listOf(
            ", disertai", " disertai",
            ", diikuti", " diikuti",
            ", serta", " serta",
            ", beserta", " beserta",
            ", dan juga", " dan juga",
            ", plus", " plus",
            ", dengan tambahan", " dengan tambahan",
            ", dirasakan seperti", " dirasakan seperti",
            ", menjalar ke", " menjalar ke",
            ", tembus ke", " tembus ke",
            ", rasa cemas", " rasa cemas",
            ", hingga tidak bisa", " hingga tidak bisa",
            ", badan lemas", " badan lemas",
            ", pingsan", " pingsan",
            " sejak ", " selama ", " pasca "
        )

        for (marker in detailMarkers) {
            val idx = text.indexOf(marker, ignoreCase = true)
            if (idx > 3) {
                text = text.substring(0, idx).trim()
            }
        }

        // 3. Ambil klausa utama pertama jika masih terpisah koma atau titik
        val parts = text.split(",", ";", ".").map { it.trim() }.filter { it.isNotBlank() }
        if (parts.isNotEmpty()) {
            text = parts[0]
        }

        text = text.replace(Regex("(?i)^(dok|dokter)\\s*[,!.-]?\\s*"), "").trim()
        text = text.trimEnd('.', ',', ' ', ';')

        if (text.isNotBlank()) {
            text = text.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        } else {
            text = "Gejala utama"
        }

        return text
    }

    fun extractDualChiefComplaints(rawChiefComplaint: String, case: ClinicalCase? = null): DualComplaintResult {
        if (rawChiefComplaint.isBlank()) {
            return DualComplaintResult(
                primaryComplaint = "Rasa sakit dan tidak nyaman pada tubuh",
                secondaryComplaint = "Badan terasa lemas dan tidak bertenaga",
                onsetTimeline = "Onset baru timbul",
                fullSummary = "Pasien datang untuk pemeriksaan medis."
            )
        }

        var cleaned = humanizeMedicalTerms(rawChiefComplaint)
            .replace(Regex("(?i)^(\\s*aduh\\s+|\\s*halo\\s+|\\s*pagi\\s+|\\s*siang\\s+|\\s*malam\\s+|\\s*selamat\\s+pagi\\s+|\\s*selamat\\s+siang\\s+|\\s*selamat\\s+malam\\s+|\\s*tolong\\s+)*(dok|dokter)(\\s*[,!.-]?\\s*)"), "")
            .replace(Regex("(?i)^(pasien\\s+(datang\\s+)?(dengan\\s+)?keluhan\\s+)"), "")
            .replace(Regex("(?i)^(keluhan\\s+utama\\s+(:)?\\s*)"), "")
            .replace(Regex("(?i)^(keluhan\\s+)"), "")
            .replace(Regex("(?i)^(mengeluh\\s+)"), "")
            .replace(Regex("(?i)^(pasien\\s+ditemukan\\s+(keluarga\\s+)?)"), "")
            .replace(Regex("(?i)^(pasien\\s+dibawa\\s+warga\\s+)"), "")
            .replace(Regex("(?i)^(saya\\s+merasakan\\s+|saya\\s+mengalami\\s+|saya\\s+merasa\\s+|ada\\s+)"), "")
            .trim()

        // 1. Ekstraksi Onset / Durasi
        var onset = "Timbul mendadak / akut"
        val onsetRegex = Regex("(?i)(sejak\\s+[^,;.]+|selama\\s+[^,;.]+|pasca\\s+[^,;.]+)")
        val onsetMatch = onsetRegex.find(cleaned)
        if (onsetMatch != null) {
            onset = onsetMatch.value.trim().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }

        // 2. Pemisahan Menjadi 2 Keluhan Utama
        val splitMarkers = listOf(", disertai ", " disertai ", ", diikuti ", " diikuti ", ", serta ", " serta ", ", beserta ", " beserta ", ", dan juga ", " dan juga ", ", lalu ", " lalu ", ", ditambah ", " ditambah ")
        var primaryPart = ""
        var secondaryPart = ""

        for (marker in splitMarkers) {
            val idx = cleaned.indexOf(marker, ignoreCase = true)
            if (idx > 5) {
                primaryPart = cleaned.substring(0, idx).trim()
                secondaryPart = cleaned.substring(idx + marker.length).trim()
                break
            }
        }

        if (primaryPart.isBlank()) {
            val commaParts = cleaned.split(",", ";").map { it.trim() }.filter { it.isNotBlank() }
            if (commaParts.size >= 2) {
                primaryPart = commaParts[0]
                secondaryPart = commaParts.drop(1).joinToString(", ")
            } else {
                primaryPart = cleaned
            }
        }

        // Clean onset from primaryPart if present at end
        primaryPart = primaryPart.replace(Regex("(?i)(sejak\\s+[^,;.]+|selama\\s+[^,;.]+|pasca\\s+[^,;.]+)"), "").trim()
        primaryPart = primaryPart.trimEnd('.', ',', ' ', ';')

        // Clean secondaryPart
        secondaryPart = secondaryPart.replace(Regex("(?i)(sejak\\s+[^,;.]+|selama\\s+[^,;.]+|pasca\\s+[^,;.]+)"), "").trim()
        secondaryPart = secondaryPart.trimEnd('.', ',', ' ', ';')

        // If secondaryPart is empty, provide appropriate companion symptom based on context
        if (secondaryPart.isBlank() || secondaryPart.length < 4) {
            val lowerPri = primaryPart.lowercase()
            secondaryPart = when {
                lowerPri.contains("dada") || lowerPri.contains("jantung") -> "Keringat dingin membasahi baju dan napas terasa agak sesak"
                lowerPri.contains("perut") || lowerPri.contains("lambung") -> "Mual, perut kembung, dan rasa tidak nyaman melilit"
                lowerPri.contains("kepala") || lowerPri.contains("pusing") -> "Pusing melayang dan rasa tegang di tengkuk leher"
                lowerPri.contains("sesak") || lowerPri.contains("napas") -> "Batuk, dada terasa berat tertekan, dan cepat lelah"
                lowerPri.contains("gatal") || lowerPri.contains("kulit") -> "Sensasi kulit panas perih dan bintik bentol kemerahan"
                lowerPri.contains("pinggang") || lowerPri.contains("kencing") -> "Nyeri pegal menjalar dan rasa perih saat buang air kecil"
                lowerPri.contains("demam") || lowerPri.contains("panas") -> "Badan menggigil dingin dan linu seluruh persendian"
                else -> "Badan terasa lemas dan kondisi tubuh tidak bertenaga"
            }
        }

        val formattedPrimary = primaryPart.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        val formattedSecondary = secondaryPart.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

        val summary = "$formattedPrimary, disertai $formattedSecondary ($onset)."

        return DualComplaintResult(
            primaryComplaint = formattedPrimary,
            secondaryComplaint = formattedSecondary,
            onsetTimeline = onset,
            fullSummary = summary
        )
    }

    fun constructNaturalGreetingBody(coreText: String): String {
        var clean = naturalizeIndonesianSentence(coreText).lowercase().trim()
        clean = clean.replace(Regex("^(saya\\s+merasakan\\s+|saya\\s+mengalami\\s+|saya\\s+merasa\\s+|ada\\s+keluhan\\s+)"), "").trim()

        return when {
            // Already starts with "saya"
            clean.startsWith("saya ") -> clean

            // Anatomical / Location / Localized finding (e.g. paha, dada, perut, kepala, mata, kaki, tangan, pinggang, leher, kulit, lutut)
            clean.startsWith("pada ") || clean.startsWith("di ") -> clean

            clean.startsWith("paha") || clean.startsWith("kaki") || clean.startsWith("tangan") ||
            clean.startsWith("lutut") || clean.startsWith("lengan") || clean.startsWith("pinggang") ||
            clean.startsWith("leher") || clean.startsWith("punggung") -> {
                if (clean.contains("ada benjolan") || clean.contains("benjolan") || clean.contains("bengkak") || clean.contains("luka")) {
                    "pada bagian $clean"
                } else if (clean.contains("nyeri") || clean.contains("sakit") || clean.contains("linu") || clean.contains("kaku")) {
                    "bagian $clean"
                } else {
                    "pada bagian $clean saya"
                }
            }

            clean.startsWith("dada") || clean.startsWith("perut") || clean.startsWith("kepala") ||
            clean.startsWith("mata") || clean.startsWith("telinga") || clean.startsWith("hidung") ||
            clean.startsWith("tenggorokan") || clean.startsWith("kulit") -> {
                if (clean.contains("terasa") || clean.contains("sakit") || clean.contains("nyeri") || clean.contains("pusing") || clean.contains("sesak") || clean.contains("gatal")) {
                    clean
                } else {
                    "$clean saya terasa sakit dan tidak nyaman"
                }
            }

            // Benjolan / bengkak / luka / bintik
            clean.startsWith("benjolan") || clean.startsWith("muncul benjolan") || clean.startsWith("ada benjolan") -> {
                if (clean.startsWith("muncul") || clean.startsWith("ada")) clean else "muncul $clean"
            }

            clean.startsWith("luka") || clean.startsWith("borok") -> {
                "ada $clean"
            }

            // Verbal/Action symptoms: batuk, muntah, buang air, sesak napas, pusing, demam, kejang
            clean.startsWith("batuk") || clean.startsWith("muntah") || clean.startsWith("buang air") ||
            clean.startsWith("pingsan") || clean.startsWith("kejang") || clean.startsWith("menggigil") ||
            clean.startsWith("keringat dingin") || clean.startsWith("sulit") || clean.startsWith("susah") ||
            clean.startsWith("tidak bisa") || clean.startsWith("badan") -> {
                "saya $clean"
            }

            clean.startsWith("demam") || clean.startsWith("panas") -> {
                "badan saya $clean"
            }

            clean.startsWith("sesak") || clean.startsWith("napas") -> {
                "napas saya $clean"
            }

            clean.startsWith("pusing") -> {
                "kepala saya $clean"
            }

            clean.startsWith("nyeri") || clean.startsWith("sakit") -> {
                "saya merasakan $clean"
            }

            else -> {
                "saya merasakan $clean"
            }
        }
    }

    fun formatInitialPatientGreeting(case: ClinicalCase): String {
        val rawComplaint = case.chiefComplaint
        val coreExtracted = extractCoreChiefComplaint(rawComplaint)
        val humanizedCore = naturalizeIndonesianSentence(humanizeMedicalTerms(sanitizePatientResponse(coreExtracted, case)))
        val lowerCore = humanizedCore.lowercase().trim()

        val rawLower = rawComplaint.lowercase()
        val isChild = case.patientAge <= 12 || rawLower.contains("anak") || rawLower.contains("bayi")
        val isPregnancy = rawLower.contains("ibu hamil") || rawLower.contains("kehamilan")
        val isPsychiatric = rawLower.contains("mengamuk") || rawLower.contains("bisikan") || rawLower.contains("gelisah")
        val isUnconscious = rawLower.contains("pingsan") || rawLower.contains("tidak sadarkan diri") || rawLower.contains("ditemukan keluarga") || rawLower.contains("dibawa warga")

        val naturalBody = constructNaturalGreetingBody(lowerCore)

        return when {
            isChild -> {
                "Aduh Dok, tolong anak saya... anak saya $naturalBody."
            }
            isPregnancy -> {
                "Aduh Dok, tolong istri saya... istri saya sedang hamil dan $naturalBody."
            }
            isPsychiatric -> {
                "Aduh Dok, tolong keluarga kami... dia $naturalBody."
            }
            isUnconscious -> {
                "Aduh Dok, tolong... pasien ini $naturalBody."
            }
            else -> {
                "Aduh Dok, tolong saya... $naturalBody."
            }
        }
    }
}
