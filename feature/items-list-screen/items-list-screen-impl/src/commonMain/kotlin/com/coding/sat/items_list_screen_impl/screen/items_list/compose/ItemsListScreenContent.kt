package com.coding.sat.items_list_screen_impl.screen.items_list.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.coding.sat.item.domain.model.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ItemsListScreenContent(
    items: List<Item>,
    totalItems: Int,
    query: String,
    onQueryChange: (String) -> Unit,
    onFabClick: () -> Unit,
    onDeleteClick: (Item) -> Unit
) {
    Scaffold(
        topBar = {
            ItemTopBar(
                totalItems = totalItems,
                query = query,
                onQueryChange = onQueryChange
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick,
                shape = CircleShape,
                containerColor = Color(0xFFFFD54F),
                contentColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Filled.Camera,
                    contentDescription = "add item"
                )
            }
        },
        modifier = Modifier
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(items) { item ->
                ItemCard(
                    item = item,
                    onDeleteClick = onDeleteClick
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}