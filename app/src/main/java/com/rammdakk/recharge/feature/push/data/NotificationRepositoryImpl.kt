package com.rammdakk.recharge.feature.push.data

import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.push.data.network.NotificationApi
import com.rammdakk.recharge.feature.push.domain.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class NotificationRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers,
) : NotificationRepository {

    private val api = retrofit.create(NotificationApi::class.java)
    override suspend fun updateToken(token: String?) {
        withContext(dispatchers.IO) {
            val realToken =
                token ?: runCatching {
                    Firebase.messaging.token.await()
                }.getOrNull() ?: return@withContext

            runCatching {
                authRepository.getToken(logoutOnError = false)?.let {
                    api.updateFcmToken(accessToken = it, fcmToken = realToken)
                }
            }
        }
    }

    override suspend fun invalidateToken(): Unit = withContext(dispatchers.IO) {
        runCatching { Firebase.messaging.deleteToken() }
    }
}