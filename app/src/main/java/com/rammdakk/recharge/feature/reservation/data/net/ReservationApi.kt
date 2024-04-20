package com.rammdakk.recharge.feature.reservation.data.net

import com.rammdakk.recharge.feature.reservation.data.model.ReservationMetaDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ReservationApi {

    @GET("/api/Reservations/GetReservation")
    suspend fun getReservationInfo(
        @Header("accessToken") accessToken: String,
        @Query("reservationId") reservationId: Int
    ): Response<ReservationMetaDataModel>
}