package open.zgdump.simplefinance.ui.records

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_records.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.presentation.records.RecordsScreenPresenter
import open.zgdump.simplefinance.presentation.records.RecordsScreenView
import open.zgdump.simplefinance.ui.global.MvpFragmentX

class RecordsScreen(
    private val financialType: FinancialTypeTransaction
) : MvpFragmentX(R.layout.fragment_records),
    RecordsScreenView {

    private val presenter by moxyPresenter { RecordsScreenPresenter(financialType) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = RecordsScreenPagerAdapter(childFragmentManager, financialType)
    }

    override fun showSumOfRecords(sum: Float) {
        monthTotalSum.text = sum.toString()
    }
}
