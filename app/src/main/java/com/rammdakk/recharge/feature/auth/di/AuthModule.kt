package com.rammdakk.recharge.feature.auth.di

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.sp.CustomSharedPreferences
import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences
import com.rammdakk.recharge.feature.auth.data.AuthRepositoryImpl
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        customSharedPreferences: CustomSharedPreferences,
        dispatchers: Dispatchers,
        errorMessageConverter: ErrorMessageConverter
    ): AuthRepository {
        return AuthRepositoryImpl(
            retrofit,
            encryptedSharedPreferences,
            customSharedPreferences,
            dispatchers,
            errorMessageConverter
        )
    }
}
