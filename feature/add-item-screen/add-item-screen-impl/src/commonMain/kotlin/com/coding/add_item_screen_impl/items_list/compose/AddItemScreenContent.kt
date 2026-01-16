package com.coding.add_item_screen_impl.items_list.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.coding.add_item_screen_impl.camera.rememberCameraPicker
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenAction
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenState
import com.coding.sat.item.domain.model.ItemCategory
import kotlin.enums.enumEntries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddItemScreenContent(
    state: AddItemScreenState,
    onAction: (AddItemScreenAction) -> Unit,
    clickOnBack: () -> Unit
) {
    Scaffold(
        topBar = {
            AddItemTopBar(
                clickOnBack = clickOnBack,
                label = if (state.isEditingItem) "Edit item" else "Add item"
            )
        }
    ) { innerPadding ->
        val screenState = rememberScrollState()

        val cameraPicker = rememberCameraPicker(
            onPhotoTaken = { uriString ->
                onAction(AddItemScreenAction.ImagePathChanged(uriString))
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(screenState)
        ) {
            SectionCard(title = "Main info", supportingText = "Название и категория") {
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { onAction(AddItemScreenAction.TitleChanged(it)) },
                    label = { Text("Enter title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Category",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (category in enumEntries<ItemCategory>()) {
                        val selected = state.category == category
                        FilterChip(
                            selected = selected,
                            onClick = {
                                onAction(
                                    AddItemScreenAction.CategoryInputChanged(category.name)
                                )
                            },
                            label = {
                                Text(
                                    text = category.name.lowercase()
                                        .replaceFirstChar { it.titlecase() }
                                        .replace('_', ' '),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            leadingIcon = if (selected) {
                                { Icon(Icons.Filled.Check, contentDescription = null) }
                            } else null,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            SectionCard(title = "Photo", supportingText = "Добавьте фото, чтобы легче находить предмет") {
                Button(
                    onClick = { cameraPicker.takePhoto() },
                    shape = RoundedCornerShape(12.dp)
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

                AnimatedVisibility(
                    visible = state.imagePath != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = state.imagePath ?: "",
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = state.imagePath,
                                contentScale = ContentScale.Crop
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Картинка из URI",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                }
            }

            SectionCard(title = "Details", supportingText = "Описание и штрихкод") {
                Text(
                    text = "Note",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                OutlinedTextField(
                    value = state.note,
                    onValueChange = { onAction(AddItemScreenAction.NoteChanged(it)) },
                    label = { Text("Enter note") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Barcode",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                OutlinedTextField(
                    value = state.barcode,
                    onValueChange = { onAction(AddItemScreenAction.BarcodeChanged(it)) },
                    label = { Text("Enter barcode") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Button(
                    enabled = state.imagePath != null,
                    onClick = { state.imagePath?.let { onAction(AddItemScreenAction.BarcodeScanRequested(it)) } },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.Photo, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Scan barcode",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = state.isSaveEnabled && !state.isSaving,
                onClick = { onAction(AddItemScreenAction.SaveItem) },
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "check"
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (state.isEditingItem) "Update item" else "Save item",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    supportingText: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            supportingText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            content()
        }
    }
}