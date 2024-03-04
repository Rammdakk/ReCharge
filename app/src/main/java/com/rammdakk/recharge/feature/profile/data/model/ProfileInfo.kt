package com.rammdakk.recharge.feature.profile.data.model

import java.util.Date

data class ProfileInfo(
    val firstName: String,
    val secondName: String,
    val phone: String,
    val email: String,
    val birthDay: Date,
    val gender: Gender,
    val city: String
)

enum class Gender { MALE, FEMALE }