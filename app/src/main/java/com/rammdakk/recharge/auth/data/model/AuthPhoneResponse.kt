package com.rammdakk.recharge.auth.data.model

data class AuthPhoneResponse(
    val isSuccess: Boolean,
    val sessionId: String?,
    val titleText: String,
    val codeSize: Int,
    val conditionalInfo: ConditionalInfo,
    val resendMessage: String?,
)

data class ConditionalInfo(
    val message: String,
    val url: String

)

data class AuthResponse(
    val isSuccess: Boolean,
    val accessToken: String?,
    val message: String?,
)