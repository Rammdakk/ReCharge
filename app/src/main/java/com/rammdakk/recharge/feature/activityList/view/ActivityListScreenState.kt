package com.rammdakk.recharge.feature.activityList.view

import com.rammdakk.recharge.feature.activityList.view.model.ActivityInfo


sealed interface ActivityListScreenState {

    data object Idle : ActivityListScreenState

    data class Loaded(
        val title: String,
        val activities: List<ActivityInfo>
    ) : ActivityListScreenState
}