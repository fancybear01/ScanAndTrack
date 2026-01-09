package com.coding.add_item_screen_impl.camera.di

import com.coding.add_item_screen_impl.camera.BarcodeScanner
import com.coding.add_item_screen_impl.camera.BarcodeScannerIos
import com.coding.add_item_screen_impl.camera.ImageSaver
import com.coding.add_item_screen_impl.camera.NoOpImageSaver
import org.koin.core.module.Module
import org.koin.dsl.module

actual val imageSaverModule: Module? = module {
    single<ImageSaver> { NoOpImageSaver }
    single<BarcodeScanner> { BarcodeScannerIos() }
}

