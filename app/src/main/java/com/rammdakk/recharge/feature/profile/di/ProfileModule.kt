package com.rammdakk.recharge.feature.profile.di

import com.rammdakk.recharge.feature.profile.data.ProfileRepositoryImpl
import com.rammdakk.recharge.feature.profile.domain.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Provides
    fun provideProfileRepository(
        dispatchers: Dispatchers,
    ): ProfileRepository {
        return ProfileRepositoryImpl(dispatchers)
    }
}