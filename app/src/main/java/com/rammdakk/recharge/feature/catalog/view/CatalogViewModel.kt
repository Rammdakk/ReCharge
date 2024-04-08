package com.rammdakk.recharge.feature.catalog.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.rammdakk.recharge.feature.catalog.domain.CatalogRepository
import com.rammdakk.recharge.feature.catalog.view.model.ActivityRecommendationModel
import com.rammdakk.recharge.feature.catalog.view.model.CategoriesList
import com.rammdakk.recharge.feature.catalog.view.model.Category
import com.rammdakk.recharge.feature.catalog.view.model.NextActivityModel
import com.rammdakk.recharge.feature.catalog.view.model.ProfileInfo
import com.rammdakk.recharge.feature.catalog.view.model.convertToActivityInfo
import com.rammdakk.recharge.feature.catalog.view.model.convertToProfileInfo
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
    private val _nextActivity: MutableState<NextActivityModel?> = mutableStateOf(null)
    private val _categoriesList: MutableState<CategoriesList> =
        mutableStateOf(CategoriesList(emptyList()) {})
    private val _activitiesList: MutableState<List<ActivityRecommendationModel>> =
        mutableStateOf(emptyList())

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
                categoriesList = _categoriesList,
                activitiesList = _activitiesList
            )
        }
    }

    fun updateScreen() = viewModelScope.launch {
        merge(
            { loadProfile() },
            { loadNextActivity() },
            { loadCategories() },
        )
    }

    private suspend fun merge(vararg func: suspend () -> Unit) = withContext(dispatchers.IO) {
        func.map { async { it.invoke() } }.awaitAll()
    }

    private suspend fun loadProfile() = withContext(dispatchers.IO) {
        val result = catalogRepository.getProfileInfo().getOrNull()?.convertToProfileInfo()

        withContext(dispatchers.Main) {
            _profileInfo.value = result
        }
    }

    private suspend fun loadNextActivity() = withContext(dispatchers.IO) {
        val result = catalogRepository.getNextActivityInfo().getOrNull()?.convertToActivityInfo()

        withContext(dispatchers.Main) {
            _nextActivity.value = result
        }
    }

    private suspend fun loadCategories() = withContext(dispatchers.IO) {
        val result = catalogRepository.getCategories().getOrNull()?.map { cat ->
            Category(
                id = cat.id,
                imagePath = cat.imagePath
            )
        }

        withContext(dispatchers.Main) {
            _categoriesList.value = CategoriesList(result.orEmpty(), ::loadActivities)
        }
    }

    private fun loadActivities(catId: Int? = null) = viewModelScope.launch(dispatchers.IO) {
        catalogRepository.updateCatalog(catId).getOrNull()?.map {
            it.convertToActivityInfo()
        }.let { activityList ->
            withContext(dispatchers.Main) {
                _activitiesList.value = activityList.orEmpty()
            }
        }
    }
}