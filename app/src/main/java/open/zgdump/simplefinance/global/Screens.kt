package open.zgdump.simplefinance.global

import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.ui.accounts.AccountsScreen
import open.zgdump.simplefinance.ui.budget.BudgetScreen
import open.zgdump.simplefinance.ui.categories.CategoriesScreen
import open.zgdump.simplefinance.ui.charts.ChartsScreen
import open.zgdump.simplefinance.ui.currencies.CurrenciesScreen
import open.zgdump.simplefinance.ui.home.HomeScreen
import open.zgdump.simplefinance.ui.loans.LoansScreen
import open.zgdump.simplefinance.ui.main.MainFlow
import open.zgdump.simplefinance.ui.more.MoreScreen
import open.zgdump.simplefinance.ui.records.RecordsScreen
import open.zgdump.simplefinance.ui.sms.SmsScreen
import open.zgdump.simplefinance.ui.unimplemented.UnimplementedScreen
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlow()
    }

    object AccountsScreen : SupportAppScreen() {
        override fun getFragment() = AccountsScreen()
    }


    object BudgetScreen : SupportAppScreen() {
        override fun getFragment() = BudgetScreen()
    }


    object CategoriesScreen : SupportAppScreen() {
        override fun getFragment() = CategoriesScreen()
    }


    object ChartsScreen : SupportAppScreen() {
        override fun getFragment() = ChartsScreen()
    }


    object CurrenciesScreen : SupportAppScreen() {
        override fun getFragment() = CurrenciesScreen()
    }


    object ExpensesScreen : SupportAppScreen() {
        override fun getFragment() = RecordsScreen(FinancialTypeTransaction.Expense)
    }


    object HomeScreen : SupportAppScreen() {
        override fun getFragment() = HomeScreen()
    }


    object IncomesScreen : SupportAppScreen() {
        override fun getFragment() = RecordsScreen(FinancialTypeTransaction.Income)
    }


    object LoansScreen : SupportAppScreen() {
        override fun getFragment() = LoansScreen()
    }


    object MoreScreen : SupportAppScreen() {
        override fun getFragment() = MoreScreen()
    }


    object SmsScreen : SupportAppScreen() {
        override fun getFragment() = SmsScreen()
    }

    object UnimplementedScreen : SupportAppScreen() {
        override fun getFragment() = UnimplementedScreen()
    }
}