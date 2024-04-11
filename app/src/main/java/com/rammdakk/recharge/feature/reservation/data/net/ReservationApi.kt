package com.rammdakk.recharge.feature.reservation.data.net

import com.rammdakk.recharge.feature.reservation.data.model.ReservationDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ReservationApi {

    @GET()
    fun getReservationInfo(
        @Header("accessToken") accessToken: String,
        @Query("reservation_id") reservationId: Int
    ): Response<ReservationDataModel>
}