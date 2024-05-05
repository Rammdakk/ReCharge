package com.rammdakk.recharge.feature.exercises.categroies.di

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.build.isMock
import com.rammdakk.recharge.feature.exercises.categroies.data.ExerciseRepositoryImpl
import com.rammdakk.recharge.feature.exercises.categroies.data.ExerciseRepositoryMockImpl
import com.rammdakk.recharge.feature.exercises.categroies.domain.ExerciseCategoryUseCase
import com.rammdakk.recharge.feature.exercises.categroies.domain.ExerciseCategoryUseCaseImpl
import com.rammdakk.recharge.feature.exercises.categroies.domain.ExerciseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ExerciseModule {

    @Provides
    fun provideExerciseRepository(
        dispatchers: Dispatchers,
        retrofit: Retrofit,
        errorMessageConverter: ErrorMessageConverter
    ): ExerciseRepository {
        if (isMock()) {
            return ExerciseRepositoryMockImpl()
        }
        return ExerciseRepositoryImpl(retrofit, dispatchers, errorMessageConverter)
    }

    @Provides
    fun provideExerciseCatsUseCase(
        exerciseRepository: ExerciseRepository
    ): ExerciseCategoryUseCase = ExerciseCategoryUseCaseImpl(exerciseRepository)
}