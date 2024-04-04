package com.rammdakk.recharge.feature.activityList.data

import com.rammdakk.recharge.base.data.network.error.ErrorHandlerImpl
import com.rammdakk.recharge.base.data.network.error.HttpException
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.feature.activityList.domain.ActivityListRepository
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ActivityListRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers
) : ActivityListRepository {

    private val api = retrofit.create(ActivityListApi::class.java)

    override suspend fun getActivities(activityCatId: Int, date: Long) =
        withContext(dispatchers.IO) {
            val response =
//            runCatching {
                api.getActivities(
                    accessToken = getAccessToken() ?: return@withContext Result.failure(
                        NetworkError(InternetError.Unauthorized)
                    ),
                    activityCatId = activityCatId,
                    date = date
                )
//        }.getOrNull()
//            ?: return@withContext Result.failure(
//                NetworkError(
//                    InternetError.Unknown,
//                    "Не удалось получить значения"
//                )
//            )

            if (!response.isSuccessful) {
                return@withContext Result.failure(
                    NetworkError(
                        ErrorHandlerImpl.getErrorType(HttpException(response.code())),
                        response.message()
                    )
                )
            }
            if (response.body() == null || response.body() == null) {
                return@withContext Result.failure(
                    NetworkError(
                        InternetError.Unknown,
                        "Не удалось получить значения"
                    )
                )
            }
            return@withContext Result.success(response.body()!!)
        }

    private suspend fun getAccessToken(): String? = withContext(dispatchers.IO) {
        authRepository.getToken()
    }
}