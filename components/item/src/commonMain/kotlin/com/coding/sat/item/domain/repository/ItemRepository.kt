package com.coding.sat.item.domain.repository

import com.coding.sat.item.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun observeItems(): Flow<List<Item>>
    suspend fun getItem(id: String): Item
    suspend fun saveItem(item: Item)
    suspend fun deleteItem(item: Item)
}