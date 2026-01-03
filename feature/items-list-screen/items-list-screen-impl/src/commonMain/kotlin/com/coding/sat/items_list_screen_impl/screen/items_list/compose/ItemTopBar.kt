package com.coding.sat.items_list_screen_impl.screen.items_list.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ItemTopBar(
    totalItems: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "My items",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Total: $totalItems",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "search"
        )
    }
}