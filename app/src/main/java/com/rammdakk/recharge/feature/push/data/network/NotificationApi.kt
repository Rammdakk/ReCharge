package com.rammdakk.recharge.feature.push.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NotificationApi {
    @POST("/api/Reservations/GetNextReservation")
    suspend fun updateFcmToken(
        @Header("accessToken") accessToken: String,
        @Body fcmToken: String
    ): Response<Unit>
}