package com.coding.add_item_screen_impl.camera

sealed class ScanResult {
    data class Success(val value: String) : ScanResult()
    object NotFound : ScanResult()
    data class Error(val message: String) : ScanResult()
}