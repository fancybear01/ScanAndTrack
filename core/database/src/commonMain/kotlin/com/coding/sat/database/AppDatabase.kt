package com.coding.sat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coding.sat.database.table.item.ItemEntity
import com.coding.sat.database.table.item.ItemsDao

@Database(
    entities = [
        ItemEntity::class,
    ],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogsDao(): ItemsDao
}

private const val DATABASE_VERSION = 1