package com.rammdakk.recharge.feature.activityList.view

import androidx.compose.runtime.State
import com.rammdakk.recharge.feature.activityList.view.model.ActivityInfo
import java.util.Date


sealed interface ActivityListScreenState {

    data object Idle : ActivityListScreenState

    data class Loaded(
        val title: String,
        val date: State<Date>,
        val activities: List<ActivityInfo>
    ) : ActivityListScreenState
}