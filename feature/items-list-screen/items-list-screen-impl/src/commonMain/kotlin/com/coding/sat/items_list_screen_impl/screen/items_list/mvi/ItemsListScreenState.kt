package com.coding.sat.items_list_screen_impl.screen.items_list.mvi

import com.coding.mvi_general.MviState
import com.coding.sat.item.domain.model.Item

internal data class ItemsListScreenState(
    val items: List<Item>
) : MviState {

    fun setItems(items: List<Item>) = copy(
        items = items
    )

    companion object {
        val DEFAULT = ItemsListScreenState(
            items = emptyList()
        )
    }
}