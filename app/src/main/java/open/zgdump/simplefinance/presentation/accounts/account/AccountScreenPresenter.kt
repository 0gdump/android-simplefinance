package open.zgdump.simplefinance.presentation.accounts.account

import kotlinx.coroutines.*
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.db.Account
import open.zgdump.simplefinance.entity.db.Currency
import open.zgdump.simplefinance.presentation.accounts.AccountsUpdatedObservable
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter
import open.zgdump.simplefinance.util.pattern.observer.Observer

class AccountScreenPresenter(
    private val isSaving: Boolean
) : PaginalPresenter<AccountScreenView, Account>(),
    Observer {

    private var editableCurrencyIndex = -1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        updateTotal()
        AccountsUpdatedObservable.observers.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountsUpdatedObservable.observers.remove(this)
    }

    override fun observableUpdated() {
        updateTotal()
        refresh()
    }

    private fun updateTotal() = launch {
        val r = App.db.statisticsDao().getSumOfValuesForCurrenciesByCriteria(3, isSaving)
        withContext(Dispatchers.Main) { viewState.updateTotal(r) }
    }

    override fun diffItems(old: Any, new: Any): Boolean {
        return if (old is Account && new is Account)
            old.hashCode() == new.hashCode()
        else
            false
    }

    override suspend fun loadPage(page: Int): List<Account> {
        return App.db.accountDao().getAccounts(
            pageSize * (page - 1),
            pageSize,
            isSaving
        ) ?: emptyList()
    }

    override fun fabClicked() {
        viewState.newAccountDialog(
            null,
            runBlocking { App.db.currencyDao().getCurrencies() } ?: listOf()
        )
    }

    override fun itemClicked(index: Int) = runBlocking {
        editableCurrencyIndex = index

        val account = async { App.db.accountDao().getAccount(editableCurrencyIndex, isSaving) }
        val curencies = async { App.db.currencyDao().getCurrencies() }

        viewState.newAccountDialog(account.await(), curencies.await() ?: listOf())
    }

    override fun provideRemove(index: Int) {
        App.db.accountDao().delete(index, isSaving)
    }

    fun accountDialogComplete(
        originalAccount: Account?,
        enteredName: String,
        enteredValue: Float,
        selectedCurrency: Currency,
        isSaving: Boolean,
        isClosed: Boolean
    ) {
        val id = originalAccount?.id ?: 0
        val value = originalAccount?.value ?: enteredValue
        val initValue = originalAccount?.initialValue ?: enteredValue
        val account = Account(
            id,
            enteredName,
            value,
            initValue,
            selectedCurrency.designation,
            isSaving,
            isClosed
        )

        launch {
            when {

                // Вставлен новый элемент
                originalAccount == null && isSaving == this@AccountScreenPresenter.isSaving -> {
                    paginator.proceed(Paginator.Action.Insert(account))
                    App.db.accountDao().insert(account)
                }

                // Вставлен элемент, но другого типа
                originalAccount == null && isSaving != this@AccountScreenPresenter.isSaving -> {
                    App.db.accountDao().insert(account)
                }

                // Обновлён элемент
                originalAccount != null && isSaving == this@AccountScreenPresenter.isSaving -> {
                    paginator.proceed(Paginator.Action.Update(account, editableCurrencyIndex))
                    App.db.accountDao().update(account)
                }

                // Обновлён элемент, но изменился тип
                originalAccount != null && isSaving != this@AccountScreenPresenter.isSaving -> {
                    paginator.proceed(Paginator.Action.Remove(editableCurrencyIndex))
                    App.db.accountDao().update(account)
                }
            }

            updateTotal()
            AccountsUpdatedObservable.updated(this@AccountScreenPresenter)
        }
    }
}
