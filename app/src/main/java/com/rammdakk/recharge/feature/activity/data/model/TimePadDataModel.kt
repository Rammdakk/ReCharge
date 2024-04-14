package com.rammdakk.recharge.feature.activity.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

@JsonIgnoreProperties(ignoreUnknown = true)
data class ListTimePadModel(
    @JsonProperty("dateTime") val selectedDate: Date,
    @JsonProperty("slots") val slots: List<TimePadDataModel>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TimePadDataModel(
    @JsonProperty("slotId") val id: Int,
    @JsonProperty("price") val price: Double,
    @JsonProperty("startTime") val startTime: Date,
    @JsonProperty("durationMinutes") val duration: Int,
)
