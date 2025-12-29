package com.coding.add_item_screen_impl.items_list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
        val snackbarHostState = remember { SnackbarHostState() }

        eventFlow.collectEvent { event ->
            when (event) {
                is AddItemScreenEvent.NavigateBack ->
                    navigator.pop()
                is AddItemScreenEvent.ShowError -> snackbarHostState.showSnackbar(
                    message = event.message
                )
            }
        }

        AddItemScreenContent(
            state = state,
            onAction = pushAction,
            clickOnBack = {
                pushAction(AddItemScreenAction.ClickOnBack)
            }
        )
    }
}