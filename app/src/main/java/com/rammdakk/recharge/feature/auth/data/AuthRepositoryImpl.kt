package com.rammdakk.recharge.feature.auth.data

import com.rammdakk.recharge.base.data.network.error.ErrorHandlerImpl
import com.rammdakk.recharge.base.data.network.error.HttpException
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences
import com.rammdakk.recharge.feature.auth.data.model.AuthCodeRequest
import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneRequest
import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneResponse
import com.rammdakk.recharge.feature.auth.data.model.AuthResponse
import com.rammdakk.recharge.feature.auth.data.net.AuthApi
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class AuthRepositoryImpl(
    retrofit: Retrofit,
    private val encryptedSharedPreferences: EncryptedSharedPreferences,
    private val dispatchers: Dispatchers
) : AuthRepository {

    private val api = retrofit.create(AuthApi::class.java)
    override suspend fun requestCode(phone: String): Result<AuthPhoneResponse> =
        withContext(dispatchers.IO) {
            val response = runCatching { api.sendPhone(AuthPhoneRequest(phone)) }.getOrNull()
                ?: return@withContext Result.failure(
                    NetworkError(
                        InternetError.Unknown,
                        "Не удалось получить значения"
                    )
                )

            if (!response.isSuccessful) {
                return@withContext Result.failure(
                    NetworkError(ErrorHandlerImpl.getErrorType(HttpException(response.code())))
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

    override suspend fun validateCode(
        code: String,
        sessionId: String,
        phone: String
    ): Result<AuthResponse> = withContext(dispatchers.IO) {
        val authCodeRequest = AuthCodeRequest(phone = phone, sessionId = sessionId, code = code)
        val response = runCatching { api.sendCode(authCodeRequest) }.getOrNull()
            ?: return@withContext Result.failure(
                NetworkError(
                    InternetError.Unknown,
                    "Не удалось получить значения"
                )
            )
        if (!response.isSuccessful) {
            return@withContext Result.failure(
                NetworkError(ErrorHandlerImpl.getErrorType(HttpException(response.code())))
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
        return@withContext response.body()!!.let {
            encryptedSharedPreferences.sharedPreferences.edit()
                .putString(ACCESS_KEY, it.accessToken).apply()
            Result.success(it)
        }
    }

    override suspend fun validateAuth(): Boolean =
        withContext(dispatchers.IO) {
            val value = encryptedSharedPreferences.sharedPreferences.getString(ACCESS_KEY, null)
            return@withContext !value.isNullOrBlank()
        }


    override suspend fun logOut() = withContext(dispatchers.IO) {
        encryptedSharedPreferences.sharedPreferences.edit().remove(ACCESS_KEY).apply()
    }

    companion object {
        private const val ACCESS_KEY = "AccessKey"
    }
}