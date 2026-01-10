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
    val validationErrors: Set<AddItemValidationError> = AddItemValidator.validateInitial(title, category),
    val isSaving: Boolean = false,
    val isScanningBarcode: Boolean = false,
    val isEditingItem: Boolean = false
) : MviState {

    val isSaveEnabled: Boolean
        get() = title.isNotBlank() && category != null

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
    fun updateBarcodeScanProgress(inProgress: Boolean) = copy(isScanningBarcode = inProgress)
    fun applyScannedBarcode(value: String?) = if (value != null) copy(barcode = value) else this

    fun updateImagePath(newImagePath: String?) = copy(imagePath = newImagePath)

    fun updateTimestamp(newTimestamp: Long?) = copy(timestamp = newTimestamp)

    fun updateId(newId: String) = copy(id = newId)

    fun toggleSaving(isSaving: Boolean) = copy(isSaving = isSaving)

    // Re-run validation after bulk updates (e.g., loading existing item)
    fun revalidate(): AddItemScreenState = copy(validationErrors = AddItemValidator.validate(this))

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

    fun validateInitial(title: String, category: ItemCategory?): Set<AddItemValidationError> = buildSet {
        if (title.isBlank()) add(AddItemValidationError.EMPTY_TITLE)
        if (category == null) add(AddItemValidationError.CATEGORY_NOT_SELECTED)
    }
}