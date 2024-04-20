package com.rammdakk.recharge.feature.exercises.categroies.view

import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.theme.setSystemBarsColors
import com.rammdakk.recharge.base.view.component.error.Error
import com.rammdakk.recharge.feature.destinations.ActivityListContentDestination

@Destination
@Composable
fun ExercisesCatContent(
    navigator: DestinationsNavigator,
    viewModel: ExerciseCatViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val uiState by viewModel.screenState

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    setSystemBarsColors(
        statusBarColor = ReChargeTokens.Background.getThemedColor(),
        navBarColor = ReChargeTokens.Background.getThemedColor()
    )

    Crossfade(
        modifier = Modifier
            .background(ReChargeTokens.Background.getThemedColor())
            .padding(horizontal = 16.dp),
        targetState = uiState,
        label = ""
    ) { state ->
        when (state) {
            is ExercisesCatScreenState.Idle -> {}

            is ExercisesCatScreenState.Loaded -> {
                ExerciseCatScreen(
                    selectedId = state.selectedId.value,
                    tabs = state.tabs.value,
                    sportTypeItems = state.items.value,
                    onTabClick = viewModel::onTabSelected,
                    onCategoryClick = {
                        navigator.navigate(ActivityListContentDestination(it))
                    }
                )
            }
        }
    }
    Error(errorState = viewModel.errorState)
}