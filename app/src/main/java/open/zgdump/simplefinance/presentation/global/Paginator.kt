package open.zgdump.simplefinance.presentation.global

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import open.zgdump.simplefinance.util.android.swap

object Paginator {

    sealed class State {
        object Empty : State()
        object EmptyProgress : State()
        data class EmptyError(val error: Throwable) : State()
        data class Data<T>(val pageCount: Int, val data: List<T>) : State()
        data class Refresh<T>(val pageCount: Int, val data: List<T>) : State()
        data class NewPageProgress<T>(val pageCount: Int, val data: List<T>) : State()
        data class FullData<T>(val pageCount: Int, val data: List<T>) : State()
    }

    sealed class Action {
        object Refresh : Action()
        object Restart : Action()
        object LoadMore : Action()
        data class NewPage<T>(val pageNumber: Int, val items: List<T>) : Action()
        data class Update<T>(val item: T, val index: Int) : Action()
        data class Move(val from: Int, val to: Int) : Action()
        data class Remove(val index: Int) : Action()
        data class Insert<T>(val item: T) : Action()
        data class PageError(val error: Throwable) : Action()
    }

    sealed class SideEffect {
        data class LoadPage(val currentPage: Int) : SideEffect()
        data class ErrorEvent(val error: Throwable) : SideEffect()
    }

    private fun <T> reducer(
        action: Action,
        state: State,
        sideEffectListener: (SideEffect) -> Unit
    ): State = when (action) {
        is Action.Refresh -> {
            sideEffectListener(SideEffect.LoadPage(1))
            when (state) {
                is State.Empty -> State.EmptyProgress
                is State.EmptyError -> State.EmptyProgress
                is State.Data<*> -> State.Refresh(state.pageCount, state.data as List<T>)
                is State.NewPageProgress<*> -> State.Refresh(
                    state.pageCount,
                    state.data as List<T>
                )
                is State.FullData<*> -> State.Refresh(state.pageCount, state.data as List<T>)
                else -> state
            }
        }
        is Action.Restart -> {
            sideEffectListener(SideEffect.LoadPage(1))
            when (state) {
                is State.Empty -> State.EmptyProgress
                is State.EmptyError -> State.EmptyProgress
                is State.Data<*> -> State.EmptyProgress
                is State.Refresh<*> -> State.EmptyProgress
                is State.NewPageProgress<*> -> State.EmptyProgress
                is State.FullData<*> -> State.EmptyProgress
                else -> state
            }
        }
        is Action.LoadMore -> {
            when (state) {
                is State.Data<*> -> {
                    sideEffectListener(SideEffect.LoadPage(state.pageCount + 1))
                    State.NewPageProgress(state.pageCount, state.data as List<T>)
                }
                else -> state
            }
        }
        is Action.NewPage<*> -> {
            val items = action.items as List<T>
            when (state) {
                is State.EmptyProgress -> {
                    if (items.isEmpty()) {
                        State.Empty
                    } else {
                        State.Data(1, items)
                    }
                }
                is State.Refresh<*> -> {
                    if (items.isEmpty()) {
                        State.Empty
                    } else {
                        State.Data(1, items)
                    }
                }
                is State.NewPageProgress<*> -> {
                    if (items.isEmpty()) {
                        State.FullData(state.pageCount, state.data as List<T>)
                    } else {
                        State.Data(state.pageCount + 1, state.data as List<T> + items)
                    }
                }
                else -> state
            }
        }
        is Action.Update<*> -> {
            when (state) {
                is State.Data<*> -> State.Data(
                    state.pageCount,
                    state.data.toMutableList().apply { set(action.index, action.item) }
                )
                is State.FullData<*> -> State.FullData(
                    state.pageCount,
                    state.data.toMutableList().apply { set(action.index, action.item) }
                )
                else -> state
            }
        }
        is Action.Move -> {
            when (state) {
                is State.Data<*> -> State.Data(
                    state.pageCount,
                    state.data.toMutableList().apply { swap(action.from, action.to) }
                )
                is State.FullData<*> -> State.FullData(
                    state.pageCount,
                    state.data.toMutableList().apply { swap(action.from, action.to) }
                )
                else -> state
            }
        }
        is Action.Remove -> {
            if (state is State.Data<*> && state.data.size > 1) {
                State.Data(
                    state.pageCount,
                    state.data.toMutableList().apply { removeAt(action.index) }
                )
            } else if (state is State.Data<*>) {
                State.Empty
            } else if (state is State.FullData<*> && state.data.size > 1) {
                State.FullData(
                    state.pageCount,
                    state.data.toMutableList().apply { removeAt(action.index) }
                )
            } else if (state is State.FullData<*>) {
                State.Empty
            } else {
                state
            }
        }
        is Action.Insert<*> -> {
            when (state) {
                is State.Empty -> State.FullData(
                    1,
                    listOf(action.item)
                )
                is State.Data<*> -> State.Data(
                    state.pageCount,
                    state.data + action.item
                )
                is State.FullData<*> -> State.FullData(
                    state.pageCount,
                    state.data + action.item
                )
                else -> state
            }
        }
        is Action.PageError -> {
            when (state) {
                is State.EmptyProgress -> State.EmptyError(action.error)
                is State.Refresh<*> -> {
                    sideEffectListener(SideEffect.ErrorEvent(action.error))
                    State.Data(state.pageCount, state.data as List<T>)
                }
                is State.NewPageProgress<*> -> {
                    sideEffectListener(SideEffect.ErrorEvent(action.error))
                    State.Data(state.pageCount, state.data as List<T>)
                }
                else -> state
            }
        }
    }

    class Store<T> : CoroutineScope by CoroutineScope(Dispatchers.Default) {
        private var state: State = State.Empty
        var render: (State) -> Unit = {}
            set(value) {
                field = value
                value(state)
            }

        val sideEffects = Channel<SideEffect>()

        fun proceed(action: Action) {
            val newState = reducer<T>(action, state) { sideEffect ->
                launch { sideEffects.send(sideEffect) }
            }
            if (newState != state) {
                state = newState
                render(state)
            }
        }
    }
}
