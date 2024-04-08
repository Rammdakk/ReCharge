package com.rammdakk.recharge.feature.activity.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActivityExtendedDataModel(
    @JsonProperty("activityId") val id: Int? = null,
    @JsonProperty("activityName") val name: String? = null,
    @JsonProperty("imageURL") val imagePath: String? = null,
    @JsonProperty("adminPhoneWA") val adminPhoneWA: String? = null,
    @JsonProperty("adminTgUsername") val adminTgUsername: String? = null,
    @JsonProperty("locationName") val locationName: String? = null,
    @JsonProperty("locationAddress") val locationAddress: String? = null,
    @JsonProperty("activityDescription") val activityDescription: String? = null,
    @JsonProperty("warning") val warning: String? = null,
    @JsonProperty("cancellationMessage") val cancellationMessage: String? = null
)

