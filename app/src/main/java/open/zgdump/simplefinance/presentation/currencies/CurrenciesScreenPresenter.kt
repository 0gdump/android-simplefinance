package open.zgdump.simplefinance.presentation.currencies

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.presentation.global.MvpPresenterX
import open.zgdump.simplefinance.presentation.global.Paginator
import timber.log.Timber

class CurrenciesScreenPresenter : MvpPresenterX<CurrenciesScreenView>() {

    val pageSize = 50

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

    private fun loadNewPage(page: Int) {
        launch {
            val currencies = App.db.currencyDao().getCurrenciesFromId(
                pageSize * (page - 1),
                pageSize
            ) ?: emptyList()

            paginator.proceed(Paginator.Action.NewPage(page, currencies))
        }
    }

    fun refresh() = paginator.proceed(Paginator.Action.Refresh)
    fun loadMore() = paginator.proceed(Paginator.Action.LoadMore)

    fun onFabClicked() {
        viewState.newCurrencyDialog(null)
    }

    fun onCurrencyClicked(currency: Currency) {
        viewState.newCurrencyDialog(currency)
    }

    fun currencyDialogComplete(
        originalCurrency: Currency?,
        enteredName: String,
        enteredDesignation: String
    ) {
        if (originalCurrency == null) {
            val currency = Currency(0, enteredDesignation, enteredName)
            launch {
                App.db.currencyDao().insert(currency)
            }
            paginator.proceed(Paginator.Action.Insert(currency))
        } else {
            val id = originalCurrency.id
            val currency = Currency(id, enteredDesignation, enteredName)
            launch {
                App.db.currencyDao().update(currency)
            }
            paginator.proceed(Paginator.Action.Update(currency, id - 1))
        }
    }
}
