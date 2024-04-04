package com.rammdakk.recharge.feature.exercises.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rammdakk.recharge.base.theme.TextPrimarySmallInverseConstant
import com.rammdakk.recharge.feature.exercises.models.presentation.SportTypeItem

@Composable
fun SportItemCell(modifier: Modifier, sportTypeItem: SportTypeItem, onClick: () -> Unit) {

    Box(
        modifier = modifier
            .aspectRatio(1f, true)
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick.invoke() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(sportTypeItem.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = sportTypeItem.title,
            contentScale = ContentScale.Crop
        )
        TextPrimarySmallInverseConstant(
            text = sportTypeItem.title,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun SportItemCellPreview() {
    SportItemCell(Modifier.size(200.dp), SportTypeItem(1, "test", ""), {})
}