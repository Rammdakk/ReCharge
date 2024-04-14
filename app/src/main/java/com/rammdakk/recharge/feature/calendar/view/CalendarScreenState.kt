package com.rammdakk.recharge.feature.calendar.view

import androidx.compose.runtime.State
import com.rammdakk.recharge.feature.calendar.view.components.calendar.CalendarState
import com.rammdakk.recharge.feature.calendar.view.model.ReservationModel

sealed interface CalendarScreenState {
    data object Idle : CalendarScreenState

    data class Loaded(
        val calendarState: CalendarState,
        val reservationList: State<List<ReservationModel>>
    ) : CalendarScreenState
}

