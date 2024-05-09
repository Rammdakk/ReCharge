package com.rammdakk.recharge.base.data.network

import android.util.Log
import com.rammdakk.recharge.base.data.network.error.ErrorHandlerImpl.getErrorType
import com.rammdakk.recharge.base.data.network.error.ErrorMessageConverter
import com.rammdakk.recharge.base.data.network.error.HttpException
import com.rammdakk.recharge.base.data.network.error.InternetError
import com.rammdakk.recharge.base.data.network.error.NetworkError
import retrofit2.Response

inline fun <T> makeRequest(
    errorConverter: ErrorMessageConverter,
    requestFunc: () -> Response<T>
): Result<T> {
    val response = runCatching { requestFunc.invoke() }.getOrElse {
        Log.d("Make request", it.message.toString())
        val error = getErrorType(it)
        return Result.failure(
            NetworkError(
                error,
                errorConverter.getError(error)
            )
        )
    }

    if (!response.isSuccessful) {
        val error = getErrorType(HttpException(response.code()))
        return Result.failure(
            NetworkError(
                error,
                response.errorBody()?.string() ?: response.message() ?: errorConverter.getError(
                    error
                )
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