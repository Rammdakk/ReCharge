package com.rammdakk.recharge.feature.reservation.view.model

import com.rammdakk.recharge.feature.catalog.view.components.formatDate
import com.rammdakk.recharge.feature.reservation.data.model.ReservationMetaDataModel

data class ReservationInfo(
    val reservationId: Int?,
    val time: String?,
    val accessCode: String,
    val activityId: Int?
)

fun ReservationMetaDataModel.convertToReservationInfo() =
    ReservationInfo(
        reservationId = this.reservationId,
        time = this.date?.time?.formatDate() ?: throw IllegalArgumentException(),
        accessCode = this.accessCode ?: throw IllegalArgumentException(),
        activityId = this.activityId
    )
