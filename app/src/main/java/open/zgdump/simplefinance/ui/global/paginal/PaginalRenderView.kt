package open.zgdump.simplefinance.ui.global.paginal

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_paginal_render.view.*
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.ui.global.DividerItemDecorator
import open.zgdump.simplefinance.util.android.inflate
import open.zgdump.simplefinance.util.android.visible

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 2019-06-22.
 */
class PaginalRenderView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var refreshCallback: (() -> Unit)? = null

    private var adapter: PaginalAdapter? = null

    init {
        inflate(R.layout.view_paginal_render, true)
        swipeToRefresh.setOnRefreshListener { refreshCallback?.invoke() }
        emptyView.setRefreshListener { refreshCallback?.invoke() }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecorator(context))
    }

    fun init(
        refreshCallback: () -> Unit,
        adapter: PaginalAdapter
    ) {
        this.refreshCallback = refreshCallback
        this.adapter = adapter
        recyclerView.adapter = adapter
    }

    fun render(state: Paginator.State) {
        post {
            when (state) {
                is Paginator.State.Empty -> {
                    fab.visible(false)
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = true
                    adapter?.update(emptyList(), false)
                    emptyView.showEmptyData()
                    swipeToRefresh.visible(true)
                }
                is Paginator.State.EmptyProgress -> {
                    fab.visible(false)
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(true)
                    adapter?.fullData = false
                    adapter?.update(emptyList(), false)
                    emptyView.hide()
                    swipeToRefresh.visible(false)
                }
                is Paginator.State.EmptyError -> {
                    fab.visible(false)
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(emptyList(), false)
                    emptyView.showEmptyError("Эм..."/*state.error.userMessage(resourceManager)*/)
                    swipeToRefresh.visible(true)
                }
                is Paginator.State.Data<*> -> {
                    fab.visible(true)
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(state.data as List<Any>, false)
                    emptyView.hide()
                    swipeToRefresh.visible(true)
                }
                is Paginator.State.Refresh<*> -> {
                    fab.visible(true)
                    swipeToRefresh.isRefreshing = true
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(state.data as List<Any>, false)
                    emptyView.hide()
                    swipeToRefresh.visible(true)
                }
                is Paginator.State.NewPageProgress<*> -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(state.data as List<Any>, true)
                    emptyView.hide()
                    swipeToRefresh.visible(true)
                }
                is Paginator.State.FullData<*> -> {
                    fab.visible(true)
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = true
                    adapter?.update(state.data as List<Any>, false)
                    emptyView.hide()
                    swipeToRefresh.visible(true)
                }
            }
        }
    }
}
