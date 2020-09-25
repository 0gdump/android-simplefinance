package open.zgdump.simplefinance.ui.currencies

import android.os.Bundle
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_currencies.*
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
                    o.id == n.id
                else
                    false
            },
            CurrencyAdapterDelegate {
                presenter.onCurrencyClicked(it)
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        paginalRenderView.init(
            { presenter.refresh() },
            adapter
        )
    }

    override fun renderPaginatorState(state: Paginator.State) {
        paginalRenderView.render(state)
    }

    override fun showMessage(message: String) {
        Toasty.info(activity, message).show()
    }
}
