package com.rammdakk.recharge.feature.activityList.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.feature.activityList.view.components.ActivityCell
import com.rammdakk.recharge.feature.activityList.view.components.DateField
import com.rammdakk.recharge.feature.activityList.view.components.SearchRow
import com.rammdakk.recharge.feature.activityList.view.model.ActivityInfo
import java.util.Date

@Composable
fun ActivityListScreen(
    title: String,
    activities: List<ActivityInfo>,
    updateDate: (Date) -> Unit,
    navigator: DestinationsNavigator
) {
    Scaffold(
        topBar = {
            SearchRow(title = title, onBackPressed = navigator::popBackStack)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                DateField(Date()) {
                    updateDate.invoke(it)
                }
            }
            items(activities) { activity ->
                ActivityCell(activityInfo = activity, navigator = navigator)
            }
        }
    }


}
