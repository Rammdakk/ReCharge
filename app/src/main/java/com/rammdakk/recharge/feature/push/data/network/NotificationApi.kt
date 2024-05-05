package com.rammdakk.recharge.feature.push.data.network

import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationApi {
    @POST("/api/User/SetFirebaseToken")
    suspend fun updateFcmToken(
        @Header("accessToken") accessToken: String,
        @Query("firebaseToken") fcmToken: String
    ): Response<Unit>
}