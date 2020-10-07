package open.zgdump.simplefinance.presentation.account

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter

class AccountScreenPresenter(
    private val isSaving: Boolean
) : PaginalPresenter<AccountScreenView, Account>() {

    private val pageSize = 10
    private var editableCurrencyIndex = -1

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
        enteredAmount: Float,
        selectedCurrency: Currency,
        isSaving: Boolean,
        isClosed: Boolean
    ) {
        val id = originalAccount?.id ?: 0
        val amount = originalAccount?.amount ?: enteredAmount
        val account = Account(
            id,
            enteredName,
            amount,
            selectedCurrency.designation,
            isSaving,
            isClosed
        )

        if (originalAccount == null) {
            launch { App.db.accountDao().insert(account) }
            paginator.proceed(Paginator.Action.Insert(account))
        } else {
            launch { App.db.accountDao().update(account) }
            paginator.proceed(Paginator.Action.Update(account, editableCurrencyIndex))
        }
    }
}
