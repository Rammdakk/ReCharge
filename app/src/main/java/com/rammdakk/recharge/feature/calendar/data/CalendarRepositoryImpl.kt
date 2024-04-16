package com.rammdakk.recharge.feature.calendar.data

import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.base.extensions.format
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.calendar.data.model.ReservationDataModel
import com.rammdakk.recharge.feature.calendar.data.network.CalendarReservationApi
import com.rammdakk.recharge.feature.calendar.domain.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.util.Date

class CalendarRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers
) : CalendarRepository {

    private val api = retrofit.create(CalendarReservationApi::class.java)
    override suspend fun loadReservations(
        startDate: Date,
        endDate: Date
    ): Result<List<ReservationDataModel>> = withContext(dispatchers.IO) {
        getAccessToken()?.let {
            makeRequest {
                api.getReservationList(
                    it,
                    startDate.format(),
                    endDate.format()
                )
            }.map { it.reservations }
        } ?: Result.failure(NetworkError(InternetError.Unauthorized))
    }

    private suspend fun getAccessToken(): String? = withContext(dispatchers.IO) {
        authRepository.getToken()
    }
}