package com.coding.sat.database.table.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {
    @Query("SELECT * FROM items ORDER BY timestamp DESC")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getById(id: String): ItemEntity

    @Insert
    suspend fun insert(item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)
}