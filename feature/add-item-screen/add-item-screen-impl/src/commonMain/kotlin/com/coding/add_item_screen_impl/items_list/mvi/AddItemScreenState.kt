package com.coding.add_item_screen_impl.items_list.mvi

import com.coding.mvi_general.MviState
import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.model.ItemCategory

internal data class AddItemScreenState(
    val id: String = "",
    val title: String = "",
    val categoryInput: String = "",
    val category: ItemCategory? = null,
    val note: String = "",
    val imagePath: String? = null,
    val barcode: String = "",
    val timestamp: Long? = null,
    val validationErrors: Set<AddItemValidationError> = emptySet(),
    val isSaving: Boolean = false
) : MviState {

    val isSaveEnabled: Boolean
        get() = validationErrors.isEmpty() && title.isNotBlank() && category != null

    fun updateTitle(newTitle: String) = withValidation(copy(title = newTitle))

    fun updateCategoryInput(newCategoryInput: String): AddItemScreenState {
        val normalizedCategory = ItemCategory.entries.firstOrNull {
            it.name.equals(newCategoryInput.trim(), ignoreCase = true)
        }
        return withValidation(copy(categoryInput = newCategoryInput, category = normalizedCategory))
    }

    fun updateCategory(newCategory: ItemCategory?) = withValidation(copy(category = newCategory))

    fun updateNote(newNote: String) = copy(note = newNote)

    fun updateBarcode(newBarcode: String) = copy(barcode = newBarcode)

    fun updateImagePath(newImagePath: String?) = copy(imagePath = newImagePath)

    fun updateTimestamp(newTimestamp: Long?) = copy(timestamp = newTimestamp)

    fun updateId(newId: String) = copy(id = newId)

    fun toggleSaving(isSaving: Boolean) = copy(isSaving = isSaving)

    fun toDomainItem(): Item? =
        if (category != null && timestamp != null) {
            Item(
                id = id,
                title = title.trim(),
                category = category,
                note = note.ifBlank { null },
                imagePath = imagePath,
                barcode = barcode.ifBlank { null },
                timestamp = timestamp
            )
        } else {
            null
        }

    private fun withValidation(next: AddItemScreenState): AddItemScreenState =
        next.copy(validationErrors = AddItemValidator.validate(next))

    companion object {
        val DEFAULT = AddItemScreenState()
    }
}

internal enum class AddItemValidationError {
    EMPTY_TITLE,
    CATEGORY_NOT_SELECTED
}

private object AddItemValidator {
    fun validate(state: AddItemScreenState): Set<AddItemValidationError> = buildSet {
        if (state.title.isBlank()) add(AddItemValidationError.EMPTY_TITLE)
        if (state.category == null) add(AddItemValidationError.CATEGORY_NOT_SELECTED)
    }
}