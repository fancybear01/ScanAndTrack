package com.coding.add_item_screen_impl.items_list

import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenAction
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEffect
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEvent
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenState
import com.coding.mvi_koin_voyager.MviModel
import com.coding.sat.item.domain.usecase.GetItemsUseCase

internal class AddItemScreenModel(
    tag: String,
    private val getItemsUseCase: GetItemsUseCase
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
        }
}