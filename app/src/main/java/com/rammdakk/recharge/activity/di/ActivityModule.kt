package com.rammdakk.recharge.activity.di

import com.rammdakk.recharge.activity.data.ActivityRepositoryImp
import com.rammdakk.recharge.activity.domain.ActivityRepository
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
        return ActivityRepositoryImp(dispatchers)
    }
}
