package com.coding.add_item_screen_impl.items_list.mvi

import com.coding.mvi_general.MviState
import com.coding.sat.item.domain.model.ItemCategory

internal data class AddItemScreenState(
    val draft: AddItemDraft = AddItemDraft(),
    val validationErrors: Set<AddItemValidationError> = emptySet(),
    val isSaving: Boolean = false
) : MviState {

    val isSaveEnabled: Boolean
        get() = validationErrors.isEmpty() && draft.isReadyForSave

    fun setTitle(title: String) = updateDraft { copy(title = title) }

    fun setCategory(category: ItemCategory?) = updateDraft { copy(category = category) }

    fun setNote(note: String) = updateDraft { copy(note = note) }

    fun setBarcode(barcode: String) = updateDraft { copy(barcode = barcode) }

    fun setImagePath(imagePath: String?) = updateDraft { copy(imagePath = imagePath) }

    fun setTimestamp(timestamp: Long?) = updateDraft { copy(timestamp = timestamp) }

    fun toggleSaving(isSaving: Boolean) = copy(isSaving = isSaving)

    private fun updateDraft(transform: AddItemDraft.() -> AddItemDraft): AddItemScreenState {
        val nextDraft = draft.transform()
        val nextErrors = AddItemDraftValidator.validate(nextDraft)
        return copy(draft = nextDraft, validationErrors = nextErrors)
    }

    companion object {
        val DEFAULT = AddItemScreenState()
    }
}

internal data class AddItemDraft(
    val title: String = "",
    val category: ItemCategory? = null,
    val note: String = "",
    val imagePath: String? = null,
    val barcode: String = "",
    val timestamp: Long? = null
) {
    val isReadyForSave: Boolean
        get() = title.isNotBlank() && category != null
}

internal enum class AddItemValidationError {
    EMPTY_TITLE,
    CATEGORY_NOT_SELECTED
}

private object AddItemDraftValidator {
    fun validate(draft: AddItemDraft): Set<AddItemValidationError> = buildSet {
        if (draft.title.isBlank()) add(AddItemValidationError.EMPTY_TITLE)
        if (draft.category == null) add(AddItemValidationError.CATEGORY_NOT_SELECTED)
    }
}