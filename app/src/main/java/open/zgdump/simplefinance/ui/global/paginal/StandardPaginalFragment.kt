package open.zgdump.simplefinance.ui.global.paginal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter
import open.zgdump.simplefinance.presentation.global.paginal.PaginalView
import open.zgdump.simplefinance.ui.global.MvpFragment
import open.zgdump.simplefinance.util.kotlin.initOnce

abstract class StandardPaginalFragment<V : PaginalView, D> : MvpFragment() {

    protected abstract val mainPresenter: PaginalPresenter<V, D>

    protected abstract val adapterDelegate: AdapterDelegate<MutableList<Any>>

    protected var paginalRenderView by initOnce<PaginalRenderView>()
        private set

    private val adapter by lazy {
        PaginalAdapter(
            mainPresenter::loadMore,
            mainPresenter::diffItems,
            adapterDelegate
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return PaginalRenderView(requireContext()).apply {
            refreshCallback = mainPresenter::refresh
            fabClickCallback = mainPresenter::fabClicked
            itemMoved = mainPresenter::move
            itemRemoved = mainPresenter::remove
            adapter = this@StandardPaginalFragment.adapter

            setHasRefresh(false)

            this@StandardPaginalFragment.paginalRenderView = this
        }
    }
}