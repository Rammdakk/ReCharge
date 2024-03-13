package com.rammdakk.recharge.feature.catalog.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ActivityDataModel(
    @JsonProperty("id") val id: Int,
    @JsonProperty("activityName") val name: String,
    @JsonProperty("id") val imagePath: String,
    @JsonProperty("id") val time: Long?,
    @JsonProperty("id") val duration: Long,
    @JsonProperty("id") val startPrice: Float,
    @JsonProperty("id") val organizationName: String,
    @JsonProperty("id") val address: String,
    @JsonProperty("id") val coordinates: Coordinates
)

data class Coordinates(
    val latitude: Float,
    val longitude: Float
)
