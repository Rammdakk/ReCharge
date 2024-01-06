package com.rammdakk.recharge.auth.view

import androidx.annotation.StringRes
import androidx.compose.runtime.State

sealed interface AuthScreenState {
    object Idle : AuthScreenState

    data class RequestPhone(
        @StringRes val greetingText: Int,
        @StringRes val hintText: Int? = null,
        val onClick: (String) -> Unit,
        val extraInfo: State<ExtraInfo?>,
    ) : AuthScreenState

    data class RequestCode(
        val greetingText: String,
        val codeSize: Int,
        val onBackPressed: () -> Unit,
        val onClick: (String) -> Unit,
        val extraInfo: State<ExtraInfo?>,
        val bottomInfo: State<BottomInfo?>,
    ) : AuthScreenState

    object LoggedIn : AuthScreenState
}

data class ExtraInfo(
    val message: String?,
    val isError: Boolean,
    val onClick: (() -> Unit)?
)

data class BottomInfo(
    val message: String,
    val path: String,
    val onClick: (() -> Unit)? = null
)