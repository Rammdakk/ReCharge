package com.rammdakk.recharge.feature.profile.di

import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences
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
        encryptedSharedPreferences: EncryptedSharedPreferences,
        dispatchers: Dispatchers,
    ): ProfileRepository {
        return ProfileRepositoryImpl(retrofit, encryptedSharedPreferences, dispatchers)
    }
}