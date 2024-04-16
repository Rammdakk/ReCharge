package com.rammdakk.recharge.feature.exercises.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.PlainText
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun SearchBox(onClick: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = ReChargeTokens.BackgroundColored.getThemedColor(),
                shape = RoundedCornerShape(50)
            ),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = ReChargeTokens.Background.getThemedColor(),
            focusedContainerColor = ReChargeTokens.Background.getThemedColor(),
            focusedIndicatorColor = ReChargeTokens.BackgroundColored.getThemedColor(),
            unfocusedIndicatorColor = ReChargeTokens.BackgroundColored.getThemedColor(),
            cursorColor = ReChargeTokens.BackgroundColored.getThemedColor(),
        ),
        leadingIcon = { Icon(Icons.Filled.Search, "Search") },
        maxLines = 1,
        singleLine = true,
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onClick(it.lowercase())
        },
        placeholder = { PlainText(text = stringResource(id = R.string.exercise_search)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onClick(searchQuery.lowercase())
            keyboard?.hide()
        }),
    )
}