package com.jfinex.collection.ui.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.jfinex.collection.R
import com.jfinex.collection.ui.pager.page.collection.CollectionPage
import com.jfinex.collection.ui.pager.page.activity.ActivityPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerNav() {
    val tabs = listOf("Activity", "Collection")
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { tabs.size })

    Box(modifier = Modifier.fillMaxSize()) {
        Title()
        FloatingPageTitle(tabs = tabs, pagerState = pagerState)

        Image(
            painter = painterResource(id = R.mipmap.icon),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 85.dp)
                .size(130.dp)
                .zIndex(2f)
        )

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fill,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> ActivityPage()
                    1 -> CollectionPage()
                }
            }
        }
    }
}