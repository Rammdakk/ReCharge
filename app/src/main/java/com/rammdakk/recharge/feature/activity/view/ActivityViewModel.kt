package com.rammdakk.recharge.feature.activity.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import com.rammdakk.recharge.feature.activity.view.model.TimePad
import com.rammdakk.recharge.feature.activity.view.model.convertToActivityInfo
import com.rammdakk.recharge.feature.activity.view.model.covertToTimePad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private val _screenState: MutableState<ActivityScreenState> =
        mutableStateOf(ActivityScreenState.Idle)
    private val _schedule: MutableState<List<TimePad>> = mutableStateOf(emptyList())

    private var job: Job? = null

    val screenState: State<ActivityScreenState> = _screenState

    fun loadData(activityId: Int) = viewModelScope.launch(dispatchers.IO) {
        _screenState.value = ActivityScreenState.Idle
        loadScheduleForDate(activityId, Date())
        val activityInfo =
            activityRepository.getActivityInfo(activityId).getOrNull()?.convertToActivityInfo()
                ?: return@launch
        withContext(dispatchers.Main) {
            _screenState.value = ActivityScreenState.Loaded(
                activityInfo = activityInfo,
                scheduleInfo = _schedule
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
            val timePad = activityRepository.getActivityTimeTable(activityId, date.time).getOrNull()
                ?.map { timePad ->
                    timePad.covertToTimePad {
                        reserveActivity(
                            activityId,
                            timePad.id
                        )
                    }
                }
                ?: return@async
            withContext(dispatchers.Main) {
                _schedule.value = timePad
            }
        }
    }

    private fun reserveActivity(activityId: Int, timeId: Int) = viewModelScope.launch {

    }

}