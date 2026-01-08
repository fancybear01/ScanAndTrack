package com.coding.add_item_screen_impl.camera

import com.coding.sat.item.domain.model.Item

/**
 * Abstraction to persist item images at save-time.
 */
interface ImageSaver {
    suspend fun persist(item: Item): Item
}

/**
 * Default no-op implementation for non-Android targets.
 */
object NoOpImageSaver : ImageSaver {
    override suspend fun persist(item: Item): Item = item
}

