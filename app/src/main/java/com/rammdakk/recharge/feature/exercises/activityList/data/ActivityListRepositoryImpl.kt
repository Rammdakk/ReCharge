package com.rammdakk.recharge.feature.exercises.activityList.data

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.base.extensions.formatToUtcString
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.exercises.activityList.domain.ActivityListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.util.Date

class ActivityListRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers,
    private val errorMessageConverter: ErrorMessageConverter
) : ActivityListRepository {

    private val api = retrofit.create(ActivityListApi::class.java)

    override suspend fun getActivities(activityCatId: Int, date: Date) =
        withContext(dispatchers.IO) {
            getAccessToken()?.let {
                makeRequest(errorMessageConverter) {
                    api.getActivities(
                        it,
                        activityCatId,
                        date.formatToUtcString()
                    )
                }
            } ?: Result.failure(NetworkError(InternetError.Unauthorized))
        }

    private suspend fun getAccessToken(): String? = withContext(dispatchers.IO) {
        authRepository.getToken()
    }
}