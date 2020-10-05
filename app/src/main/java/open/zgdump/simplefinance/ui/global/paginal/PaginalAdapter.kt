package open.zgdump.simplefinance.ui.global.paginal

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import open.zgdump.simplefinance.ui.global.recyclerview.ItemTouchHelperContract

class PaginalAdapter(
    private val nextPageCallback: () -> Unit,
    itemDiff: (old: Any, new: Any) -> Boolean,
    vararg delegate: AdapterDelegate<MutableList<Any>>
) : AsyncListDifferDelegationAdapter<Any>(DummyDiffItemCallback(itemDiff)),
    ItemTouchHelperContract {

    var itemMoved: ((fromPosition: Int, toPosition: Int) -> Unit)? = null
    var itemRemoved: ((position: Int) -> Unit)? = null

    var fullData = false
    var nextPageCaught = false
    var refreshStarted = false

    init {
        items = mutableListOf()

        delegatesManager.addDelegate(ProgressAdapterDelegate())
        delegate.forEach { delegatesManager.addDelegate(it) }
    }

    fun update(data: List<Any>, isPageProgress: Boolean) {
        nextPageCaught = false

        items = mutableListOf<Any>().apply {
            addAll(data)
            if (isPageProgress) add(ProgressItem)
        }

        if (refreshStarted) {
            refreshStarted = false
            nextPageCallback.invoke()
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (!fullData && !nextPageCaught && position >= items.size - 10) {
            nextPageCaught = true
            nextPageCallback.invoke()
        }
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        itemMoved?.invoke(fromPosition, toPosition)
    }

    override fun onRowSelected(viewHolder: RecyclerView.ViewHolder) {}

    override fun onRowClear(viewHolder: RecyclerView.ViewHolder) {}

    override fun onRowSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemRemoved?.invoke(viewHolder.adapterPosition)
    }
}
