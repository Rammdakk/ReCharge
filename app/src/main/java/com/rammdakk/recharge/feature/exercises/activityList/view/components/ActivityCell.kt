package com.rammdakk.recharge.feature.exercises.activityList.view.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.PlainTextBold
import com.rammdakk.recharge.base.theme.PlainTextInverse
import com.rammdakk.recharge.base.theme.PlainTextSmallInverse
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryMediumInverse
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.destinations.ActivityContentDestination
import com.rammdakk.recharge.feature.exercises.activityList.view.model.ActivityInfo
import kotlin.math.roundToInt

@Composable
fun ActivityCell(
    activityInfo: ActivityInfo,
    navigator: DestinationsNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(roundedCorner))
            .aspectRatio(2.2f)
            .clickable {
                navigator.navigate(ActivityContentDestination(activityInfo.id, activityInfo.date))
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(activityInfo.imagePath)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(ReChargeTokens.BackgroundColored.getThemedColor())
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.75f)) {
                TextPrimaryMediumInverse(
                    text = activityInfo.name,
                    maxLines = 2,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                PlainTextSmallInverse(
                    text = activityInfo.organizationName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                PlainTextSmallInverse(
                    text = activityInfo.address,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                activityInfo.time?.let {
                    PlainTextInverse(
                        text = it,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                PlainTextBold(
                    text = stringResource(
                        id = R.string.price,
                        activityInfo.price.roundToInt()
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(50f))
                        .background(ReChargeTokens.TextPrimaryInverse.getThemedColor())
                        .padding(horizontal = 6.dp, vertical = 2.dp)

                )
            }
        }
    }
}

@Composable
fun LaunchWebPage(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(LocalContext.current, intent, null)
}

private val roundedCorner = 24.dp