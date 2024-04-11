package com.rammdakk.recharge.feature.reservation.domain

import com.rammdakk.recharge.feature.reservation.data.model.ReservationDataModel

interface ReservationRepository {
    suspend fun getReservationInfo(reservationId: Int): Result<ReservationDataModel>
}