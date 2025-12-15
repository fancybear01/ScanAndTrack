package com.coding.sat.item.data.repository

import com.coding.sat.database.table.item.ItemsDao
import com.coding.sat.item.data.mapper.toDomain
import com.coding.sat.item.data.mapper.toEntity
import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ItemRepositoryImpl(
    val dao: ItemsDao
) : ItemRepository {
    override fun observeItems(): Flow<List<Item>> =
        dao.getAllItems().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun getItem(id: String): Item? =
        dao.getById(id)?.toDomain()

    override suspend fun saveItem(item: Item) {
        dao.insert(item.toEntity())
    }

    override suspend fun deleteItem(item: Item) {
        dao.delete(item.toEntity())
    }
}