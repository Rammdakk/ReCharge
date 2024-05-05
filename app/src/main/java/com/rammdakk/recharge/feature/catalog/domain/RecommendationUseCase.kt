package com.rammdakk.recharge.feature.catalog.domain

import com.rammdakk.recharge.feature.catalog.data.model.ActivityRecommendationDataModel
import com.rammdakk.recharge.feature.catalog.data.model.CategoryDataModel

interface RecommendationUseCase {
    suspend fun getCategories(): Result<List<CategoryDataModel>>

    suspend fun getRecommendations(selectedCategoryId: Int? = null): Result<List<ActivityRecommendationDataModel>>
}