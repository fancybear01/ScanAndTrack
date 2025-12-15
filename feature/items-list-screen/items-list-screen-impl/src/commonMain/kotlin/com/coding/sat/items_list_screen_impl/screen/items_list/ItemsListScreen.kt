package com.coding.sat.items_list_screen_impl.screen.items_list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.coding.mvi_koin_voyager.MviView
import com.coding.mvi_koin_voyager.collectEvent
import com.coding.sat.items_list_screen_impl.screen.items_list.compose.ItemsListScreenContent
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenAction
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenEvent
import com.coding.sat.items_list_screen_impl.screen.items_list.mvi.ItemsListScreenState
import kotlinx.coroutines.flow.Flow

internal class ItemsListScreen :
    MviView<ItemsListScreenAction, ItemsListScreenEvent, ItemsListScreenState> {

    @Composable
    override fun content(
        state: ItemsListScreenState,
        eventFlow: Flow<ItemsListScreenEvent>,
        pushAction: (ItemsListScreenAction) -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow

        eventFlow.collectEvent { event ->
            when (event) {
                is ItemsListScreenEvent.NavigateToItemDetails ->
                    TODO()
            }
        }

        ItemsListScreenContent()
    }
}