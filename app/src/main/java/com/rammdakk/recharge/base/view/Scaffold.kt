package com.rammdakk.recharge.base.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.spec.Route
import com.rammdakk.recharge.feature.appCurrentDestinationAsState
import com.rammdakk.recharge.feature.destinations.Destination
import com.rammdakk.recharge.feature.startAppDestination

@SuppressLint("RestrictedApi")
@Composable
fun NavScaffold(
    startRoute: Route,
    navController: NavHostController,
    topBar: @Composable (Destination, NavBackStackEntry?) -> Unit,
    bottomBar: @Composable (Destination) -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination
    val navBackStackEntry = navController.currentBackStackEntry

    // 👇 only for debugging, you shouldn't use currentBackStack API as it is restricted by annotation
    navController.currentBackStack.collectAsState().value.print()

    // 👇 ModalBottomSheetLayout is only needed if some destination is bottom sheet styled

    Scaffold(
        topBar = { topBar(destination, navBackStackEntry) },
        bottomBar = { bottomBar(destination) },
        content = content
    )
}

private fun Collection<NavBackStackEntry>.print(prefix: String = "stack") {
    val stack = map { it.destination.route }.toTypedArray().contentToString()
    println("$prefix = $stack")
}