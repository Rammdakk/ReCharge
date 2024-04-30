package com.rammdakk.recharge.base.data.sp

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RechargeDataModule {
    @Provides
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): EncryptedSharedPreferences =
        EncryptedSharedPreferences(context)

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): CustomSharedPreferences =
        CustomSharedPreferences(context)
}
