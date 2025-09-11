package com.jfinex.collection.ui.dialog.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jfinex.collection.BuildConfig
import com.jfinex.collection.ui.dialog.components.StyledCard

@Composable
fun About(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        StyledCard(
            title = "About",
            cardHeight = 400.dp
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = "About JFINEX COLLECTION",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "version:    ${BuildConfig.APP_VERSION}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "JFINEX Collection is a collection system built to support student organizations " +
                            "with efficient record-keeping, receipt tracking, " +
                            "and data management.",
                    style = MaterialTheme.typography.bodyMedium
                )

                val uriHandler = LocalUriHandler.current
                val authorText = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                        append("This app was designed, developed, and maintained by ")
                    }
                    pushStringAnnotation("URL", "https://alexis0123.github.io/")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue
                        )
                    ) {
                        append("Alexis Buban")
                    }
                    pop()
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                        append(
                            ", with a focus on reliability, simplicity, and offline-first functionality " +
                                    "to make collection processes smoother and more accessible."
                        )
                    }
                }

                val docsText = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                        append("See ")
                    }
                    pushStringAnnotation("DOCS", "https://alexis0123.github.io/project-jfinex-collection.html#Usage")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue
                        )
                    ) {
                        append("Documentation")
                    }
                    pop()
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                        append(" for details on setup, features, and usage.")
                    }
                }

                ClickableText(
                    text = authorText,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        authorText.getStringAnnotations(start = offset, end = offset).firstOrNull()?.let {
                            uriHandler.openUri(it.item)
                        }
                    }
                )

                ClickableText(
                    text = docsText,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        docsText.getStringAnnotations(start = offset, end = offset).firstOrNull()?.let {
                            uriHandler.openUri(it.item)
                        }
                    }
                )

            }
        }
    }
}