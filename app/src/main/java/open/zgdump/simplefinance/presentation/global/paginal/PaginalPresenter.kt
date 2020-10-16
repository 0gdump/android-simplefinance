package open.zgdump.simplefinance.presentation.global.paginal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import open.zgdump.simplefinance.presentation.global.MvpPresenterX
import open.zgdump.simplefinance.presentation.global.Paginator
import timber.log.Timber

abstract class PaginalPresenter<V : PaginalView, D> : MvpPresenterX<V>() {

    protected open val pageSize = 10
    protected val paginator by lazy { Paginator.Store<D>(pageSize) }

    init {
        paginator.render = viewState::renderPaginatorState
        launch { paginator.sideEffects.consumeEach(::sideEffectConsumer) }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        refresh()
    }

    private fun sideEffectConsumer(effect: Paginator.SideEffect) = when (effect) {
        is Paginator.SideEffect.LoadPage -> {
            paginator.proceed(
                Paginator.Action.NewPage(
                    effect.currentPage,
                    runBlocking { loadPage(effect.currentPage) }
                )
            )
        }
        is Paginator.SideEffect.ErrorEvent -> {
            viewState.showMessage(effect.error.message.orEmpty())
        }
    }

    abstract fun diffItems(old: Any, new: Any): Boolean

    protected abstract suspend fun loadPage(page: Int): List<D>

    abstract fun fabClicked()

    abstract fun itemClicked(index: Int)

    fun refresh() = paginator.proceed(Paginator.Action.Refresh)

    fun loadMore() = paginator.proceed(Paginator.Action.LoadMore)

    fun move(from: Int, to: Int) {
        Timber.e("Unimplemented")
        //launch(Dispatchers.IO) {  provideMove(from, to)  }
        //paginator.proceed(Paginator.Action.Move(from, to))
    }

    // protected abstract fun provideMove(from: Int, to: Int)

    fun remove(index: Int) {
        launch(Dispatchers.IO) { provideRemove(index) }
        paginator.proceed(Paginator.Action.Remove(index))
    }

    protected abstract fun provideRemove(index: Int)
}