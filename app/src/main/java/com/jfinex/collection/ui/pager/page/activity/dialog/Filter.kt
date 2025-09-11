package com.jfinex.collection.ui.pager.page.activity.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.collection.ui.components.StyledButton
import com.jfinex.collection.ui.components.StyledOutlinedButton
import com.jfinex.collection.ui.dialog.components.StyledCard
import com.jfinex.collection.ui.pager.page.activity.ActivitiesViewModel
import com.jfinex.collection.ui.pager.page.collection.components.CollectionTextField

@Composable
fun Filter(
    onDismiss: () -> Unit,
    activitiesViewModel: ActivitiesViewModel = hiltViewModel()
) {
    val items by activitiesViewModel.items.collectAsState()
    var blockFilter by remember { mutableStateOf(activitiesViewModel.blockFilter.value) }
    var itemFilter by remember { mutableStateOf(activitiesViewModel.itemFilter.value) }
    var itemExpanded by remember { mutableStateOf(false) }
    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "Filter",
            cardHeight = 200.dp
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    CollectionTextField(
                        value = blockFilter,
                        onValueChange = { blockFilter = it.uppercase() },
                        placeholder = "Block",
                        isEnabled = true,
                        warning = false,
                        modifier = Modifier.weight(6f).fillMaxHeight()
                    )
                    Spacer(modifier = Modifier.weight(0.1f).fillMaxHeight())
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(9f)
                            .fillMaxHeight()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(10.dp)
                            .clickable(onClick = { itemExpanded = true })
                    ) {
                        Text(
                            text = if (itemFilter.isEmpty() || itemFilter.size == items.size) {
                                "All Items"
                            } else { "${itemFilter.size} Selected" }
                        )
                        Icon(
                            imageVector = if (itemExpanded) {
                                Icons.Default.ExpandLess
                            } else { Icons.Default.ExpandMore },
                            contentDescription = "Expand",
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                        DropdownMenu(
                            expanded = itemExpanded,
                            onDismissRequest = { itemExpanded = false },
                            modifier = Modifier
                                .width(160.dp)
                                .heightIn(max = 230.dp)
                        ) {
                            Column {
                                items.forEach { item ->
                                    DropdownMenuItem(
                                        leadingIcon = {
                                            Icon(
                                                imageVector = if (item in itemFilter) {
                                                    Icons.Default.CheckBox
                                                } else {
                                                    Icons.Default.CheckBoxOutlineBlank
                                                },
                                                contentDescription = null
                                            )
                                        },
                                        text = { Text(item) },
                                        onClick = {
                                            if (item in itemFilter) {
                                                itemFilter -= item
                                            } else { itemFilter += item }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    StyledButton(
                        onClick = {
                            activitiesViewModel.updateBlockFilter(blockFilter)
                            activitiesViewModel.updateItemFilter(itemFilter)
                            onDismiss()
                        },
                        name = "Apply",
                        enabled = true,
                        modifier = Modifier.weight(3f).fillMaxHeight()
                    )
                    Spacer(modifier = Modifier.weight(0.1f).fillMaxHeight())
                    StyledButton(
                        onClick = {
                            activitiesViewModel.clearFilter()
                            onDismiss()
                        },
                        name = "Clear",
                        enabled = true,
                        modifier = Modifier.weight(3f).fillMaxHeight()
                    )
                    Spacer(modifier = Modifier.weight(0.1f).fillMaxHeight())
                    StyledOutlinedButton(
                        onClick = onDismiss,
                        name = "Cancel",
                        modifier = Modifier.weight(3f).fillMaxHeight()
                    )
                }
            }
        }
    }
}