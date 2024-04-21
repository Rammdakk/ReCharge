package com.rammdakk.recharge.feature.activity.view

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
import java.util.Date

@Destination
@Composable
fun ActivityContent(
    navigator: DestinationsNavigator,
    activityId: Int,
    date: Date = Date(),
    viewModel: ActivityViewModel = hiltViewModel()
) {
    val uiState by viewModel.screenState

    setSystemBarsColors(
        statusBarColor = ReChargeTokens.BackgroundColored.getThemedColor(),
        navBarColor = ReChargeTokens.BackgroundColored.getThemedColor()
    )

    Crossfade(
        modifier = Modifier
            .background(ReChargeTokens.BackgroundColored.getThemedColor()),
        targetState = uiState,
        label = ""
    ) { state ->
        when (state) {
            is ActivityScreenState.Idle -> {
                viewModel.loadData(activityId, date)
            }

            is ActivityScreenState.Loaded -> {
                ActivityInfoScreen(
                    state.activityInfo,
                    date,
                    { viewModel.loadScheduleForDate(activityId, it) },
                    state.scheduleInfo,
                    state.usersMaxNumber,
                    viewModel::reserve,
                    navigator::popBackStack,
                )
            }

        }
    }
    Error(errorState = viewModel.errorState)
}
