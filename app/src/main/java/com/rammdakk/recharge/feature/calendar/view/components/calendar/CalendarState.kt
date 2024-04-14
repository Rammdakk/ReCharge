package com.rammdakk.recharge.feature.calendar.view.components.calendar

import androidx.compose.runtime.State
import java.time.YearMonth
import java.util.Date

data class CalendarState(
    val currentMoth: State<YearMonth>,
    val onMonthChanged: (YearMonth) -> Unit,
    val onDateSelected: (Date) -> Unit
)