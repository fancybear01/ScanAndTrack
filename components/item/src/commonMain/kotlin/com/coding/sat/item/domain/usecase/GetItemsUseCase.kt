package com.coding.sat.item.domain.usecase

import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class GetItemsUseCase(val repo: ItemRepository) {
    operator fun invoke(): Flow<List<Item>> = repo.observeItems()
}
