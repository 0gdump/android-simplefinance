package open.zgdump.simplefinance.presentation.currencies

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter

class CurrenciesScreenPresenter : PaginalPresenter<CurrenciesScreenView, Currency>() {

    private val pageSize = 10
    private var editableCurrencyIndex = -1

    override fun diffItems(old: Any, new: Any): Boolean {
        return if (old is Currency && new is Currency)
            old.hashCode() == new.hashCode()
        else
            false
    }

    override suspend fun loadPage(page: Int): List<Currency> {
        return App.db.currencyDao().getCurrencies(
            pageSize * (page - 1),
            pageSize
        ) ?: emptyList()
    }

    override fun fabClicked() {
        viewState.newCurrencyDialog(null)
    }

    override fun itemClicked(index: Int) {
        editableCurrencyIndex = index

        val currency = runBlocking { App.db.currencyDao().getCurrency(editableCurrencyIndex) }
        viewState.newCurrencyDialog(currency)
    }

    override fun provideRemove(index: Int) {
        App.db.currencyDao().delete(index)
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
