package com.rammdakk.recharge.feature.activityList.view

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.theme.setSystemBarsColors
import com.rammdakk.recharge.base.view.component.error.Error
import java.util.Date

@Destination
@Composable
fun ActivityListContent(
    navigator: DestinationsNavigator,
    activityCatId: Int,
    viewModel: ActivityListViewModel = hiltViewModel()
) {

    setSystemBarsColors(
        statusBarColor = ReChargeTokens.Background.getThemedColor(),
        navBarColor = ReChargeTokens.Background.getThemedColor()
    )

    LaunchedEffect(Unit) {
        viewModel.loadData(activityCatId, Date())
    }
    val uiState by viewModel.screenState

    Crossfade(targetState = uiState, label = "ActivityListContent") { state ->
        when (state) {
            is ActivityListScreenState.Idle -> {}
            is ActivityListScreenState.Loaded -> {
                ActivityListScreen(
                    title = state.title,
                    date = state.date,
                    activities = state.activities,
                    updateDate = { date -> viewModel.loadData(activityCatId, date) },
                    navigator = navigator
                )
            }
        }
    }
    Error(errorState = viewModel.errorState)
}