package com.coding.add_item_screen_impl.items_list.compose

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.coding.sat.core.resources.Res
import com.coding.sat.core.resources.arrow_2
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemTopBar(
    clickOnBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Add item",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = clickOnBack) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_2),
                    contentDescription = "back"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    )
}