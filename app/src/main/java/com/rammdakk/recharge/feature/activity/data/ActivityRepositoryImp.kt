package com.rammdakk.recharge.feature.activity.data


import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.base.extensions.formatToUtcString
import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import com.rammdakk.recharge.feature.activity.data.model.UserBookingDataModel
import com.rammdakk.recharge.feature.activity.data.net.ActivityApi
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.util.Date

class ActivityRepositoryImp(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers,
    private val errorMessageConverter: ErrorMessageConverter
) : ActivityRepository {

    private val api = retrofit.create(ActivityApi::class.java)

    override suspend fun getActivityInfo(activityId: Int): Result<ActivityExtendedDataModel> =
        withContext(dispatchers.IO) {
            makeRequest(errorMessageConverter) { api.getActivityInfo(activityId) }
        }

    override suspend fun getActivityTimeTable(
        activityId: Int,
        date: Date
    ): Result<List<TimePadDataModel>> =
        withContext(dispatchers.IO) {
            makeRequest(errorMessageConverter) {
                api.getActivityTabs(
                    activityId,
                    date.formatToUtcString()
                )
            }.map { it.slots }
        }

    override suspend fun getUsersMaxNumber(tabId: Int): Result<Int> =
        withContext(dispatchers.IO) {
            makeRequest(errorMessageConverter) { api.getUsersMaxNumber(tabId) }.map { it.freeSpots }
        }

    override suspend fun reserveActivity(tabId: Int, userBookingInfo: UserBookingDataModel) =
        withContext(dispatchers.IO) {
            getAccessToken()?.let {
                makeRequest(errorMessageConverter) {
                    api.reserveActivity(
                        it,
                        tabId,
                        userBookingInfo
                    )
                }
            } ?: Result.failure(NetworkError(InternetError.Unauthorized))
        }


    private suspend fun getAccessToken(): String? = withContext(dispatchers.IO) {
        authRepository.getToken()
    }
}