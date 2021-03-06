package open.zgdump.simplefinance.ui.accounts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.ui.accounts.account.AccountScreen
import open.zgdump.simplefinance.util.android.getString

class AccountsPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments: MutableList<Fragment?> = mutableListOf(null, null)

    override fun getCount(): Int = 2

    override fun getItem(i: Int) = when (i) {
        0 -> AccountScreen.create(isSaving = false)
        1 -> AccountScreen.create(isSaving = true)
        else -> throw IllegalStateException()
    }

    override fun getPageTitle(position: Int) = when (position) {
        0 -> getString(R.string.AccountsScreen_CurrentAccountsTab)
        1 -> getString(R.string.AccountsScreen_SavingAccountsTab)
        else -> throw IllegalStateException()
    }
}