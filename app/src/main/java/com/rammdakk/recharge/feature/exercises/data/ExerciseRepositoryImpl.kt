package com.rammdakk.recharge.feature.exercises.data

import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.feature.exercises.domain.ExerciseRepository
import com.rammdakk.recharge.feature.exercises.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.models.data.SportTypeDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ExerciseRepositoryImpl(
    retrofit: Retrofit,
    private val dispatchers: Dispatchers
) : ExerciseRepository {


    private val api = retrofit.create(ExerciseApi::class.java)

    override suspend fun getTabsCategories(): Result<List<ExerciseTabDataModel>> =
        withContext(dispatchers.IO) {
            makeRequest { api.getTabs() }
        }

    override suspend fun getSports(tabId: Int): Result<List<SportTypeDataModel>> =
        withContext(dispatchers.IO) {
            makeRequest { api.getActivitiesCats(tabId) }
        }
}