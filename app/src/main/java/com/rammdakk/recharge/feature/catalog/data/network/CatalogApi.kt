package com.rammdakk.recharge.feature.catalog.data.network

import com.rammdakk.recharge.feature.catalog.data.model.ActivityRecommendationDataModel
import com.rammdakk.recharge.feature.catalog.data.model.CategoryDataModel
import com.rammdakk.recharge.feature.catalog.data.model.NextActivityDataModel
import com.rammdakk.recharge.feature.catalog.data.model.ProfileDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface CatalogApi {
    @GET("api/User/GetProfileHeader")
    suspend fun getProfileInfo(
        @Header("accessToken") accessToken: String
    ): Response<ProfileDataModel>

    @GET("/api/Activity/GetNextActivity")
    suspend fun getNextActivity(
        @Header("accessToken") accessToken: String
    ): Response<NextActivityDataModel>


    @GET("api/Category/GetCategories")
    suspend fun getCategories(@Header("accessToken") accessToken: String): Response<List<CategoryDataModel>>

    @GET("/api/Activity/GetActivitiesRecommendations")
    suspend fun getActivitiesForCategory(
        @Header("accessToken") accessToken: String,
        @Query("category_id") categoryId: Int?
    ): Response<List<ActivityRecommendationDataModel>>
}