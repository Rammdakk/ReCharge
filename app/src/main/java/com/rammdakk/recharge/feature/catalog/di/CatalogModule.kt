package com.rammdakk.recharge.feature.catalog.di

import com.rammdakk.recharge.build.isMock
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.catalog.data.CatalogRepositoryImp
import com.rammdakk.recharge.feature.catalog.data.CatalogRepositoryMockkImp
import com.rammdakk.recharge.feature.catalog.domain.CatalogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class CatalogModule {

    @Provides
    fun provideCatalogRepository(
        retrofit: Retrofit,
        authRepository: AuthRepository,
        dispatchers: Dispatchers,
    ): CatalogRepository {
        if (isMock()) {
            return CatalogRepositoryMockkImp()
        }
        return CatalogRepositoryImp(retrofit, authRepository, dispatchers)
    }
}
