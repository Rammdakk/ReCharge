package com.rammdakk.recharge.feature.auth.data.model

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
    val isSuccess: Boolean,
    val errorText: String?,
    val sessionId: String?,
    val titleText: String?,
    val codeSize: Int? = 4,
    val conditionalInfo: ConditionalInfo?,
)

/**
 * Содержит информацию об условиях пользования,оферт
 * @param message текст
 * @param url ссылка на условия
 */

data class ConditionalInfo(
    val message: String,
    val url: String
)

/**
 * Отправляется после получения кода
 * @param accessToken при успешной регистрации отправляется токен для дальнейшего использования
 * @param message сообщение об ошибке
 */

data class AuthResponse(
    val isSuccess: Boolean,
    val accessToken: String?,
    val message: String?,
)