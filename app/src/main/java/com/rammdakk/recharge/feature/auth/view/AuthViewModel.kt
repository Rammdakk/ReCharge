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
import com.rammdakk.recharge.feature.auth.domain.AuthValidationUseCase
import com.rammdakk.recharge.feature.auth.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authValidationUseCase: AuthValidationUseCase,
    private val loginUseCase: LoginUseCase,
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

    fun init() = viewModelScope.launch {
        if (!checkLoginStatus()) {
            _errorMessage.value = null
            _authState.value = AuthScreenState.RequestPhone(
                greetingText = R.string.greeting,
                hintText = null,
                onRequestCodeClick = this@AuthViewModel::requestCode,
                errorMessage = _errorMessage
            )
        }
    }

    private suspend fun checkLoginStatus(): Boolean {
        return authValidationUseCase.validateAuth().also {
            _isLoggedIn.value = it
        }
    }

    private fun requestCode(phoneNumber: String) {
        viewModelScope.launch {
            _errorMessage.value = null

            loginUseCase.requestCode(phoneNumber).fold(
                onSuccess = { result ->
                    updateState(result, phoneNumber)
                },
                onFailure = {
                    _errorMessage.value = it.message
                }
            )


        }
    }

    private fun updateState(
        result: AuthPhoneResponse,
        phoneNumber: String
    ) {
        sessionId = result.sessionId
        _authState.value = AuthScreenState.RequestCode(
            greetingText = result.titleText
                ?: resources.getString(R.string.request_code_title),
            codeSize = result.codeSize ?: 0,
            onBackPressed = this@AuthViewModel::init,
            onSubmitClick = { validateCode(it, phoneNumber) },
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
    }


    private fun validateCode(code: String, phoneNumber: String) = viewModelScope.launch {
        sessionId?.let {
            loginUseCase.validateCode(code, it, phoneNumber).fold(
                onSuccess = {
                    _isLoggedIn.value = true
                },
                onFailure = {
                    _errorMessage.value = it.message
                }
            )
        } ?: init()
    }
}