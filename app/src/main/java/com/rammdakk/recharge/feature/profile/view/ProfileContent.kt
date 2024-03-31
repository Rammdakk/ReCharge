package com.rammdakk.recharge.feature.profile.view

import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor

@Destination
@Composable
fun ProfileContent(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val uiState by viewModel.profileState

    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(ReChargeTokens.Background.getThemedColor())
    systemUiController.setStatusBarColor(ReChargeTokens.Background.getThemedColor())

    Crossfade(
        modifier = Modifier.background(ReChargeTokens.Background.getThemedColor()),
        targetState = uiState,
        label = ""
    ) { state ->
        when (state) {
            is ProfileScreenState.Idle -> {

            }

            is ProfileScreenState.Loaded -> {
                ProfileScreen(
                    firstName = state.firstName,
                    secondName = state.secondName,
                    phone = state.phone,
                    email = state.email,
                    birthDay = state.birthDay.time,
                    isMale = state.isMale,
                    city = state.city,
                    onSaveClick = viewModel::updateData,
                    onLogOutClick = viewModel::logOut
                )
            }
        }
    }
}