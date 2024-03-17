package com.rammdakk.recharge.feature.auth.di

import android.content.Context
import android.content.res.Resources
import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences
import com.rammdakk.recharge.feature.auth.data.AuthRepositoryImpl
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideAuthRepository(
        retrofit: Retrofit,
        encryptedSharedPreferences: EncryptedSharedPreferences,
        dispatchers: Dispatchers
    ): AuthRepository {
        return AuthRepositoryImpl(retrofit, encryptedSharedPreferences, dispatchers)
    }

    @Provides
    fun provideResource(@ApplicationContext context: Context): Resources {
        return context.resources
    }
}
