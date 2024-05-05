package com.rammdakk.recharge.feature.activity.di

import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.build.isMock
import com.rammdakk.recharge.feature.activity.data.ActivityMockRepositoryImp
import com.rammdakk.recharge.feature.activity.data.ActivityRepositoryImp
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import com.rammdakk.recharge.feature.activity.domain.ActivityUseCase
import com.rammdakk.recharge.feature.activity.domain.ActivityUseCaseImpl
import com.rammdakk.recharge.feature.activity.domain.ReservationInfoUseCase
import com.rammdakk.recharge.feature.activity.domain.ReservationInfoUseCaseImpl
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ActivityModule {

    @Provides
    fun provideActivityRepository(
        dispatchers: Dispatchers,
        authRepository: AuthRepository,
        retrofit: Retrofit,
        errorMessageConverter: ErrorMessageConverter
    ): ActivityRepository {
        if (isMock())
            return ActivityMockRepositoryImp()
        return ActivityRepositoryImp(retrofit, authRepository, dispatchers, errorMessageConverter)
    }

    @Provides
    fun provideActivityUseCase(
        activityRepository: ActivityRepository
    ): ActivityUseCase = ActivityUseCaseImpl(activityRepository)

    @Provides
    fun provideReservationUseCase(
        activityRepository: ActivityRepository
    ): ReservationInfoUseCase = ReservationInfoUseCaseImpl(activityRepository)
}
