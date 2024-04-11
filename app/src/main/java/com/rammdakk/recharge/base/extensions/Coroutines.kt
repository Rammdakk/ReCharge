package com.rammdakk.recharge.base.extensions

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun merge(context: CoroutineContext, vararg func: suspend () -> Unit) =
    withContext(context) {
        func.map { async { it.invoke() } }.awaitAll()
    }