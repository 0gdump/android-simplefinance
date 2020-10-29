package open.zgdump.simplefinance.ui.currencies

import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.dialog_new_currency.view.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.db.Currency
import open.zgdump.simplefinance.presentation.currencies.CurrenciesScreenPresenter
import open.zgdump.simplefinance.presentation.currencies.CurrenciesScreenView
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.ui.global.paginal.StandardPaginalFragment

class CurrenciesScreen :
    StandardPaginalFragment<CurrenciesScreenView, Currency>(),
    CurrenciesScreenView {

    override val mainPresenter by moxyPresenter { CurrenciesScreenPresenter() }

    override val adapterDelegate: AdapterDelegate<MutableList<Any>>
        get() = CurrencyAdapterDelegate(mainPresenter::itemClicked)

    override fun renderPaginatorState(state: Paginator.State) {
        paginalRenderView.render(state)
    }

    override fun showMessage(message: String) {
        Toasty.info(activity, message).show()
    }

    override fun newCurrencyDialog(currency: Currency?) {
        MaterialDialog(activity, ModalDialog).show {

            val isEdit = currency != null
            val title =
                if (isEdit)
                    R.string.CurrencyScreen_editCurrencyDialogTitle
                else
                    R.string.CurrencyScreen_newCurrencyDialogTitle

            // Содержимое
            title(title)
            customView(
                R.layout.dialog_new_currency,
                scrollable = true,
                horizontalPadding = true
            )

            // Настройка кнопок
            positiveButton(android.R.string.ok) { d ->
                newCurrencyDialogComplete(d.getCustomView(), currency)
            }
            negativeButton(android.R.string.cancel)

            // Настройка содержимого
            getCustomView().apply {
                nameEditText.setText(currency?.name ?: "")
                designationEditText.setText(currency?.designation ?: "")
            }
        }
    }

    private fun newCurrencyDialogComplete(
        dialogView: View,
        originalCurrency: Currency?
    ) {
        mainPresenter.currencyDialogComplete(
            originalCurrency,
            dialogView.nameEditText.text.toString(),
            dialogView.designationEditText.text.toString(),
        )
    }
}