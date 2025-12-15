package com.coding.sat.item.domain.usecase

import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.repository.ItemRepository

class GetItemUseCase(val repo: ItemRepository) {
    suspend operator fun invoke(id: String): Item? = repo.getItem(id)
}