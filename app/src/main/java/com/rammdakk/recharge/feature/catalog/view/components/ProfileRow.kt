package com.rammdakk.recharge.feature.catalog.view.components

import android.text.format.DateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.TextSecondarySmall
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.catalog.view.model.ProfileInfo
import com.rammdakk.recharge.feature.destinations.ProfileContentDestination
import java.util.Date

@Composable
fun ProfileRow(
    profileInfo: State<ProfileInfo?>,
    navigator: DestinationsNavigator,
    onSearch: (String) -> Unit,
) {
    val profile = profileInfo.value
    var isSearchBarVisible by remember {
        mutableStateOf(false)
    }
    Box {
        AnimatedVisibility(
            visible = !isSearchBarVisible,
            enter = slideInHorizontally(
                animationSpec = tween(
                    delayMillis = 0,
                    easing = LinearEasing
                )
            ) { -it },
            exit = slideOutHorizontally(
                animationSpec = tween(
                    delayMillis = 0,
                    easing = LinearEasing
                )
            ) { -it }
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f, true)
                        .clip(CircleShape)
                        .clickable {
                            navigator.navigate(ProfileContentDestination)
                        },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profile?.photoPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextPrimaryLarge(
                        text = profile?.name?.let {
                            stringResource(
                                id = R.string.greeting_with_username,
                                it
                            )
                        } ?: stringResource(id = R.string.greeting_without_username),
                    )
                    TextSecondarySmall(
                        text = stringResource(
                            id = R.string.today,
                            DateFormat.format("d MMM", Date().time)
                        )
                    )
                }

                Icon(
                    painterResource(id = R.drawable.search),
                    "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { isSearchBarVisible = true }
                        .background(Color.Transparent)
                        .fillMaxHeight()
                        .padding(10.dp)
                        .aspectRatio(1f, true),
                    ReChargeTokens.TextSecondary.getThemedColor()
                )
            }

        }
        AnimatedVisibility(
            visible = isSearchBarVisible,
            enter = slideInHorizontally(
                animationSpec = tween(
                    delayMillis = 0,
                    easing = LinearEasing
                )
            ) { it },
            exit = slideOutHorizontally(
                animationSpec = tween(
                    delayMillis = 0,
                    easing = LinearEasing
                )
            ) { it }
        ) {
            val focusRequester = remember { FocusRequester() }
            val modifier = Modifier.focusRequester(focusRequester)
            SearchBox(modifier, onSearch = onSearch) {
                isSearchBarVisible = false
            }
            LaunchedEffect(Unit) {
                if (isSearchBarVisible) {
                    focusRequester.requestFocus()
                } else {
                    focusRequester.freeFocus()
                }
            }
        }
    }
}