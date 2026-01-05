package com.coding.add_item_screen_impl.camera

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
internal actual fun rememberCameraPicker(
    onPhotoTaken: (String?) -> Unit,
    onError: (Throwable) -> Unit
): CameraPicker {
    val context = LocalContext.current

    val uriHolder = remember { UriHolder() }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            onPhotoTaken(uriHolder.uri?.toString())
        } else {
            onPhotoTaken(null)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createImageUri(context)
            uriHolder.uri = uri
            takePictureLauncher.launch(uri)
        } else {
            onPhotoTaken(null)
        }
    }

    return remember {
        object : CameraPicker {
            override fun takePhoto() {
                try {
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                } catch (t: Throwable) {
                    onError(t)
                }
            }
        }
    }
}

private class UriHolder {
    var uri: Uri? = null
}

private fun createImageUri(context: Context): Uri {
    val imagesDir = File(context.cacheDir, "images").apply { mkdirs() }
    val imageFile = File.createTempFile("sat_", ".jpg", imagesDir)

    val authority = context.packageName + ".fileprovider"
    return FileProvider.getUriForFile(context, authority, imageFile)
}
