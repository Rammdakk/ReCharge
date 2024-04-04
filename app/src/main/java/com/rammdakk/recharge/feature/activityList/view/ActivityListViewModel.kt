package com.rammdakk.recharge.feature.activityList.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.feature.activityList.domain.ActivityListRepository
import com.rammdakk.recharge.feature.activityList.view.model.convertToActivityInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ActivityListViewModel @Inject constructor(
    private val activityListRepository: ActivityListRepository,
    private val dispatchers: Dispatchers
) : ViewModel() {

    private val _screenState: MutableState<ActivityListScreenState> =
        mutableStateOf(ActivityListScreenState.Idle)
    val screenState: State<ActivityListScreenState> = _screenState

    fun loadData(activityCatId: Int, date: Date) = viewModelScope.launch {
        date.apply {
            hours = 0
            minutes = 0
            seconds = 0
        }

        activityListRepository.getActivities(activityCatId, date.time).getOrNull()?.let {
            _screenState.value = ActivityListScreenState.Loaded(
                title = it.activityName,
                activities = it.activityList.map { it.convertToActivityInfo() }
            )
        }
    }

}
