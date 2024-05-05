package com.rammdakk.recharge.feature.auth.domain

import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneResponse
import com.rammdakk.recharge.feature.auth.data.model.AuthResponse

interface LoginUseCase {

    /**
     * Запрос кода
     *
     * @param phone номер телефона авторизации
     */
    suspend fun requestCode(phone: String): Result<AuthPhoneResponse>

    /**
     * Проверка введенного пользователем кода
     *
     * @param code код, введенный пользователем
     * @param sessionId id сессии авторизации
     * @param phone номер телефона авторизации
     */
    suspend fun validateCode(code: String, sessionId: String, phone: String): Result<AuthResponse>
}