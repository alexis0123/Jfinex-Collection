package com.jfinex.collection.ui.dialog.tally

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfinex.collection.data.local.components.dateToday
import com.jfinex.collection.data.local.features.user.UserViewModel
import com.jfinex.collection.ui.components.StyledButton
import com.jfinex.collection.ui.components.StyledOutlinedButton
import com.jfinex.collection.ui.dialog.components.StyledCard
import com.jfinex.collection.ui.dialog.setUser.SetUserName
import com.jfinex.collection.ui.pager.page.collection.FieldViewModel
import com.jfinex.collection.ui.pager.page.collection.dialog.receipt.ReceiptViewModel

@Composable
fun CollectionData(
    onDismiss: () -> Unit,
    receiptViewModel: ReceiptViewModel = hiltViewModel(),
    fieldViewModel: FieldViewModel = hiltViewModel(),
    tallyViewModel: TallyViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val exportState by tallyViewModel.exportState.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        contract = CreateDocument("application/json"),
        onResult = { uri ->
            if (uri != null) {
                tallyViewModel.export(uri, context.contentResolver)
            }
        }
    )
    LaunchedEffect(exportState) {
        when (val state = exportState) {
            is ExportState.ShareReady -> {
                Toast.makeText(context, "Tally exported successfully!", Toast.LENGTH_SHORT).show()

                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/json"
                    putExtra(Intent.EXTRA_STREAM, state.uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share Tally"))

                tallyViewModel.resetState()
                onDismiss()
            }
            is ExportState.Error -> {
                Toast.makeText(context, "Export failed: ${state.message}", Toast.LENGTH_LONG).show()
                tallyViewModel.resetState()
            }
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        receiptViewModel.getAll()
    }
    val user by userViewModel.user.collectAsState()
    val receipts by receiptViewModel.receipts.collectAsState()
    val fields by fieldViewModel.fields.collectAsState()

    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "Tally"
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    items(fields) { field ->
                        var expanded by remember { mutableStateOf(false) }
                        val categories = field.categories
                        val receiptsForField = receipts.filter {
                            it.item == field.name && it.new
                        }
                        val totalPerCategory: Map<String, Int> = receiptsForField
                            .groupingBy { it.category }
                            .eachCount()
                        val receiptTotal = receiptsForField.size


                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(
                                    if (expanded) {
                                        (90 + (36 * categories.size)).dp
                                    } else {
                                        50.dp
                                    }
                                )
                                .background(Color.White, RoundedCornerShape(10.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                                .clickable(
                                    onClick = {
                                        if (field.categories.isNotEmpty()) expanded = !expanded
                                    },
                                    enabled = field.categories.isNotEmpty()
                                )
                        ) {
                            if (expanded) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(horizontal = 15.dp)
                                    ) {
                                        Text(field.name, modifier = Modifier.weight(5f))
                                        Text(
                                            text = "",
                                            textAlign = TextAlign.End,
                                            modifier = Modifier.weight(5f))
                                        Icon(
                                            imageVector = Icons.Filled.ExpandLess,
                                            contentDescription = "Expand",
                                            modifier = Modifier.weight(2f)
                                        )
                                    }
                                    categories.forEach { category ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 15.dp)
                                                .padding(end = 35.dp)
                                        ) {
                                            Text("    $category")
                                            Text(
                                                text = " - - - - - - - - - - - - - - - - - - - - - - - - - - - ",
                                                color = Color.LightGray,
                                                maxLines = 1,
                                                overflow = TextOverflow.Clip,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text((totalPerCategory[category] ?: 0).toString())
                                        }
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 15.dp)
                                            .padding(end = 35.dp)
                                    ) {
                                        Text(text = "Total", fontWeight = FontWeight.Bold)
                                        Text(
                                            text = " - - - - - - - - - - - - - - - - - - - - - - - - - - - ",
                                            color = Color.LightGray,
                                            maxLines = 1,
                                            overflow = TextOverflow.Clip,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(receiptTotal.toString(), fontWeight = FontWeight.Bold)
                                    }
                                }
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 15.dp)
                                ) {
                                    Text(field.name, modifier = Modifier.weight(5f))
                                    Text(
                                        text = receiptTotal.toString(),
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.weight(5f))
                                    if (categories.isNotEmpty()){
                                        Icon(
                                            imageVector = Icons.Filled.ExpandMore,
                                            contentDescription = "Expand",
                                            modifier = Modifier.weight(2f)
                                        )
                                    } else {
                                        Box(modifier = Modifier.weight(2f)) {}
                                    }
                                }
                            }
                        }
                    }
                }
                HorizontalDivider(color = Color.Black)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    StyledButton(
                        onClick = {
                            launcher.launch(
                                "${user?.name?.dropLast(6)}-Tally-${dateToday()}.json"
                            )
                        },
                        name = "Tally",
                        enabled = true,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp)
                    )
                    StyledOutlinedButton(
                        onClick = onDismiss,
                        name = "Cancel",
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(110.dp)
                    )
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