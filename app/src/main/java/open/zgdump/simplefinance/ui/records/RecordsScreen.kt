package open.zgdump.simplefinance.ui.records

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
import kotlinx.android.synthetic.main.fragment_records.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.todayAt
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.records.RecordsScreenPresenter
import open.zgdump.simplefinance.presentation.records.RecordsScreenView
import open.zgdump.simplefinance.ui.global.paginal.PaginalFragment

class RecordsScreen :
    PaginalFragment<RecordsScreenView, Record>(R.layout.fragment_records),
    RecordsScreenView {

    override val mainPresenter by moxyPresenter {
        RecordsScreenPresenter(FinancialTypeTransaction.Income)
    }

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

    override fun newRecordDialog(
        record: Record?,
        categories: List<Category>,
        accounts: List<Account>
    ) {
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

            // Настройка кнопок
            positiveButton(android.R.string.ok) { d ->
                newRecordDialogComplete(d.getCustomView(), record, categories, accounts)
            }
            negativeButton(android.R.string.cancel)

            // Настройка содержимого
            getCustomView().apply {
                categorySpinner.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    categories.map { it.name }
                )
                accountSpinner.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    accounts.map { it.name }
                )
                valueEditText.setText((record?.value ?: 0.0).toString())
                commentEditText.setText(record?.comment.orEmpty())
            }
        }
    }

    private fun newRecordDialogComplete(
        dialogView: View,
        originalRecord: Record?,
        categories: List<Category>,
        accounts: List<Account>
    ) = mainPresenter.recordDialogComplete(
        originalRecord,
        Clock.System.todayAt(currentSystemDefault()),
        categories[dialogView.categorySpinner.selectedItemPosition],
        accounts[dialogView.categorySpinner.selectedItemPosition],
        dialogView.valueEditText.text.toString().toFloat(),
        dialogView.commentEditText.text.toString()
    )
}