package com.coding.sat.item.data.mapper

import com.coding.sat.database.table.item.ItemEntity
import com.coding.sat.item.domain.model.Item
import com.coding.sat.item.domain.model.ItemCategory

fun ItemEntity.toDomain(): Item = Item(
    id = id,
    title = title,
    category = ItemCategory.valueOf(category),
    note = note,
    imagePath = imagePath,
    barcode = barcode,
    timestamp = timestamp
)

fun Item.toEntity(): ItemEntity = ItemEntity(
    id = id,
    title = title,
    category = category.name,
    note = note,
    imagePath = imagePath,
    barcode = barcode,
    timestamp = timestamp
)