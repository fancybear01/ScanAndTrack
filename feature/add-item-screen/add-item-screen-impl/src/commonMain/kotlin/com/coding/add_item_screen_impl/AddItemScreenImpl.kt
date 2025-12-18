package com.coding.add_item_screen_impl

import cafe.adriel.voyager.core.screen.Screen
import com.coding.items_list_screen_api.ItemsListScreenApi
import com.coding.sat.items_list_screen_impl.screen.items_list.ItemsListScreen

internal class AddItemScreenImpl : ItemsListScreenApi {
    override fun itemsListScreen(): Screen = ItemsListScreen()
}