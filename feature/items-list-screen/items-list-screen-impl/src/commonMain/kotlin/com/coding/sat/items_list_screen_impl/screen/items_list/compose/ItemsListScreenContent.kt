package com.coding.sat.items_list_screen_impl.screen.items_list.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coding.sat.item.domain.model.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ItemsListScreenContent(
    items: List<Item>
) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Title")
                    },
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(items) { item ->
                    ItemCard(
                        item = item
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}