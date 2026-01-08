package com.coding.add_item_screen_impl.camera

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
            val capturedUri = uriHolder.uri
            onPhotoTaken(capturedUri?.toString())
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

fun saveImageToGallery(
    context: Context,
    bitmap: Bitmap,
    title: String
): Uri? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        saveImageToMediaStore(context, bitmap, title)
    } else {
        saveImageToExternalStorage(context, bitmap, title)
    }
}

private fun saveImageToMediaStore(
    context: Context,
    bitmap: Bitmap,
    title: String
): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "$title.jpg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/ScanAndTrack")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    return try {
        uri?.let { imageUri ->
            resolver.openOutputStream(imageUri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(imageUri, contentValues, null, null)
            }
            imageUri
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Suppress("DEPRECATION")
private fun saveImageToExternalStorage(
    context: Context,
    bitmap: Bitmap,
    title: String
): Uri? {
    val picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val appDirectory = File(picturesDirectory, "ScanAndTrack")

    if (!appDirectory.exists()) {
        appDirectory.mkdirs()
    }

    val imageFile = File(appDirectory, "$title.jpg")

    return try {
        FileOutputStream(imageFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        }

        MediaScannerConnection.scanFile(
            context,
            arrayOf(imageFile.absolutePath),
            arrayOf("image/jpeg"),
            null
        )
        Uri.fromFile(imageFile)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        if (inputStream != null) {
            bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bitmap
}