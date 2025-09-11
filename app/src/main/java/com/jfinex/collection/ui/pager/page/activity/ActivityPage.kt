package com.jfinex.collection.ui.pager.page.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.EditOff
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfinex.collection.data.local.components.formattedDate
import com.jfinex.collection.data.local.features.collection.Collection
import com.jfinex.collection.data.local.features.user.UserViewModel
import com.jfinex.collection.ui.pager.page.activity.dialog.Filter
import com.jfinex.collection.ui.pager.page.activity.dialog.ViewComment
import com.jfinex.collection.ui.pager.page.collection.components.CollectionTextField

@Composable
fun ActivityPage(
    activityViewModel: ActivitiesViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {

    val userName by userViewModel.user.collectAsState()
    val activities by activityViewModel.results.collectAsState()
    val query by activityViewModel.query.collectAsState()
    val isLoading by activityViewModel.isLoading.collectAsState()
    val filterOn by activityViewModel.filterOn.collectAsState()
    var showVoidConfirmation by remember { mutableStateOf(false) }
    var showViewComment by remember { mutableStateOf(false) }
    var selectedActivity by remember { mutableStateOf<Collection?>(null) }
    var showFilter by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    if (showVoidConfirmation) {
        VoidConfirmation(
            collection = selectedActivity!!,
            officerName = userName!!.name,
            onDismiss = { showVoidConfirmation = false }
        )
    }

    if (showViewComment) {
        ViewComment(
            comment = selectedActivity?.comment ?: "",
            onDismiss = { showViewComment = false }
        )
    }

    if (showFilter) {
        Filter(
            onDismiss = { showFilter = false }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {
        Box(
            modifier = Modifier
                .padding(13.dp)
                .height(40.dp)
                .width(100.dp)
                .border(
                    width = 1.dp, color = Color.Black,
                    shape = RoundedCornerShape(32.dp)
                )
                .align(Alignment.TopStart)
                .padding(horizontal = 10.dp)
        ) { Text(
            text = userName?.name?.takeLast(5) ?: "",
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Center)
        ) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
                .padding(top = 65.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Text("Activities", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(
                    "Activities", fontWeight = FontWeight.Bold, fontSize = 25.sp,
                    modifier = Modifier.offset(1.dp, 1.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxHeight()
                ) {
                    Text("   Filter", fontWeight = FontWeight.Bold)

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.65f)
                            .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                            .clickable(
                                onClick = { showFilter = true }
                            )
                    ) {
                        if (filterOn) {
                            Icon(
                                imageVector = Icons.Default.FilterAlt,
                                contentDescription = "FilterOn",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.FilterAltOff,
                                contentDescription = "FilterOff",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(0.02f))

                Column(
                    modifier = Modifier
                        .weight(0.73f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("   Search", fontWeight = FontWeight.Bold)
                        if (query.isNotBlank()) Text(
                            text = "Clear",
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                color = Color.Red,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier.clickable(
                                onClick = {
                                    activityViewModel.clear()
                                    focusManager.clearFocus()
                                }
                            )
                        )
                    }
                    CollectionTextField(
                        value = query,
                        onValueChange = { activityViewModel.updateQuery(it) },
                        placeholder = "Search by Student Name",
                        isEnabled = true,
                        warning = false,
                        modifier = Modifier.weight(0.65f)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(345.dp)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(strokeWidth = 5.dp)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (activities.isEmpty()) {
                            item {
                                Text("No Activities")
                            }
                        }
                        items(activities) { activity ->
                            Surface(
                                color = when {
                                    activity.type == "Receipt" && activity.officerName == userName?.name ->
                                        Color.White

                                    activity.type == "Receipt" ->
                                        Color.White.copy(alpha = 0.5f)

                                    activity.type == "Voided Receipt" ->
                                        Color.Red.copy(alpha = 0.1f)

                                    else ->
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                },
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
                                    if (activity.type == "Receipt" || activity.type == "Voided Receipt") {
                                        var visible by remember { mutableStateOf(false) }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = activity.block,
                                                modifier = Modifier.weight(if (activity.new) { 0.12f } else { 0.25f })
                                            )
                                            if (activity.new) {
                                                Text(
                                                    text = "NEW",
                                                    color = Color.Red.copy(alpha = 0.7f),
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    modifier = Modifier.weight(0.13f)
                                                )
                                            }
                                            Text(
                                                text = "${activity.type} ",
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.weight(0.4f),
                                                textAlign = TextAlign.Right
                                            )
                                            Text(
                                                text = activity.officerName.dropLast(6),
                                                color = Color.Gray,
                                                modifier = Modifier.weight(0.3f),
                                                textAlign = TextAlign.Center,
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = 1
                                            )
                                            when {
                                                activity.type == "Receipt" &&
                                                activity.officerName == userName?.name ->
                                                    Icon(
                                                        imageVector = Icons.Default.DeleteOutline,
                                                        contentDescription = "Clear search",
                                                        tint = Color.Gray,
                                                        modifier = Modifier
                                                            .weight(0.15f)
                                                            .clickable {
                                                                selectedActivity = activity
                                                                showVoidConfirmation = true
                                                            }
                                                    )

                                                activity.type == "Receipt" &&
                                                activity.officerName != userName?.name ->
                                                    Icon(
                                                        imageVector = Icons.Default.Check,
                                                        contentDescription = "Check",
                                                        tint = Color.Gray,
                                                        modifier = Modifier
                                                            .weight(0.15f)
                                                    )

                                                else ->
                                                    Icon(
                                                        imageVector = Icons.Default.EditOff,
                                                        contentDescription = "EditOff",
                                                        tint = Color.Gray,
                                                        modifier = Modifier
                                                            .weight(0.15f)
                                                    )
                                            }
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("${if(visible){activity.receiptNumber}else{"******"}}")
                                            Icon(
                                                imageVector = if (visible) {
                                                    Icons.Default.Visibility
                                                } else { Icons.Default.VisibilityOff },
                                                contentDescription = "Delete Receipt",
                                                tint =
                                                    if (activity.type == "Receipt") { Color.Gray }
                                                    else { Color.Gray.copy(alpha = 0.3f) },
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .clickable(
                                                        enabled = activity.type == "Receipt",
                                                        onClick = { visible = !visible }
                                                    )
                                            )
                                            Text("           ")
                                            Text("           ")
                                            Text(formattedDate(activity.date))
                                        }
                                        Text("   ${activity.name}",
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis)
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(end = 10.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "   ${activity.item}${
                                                    if (activity.category != "Paid" && !activity.category.isNullOrBlank()) 
                                                        " (${activity.category})" else ""}",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            if (activity.comment.isNotBlank()) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.Comment,
                                                    contentDescription = "Delete Receipt",
                                                    tint = Color.Gray,
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                        .clickable {
                                                            selectedActivity = activity
                                                            showViewComment = true
                                                        }
                                                )
                                            }
                                        }


                                    }
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        if (activity.new) {
                                            Text(
                                                text = "NEW",
                                                color = Color.Red.copy(alpha = 0.7f),
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Right,
                                                style = MaterialTheme.typography.bodySmall,
                                                modifier = Modifier.weight(0.15f)
                                            )
                                        }
                                        Text(
                                            text = activity.type,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Right,
                                            modifier = Modifier.weight(0.5f)
                                        )
                                        Text(
                                            text = activity.officerName.dropLast(6),
                                            color = Color.Gray,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.weight(0.35f),
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(activity.block)
                                        Text(formattedDate(activity.date))
                                    }
                                    Text(activity.name)
                                }
                            }
                        }
                    }
                }
            }
            HorizontalDivider(color = Color.Black)
        }
    }
}