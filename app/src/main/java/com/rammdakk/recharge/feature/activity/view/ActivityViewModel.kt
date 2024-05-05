package com.rammdakk.recharge.feature.activity.view

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.view.component.error.ErrorState
import com.rammdakk.recharge.feature.activity.domain.ActivityUseCase
import com.rammdakk.recharge.feature.activity.domain.ReservationInfoUseCase
import com.rammdakk.recharge.feature.activity.view.model.CurrentUserInfo
import com.rammdakk.recharge.feature.activity.view.model.TimePad
import com.rammdakk.recharge.feature.activity.view.model.UserBookingInfo
import com.rammdakk.recharge.feature.activity.view.model.convertToActivityInfo
import com.rammdakk.recharge.feature.activity.view.model.covertToDataModel
import com.rammdakk.recharge.feature.activity.view.model.covertToTimePad
import com.rammdakk.recharge.feature.profile.domain.ProfileShortInfoUseCase
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
class ActivityViewModel @Inject constructor(
    private val activityUseCase: ActivityUseCase,
    private val reservationInfoUseCase: ReservationInfoUseCase,
    private val profileUseCase: ProfileShortInfoUseCase,
    private val dispatchers: Dispatchers,
    private val resources: Resources
) : ViewModel() {

    private var errorJob: Job? = null
    private var currentUser: CurrentUserInfo? = null
    private val _screenState: MutableState<ActivityScreenState> =
        mutableStateOf(ActivityScreenState.Idle)
    private val _schedule: MutableState<List<TimePad>> = mutableStateOf(emptyList())
    private var _userNumber: MutableState<Int?> = mutableStateOf(null)
    private var _errorState = mutableStateOf<ErrorState>(ErrorState.Idle)

    private var job: Job? = null

    val screenState: State<ActivityScreenState> = _screenState
    val errorState: State<ErrorState> = _errorState

    fun loadData(activityId: Int, date: Date = Date()) = viewModelScope.launch(dispatchers.IO) {
        loadScheduleForDate(activityId, date)

        val deferred = if (currentUser == null) {
            async {
                profileUseCase.getProfileShortInfo().getOrNull()?.let {
                    CurrentUserInfo(
                        userName = "${it.firstName.orEmpty()} ${it.secondName.orEmpty()}",
                        phone = it.phone.orEmpty(),
                        email = it.email.orEmpty()
                    )
                }
            }
        } else {
            null
        }
        val activityInfo =
            activityUseCase.getActivityInfo(activityId).getOrElse { handleError(it) }
                ?.convertToActivityInfo()
                ?: return@launch
        currentUser = currentUser ?: deferred?.await()
        withContext(dispatchers.Main) {
            _screenState.value = ActivityScreenState.Loaded(
                activityInfo = activityInfo,
                scheduleInfo = _schedule,
                usersMaxNumber = _userNumber,
                currentUserInfo = currentUser
            )
        }
    }

    fun loadScheduleForDate(activityId: Int, date: Date) = viewModelScope.launch {
        job?.cancel()
        job = async(dispatchers.IO) {
            date.apply {
                hours = 0
                minutes = 0
                seconds = 0
            }
            val timePad = activityUseCase.getActivityTimeTable(activityId, date)
                .getOrElse { handleError(it) }
                ?.map { timePad ->
                    timePad.covertToTimePad {
                        getMaxUsersNumber(timePad.id)
                    }
                }
                ?: return@async
            withContext(dispatchers.Main) {
                _schedule.value = timePad
            }
        }
    }

    private fun getMaxUsersNumber(timeId: Int) = viewModelScope.launch {
        _userNumber.value = null
        reservationInfoUseCase.getUsersMaxNumber(tabId = timeId).getOrElse { handleError(it) }
            ?.let {
            _userNumber.value = it
        }
    }

    fun reserve(timeId: Int, userBookingInfo: UserBookingInfo, onSuccessReserve: () -> Unit) =
        viewModelScope.launch {
            reservationInfoUseCase.reserveActivity(timeId, userBookingInfo.covertToDataModel())
                .getOrElse {
                    handleError(it)
                    null
                }?.let {
                    _errorState.value =
                        ErrorState.Success(resources.getString(R.string.reservation_success))
                    errorJob = async {
                        delay(2000)
                        _errorState.value = ErrorState.Idle
                    }
                    onSuccessReserve.invoke()
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