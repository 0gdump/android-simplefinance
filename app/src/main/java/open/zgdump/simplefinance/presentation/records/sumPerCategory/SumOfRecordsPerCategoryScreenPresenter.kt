package open.zgdump.simplefinance.presentation.records.sumPerCategory

import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.entity.SumOfRecordsPerCategory
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter
import open.zgdump.simplefinance.presentation.records.RecordsUpdatedObservable
import open.zgdump.simplefinance.util.pattern.observer.Observer

class SumOfRecordsPerCategoryScreenPresenter(
    private val type: FinancialTypeTransaction
) : PaginalPresenter<SumOfRecordsPerCategoryScreenView, SumOfRecordsPerCategory>(),
    Observer {

    private var editableCurrencyIndex = -1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        RecordsUpdatedObservable.observers.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RecordsUpdatedObservable.observers.remove(this)
    }

    override fun observableUpdated() {
        refresh()
    }

    override suspend fun loadPage(page: Int): List<SumOfRecordsPerCategory> {
        return App.db.recordDao().getSumOfRecordsPerCategories(
            pageSize * (page - 1),
            pageSize,
            LocalDate(2020, 10, 1),
            LocalDate(2020, 10, 30),
            type
        ) ?: emptyList()
    }
}