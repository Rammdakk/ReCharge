package com.rammdakk.recharge.base

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.rammdakk.recharge.NavGraphs
import com.rammdakk.recharge.appCurrentDestinationAsState
import com.rammdakk.recharge.destinations.AuthContentDestination
import com.rammdakk.recharge.destinations.Destination

@Composable
fun SampleApp() {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    val vm: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)

    // 👇 this avoids a jump in the UI that would happen if we relied only on ShowLoginWhenLoggedOut
    val startRoute = if (!vm.isLoggedInFlow.value) AuthContentDestination else NavGraphs.root.startRoute

    DestinationsNavHost(
        engine = engine,
        navController = navController,
        navGraph = NavGraphs.root,
        modifier = Modifier.fillMaxSize(),
        startRoute = startRoute
    )

    // Has to be called after calling DestinationsNavHost because only
    // then does NavController have a graph associated that we need for
    // `appCurrentDestinationAsState` method
    ShowLoginWhenLoggedOut(vm, navController)
}

private val Destination.shouldShowScaffoldElements get() = this !is AuthContentDestination

@Composable
private fun ShowLoginWhenLoggedOut(
    vm: MainViewModel,
    navController: NavHostController
) {
    val currentDestination by navController.appCurrentDestinationAsState()
    val isLoggedIn by vm.isLoggedInFlow

    if (!isLoggedIn && currentDestination != AuthContentDestination) {
        navController.navigate(AuthContentDestination) {
            launchSingleTop = true
        }
    }
}