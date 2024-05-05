package com.rammdakk.recharge.feature.profile.data

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.base.data.sp.CustomSharedPreferences
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.profile.data.models.ProfileInfo
import com.rammdakk.recharge.feature.profile.data.models.ShortProfileInfo
import com.rammdakk.recharge.feature.profile.data.net.ProfileApi
import com.rammdakk.recharge.feature.profile.domain.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ProfileRepositoryImpl(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers,
    private val sharedPreferences: CustomSharedPreferences,
    private val errorMessageConverter: ErrorMessageConverter
) : ProfileRepository {


    private val api = retrofit.create(ProfileApi::class.java)

    override suspend fun getProfile(): Result<ProfileInfo> =
        withContext(dispatchers.IO) {
            getAccessToken()?.let {
                async { getProfileShortInfo(forceUpdate = true) }
                makeRequest(errorMessageConverter) { api.getUserInfo(it) }
            } ?: Result.failure(NetworkError(InternetError.Unauthorized))
        }

    override suspend fun getProfileShortInfo(forceUpdate: Boolean): Result<ShortProfileInfo> {
        if (!forceUpdate) {
            withContext(dispatchers.IO) {
                sharedPreferences.getData(PROFILE_INFO_KEY, ShortProfileInfo::class.java)?.let {
                    return@withContext Result.success(it)
                }
            }
        }
        return withContext(dispatchers.IO) {
            getAccessToken()?.let {
                makeRequest(errorMessageConverter) { api.getUserShortInfo(it) }.onSuccess {
                    sharedPreferences.saveData(it, PROFILE_INFO_KEY)
                }
            } ?: Result.failure(NetworkError(InternetError.Unauthorized))
        }
    }


    override suspend fun updateProfile(profile: ProfileInfo): Result<Unit> =
        withContext(dispatchers.IO) {
            getAccessToken()?.let {
                makeRequest(errorMessageConverter) {
                    api.updateUser(
                        accessToken = it,
                        profileInfo = profile
                    )
                }
            } ?: Result.failure(NetworkError(InternetError.Unauthorized))
        }

    private suspend fun getAccessToken(): String? = withContext(dispatchers.IO) {
        authRepository.getToken()
    }

    private companion object {
        private const val PROFILE_INFO_KEY = "Profile"
    }
}