package com.rammdakk.recharge.feature.reservation.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReservationMetaDataModel(
    @JsonProperty("reservationId") val reservationId: Int? = null,
    @JsonProperty("activityId") val activityId: Int? = null,
    @JsonProperty("dateTime") val date: Date? = null,
    @JsonProperty("accessCode") val accessCode: String? = null
)