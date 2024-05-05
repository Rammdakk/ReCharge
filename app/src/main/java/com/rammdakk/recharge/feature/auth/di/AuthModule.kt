package com.rammdakk.recharge.feature.auth.di

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.sp.CustomSharedPreferences
import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences
import com.rammdakk.recharge.feature.auth.data.AuthRepositoryImpl
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.auth.domain.AuthValidationUseCase
import com.rammdakk.recharge.feature.auth.domain.AuthValidationUseCaseImpl
import com.rammdakk.recharge.feature.auth.domain.LogOutUseCase
import com.rammdakk.recharge.feature.auth.domain.LogOutUseCaseImpl
import com.rammdakk.recharge.feature.auth.domain.LoginUseCase
import com.rammdakk.recharge.feature.auth.domain.LoginUseCaseImpl
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

    @Provides
    fun provideAuthValidationUseCase(
        authRepository: AuthRepository
    ): AuthValidationUseCase = AuthValidationUseCaseImpl(authRepository)

    @Provides
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUseCase = LoginUseCaseImpl(authRepository)

    @Provides
    fun provideLogOutUseCase(
        authRepository: AuthRepository
    ): LogOutUseCase = LogOutUseCaseImpl(authRepository)
}
