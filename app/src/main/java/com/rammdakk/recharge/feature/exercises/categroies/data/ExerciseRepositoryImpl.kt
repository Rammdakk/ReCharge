package com.rammdakk.recharge.feature.exercises.categroies.data

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.feature.exercises.categroies.domain.ExerciseRepository
import com.rammdakk.recharge.feature.exercises.categroies.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.categroies.models.data.SportTypeDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ExerciseRepositoryImpl(
    retrofit: Retrofit,
    private val dispatchers: Dispatchers,
    private val errorMessageConverter: ErrorMessageConverter
) : ExerciseRepository {


    private val api = retrofit.create(ExerciseApi::class.java)

    override suspend fun getTabsCategories(): Result<List<ExerciseTabDataModel>> =
        withContext(dispatchers.IO) {
            makeRequest(errorMessageConverter) { api.getTabs() }
        }

    override suspend fun getSports(tabId: Int): Result<List<SportTypeDataModel>> =
        withContext(dispatchers.IO) {
            makeRequest(errorMessageConverter) { api.getActivitiesCats(tabId) }
        }
}