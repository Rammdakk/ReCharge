package com.rammdakk.recharge.feature.activity.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.base.view.component.error.ErrorState
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import com.rammdakk.recharge.feature.activity.view.model.TimePad
import com.rammdakk.recharge.feature.activity.view.model.UserBookingInfo
import com.rammdakk.recharge.feature.activity.view.model.convertToActivityInfo
import com.rammdakk.recharge.feature.activity.view.model.covertToDataModel
import com.rammdakk.recharge.feature.activity.view.model.covertToTimePad
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
    private val activityRepository: ActivityRepository,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private var errorJob: Job? = null

    private val _screenState: MutableState<ActivityScreenState> =
        mutableStateOf(ActivityScreenState.Idle)
    private val _schedule: MutableState<List<TimePad>> = mutableStateOf(emptyList())
    private var _userNumber: MutableState<Int?> = mutableStateOf(null)
    private var _errorState = mutableStateOf<ErrorState>(ErrorState.Idle)

    private var job: Job? = null

    val screenState: State<ActivityScreenState> = _screenState
    val errorState: State<ErrorState> = _errorState

    fun loadData(activityId: Int) = viewModelScope.launch(dispatchers.IO) {
        loadScheduleForDate(activityId, Date())
        val activityInfo =
            activityRepository.getActivityInfo(activityId).getOrElse { handleError(it) }
                ?.convertToActivityInfo()
                ?: return@launch
        withContext(dispatchers.Main) {
            _screenState.value = ActivityScreenState.Loaded(
                activityInfo = activityInfo,
                scheduleInfo = _schedule,
                usersMaxNumber = _userNumber,
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
            val timePad = activityRepository.getActivityTimeTable(activityId, date)
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
        activityRepository.getUsersMaxNumber(tabId = timeId).getOrElse { handleError(it) }?.let {
            _userNumber.value = it
        }
    }

    fun reserve(timeId: Int, userBookingInfo: UserBookingInfo) = viewModelScope.launch {
        activityRepository.reserveActivity(timeId, userBookingInfo.covertToDataModel())
            .getOrElse { handleError(it) }
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