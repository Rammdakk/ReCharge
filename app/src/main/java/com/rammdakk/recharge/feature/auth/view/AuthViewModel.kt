package com.rammdakk.recharge.feature.auth.view

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.R
import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneResponse
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val resources: Resources
) : ViewModel() {

    private val _authState: MutableState<AuthScreenState> = mutableStateOf(AuthScreenState.Idle)
    private val _errorMessage: MutableState<String?> = mutableStateOf(null)
    private val _isLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _openLink: MutableLiveData<String?> = MutableLiveData(null)
    private var sessionId: String? = null

    val authState: State<AuthScreenState> = _authState

    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    val openLink: LiveData<String?>
        get() = _openLink

    fun init() {
        checkLoginStatus()
        _errorMessage.value = null
        _authState.value = AuthScreenState.RequestPhone(
            greetingText = R.string.greeting,
            hintText = null,
            onRequestCodeClick = this::requestCode,
            errorMessage = _errorMessage
        )
    }

    private fun checkLoginStatus() = viewModelScope.launch {
        _isLoggedIn.value = authRepository.validateAuth()
    }

    private fun requestCode(phoneNumber: String) {
        viewModelScope.launch {
            _errorMessage.value = null

            authRepository.requestCode(phoneNumber).fold(
                onSuccess = { result ->
                    updateState(result, phoneNumber)
                },
                onFailure = {}
            )


        }
    }

    private fun updateState(
        result: AuthPhoneResponse,
        phoneNumber: String
    ) {
        if (result.isSuccess) {
            sessionId = result.sessionId
            _authState.value = AuthScreenState.RequestCode(
                greetingText = result.titleText
                    ?: resources.getString(R.string.request_code_title),
                codeSize = result.codeSize ?: 0,
                onBackPressed = this@AuthViewModel::init,
                onSubmitClick = this@AuthViewModel::validateCode,
                onRequestCodeClick = { requestCode(phoneNumber) },
                errorMessage = _errorMessage,
                bottomInfo = mutableStateOf(
                    result.conditionalInfo?.let { info ->
                        BottomInfo(info.message) {
                            _openLink.value = info.url
                        }
                    }
                )
            )
        } else {
            _errorMessage.value = result.errorText
        }
    }


    private fun validateCode(code: String) = viewModelScope.launch {
        sessionId?.let {
            authRepository.validateCode(code, it).fold(
                onSuccess = { result ->
                    if (result.isSuccess) {
                        _isLoggedIn.value = true
                    } else {
                        _errorMessage.value = result.message
                    }

                },
                onFailure = {}
            )
        } ?: init()
    }
}