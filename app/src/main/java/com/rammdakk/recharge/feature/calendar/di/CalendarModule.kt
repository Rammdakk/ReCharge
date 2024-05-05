package com.rammdakk.recharge.feature.calendar.di

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.build.isMock
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.calendar.data.CalendarMockReservationRepositoryImpl
import com.rammdakk.recharge.feature.calendar.data.CalendarReservationRepositoryImpl
import com.rammdakk.recharge.feature.calendar.domain.CalendarReservationRepository
import com.rammdakk.recharge.feature.calendar.domain.ReservationListUseCase
import com.rammdakk.recharge.feature.calendar.domain.ReservationListUseCaseImpl
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
        errorMessageConverter: ErrorMessageConverter
    ): CalendarReservationRepository {
        if (isMock()) {
            return CalendarMockReservationRepositoryImpl()
        }
        return CalendarReservationRepositoryImpl(
            retrofit,
            authRepository,
            dispatchers,
            errorMessageConverter
        )
    }

    @Provides
    fun provideReservationListUseCase(
        calendarReservationRepository: CalendarReservationRepository
    ): ReservationListUseCase = ReservationListUseCaseImpl(calendarReservationRepository)
}
