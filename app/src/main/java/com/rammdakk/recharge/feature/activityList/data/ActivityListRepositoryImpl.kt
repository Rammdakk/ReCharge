package com.rammdakk.recharge.feature.activityList.data

import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.feature.activityList.domain.ActivityListRepository
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivityListRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers
) : ActivityListRepository {

    private val api = retrofit.create(ActivityListApi::class.java)

    override suspend fun getActivities(activityCatId: Int, date: Date) =
        withContext(dispatchers.IO) {
            val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
            getAccessToken()?.let {
                makeRequest {
                    api.getActivities(
                        it,
                        activityCatId,
                        dateString
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