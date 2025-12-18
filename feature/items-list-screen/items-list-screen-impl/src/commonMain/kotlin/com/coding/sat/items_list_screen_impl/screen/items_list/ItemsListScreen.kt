package com.coding.sat.items_list_screen_impl.screen.items_list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.coding.add_item_screen_api.AddItemScreenApi
import com.coding.mvi_koin_voyager.MviView
import com.coding.mvi_koin_voyager.collectEvent
import com.coding.sat.items_list_screen_impl.screen.items_list.compose.ItemsListScreenContent
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenAction
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenEvent
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenState
import kotlinx.coroutines.flow.Flow
import org.koin.compose.koinInject

internal class ItemsListScreen :
    MviView<ItemsListScreenAction, ItemsListScreenEvent, ItemsListScreenState> {

    @Composable
    override fun content(
        state: ItemsListScreenState,
        eventFlow: Flow<ItemsListScreenEvent>,
        pushAction: (ItemsListScreenAction) -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val addItemScreenApi = koinInject<AddItemScreenApi>()
        eventFlow.collectEvent { event ->
            when (event) {
                is ItemsListScreenEvent.NavigateToItemDetails ->
                    TODO()

                ItemsListScreenEvent.NavigateToAddItem ->
                    navigator.push(addItemScreenApi.addItemScreen())
            }
        }

        ItemsListScreenContent(
            items = state.items,
            onFabClick = {
                pushAction(ItemsListScreenAction.AddItem)
            }
        )
    }
}