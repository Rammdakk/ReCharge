package com.rammdakk.recharge.feature.activity.di

import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class ActivityModule {

    @Provides
    fun provideAuthRepository(
        dispatchers: Dispatchers,
    ): ActivityRepository {
        return com.rammdakk.recharge.feature.activity.data.ActivityRepositoryImp(dispatchers)
    }
}
