package com.coding.add_item_screen_impl.camera

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Vision.VNBarcodeObservation
import platform.Vision.VNDetectBarcodesRequest
import platform.Vision.VNImageRequestHandler
import platform.Vision.VNRequest
import platform.Vision.VNRequestCompletionHandler
import platform.darwin.NSObject

internal class BarcodeScannerIos : BarcodeScanner {
    @OptIn(ExperimentalForeignApi::class)
    override suspend fun scan(uri: String): ScanResult = withContext(Dispatchers.Default) {
        val url = NSURL.URLWithString(uri) ?: return@withContext ScanResult.Error("Invalid URI")
        suspendCancellableVision(url)
    }
}

@OptIn(ExperimentalForeignApi::class)
private suspend fun suspendCancellableVision(url: NSURL): ScanResult = suspendCancellableCoroutine { cont ->
    val request = VNDetectBarcodesRequest(object : NSObject(), VNRequestCompletionHandler {
        override fun invoke(request: VNRequest?, error: NSError?) {
            if (cont.isCompleted) return
            if (error != null) {
                cont.resume(
                    ScanResult.Error(
                        error.localizedDescription
                    )
                ) { cause, _, _ -> null?.let { it(cause) } }
                return
            }
            val observations = request?.results?.filterIsInstance<VNBarcodeObservation>() ?: emptyList()
            val value = observations.firstOrNull()?.payloadStringValue
            cont.resume(value?.let { ScanResult.Success(it) }
                ?: ScanResult.NotFound) { cause, _, _ -> null?.let { it(cause) } }
        }
    })

    val handler = VNImageRequestHandler(uRL = url, options = emptyMap<Any?, Any>())
    memScoped {
        val errorPtr = alloc<ObjCObjectVar<NSError?>>()
        handler.performRequests(listOf(request), error = errorPtr.ptr)
        val err = errorPtr.value
        if (err != null && !cont.isCompleted) {
            cont.resume(
                ScanResult.Error(
                    err.localizedDescription
                )
            ) { cause, _, _ -> null?.let { it(cause) } }
        }
    }
}
