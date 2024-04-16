package com.rammdakk.recharge.feature.calendar.data.network

import com.rammdakk.recharge.feature.calendar.data.model.ReservationResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CalendarReservationApi {

    @GET("api/Reservations/GetReservations")
    suspend fun getReservationList(
        @Header("accessToken") accessToken: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
    ): Response<ReservationResponseModel>
}