package com.rammdakk.recharge.feature.auth.domain

import com.rammdakk.recharge.feature.push.domain.NotificationRepository

class LogOutUseCaseImpl(
    private val authRepository: AuthRepository,
    private val notificationRepository: NotificationRepository
) : LogOutUseCase {
    override suspend fun logOut(): Boolean {
        notificationRepository.invalidateToken()
        return authRepository.logOut()
    }
}