package com.rammdakk.recharge.auth.di

import com.rammdakk.recharge.auth.data.AuthRepositoryImpl
import com.rammdakk.recharge.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }
}
