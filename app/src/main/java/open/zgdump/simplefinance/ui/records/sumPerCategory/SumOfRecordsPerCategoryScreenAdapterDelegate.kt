package open.zgdump.simplefinance.ui.records.sumPerCategory

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.item_sum_of_records_per_category.view.*
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.SumOfRecordsPerCategory
import open.zgdump.simplefinance.util.android.inflate

class SumOfRecordsPerCategoryScreenAdapterDelegate(
    private val clickListener: (Int) -> Unit
) : AdapterDelegate<MutableList<Any>>() {

    override fun isForViewType(items: MutableList<Any>, position: Int) =
        items[position] is SumOfRecordsPerCategory

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_sum_of_records_per_category))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = (viewHolder as ViewHolder).bind(items[position] as SumOfRecordsPerCategory)

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(record: SumOfRecordsPerCategory) {
            itemView.container.setOnClickListener { clickListener(adapterPosition) }
            itemView.category.text = record.categoryName
            itemView.value.text = ("${record.sum} ${record.currencyDesignation}")
        }
    }
}