package com.rammdakk.recharge.feature.reservation.data

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.reservation.data.model.ReservationMetaDataModel
import com.rammdakk.recharge.feature.reservation.data.net.ReservationApi
import com.rammdakk.recharge.feature.reservation.domain.ReservationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ReservationRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers,
    private val errorMessageConverter: ErrorMessageConverter
) : ReservationRepository {

    private val api = retrofit.create(ReservationApi::class.java)

    override suspend fun getReservationInfo(reservationId: Int): Result<ReservationMetaDataModel> =
        withContext(dispatchers.IO) {
            getAccessToken()?.let {
                makeRequest(errorMessageConverter) {
                    api.getReservationInfo(
                        accessToken = it,
                        reservationId = reservationId
                    )
                }
            } ?: Result.failure(
                NetworkError(InternetError.Unauthorized)
            )
        }

    override suspend fun cancelReservation(reservationId: Int): Result<Unit> =
        withContext(dispatchers.IO) {
            getAccessToken()?.let {
                makeRequest(errorMessageConverter) {
                    api.setReservationCanceled(
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