package com.rammdakk.recharge.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.R
import com.rammdakk.recharge.auth.domain.AuthRepository
import com.rammdakk.recharge.auth.view.AuthScreenState
import com.rammdakk.recharge.auth.view.BottomInfo
import com.rammdakk.recharge.auth.view.ExtraInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _authState: MutableState<AuthScreenState> = mutableStateOf(AuthScreenState.Idle)
    private val _extraInfo: MutableState<ExtraInfo?> = mutableStateOf(null)
    private var data: MutableState<Boolean> = mutableStateOf(false)
    private var sessionId: String? = null

    val authState: State<AuthScreenState> = _authState

    val isLoggedIn get() = data.value

    val isLoggedInFlow get() = data

    fun init() {
        _extraInfo.value = null
        _authState.value = AuthScreenState.RequestPhone(
            greetingText = R.string.greeting,
            hintText = null,
            onClick = this::requestCode,
            extraInfo = _extraInfo
        )
    }

    private fun requestCode(phoneNumber: String) {
        viewModelScope.launch {
            val result = authRepository.requestCode(phoneNumber)
            if (result.isSuccess) {
                sessionId = result.sessionId
                _authState.value = AuthScreenState.RequestCode(
                    greetingText = result.titleText,
                    codeSize = result.codeSize,
                    onBackPressed = this@MainViewModel::init,
                    onClick = this@MainViewModel::validateCode,
                    extraInfo = _extraInfo,
                    bottomInfo = mutableStateOf(
                        BottomInfo(result.conditionalInfo.message, result.conditionalInfo.url)
                    )
                )
                _extraInfo.value = ExtraInfo(
                    result.resendMessage, false
                ) { requestCode(phoneNumber) }
            } else {
                _extraInfo.value = ExtraInfo(result.resendMessage, true, null)
            }
        }
    }

    private fun validateCode(code: String) {
        viewModelScope.launch {
            sessionId?.let {
                authRepository.validateCode(code, it).let { success ->
                    if (success) {
                        isLoggedInFlow.value = true
                    }
                }
            } ?: init()
        }
    }
}