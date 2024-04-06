package com.rammdakk.recharge.feature.catalog.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class NextActivityDataModel(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("imageUrl") val imagePath: String,
    @JsonProperty("timeMilliseconds") val time: Long?,
    @JsonProperty("locationName") val organizationName: String,
    @JsonProperty("addressString") val address: String,
    @JsonProperty("coordinates") val coordinates: Coordinates
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActivityRecommendationDataModel(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("imageUrl") val imagePath: String,
    @JsonProperty("startPrice") val startPrice: Float,
    @JsonProperty("locationName") val organizationName: String,
    @JsonProperty("addressString") val address: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Coordinates(
    @JsonProperty("latitude") val latitude: Float,
    @JsonProperty("longitude") val longitude: Float
)
