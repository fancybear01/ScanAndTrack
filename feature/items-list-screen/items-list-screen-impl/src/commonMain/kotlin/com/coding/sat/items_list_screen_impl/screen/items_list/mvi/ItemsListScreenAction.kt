package com.coding.sat.items_list_screen_impl.screen.items_list.mvi

import com.coding.mvi_general.MviAction

internal sealed interface ItemsListScreenAction : MviAction {
    data class ClickOnItem(val id: Int) : ItemsListScreenAction
}