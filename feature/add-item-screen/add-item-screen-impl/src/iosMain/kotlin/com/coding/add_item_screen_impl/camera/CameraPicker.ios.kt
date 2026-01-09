package com.coding.add_item_screen_impl.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.uikit.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.StableRef
import platform.Foundation.NSData
import platform.Foundation.NSUUID
import platform.Foundation.NSURL
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.writeToFile
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.objc.objc_setAssociatedObject
import platform.objc.OBJC_ASSOCIATION_RETAIN_NONATOMIC

@Composable
internal actual fun rememberCameraPicker(
    onPhotoTaken: (String?) -> Unit,
    onError: (Throwable) -> Unit
): CameraPicker {
    val controller = LocalUIViewController.current

    return remember(controller) {
        object : CameraPicker {
            override fun takePhoto() {
                if (controller == null) {
                    onError(IllegalStateException("No UIViewController available"))
                    return
                }
                if (!UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)) {
                    onError(UnsupportedOperationException("Camera not available on this device"))
                    return
                }
                launchPicker(controller, onPhotoTaken, onError)
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun launchPicker(
    host: UIViewController,
    onPhotoTaken: (String?) -> Unit,
    onError: (Throwable) -> Unit
) {
    dispatch_async(dispatch_get_main_queue()) {
        val picker = UIImagePickerController().apply {
            sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            allowsEditing = false
        }

        val delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
            override fun imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>) {
                val image = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
                val path = image?.let { saveTempImage(it) }
                onPhotoTaken(path)
                picker.dismissViewControllerAnimated(true, null)
            }

            override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                onPhotoTaken(null)
                picker.dismissViewControllerAnimated(true, null)
            }
        }

        val delegateKey = StableRef.create("sat_picker_delegate").asCPointer()

        picker.delegate = delegate
        objc_setAssociatedObject(picker, delegateKey, delegate, OBJC_ASSOCIATION_RETAIN_NONATOMIC)

        try {
            host.presentViewController(picker, true, null)
        } catch (t: Throwable) {
            onError(t)
        }
    }
}

private fun saveTempImage(image: UIImage): String? {
    val data: NSData? = UIImageJPEGRepresentation(image, 0.9)
    val filename = "sat_${NSUUID().UUIDString}.jpg"
    val tempDir = NSTemporaryDirectory()
    val fullPath = tempDir + filename
    return if (data != null && data.writeToFile(fullPath, true)) {
        NSURL.fileURLWithPath(fullPath).absoluteString
    } else {
        null
    }
}
