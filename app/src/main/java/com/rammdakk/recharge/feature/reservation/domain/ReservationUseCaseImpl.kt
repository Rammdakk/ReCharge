package com.rammdakk.recharge.feature.reservation.domain

import com.rammdakk.recharge.feature.reservation.data.model.ReservationMetaDataModel

class ReservationUseCaseImpl(
    private val reservationRepository: ReservationRepository
) : ReservationUseCase {
    override suspend fun getReservationInfo(reservationId: Int): Result<ReservationMetaDataModel> =
        reservationRepository.getReservationInfo(reservationId)

    override suspend fun cancelReservation(reservationId: Int): Result<Unit> =
        reservationRepository.cancelReservation(reservationId)
}