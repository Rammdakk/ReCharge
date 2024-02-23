package com.rammdakk.recharge.catalog.view

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

@Destination(start = true)
@Composable
fun CatalogContent(
    navigator: DestinationsNavigator,
    viewModel: CatalogViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val uiState by viewModel.screenState

    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(ReChargeTokens.Background.getThemedColor())
    systemUiController.setStatusBarColor(ReChargeTokens.Background.getThemedColor())

    Crossfade(
        modifier = Modifier.background(ReChargeTokens.Background.getThemedColor()),
        targetState = uiState,
        label = ""
    ) { state ->
        when (state) {
            is CatalogScreenState.Idle -> {
                viewModel.loadData()
            }

            is CatalogScreenState.Loaded -> {
                CatalogScreen(
                    profileInfo = state.profileInfo,
                    nextActivity = state.nextActivity,
                    categories = state.categories,
                    activities = state.activitiesList,
                    navigator = navigator,
                )
            }
        }
    }
}