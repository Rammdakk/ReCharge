package com.rammdakk.recharge.feature.activity.di

import com.rammdakk.recharge.build.isMock
import com.rammdakk.recharge.feature.activity.data.ActivityMockRepositoryImp
import com.rammdakk.recharge.feature.activity.data.ActivityRepositoryImp
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ActivityModule {

    @Provides
    fun provideAuthRepository(
        dispatchers: Dispatchers,
        authRepository: AuthRepository,
        retrofit: Retrofit,
    ): ActivityRepository {
        if (isMock())
            return ActivityMockRepositoryImp()
        return ActivityRepositoryImp(retrofit, authRepository, dispatchers)
    }
}
