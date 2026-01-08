package com.coding.add_item_screen_impl

import com.coding.add_item_screen_api.AddItemScreenApi
import com.coding.add_item_screen_impl.camera.ImageSaver
import com.coding.add_item_screen_impl.camera.NoOpImageSaver
import com.coding.add_item_screen_impl.items_list.AddItemScreen
import com.coding.add_item_screen_impl.items_list.AddItemScreenModel
import com.coding.mvi_koin_voyager.provideMviModel
import org.koin.dsl.module

val addItemScreenModule
    get() = module {
        single<ImageSaver> { NoOpImageSaver }
        provideMviModel<AddItemScreen> { tag, _ ->
            AddItemScreenModel(
                tag,
                get(),
                get(),
                get()
            )
        }
        single<AddItemScreenApi> { AddItemScreenImpl() }
    }