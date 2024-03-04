package com.rammdakk.recharge.feature.auth.di

import android.content.Context
import android.content.res.Resources
import com.rammdakk.recharge.feature.auth.data.AuthRepositoryImpl
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    fun provideResource(@ApplicationContext context: Context): Resources {
        return context.resources
    }
}
