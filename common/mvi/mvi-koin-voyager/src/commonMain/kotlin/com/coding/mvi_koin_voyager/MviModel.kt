package com.coding.mvi_koin_voyager

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.coding.common.utils.simpleNameOrThrow
import com.coding.mvi_general.Mvi
import com.coding.mvi_general.MviAction
import com.coding.mvi_general.MviEffect
import com.coding.mvi_general.MviEvent
import com.coding.mvi_general.MviState
import com.coding.mvi_general.createMvi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

abstract class MviModel<Action : MviAction, Effect : MviEffect, Event : MviEvent, State : MviState>(
    defaultState: State,
    tag: String,
) : ScreenModel, Mvi<Action, Effect, Event, State> {

    private val mvi: Mvi<Action, Effect, Event, State> by lazy {
        createMvi(
            tag = tag,
            defaultState = defaultState,
            scope = screenModelScope,
            dispatcher = Dispatchers.Default,
            reducer = ::reducer,
            actor = ::actor,
            bootstrap = ::bootstrap,
        )
    }

    final override val eventFlow: Flow<Event> get() = mvi.eventFlow
    final override val stateFlow: StateFlow<State> get() = mvi.stateFlow
    final override fun push(action: Action) = mvi.push(action)
    final override fun push(event: Event) = mvi.push(event)
    final override fun push(effect: Effect) = mvi.push(effect)
    protected open suspend fun bootstrap() = run { }
    protected open suspend fun actor(action: Action) = run { }
    protected open fun reducer(effect: Effect, previousState: State): State = previousState
}

inline fun <reified T : MviView<*, *, *>> Module.provideMviModel(
    crossinline factoryMviModel: Scope.(tag: String, params: ParametersHolder) -> MviModel<*, *, *, *>,
): KoinDefinition<MviModel<*, *, *, *>> = T::class.simpleNameOrThrow.let { tag ->
    factory<MviModel<*, *, *, *>>(named(tag)) { params -> factoryMviModel(tag, params) }
}