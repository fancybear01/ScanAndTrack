package com.coding.sat.items_list_screen_impl.screen.items_list.mvi

import com.coding.mvi_general.MviEffect
import com.coding.sat.item.domain.model.Item

internal sealed interface ItemsListScreenEffect : MviEffect {
    data class GetItems(val items: List<Item>) : ItemsListScreenEffect
}