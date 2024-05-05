package com.rammdakk.recharge.feature.profile.domain

import com.rammdakk.recharge.feature.profile.data.models.ProfileHeaderDataModel
import com.rammdakk.recharge.feature.profile.data.models.ShortProfileInfo

interface ProfileShortInfoUseCase {

    suspend fun getProfileShortInfo(forceUpdate: Boolean = false): Result<ShortProfileInfo>

    suspend fun getProfileHeaderInfo(): Result<ProfileHeaderDataModel>
}