package com.coding.add_item_screen_impl.items_list.mvi

import com.coding.mvi_general.MviAction
import com.coding.sat.item.domain.model.ItemCategory

internal sealed interface AddItemScreenAction : MviAction {
    data class TitleChanged(val value: String) : AddItemScreenAction
    data class CategoryInputChanged(val value: String) : AddItemScreenAction
    data class CategorySelected(val value: ItemCategory?) : AddItemScreenAction
    data class NoteChanged(val value: String) : AddItemScreenAction
    data class BarcodeChanged(val value: String) : AddItemScreenAction
    data class ImagePathChanged(val value: String?) : AddItemScreenAction
    data class TimestampChanged(val value: Long?) : AddItemScreenAction
    data object ClickOnBack : AddItemScreenAction
    data object SaveItem : AddItemScreenAction
}