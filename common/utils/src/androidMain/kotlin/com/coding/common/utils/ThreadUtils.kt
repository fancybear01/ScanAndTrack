package com.coding.common.utils

actual object ThreadUtils {
    actual var getThreadName: () -> String = { Thread.currentThread().name }
}