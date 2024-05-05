package com.rammdakk.recharge.feature.calendar.domain

import com.rammdakk.recharge.feature.calendar.data.model.ReservationDataModel
import java.util.Date

class ReservationListUseCaseImpl(
    private val calendarReservationRepository: CalendarReservationRepository
) : ReservationListUseCase {
    override suspend fun loadReservations(
        startDate: Date,
        endDate: Date
    ): Result<List<ReservationDataModel>> =
        calendarReservationRepository.loadReservations(startDate, endDate)
}