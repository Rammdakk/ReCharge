package com.rammdakk.recharge.feature.activity.data.net

import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.FreeSpotsDataModel
import com.rammdakk.recharge.feature.activity.data.model.ListTimePadModel
import com.rammdakk.recharge.feature.activity.data.model.UserBookingDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ActivityApi {

    @GET("/api/Activity/GetActivityView")
    suspend fun getActivityInfo(
        @Query("id") activityId: Int
    ): Response<ActivityExtendedDataModel>

    @GET("/api/Slot/GetActivityViewSlots")
    suspend fun getActivityTabs(
        @Query("activityId") activityId: Int,
        @Query("date") date: String
    ): Response<ListTimePadModel>

    @GET("/api/Slot/GetSlotFreeSpots")
    suspend fun getUsersMaxNumber(
        @Query("id") tabId: Int
    ): Response<FreeSpotsDataModel>

    @POST("/api/Reservations/MakeReservation")
    suspend fun reserveActivity(
        @Header("accessToken") accessToken: String,
        @Query("slotId") tabId: Int,
        @Body bookingInfo: UserBookingDataModel
    ): Response<Unit>

}