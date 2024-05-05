package com.rammdakk.recharge.feature.profile.domain

import com.rammdakk.recharge.feature.profile.data.models.ShortProfileInfo

class ProfileShortInfoUseCaseImpl(
    private val profileRepository: ProfileRepository
) : ProfileShortInfoUseCase {
    override suspend fun getProfileShortInfo(forceUpdate: Boolean): Result<ShortProfileInfo> =
        profileRepository.getProfileShortInfo(forceUpdate)
}