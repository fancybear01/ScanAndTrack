package com.coding.add_item_screen_impl.camera

import android.content.Context
import com.coding.sat.item.domain.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.net.toUri

internal class AndroidImageSaver(
    private val appContext: Context
) : ImageSaver {
    override suspend fun persist(item: Item): Item = withContext(Dispatchers.IO) {
        val path = item.imagePath ?: return@withContext item
        val uri = runCatching { path.toUri() }.getOrNull() ?: return@withContext item
        val bitmap = uriToBitmap(appContext, uri) ?: return@withContext item
        val savedUri = saveImageToGallery(appContext, bitmap, "sat_${System.currentTimeMillis()}")
        if (savedUri != null) item.copy(imagePath = savedUri.toString()) else item
    }
}

