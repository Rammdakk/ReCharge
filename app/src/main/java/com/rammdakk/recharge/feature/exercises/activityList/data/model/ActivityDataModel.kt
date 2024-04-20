package com.rammdakk.recharge.feature.exercises.activityList.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActivityListDataModel(
    @JsonProperty("categoryName") val activityName: String,
    @JsonProperty("slots") val activityList: List<ActivityDataModel> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActivityDataModel(
    @JsonProperty("activityId") val id: Int,
    @JsonProperty("activityName") val name: String,
    @JsonProperty("imageUrl") val imagePath: String,
    @JsonProperty("dateTime") val time: Date?,
    @JsonProperty("lengthMinutes") val duration: Long,
    @JsonProperty("price") val price: Float,
    @JsonProperty("locationName") val organizationName: String,
    @JsonProperty("address") val address: String,
    @JsonProperty("coordinates") val coordinates: Coordinates
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Coordinates(
    @JsonProperty("latitude") val latitude: Float,
    @JsonProperty("longitude") val longitude: Float
)
