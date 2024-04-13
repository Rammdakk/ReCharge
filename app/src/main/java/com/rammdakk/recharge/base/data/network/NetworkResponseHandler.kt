package com.rammdakk.recharge.base.data.network

import android.util.Log
import com.rammdakk.recharge.base.data.network.error.ErrorHandlerImpl
import com.rammdakk.recharge.base.data.network.error.HttpException
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import retrofit2.Response

inline fun <T> makeRequest(requestFunc: () -> Response<T>): Result<T> {
    val response = runCatching { requestFunc.invoke() }.getOrElse {
        Log.d("Make request", it.message.toString())
        null
    } ?: return Result.failure(
        NetworkError(
            code = InternetError.Unknown,
            message = "Не удалось получить значения"
        )
    )

    if (!response.isSuccessful) {
        return Result.failure(
            NetworkError(
                ErrorHandlerImpl.getErrorType(HttpException(response.code())),
                response.errorBody()?.string() ?: response.message()
            )
        )
    }
    if (response.body() == null) {
        return Result.failure(
            NetworkError(
                InternetError.Unknown,
                "Не удалось получить значения"
            )
        )
    }
    return Result.success(response.body()!!)
}