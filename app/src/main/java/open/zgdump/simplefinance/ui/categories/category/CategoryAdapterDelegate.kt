package open.zgdump.simplefinance.ui.categories.category

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.item_category.view.*
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.util.android.inflate

class CategoryAdapterDelegate(
    private val clickListener: (Int) -> Unit
) : AdapterDelegate<MutableList<Any>>() {

    override fun isForViewType(items: MutableList<Any>, position: Int) =
        items[position] is Category

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_category))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = (viewHolder as ViewHolder).bind(items[position] as Category)

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(category: Category) {
            itemView.category.setOnClickListener { clickListener(adapterPosition) }
            itemView.category.text = category.name
        }
    }
}