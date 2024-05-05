package com.rammdakk.recharge.feature.push.domain

interface NotificationRepository {
    suspend fun updateToken(token: String? = null)

    suspend fun invalidateToken()
}