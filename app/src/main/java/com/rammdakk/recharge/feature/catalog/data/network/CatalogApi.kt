package com.rammdakk.recharge.feature.catalog.data.network

import com.rammdakk.recharge.feature.catalog.data.model.ActivityDataModel
import com.rammdakk.recharge.feature.catalog.data.model.CategoryDataModel
import com.rammdakk.recharge.feature.catalog.data.model.ProfileDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CatalogApi {
    @GET("api/activity/GetActivities")
    suspend fun getActivities(): Response<List<ActivityDataModel>>

    @GET("api/Activity/GetActivityByCategory")
    suspend fun getActivitiesForCategory(@Query("categoryId") categoryId: Int): Response<List<ActivityDataModel>>

    @GET("api/Category/GetCategories")
    suspend fun getCategories(): Response<List<CategoryDataModel>>

    @GET("api/User/GetProfileHeader")
    suspend fun getCategories(
        @Query("userId") userId: Int,
        @Query("accessToken") accessToken: String
    ): Response<ProfileDataModel>
}