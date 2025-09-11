package com.jfinex.collection.ui.dialog.loadSetup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.collection.ui.config.exportConfig.ConfigViewModel
import com.jfinex.collection.ui.dialog.components.StyledCard
import java.io.File
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jfinex.collection.data.local.features.user.UserViewModel

@Composable
fun ImportConfig(
    onDismiss: () -> Unit,
    viewModel: ConfigViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val user by userViewModel.user.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val importResult by viewModel.importResult.collectAsState()
    var showWarning by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {
        onDismiss()
        viewModel.reset()
    }) {
        StyledCard(
            title = "Load New Setup",
            cardHeight = 200.dp
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.OpenDocument(),
                    onResult = { uri ->
                        if (uri != null) {
                            val outputFile = File(context.filesDir, "imported_config.json")
                            viewModel.importConfig(context.contentResolver, uri, outputFile)
                        }
                    }
                )
                importResult?.let {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                            .padding(5.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 1.dp, color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        it.getOrNull()?.let {
                            Text(
                                "Import successful!",
                                color = Color.Green
                            )
                        } ?: run {
                            Text(
                                "Failed: ${importResult!!.exceptionOrNull()?.message ?: "Unknown error"}",
                                color = Color.Red,
                            )
                        }
                    }
                }

                if (showWarning) {
                    Warning(
                        onConfirm = {
                            launcher.launch(arrayOf("application/json"))
                            viewModel.reset()
                            showWarning = false
                        },
                        onDismiss = onDismiss
                    )
                }

                if (isLoading) {
                    Text("Importing…")
                } else {
                    Button(
                        onClick = {
                            if (user == null) {
                                launcher.launch(arrayOf("application/json"))
                                viewModel.reset()
                            } else { showWarning = true }
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier.height(53.7.dp)
                    ) {
                        Text("Select Setup File")
                    }
                }
            }
        }
    }
}