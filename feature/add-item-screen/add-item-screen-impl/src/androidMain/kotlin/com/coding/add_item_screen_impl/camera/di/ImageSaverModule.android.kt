package com.coding.add_item_screen_impl.camera.di

import com.coding.add_item_screen_impl.camera.AndroidImageSaver
import com.coding.add_item_screen_impl.camera.ImageSaver
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

actual val imageSaverModule: Module? = module {
    single<ImageSaver> { AndroidImageSaver(androidContext()) }
}
