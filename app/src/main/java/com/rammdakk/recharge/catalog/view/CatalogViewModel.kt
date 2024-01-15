package com.rammdakk.recharge.catalog.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.rammdakk.recharge.catalog.domain.CatalogRepository
import com.rammdakk.recharge.catalog.view.model.ActivityInfo
import com.rammdakk.recharge.catalog.view.model.Category
import com.rammdakk.recharge.catalog.view.model.ProfileInfo
import com.rammdakk.recharge.catalog.view.model.convertToActivityInfo
import com.rammdakk.recharge.catalog.view.model.convertToProfileInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private val _screenState: MutableState<CatalogScreenState> =
        mutableStateOf(CatalogScreenState.Idle)

    private val _profileInfo: MutableState<ProfileInfo?> = mutableStateOf(null)
    private val _nextActivity: MutableState<ActivityInfo?> = mutableStateOf(null)
    private val _categories: MutableState<List<Category>> = mutableStateOf(emptyList())
    private val _activitiesList: MutableState<List<ActivityInfo>> = mutableStateOf(emptyList())

    val screenState: State<CatalogScreenState> = _screenState

    fun loadData() = viewModelScope.launch {
        merge(
            { loadProfile() },
            { loadNextActivity() },
            { loadCategories() },
            { loadActivities() }
        )
        withContext(dispatchers.Main)
        {
            _screenState.value = CatalogScreenState.Loaded(
                profileInfo = _profileInfo,
                nextActivity = _nextActivity,
                categories = _categories,
                activitiesList = _activitiesList
            )
        }
    }

    private suspend fun merge(vararg func: suspend () -> Unit) = withContext(dispatchers.IO) {
        func.map { async { it.invoke() } }.awaitAll()
    }

    private suspend fun loadProfile() = withContext(dispatchers.IO) {
        val result = catalogRepository.getProfileInfo().convertToProfileInfo()

        withContext(dispatchers.Main) {
            _profileInfo.value = result
        }
    }

    private suspend fun loadNextActivity() = withContext(dispatchers.IO) {
        val result = catalogRepository.getNextActivityInfo()?.convertToActivityInfo()

        withContext(dispatchers.Main) {
            _nextActivity.value = result
        }
    }

    private suspend fun loadCategories() = withContext(dispatchers.IO) {
        val result = catalogRepository.getCategories().map { cat ->
            Category(imagePath = cat.imagePath) {
                viewModelScope.launch {
                    loadActivities(cat.id)
                }
            }
        }

        withContext(dispatchers.Main) {
            _categories.value = result
        }
    }

    private suspend fun loadActivities(catId: String? = null) = withContext(dispatchers.IO) {
        catalogRepository.updateCatalog(catId).map {
            it.convertToActivityInfo()
        }.let { activityList ->
            withContext(dispatchers.Main) {
                _activitiesList.value = activityList
            }
        }
    }
}