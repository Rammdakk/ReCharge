package com.rammdakk.recharge.feature.reservation.domain

import com.rammdakk.recharge.feature.reservation.data.model.ReservationMetaDataModel

interface ReservationUseCase {

    suspend fun getReservationInfo(reservationId: Int): Result<ReservationMetaDataModel>

    suspend fun cancelReservation(reservationId: Int): Result<Unit>

}