package open.zgdump.simplefinance.ui.home

import kotlinx.android.synthetic.main.fragment_home.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.helper.FinancialValue
import open.zgdump.simplefinance.presentation.home.HomeScreenPresenter
import open.zgdump.simplefinance.presentation.home.HomeScreenView
import open.zgdump.simplefinance.ui.global.MvpFragmentX

class HomeScreen : MvpFragmentX(R.layout.fragment_home), HomeScreenView {

    private val presenter by moxyPresenter { HomeScreenPresenter() }

    override fun showMonthName(monthName: String) {
        incomesTitle.text = getString(R.string.HomeScreen_incomesTitle).format(monthName)
        expensesTitle.text = getString(R.string.HomeScreen_expensesTitle).format(monthName)
    }

    override fun showTotal(top: List<FinancialValue>) {
        totalTopText.text = formatTopToString(top)
    }

    override fun showActualAccountsTotal(top: List<FinancialValue>) {
        actualTopText.text = formatTopToString(top)
    }

    override fun showSavingsAccountsTotal(top: List<FinancialValue>) {
        savingTopText.text = formatTopToString(top)
    }

    override fun showIncomesForMonth(top: List<FinancialValue>) {
        incomesTopText.text = formatTopToString(top)
    }

    override fun showExpensesForMonth(top: List<FinancialValue>) {
        expensesTopText.text = formatTopToString(top)
    }

    private fun formatTopToString(top: List<FinancialValue>): String {
        return if (top.isEmpty()) {
            getString(R.string.sad)
        } else {
            top.joinToString(separator = "\n") { "${it.total}${it.currencyDesignation}" }
        }
    }
}
