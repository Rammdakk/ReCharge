package com.rammdakk.recharge.feature.catalog.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ProfileDataModel(
    @JsonProperty("photoUrl") val photoPath: String,
    @JsonProperty("name") val name: String
)