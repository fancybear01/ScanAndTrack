package com.coding.add_item_screen_impl.items_list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.coding.add_item_screen_impl.items_list.compose.AddItemScreenContent
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenAction
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenEvent
import com.coding.add_item_screen_impl.items_list.mvi.AddItemScreenState
import com.coding.mvi_koin_voyager.MviView
import com.coding.mvi_koin_voyager.collectEvent
import kotlinx.coroutines.flow.Flow

internal class AddItemScreen :
    MviView<AddItemScreenAction, AddItemScreenEvent, AddItemScreenState> {

    @Composable
    override fun content(
        state: AddItemScreenState,
        eventFlow: Flow<AddItemScreenEvent>,
        pushAction: (AddItemScreenAction) -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow

        eventFlow.collectEvent { event ->
            when (event) {
                is AddItemScreenEvent.NavigateBack ->
                    navigator.pop()
            }
        }

        AddItemScreenContent(
            clickOnBack = {
                pushAction(AddItemScreenAction.ClickOnBack)
            }
        )
    }
}