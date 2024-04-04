package com.rammdakk.recharge.feature.profile.data.net

import com.rammdakk.recharge.feature.profile.models.data.ProfileHeader
import com.rammdakk.recharge.feature.profile.models.data.ProfileInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ProfileApi {
    @GET("/api/User/GetUserByNumber")
    suspend fun getUserInfo(
        @Header("accessToken") accessToken: String,
    ): Response<ProfileInfo>

    @GET("/api/User/GetProfileHeader")
    suspend fun getUserHeader(
        @Header("accessToken") accessToken: String,
    ): Response<ProfileHeader>

    @POST("/api/User/UpdateUser")
    suspend fun updateUser(
        @Header("accessToken") accessToken: String,
        @Body profileInfo: ProfileInfo
    )
}