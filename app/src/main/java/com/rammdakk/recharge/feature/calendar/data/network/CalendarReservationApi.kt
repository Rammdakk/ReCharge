package com.rammdakk.recharge.feature.calendar.data.network

import com.rammdakk.recharge.feature.calendar.data.model.ReservationDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.Date

interface CalendarReservationApi {

    @GET()
    suspend fun getProfileInfo(
        @Header("accessToken") accessToken: String,
        @Query("start_date") startDate: Date,
        @Query("end_date") endDate: Date,
    ): Response<List<ReservationDataModel>>
}