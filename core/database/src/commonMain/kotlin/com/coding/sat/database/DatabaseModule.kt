package com.coding.sat.database

import com.coding.sat.database.table.item.ItemsDao

val databaseModule
    get() = platformDatabaseModule(fileName = "database.db")
        .apply {
            single<ItemsDao> { get<AppDatabase>().dogsDao() }
        }