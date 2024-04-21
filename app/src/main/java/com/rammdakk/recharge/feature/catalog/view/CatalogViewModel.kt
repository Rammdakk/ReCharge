package com.rammdakk.recharge.feature.catalog.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.recharge.base.extensions.merge
import com.rammdakk.recharge.base.view.component.error.ErrorState
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private var errorJob: Job? = null

    private val _screenState: MutableState<CatalogScreenState> =
        mutableStateOf(CatalogScreenState.Idle)
    private val _profileInfo: MutableState<ProfileInfo?> = mutableStateOf(null)
    private val _nextActivity: MutableState<NextActivityModel?> = mutableStateOf(null)
    private val _categoriesList: MutableState<CategoriesList> =
        mutableStateOf(CategoriesList(emptyList()) {})
    private var _nonFilteredActivitiesList: List<ActivityRecommendationModel> = emptyList()
    private val _activitiesList: MutableState<List<ActivityRecommendationModel>> =
        mutableStateOf(emptyList())
    private var _errorState = mutableStateOf<ErrorState>(ErrorState.Idle)

    val screenState: State<CatalogScreenState> = _screenState
    val errorState: State<ErrorState> = _errorState

    fun loadData() = viewModelScope.launch {
        merge(
            dispatchers.IO,
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
                activitiesList = _activitiesList,
            )
        }
    }

    fun updateScreen() = viewModelScope.launch {
        merge(
            dispatchers.IO,
            { loadProfile() },
            { loadNextActivity() },
            { loadCategories() },
        )
    }

    fun filterActivities(request: String) = viewModelScope.launch(dispatchers.IO) {
        _nonFilteredActivitiesList.filter {
            it.name.lowercase().startsWith(request.lowercase()) || it.organizationName.lowercase()
                .startsWith(request.lowercase())
        }.let {
            _activitiesList.value = it
        }
    }

    private suspend fun loadProfile() = withContext(dispatchers.IO) {
        val result =
            catalogRepository.getProfileInfo().getOrNull()?.convertToProfileInfo()
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
        val result = catalogRepository.getCategories().getOrElse { handleError(it) }?.map { cat ->
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
        catalogRepository.updateCatalog(catId).getOrElse { handleError(it) }?.map {
            it.convertToActivityInfo()
        }.let { activityList ->
            _nonFilteredActivitiesList = activityList.orEmpty()
            withContext(dispatchers.Main) {
                _activitiesList.value = _nonFilteredActivitiesList
            }
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