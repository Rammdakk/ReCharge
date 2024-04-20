package com.rammdakk.recharge.feature.exercises.categroies.data

import com.rammdakk.recharge.feature.exercises.categroies.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.categroies.models.data.SportTypeDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApi {
    @GET("/api/Category/GetCategoryTabs")
    suspend fun getTabs(
    ): Response<List<ExerciseTabDataModel>>

    @GET("/api/Category/GetCategoriesByTabId")
    suspend fun getActivitiesCats(
        @Query("tabId") activityId: Int,
    ): Response<List<SportTypeDataModel>>

}