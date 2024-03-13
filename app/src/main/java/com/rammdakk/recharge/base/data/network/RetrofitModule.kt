package com.rammdakk.recharge.base.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RechargeRetrofitModule {
    @Provides
    fun retrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            JacksonConverterFactory.create()
        ).client(HttpClient.client)
            .build()
    }

    private const val BASE_URL = "https://recharge.free.beeceptor.com"
}
