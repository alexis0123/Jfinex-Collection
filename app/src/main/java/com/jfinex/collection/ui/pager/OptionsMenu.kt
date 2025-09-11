package com.jfinex.collection.ui.pager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jfinex.collection.ui.dialog.about.About
import com.jfinex.collection.ui.dialog.addStudent.AddStudent
import com.jfinex.collection.ui.dialog.tally.CollectionData
import com.jfinex.collection.ui.dialog.loadSetup.ImportConfig

@Composable
fun OptionsMenu() {
    var expanded by remember { mutableStateOf(false) }
    var showAddStudent by remember { mutableStateOf(false) }
    var showImportConfig by remember { mutableStateOf(false) }
    var showAbout by remember { mutableStateOf(false) }
    var showCollectionData by remember { mutableStateOf(false) }

    if (showAddStudent) {
        AddStudent(
            onDismiss = { showAddStudent = false }
        )
    }

    if (showImportConfig) {
        ImportConfig(
            onDismiss = { showImportConfig = false }
        )
    }

    if (showAbout) {
        About(
            onDismiss = { showAbout = false }
        )
    }

    if (showCollectionData) {
        CollectionData(
            onDismiss = { showCollectionData = false }
        )
    }

    Box {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Options",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.clickable { expanded = true }.size(30.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(200.dp)
        ) {
            Column {
                DropdownMenuItem(
                text = { Text("Load Setup") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.FileOpen,
                            contentDescription = "Load Setup"
                        )
                    },
                onClick = {
                        expanded = false
                        showImportConfig = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Tally") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                            contentDescription = "Tally"
                        )
                    },
                    onClick = {
                        expanded = false
                        showCollectionData = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Add Student") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = "Add"
                        )
                    },
                    onClick = {
                        expanded = false
                        showAddStudent = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("About") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info"
                        )
                    },
                    onClick = {
                        expanded = false
                        showAbout = true
                    }
                )
            }
        }
    }
}