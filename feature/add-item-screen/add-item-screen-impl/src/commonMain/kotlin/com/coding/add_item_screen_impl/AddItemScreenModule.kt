package com.coding.add_item_screen_impl

import com.coding.add_item_screen_api.AddItemScreenApi
import com.coding.add_item_screen_impl.camera.ImageSaver
import com.coding.add_item_screen_impl.camera.NoOpImageSaver
import com.coding.add_item_screen_impl.items_list.AddItemScreen
import com.coding.add_item_screen_impl.items_list.AddItemScreenModel
import com.coding.add_item_screen_impl.camera.di.imageSaverModule
import org.koin.core.module.Module
import com.coding.mvi_koin_voyager.provideMviModel
import org.koin.dsl.module

val addItemScreenModule
    get() = module {
        val platformModule: Module? = imageSaverModule
        platformModule?.let { includes(it) }

        single<ImageSaver> { NoOpImageSaver }
        provideMviModel<AddItemScreen> { tag, params ->
            val id: String? = params.getOrNull()
            AddItemScreenModel(
                tag = tag,
                id = id,
                get(),
                get(),
                get(),
                get()
            )
        }
        single<AddItemScreenApi> { AddItemScreenImpl() }
    }