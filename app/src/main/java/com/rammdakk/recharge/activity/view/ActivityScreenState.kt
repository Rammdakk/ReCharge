package com.rammdakk.recharge.activity.view

import androidx.compose.runtime.State
import com.rammdakk.recharge.activity.view.model.ActivityExtendedInfo
import com.rammdakk.recharge.activity.view.model.TimePad

sealed interface ActivityScreenState {
    data object Idle : ActivityScreenState

    data class Loaded(
        val activityInfo: ActivityExtendedInfo,
        val scheduleInfo: State<List<TimePad>>,
    ) : ActivityScreenState
}

