package com.rammdakk.recharge.feature.profile.models.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ShortProfileInfo(
    @JsonProperty("name")
    val firstName: String? = "",
    @JsonProperty("surname")
    val secondName: String? = "",
    @JsonProperty("phoneNumber")
    val phone: String? = "",
    @JsonProperty("email")
    val email: String? = "",
)