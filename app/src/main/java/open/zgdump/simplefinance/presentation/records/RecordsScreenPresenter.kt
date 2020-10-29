package open.zgdump.simplefinance.presentation.records

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.TransactionType
import open.zgdump.simplefinance.presentation.global.MvpPresenterX
import open.zgdump.simplefinance.util.pattern.observer.Observer

class RecordsScreenPresenter(
    private val type: TransactionType
) : MvpPresenterX<RecordsScreenView>(),
    Observer {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        RecordsUpdatedObservable.observers.add(this)
        showTotalSum()
    }

    override fun onDestroy() {
        super.onDestroy()
        RecordsUpdatedObservable.observers.remove(this)
    }

    private fun showTotalSum() {
        viewState.showSumOfRecords(runBlocking {
            App.db.recordDao().getSumOfRecords(
                LocalDate(2020, 10, 1),
                LocalDate(2020, 10, 30),
                type
            ) ?: 0.0f
        })
    }

    override fun observableUpdated() {
        showTotalSum()
    }
}
