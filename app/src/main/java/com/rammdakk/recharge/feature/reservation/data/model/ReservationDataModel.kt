package com.rammdakk.recharge.feature.reservation.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReservationDataModel(
    val reservationId: Int? = null,
    val activityId: Int? = null,
    val time: Long? = null,
    val accessCode: String? = null
)