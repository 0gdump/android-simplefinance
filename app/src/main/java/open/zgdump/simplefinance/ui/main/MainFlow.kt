package open.zgdump.simplefinance.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main_container.*
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.ui.global.MvpFragmentX
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class MainFlow : MvpFragmentX(R.layout.fragment_main) {

    private val navigator by lazy {
        SupportAppNavigator(activity, R.id.fragmentContainer)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigationView.setNavigationItemSelectedListener {
            navigateTo(it)
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        App.navigationHolder.setNavigator(navigator)
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
                Log.d("TEST", "navHome")
            }
            R.id.navAccounts -> {
                Log.d("TEST", "navAccounts")
            }
            R.id.navBudget -> {
                Log.d("TEST", "navBudget")
            }
            R.id.navGroupMoney -> {
                Log.d("TEST", "navGroupMoney")
            }
            R.id.navIncome -> {
                Log.d("TEST", "navIncome")
            }
            R.id.navExpense -> {
                Log.d("TEST", "navExpense")
            }
            R.id.navLoan -> {
                Log.d("TEST", "navLoan")
            }
            R.id.navSms -> {
                Log.d("TEST", "navSms")
            }
            R.id.navChart -> {
                Log.d("TEST", "navChart")
            }
            R.id.navCurrencies -> {
                Log.d("TEST", "navCurrencies")
            }
            R.id.navCategories -> {
                Log.d("TEST", "navCategories")
            }
            R.id.navMore -> {
                Log.d("TEST", "navMore")
            }
            else -> throw IllegalArgumentException()
        }
    }
}