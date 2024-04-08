package com.rammdakk.recharge.feature.catalog.view

import androidx.compose.runtime.State
import com.rammdakk.recharge.feature.catalog.view.model.ActivityRecommendationModel
import com.rammdakk.recharge.feature.catalog.view.model.CategoriesList
import com.rammdakk.recharge.feature.catalog.view.model.NextActivityModel
import com.rammdakk.recharge.feature.catalog.view.model.ProfileInfo

sealed interface CatalogScreenState {
    data object Idle : CatalogScreenState

    data class Loaded(
        val profileInfo: State<ProfileInfo?>,
        val nextActivity: State<NextActivityModel?>,
        val categoriesList: State<CategoriesList>,
        val activitiesList: State<List<ActivityRecommendationModel>>
    ) : CatalogScreenState
}

