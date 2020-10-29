package open.zgdump.simplefinance.presentation.home

import android.text.format.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.TransactionType
import open.zgdump.simplefinance.presentation.global.MvpPresenterX
import java.util.*

class HomeScreenPresenter : MvpPresenterX<HomeScreenView>() {

    private val calendar = Calendar.getInstance()
    private val monthNumber = calendar.get(Calendar.MONTH) + 1  // By default, january is zero
    private val maxMonthDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    private val monthName = DateUtils.formatDateTime(
        App.appContext,
        calendar.timeInMillis,
        DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NO_MONTH_DAY or DateUtils.FORMAT_NO_YEAR
    )

    private val firstDayOfMonth = LocalDate(2020, monthNumber, 1)
    private val lastDayOfMonth = LocalDate(2020, monthNumber, maxMonthDay)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showMonthName(monthName)

        showTotal()
        showActualAccountsTotal()
        showSavingsAccountsTotal()
        showIncomesForMonth()
        showExpensesForMonth()
    }

    private fun showTotal() = launch {
        val r = App.db.statisticsDao().getSumOfValuesForCurrencies(3)
        withContext(Dispatchers.Main) { viewState.showTotal(r) }
    }

    private fun showActualAccountsTotal() = launch {
        val r = App.db.statisticsDao().getSumOfValuesForCurrenciesByCriteria(3, false)
        withContext(Dispatchers.Main) { viewState.showActualAccountsTotal(r) }
    }

    private fun showSavingsAccountsTotal() = launch {
        val r = App.db.statisticsDao().getSumOfValuesForCurrenciesByCriteria(3, true)
        withContext(Dispatchers.Main) { viewState.showSavingsAccountsTotal(r) }
    }

    private fun showIncomesForMonth() = launch {
        val r = App.db.statisticsDao().getSumOfRecordsForCurrencies(
            firstDayOfMonth,
            lastDayOfMonth,
            TransactionType.Income,
            3
        )
        withContext(Dispatchers.Main) { viewState.showIncomesForMonth(r) }
    }

    private fun showExpensesForMonth() = launch {
        val r = App.db.statisticsDao().getSumOfRecordsForCurrencies(
            firstDayOfMonth,
            lastDayOfMonth,
            TransactionType.Expense,
            3
        )
        withContext(Dispatchers.Main) { viewState.showExpensesForMonth(r) }
    }
}
