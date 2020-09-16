package open.zgdump.simplefinance.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main_container.*
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.Screens
import open.zgdump.simplefinance.global.CiceroneNavigator
import open.zgdump.simplefinance.ui.global.MvpFragmentX


class MainFlow : MvpFragmentX(R.layout.fragment_main) {

    private val defaultNavigationItem = R.id.navHome

    private var coldStart = true
    private val navigator by lazy {
        CiceroneNavigator(activity, R.id.fragmentContainer)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        coldStart = savedInstanceState == null

        navigationView.setNavigationItemSelectedListener {
            navigateTo(it)
            Handler().post { drawerLayout.closeDrawer(GravityCompat.START) }
            true
        }
    }

    override fun onResume() {
        super.onResume()

        App.navigationHolder.setNavigator(navigator)

        setupNavigation()
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

    private fun setupNavigation() {
        if (coldStart) {
            Handler().post {
                navigateTo(navigationView.menu.findItem(defaultNavigationItem))
                navigationView.setCheckedItem(defaultNavigationItem)
            }
        }
    }

    private fun navigateTo(menuItem: MenuItem) {
        if (menuItem.itemId == navigationView.checkedItem?.itemId) return
        toolbar.title = menuItem.title
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