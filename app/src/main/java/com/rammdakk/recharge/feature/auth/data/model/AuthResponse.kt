package com.rammdakk.recharge.feature.auth.data.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Отправляется после получения кода
 * @param accessToken при успешной регистрации отправляется токен для дальнейшего использования
 * @param message сообщение об ошибке
 */

data class AuthResponse(
    @JsonProperty("isSuccess")
    val isSuccess: Boolean,
    @JsonProperty("accessToken")
    val accessToken: String?,
    @JsonProperty("statusMessage")
    val message: String?,
)
