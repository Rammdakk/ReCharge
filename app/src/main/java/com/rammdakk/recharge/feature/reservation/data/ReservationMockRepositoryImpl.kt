package com.rammdakk.recharge.feature.reservation.data

import com.rammdakk.recharge.feature.reservation.data.model.ReservationMetaDataModel
import com.rammdakk.recharge.feature.reservation.domain.ReservationRepository
import java.util.Date

class ReservationMockRepositoryImpl : ReservationRepository {

    override suspend fun getReservationInfo(reservationId: Int): Result<ReservationMetaDataModel> {
        return Result.success(
            ReservationMetaDataModel(
                reservationId = 1367,
                activityId = 2,
                date = Date(),
                accessCode = "gszdhkaetquyef1267euqiwehcbadk"
            )
        )
    }

    override suspend fun cancelReservation(reservationId: Int): Result<Unit> {
        return Result.success(Unit)
    }
}