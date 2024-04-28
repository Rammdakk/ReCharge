package com.rammdakk.recharge.feature.push.data

import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.push.data.network.NotificationApi
import com.rammdakk.recharge.feature.push.domain.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class NotificationRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers,
) : NotificationRepository {

    val api = retrofit.create(NotificationApi::class.java)
    override suspend fun updateToken(token: String) {
        withContext(dispatchers.IO) {
            runCatching {
                authRepository.getToken(logoutOnError = false)?.let {
                    api.updateFcmToken(accessToken = it, fcmToken = token)
                }
            }
        }
    }
}