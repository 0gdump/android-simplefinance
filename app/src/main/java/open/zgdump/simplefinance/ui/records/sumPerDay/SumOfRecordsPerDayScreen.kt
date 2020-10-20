package open.zgdump.simplefinance.ui.records.sumPerDay

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sum_of_records_per_day.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.entity.SumOfRecordsPerDay
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.records.sumPerDay.SumOfRecordsPerDayScreenPresenter
import open.zgdump.simplefinance.presentation.records.sumPerDay.SumOfRecordsPerDayScreenView
import open.zgdump.simplefinance.ui.global.paginal.StandardPaginalFragment

class SumOfRecordsPerDayScreen(
    financialType: FinancialTypeTransaction
) : StandardPaginalFragment<SumOfRecordsPerDayScreenView, SumOfRecordsPerDay>(),
    SumOfRecordsPerDayScreenView {

    override val mainPresenter by moxyPresenter {
        SumOfRecordsPerDayScreenPresenter(financialType)
    }

    override val adapterDelegate: AdapterDelegate<MutableList<Any>>
        get() = SumOfRecordsPerDayScreenAdapterDelegate(mainPresenter::itemClicked)

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