package open.zgdump.simplefinance.ui.records.sumPerCategory

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
import kotlinx.android.synthetic.main.fragment_sum_of_records_per_categories.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.todayAt
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.*
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.records.sumPerCategory.SumOfRecordsPerCategoryScreenPresenter
import open.zgdump.simplefinance.presentation.records.sumPerCategory.SumOfRecordsPerCategoryScreenView
import open.zgdump.simplefinance.ui.global.paginal.PaginalFragment
import open.zgdump.simplefinance.presentation.records.RecordsUpdatedObservable
import open.zgdump.simplefinance.util.pattern.observer.Observer

class SumOfRecordsPerCategoryScreen(
    financialType: FinancialTypeTransaction
) : PaginalFragment<SumOfRecordsPerCategoryScreenView, SumOfRecordsPerCategory>(R.layout.fragment_records_per_range),
    SumOfRecordsPerCategoryScreenView {

    override val mainPresenter by moxyPresenter {
        SumOfRecordsPerCategoryScreenPresenter(financialType)
    }

    override val adapterDelegate: AdapterDelegate<MutableList<Any>>
        get() = SumOfRecordsPerCategoryScreenAdapterDelegate(mainPresenter::itemClicked)

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