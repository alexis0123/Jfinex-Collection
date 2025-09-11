package com.jfinex.collection.ui.pager

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FloatingPageTitle(tabs: List<String>, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage
    val offset = pagerState.currentPageOffsetFraction

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(top = 110.dp)
            .padding(horizontal = 13.dp)
            .padding(start = 17.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        tabs.forEachIndexed { index, title ->
            val focus = 1f - abs(currentPage + offset - index).coerceIn(0f, 1f)

            val animatedScale by animateFloatAsState(
                targetValue = lerp(0.85f, 1f, focus),
                label = "scaleAnim"
            )
            val animatedAlpha by animateFloatAsState(
                targetValue = lerp(0.4f, 1f, focus),
                label = "alphaAnim"
            )
            val animatedWeight = FontWeight(
                lerp(
                    FontWeight.Normal.weight.toFloat(),
                    FontWeight.Bold.weight.toFloat(),
                    focus
                ).toInt()
            )

//            Box(
//                modifier = Modifier.weight(1f),
//                contentAlignment = Alignment.Center
//                ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .graphicsLayer {
                            scaleX = animatedScale
                            scaleY = animatedScale
                            alpha = animatedAlpha
                        }
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = animatedWeight,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                )
//            }
        }
    }
}