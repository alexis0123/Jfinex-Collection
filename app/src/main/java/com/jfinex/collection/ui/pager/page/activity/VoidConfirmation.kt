package com.jfinex.collection.ui.pager.page.activity

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.collection.data.local.components.dateToday
import com.jfinex.collection.data.local.components.formattedDate
import com.jfinex.collection.data.local.features.collection.Collection
import com.jfinex.collection.data.local.features.collection.CollectionViewModel
import com.jfinex.collection.ui.components.StyledButton
import com.jfinex.collection.ui.components.StyledOutlinedButton
import com.jfinex.collection.ui.dialog.components.StyledCard
import com.jfinex.collection.ui.pager.page.collection.components.CollectionTextField
import com.jfinex.collection.ui.pager.page.collection.dialog.receipt.ReceiptViewModel
import kotlin.text.isNullOrBlank

@Composable
fun VoidConfirmation(
    collection: Collection,
    onDismiss: () -> Unit,
    officerName: String,
    receiptViewModel: ReceiptViewModel = hiltViewModel(),
    collectionViewModel: CollectionViewModel = hiltViewModel()
) {

    var value by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "Void Receipt",
            cardHeight = 400.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(20.dp).fillMaxSize()
            ) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        var visible by remember { mutableStateOf(false) }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(collection.block)
                            Text(
                                text = collection.type,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.QuestionMark,
                                contentDescription = "Clear search",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "${
                                    if (visible) {
                                        collection.receiptNumber
                                    } else {
                                        "******"
                                    }
                                }"
                            )
                            Icon(
                                imageVector = if (visible) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = "Delete Receipt",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { visible = !visible }
                            )
                            Text("      ")
                            Text("      ")
                            Text(formattedDate(collection.date))
                        }
                        Text("   ${collection.name}")
                        Text(
                            text = "   ${collection.item}${if (collection.category != "Paid" && !collection.category.isNullOrBlank()) " (${collection.category})" else ""}"
                        )
                    }
                }
                CollectionTextField(
                    value = value,
                    onValueChange = { value = it },
                    placeholder = "Enter Student name to Confirm",
                    true,
                    false
                )

                HorizontalDivider(color = Color.Black)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    StyledButton(
                        onClick = {
                            receiptViewModel.removeByNameBlockItem(
                                name = collection.name,
                                block = collection.block,
                                item = collection.item ?: ""
                            )

                            collectionViewModel.addCollection(
                                type = "Voided Receipt",
                                date = dateToday(),
                                name = collection.name,
                                block = collection.block,
                                officerName = officerName,
                                item = collection.item ?: "",
                                category = collection.category!!,
                                receiptNumber = collection.receiptNumber!!,
                                comment = ""
                            )
                            onDismiss()
                            collectionViewModel.delete(collection)

                        },
                        name = "Delete",
                        enabled = collection.name == value,
                        modifier = Modifier
                            .height(60.dp)
                            .width(160.dp)
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