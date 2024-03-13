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
import retrofit2.Retrofit

class AuthRepositoryImpl(
    retrofit: Retrofit,
    private val encryptedSharedPreferences: EncryptedSharedPreferences
) : AuthRepository {

    private val api = retrofit.create(AuthApi::class.java)
    override suspend fun requestCode(phone: String): Result<AuthPhoneResponse> {
        val response = runCatching { api.sendPhone(AuthPhoneRequest(phone)) }.getOrNull()
            ?: return Result.failure(
                NetworkError(
                    InternetError.Unknown,
                    "Не удалось получить значения"
                )
            )

        if (!response.isSuccessful) {
            return Result.failure(
                NetworkError(ErrorHandlerImpl.getErrorType(HttpException(response.code())))
            )
        }
        if (response.body() == null || response.body() == null) {
            return Result.failure(
                NetworkError(
                    InternetError.Unknown,
                    "Не удалось получить значения"
                )
            )
        }
        return Result.success(response.body()!!)
    }

    override suspend fun validateCode(code: String, sessionId: String): Result<AuthResponse> {
        val authCodeRequest = AuthCodeRequest(phone = "", sessionId = sessionId, code = code)
        val response = runCatching { api.sendCode(authCodeRequest) }.getOrNull()
            ?: return Result.failure(
                NetworkError(
                    InternetError.Unknown,
                    "Не удалось получить значения"
                )
            )
        if (!response.isSuccessful) {
            return Result.failure(
                NetworkError(ErrorHandlerImpl.getErrorType(HttpException(response.code())))
            )
        }
        if (response.body() == null || response.body() == null) {
            return Result.failure(
                NetworkError(
                    InternetError.Unknown,
                    "Не удалось получить значения"
                )
            )
        }
        return response.body()!!.let {
            encryptedSharedPreferences.sharedPreferences.edit()
                .putString("AccessKey", it.accessToken).apply()
            Result.success(it)
        }
    }

    override suspend fun validateAuth(): Boolean {
        val value = encryptedSharedPreferences.sharedPreferences.getString("DATA", null)
        return !value.isNullOrBlank()
    }
}