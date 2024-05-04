package com.rammdakk.recharge.feature.auth.data

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.base.data.sp.CustomSharedPreferences
import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences
import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences.Companion.ACCESS_KEY
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
    private val customSharedPreferences: CustomSharedPreferences,
    private val dispatchers: Dispatchers,
    private val errorMessageConverter: ErrorMessageConverter
) : AuthRepository {

    private val api = retrofit.create(AuthApi::class.java)
    override suspend fun requestCode(phone: String): Result<AuthPhoneResponse> =
        withContext(dispatchers.IO) {
            makeRequest(errorMessageConverter) { api.sendPhone(AuthPhoneRequest(phone)) }
        }

    override suspend fun validateCode(
        code: String,
        sessionId: String,
        phone: String
    ): Result<AuthResponse> = withContext(dispatchers.IO) {
        val authCodeRequest = AuthCodeRequest(phone = phone, sessionId = sessionId, code = code)

        return@withContext makeRequest(errorMessageConverter) { api.sendCode(authCodeRequest) }.also { result ->
            result.getOrNull()?.let {
                encryptedSharedPreferences.sharedPreferences.edit()
                    .putString(ACCESS_KEY, it.accessToken).apply()
            }
        }
    }

    override suspend fun validateAuth(): Boolean =
        withContext(dispatchers.IO) {
            val value = encryptedSharedPreferences.sharedPreferences.getString(ACCESS_KEY, null)
            return@withContext !value.isNullOrBlank()
        }


    override suspend fun getToken(logoutOnError: Boolean): String? =
        withContext(dispatchers.IO) {
            val value = encryptedSharedPreferences.sharedPreferences.getString(ACCESS_KEY, null)
            if (value.isNullOrBlank() && logoutOnError) logOut()
            return@withContext value
        }


    override suspend fun logOut(): Boolean = withContext(dispatchers.IO) {
        getToken(logoutOnError = false)?.let { makeRequest(errorMessageConverter) { api.logOut(it) } }
        encryptedSharedPreferences.sharedPreferences.edit().remove(ACCESS_KEY).apply()
        customSharedPreferences.onLogOut()
        return@withContext getToken(logoutOnError = false).isNullOrBlank()
    }

}