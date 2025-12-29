package com.coding.add_item_screen_impl.items_list.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenAction
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddItemScreenContent(
    state: AddItemScreenState,
    onAction: (AddItemScreenAction) -> Unit,
    clickOnBack: () -> Unit
) {
    MaterialTheme {
        Scaffold(
            topBar = {
                AddItemTopBar(
                    clickOnBack = clickOnBack
                )
            }
        ) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = state.title,
                        onValueChange = { onAction(AddItemScreenAction.TitleChanged(it)) },
                        label = { Text("Enter title") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = state.categoryInput,
                        onValueChange = { onAction(AddItemScreenAction.CategoryInputChanged(it)) },
                        label = { Text("Enter category") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Note",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = state.note,
                        onValueChange = { onAction(AddItemScreenAction.NoteChanged(it)) },
                        label = { Text("Enter note") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Barcode",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = state.barcode,
                        onValueChange = { onAction(AddItemScreenAction.BarcodeChanged(it)) },
                        label = { Text("Enter barcode") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = state.isSaveEnabled && !state.isSaving,
                    onClick = { onAction(AddItemScreenAction.SaveItem) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "check"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save item",
                            fontSize = 28.sp
                        )
                    }
                }
            }
        }
    }
}