package open.zgdump.simplefinance.presentation.currencies

import kotlinx.coroutines.async
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.presentation.global.MvpPresenterX
import open.zgdump.simplefinance.presentation.global.Paginator

class CurrenciesScreenPresenter : MvpPresenterX<CurrenciesScreenView>() {

    private val paginator = Paginator.Store<Currency>()

    init {
        paginator.render = { viewState.renderPaginatorState(it) }
        launch {
            paginator.sideEffects.consumeEach { effect ->
                when (effect) {
                    is Paginator.SideEffect.LoadPage -> {
                        loadNewPage(effect.currentPage, effect.currentPageLastItem as Currency?)
                    }
                    is Paginator.SideEffect.ErrorEvent -> {
                        viewState.showMessage(effect.error.message.orEmpty())
                    }
                }
            }
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        refresh()
    }

    private fun loadNewPage(page: Int, lastItem: Currency?) {
        val pageSize = 10

        launch {
            val currencies = App.db.currencyDao().getCurrenciesFromId(
                pageSize * (page - 1),
                pageSize
            ) ?: emptyList()

            paginator.proceed(Paginator.Action.NewPage(page + 1, currencies))
        }
    }

    fun refresh() = paginator.proceed(Paginator.Action.Refresh)
    fun loadMore() = paginator.proceed(Paginator.Action.LoadMore)

    fun onCurrencyClicked(currency: Currency) {

    }
}
