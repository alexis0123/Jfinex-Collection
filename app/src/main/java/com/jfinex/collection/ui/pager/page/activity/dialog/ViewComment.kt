package com.jfinex.collection.ui.pager.page.activity.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jfinex.collection.ui.dialog.components.StyledCard

@Composable
fun ViewComment(
    onDismiss: () -> Unit,
    comment: String
) {
    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "Comment",
            cardHeight = 220.dp
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .padding(10.dp)
            ) {
                OutlinedTextField(
                    value = comment,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = { Text("Write Comment here...") },
                    minLines = 5,
                    maxLines = Int.MAX_VALUE,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                        unfocusedIndicatorColor = Color.Gray,
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
        }
    }
}