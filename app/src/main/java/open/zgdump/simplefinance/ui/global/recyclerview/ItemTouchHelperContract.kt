package open.zgdump.simplefinance.ui.global.recyclerview

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperContract {
    fun onRowMoved(fromPosition: Int, toPosition: Int)
    fun onRowSelected(viewHolder: RecyclerView.ViewHolder)
    fun onRowClear(viewHolder: RecyclerView.ViewHolder)
    fun onRowSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
}