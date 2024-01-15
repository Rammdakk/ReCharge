package com.rammdakk.recharge.catalog.view.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.catalog.view.model.ActivityInfo
import com.rammdakk.recharge.destinations.ActivityContentDestination

@Composable
fun ActivityCell(
    activityInfo: ActivityInfo,
    navigator: DestinationsNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(roundedCorner))
            .aspectRatio(2f)
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
                .aspectRatio(4.5f)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textColor = ReChargeTokens.TextPrimaryInverse.getThemedColor()
            Column(Modifier.fillMaxHeight()) {
                Text(
                    text = activityInfo.name,
                    color = textColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = activityInfo.time, color = textColor, fontSize = 13.sp)
                Text(text = activityInfo.address, color = textColor, fontSize = 13.sp)
            }

            Column(
                Modifier
                    .fillMaxHeight()
                    .clickable {
                        navigator.navigate(ActivityContentDestination(activityInfo.id))
                    },
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = activityInfo.time, color = textColor, maxLines = 1, fontSize = 13.sp)
                Card(
                    colors = CardDefaults.cardColors(textColor, textColor, textColor, textColor),
                ) {
                    Text(
                        text = activityInfo.price,
                        color = ReChargeTokens.TextPrimary.getThemedColor(),
                        fontSize = 13.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

            }

        }

    }
}

@Composable
fun launchWebPage(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(LocalContext.current, intent, null)
}

private val roundedCorner = 20.dp