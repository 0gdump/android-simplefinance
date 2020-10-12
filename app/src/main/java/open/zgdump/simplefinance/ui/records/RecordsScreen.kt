package open.zgdump.simplefinance.ui.records

import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_records.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.records.RecordsScreenPresenter
import open.zgdump.simplefinance.presentation.records.RecordsScreenView
import open.zgdump.simplefinance.ui.global.paginal.PaginalFragment

class RecordsScreen :
    PaginalFragment<RecordsScreenView, Record>(R.layout.fragment_records),
    RecordsScreenView {

    override val mainPresenter by moxyPresenter { RecordsScreenPresenter() }

    override val adapterDelegate: AdapterDelegate<MutableList<Any>>
        get() = RecordsAdapterDelegate(mainPresenter::itemClicked)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupPaginalRenderView(paginalRenderView)
    }

    override fun renderPaginatorState(state: Paginator.State) {
        paginalRenderView.render(state)
    }

    override fun showMessage(message: String) {
        Toasty.info(activity, message).show()
    }

    override fun newRecordDialog(record: Record?) {
        MaterialDialog(activity, ModalDialog).show {

            val isEdit = record != null
            val title =
                if (isEdit)
                    R.string.AccountScreen_editCurrencyDialogTitle
                else
                    R.string.AccountScreen_newCurrencyDialogTitle

            // Содержимое
            title(title)
            customView(
                R.layout.dialog_new_record,
                scrollable = true,
                horizontalPadding = true
            )

            positiveButton(android.R.string.ok) { d ->
                //newRecordDialogComplete(d.getCustomView(), account, currencies)
            }
            negativeButton(android.R.string.cancel)

            getCustomView().apply {

            }
        }
    }
}