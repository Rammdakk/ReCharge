package com.rammdakk.recharge.feature.profile.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.base.view.component.error.ErrorState
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.profile.domain.ProfileRepository
import com.rammdakk.recharge.feature.profile.models.data.Gender
import com.rammdakk.recharge.feature.profile.models.data.ProfileInfo
import com.rammdakk.recharge.feature.profile.models.presentation.ProfileScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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

    private var errorJob: Job? = null

    private val _profileState: MutableState<ProfileScreenState> =
        mutableStateOf(ProfileScreenState.Idle)
    private val _isLoggedOut: MutableState<LogOutState> =
        mutableStateOf(LogOutState.LOGIN)
    private var _errorState = mutableStateOf<ErrorState>(ErrorState.Idle)

    private var profile: ProfileInfo? = null

    val profileState: State<ProfileScreenState> = _profileState
    val isLoggedOut: State<LogOutState> = _isLoggedOut
    val errorState: State<ErrorState> = _errorState

    fun loadData() = viewModelScope.launch {
        if (profile != null) {
            updateScreenValues()
        }
        val newProfile = profileRepository.getProfile().getOrElse { handleError(it) }
        if (newProfile == null) {
            if (_profileState.value is ProfileScreenState.Idle) {
                _profileState.value = ProfileScreenState.Error
                return@launch
            }
        }
        if (newProfile != profile) {
            profile = newProfile
            updateScreenValues()
        }
    }

    fun updateData(
        profileScreenModel: ProfileScreenModel
    ) = viewModelScope.launch(dispatchers.IO) {
        profileScreenModel.covertToProfileInfo()
            .let {
                profile = it
                profileRepository.updateProfile(it).getOrElse { handleError(it) }
            }

    }

    private suspend fun updateScreenValues() {
        withContext(dispatchers.Main) {
            _profileState.value = ProfileScreenState.Loaded(
                firstName = profile?.firstName.orEmpty(),
                secondName = profile?.secondName.orEmpty(),
                phone = profile?.phone.orEmpty(),
                email = profile?.email.orEmpty(),
                birthDay = profile?.birthDay,
                isMale = profile?.gender?.let { it == Gender.MALE },
                city = profile?.city.orEmpty(),
                errorState = _errorState
            )
        }
    }

    private fun ProfileScreenModel.covertToProfileInfo(): ProfileInfo = ProfileInfo(
        firstName, secondName,
        phone,
        email, birthDay?.let { Date(it) },
        gender,
        city
    )

    fun logOut(onLogOutSuccess: () -> Unit) = viewModelScope.launch {
        _isLoggedOut.value = LogOutState.IN_PROGRESS
        authRepository.logOut().let {
            _isLoggedOut.value = LogOutState.LOGOUT
            onLogOutSuccess.invoke()
        }
    }

    private suspend fun handleError(throwable: Throwable) = withContext(dispatchers.Main) {
        errorJob?.cancel()
        _errorState.value = ErrorState.Error(throwable.message.toString())
        errorJob = async {
            delay(2000)
            _errorState.value = ErrorState.Idle
        }
        null
    }
}

public enum class LogOutState { LOGIN, IN_PROGRESS, LOGOUT }