package com.rammdakk.recharge.feature.auth.data.net

import com.rammdakk.recharge.feature.auth.data.model.AuthCodeRequest
import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneRequest
import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneResponse
import com.rammdakk.recharge.feature.auth.data.model.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/Authorization/AuthPhone")
    suspend fun sendPhone(@Body authPhoneRequest: AuthPhoneRequest): Response<AuthPhoneResponse>

    @POST("/api/Authorization/Auth")
    suspend fun sendCode(@Body authCodeRequest: AuthCodeRequest): Response<AuthResponse>

    @POST("/api/User/LogOut")
    suspend fun logOut(@Header("accessToken") accessToken: String): Response<Unit>
}