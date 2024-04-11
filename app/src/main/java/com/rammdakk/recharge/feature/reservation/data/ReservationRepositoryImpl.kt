package com.rammdakk.recharge.feature.reservation.data

import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.reservation.data.model.ReservationDataModel
import com.rammdakk.recharge.feature.reservation.data.net.ReservationApi
import com.rammdakk.recharge.feature.reservation.domain.ReservationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ReservationRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers
) : ReservationRepository {

    private val api = retrofit.create(ReservationApi::class.java)

    override suspend fun getReservationInfo(reservationId: Int): Result<ReservationDataModel> =
        withContext(dispatchers.IO) {
            getAccessToken()?.let {
                makeRequest {
                    api.getReservationInfo(
                        accessToken = it,
                        reservationId = reservationId
                    )
                }
            } ?: Result.failure(
                NetworkError(InternetError.Unauthorized)
            )
        }

    private suspend fun getAccessToken(): String? = withContext(dispatchers.IO) {
        authRepository.getToken()
    }
}