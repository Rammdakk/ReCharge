package com.rammdakk.recharge.feature.calendar.domain

import com.rammdakk.recharge.feature.calendar.data.model.ReservationDataModel
import java.util.Date

interface CalendarReservationRepository {

    suspend fun loadReservations(startDate: Date, endDate: Date): Result<List<ReservationDataModel>>
}