package com.rammdakk.recharge.build

import com.rammdakk.recharge.BuildConfig

fun isMock(): Boolean = BuildConfig.BUILD_TYPE == "mock"