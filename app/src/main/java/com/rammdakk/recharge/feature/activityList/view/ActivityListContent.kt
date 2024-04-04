package com.rammdakk.recharge.feature.activityList.view

import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.Date

@Destination
@Composable
fun ActivityListContent(
    navigator: DestinationsNavigator,
    activityCatId: Int,
    viewModel: ActivityListViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {

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
                    activities = state.activities,
                    updateDate = { date -> viewModel.loadData(activityCatId, date) },
                    navigator = navigator
                )
            }
        }
    }
}