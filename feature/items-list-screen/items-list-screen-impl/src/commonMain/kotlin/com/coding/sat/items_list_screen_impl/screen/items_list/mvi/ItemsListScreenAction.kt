package com.coding.sat.items_list_screen_impl.screen.items_list.mvi

import com.coding.mvi_general.MviAction
import com.coding.sat.item.domain.model.Item

internal sealed interface ItemsListScreenAction : MviAction {
    data class ClickOnItem(val id: Int) : ItemsListScreenAction
    data object AddItem : ItemsListScreenAction
    data class DeleteItem(val item: Item) : ItemsListScreenAction
    data class SearchQueryChanged(val query: String) : ItemsListScreenAction
}