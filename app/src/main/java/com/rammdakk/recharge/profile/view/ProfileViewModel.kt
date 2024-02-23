package com.rammdakk.recharge.profile.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.profile.data.model.Gender
import com.rammdakk.recharge.profile.data.model.ProfileInfo
import com.rammdakk.recharge.profile.domain.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private val _profileState: MutableState<ProfileScreenState> =
        mutableStateOf(ProfileScreenState.Idle)
    val profileState: State<ProfileScreenState> = _profileState

    fun loadData() = viewModelScope.launch(dispatchers.IO) {
        val profile = profileRepository.getProfile()
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
        firstName: String,
        secondName: String,
        phone: String,
        email: String,
        birthDay: Date,
        isMale: Boolean,
        city: String
    ) = viewModelScope.launch(dispatchers.IO) {
        val profileInfo = ProfileInfo(
            firstName, secondName,
            phone, email, birthDay,
            if (isMale) Gender.MALE else Gender.FEMALE,
            city
        ).let { profileRepository::updateProfile }
    }
}