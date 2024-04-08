package com.rammdakk.recharge.feature.catalog.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.catalog.view.model.CategoriesList
import com.rammdakk.recharge.feature.catalog.view.model.Category

@Composable
fun CategoryRow(categories: CategoriesList) {
    var selectedId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    LaunchedEffect(Unit) {
        categories.onClick.invoke(selectedId)
    }

    LazyRow(
        modifier = Modifier
            .background(ReChargeTokens.Background.getThemedColor())
            .fillMaxWidth()
            .height(60.dp)
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        categories.cats.forEach {
            item {
                CategoryCell(category = it, isSelected = (selectedId == it.id)) {
                    selectedId = if (selectedId == it.id) {
                        null
                    } else {
                        it.id
                    }
                    categories.onClick.invoke(selectedId)
                }
            }
        }
    }
}

@Composable
fun CategoryCell(category: Category, isSelected: Boolean, onClick: () -> Unit) {
    Box(modifier = Modifier
        .padding(horizontal = 10.dp)
        .fillMaxHeight()
        .aspectRatio(1f, true)
        .clip(CircleShape)
        .background(ReChargeTokens.BackgroundColored.getThemedColor())
        .clickable { onClick.invoke() }) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(fraction = 0.65f)
                .align(Alignment.Center),
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.imagePath)
                .crossfade(true)
                .build(),
            contentDescription = "",
            colorFilter = ColorFilter.tint(if (isSelected) ReChargeTokens.TextPrimary.getThemedColor() else ReChargeTokens.TextPrimaryInverse.getThemedColor()),
            contentScale = ContentScale.Fit,
        )
    }

}