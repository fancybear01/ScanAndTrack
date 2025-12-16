package com.coding.sat.items_list_screen_impl.screen.items_list.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coding.sat.core.resources.Res
import com.coding.sat.core.resources.landscape_placeholder_svgrepo_com
import com.coding.sat.item.domain.model.Item
import org.jetbrains.compose.resources.painterResource

@Composable
fun ItemCard(
    item: Item
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.landscape_placeholder_svgrepo_com),
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = item.title)
                    Text(text = item.category.toString())
                    Text(text = item.barcode ?: "No barcode")
                }
            }
            Text(
                text = item.timestamp.toString(),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}