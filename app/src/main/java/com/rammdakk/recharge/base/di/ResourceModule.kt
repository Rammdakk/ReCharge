package com.rammdakk.recharge.base.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ResourceModule {

    @Provides
    fun provideResource(
        @ApplicationContext context: Context
    ): Resources {
        return context.resources
    }
}