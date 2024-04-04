package com.rammdakk.recharge.feature.exercises.view

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.feature.exercises.models.presentation.ExercisesTab
import com.rammdakk.recharge.feature.exercises.models.presentation.SportTypeItem
import com.rammdakk.recharge.feature.exercises.view.components.SearchBox
import com.rammdakk.recharge.feature.exercises.view.components.SportItemCell
import com.rammdakk.recharge.feature.exercises.view.components.TabCell

@Composable
fun ExerciseCatScreen(
    selectedId: Int,
    tabs: List<ExercisesTab>,
    sportTypeItems: List<SportTypeItem>,
    onTabClick: (Int) -> Unit,
    onCategoryClick: (Int) -> Unit,
) {

    var searchString by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBox(onClick = {
            searchString = it
            Log.d("Ramil", it)
        })
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            items(tabs) { exercise ->
                TabCell(
                    modifier = Modifier,
                    exercisesName = exercise.exercisesName,
                    isSelected = exercise.id == selectedId,
                    onClick = {
                        if (selectedId != exercise.id) {
                            onTabClick.invoke(exercise.id)
                        }
                    }
                )
            }
        }

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .focusable(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(sportTypeItems.filter {
                it.title.lowercase().startsWith(searchString)
            }) { sportItem ->
                SportItemCell(
                    modifier = Modifier.fillMaxWidth(),
                    sportTypeItem = sportItem
                ) {
                    Log.d("Ramil", sportItem.title)
                    onCategoryClick.invoke(sportItem.id)
                }
            }
            items(2) {
                Spacer(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Preview
@Composable
fun ExerciseScreenPreview() {
//    ExerciseScreen()
}