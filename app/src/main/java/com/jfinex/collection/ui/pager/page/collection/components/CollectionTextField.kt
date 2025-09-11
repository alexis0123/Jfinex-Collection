package com.jfinex.collection.ui.pager.page.collection.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun CollectionTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isEnabled: Boolean,
    warning: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        enabled = isEnabled,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words
        ),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = if (warning) Color.Red else Color.DarkGray,
            disabledIndicatorColor = Color.Black,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = Color.DarkGray,
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Black
        )
    )
}