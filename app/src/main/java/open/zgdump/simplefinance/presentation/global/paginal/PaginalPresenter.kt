package open.zgdump.simplefinance.presentation.global.paginal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import open.zgdump.simplefinance.presentation.global.MvpPresenterX
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.util.kotlin.initOnce
import timber.log.Timber

abstract class PaginalPresenter<V : PaginalView, D>(
    private val reversedStore: Boolean = false
) : MvpPresenterX<V>() {

    protected open val pageSize = 10
    protected var paginator: Paginator.Store<D> by initOnce()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setupPaginator()
        refresh()
    }

    private fun setupPaginator() {
        paginator = Paginator.Store(pageSize, reversedStore)
        paginator.render = viewState::renderPaginatorState

        launch { paginator.sideEffects.consumeEach(::sideEffectConsumer) }
    }

    private fun sideEffectConsumer(effect: Paginator.SideEffect) {
        when (effect) {
            is Paginator.SideEffect.LoadPage -> launch {
                paginator.proceed(
                    Paginator.Action.NewPage(
                        effect.currentPage,
                        loadPage(effect.currentPage)
                    )
                )
            }
            is Paginator.SideEffect.ErrorEvent -> {
                viewState.showMessage(effect.error.message.orEmpty())
            }
        }
    }

    open fun diffItems(old: Any, new: Any): Boolean = true

    protected abstract suspend fun loadPage(page: Int): List<D>

    open fun fabClicked() {}

    open fun itemClicked(index: Int) {}

    fun refresh() = paginator.proceed(Paginator.Action.Refresh)

    fun loadMore() = paginator.proceed(Paginator.Action.LoadMore)

    fun move(from: Int, to: Int) {
        Timber.e("Unimplemented")
        //launch(Dispatchers.IO) {  provideMove(from, to)  }
        //paginator.proceed(Paginator.Action.Move(from, to))
    }

    open fun provideMove(from: Int, to: Int) {}

    fun remove(index: Int) {
        launch(Dispatchers.IO) { provideRemove(index) }
        paginator.proceed(Paginator.Action.Remove(index))
    }

    open fun provideRemove(index: Int) {}
}