package com.rammdakk.recharge.feature.calendar.view

import android.util.Log
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
fun CalendarContent(
    navigator: DestinationsNavigator,
    viewModel: CalendarScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.screenState
    setSystemBarsColors(
        statusBarColor = ReChargeTokens.Background.getThemedColor(),
        navBarColor = ReChargeTokens.Background.getThemedColor()
    )

    Crossfade(
        targetState = uiState, label = "CalendarContent",
        modifier = Modifier.background(
            ReChargeTokens.Background.getThemedColor()
        ),
    ) { state ->
        when (state) {
            is CalendarScreenState.Idle -> {
                viewModel.loadData()
            }

            is CalendarScreenState.Loaded -> {
                Log.d("ramil", "loaded ${state.calendarState.currentMoth.value}")
                CalendarScreen(
                    calendarState = state.calendarState,
                    reservationList = state.reservationList,
                    navigator = navigator
                )
            }
        }

    }
    Error(errorState = viewModel.errorState)
}