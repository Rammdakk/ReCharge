package com.rammdakk.recharge.feature.calendar.di

import com.rammdakk.recharge.build.isMock
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.calendar.data.CalendarMockRepositoryImpl
import com.rammdakk.recharge.feature.calendar.data.CalendarRepositoryImpl
import com.rammdakk.recharge.feature.calendar.domain.CalendarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class CalendarModule {

    @Provides
    fun provideCalendarRepository(
        retrofit: Retrofit,
        authRepository: AuthRepository,
        dispatchers: Dispatchers,
    ): CalendarRepository {
        if (isMock()) {
            return CalendarMockRepositoryImpl()
        }
        return CalendarRepositoryImpl(retrofit, authRepository, dispatchers)
    }
}
