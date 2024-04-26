package com.rammdakk.recharge.feature.exercises.activityList.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.PlainTextLarge
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun SearchBox(modifier: Modifier, onSearch: (String) -> Unit, onClose: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxSize()
            .border(
                width = 2.dp,
                color = ReChargeTokens.BackgroundColored.getThemedColor(),
                shape = RoundedCornerShape(50)
            ),
        textStyle = TextStyle.Default.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            color = ReChargeTokens.TextPrimary.getThemedColor(),
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
        trailingIcon = {
            IconButton(
                onClick = {
                    if (searchQuery.isNotEmpty()) {
                        searchQuery = ""
                        onSearch.invoke("")
                    } else {
                        onClose.invoke()
                    }

                }
            ) { Icon(Icons.Filled.Close, "Close") }
        },
        maxLines = 1,
        singleLine = true,
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onSearch(it.lowercase())
        },
        placeholder = { PlainTextLarge(text = stringResource(id = R.string.exercise_search)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(searchQuery.lowercase())
            keyboard?.hide()
        }),
    )
}