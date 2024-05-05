package com.rammdakk.recharge.feature.auth.domain

class LogOutUseCaseImpl(
    private val authRepository: AuthRepository
) : LogOutUseCase {
    override suspend fun logOut(): Boolean {
        return authRepository.logOut()
    }
}