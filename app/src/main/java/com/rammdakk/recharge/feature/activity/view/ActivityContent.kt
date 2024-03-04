package com.rammdakk.recharge.feature.activity.view

import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
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
fun ActivityContent(
    navigator: DestinationsNavigator,
    activityId: Int,
    viewModel: ActivityViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val uiState by viewModel.screenState

    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(ReChargeTokens.BackgroundColored.getThemedColor())
    systemUiController.setStatusBarColor(ReChargeTokens.BackgroundColored.getThemedColor())

    Crossfade(
        modifier = Modifier.background(ReChargeTokens.BackgroundColored.getThemedColor()),
        targetState = uiState,
        label = ""
    ) { state ->
        when (state) {
            is ActivityScreenState.Idle -> {
                viewModel.loadData(activityId)
            }

            is ActivityScreenState.Loaded -> {
                ActivityInfoScreen(
                    state.activityInfo,
                    viewModel::loadScheduleForDate,
                    state.scheduleInfo,
                    navigator
                )
            }

        }
    }
}
