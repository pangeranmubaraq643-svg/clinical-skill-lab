package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "case_history")
data class CaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val organSystem: String,
    val timeMode: String,
    val chiefComplaint: String,
    val userDiagnosis: String,
    val trueDiagnosis: String,
    val diagnosisStatus: String, // BENAR, KURANG TEPAT, SALAH
    val totalCostIncurred: Long,
    val costStatus: String, // OPTIMAL, BOROS
    val totalScore: Int,
    val downloadCode: String,
    val evaluationSummary: String,
    val timestamp: Long = System.currentTimeMillis()
)
