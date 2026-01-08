package com.coding.add_item_screen_impl.items_list

import com.coding.add_item_screen_impl.camera.BarcodeScanner
import com.coding.add_item_screen_impl.camera.ImageSaver
import com.coding.add_item_screen_impl.camera.ScanResult
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenAction
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenAction.*
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEffect
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEffect.*
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEvent
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEvent.NavigateBack
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEvent.ShowError
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenState
import com.coding.mvi_koin_voyager.MviModel
import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.usecase.SaveItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class AddItemScreenModel(
    tag: String,
    private val saveItemUseCase: SaveItemUseCase,
    private val imageSaver: ImageSaver,
    private val barcodeScanner: BarcodeScanner
) : MviModel<AddItemScreenAction, AddItemScreenEffect, AddItemScreenEvent, AddItemScreenState>(
    defaultState = AddItemScreenState.DEFAULT,
    tag = tag,
) {

    override fun reducer(
        effect: AddItemScreenEffect,
        previousState: AddItemScreenState
    ): AddItemScreenState = when (effect) {
        is AddItemScreenEffect.TitleChanged -> previousState.updateTitle(effect.value)
        is AddItemScreenEffect.CategoryInputChanged -> previousState.updateCategoryInput(effect.value)
        is AddItemScreenEffect.CategorySelected -> previousState.updateCategory(effect.value)
        is AddItemScreenEffect.NoteChanged -> previousState.updateNote(effect.value)
        is AddItemScreenEffect.BarcodeChanged -> previousState.updateBarcode(effect.value)
        is AddItemScreenEffect.ImagePathChanged -> previousState.updateImagePath(effect.value)
        is AddItemScreenEffect.TimestampChanged -> previousState.updateTimestamp(effect.value)
        is IdChanged -> previousState.updateId(effect.value)
        is Saving -> previousState.toggleSaving(effect.value)
        is BarcodeScanInProgress -> previousState.updateBarcodeScanProgress(effect.value)
        is BarcodeScanResult -> previousState.applyScannedBarcode(effect.value)
    }

    override suspend fun actor(action: AddItemScreenAction) = when (action) {
        is AddItemScreenAction.TitleChanged -> push(AddItemScreenEffect.TitleChanged(action.value))
        is AddItemScreenAction.CategoryInputChanged -> push(AddItemScreenEffect.CategoryInputChanged(action.value))
        is AddItemScreenAction.CategorySelected -> push(AddItemScreenEffect.CategorySelected(action.value))
        is AddItemScreenAction.NoteChanged -> push(AddItemScreenEffect.NoteChanged(action.value))
        is AddItemScreenAction.BarcodeChanged -> push(AddItemScreenEffect.BarcodeChanged(action.value))
        is AddItemScreenAction.ImagePathChanged -> push(AddItemScreenEffect.ImagePathChanged(action.value))
        is AddItemScreenAction.TimestampChanged -> push(AddItemScreenEffect.TimestampChanged(action.value))
        ClickOnBack -> push(NavigateBack)
        SaveItem -> handleSave()
        is AddItemScreenAction.BarcodeScanRequested -> handleBarcodeScan(action.imageUri)
        is AddItemScreenAction.BarcodeScanCompleted -> push(BarcodeScanResult(action.value))
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    private suspend fun handleSave() {
        val currentState = stateFlow.value
        if (!currentState.isSaveEnabled || currentState.isSaving) return

        val ensuredId = currentState.id.ifBlank { Uuid.random().toString() }
        val ensuredTimestamp = currentState.timestamp ?: kotlin.time.Clock.System.now().epochSeconds

        push(IdChanged(ensuredId))
        push(AddItemScreenEffect.TimestampChanged(ensuredTimestamp))
        push(Saving(true))

        val category = currentState.category
        val title = currentState.title.trim()
        if (category == null || title.isBlank()) {
            push(Saving(false))
            return
        }

        var item = Item(
            id = ensuredId,
            title = title,
            category = category,
            note = currentState.note.ifBlank { null },
            imagePath = currentState.imagePath,
            barcode = currentState.barcode.ifBlank { null },
            timestamp = ensuredTimestamp
        )

        try {
            item = withContext(Dispatchers.IO) { imageSaver.persist(item) }
            withContext(Dispatchers.Default) { saveItemUseCase(item) }
            push(NavigateBack)
        } catch (t: Throwable) {
            push(ShowError(t.message ?: "Unable to save item"))
        } finally {
            push(Saving(false))
        }
    }

    private suspend fun handleBarcodeScan(imageUri: String) {
        push(BarcodeScanInProgress(true))
        try {
            val result = withContext(Dispatchers.IO) { barcodeScanner.scan(imageUri) }
            when (result) {
                is ScanResult.Success -> push(BarcodeScanResult(result.value))
                ScanResult.NotFound -> push(BarcodeScanResult(null))
                is ScanResult.Error -> push(ShowError(result.message))
            }
        } catch (t: Throwable) {
            push(ShowError(t.message ?: "Scan failed"))
        } finally {
            push(BarcodeScanInProgress(false))
        }
    }
}