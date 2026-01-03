package com.coding.sat.items_list_screen_impl.screen.items_list

import cafe.adriel.voyager.core.model.screenModelScope
import com.coding.mvi_koin_voyager.MviModel
import com.coding.sat.item.domain.usecase.DeleteItemUseCase
import com.coding.sat.item.domain.usecase.GetItemsUseCase
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenAction
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenEffect
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenEvent
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenEvent.*
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class ItemsListScreenModel(
    tag: String,
    private val getItemsUseCase: GetItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : MviModel<ItemsListScreenAction, ItemsListScreenEffect, ItemsListScreenEvent, ItemsListScreenState>(
    defaultState = ItemsListScreenState.DEFAULT,
    tag = tag,
) {
    override suspend fun bootstrap() {
        getItemsUseCase()
            .onEach { items ->
                push(ItemsListScreenEffect.GetItems(items = items))
            }
            .launchIn(screenModelScope)
    }

    override fun reducer(
        effect: ItemsListScreenEffect,
        previousState: ItemsListScreenState
    ): ItemsListScreenState = when(effect) {
        is ItemsListScreenEffect.GetItems -> previousState.setItems(items = effect.items)
    }

    override suspend fun actor(action: ItemsListScreenAction) =
        when (action) {
            is ItemsListScreenAction.ClickOnItem ->
                push(NavigateToItemDetails(action.id))

            ItemsListScreenAction.AddItem ->
                push(NavigateToAddItem)

            is ItemsListScreenAction.DeleteItem ->
                deleteItemUseCase(action.item)
        }
}