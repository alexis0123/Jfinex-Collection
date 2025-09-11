package com.jfinex.collection.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StyledButton(
    onClick: () -> Unit,
    name: String,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Black),
        enabled = enabled,
        modifier = modifier
    ) {
        Text(name)
    }
}