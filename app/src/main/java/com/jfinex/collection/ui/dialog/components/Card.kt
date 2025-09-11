package com.jfinex.collection.ui.dialog.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StyledCard(
    cardHeight: Dp = 500.dp,
    title: String = "",
    iconWarning: Boolean = false,
    borderWarning: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .width(400.dp)
            .height(cardHeight),
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(15.dp),
        border =
            if (borderWarning){
                BorderStroke(2.dp, Color.Red)
            } else { BorderStroke(1.dp, Color.Black) },
        shadowElevation = 2.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    if (iconWarning) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = "Warning",
                            tint = Color.LightGray
                        )
                    }
                }
            }

            content()
        }
    }
}