package open.zgdump.simplefinance.ui.global.paginal

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_paginal_render.view.*
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.ui.global.recyclerview.DividerItemDecorator
import open.zgdump.simplefinance.util.android.inflate
import open.zgdump.simplefinance.util.android.visible

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 2019-06-22.
 */
class PaginalRenderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    var refreshCallback: (() -> Unit)? = null
    var fabClickCallback: (() -> Unit)? = null

    var adapter: PaginalAdapter? = null
        set(value) {
            recyclerView.adapter = value
            field = value
        }

    init {
        inflate(R.layout.view_paginal_render, true)

        parseAttrs(attrs)

        swipeToRefresh.setOnRefreshListener { refreshCallback?.invoke() }
        emptyView.setRefreshButtonClickListener { refreshCallback?.invoke() }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecorator(context))

        fab.setOnClickListener { fabClickCallback?.invoke() }
    }

    private fun parseAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PaginalRenderView)

        emptyView.errorImage = a.getDrawable(R.styleable.PaginalRenderView_errorImage)
            ?: App.res.getDrawable(R.drawable.ic_error, App.appContext.theme)
        emptyView.errorTitle = a.getString(R.styleable.PaginalRenderView_errorTitle)
            ?: App.res.getString(R.string.EmptyView_defaultErrorTitle)
        emptyView.errorContent = a.getString(R.styleable.PaginalRenderView_errorContent)
            ?: App.res.getString(R.string.EmptyView_defaultErrorContent)
        emptyView.emptyImage = a.getDrawable(R.styleable.PaginalRenderView_emptyImage)
            ?: App.res.getDrawable(R.drawable.ic_add, App.appContext.theme)
        emptyView.emptyTitle = a.getString(R.styleable.PaginalRenderView_emptyTitle)
            ?: App.res.getString(R.string.EmptyView_defaultEmptyTitle)
        emptyView.emptyContent = a.getString(R.styleable.PaginalRenderView_emptyContent)
            ?: App.res.getString(R.string.EmptyView_defaultEmptyContent)

        fab.visible(a.getBoolean(R.styleable.PaginalRenderView_hasFab, true))
        fab.icon = a.getDrawable(R.styleable.PaginalRenderView_fabIcon)
            ?: App.res.getDrawable(R.drawable.ic_add)
        fab.text = a.getString(R.styleable.PaginalRenderView_fabText)
            ?: App.res.getString(R.string.PaginalRenderView_defaultFabText)

        emptyView.hasRefresh = a.getBoolean(R.styleable.PaginalRenderView_hasRefresh, true)

        a.recycle()

    }

    fun render(state: Paginator.State) {
        post {
            when (state) {
                is Paginator.State.Empty -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = true
                    adapter?.update(emptyList(), false)
                    emptyView.showEmptyData()
                    swipeToRefresh.visible(true)
                }
                is Paginator.State.EmptyProgress -> {
                    fab.visible(true)
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(true)
                    adapter?.fullData = false
                    adapter?.update(emptyList(), false)
                    emptyView.hide()
                    swipeToRefresh.visible(false)
                }
                is Paginator.State.EmptyError -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(emptyList(), false)
                    emptyView.showEmptyError()
                    swipeToRefresh.visible(true)
                }
                is Paginator.State.Data<*> -> {
                    swipeToRefresh.isRefreshing = false
                    fullscreenProgressView.visible(false)
                    adapter?.fullData = false
                    adapter?.update(state.data as List<Any>, false)
                    emptyView.hide()
                    swipeToRefresh.visible(true)
                }
                is Paginator.State.Refresh<*> -> {
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
