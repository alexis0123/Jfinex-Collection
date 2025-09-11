package com.jfinex.collection.ui.pager.page.collection.dialog.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.collection.ui.components.StyledButton
import com.jfinex.collection.ui.components.StyledOutlinedButton
import com.jfinex.collection.ui.dialog.components.StyledCard
import com.jfinex.collection.ui.pager.page.collection.FieldViewModel

@Composable
fun Comment(
    fieldName: String,
    onDismiss: () -> Unit,
    viewModel: FieldViewModel = hiltViewModel()
) {
    val comments by viewModel.commentPerField.collectAsState()
    var value by remember { mutableStateOf("") }
    var warning by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {}) {
        StyledCard(
            title = "Add Comment",
            cardHeight = 300.dp
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .padding(10.dp)
            ) {
                OutlinedTextField(
                    value = if (fieldName in comments) { comments[fieldName]?: "" } else { value },
                    onValueChange = { value = it },
                    readOnly = fieldName in comments,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = { Text("Write Comment here...") },
                    minLines = 5,
                    maxLines = Int.MAX_VALUE,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    shape = RoundedCornerShape(12.dp),
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
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StyledButton(
                        onClick = {
                            if (fieldName in comments) {
                                value = comments[fieldName] ?: ""
                                viewModel.removeComment(fieldName = fieldName)
                            } else {
                                viewModel.addComment(fieldName = fieldName, comment = value)
                                onDismiss()
                            }
                        },
                        name = if (fieldName in comments) { "Edit" } else { "Save" },
                        enabled = true,
                        modifier = Modifier
                            .height(60.dp)
                            .width(150.dp)
                    )
                    StyledOutlinedButton(
                        onClick = onDismiss,
                        name = "Cancel",
                        modifier = Modifier
                            .height(60.dp)
                            .width(100.dp)
                    )
                }
            }
        }
    }
}
