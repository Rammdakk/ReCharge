package com.rammdakk.recharge.feature.reservation.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import com.rammdakk.recharge.feature.activity.view.model.convertToActivityInfo
import com.rammdakk.recharge.feature.reservation.domain.ReservationRepository
import com.rammdakk.recharge.feature.reservation.view.model.convertToReservationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val reservationRepository: ReservationRepository,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private val _screenState: MutableState<ReservationScreenState> =
        mutableStateOf(ReservationScreenState.Idle)

    val screenState: State<ReservationScreenState> = _screenState

    fun loadData(reservationId: Int) = viewModelScope.launch(dispatchers.IO) {
        val reservationInfo =
            reservationRepository.getReservationInfo(reservationId = reservationId).getOrNull()
                ?: return@launch

        val activityInfo =
            reservationInfo.activityId?.let { activityRepository.getActivityInfo(it).getOrNull() }
                ?.convertToActivityInfo()
                ?: return@launch

        withContext(dispatchers.Main) {
            _screenState.value = ReservationScreenState.Loaded(
                activityInfo = activityInfo,
                reservationInfo = reservationInfo.convertToReservationInfo()
            )
        }
    }

}