package com.jfinex.collection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jfinex.collection.ui.pager.PagerNav
import com.jfinex.collection.ui.theme.AdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdminTheme(
                darkTheme = false,
                dynamicColor = false
            ) {
                PagerNav()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    AdminTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        PagerNav()
    }
}