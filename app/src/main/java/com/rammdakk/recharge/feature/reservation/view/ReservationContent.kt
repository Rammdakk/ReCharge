package com.rammdakk.recharge.feature.reservation.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.theme.setSystemBarsColors
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
    Crossfade(
        modifier = Modifier
            .background(ReChargeTokens.BackgroundColored.getThemedColor()),
        targetState = uiState,
        label = ""
    ) { state ->
        when (state) {
            is ReservationScreenState.Idle -> {
                setSystemBarsColors(
                    statusBarColor = ReChargeTokens.BackgroundColored.getThemedColor(),
                    navBarColor = ReChargeTokens.BackgroundColored.getThemedColor()
                )
                viewModel.loadData(reservationId = reservationId, activityId = activityId)
            }

            is ReservationScreenState.Loaded -> {
                ReservationInfoScreen(
                    state.activityInfo,
                    state.reservationInfo,
                    navigator::popBackStack,
                    viewModel::cancelReservation
                )
                setSystemBarsColors(
                    statusBarColor = ReChargeTokens.BackgroundColored.getThemedColor(),
                    navBarColor = ReChargeTokens.BackgroundContainer.getThemedColor()
                )
            }
        }
    }
    Error(errorState = viewModel.errorState)
}
