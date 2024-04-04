package com.rammdakk.recharge.feature.profile.data

import com.rammdakk.recharge.base.data.network.error.ErrorHandlerImpl
import com.rammdakk.recharge.base.data.network.error.HttpException
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.profile.data.net.ProfileApi
import com.rammdakk.recharge.feature.profile.domain.ProfileRepository
import com.rammdakk.recharge.feature.profile.models.data.ProfileInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ProfileRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers
) : ProfileRepository {


    private val api = retrofit.create(ProfileApi::class.java)

    override suspend fun getProfile(): Result<ProfileInfo> =
        withContext(dispatchers.IO) {
            val response = runCatching {
                api.getUserInfo(
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

    override suspend fun updateProfile(profile: ProfileInfo): Unit =
        withContext(dispatchers.IO) {
            runCatching {
                api.updateUser(
                    accessToken = getAccessToken() ?: return@withContext,
                    profileInfo = profile
                )
            }
        }

    private suspend fun getAccessToken(): String? = withContext(dispatchers.IO) {
        authRepository.getToken()
    }

    companion object {
        private const val ACCESS_KEY = "AccessKey"
    }
}