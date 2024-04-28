package com.rammdakk.recharge.feature.push.domain

interface NotificationRepository {
    suspend fun updateToken(token: String)
}