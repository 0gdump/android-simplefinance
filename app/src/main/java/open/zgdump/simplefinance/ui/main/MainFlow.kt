package open.zgdump.simplefinance.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main_container.*
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.Screens
import open.zgdump.simplefinance.ui.global.MvpFragmentX
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class MainFlow : MvpFragmentX(R.layout.fragment_main) {

    private val navigator by lazy {
        SupportAppNavigator(activity, R.id.fragmentContainer)
    }

    private var coldStart = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        coldStart = savedInstanceState == null

        navigationView.setCheckedItem(R.id.navHome)
        navigationView.setNavigationItemSelectedListener {
            navigateTo(it)
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onResume() {
        super.onResume()


        App.navigationHolder.setNavigator(navigator)
        if (coldStart) {
            Handler().post {
                App.router.navigateTo(Screens.HomeScreen)
            }
        }

        setupToolbar()
    }

    override fun onPause() {
        super.onPause()
        activity.setSupportActionBar(null)
        App.navigationHolder.removeNavigator()
    }

    private fun setupToolbar() {
        activity.setSupportActionBar(toolbar)
        ActionBarDrawerToggle(
            activity,
            drawerLayout,
            toolbar,
            R.string.drawerOpen,
            R.string.drawerClose
        ).apply {
            drawerLayout.setDrawerListener(this)
            syncState()
        }
    }

    private fun navigateTo(menuItem: MenuItem) {
        if (menuItem.itemId == navigationView.checkedItem?.itemId) return
        when (menuItem.itemId) {
            R.id.navHome -> {
                App.router.navigateTo(Screens.HomeScreen)
            }
            R.id.navAccounts -> {
                App.router.navigateTo(Screens.AccountsScreen)
            }
            R.id.navBudget -> {
                App.router.navigateTo(Screens.BudgetScreen)
            }
            R.id.navIncome -> {
                App.router.navigateTo(Screens.IncomesScreen)
            }
            R.id.navExpense -> {
                App.router.navigateTo(Screens.ExpensesScreen)
            }
            R.id.navLoan -> {
                App.router.navigateTo(Screens.LoansScreen)
            }
            R.id.navSms -> {
                App.router.navigateTo(Screens.SmsScreen)
            }
            R.id.navChart -> {
                App.router.navigateTo(Screens.ChartsScreen)
            }
            R.id.navCurrencies -> {
                App.router.navigateTo(Screens.CurrenciesScreen)
            }
            R.id.navCategories -> {
                App.router.navigateTo(Screens.CategoriesScreen)
            }
            R.id.navMore -> {
                App.router.navigateTo(Screens.MoreScreen)
            }
            else -> throw IllegalArgumentException()
        }
    }
}