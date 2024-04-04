package com.rammdakk.recharge.feature.activityList.data

import com.rammdakk.recharge.feature.activityList.data.model.ActivityListDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ActivityListApi {
    @GET("/api/Activities/List")
    suspend fun getActivities(
        @Header("accessToken") accessToken: String,
        @Query("activityCatId") activityCatId: Int,
        @Query("date") date: Long
    ): Response<ActivityListDataModel>

}