package com.coding.sat.items_list_screen_impl

import com.coding.items_list_screen_api.ItemsListScreenApi
import com.coding.mvi_koin_voyager.provideMviModel
import com.coding.sat.items_list_screen_impl.screen.items_list.ItemsListScreen
import com.coding.sat.items_list_screen_impl.screen.items_list.ItemsListScreenModel
import org.koin.dsl.module

val itemsListScreenModule
    get() = module {
        provideMviModel<ItemsListScreen> { tag, _ -> ItemsListScreenModel(tag, get()) }
        single<ItemsListScreenApi> { ItemsListScreenImpl() }
    }