package com.rammdakk.recharge.feature.exercises.view

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
fun ExercisesCatContent(
    navigator: DestinationsNavigator,
    viewModel: ExerciseCatViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val uiState by viewModel.screenState

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(ReChargeTokens.Background.getThemedColor())
    systemUiController.setStatusBarColor(ReChargeTokens.Background.getThemedColor())

    Crossfade(
        modifier = Modifier.background(ReChargeTokens.Background.getThemedColor()),
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
                    onCategoryClick = viewModel::onCategorySelected
                )
            }
        }
    }
}