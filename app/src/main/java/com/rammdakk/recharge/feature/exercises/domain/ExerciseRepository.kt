package com.rammdakk.recharge.feature.exercises.domain

import com.rammdakk.recharge.feature.exercises.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.models.data.SportTypeDataModel

interface ExerciseRepository {

    /**
     * Загрузить список категорий для табов
     */
    suspend fun getTabsCategories(): Result<List<ExerciseTabDataModel>>

    suspend fun getSports(tabId: Int): Result<List<SportTypeDataModel>>
}