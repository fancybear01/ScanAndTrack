package com.coding.sat

import com.coding.sat.database.databaseModule
import com.coding.sat.item.di.itemModule
import com.coding.sat.items_list_screen_impl.itemsListScreenModule

private val coreModules
    get() = listOf(
        databaseModule,
    )

private val componentsModules
    get() = listOf(
        itemModule,
    )

private val featureModules
    get() = listOf(
        itemsListScreenModule,
    )

val appModules
    get() = listOf(
        coreModules,
        componentsModules,
        featureModules,
    ).flatten()