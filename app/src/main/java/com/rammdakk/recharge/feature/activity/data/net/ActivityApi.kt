package com.rammdakk.recharge.feature.activity.data.net

import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityApi {

    @GET("/api/Activity/GetActivityView")
    suspend fun getActivityInfo(
        @Query("id") activityId: Int
    ): Response<ActivityExtendedDataModel>

    @GET("/api/Slot/GetActivityViewSlots")
    suspend fun getActivityTabs(
        @Query("activityId") activityId: Int,
        @Query("date") date: Long
    ): Response<List<TimePadDataModel>>

}