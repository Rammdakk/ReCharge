package com.rammdakk.recharge.feature.exercises.data

import com.rammdakk.recharge.feature.exercises.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.models.data.SportTypeDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ExerciseApi {
    @GET("/api/Activities/Tabs")
    suspend fun getTabs(
        @Header("accessToken") accessToken: String,
    ): Response<List<ExerciseTabDataModel>>

    @GET("/api/Activities/Activities")
    suspend fun getActivities(
        @Header("accessToken") accessToken: String,
        @Query("activityId") activityId: Int,
    ): Response<List<SportTypeDataModel>>

}