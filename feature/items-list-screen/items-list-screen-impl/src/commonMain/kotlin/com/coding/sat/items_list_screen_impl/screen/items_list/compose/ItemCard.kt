package com.coding.sat.items_list_screen_impl.screen.items_list.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coding.sat.core.resources.Res
import com.coding.sat.core.resources.arrow_2
import com.coding.sat.item.domain.model.Item
import org.jetbrains.compose.resources.painterResource

@Composable
fun ItemCard(
    item: Item
) {
    Card {
        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(Res.drawable.landscape_placeholder_svgrepo_com),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title
                )
                Text(
                    text = item.category.toString()
                )
                Text(
                    text = item.barcode ?: "No barcode"
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = item.timestamp.toString()
            )
        }
    }
}