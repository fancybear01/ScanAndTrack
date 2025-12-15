package com.coding.sat.item.domain.usecase

import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.repository.ItemRepository

class SaveItemUseCase(val repo: ItemRepository) {
    suspend operator fun invoke(item: Item) = repo.saveItem(item)
}