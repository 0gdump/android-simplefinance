package open.zgdump.simplefinance.ui.records.sumPerCategory

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sum_of_records_per_categories.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.entity.SumOfRecordsPerCategory
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.records.sumPerCategory.SumOfRecordsPerCategoryScreenPresenter
import open.zgdump.simplefinance.presentation.records.sumPerCategory.SumOfRecordsPerCategoryScreenView
import open.zgdump.simplefinance.ui.global.paginal.StandardPaginalFragment

class SumOfRecordsPerCategoryScreen(
    financialType: FinancialTypeTransaction
) : StandardPaginalFragment<SumOfRecordsPerCategoryScreenView, SumOfRecordsPerCategory>(),
    SumOfRecordsPerCategoryScreenView {

    override val mainPresenter by moxyPresenter {
        SumOfRecordsPerCategoryScreenPresenter(financialType)
    }

    override val adapterDelegate: AdapterDelegate<MutableList<Any>>
        get() = SumOfRecordsPerCategoryScreenAdapterDelegate(mainPresenter::itemClicked)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paginalRenderView.setFabVisible(false)
    }

    override fun renderPaginatorState(state: Paginator.State) {
        paginalRenderView.render(state)
    }

    override fun showMessage(message: String) {
        Toasty.info(activity, message).show()
    }
}