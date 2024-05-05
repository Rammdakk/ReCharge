package com.rammdakk.recharge.feature.profile.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ProfileHeader(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("name")
    val photoUrl: String
)
