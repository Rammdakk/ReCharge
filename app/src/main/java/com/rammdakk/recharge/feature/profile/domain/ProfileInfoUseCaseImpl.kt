package com.rammdakk.recharge.feature.profile.domain

import com.rammdakk.recharge.feature.profile.data.models.ProfileInfo

class ProfileInfoUseCaseImpl(
    private val profileRepository: ProfileRepository
) : ProfileInfoUseCase {
    override suspend fun getProfile(): Result<ProfileInfo> =
        profileRepository.getProfile()

    override suspend fun updateProfile(profile: ProfileInfo): Result<Unit> =
        profileRepository.updateProfile(profile)
}