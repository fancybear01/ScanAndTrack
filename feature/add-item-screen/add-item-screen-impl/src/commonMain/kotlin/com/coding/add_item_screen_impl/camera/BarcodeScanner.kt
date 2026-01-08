package com.coding.add_item_screen_impl.camera

interface BarcodeScanner {
    suspend fun scan(uri: String): ScanResult
}