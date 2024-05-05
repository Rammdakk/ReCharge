package com.rammdakk.recharge.feature.exercises.activityList.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.view.component.error.ErrorState
import com.rammdakk.recharge.feature.exercises.activityList.domain.ActivityListUseCase
import com.rammdakk.recharge.feature.exercises.activityList.view.model.ActivityInfo
import com.rammdakk.recharge.feature.exercises.activityList.view.model.SortingTypes
import com.rammdakk.recharge.feature.exercises.activityList.view.model.convertToActivityInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ActivityListViewModel @Inject constructor(
    private val activityListUseCase: ActivityListUseCase,
    private val dispatchers: Dispatchers,
    private val resources: Resources,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var location = LocationServices.getFusedLocationProviderClient(context)

    private var errorJob: Job? = null
    private var _timeSortedActivities = emptyList<ActivityInfo>()

    private val _screenState: MutableState<ActivityListScreenState> =
        mutableStateOf(ActivityListScreenState.Idle)
    private var _activityName = mutableStateOf("")
    private var _selectedDate = mutableStateOf(Date())
    private var _listOfActivities = mutableStateOf<List<ActivityInfo>>(emptyList())
    private var _errorState = mutableStateOf<ErrorState>(ErrorState.Idle)
    private var _sortingType = mutableStateOf(SortingTypes.TIME)

    val screenState: State<ActivityListScreenState> = _screenState
    val errorState: State<ErrorState> = _errorState

    fun loadData(activityCatId: Int) {
        loadActivitiesData(activityCatId, _selectedDate.value)
        _screenState.value = ActivityListScreenState.Loaded(
            title = _activityName,
            date = _selectedDate,
            activities = _listOfActivities,
            sortingType = _sortingType
        )
    }

    fun loadActivitiesData(activityCatId: Int, date: Date = _selectedDate.value) =
        viewModelScope.launch {
            date.apply {
                hours = 0
                minutes = 0
                seconds = 0
            }
            _selectedDate.value = date

            withContext(dispatchers.IO)
            {
                activityListUseCase.getActivities(activityCatId, _selectedDate.value)
                    .getOrElse { handleError(it) }
                    ?.let {
                        _timeSortedActivities =
                            it.activityList.mapNotNull { activity -> activity.convertToActivityInfo() }
                                .sortedBy { activity -> activity.time }
                        sort(_sortingType.value)
                        withContext(dispatchers.Main) { _activityName.value = it.activityName }
                    }
            }
        }

    fun sort(sortType: SortingTypes) = viewModelScope.launch {
        _sortingType.value = sortType
        val sorted = withContext(dispatchers.IO) {
            when (sortType) {
                SortingTypes.TIME -> _timeSortedActivities
                SortingTypes.PRICE_HIGH -> _timeSortedActivities.sortedByDescending { it.price }
                SortingTypes.PRICE_LOW -> _timeSortedActivities.sortedBy { it.price }
                SortingTypes.LOCATION -> sortGeoLocation()
            }
        }
        withContext(dispatchers.Main) {
            _listOfActivities.value = sorted
        }
    }

    private suspend fun sortGeoLocation(): List<ActivityInfo> {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            handleError(Throwable(resources.getString(R.string.no_geo_access)))
            sort(SortingTypes.TIME)
            return _timeSortedActivities
        }
        runCatching {
            location.lastLocation.await().let { userLocation ->
                return _timeSortedActivities.sortedBy {
                    val loc = Location("").apply {
                        latitude = it.location.latitude.toDouble()
                        longitude = it.location.longitude.toDouble()
                    }
                    userLocation.distanceTo(loc)
                }
            }
        }
        sort(SortingTypes.TIME)
        return _timeSortedActivities
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
        _sortingType.value = SortingTypes.TIME
    }

}
