package com.rammdakk.recharge.feature.profile.view.models

import com.rammdakk.recharge.feature.profile.data.models.Gender

data class ProfileScreenModel(
    val firstName: String,
    val secondName: String,
    val phone: String,
    val email: String,
    val birthDay: Long?,
    val gender: Gender?,
    val city: String
)
