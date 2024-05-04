package com.rammdakk.recharge.feature.profile.di

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.sp.CustomSharedPreferences
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.profile.data.ProfileRepositoryImpl
import com.rammdakk.recharge.feature.profile.domain.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Provides
    fun provideProfileRepository(
        retrofit: Retrofit,
        authRepository: AuthRepository,
        dispatchers: Dispatchers,
        sharedPreferences: CustomSharedPreferences,
        errorMessageConverter: ErrorMessageConverter
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            retrofit,
            authRepository,
            dispatchers,
            sharedPreferences,
            errorMessageConverter
        )
    }
}