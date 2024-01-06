package com.rammdakk.recharge.auth.view

import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.MainViewModel
import com.rammdakk.recharge.destinations.MainScreenDestination

@Destination
@Composable
fun AuthContent(
    navigator: DestinationsNavigator,
    vm: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    Crossfade(targetState = vm.authState.value) { state ->
        if (state is AuthScreenState.Idle) {
            vm.init()
        }
        if (state is AuthScreenState.RequestPhone) {
            AuthPhoneScreen(
                greetingText = stringResource(id = state.greetingText),
                hintText = state.hintText?.let { stringResource(id = it) },
                onClick = state.onClick,
                extraInfo = state.extraInfo,
            )
        }
        if (state is AuthScreenState.RequestCode) {
            AuthCodeValidationScreen(
                greetingText = state.greetingText,
                codeSize = state.codeSize,
                onBackPressed = state.onBackPressed,
                onClick = state.onClick,
                extraInfo = state.extraInfo,
                errorInfo = state.extraInfo,
                bottomInfo = state.bottomInfo
            )
        }
        if (state is AuthScreenState.LoggedIn) {
            navigator.navigate(MainScreenDestination)
        }
    }

}