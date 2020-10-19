package open.zgdump.simplefinance.ui.global.paginal

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_paginal_render.view.*
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.ui.global.recyclerview.DividerItemDecorator
import open.zgdump.simplefinance.ui.global.recyclerview.ItemTouchHelperCallback
import open.zgdump.simplefinance.util.android.*

class PaginalRenderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    val errorImage get() = emptyView.errorImage
    val errorTitle get() = emptyView.errorTitle
    val errorContent get() = emptyView.errorContent

    val emptyImage get() = emptyView.emptyImage
    val emptyTitle get() = emptyView.emptyTitle
    val emptyContent get() = emptyView.emptyContent

    var fabVisible: Boolean = false
        private set
    lateinit var fabIcon: Drawable
        private set
    lateinit var fabText: String
        private set

    var hasRefresh: Boolean = false
        private set

    var refreshCallback: (() -> Unit)? = null
    var fabClickCallback: (() -> Unit)? = null

    var itemMoved: ((fromPosition: Int, toPosition: Int) -> Unit)? = null
    var itemRemoved: ((position: Int) -> Unit)? = null

    var adapter: PaginalAdapter? = null
        set(value) {
            value?.itemMoved = itemMoved
            value?.itemRemoved = itemRemoved
            recyclerView.adapter = value
            field = value
            linkItemTouchHelper()
        }

    private var itemTouchHelper: ItemTouchHelper? = null

    init {
        inflate(R.layout.view_paginal_render, true)

        parseAttrs(attrs)

        swipeToRefresh.setOnRefreshListener { refreshCallback?.invoke() }
        emptyView.refreshCallback = refreshCallback

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecorator(context))

        fab.setOnClickListener { fabClickCallback?.invoke() }
    }

    private fun parseAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PaginalRenderView)

        setErrorImage(a.getDrawable(R.styleable.PaginalRenderView_errorImage))
        setErrorTitle(a.getString(R.styleable.PaginalRenderView_errorTitle))
        setErrorContent(a.getString(R.styleable.PaginalRenderView_errorContent))

        setEmptyImage(a.getDrawable(R.styleable.PaginalRenderView_emptyImage))
        setEmptyTitle(a.getString(R.styleable.PaginalRenderView_emptyTitle))
        setEmptyContent(a.getString(R.styleable.PaginalRenderView_emptyContent))

        setFabVisible(
            a.getBoolean(
                R.styleable.PaginalRenderView_hasFab,
                getBoolean(R.bool.PaginalRenderView_hasFabDefault)
            )
        )
        setFabIcon(a.getDrawable(R.styleable.PaginalRenderView_fabIcon))
        setFabText(a.getString(R.styleable.PaginalRenderView_fabText))

        setHasRefresh(
            a.getBoolean(
                R.styleable.PaginalRenderView_hasRefresh,
                getBoolean(R.bool.PaginalRenderView_hasRefreshDefault)
            )
        )

        a.recycle()
    }

    private fun linkItemTouchHelper() {
        itemTouchHelper?.attachToRecyclerView(null)
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper?.attachToRecyclerView(recyclerView)
    }

    //region Setters for settings

    fun setErrorImage(value: Drawable?) = emptyView.setErrorImage(value)

    fun setErrorTitle(value: String?) = emptyView.setErrorTitle(value)

    fun setErrorContent(value: String?) = emptyView.setErrorContent(value)

    fun setEmptyImage(value: Drawable?) = emptyView.setEmptyImage(value)

    fun setEmptyTitle(value: String?) = emptyView.setEmptyTitle(value)

    fun setEmptyContent(value: String?) = emptyView.setEmptyContent(value)

    fun setFabVisible(value: Boolean?) {
        fabVisible = value ?: getBoolean(R.bool.PaginalRenderView_hasFabDefault)
        fab.visible(fabVisible)
    }

    fun setFabIcon(value: Drawable?) {
        fabIcon = value ?: getDrawable(R.drawable.ic_add)
        fab.icon = fabIcon
    }

    fun setFabText(value: String?) {
        fabText = value ?: getString(R.string.PaginalRenderView_defaultFabText)
        fab.text = fabText
    }

    fun setHasRefresh(value: Boolean?) {
        hasRefresh = value ?: getBoolean(R.bool.PaginalRenderView_hasRefreshDefault)
        emptyView.setHasRefresh(hasRefresh)
        swipeToRefresh.isEnabled = hasRefresh
    }

    //endregion

    fun render(state: Paginator.State) = post {
        when (state) {
            is Paginator.State.Empty -> {
                fullscreenProgressView.visible(false)
                adapter?.update(emptyList(), false)
                adapter?.fullData = true
                emptyView.showEmptyData()
                fab.visible(fabVisible)
                swipeToRefresh.isRefreshing = false
                swipeToRefresh.isEnabled = hasRefresh
            }
            is Paginator.State.EmptyProgress -> {
                fullscreenProgressView.visible(true)
                adapter?.update(emptyList(), false)
                adapter?.fullData = false
                emptyView.hide()
                fab.visible(false)
                swipeToRefresh.isRefreshing = false
                swipeToRefresh.isEnabled = false
            }
            is Paginator.State.EmptyError -> {
                fullscreenProgressView.visible(false)
                adapter?.update(emptyList(), false)
                adapter?.fullData = false
                emptyView.showEmptyError()
                fab.visible(fabVisible)
                swipeToRefresh.isRefreshing = false
                swipeToRefresh.isEnabled = hasRefresh
            }
            is Paginator.State.Data<*> -> {
                fullscreenProgressView.visible(false)
                adapter?.update(state.data as List<Any>, false)
                adapter?.fullData = false
                emptyView.hide()
                fab.visible(fabVisible)
                swipeToRefresh.isRefreshing = false
                swipeToRefresh.isEnabled = hasRefresh
            }
            is Paginator.State.Refresh<*> -> {
                fullscreenProgressView.visible(false)
                adapter?.update(state.data as List<Any>, false)
                adapter?.fullData = false
                adapter?.refreshStarted = true
                emptyView.hide()
                fab.visible(fabVisible)
                swipeToRefresh.isRefreshing = true
                swipeToRefresh.isEnabled = hasRefresh
            }
            is Paginator.State.NewPageProgress<*> -> {
                fullscreenProgressView.visible(false)
                adapter?.fullData = false
                adapter?.update(state.data as List<Any>, true)
                emptyView.hide()
                fab.visible(fabVisible)
                swipeToRefresh.isRefreshing = false
                swipeToRefresh.isEnabled = hasRefresh
            }
            is Paginator.State.FullData<*> -> {
                fullscreenProgressView.visible(false)
                adapter?.update(state.data as List<Any>, false)
                adapter?.fullData = true
                emptyView.hide()
                fab.visible(fabVisible)
                swipeToRefresh.isRefreshing = false
                swipeToRefresh.isEnabled = hasRefresh
            }
        }
    }
}
