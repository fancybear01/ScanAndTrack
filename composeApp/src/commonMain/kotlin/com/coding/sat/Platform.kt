package com.coding.sat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform