package com.rammdakk.recharge.feature.calendar.view.components.reservation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.PlainTextSmallInverse
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryMediumInverse
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.calendar.view.model.ReservationModel
import com.rammdakk.recharge.feature.destinations.ReservationContentDestination
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun ReservationCell(
    activityInfo: ReservationModel,
    navigator: DestinationsNavigator,
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(vertical = 10.dp)
        .clip(RoundedCornerShape(roundedCorner))
        .clickable {
            navigator.navigate(
                ReservationContentDestination(
                    ReservationContentDestination.NavArgs(
                        activityId = activityInfo.activityId,
                        reservationId = activityInfo.reservationId
                    )
                )
            )
        }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(activityInfo.imagePath)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3.8f),
        )
        Column(
            modifier = Modifier
                .background(ReChargeTokens.BackgroundColored.getThemedColor())
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(0.85f)
                ) {
                    TextPrimaryMediumInverse(
                        modifier = Modifier.fillMaxWidth(),
                        text = activityInfo.name,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    activityInfo.time?.let {
                        PlainTextSmallInverse(
                            text = it.time.formatDate(),
                            textAlign = TextAlign.Start
                        )
                    }
                    PlainTextSmallInverse(
                        text = activityInfo.address,
                        textAlign = TextAlign.Start
                    )
                }
                activityInfo.location?.let { loc ->
                    Icon(
                        painterResource(id = R.drawable.location),
                        "",
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
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
    }
}

@SuppressLint("SimpleDateFormat")
private fun Long.formatDate(): String =
    if (DateUtils.isToday(this)) {
        SimpleDateFormat("'Сегодня', HH:mm")
    } else {
        SimpleDateFormat("d MMMM, HH:mm")
    }.format(Date(this))


private val roundedCorner = 20.dp