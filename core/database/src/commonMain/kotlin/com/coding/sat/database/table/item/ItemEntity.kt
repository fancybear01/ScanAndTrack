package com.coding.sat.database.table.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val note: String?,
    val imagePath: String?,
    val barcode: String?,
    val timestamp: Long
)