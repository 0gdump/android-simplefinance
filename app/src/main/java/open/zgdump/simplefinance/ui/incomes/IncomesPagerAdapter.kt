package open.zgdump.simplefinance.ui.incomes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import open.zgdump.simplefinance.ui.records.range.RecordsScreen
import open.zgdump.simplefinance.ui.records.sumPerCategory.SumOfRecordsPerCategoryScreen
import open.zgdump.simplefinance.ui.records.sumPerDay.SumOfRecordsPerDayScreen

class IncomesPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(i: Int): Fragment = when (i) {
        0 -> RecordsScreen()
        1 -> SumOfRecordsPerDayScreen()
        2 -> SumOfRecordsPerCategoryScreen()
        else -> throw IllegalStateException()
    }

    override fun getPageTitle(position: Int) = when (position) {
        0 -> "Все"
        1 -> "По дням"
        2 -> "По категориям"
        else -> throw IllegalStateException()
    }
}