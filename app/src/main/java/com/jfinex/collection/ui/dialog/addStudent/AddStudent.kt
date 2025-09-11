package com.jfinex.collection.ui.dialog.addStudent

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.jfinex.collection.ui.dialog.setUser.SetUserName

@Composable
fun AddStudent(
    onDismiss: () -> Unit,
    studentViewModel: StudentViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val user by userViewModel.user.collectAsState()

    var block by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var middleInitial by remember { mutableStateOf("") }

    var blockWarning by remember { mutableStateOf(false) }
    var lastNameWarning by remember { mutableStateOf(false) }
    var firstNameWarning by remember { mutableStateOf(false) }
    var middleInitialWarning by remember { mutableStateOf(false) }

    val formattedName = if (middleInitial.isBlank()) {
        "${lastName.trim()}, ${firstName.trim()}"
    } else {
        "${lastName.trim()}, ${firstName.trim()} ${middleInitial.trim()}."
    }

    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "Add Student",
            cardHeight = 300.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = block,
                        onValueChange = {
                            block = it.take(2).uppercase()
                            blockWarning = false
                        },
                        label = { Text("Block") },
                        isError = blockWarning,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters
                        ),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        modifier = Modifier.weight(0.29f)
                    )
                    Spacer(modifier = Modifier.weight(0.01f))
                    Surface(
                        modifier = Modifier
                            .weight(0.7f)
                            .height(63.dp)
                            .offset(y = 3.dp)
                            .padding(5.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        color = MaterialTheme.colorScheme.secondary
                    ) {
                        Text(
                            text = formattedName,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = {
                            lastName = it
                            lastNameWarning = false
                        },
                        label = { Text("Surname") },
                        isError = lastNameWarning,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        ),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        modifier = Modifier.weight(0.34f)
                    )

                    Spacer(modifier = Modifier.weight(0.01f))

                    OutlinedTextField(
                        value = firstName,
                        onValueChange = {
                            firstName = it
                            firstNameWarning = false
                        },
                        label = { Text("Name") },
                        isError = firstNameWarning,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        ),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        modifier = Modifier.weight(0.34f)
                    )

                    Spacer(modifier = Modifier.weight(0.01f))

                    OutlinedTextField(
                        value = middleInitial,
                        onValueChange = {
                            middleInitial = it.take(1).uppercase()
                            middleInitialWarning = false
                        },
                        label = { Text("MI") },
                        isError = middleInitialWarning,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters
                        ),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        modifier = Modifier.weight(0.2f)
                    )
                }

                HorizontalDivider(color = Color.Black)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            blockWarning = block.isBlank()
                            lastNameWarning = lastName.isBlank()
                            firstNameWarning = firstName.isBlank()

                            val hasError = blockWarning || lastNameWarning || firstNameWarning
                            if (!hasError) {
                                studentViewModel.addStudent(
                                    block = block.trim(),
                                    name = formattedName
                                )
                                onDismiss()
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .width(160.dp)
                            .height(50.dp)
                    ) {
                        Text("Add Student")
                    }
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(110.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
    if (user == null) {
        SetUserName(
            onDismiss = onDismiss
        )
    }
}