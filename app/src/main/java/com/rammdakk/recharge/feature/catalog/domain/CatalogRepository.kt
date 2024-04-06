package com.rammdakk.recharge.feature.catalog.domain

import com.rammdakk.recharge.feature.catalog.data.model.ActivityRecommendationDataModel
import com.rammdakk.recharge.feature.catalog.data.model.CategoryDataModel
import com.rammdakk.recharge.feature.catalog.data.model.NextActivityDataModel
import com.rammdakk.recharge.feature.catalog.data.model.ProfileDataModel

interface CatalogRepository {
    suspend fun getProfileInfo(): Result<ProfileDataModel>

    suspend fun getNextActivityInfo(): Result<NextActivityDataModel>

    suspend fun getCategories(): Result<List<CategoryDataModel>>

    suspend fun updateCatalog(selectedCategoryId: Int? = null): Result<List<ActivityRecommendationDataModel>>
}