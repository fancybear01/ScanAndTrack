package com.coding.add_item_screen_impl

import cafe.adriel.voyager.core.screen.Screen
import com.coding.add_item_screen_api.AddItemScreenApi
import com.coding.add_item_screen_impl.items_list.AddItemScreen

internal class AddItemScreenImpl : AddItemScreenApi {
    override fun addItemScreen(id: String?): Screen = AddItemScreen(id = id)
}