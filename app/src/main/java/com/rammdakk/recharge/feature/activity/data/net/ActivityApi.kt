package com.rammdakk.recharge.feature.activity.data.net

import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ActivityApi {

    @GET()
    fun getActivityInfo(
        @Header("accessToken") accessToken: String,
        @Query("activity_id") activityId: Int
    ): Response<ActivityExtendedDataModel>

    fun getActivityTabs(
        @Header("accessToken") accessToken: String,
        @Query("activity_id") activityId: Int,
        @Query("date") date: Long
    ): Response<List<TimePadDataModel>>

}