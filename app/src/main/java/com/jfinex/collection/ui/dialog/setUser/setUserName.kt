package com.jfinex.collection.ui.dialog.setUser

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.collection.data.local.features.user.UserViewModel
import com.jfinex.collection.ui.dialog.components.StyledCard

@Composable
fun SetUserName(
    onDismiss: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var notValidName by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            cardHeight = 150.dp,
            title = "Set User Name"
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            notValidName = false
                        },
                        label = { Text("Officer Name") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(0.6f),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = if (notValidName) Color.Red
                                    else MaterialTheme.colorScheme.secondary,
                            unfocusedIndicatorColor = if (notValidName) Color.Red
                                    else Color.DarkGray,
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
                    Spacer(modifier = Modifier.weight(0.05f))
                    Button(
                        onClick = {
                            when {
                                name.length < 2 ->
                                    notValidName = true
                                else ->
                                    viewModel.setUserTo(name.replace(".", "").trim())
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .weight(0.35f)
                            .height(53.7.dp)
                            .offset(y = 10.dp)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}