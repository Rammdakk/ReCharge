package com.rammdakk.recharge.feature.reservation.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.view.component.error.Error

@Destination
@Composable
fun ReservationContent(
    navigator: DestinationsNavigator,
    activityId: Int,
    reservationId: Int,
    viewModel: ReservationViewModel = hiltViewModel()
) {
    val uiState by viewModel.screenState

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
            is ReservationScreenState.Idle -> {
                viewModel.loadData(reservationId = reservationId, activityId = activityId)
            }

            is ReservationScreenState.Loaded -> {
                ReservationInfoScreen(
                    state.activityInfo,
                    state.reservationInfo,
                    navigator::popBackStack
                )
            }
        }
    }
    Error(errorState = viewModel.errorState)
}
