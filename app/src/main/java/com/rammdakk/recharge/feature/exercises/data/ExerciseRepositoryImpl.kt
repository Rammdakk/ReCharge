package com.rammdakk.recharge.feature.exercises.data

import com.rammdakk.recharge.base.data.network.error.ErrorHandlerImpl
import com.rammdakk.recharge.base.data.network.error.HttpException
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences
import com.rammdakk.recharge.feature.exercises.domain.ExerciseRepository
import com.rammdakk.recharge.feature.exercises.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.models.data.SportTypeDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ExerciseRepositoryImpl(
    retrofit: Retrofit,
    private val encryptedSharedPreferences: EncryptedSharedPreferences,
    private val dispatchers: Dispatchers
) : ExerciseRepository {


    private val api = retrofit.create(ExerciseApi::class.java)

    override suspend fun getTabsCategories(): Result<List<ExerciseTabDataModel>> =
        withContext(dispatchers.IO) {
            val response = runCatching {
                api.getTabs(
                    accessToken = getAccessToken() ?: return@withContext Result.failure(
                        NetworkError(InternetError.Unauthorized)
                    ),
                )
            }.getOrNull()
                ?: return@withContext Result.failure(
                    NetworkError(
                        InternetError.Unknown,
                        "Не удалось получить значения"
                    )
                )

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

    override suspend fun getSports(tabId: Int): Result<List<SportTypeDataModel>> =
        withContext(dispatchers.IO) {
            val response = runCatching {
                api.getActivities(
                    accessToken = getAccessToken() ?: return@withContext Result.failure(
                        NetworkError(InternetError.Unauthorized)
                    ),
                    activityId = tabId
                )
            }.getOrNull()
                ?: return@withContext Result.failure(
                    NetworkError(
                        InternetError.Unknown,
                        "Не удалось получить значения"
                    )
                )

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
        encryptedSharedPreferences.sharedPreferences.getString(
            ACCESS_KEY, null
        )
    }

    companion object {
        private const val ACCESS_KEY = "AccessKey"
    }
}