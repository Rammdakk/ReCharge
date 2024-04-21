package com.rammdakk.recharge.feature.exercises.activityList.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.base.view.component.error.ErrorState
import com.rammdakk.recharge.feature.exercises.activityList.domain.ActivityListRepository
import com.rammdakk.recharge.feature.exercises.activityList.view.model.convertToActivityInfo
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
class ActivityListViewModel @Inject constructor(
    private val activityListRepository: ActivityListRepository,
    private val dispatchers: Dispatchers
) : ViewModel() {

    private var errorJob: Job? = null

    private val _screenState: MutableState<ActivityListScreenState> =
        mutableStateOf(ActivityListScreenState.Idle)
    private var _selectedDate = mutableStateOf(Date())
    private var _errorState = mutableStateOf<ErrorState>(ErrorState.Idle)

    val screenState: State<ActivityListScreenState> = _screenState
    val errorState: State<ErrorState> = _errorState


    fun loadActivitiesData(activityCatId: Int, date: Date = _selectedDate.value) =
        viewModelScope.launch {
            date.apply {
                hours = 0
                minutes = 0
                seconds = 0
            }
            _selectedDate.value = date

            activityListRepository.getActivities(activityCatId, _selectedDate.value)
                .getOrElse { handleError(it) }
                ?.let {
                    _screenState.value = ActivityListScreenState.Loaded(
                        title = it.activityName,
                        date = _selectedDate,
                        activities = it.activityList.map { it.convertToActivityInfo() }
                            .filterNotNull(),
                    )
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

    fun resetDate() {
        _screenState.value = ActivityListScreenState.Idle
        _selectedDate.value = Date()
    }

}
