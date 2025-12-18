package com.coding.add_item_screen_impl.items_list.mvi

import com.coding.mvi_general.MviEvent

internal sealed interface AddItemScreenEvent : MviEvent {
    data object NavigateBack : AddItemScreenEvent
}