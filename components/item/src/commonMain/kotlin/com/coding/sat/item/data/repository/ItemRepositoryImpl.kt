package com.coding.sat.item.data.repository

import com.coding.sat.database.table.item.ItemsDao
import com.coding.sat.item.data.mapper.toDomain
import com.coding.sat.item.data.mapper.toEntity
import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.model.ItemCategory
import com.coding.sat.item.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class ItemRepositoryImpl(
    val dao: ItemsDao
) : ItemRepository {
    private val mockItems = listOf(
        Item("item-1", "Laptop", ItemCategory.ELECTRONICS, "Work device", null, "ABC123", 1_701_000_000L),
        Item("item-2", "Jacket", ItemCategory.CLOTHES, "Winter coat", null, "DEF456", 1_701_000_100L),
        Item("item-3", "Passport", ItemCategory.DOCUMENTS, "Travel", null, "GHI789", 1_701_000_200L),
        Item("item-4", "Drill", ItemCategory.TOOLS, "Garage", null, "JKL012", 1_701_000_300L),
        Item("item-5", "Vacuum", ItemCategory.HOME, "Living room", null, "MNO345", 1_701_000_400L)
    )

    override fun observeItems(): Flow<List<Item>> =
        flowOf(mockItems)
//        dao.getAllItems().map { list ->
//            list.map { it.toDomain() }
//        }

    override suspend fun getItem(id: String): Item? =
        dao.getById(id)?.toDomain()

    override suspend fun saveItem(item: Item) {
        dao.insert(item.toEntity())
    }

    override suspend fun deleteItem(item: Item) {
        dao.delete(item.toEntity())
    }
}