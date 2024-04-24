package com.rammdakk.recharge.feature.profile.models.presentation

import com.rammdakk.recharge.feature.profile.models.data.Gender

data class ProfileScreenModel(
    val firstName: String,
    val secondName: String,
    val phone: String,
    val email: String,
    val birthDay: Long?,
    val gender: Gender?,
    val city: String
)
