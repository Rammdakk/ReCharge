package com.rammdakk.recharge.feature.exercises.view

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.feature.exercises.domain.ExerciseRepository
import com.rammdakk.recharge.feature.exercises.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.models.data.SportTypeDataModel
import com.rammdakk.recharge.feature.exercises.models.presentation.ExercisesTab
import com.rammdakk.recharge.feature.exercises.models.presentation.SportTypeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExerciseCatViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private val _screenState: MutableState<ExercisesCatScreenState> =
        mutableStateOf(ExercisesCatScreenState.Idle)
    private val _selectedId = mutableIntStateOf(0)
    private val _tabs: MutableState<List<ExercisesTab>> = mutableStateOf(emptyList())
    private val _sportTypes: MutableState<List<SportTypeItem>> = mutableStateOf(emptyList())

    val screenState: State<ExercisesCatScreenState> = _screenState

    fun loadData() = viewModelScope.launch {
        loadTabs()
        _screenState.value = ExercisesCatScreenState.Loaded(
            selectedId = _selectedId,
            tabs = _tabs,
            items = _sportTypes
        )
    }

    fun onTabSelected(id: Int) = viewModelScope.launch {
        loadActivities(id)
    }

    fun onCategorySelected(id: Int) {

    }

    private suspend fun loadTabs() = withContext(dispatchers.IO) {
        val tabs =
            exerciseRepository.getTabsCategories().getOrNull()?.map { it.covertToScreenModel() }
                ?: return@withContext
        Log.d("Ramil", tabs.toString())
        val sports = tabs.first().let { exercisesTab ->
            exerciseRepository.getSports(exercisesTab.id).getOrNull()
                ?.map { it.covertToScreenModel() }
        } ?: emptyList()
        withContext(dispatchers.Main) {
            _selectedId.intValue = tabs.first().id
            _tabs.value = tabs
            _sportTypes.value = sports
        }
    }

    private suspend fun loadActivities(id: Int) = withContext(dispatchers.IO) {
        withContext(dispatchers.Main) {
            _selectedId.intValue = id
            _sportTypes.value = emptyList()
        }
        exerciseRepository.getSports(id).getOrNull()?.map { it.covertToScreenModel() }?.let {
            withContext(dispatchers.Main) {
                _sportTypes.value = it
            }
        }
    }

    private fun ExerciseTabDataModel.covertToScreenModel(): ExercisesTab =
        ExercisesTab(id = id, exercisesName = name)

    private fun SportTypeDataModel.covertToScreenModel(): SportTypeItem =
        SportTypeItem(id = id, title = name, imageUrl = imageUrl)

}