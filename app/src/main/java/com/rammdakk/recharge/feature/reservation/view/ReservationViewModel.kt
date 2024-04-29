package com.rammdakk.recharge.feature.reservation.view

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.view.component.error.ErrorState
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import com.rammdakk.recharge.feature.activity.view.model.convertToActivityInfo
import com.rammdakk.recharge.feature.reservation.domain.ReservationRepository
import com.rammdakk.recharge.feature.reservation.view.model.convertToReservationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val reservationRepository: ReservationRepository,
    private val resources: Resources,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private var errorJob: Job? = null

    private val _screenState: MutableState<ReservationScreenState> =
        mutableStateOf(ReservationScreenState.Idle)
    private val _errorState: MutableState<ErrorState> = mutableStateOf(ErrorState.Idle)

    val screenState: State<ReservationScreenState> = _screenState
    val errorState: State<ErrorState> = _errorState

    fun loadData(reservationId: Int, activityId: Int) = viewModelScope.launch(dispatchers.IO) {
        val reservationDeferred = async {
            reservationRepository.getReservationInfo(reservationId = reservationId)
                .getOrElse { error -> handleError(error) }?.convertToReservationInfo()
        }

        val activityInfoDeferred = async {
            activityRepository.getActivityInfo(activityId)
                .getOrElse { error -> handleError(error) }?.convertToActivityInfo()
        }

        withContext(dispatchers.Main) {
            _screenState.value = ReservationScreenState.Loaded(
                activityInfo = activityInfoDeferred.await() ?: return@withContext,
                reservationInfo = reservationDeferred.await() ?: return@withContext
            )
        }
    }

    fun cancelReservation(reservationId: Int) = viewModelScope.launch {
        reservationRepository.getReservationInfo(reservationId = reservationId)
            .getOrElse { error ->
                handleError(error)
                null
            }?.let {
                _errorState.value =
                    ErrorState.Success(resources.getString(R.string.reservation_canceled_success))
                errorJob = async {
                    delay(2000)
                    _errorState.value = ErrorState.Idle
                }
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