package com.rammdakk.recharge.feature.activity.view

import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    preselectedTabId: Int? = null,
    viewModel: ActivityViewModel = hiltViewModel()
) {
    val uiState by viewModel.screenState

    setSystemBarsColors(
        statusBarColor = ReChargeTokens.BackgroundColored.getThemedColor(),
        navBarColor = ReChargeTokens.BackgroundColored.getThemedColor()
    )

    val context = LocalContext.current

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
                    preselectedTabId,
                    { viewModel.loadScheduleForDate(activityId, it) },
                    state.scheduleInfo,
                    state.usersMaxNumber,
                    { id, userBookingInfo ->
                        viewModel.reserve(id, userBookingInfo) {
                            val intent = Intent(Intent.ACTION_EDIT)
                            intent.setType("vnd.android.cursor.item/event")
                            intent.putExtra("beginTime", userBookingInfo.calendarInfo.startDate)
                            intent.putExtra("allDay", false)
                            intent.putExtra("rrule", "FREQ=ONCE")
                            intent.putExtra("endTime", userBookingInfo.calendarInfo.endTime)
                            intent.putExtra("title", userBookingInfo.calendarInfo.activityName)
                            intent.putExtra(
                                "eventLocation",
                                userBookingInfo.calendarInfo.locationName
                            )
                            context.startActivity(intent)
                        }
                    },
                    navigator::popBackStack,
                )
            }

        }
    }
    Error(errorState = viewModel.errorState)
}
