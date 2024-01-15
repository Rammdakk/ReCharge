package com.rammdakk.recharge.catalog.domain

import com.rammdakk.recharge.catalog.data.model.ActivityDataModel
import com.rammdakk.recharge.catalog.data.model.CategoryDataModel
import com.rammdakk.recharge.catalog.data.model.ProfileDataModel

interface CatalogRepository {
    suspend fun getProfileInfo(): ProfileDataModel

    suspend fun getNextActivityInfo(): ActivityDataModel?

    suspend fun getCategories(): List<CategoryDataModel>

    suspend fun updateCatalog(selectedCategoryId: String? = null): List<ActivityDataModel>
}