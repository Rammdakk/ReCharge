package com.rammdakk.recharge.feature.calendar.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.base.view.component.error.ErrorState
import com.rammdakk.recharge.feature.calendar.domain.CalendarRepository
import com.rammdakk.recharge.feature.calendar.view.components.calendar.CalendarState
import com.rammdakk.recharge.feature.calendar.view.model.ReservationModel
import com.rammdakk.recharge.feature.calendar.view.model.convertToReservationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val dispatchers: Dispatchers,
) : ViewModel() {
    private var errorJob: Job? = null
    private var updateJob: Job? = null

    private val _screenState: MutableState<CalendarScreenState> =
        mutableStateOf(CalendarScreenState.Idle)
    private val _calendarState: MutableState<YearMonth> = mutableStateOf(YearMonth.now())
    private val _reservationList: MutableState<List<ReservationModel>> = mutableStateOf(emptyList())
    private var _errorState = mutableStateOf<ErrorState>(ErrorState.Idle)

    val screenState: State<CalendarScreenState> = _screenState

    val errorState: State<ErrorState> = _errorState

    fun loadData() = viewModelScope.launch {
        updateReservations(_calendarState.value)
        _screenState.value = CalendarScreenState.Loaded(
            CalendarState(_calendarState, ::onMonthChanged, {}),
            _reservationList
        )
    }

    private fun onMonthChanged(newMonth: YearMonth) = viewModelScope.launch {
        updateJob?.cancel()
        if (newMonth != _calendarState.value) {
            _reservationList.value = emptyList()
            _calendarState.value = newMonth
        }
        updateJob = async(dispatchers.Main) {
            updateReservations(newMonth)
        }
    }

    private suspend fun updateReservations(newMonth: YearMonth) {
        val date = withContext(dispatchers.IO) {
            calendarRepository.loadReservations(
                newMonth.atDay(1).toDate(),
                newMonth.plusMonths(1).atDay(1).toDate(),
            ).getOrElse { handleError(it) }?.map { it.convertToReservationModel() }
        }.orEmpty()
        _reservationList.value = date
    }

    private fun LocalDate.toDate() = Date.from(
        this.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
    )

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