package com.rammdakk.recharge.feature.auth.data.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Отправляется после получения номера телефона
 * @param isSuccess валиден ли номер телефона или нет
 * @param errorText текст ошибки, если isSuccess = false
 * @param sessionId сессия для авторизации
 * @param titleText текст заголовка на экране вода кода из смс,
 * например, "Введите код из смс"
 * @param codeSize кол-во символов в коде авторизации
 * @param conditionalInfo информация об условиях пользования,оферте
 */

data class AuthPhoneResponse(
    @JsonProperty("isSuccess")
    val isSuccess: Boolean,
    @JsonProperty("errorText")
    val errorText: String? = null,
    @JsonProperty("sessionId")
    val sessionId: String? = null,
    @JsonProperty("titleText")
    val titleText: String? = null,
    @JsonProperty("codeSize")
    val codeSize: Int? = 4,
    @JsonProperty("conditionalInfo")
    val conditionalInfo: ConditionalInfo? = null,
)

/**
 * Содержит информацию об условиях пользования,оферт
 * @param message текст
 * @param url ссылка на условия
 */

data class ConditionalInfo(
    @JsonProperty("message")
    val message: String,
    @JsonProperty("url")
    val url: String
)

