package com.coding.sat.item.domain.model

data class Item(
    val id: String,
    val title: String,
    val category: ItemCategory,
    val note: String?,
    val imagePath: String?,
    val barcode: String?,
    val timestamp: Long
)