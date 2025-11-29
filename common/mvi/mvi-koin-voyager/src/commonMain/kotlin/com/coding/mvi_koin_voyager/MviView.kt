package com.coding.mvi_koin_voyager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.coding.common.utils.simpleNameOrThrow
import com.coding.mvi_general.MviAction
import com.coding.mvi_general.MviEvent
import com.coding.mvi_general.MviState
import kotlinx.coroutines.flow.Flow
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

interface MviView<Action : MviAction, Event : MviEvent, State : MviState> : Screen {

    // Новый интерфейс: если экран реализует его, параметры будут переданы в модель
    interface ParamsProvider {
        fun modelParams(): Array<Any>
    }

    @Composable
    override fun Content() {
        val tag = this::class.simpleNameOrThrow
        @Suppress("UNCHECKED_CAST")
        val model = if (this is ParamsProvider) {
            getScreenModel<MviModel<Action, *, Event, State>>(qualifier = named(tag), parameters = { parametersOf(*modelParams()) })
        } else {
            getScreenModel<MviModel<Action, *, Event, State>>(qualifier = named(tag))
        }
        val state by model.stateFlow.collectAsState()
        content(
            state = state,
            eventFlow = model.eventFlow,
            pushAction = model::push
        )
    }

    @Composable
    fun content(
        state: State,
        eventFlow: Flow<Event>,
        pushAction: (Action) -> Unit,
    )
}

@Composable
private inline fun <reified T : MviModel<*, *, *, *>> MviView<*, *, *>.getMviModel(): T =
    getScreenModel<T>(qualifier = named(this::class.simpleNameOrThrow))

@Composable
inline fun <reified Event : MviEvent> Flow<Event>.collectEvent(
    crossinline onEvent: suspend (Event) -> Unit,
) = LaunchedEffect(Unit) { collect { onEvent(it) } }