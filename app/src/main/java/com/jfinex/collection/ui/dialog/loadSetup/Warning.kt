package com.jfinex.collection.ui.dialog.loadSetup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jfinex.collection.ui.components.StyledButton
import com.jfinex.collection.ui.components.StyledOutlinedButton
import com.jfinex.collection.ui.dialog.components.StyledCard
import kotlinx.coroutines.delay

@Composable
fun Warning(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var countDown by remember { mutableIntStateOf(5) }

    LaunchedEffect(Unit) {
        while (countDown > 0) {
            delay(1000L)
            countDown--
        }
    }

    Dialog(onDismissRequest = onDismiss){
        StyledCard(
            title = "WARNING",
            cardHeight = 230.dp,
            iconWarning = true,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize().padding(15.dp)
            ) {
                Text(
                    buildAnnotatedString {
                        append("All saved records under username will be ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("ERASED")
                        }
                        append(" and ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("CANNOT be recovered")
                        }
                        append(". Do you still want to continue?")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StyledButton(
                        onClick = onConfirm,
                        name = if (countDown > 0) { countDown.toString() } else { "Confirm" },
                        enabled = countDown == 0,
                        modifier = Modifier
                            .height(50.dp)
                            .width(110.dp)
                    )
                    StyledOutlinedButton(
                        onClick = onDismiss,
                        name = "Cancel",
                        modifier = Modifier
                            .height(50.dp)
                            .width(160.dp)
                    )
                }
            }
        }
    }
}