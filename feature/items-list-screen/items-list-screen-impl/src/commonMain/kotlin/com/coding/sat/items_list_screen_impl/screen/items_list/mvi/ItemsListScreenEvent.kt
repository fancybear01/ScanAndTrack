package com.coding.sat.items_list_screen_impl.screen.items_list.mvi

import com.coding.mvi_general.MviEvent

internal sealed interface ItemsListScreenEvent : MviEvent {
    data class NavigateToItemDetails(val id: Int) : ItemsListScreenEvent
    data object NavigateToAddItem : ItemsListScreenEvent
}