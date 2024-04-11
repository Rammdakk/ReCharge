package com.rammdakk.recharge.feature.reservation.view.model

import com.rammdakk.recharge.feature.reservation.data.model.ReservationDataModel

data class ReservationInfo(
    val id: Int,
    val time: Long?
)

fun ReservationDataModel.convertToReservationInfo() =
    ReservationInfo(
        id = this.reservationId ?: throw IllegalArgumentException(),
        time = this.time ?: throw IllegalArgumentException(),
    )
