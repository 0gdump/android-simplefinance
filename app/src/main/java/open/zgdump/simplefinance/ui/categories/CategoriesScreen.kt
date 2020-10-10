package open.zgdump.simplefinance.ui.categories

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_categories.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.presentation.categories.CategoriesScreenPresenter
import open.zgdump.simplefinance.presentation.categories.CategoriesScreenView
import open.zgdump.simplefinance.ui.global.MvpFragmentX

class CategoriesScreen : MvpFragmentX(R.layout.fragment_categories), CategoriesScreenView {

    private val presenter by moxyPresenter { CategoriesScreenPresenter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = CategoriesPagerAdapter(childFragmentManager)
    }
}
