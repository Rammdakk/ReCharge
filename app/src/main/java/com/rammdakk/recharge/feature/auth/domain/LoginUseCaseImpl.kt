package com.rammdakk.recharge.feature.auth.domain

import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneResponse
import com.rammdakk.recharge.feature.auth.data.model.AuthResponse

class LoginUseCaseImpl(
    private val authRepository: AuthRepository
) : LoginUseCase {
    override suspend fun requestCode(phone: String): Result<AuthPhoneResponse> =
        authRepository.requestCode(phone)

    override suspend fun validateCode(
        code: String,
        sessionId: String,
        phone: String
    ): Result<AuthResponse> = authRepository.validateCode(code, sessionId, phone)
}