package com.rammdakk.recharge.base

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.view.NavScaffold
import com.rammdakk.recharge.feature.NavGraphs
import com.rammdakk.recharge.feature.destinations.ActivityContentDestination
import com.rammdakk.recharge.feature.destinations.AuthContentDestination
import com.rammdakk.recharge.feature.destinations.CalendarContentDestination
import com.rammdakk.recharge.feature.destinations.CatalogContentDestination
import com.rammdakk.recharge.feature.destinations.Destination
import com.rammdakk.recharge.feature.destinations.DirectionDestination
import com.rammdakk.recharge.feature.destinations.ExercisesCatContentDestination
import com.rammdakk.recharge.feature.destinations.MapContentDestination
import com.rammdakk.recharge.feature.destinations.ProfileContentDestination
import com.rammdakk.recharge.feature.destinations.ReservationContentDestination

@Composable
fun AppContentHandler() {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    // 👇 this avoids a jump in the UI that would happen if we relied only on ShowLoginWhenLoggedOut
    val startRoute = NavGraphs.root.startRoute
    NavScaffold(
        navController = navController,
        startRoute = startRoute,
        topBar = { dest, backStackEntry ->
            if (dest.shouldShowScaffoldElements) {
                TopBar(dest, backStackEntry)
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = it.shouldShowScaffoldElements,
                enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
            ) {
                NavBar(navController)
            }
        }
    ) {
        DestinationsNavHost(
            engine = engine,
            navController = navController,
            navGraph = NavGraphs.root,
            modifier = Modifier.fillMaxSize(),
            startRoute = startRoute
        )
    }
}

@Composable
fun TopBar(dest: Destination?, backStackEntry: NavBackStackEntry?) {

}

@SuppressLint("RememberReturnType")
@Composable
fun NavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Exercise,
        BottomNavItem.Map,
        BottomNavItem.Calendar,
        BottomNavItem.Profile
    )

    var currentIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    LaunchedEffect(navController.currentDestination) {
        Log.d("Ramil", navController.currentDestination.toString())
        items.indexOfFirst { it.destination.route == navController.currentDestination?.route }.let {
            if (it > -1) {
                currentIndex = it
            }
        }
    }

    Box(
        modifier = Modifier
            .windowInsetsPadding(NavigationBarDefaults.windowInsets)
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .aspectRatio(5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items.forEachIndexed { index, item ->
                val color = remember { Animatable(Color.White) }
                val bgColor = remember { Animatable(Color.Transparent) }

                LaunchedEffect(key1 = currentIndex) {
                    if (index == currentIndex) color.animateTo(targetValue = Color.Black)
                    else color.animateTo(targetValue = Color.White)
                }

                LaunchedEffect(key1 = currentIndex) {
                    if (index == currentIndex) bgColor.animateTo(targetValue = Color.White)
                    else bgColor.animateTo(targetValue = Color.Transparent)
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f, true)
                        .padding(2.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(bgColor.value)
                        .clickable {
                            navController.navigate(item.destination)
                        },
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .align(Alignment.Center)
                            .aspectRatio(1f, true),
                        painter = painterResource(id = item.icon),
                        tint = color.value,
                        contentDescription = item.title,
                    )
                }
            }
        }
    }
}

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val destination: DirectionDestination
) {
    data object Home : BottomNavItem("Home", R.drawable.home, CatalogContentDestination)
    data object Exercise :
        BottomNavItem("Exercise", R.drawable.exercise, ExercisesCatContentDestination)

    data object Map : BottomNavItem("Map", R.drawable.map, MapContentDestination)
    data object Calendar :
        BottomNavItem("Calendar", R.drawable.calendar, CalendarContentDestination)

    data object Profile : BottomNavItem("Profile", R.drawable.profile, ProfileContentDestination)
}


private val Destination.shouldShowScaffoldElements
    get() =
        this !is AuthContentDestination && this !is ActivityContentDestination && this !is ReservationContentDestination
