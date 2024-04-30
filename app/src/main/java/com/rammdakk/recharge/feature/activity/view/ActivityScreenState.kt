package com.rammdakk.recharge.feature.activity.view

import androidx.compose.runtime.State
import com.rammdakk.recharge.feature.activity.view.model.ActivityExtendedInfo
import com.rammdakk.recharge.feature.activity.view.model.CurrentUserInfo
import com.rammdakk.recharge.feature.activity.view.model.TimePad

sealed interface ActivityScreenState {
    data object Idle : ActivityScreenState

    data class Loaded(
        val activityInfo: ActivityExtendedInfo,
        val currentUserInfo: CurrentUserInfo?,
        val scheduleInfo: State<List<TimePad>>,
        val usersMaxNumber: State<Int?>
    ) : ActivityScreenState
}

