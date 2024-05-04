package com.rammdakk.recharge.feature.exercises.activityList.di

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.exercises.activityList.data.ActivityListRepositoryImpl
import com.rammdakk.recharge.feature.exercises.activityList.domain.ActivityListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ActivityListModule {

    @Provides
    fun provideAuthRepository(
        retrofit: Retrofit,
        authRepository: AuthRepository,
        dispatchers: Dispatchers,
        errorMessageConverter: ErrorMessageConverter
    ): ActivityListRepository {
        return ActivityListRepositoryImpl(
            retrofit,
            authRepository,
            dispatchers,
            errorMessageConverter
        )
    }
}
