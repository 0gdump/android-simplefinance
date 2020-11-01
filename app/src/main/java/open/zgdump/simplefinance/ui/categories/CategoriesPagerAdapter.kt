package open.zgdump.simplefinance.ui.categories

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.TransactionType
import open.zgdump.simplefinance.ui.categories.category.CategoryScreen
import open.zgdump.simplefinance.util.android.getString

class CategoriesPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 2

    override fun getItem(i: Int) = when (i) {
        0 -> CategoryScreen.create(TransactionType.Income)
        1 -> CategoryScreen.create(TransactionType.Expense)
        else -> throw IllegalStateException()
    }

    override fun getPageTitle(position: Int) = when (position) {
        0 -> getString(R.string.CategoriesScreen_IncomeCategoriesTab)
        1 -> getString(R.string.CategoriesScreen_ExpenseCategoriesTab)
        else -> throw IllegalStateException()
    }
}