package com.rammdakk.recharge.feature.auth.domain

class AuthValidationUseCaseImpl(
    private val authRepository: AuthRepository
) : AuthValidationUseCase {
    override suspend fun validateAuth(): Boolean {
        return authRepository.validateAuth()
    }

    override suspend fun getToken(logoutOnError: Boolean): String? {
        return authRepository.getToken(logoutOnError)
    }
}