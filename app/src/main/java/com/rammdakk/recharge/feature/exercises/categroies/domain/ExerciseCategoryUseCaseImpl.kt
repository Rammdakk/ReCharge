package com.rammdakk.recharge.feature.exercises.categroies.domain

import com.rammdakk.recharge.feature.exercises.categroies.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.categroies.models.data.SportTypeDataModel

class ExerciseCategoryUseCaseImpl(
    private val exerciseRepository: ExerciseRepository
) : ExerciseCategoryUseCase {

    /**
     * Загрузить список категорий для табов
     */
    override suspend fun getTabsCategories(): Result<List<ExerciseTabDataModel>> {
        return exerciseRepository.getTabsCategories()
    }

    override suspend fun getSports(tabId: Int): Result<List<SportTypeDataModel>> =
        exerciseRepository.getSports(tabId)
}