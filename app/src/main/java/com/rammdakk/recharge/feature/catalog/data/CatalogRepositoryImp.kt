package com.rammdakk.recharge.feature.catalog.data

import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.catalog.data.model.ActivityRecommendationDataModel
import com.rammdakk.recharge.feature.catalog.data.model.CategoryDataModel
import com.rammdakk.recharge.feature.catalog.data.model.NextActivityDataModel
import com.rammdakk.recharge.feature.catalog.data.model.ProfileDataModel
import com.rammdakk.recharge.feature.catalog.data.network.CatalogApi
import com.rammdakk.recharge.feature.catalog.domain.CatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class CatalogRepositoryImp(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers,
) : CatalogRepository {

    private val api = retrofit.create(CatalogApi::class.java)

    override suspend fun getProfileInfo(): Result<ProfileDataModel> = withContext(dispatchers.IO) {
        getAccessToken()?.let { makeRequest { api.getProfileInfo(it) } } ?: Result.failure(
            NetworkError(InternetError.Unauthorized)
        )
    }


    override suspend fun getNextActivityInfo(): Result<NextActivityDataModel> =
        withContext(dispatchers.IO) {
            getAccessToken()?.let { makeRequest { api.getNextActivity(it) } } ?: Result.failure(
                NetworkError(InternetError.Unauthorized)
            )
        }

    override suspend fun getCategories(): Result<List<CategoryDataModel>> =
        withContext(dispatchers.IO) {
            getAccessToken()?.let { makeRequest { api.getCategories(it) } } ?: Result.failure(
                NetworkError(InternetError.Unauthorized)
            )
        }

    override suspend fun updateCatalog(selectedCategoryId: Int?): Result<List<ActivityRecommendationDataModel>> =
        withContext(dispatchers.IO) {
            getAccessToken()?.let {
                makeRequest {
                    api.getActivitiesForCategory(
                        it,
                        selectedCategoryId
                    )
                }
            } ?: Result.failure(
                NetworkError(InternetError.Unauthorized)
            )
        }

    private suspend fun getAccessToken(): String? = withContext(dispatchers.IO) {
        authRepository.getToken()
    }
}