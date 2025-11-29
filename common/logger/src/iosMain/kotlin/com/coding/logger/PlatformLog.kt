package com.coding.logger

actual fun platformLog(tag: String, message: String) {
    println("$tag $message")
}