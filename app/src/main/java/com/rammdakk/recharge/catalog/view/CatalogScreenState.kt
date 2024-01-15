package com.rammdakk.recharge.catalog.view

import androidx.compose.runtime.State
import com.rammdakk.recharge.catalog.view.model.ActivityInfo
import com.rammdakk.recharge.catalog.view.model.Category
import com.rammdakk.recharge.catalog.view.model.ProfileInfo

sealed interface CatalogScreenState {
    data object Idle : CatalogScreenState

    data class Loaded(
        val profileInfo: State<ProfileInfo?>,
        val nextActivity: State<ActivityInfo?>,
        val categories: State<List<Category>>,
        val activitiesList: State<List<ActivityInfo>>
    ) : CatalogScreenState
}

