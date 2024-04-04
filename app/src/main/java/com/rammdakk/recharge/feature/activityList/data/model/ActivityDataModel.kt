package com.rammdakk.recharge.feature.activityList.data.model

import com.fasterxml.jackson.annotation.JsonProperty


data class ActivityListDataModel(
    @JsonProperty("activity_name") val activityName: String,
    @JsonProperty("activity_list") val activityList: List<ActivityDataModel>
)

data class ActivityDataModel(
    @JsonProperty("id") val id: Int,
    @JsonProperty("activityName") val name: String,
    @JsonProperty("imagePath") val imagePath: String,
    @JsonProperty("time") val time: Long?,
    @JsonProperty("duration") val duration: Long,
    @JsonProperty("price") val price: Float,
    @JsonProperty("organizationName") val organizationName: String,
    @JsonProperty("address") val address: String,
    @JsonProperty("coordinates") val coordinates: Coordinates
)

data class Coordinates(
    @JsonProperty("latitude") val latitude: Float,
    @JsonProperty("longitude") val longitude: Float
)
