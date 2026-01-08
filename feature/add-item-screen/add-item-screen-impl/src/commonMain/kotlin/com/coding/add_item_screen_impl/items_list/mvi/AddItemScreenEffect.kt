package com.coding.add_item_screen_impl.items_list.mvi

import com.coding.mvi_general.MviEffect
import com.coding.sat.item.domain.model.ItemCategory

internal sealed interface AddItemScreenEffect : MviEffect {
    data class TitleChanged(val value: String) : AddItemScreenEffect
    data class CategoryInputChanged(val value: String) : AddItemScreenEffect
    data class CategorySelected(val value: ItemCategory?) : AddItemScreenEffect
    data class NoteChanged(val value: String) : AddItemScreenEffect
    data class BarcodeChanged(val value: String) : AddItemScreenEffect
    data class ImagePathChanged(val value: String?) : AddItemScreenEffect
    data class TimestampChanged(val value: Long?) : AddItemScreenEffect
    data class IdChanged(val value: String) : AddItemScreenEffect
    data class Saving(val value: Boolean) : AddItemScreenEffect
    data class BarcodeScanInProgress(val value: Boolean) : AddItemScreenEffect
    data class BarcodeScanResult(val value: String?) : AddItemScreenEffect
}