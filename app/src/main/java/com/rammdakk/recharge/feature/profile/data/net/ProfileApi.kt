package com.rammdakk.recharge.feature.profile.data.net

import com.rammdakk.recharge.feature.profile.data.models.ProfileHeaderDataModel
import com.rammdakk.recharge.feature.profile.data.models.ProfileInfo
import com.rammdakk.recharge.feature.profile.data.models.ShortProfileInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ProfileApi {
    @GET("/api/User/GetUserByAccessToken")
    suspend fun getUserInfo(
        @Header("accessToken") accessToken: String,
    ): Response<ProfileInfo>

    @GET("/api/User/GetUserByAccessToken")
    suspend fun getUserShortInfo(
        @Header("accessToken") accessToken: String,
    ): Response<ShortProfileInfo>

    @GET("/api/User/GetProfileHeader")
    suspend fun getUserHeader(
        @Header("accessToken") accessToken: String,
    ): Response<ProfileHeaderDataModel>

    @POST("/api/User/UpdateUser")
    suspend fun updateUser(
        @Header("accessToken") accessToken: String,
        @Body profileInfo: ProfileInfo
    ): Response<Unit>
}