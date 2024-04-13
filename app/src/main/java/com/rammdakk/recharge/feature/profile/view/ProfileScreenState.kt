package com.rammdakk.recharge.feature.profile.view

import androidx.compose.runtime.State
import com.rammdakk.recharge.base.view.component.error.ErrorState
import java.util.Date

sealed interface ProfileScreenState {
    data object Idle : ProfileScreenState

    data class Loaded(
        val firstName: String,
        val secondName: String,
        val phone: String,
        val email: String,
        val birthDay: Date?,
        val isMale: Boolean,
        val city: String,
        val errorState: State<ErrorState>
    ) : ProfileScreenState
}