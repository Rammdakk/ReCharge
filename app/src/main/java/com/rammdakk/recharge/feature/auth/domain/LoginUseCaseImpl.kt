package com.rammdakk.recharge.feature.auth.domain

import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneResponse
import com.rammdakk.recharge.feature.auth.data.model.AuthResponse
import com.rammdakk.recharge.feature.push.domain.NotificationRepository

class LoginUseCaseImpl(
    private val authRepository: AuthRepository,
    private val notificationRepository: NotificationRepository
) : LoginUseCase {
    override suspend fun requestCode(phone: String): Result<AuthPhoneResponse> =
        authRepository.requestCode(phone)

    override suspend fun validateCode(
        code: String,
        sessionId: String,
        phone: String
    ): Result<AuthResponse> {
        val result = authRepository.validateCode(code, sessionId, phone)
        if (result.isSuccess) {
            notificationRepository.updateToken()
        }
        return result
    }
}