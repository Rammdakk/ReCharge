package com.rammdakk.recharge.feature.exercises.activityList.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.theme.setSystemBarsColors
import com.rammdakk.recharge.base.view.component.error.Error

@Destination
@Composable
fun ActivityListContent(
    navigator: DestinationsNavigator,
    activityCatId: Int,
    viewModel: ActivityListViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    setSystemBarsColors(
        statusBarColor = ReChargeTokens.Background.getThemedColor(),
        navBarColor = ReChargeTokens.Background.getThemedColor()
    )
    BackHandler {
        viewModel.resetDate()
        navigator.popBackStack()
    }

    LaunchedEffect(Unit) {
        viewModel.loadData(activityCatId)
    }

    val uiState by viewModel.screenState

    Crossfade(
        targetState = uiState, label = "ActivityListContent"
    ) { state ->
        when (state) {
            is ActivityListScreenState.Idle -> {}
            is ActivityListScreenState.Loaded -> {
                ActivityListScreen(
                    title = state.title.value,
                    date = state.date,
                    sortingType = state.sortingType,
                    sort = viewModel::sort,
                    activities = state.activities,
                    updateDate = { date -> viewModel.loadActivitiesData(activityCatId, date) },
                    navigator = navigator,
                    onBackPressed = {
                        viewModel.resetDate()
                        navigator.popBackStack()
                    }
                )
            }
        }
    }
    Error(errorState = viewModel.errorState)
}