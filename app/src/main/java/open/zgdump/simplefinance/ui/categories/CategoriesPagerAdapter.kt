package open.zgdump.simplefinance.ui.categories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.ui.account.AccountScreen
import open.zgdump.simplefinance.ui.category.CategoryScreen

class CategoriesPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 2

    override fun getItem(i: Int) = when (i) {
        0 -> CategoryScreen.create(FinancialTypeTransaction.Income)
        1 -> CategoryScreen.create(FinancialTypeTransaction.Expense)
        else -> throw IllegalStateException()
    }

    override fun getPageTitle(position: Int) = when(position) {
        0 -> "Доходы"
        1 -> "Расходы"
        else -> throw IllegalStateException()
    }
}