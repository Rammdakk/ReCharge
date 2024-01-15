package com.rammdakk.recharge.auth.view

import androidx.annotation.StringRes
import androidx.compose.runtime.State

sealed interface AuthScreenState {
    data object Idle : AuthScreenState

    data class RequestPhone(
        @StringRes val greetingText: Int,
        @StringRes val hintText: Int? = null,
        val onRequestCodeClick: (String) -> Unit,
        val errorMessage: State<String?>,
    ) : AuthScreenState

    data class RequestCode(
        val greetingText: String,
        val codeSize: Int,
        val onBackPressed: () -> Unit,
        val onSubmitClick: (String) -> Unit,
        val onRequestCodeClick: () -> Unit,
        val errorMessage: State<String?>,
        val bottomInfo: State<BottomInfo?>,
    ) : AuthScreenState

    object LoggedIn : AuthScreenState
}

data class BottomInfo(
    val message: String,
    val onClick: (() -> Unit)? = null
)