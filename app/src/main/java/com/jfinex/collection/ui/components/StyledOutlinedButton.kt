package com.jfinex.collection.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StyledOutlinedButton(
    onClick: () -> Unit,
    name: String,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier
    ) {
        Text(text = name, color = Color.Black)
    }
}