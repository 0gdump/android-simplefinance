package open.zgdump.simplefinance.presentation.currencies

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.presentation.global.MvpPresenterX
import open.zgdump.simplefinance.presentation.global.Paginator
import timber.log.Timber

class CurrenciesScreenPresenter : MvpPresenterX<CurrenciesScreenView>() {

    val pageSize = 10

    var editableCurrencyIndex = -1

    private val paginator = Paginator.Store<Currency>()

    init {
        paginator.render = { viewState.renderPaginatorState(it) }
        launch {
            paginator.sideEffects.consumeEach { effect ->
                when (effect) {
                    is Paginator.SideEffect.LoadPage -> {
                        loadNewPage(effect.currentPage)
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

    private fun loadNewPage(page: Int) = launch {
        val currencies = App.db.currencyDao().getCurrencies(
            pageSize * (page - 1),
            pageSize
        ) ?: emptyList()

        paginator.proceed(Paginator.Action.NewPage(page, currencies))
    }

    fun refresh() = paginator.proceed(Paginator.Action.Refresh)
    fun loadMore() = paginator.proceed(Paginator.Action.LoadMore)

    fun onMove(from: Int, to: Int) {
        Timber.e("Unimplemented dragging")
    }

    fun onRemove(index: Int) {
        launch(Dispatchers.IO) { App.db.currencyDao().delete(index) }
        paginator.proceed(Paginator.Action.Remove(index))
    }

    fun onCurrencyClicked(index: Int) {
        editableCurrencyIndex = index

        val currency = runBlocking { App.db.currencyDao().getCurrency(editableCurrencyIndex) }
        viewState.newCurrencyDialog(currency)
    }

    fun onFabClicked() {
        viewState.newCurrencyDialog(null)
    }

    fun currencyDialogComplete(
        originalCurrency: Currency?,
        enteredName: String,
        enteredDesignation: String
    ) {
        val id = originalCurrency?.id ?: 0
        val currency = Currency(id, enteredName, enteredDesignation)

        if (originalCurrency == null) {
            launch { App.db.currencyDao().insert(currency) }
            paginator.proceed(Paginator.Action.Insert(currency))
        } else {
            launch { App.db.currencyDao().update(currency) }
            paginator.proceed(Paginator.Action.Update(currency, editableCurrencyIndex))
        }
    }
}
