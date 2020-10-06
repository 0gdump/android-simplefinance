package open.zgdump.simplefinance.ui.account

import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.presentation.account.AccountScreenPresenter
import open.zgdump.simplefinance.presentation.account.AccountScreenView
import open.zgdump.simplefinance.ui.global.MvpFragmentX

class AccountScreen : MvpFragmentX(R.layout.fragment_account), AccountScreenView {

    private val presenter by moxyPresenter { AccountScreenPresenter() }
}