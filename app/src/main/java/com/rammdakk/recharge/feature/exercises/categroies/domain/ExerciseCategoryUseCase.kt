package com.rammdakk.recharge.feature.exercises.categroies.domain

import com.rammdakk.recharge.feature.exercises.categroies.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.categroies.models.data.SportTypeDataModel

interface ExerciseCategoryUseCase {

    /**
     * Загрузить список категорий для табов
     */
    suspend fun getTabsCategories(): Result<List<ExerciseTabDataModel>>

    suspend fun getSports(tabId: Int): Result<List<SportTypeDataModel>>
}