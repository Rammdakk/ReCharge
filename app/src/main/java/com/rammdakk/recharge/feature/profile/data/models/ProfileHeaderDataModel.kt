package com.rammdakk.recharge.feature.profile.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProfileHeaderDataModel(
    @JsonProperty("photoUrl") val photoPath: String?,
    @JsonProperty("name") val name: String?
)