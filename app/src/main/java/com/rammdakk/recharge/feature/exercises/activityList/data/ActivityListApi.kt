package com.rammdakk.recharge.feature.exercises.activityList.data

import com.rammdakk.recharge.feature.exercises.activityList.data.model.ActivityListDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ActivityListApi {
    @GET("/api/Slot/GetSlotsByCategoryIdAndTime")
    suspend fun getActivities(
        @Header("accessToken") accessToken: String,
        @Query("categoryId") activityCatId: Int,
        @Query("dateTime") date: String
    ): Response<ActivityListDataModel>

}