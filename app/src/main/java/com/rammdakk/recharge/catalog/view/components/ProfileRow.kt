package com.rammdakk.recharge.catalog.view.components

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.catalog.view.model.ProfileInfo
import com.rammdakk.recharge.destinations.ProfileContentDestination
import java.util.Date

@Composable
fun ProfileRow(
    profileInfo: State<ProfileInfo?>,
    navigator: DestinationsNavigator
) {
    val profile = profileInfo.value
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .height(50.dp)
            .fillMaxWidth(0.9f),
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
            Text(
                text = stringResource(
                    id = R.string.today,
                    DateFormat.format("d MMM", Date().time)
                ),
                color = ReChargeTokens.TextSecondary.getThemedColor(),
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }

        Icon(
            painterResource(id = R.drawable.search),
            "",
            modifier = Modifier
                .clip(CircleShape)
                .clickable { }
                .background(Color.Transparent)
                .fillMaxHeight()
                .padding(10.dp)
                .aspectRatio(1f, true),
            ReChargeTokens.TextSecondary.getThemedColor()
        )
    }
}