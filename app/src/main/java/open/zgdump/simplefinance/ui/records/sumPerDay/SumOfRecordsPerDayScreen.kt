package open.zgdump.simplefinance.ui.records.sumPerDay

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import es.dmoral.toasty.Toasty
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.entity.TransactionType
import open.zgdump.simplefinance.entity.helper.SumOfRecordsPerDay
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.records.sumPerDay.SumOfRecordsPerDayScreenPresenter
import open.zgdump.simplefinance.presentation.records.sumPerDay.SumOfRecordsPerDayScreenView
import open.zgdump.simplefinance.ui.global.paginal.StandardPaginalFragment

class SumOfRecordsPerDayScreen(
    transactionType: TransactionType
) : StandardPaginalFragment<SumOfRecordsPerDayScreenView, SumOfRecordsPerDay>(),
    SumOfRecordsPerDayScreenView {

    override val mainPresenter by moxyPresenter {
        SumOfRecordsPerDayScreenPresenter(transactionType)
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