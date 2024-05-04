package com.rammdakk.recharge.base.di

import android.content.Context
import android.content.res.Resources
import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverterImpl
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

    @Provides
    fun provideErrorConverter(
        @ApplicationContext context: Context
    ): ErrorMessageConverter {
        return ErrorMessageConverterImpl(context.resources)
    }
}