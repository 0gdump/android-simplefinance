package open.zgdump.simplefinance.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main_container.*
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.global.CiceroneNavigator
import open.zgdump.simplefinance.global.Screens
import open.zgdump.simplefinance.ui.global.MvpFragmentX


class MainFlow : MvpFragmentX(R.layout.fragment_main) {

    private val defaultNavigationItem = R.id.navAccounts

    private var needToNavigation = false
    private var previousSelectedNavigationItem: MenuItem? = null
    private var selectedNavigationItem: MenuItem? = null
        set(value) {
            previousSelectedNavigationItem = field
            field = value
        }

    private val drawerListener = object : DrawerLayout.DrawerListener {

        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
        override fun onDrawerOpened(drawerView: View) {}
        override fun onDrawerStateChanged(newState: Int) {}

        override fun onDrawerClosed(drawerView: View) {
            if (needToNavigation) {
                navigateToSelected()
            }
        }
    }

    private var coldStart = true
    private val navigator by lazy {
        CiceroneNavigator(activity, R.id.fragmentContainer)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        coldStart = savedInstanceState == null

        drawerLayout.addDrawerListener(drawerListener)
        navigationView.setNavigationItemSelectedListener {
            navigationItemSelectedListener(it)
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
            navigationView.post {
                navigationView.setCheckedItem(defaultNavigationItem)
                startNavigationWith(navigationView.menu.findItem(defaultNavigationItem))
                navigateToSelected()
            }
        }
    }

    private fun navigationItemSelectedListener(menuItem: MenuItem) {
        // Навигация произойдёт после закрытия drawer внутри drawerListener
        startNavigationWith(menuItem)
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun startNavigationWith(menuItem: MenuItem) {
        if (menuItem.itemId != selectedNavigationItem?.itemId) {
            crossFade(progressStub, fragmentContainer, false)

            selectedNavigationItem = menuItem
            needToNavigation = true

            toolbar.title = selectedNavigationItem!!.title
        }
    }

    private fun navigateToSelected() {
        if (selectedNavigationItem?.itemId == previousSelectedNavigationItem?.itemId) {
            return
        }

        if (selectedNavigationItem == null) {
            throw IllegalStateException()
        }

        when (selectedNavigationItem!!.itemId) {
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

        crossFade(fragmentContainer, progressStub, false)
        needToNavigation = false
    }

    // From: https://stackoverflow.com/questions/36351141/
    private fun crossFade(viewIn: View, viewOut: View, animateViewOut: Boolean = true) {
        val crossFadeDuration = 200L

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        viewIn.alpha = 0f
        viewIn.visibility = View.VISIBLE
        viewIn.bringToFront()

        // Animate the in view to 100% opacity, and clear any animation
        // listener set on the view.
        viewIn.animate()
            .alpha(1f)
            .setDuration(crossFadeDuration)
            .setListener(null)

        // Animate the out view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        viewOut.animate()
            .alpha(0f)
            .setDuration(if (animateViewOut) crossFadeDuration else 0)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    viewOut.visibility = View.GONE
                }
            })

    }
}