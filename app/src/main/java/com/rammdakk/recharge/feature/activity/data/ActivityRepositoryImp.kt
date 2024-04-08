package com.rammdakk.recharge.feature.activity.data


import com.rammdakk.recharge.base.data.network.makeRequest
import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import com.rammdakk.recharge.feature.activity.data.net.ActivityApi
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ActivityRepositoryImp(
    retrofit: Retrofit,
    private val authRepository: AuthRepository,
    private val dispatchers: Dispatchers
) : ActivityRepository {

    private val api = retrofit.create(ActivityApi::class.java)

    override suspend fun getActivityInfo(activityId: Int): Result<ActivityExtendedDataModel> =
        withContext(dispatchers.IO) {
            makeRequest { api.getActivityInfo(activityId) }
        }

    override suspend fun getActivityTimeTable(
        activityId: Int,
        date: Long
    ): Result<List<TimePadDataModel>> =
        withContext(dispatchers.IO) {
            makeRequest { api.getActivityTabs(activityId, date) }
        }


    private suspend fun getAccessToken(): String? = withContext(dispatchers.IO) {
        authRepository.getToken()
    }
}