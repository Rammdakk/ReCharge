package com.rammdakk.recharge.feature.activity.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    viewModel: ActivityViewModel = hiltViewModel()
) {
    val uiState by viewModel.screenState

    LaunchedEffect(Unit) {
        viewModel.loadData(activityId)
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(ReChargeTokens.BackgroundColored.getThemedColor())
    systemUiController.setStatusBarColor(ReChargeTokens.BackgroundColored.getThemedColor())

    Crossfade(
        modifier = Modifier
            .background(ReChargeTokens.BackgroundColored.getThemedColor()),
        targetState = uiState,
        label = ""
    ) { state ->
        when (state) {
            is ActivityScreenState.Idle -> {
            }

            is ActivityScreenState.Loaded -> {
                ActivityInfoScreen(
                    state.activityInfo,
                    { viewModel.loadScheduleForDate(activityId, it) },
                    state.scheduleInfo,
                    state.usersMaxNumber,
                    viewModel::reserve,
                    navigator::popBackStack,
                )
            }

        }
    }
}
