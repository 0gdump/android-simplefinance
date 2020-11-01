package open.zgdump.simplefinance.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main_container.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.presentation.main.MainFlowPresenter
import open.zgdump.simplefinance.presentation.main.MainFlowView
import open.zgdump.simplefinance.ui.global.MvpFragmentX
import open.zgdump.simplefinance.util.android.DrawerListener
import open.zgdump.simplefinance.util.android.crossFade
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class MainFlow : MvpFragmentX(R.layout.fragment_main), MainFlowView {

    private val presenter by moxyPresenter { MainFlowPresenter() }
    private val navigator by lazy { SupportAppNavigator(activity, R.id.fragmentContainer) }

    private val defaultNavigationItem = R.id.navExpense

    private var needToNavigation = false
    private var previousSelectedNavigationItem: MenuItem? = null
    private var selectedNavigationItem: MenuItem? = null
        set(value) {
            previousSelectedNavigationItem = field
            field = value
        }

    private val drawerListener = object : DrawerListener() {
        override fun onDrawerClosed(drawerView: View) {
            if (needToNavigation) {
                navigateToSelected()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerLayout.addDrawerListener(drawerListener)
        navigationView.setNavigationItemSelectedListener(::navigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()

        setupToolbar()
        App.navigationHolder.setNavigator(navigator)
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

    override fun setupNavigation() {
        navigationView.post {
            navigationView.setCheckedItem(defaultNavigationItem)
            startNavigationWith(navigationView.menu.findItem(defaultNavigationItem))
            navigateToSelected()
        }
    }

    // Navigation will occur after closing drawer inside drawerListener
    private fun navigationItemSelectedListener(menuItem: MenuItem): Boolean {
        startNavigationWith(menuItem)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
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

        MainMenuScreens[selectedNavigationItem?.itemId]?.let { App.router.navigateTo(it) }

        crossFade(fragmentContainer, progressStub, false)
        needToNavigation = false
    }
}