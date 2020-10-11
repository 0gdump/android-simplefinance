package open.zgdump.simplefinance.ui.incomes

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_incomes.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.presentation.incomes.IncomesScreenPresenter
import open.zgdump.simplefinance.presentation.incomes.IncomesScreenView
import open.zgdump.simplefinance.ui.global.MvpFragmentX

class IncomesScreen : MvpFragmentX(R.layout.fragment_incomes), IncomesScreenView {

    private val presenter by moxyPresenter { IncomesScreenPresenter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = IncomesPagerAdapter(childFragmentManager)
    }
}
