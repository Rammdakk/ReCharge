package com.rammdakk.recharge.feature.catalog.domain

import com.rammdakk.recharge.feature.catalog.data.model.ActivityRecommendationDataModel
import com.rammdakk.recharge.feature.catalog.data.model.CategoryDataModel

class RecommendationUseCaseImpl(
    private val catalogRepository: CatalogRepository
) : RecommendationUseCase {
    override suspend fun getCategories(): Result<List<CategoryDataModel>> {
        return catalogRepository.getCategories()
    }

    override suspend fun getRecommendations(selectedCategoryId: Int?): Result<List<ActivityRecommendationDataModel>> {
        return catalogRepository.updateCatalog(selectedCategoryId)
    }
}