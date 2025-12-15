package com.coding.sat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.coding.items_list_screen_api.ItemsListScreenApi
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val itemsListScreenApi = koinInject<ItemsListScreenApi>()
            MaterialTheme {
                Navigator(
                    screen = itemsListScreenApi.itemsListScreen()
                )
            }
        }
    }
}