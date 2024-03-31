package com.rammdakk.recharge.feature.exercises.di

import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences
import com.rammdakk.recharge.feature.exercises.data.ExerciseRepositoryMockImpl
import com.rammdakk.recharge.feature.exercises.domain.ExerciseRepository
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
        encryptedSharedPreferences: EncryptedSharedPreferences,
        retrofit: Retrofit,
    ): ExerciseRepository {
//        return ExerciseRepositoryImpl(retrofit, encryptedSharedPreferences, dispatchers)
        return ExerciseRepositoryMockImpl()
    }
}