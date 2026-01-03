package com.coding.sat.items_list_screen_impl.screen.items_list.mvi

import com.coding.mvi_general.MviState
import com.coding.sat.item.domain.model.Item
import kotlin.collections.emptyList

internal data class ItemsListScreenState(
    val items: List<Item>,
    val query: String,
    val filteredItems: List<Item>
) : MviState {

    fun setItems(items: List<Item>) = copy(
        items = items,
        filteredItems = items.filterByQuery(query)
    )

    fun updateQuery(query: String) = copy(
        query = query,
        filteredItems = items.filterByQuery(query)
    )

    private fun List<Item>.filterByQuery(query: String): List<Item> =
        if (query.isBlank()) this else filter { it.title.contains(query, ignoreCase = true) }

    companion object {
        val DEFAULT = ItemsListScreenState(
            items = emptyList(),
            query = "",
            filteredItems = emptyList()
        )
    }
}