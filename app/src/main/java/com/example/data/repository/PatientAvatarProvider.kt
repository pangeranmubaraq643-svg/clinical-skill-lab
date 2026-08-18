package com.example.data.repository

import com.example.R
import com.example.data.model.ClinicalCase

object PatientAvatarProvider {

    fun getAvatarResId(case: ClinicalCase): Int {
        val age = case.patientAge
        val isMale = case.patientGender.contains("laki", ignoreCase = true)

        return when {
            age < 17 -> R.drawable.img_patient_pediatric_1786373115068
            age >= 60 -> R.drawable.img_patient_elderly_1786373094755
            isMale -> R.drawable.img_patient_male_1786373060243
            else -> R.drawable.img_patient_female_1786373079101
        }
    }
}
