package com.rammdakk.recharge.profile.view

import java.util.Date

sealed interface ProfileScreenState {
    data object Idle : ProfileScreenState

    data class Loaded(
        val firstName: String,
        val secondName: String,
        val phone: String,
        val email: String,
        val birthDay: Date,
        val isMale: Boolean,
        val city: String
    ) : ProfileScreenState
}