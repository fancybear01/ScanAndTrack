package com.coding.sat.items_list_screen_impl.screen.items_list.mvi

import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.model.ItemCategory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ItemsListScreenStateSortTest {

    private val items = listOf(
        Item("1", "B", ItemCategory.ELECTRONICS, null, null, null, 100L),
        Item("2", "A", ItemCategory.CLOTHES, null, null, null, 200L),
        Item("3", "C", ItemCategory.ELECTRONICS, null, null, null, 150L)
    )

    @Test
    fun `filter keeps sort by timestamp desc`() {
        val state = ItemsListScreenState.DEFAULT.copy(items = items, filteredItems = items)
        val next = state.updateQuery("")

        val expected = items.sortedByDescending { it.timestamp }
        assertEquals(expected, next.filteredItems)
    }

    @Test
    fun `query filters then sorts`() {
        val state = ItemsListScreenState.DEFAULT.copy(items = items, filteredItems = items)

        val next = state.updateQuery("c")

        val expected = listOf(items[2])
        assertEquals(expected, next.filteredItems)
    }
}

