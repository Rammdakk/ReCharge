package com.rammdakk.recharge.feature.auth.domain

interface AuthValidationUseCase {

    /**
     * Проверка, авторизован ли пользователь
     */
    suspend fun validateAuth(): Boolean

    /**
     * Запрос на получение токена
     */
    suspend fun getToken(logoutOnError: Boolean = true): String?
}