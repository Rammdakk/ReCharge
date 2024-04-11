package com.rammdakk.recharge.feature.activity.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserBookingDataModel(
    @field:JsonProperty("name") val userName: String,
    @field:JsonProperty("phone") val phone: String,
    @field:JsonProperty("email") val email: String,
    @field:JsonProperty("reserveCount") val reserveCount: Int,
)
