package com.rammdakk.recharge.feature.activity.view.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimarySmallInverseConstant
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.activity.view.model.AdminInfo

@Composable
fun ActivityImage(
    imagePath: String?,
    adminInfo: AdminInfo?
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(roundedCorner))
            .fillMaxWidth(0.9f)
            .aspectRatio(1.5f)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath).build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        )
        adminInfo?.let { adminInfo ->
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(horizontal = roundedCorner),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextPrimarySmallInverseConstant(
                    text = stringResource(id = R.string.contacts),
                )
                Row(modifier = Modifier.fillMaxHeight(0.6f)) {
                    adminInfo.whatsApp?.let {
                        Image(
                            painter = painterResource(id = R.drawable.wa_image),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(ReChargeTokens.TextPrimaryInverseConstant.getThemedColor()),
                            modifier = Modifier.clickable {
                                adminInfo.onWhatsAppClick.invoke(it)
                            }
                        )
                    }
                    adminInfo.telegram?.let {
                        Image(
                            painter = painterResource(id = R.drawable.tg_image),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(ReChargeTokens.TextPrimaryInverseConstant.getThemedColor()),
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .clickable {
                                    adminInfo.onTelegramClick.invoke(it)
                                }
                        )
                    }
                }
            }
        }
    }
}

private val roundedCorner = 20.dp