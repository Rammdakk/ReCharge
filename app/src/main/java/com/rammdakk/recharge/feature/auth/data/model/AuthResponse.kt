package com.rammdakk.recharge.feature.auth.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Отправляется после получения кода
 * @param accessToken при успешной регистрации отправляется токен для дальнейшего использования
 * @param message сообщение об ошибке
 */

@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthResponse(
    @JsonProperty("isSuccess")
    val isSuccess: Boolean = false,
    @JsonProperty("accessToken")
    val accessToken: String? = null,
    @JsonProperty("statusMessage")
    val message: String? = null,
)
