package com.coding.sat.items_list_screen_impl.screen.items_list.mvi

import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.model.ItemCategory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ItemsListScreenStateTest {

    private val items = listOf(
        Item("1", "Laptop", ItemCategory.ELECTRONICS, null, null, null, 1L),
        Item("2", "Phone", ItemCategory.ELECTRONICS, null, null, null, 2L),
        Item("3", "Jacket", ItemCategory.CLOTHES, null, null, null, 3L)
    )

    @Test
    fun `setItems filters by current query`() {
        val state = ItemsListScreenState.DEFAULT.updateQuery("lap")

        val next = state.setItems(items)

        assertEquals(listOf(items[0]), next.filteredItems)
    }

    @Test
    fun `updateQuery filters over current items`() {
        val state = ItemsListScreenState.DEFAULT.setItems(items)

        val next = state.updateQuery("phone")

        assertEquals(listOf(items[1]), next.filteredItems)
    }

    @Test
    fun `filterByQuery returns all when query blank`() {
        val state = ItemsListScreenState.DEFAULT.setItems(items)

        val next = state.updateQuery("")

        assertTrue(next.filteredItems.containsAll(items))
        assertEquals(items.size, next.filteredItems.size)
    }
}

