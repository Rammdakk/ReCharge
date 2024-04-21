package com.rammdakk.recharge.feature.exercises.activityList.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.exercises.activityList.view.components.ActivityCell
import com.rammdakk.recharge.feature.exercises.activityList.view.components.DateField
import com.rammdakk.recharge.feature.exercises.activityList.view.components.SearchRow
import com.rammdakk.recharge.feature.exercises.activityList.view.model.ActivityInfo
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActivityListScreen(
    title: String,
    date: State<Date>,
    activities: List<ActivityInfo>,
    updateDate: (Date) -> Unit,
    navigator: DestinationsNavigator,
    onBackPressed: () -> Unit
) {
    Scaffold(
        containerColor = ReChargeTokens.Background.getThemedColor(),
        topBar = {
            SearchRow(title = title, onBackPressed = onBackPressed)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ReChargeTokens.Background.getThemedColor())
                ) {
                    DateField(date) {
                        updateDate.invoke(it)
                    }
                }
            }
            items(activities) { activity ->
                ActivityCell(activityInfo = activity, navigator = navigator)
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }


}
