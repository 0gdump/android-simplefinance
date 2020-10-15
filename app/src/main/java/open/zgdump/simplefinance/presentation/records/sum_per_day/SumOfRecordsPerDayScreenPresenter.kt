package open.zgdump.simplefinance.presentation.records.sum_per_day

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.*
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter

class SumOfRecordsPerDayScreenPresenter(
    private val type: FinancialTypeTransaction
) : PaginalPresenter<SumOfRecordsPerDayScreenView, SumOfRecordsPerDay>() {

    private val pageSize = 10
    private var editableCurrencyIndex = -1

    override fun diffItems(old: Any, new: Any): Boolean {
        return true
    }

    override suspend fun loadPage(page: Int): List<SumOfRecordsPerDay> {
        return App.db.recordDao().getSumOfRecordsPerDays(
            pageSize * (page - 1),
            pageSize,
            LocalDate(2020, 10, 1),
            LocalDate(2020, 10, 30),
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