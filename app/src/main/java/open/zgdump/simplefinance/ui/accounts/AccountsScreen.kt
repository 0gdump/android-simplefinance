package open.zgdump.simplefinance.ui.accounts

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_accounts.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.presentation.accounts.AccountsScreenPresenter
import open.zgdump.simplefinance.presentation.accounts.AccountsScreenView
import open.zgdump.simplefinance.ui.global.MvpFragmentX

class AccountsScreen : MvpFragmentX(R.layout.fragment_accounts), AccountsScreenView {

    private val presenter by moxyPresenter { AccountsScreenPresenter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = AccountsPagerAdapter(childFragmentManager)
    }
}

