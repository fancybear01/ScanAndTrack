package com.coding.add_item_screen_impl.camera

import androidx.compose.runtime.Composable

/**
 * Platform-specific camera launcher.
 *
 * Contract:
 * - call [takePhoto] to start capturing a photo
 * - when photo is ready, [onPhotoTaken] is called with an URI as String (or null on cancel/failure)
 */
internal interface CameraPicker {
    fun takePhoto()
}

@Composable
internal expect fun rememberCameraPicker(
    onPhotoTaken: (String?) -> Unit,
    onError: (Throwable) -> Unit = {}
): CameraPicker