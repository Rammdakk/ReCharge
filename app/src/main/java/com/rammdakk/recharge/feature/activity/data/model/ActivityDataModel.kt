package com.rammdakk.recharge.feature.activity.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActivityExtendedDataModel(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("imagePath") val imagePath: String?,
    @JsonProperty("adminPhoneWA") val adminPhoneWA: String?,
    @JsonProperty("adminTgUsername") val adminTgUsername: String?,
    @JsonProperty("locationName") val locationName: String,
    @JsonProperty("locationAddress") val locationAddress: String,
    @JsonProperty("activityDescription") val activityDescription: String?,
    @JsonProperty("warning") val warning: String?,
    @JsonProperty("cancellationMessage") val cancellationMessage: String?
)

