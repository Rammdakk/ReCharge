package com.rammdakk.recharge.feature.catalog.view

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

@Destination(start = true)
@Composable
fun CatalogContent(
    navigator: DestinationsNavigator,
    viewModel: CatalogViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val uiState by viewModel.screenState

    LaunchedEffect(Unit) {
        if (uiState is CatalogScreenState.Loaded) {
            viewModel.updateScreen()
        }
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
            is CatalogScreenState.Idle -> {
                viewModel.loadData()
            }

            is CatalogScreenState.Loaded -> {
                CatalogScreen(
                    profileInfo = state.profileInfo,
                    nextActivity = state.nextActivity,
                    categories = state.categoriesList,
                    activities = state.activitiesList,
                    navigator = navigator,
                    onSearch = viewModel::filterActivities
                )
            }
        }
    }
    Error(errorState = viewModel.errorState)
}