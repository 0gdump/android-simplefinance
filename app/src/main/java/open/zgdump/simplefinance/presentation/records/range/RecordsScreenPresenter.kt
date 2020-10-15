package open.zgdump.simplefinance.presentation.records.range

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter

class RecordsScreenPresenter(
    private val type: FinancialTypeTransaction
) : PaginalPresenter<RecordsScreenView, Record>() {

    private val pageSize = 10
    private var editableCurrencyIndex = -1

    override fun diffItems(old: Any, new: Any): Boolean {
        return true
    }

    override suspend fun loadPage(page: Int): List<Record> {
        return App.db.recordDao().getRecords(
            pageSize * (page - 1),
            pageSize,
            type
        ) ?: emptyList()
    }

    override fun fabClicked() = runBlocking {

        val categories = async { App.db.categoryDao().getCategories(type) }
        val accounts = async { App.db.accountDao().getAccounts() }

        viewState.newRecordDialog(
            null,
            categories.await() ?: emptyList(),
            accounts.await() ?: emptyList()
        )
    }

    override fun itemClicked(index: Int) = runBlocking {
        editableCurrencyIndex = index

        val record = async { App.db.recordDao().getRecord(editableCurrencyIndex) }
        val categories = async { App.db.categoryDao().getCategories(type) }
        val accounts = async { App.db.accountDao().getAccounts() }

        viewState.newRecordDialog(
            record.await(),
            categories.await() ?: emptyList(),
            accounts.await() ?: emptyList()
        )
    }

    override fun provideRemove(index: Int) {

    }

    fun recordDialogComplete(
        originalRecord: Record?,
        date: LocalDate,
        category: Category,
        account: Account,
        value: Float,
        comment: String
    ) {
        val id = originalRecord?.id ?: 0
        val record = Record(
            id,
            date,
            category.name,
            category.id,
            account.name,
            account.id,
            account.currencyDesignation,
            value,
            comment,
            type
        )

        if (originalRecord == null) {
            launch { App.db.recordDao().insert(record) }
            paginator.proceed(Paginator.Action.Insert(record))
        } else {
            launch { App.db.recordDao().update(record) }
            paginator.proceed(Paginator.Action.Update(record, editableCurrencyIndex))
        }
    }
}