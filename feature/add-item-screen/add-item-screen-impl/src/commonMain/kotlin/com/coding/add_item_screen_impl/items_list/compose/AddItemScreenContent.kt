package com.coding.add_item_screen_impl.items_list.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.coding.add_item_screen_impl.camera.rememberCameraPicker
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenAction
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenState
import com.coding.sat.item.domain.model.ItemCategory
import kotlinx.coroutines.launch
import kotlin.enums.enumEntries

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
            val scope = rememberCoroutineScope()
            val sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = false
            )
            val screenState = rememberScrollState()
            var showBottomSheet by remember { mutableStateOf(false) }
            var chosenCategory: String? by remember { mutableStateOf(null) }

            val cameraPicker = rememberCameraPicker(
                onPhotoTaken = { uriString ->
                    onAction(AddItemScreenAction.ImagePathChanged(uriString))
                }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .verticalScroll(screenState)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
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

                    Button(
                        onClick = { showBottomSheet = true }
                    ) {
                        Text(
                            text = if (chosenCategory == null) "Choose category" else "$chosenCategory",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Light
                        )
                    }

                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showBottomSheet = false },
                            sheetState = sheetState,
                            dragHandle = { BottomSheetDefaults.DragHandle() }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                for (category in enumEntries<ItemCategory>()) {
                                    Text(
                                        text = "$category",
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    onAction(
                                                        AddItemScreenAction.CategoryInputChanged(
                                                            category.toString()
                                                        )
                                                    )
                                                    chosenCategory = category.toString()
                                                    scope.launch {
                                                        sheetState.hide()
                                                    }.invokeOnCompletion {
                                                        if (!sheetState.isVisible) {
                                                            showBottomSheet = false
                                                        }
                                                    }
                                                }
                                            )
                                    )
                                }
                                Button(onClick = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                }) {
                                    Text("Закрыть")
                                }
                            }
                        }
                    }

                    Text(
                        text = "Photo",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Button(
                        onClick = { cameraPicker.takePhoto() }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                contentDescription = "camera"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = if (state.imagePath == null) "Take photo" else "Retake photo")
                        }
                    }
                    if (state.imagePath != null) {
                        Text(
                            text = "Saved: ${state.imagePath}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Image(
                            painter = rememberAsyncImagePainter(
                                model = state.imagePath,
                                contentScale = ContentScale.Crop,
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Картинка из URI",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(MaterialTheme.shapes.medium)
                        )
                    }

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