package com.rammdakk.recharge.base.view.component.error

sealed interface ErrorState {
    data object Idle : ErrorState

    data class Success(val text: String) : ErrorState

    data class Error(val text: String) : ErrorState
}