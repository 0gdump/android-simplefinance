package open.zgdump.simplefinance.ui.global.paginal

import androidx.annotation.LayoutRes
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter
import open.zgdump.simplefinance.presentation.global.paginal.PaginalView
import open.zgdump.simplefinance.ui.global.MvpFragmentX

abstract class PaginalFragment<V : PaginalView, D>(
    @LayoutRes layout: Int
) : MvpFragmentX(layout) {

    protected abstract val mainPresenter: PaginalPresenter<V, D>

    protected abstract val adapterDelegate: AdapterDelegate<MutableList<Any>>

    protected val adapter by lazy {
        PaginalAdapter(
            mainPresenter::loadMore,
            mainPresenter::diffItems,
            adapterDelegate
        )
    }

    protected fun setupPaginalRenderView(paginalRenderView: PaginalRenderView) =
        paginalRenderView.apply {
            refreshCallback = mainPresenter::refresh
            fabClickCallback = mainPresenter::fabClicked
            itemMoved = mainPresenter::move
            itemRemoved = mainPresenter::remove
            adapter = this@PaginalFragment.adapter
        }
}