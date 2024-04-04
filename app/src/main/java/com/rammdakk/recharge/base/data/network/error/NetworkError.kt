package com.rammdakk.recharge.base.data.network.error

class NetworkError(
    val code: InternetError,
    override val message: String? = ""
) : Exception() {
    override fun toString(): String {
        return code.toString()
    }
}
