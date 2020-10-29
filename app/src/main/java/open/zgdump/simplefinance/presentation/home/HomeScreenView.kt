package open.zgdump.simplefinance.presentation.home

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import open.zgdump.simplefinance.entity.FinancialValue

@AddToEndSingle
interface HomeScreenView : MvpView {

    fun showMonthName(monthName: String)

    fun showTotal(top: List<FinancialValue>)
    fun showActualAccountsTotal(top: List<FinancialValue>)
    fun showSavingsAccountsTotal(top: List<FinancialValue>)
    fun showIncomesForMonth(top: List<FinancialValue>)
    fun showExpensesForMonth(top: List<FinancialValue>)
}
