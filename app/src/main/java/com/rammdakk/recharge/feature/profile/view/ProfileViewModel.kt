package com.rammdakk.recharge.feature.profile.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.profile.domain.ProfileRepository
import com.rammdakk.recharge.feature.profile.models.data.Gender
import com.rammdakk.recharge.feature.profile.models.data.ProfileInfo
import com.rammdakk.recharge.feature.profile.models.presentation.ProfileScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private val _profileState: MutableState<ProfileScreenState> =
        mutableStateOf(ProfileScreenState.Idle)
    val profileState: State<ProfileScreenState> = _profileState

    fun loadData() = viewModelScope.launch {
        val profile = profileRepository.getProfile().getOrNull() ?: return@launch
        withContext(dispatchers.Main) {
            _profileState.value = ProfileScreenState.Loaded(
                firstName = profile.firstName,
                secondName = profile.secondName,
                phone = profile.phone,
                email = profile.email,
                birthDay = profile.birthDay,
                isMale = (profile.gender == Gender.MALE),
                city = profile.city
            )
        }
    }

    fun updateData(
        profileScreenModel: ProfileScreenModel
    ) = viewModelScope.launch(dispatchers.IO) {
        profileScreenModel.covertToProfileInfo()
            .let { profileRepository.updateProfile(it) }
    }

    private fun ProfileScreenModel.covertToProfileInfo(): ProfileInfo = ProfileInfo(
        firstName, secondName,
        phone, email, Date(birthDay),
        gender,
        city
    )


    fun logOut() = viewModelScope.launch {
        authRepository.logOut()
    }
}