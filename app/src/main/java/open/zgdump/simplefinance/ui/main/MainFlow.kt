package open.zgdump.simplefinance.ui.main

import android.os.Bundle
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
}