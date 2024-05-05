package com.rammdakk.recharge.feature.reservation.di

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.build.isMock
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import com.rammdakk.recharge.feature.reservation.data.ReservationMockRepositoryImpl
import com.rammdakk.recharge.feature.reservation.data.ReservationRepositoryImpl
import com.rammdakk.recharge.feature.reservation.domain.ReservationRepository
import com.rammdakk.recharge.feature.reservation.domain.ReservationUseCase
import com.rammdakk.recharge.feature.reservation.domain.ReservationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ReservationModule {

    @Provides
    fun provideReservationRepository(
        retrofit: Retrofit,
        authRepository: AuthRepository,
        dispatchers: Dispatchers,
        errorMessageConverter: ErrorMessageConverter
    ): ReservationRepository {
        if (isMock()) {
            return ReservationMockRepositoryImpl()
        }
        return ReservationRepositoryImpl(
            retrofit,
            authRepository,
            dispatchers,
            errorMessageConverter
        )
    }

    @Provides
    fun provideReservationUseCase(
        reservationRepository: ReservationRepository
    ): ReservationUseCase = ReservationUseCaseImpl(reservationRepository)
}
