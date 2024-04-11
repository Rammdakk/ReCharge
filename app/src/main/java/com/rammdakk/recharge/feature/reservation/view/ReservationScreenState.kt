package com.rammdakk.recharge.feature.reservation.view

import com.rammdakk.recharge.feature.activity.view.model.ActivityExtendedInfo
import com.rammdakk.recharge.feature.reservation.view.model.ReservationInfo

sealed interface ReservationScreenState {
    data object Idle : ReservationScreenState

    data class Loaded(
        val activityInfo: ActivityExtendedInfo,
        val reservationInfo: ReservationInfo
    ) : ReservationScreenState
}

