package open.zgdump.simplefinance.ui.records.sumPerDay

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.dialog_new_record.view.*
import kotlinx.android.synthetic.main.fragment_sum_of_records_per_day.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.todayAt
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.*
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.records.sumPerDay.SumOfRecordsPerDayScreenPresenter
import open.zgdump.simplefinance.presentation.records.sumPerDay.SumOfRecordsPerDayScreenView
import open.zgdump.simplefinance.ui.global.paginal.PaginalFragment

class SumOfRecordsPerDayScreen(
    financialType: FinancialTypeTransaction
) : PaginalFragment<SumOfRecordsPerDayScreenView, SumOfRecordsPerDay>(R.layout.fragment_sum_of_records_per_day),
    SumOfRecordsPerDayScreenView {

    override val mainPresenter by moxyPresenter {
        SumOfRecordsPerDayScreenPresenter(financialType)
    }

    override val adapterDelegate: AdapterDelegate<MutableList<Any>>
        get() = SumOfRecordsPerDayScreenAdapterDelegate(mainPresenter::itemClicked)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPaginalRenderView(paginalRenderView)
    }

    override fun renderPaginatorState(state: Paginator.State) {
        paginalRenderView.render(state)
    }

    override fun showMessage(message: String) {
        Toasty.info(activity, message).show()
    }
}