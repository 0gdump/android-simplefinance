package open.zgdump.simplefinance.ui.currencies

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_currencies.*
import kotlinx.android.synthetic.main.fragment_new_currency.view.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.presentation.currencies.CurrenciesScreenPresenter
import open.zgdump.simplefinance.presentation.currencies.CurrenciesScreenView
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.ui.global.MvpFragmentX
import open.zgdump.simplefinance.ui.global.paginal.PaginalAdapter

class CurrenciesScreen : MvpFragmentX(R.layout.fragment_currencies), CurrenciesScreenView {

    private val presenter by moxyPresenter { CurrenciesScreenPresenter() }

    private val adapter by lazy {
        PaginalAdapter(
            { presenter.loadMore() },
            { o, n ->
                if (o is Currency && n is Currency)
                    o.hashCode() == n.hashCode()
                else
                    false
            },
            CurrencyAdapterDelegate { presenter.onCurrencyClicked(it) }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        paginalRenderView.apply {
            refreshCallback = presenter::refresh
            fabClickCallback = presenter::onFabClicked
            itemMoved = presenter::onMove
            itemRemoved = presenter::onRemove
            adapter = this@CurrenciesScreen.adapter
        }
    }

    override fun renderPaginatorState(state: Paginator.State) {
        paginalRenderView.render(state)
    }

    override fun showMessage(message: String) {
        Toasty.info(activity, message).show()
    }

    override fun newCurrencyDialog(
        currency: Currency?
    ) {
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
                R.layout.fragment_new_currency,
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
        presenter.currencyDialogComplete(
            originalCurrency,
            dialogView.nameEditText.text.toString(),
            dialogView.designationEditText.text.toString(),
        )
    }
}
