package open.zgdump.simplefinance.ui.global.paginal

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DummyDiffItemCallback(
    private val itemDiff: (old: Any, new: Any) -> Boolean
) : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return itemDiff.invoke(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: Any, newItem: Any) = Any()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
}