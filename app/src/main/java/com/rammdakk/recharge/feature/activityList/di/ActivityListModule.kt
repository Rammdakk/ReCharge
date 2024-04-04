package com.rammdakk.recharge.feature.activityList.di

import com.rammdakk.recharge.feature.activityList.data.ActivityListRepositoryImpl
import com.rammdakk.recharge.feature.activityList.domain.ActivityListRepository
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
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
    ): ActivityListRepository {
        return ActivityListRepositoryImpl(retrofit, authRepository, dispatchers)
    }
}
