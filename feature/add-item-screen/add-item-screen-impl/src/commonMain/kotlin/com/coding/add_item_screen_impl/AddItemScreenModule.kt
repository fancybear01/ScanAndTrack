package com.coding.add_item_screen_impl

import com.coding.add_item_screen_api.AddItemScreenApi
import com.coding.add_item_screen_impl.items_list.AddItemScreen
import com.coding.add_item_screen_impl.items_list.AddItemScreenModel
import com.coding.mvi_koin_voyager.provideMviModel
import org.koin.dsl.module

val addItemScreenModule
    get() = module {
        provideMviModel<AddItemScreen> { tag, _ -> AddItemScreenModel(tag) }
        single<AddItemScreenApi> { AddItemScreenImpl() }
    }