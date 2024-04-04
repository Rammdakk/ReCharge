package com.rammdakk.recharge.feature.catalog.view.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.PlainTextSmallInverse
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryMediumInverse
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.catalog.view.model.ActivityInfo


@Composable
fun NextActivityCell(
    activityInfo: ActivityInfo,
    navigator: DestinationsNavigator,
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(roundedCorner))
            .aspectRatio(1.8f)
            .clickable {
//                navigator.navigate(ActivityContentDestination(activityInfo.id))
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(activityInfo.imagePath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.navigate_next),
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
            Column {
                TextPrimaryMediumInverse(text = activityInfo.name)
                activityInfo.time?.let { PlainTextSmallInverse(text = it) }
                PlainTextSmallInverse(text = activityInfo.address)
            }

            Icon(
                painterResource(id = R.drawable.location),
                "",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        val loc = activityInfo.location
                        val uri = Uri.parse("geo:${loc.latitude},${loc.longitude}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        launcher.launch(intent)
                    }
                    .background(Color.Transparent)
                    .height(36.dp)
                    .aspectRatio(1f, true),
                ReChargeTokens.TextPrimaryInverse.getThemedColor()
            )

        }

    }
}

private val roundedCorner = 20.dp