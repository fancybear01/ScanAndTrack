package com.coding.add_item_screen_impl.items_list.mvi

import com.coding.mvi_general.MviAction

internal sealed interface AddItemScreenAction : MviAction {
    data object SaveItem : AddItemScreenAction
    data object ClickOnBack : AddItemScreenAction
}