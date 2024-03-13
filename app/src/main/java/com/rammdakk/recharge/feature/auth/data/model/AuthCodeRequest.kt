package com.rammdakk.recharge.feature.auth.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthCodeRequest(
    @JsonProperty("phoneNumber")
    val phone: String,
    @JsonProperty("sessionId")
    val sessionId: String,
    @JsonProperty("code")
    val code: String,
)
