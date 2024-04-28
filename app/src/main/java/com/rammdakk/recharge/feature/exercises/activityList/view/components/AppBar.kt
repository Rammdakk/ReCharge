package com.rammdakk.recharge.feature.exercises.activityList.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.HeaderTextPrimary
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun AppBar(
    title: String,
    onSearch: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    var isSearchBarVisible by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .background(ReChargeTokens.Background.getThemedColor())
            .padding(top = 20.dp, start = 8.dp, end = 8.dp)
            .height(50.dp)
            .fillMaxWidth(),
    ) {
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
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onBackPressed.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "",
                        tint = ReChargeTokens.TextPrimary.getThemedColor(),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                HeaderTextPrimary(
                    text = title,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 4.dp)
                )
                Icon(
                    painterResource(id = R.drawable.search),
                    "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            isSearchBarVisible = true
                        }
                        .background(Color.Transparent)
                        .fillMaxHeight()
                        .padding(10.dp)
                        .aspectRatio(1f, true),
                    ReChargeTokens.BackgroundInverse.getThemedColor()
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
            SearchBox(
                modifier,
                onSearch = onSearch
            ) {
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

@Preview
@Composable
fun AppBarPreview() {
    AppBar("Активность", { }, {})
}