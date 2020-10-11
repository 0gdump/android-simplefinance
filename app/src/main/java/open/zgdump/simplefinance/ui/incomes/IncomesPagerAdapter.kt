package open.zgdump.simplefinance.ui.incomes

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import open.zgdump.simplefinance.ui.records.RecordsScreen

class IncomesPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(i: Int) = when (i) {
        0 -> RecordsScreen()
        1 -> RecordsScreen()
        2 -> RecordsScreen()
        else -> throw IllegalStateException()
    }

    override fun getPageTitle(position: Int) = when (position) {
        0 -> "Все"
        1 -> "По дням"
        2 -> "По категориям"
        else -> throw IllegalStateException()
    }
}