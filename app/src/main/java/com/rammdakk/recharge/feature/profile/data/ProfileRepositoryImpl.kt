package com.rammdakk.recharge.feature.profile.data

import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.network.makeRequest
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
            getAccessToken()?.let {
                makeRequest { api.getUserInfo(it) }
            } ?: Result.failure(NetworkError(InternetError.Unauthorized))
        }

    override suspend fun updateProfile(profile: ProfileInfo): Result<Unit> =
        withContext(dispatchers.IO) {
            getAccessToken()?.let {
                makeRequest {
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
}