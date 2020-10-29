package open.zgdump.simplefinance.ui.records

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import open.zgdump.simplefinance.entity.TransactionType
import open.zgdump.simplefinance.ui.records.betweenDates.RecordsBetweenDatesScreen
import open.zgdump.simplefinance.ui.records.sumPerCategory.SumOfRecordsPerCategoryScreen
import open.zgdump.simplefinance.ui.records.sumPerDay.SumOfRecordsPerDayScreen

class RecordsScreenPagerAdapter(
    fm: FragmentManager,
    private val type: TransactionType
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(i: Int): Fragment = when (i) {
        0 -> RecordsBetweenDatesScreen(type)
        1 -> SumOfRecordsPerDayScreen(type)
        2 -> SumOfRecordsPerCategoryScreen(type)
        else -> throw IllegalStateException()
    }

    override fun getPageTitle(position: Int): String = when (position) {
        0 -> "Все"
        1 -> "По дням"
        2 -> "По категориям"
        else -> throw IllegalStateException()
    }
}