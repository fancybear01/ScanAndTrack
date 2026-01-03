package com.coding.add_item_screen_impl.items_list.mvi

import com.coding.sat.item.domain.model.ItemCategory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AddItemScreenStateTest {

    @Test
    fun `isSaveEnabled false when title blank`() {
        val state = AddItemScreenState.DEFAULT

        assertFalse(state.isSaveEnabled)
    }

    @Test
    fun `isSaveEnabled true when title and category set`() {
        val state = AddItemScreenState.DEFAULT
            .updateTitle("Laptop")
            .updateCategory(ItemCategory.ELECTRONICS)

        assertTrue(state.isSaveEnabled)
    }

    @Test
    fun `updateCategoryInput matches enum ignoring case`() {
        val state = AddItemScreenState.DEFAULT.updateCategoryInput("electronics")

        assertEquals(ItemCategory.ELECTRONICS, state.category)
    }

    @Test
    fun `toDomainItem returns null when timestamp missing`() {
        val state = AddItemScreenState.DEFAULT
            .updateTitle("Laptop")
            .updateCategory(ItemCategory.ELECTRONICS)

        assertNull(state.toDomainItem())
    }

    @Test
    fun `toDomainItem maps fields when data complete`() {
        val state = AddItemScreenState.DEFAULT
            .updateId("item-1")
            .updateTitle("Laptop")
            .updateCategory(ItemCategory.ELECTRONICS)
            .updateNote("Personal")
            .updateBarcode("123")
            .updateImagePath("/tmp/image.png")
            .updateTimestamp(1_701_000_000L)

        val item = state.toDomainItem()

        assertNotNull(item)
        assertEquals("item-1", item.id)
        assertEquals("Laptop", item.title)
        assertEquals("Personal", item.note)
        assertEquals("123", item.barcode)
        assertEquals("/tmp/image.png", item.imagePath)
        assertEquals(1_701_000_000L, item.timestamp)
    }
}

