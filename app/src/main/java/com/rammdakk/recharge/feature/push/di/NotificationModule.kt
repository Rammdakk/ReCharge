package com.rammdakk.recharge.feature.push.di

import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.push.data.NotificationRepositoryImpl
import com.rammdakk.recharge.feature.push.domain.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {

    @Provides
    fun provideNotificationRepository(
        retrofit: Retrofit,
        authRepository: AuthRepository,
        dispatchers: Dispatchers,
    ): NotificationRepository {
        return NotificationRepositoryImpl(retrofit, authRepository, dispatchers)
    }
}