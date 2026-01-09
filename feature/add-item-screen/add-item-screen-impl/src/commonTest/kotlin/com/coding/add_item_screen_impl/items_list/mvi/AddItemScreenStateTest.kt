package com.coding.add_item_screen_impl.items_list.mvi

import com.coding.sat.item.domain.model.ItemCategory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame
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

    @Test
    fun `scanning barcode`() {
        val state = AddItemScreenState.DEFAULT
            .updateBarcodeScanProgress(true)
            .applyScannedBarcode("987654")

        assertTrue(state.isScanningBarcode)
        assertEquals("987654", state.barcode)
    }

    @Test
    fun `applyScannedBarcode ignores null`() {
        val state = AddItemScreenState.DEFAULT
        val result = state.applyScannedBarcode(null)

        assertSame(state, result)
        assertEquals("", result.barcode)
    }

    @Test
    fun `validationErrors include title and category`() {
        val state = AddItemScreenState.DEFAULT

        assertTrue(state.validationErrors.contains(AddItemValidationError.EMPTY_TITLE))
        assertTrue(state.validationErrors.contains(AddItemValidationError.CATEGORY_NOT_SELECTED))
        assertFalse(state.isSaveEnabled)
    }

    @Test
    fun `validation clears when title and category set`() {
        val state = AddItemScreenState.DEFAULT
            .updateTitle("  Laptop ")
            .updateCategory(ItemCategory.ELECTRONICS)

        assertTrue(state.validationErrors.isEmpty())
        assertTrue(state.isSaveEnabled)
    }

    @Test
    fun `invalid category input does not set category`() {
        val state = AddItemScreenState.DEFAULT.updateCategoryInput("invalid-category")

        assertNull(state.category)
        assertTrue(state.validationErrors.contains(AddItemValidationError.CATEGORY_NOT_SELECTED))
    }

    @Test
    fun `blank note and barcode are mapped to null in domain`() {
        val state = AddItemScreenState.DEFAULT
            .updateId("item-2")
            .updateTitle("  Tablet ")
            .updateCategory(ItemCategory.ELECTRONICS)
            .updateNote("   ")
            .updateBarcode("   ")
            .updateImagePath("/tmp/photo.png")
            .updateTimestamp(123L)

        val item = state.toDomainItem()

        assertNotNull(item)
        assertEquals("item-2", item.id)
        assertEquals("Tablet", item.title)
        assertNull(item.note)
        assertNull(item.barcode)
        assertEquals("/tmp/photo.png", item.imagePath)
        assertEquals(123L, item.timestamp)
    }
}
