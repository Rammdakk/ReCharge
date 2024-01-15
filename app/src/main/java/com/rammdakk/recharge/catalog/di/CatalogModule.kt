package com.rammdakk.recharge.auth.di

import com.rammdakk.recharge.catalog.data.CatalogRepositoryImp
import com.rammdakk.recharge.catalog.domain.CatalogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CatalogModule {

    @Provides
    fun provideCatalogRepository(
        dispatchers: Dispatchers,
    ): CatalogRepository {
        return CatalogRepositoryImp(dispatchers)
    }
}
