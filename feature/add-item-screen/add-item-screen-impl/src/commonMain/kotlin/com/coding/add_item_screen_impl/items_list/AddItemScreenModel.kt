package com.coding.add_item_screen_impl.items_list

import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenAction
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEffect
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEvent
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenState
import com.coding.mvi_koin_voyager.MviModel

internal class AddItemScreenModel(
    tag: String
) : MviModel<AddItemScreenAction, AddItemScreenEffect, AddItemScreenEvent, AddItemScreenState>(
    defaultState = AddItemScreenState.DEFAULT,
    tag = tag,
) {

//    override fun reducer(
//        effect: AddItemScreenEffect,
//        previousState: AddItemScreenState
//    ): AddItemScreenState = when(effect) {
//
//    }

    override suspend fun actor(action: AddItemScreenAction) =
        when (action) {
            is AddItemScreenAction.SaveItem ->
                push(AddItemScreenEvent.NavigateBack)
            is AddItemScreenAction.ClickOnBack ->
                push(AddItemScreenEvent.NavigateBack)
        }
}