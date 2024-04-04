package com.rammdakk.recharge.feature.auth.view

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AuthContent(
    vm: AuthViewModel
) {

    val uiState by vm.authState
    Crossfade(targetState = uiState, label = "AuthContent") { state ->
        if (state is AuthScreenState.Idle) {
            vm.init()
        }
        if (state is AuthScreenState.RequestPhone) {
            AuthPhoneScreen(
                greetingText = stringResource(id = state.greetingText),
                hintText = state.hintText?.let { stringResource(id = it) },
                onClick = state.onRequestCodeClick,
                errorMessage = state.errorMessage,
            )
        }
        if (state is AuthScreenState.RequestCode) {
            AuthCodeValidationScreen(
                greetingText = state.greetingText,
                codeSize = state.codeSize,
                onBackPressed = state.onBackPressed,
                onClick = state.onSubmitClick,
                onRequestCodeClick = state.onRequestCodeClick,
                errorMessage = state.errorMessage,
                bottomInfo = state.bottomInfo
            )
        }
    }

}