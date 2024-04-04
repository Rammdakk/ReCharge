package com.rammdakk.recharge.feature.catalog.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.catalog.view.components.ActivityCell
import com.rammdakk.recharge.feature.catalog.view.components.CategoryRow
import com.rammdakk.recharge.feature.catalog.view.components.NextActivityCell
import com.rammdakk.recharge.feature.catalog.view.components.ProfileRow
import com.rammdakk.recharge.feature.catalog.view.model.ActivityInfo
import com.rammdakk.recharge.feature.catalog.view.model.Category
import com.rammdakk.recharge.feature.catalog.view.model.ProfileInfo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogScreen(
    profileInfo: State<ProfileInfo?>,
    nextActivity: State<ActivityInfo?>,
    categories: State<List<Category>>,
    activities: State<List<ActivityInfo>>,
    navigator: DestinationsNavigator,
) {
    Column(
        modifier = Modifier.background(ReChargeTokens.Background.getThemedColor()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileRow(profileInfo, navigator)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            nextActivity.value?.let {
                item {
                    TextPrimaryLarge(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        text = stringResource(id = R.string.next_activity),
                        textAlign = TextAlign.Start
                    )
                    NextActivityCell(it, navigator)
                }
            }

            stickyHeader {
                TextPrimaryLarge(
                    modifier = Modifier
                        .background(ReChargeTokens.Background.getThemedColor())
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    text = stringResource(id = R.string.suggested_activity),
                    textAlign = TextAlign.Start
                )
                CategoryRow(categories.value)
            }

            activities.value.forEach {
                item { ActivityCell(activityInfo = it, navigator = navigator) }
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}
