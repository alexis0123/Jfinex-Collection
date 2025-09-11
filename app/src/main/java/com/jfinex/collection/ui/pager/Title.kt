package com.jfinex.collection.ui.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.jfinex.collection.BuildConfig

@Composable
fun Title() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .zIndex(1f)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth().weight(0.4f).padding(horizontal = 20.dp)) {
                Text(
                    text = "v${BuildConfig.APP_VERSION}",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Box {
                    Text(
                        text = "JFINEX - Collection",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.offset(1.dp, 1.dp)
                    )
                    Text(
                        text = "JFINEX - Collection",
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                OptionsMenu()
            }
        }
    }
}