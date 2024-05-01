package com.rammdakk.recharge.feature.calendar.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReservationResponseModel(
    @JsonProperty("selectedDateTimeStart") val startDate: Date,
    @JsonProperty("selectedDateTimeEnd") val endDate: Date,
    @JsonProperty("reservations") val reservations: List<ReservationDataModel>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReservationDataModel(
    @JsonProperty("activityId") val activityId: Int,
    @JsonProperty("reservationId") val reservationId: Int,
    @JsonProperty("activityName") val name: String,
    @JsonProperty("imageUrl") val imagePath: String,
    @JsonProperty("dateTime") val time: Date,
    @JsonProperty("locationName") val organizationName: String,
    @JsonProperty("address") val address: String,
    @JsonProperty("coordinates") val coordinates: Coordinates?,
    @JsonProperty("status") val reservationStatus: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Coordinates(
    @JsonProperty("latitude") val latitude: Float,
    @JsonProperty("longitude") val longitude: Float
)
