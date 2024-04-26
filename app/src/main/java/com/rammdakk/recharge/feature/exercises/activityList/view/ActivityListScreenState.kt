package com.rammdakk.recharge.feature.exercises.activityList.view

import androidx.compose.runtime.State
import com.rammdakk.recharge.feature.exercises.activityList.view.model.ActivityInfo
import java.util.Date


sealed interface ActivityListScreenState {

    data object Idle : ActivityListScreenState

    data class Loaded(
        val title: State<String>,
        val date: State<Date>,
        val activities: State<List<ActivityInfo>>
    ) : ActivityListScreenState
}