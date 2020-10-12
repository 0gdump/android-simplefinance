package open.zgdump.simplefinance.ui.records

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.item_record.view.*
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.util.android.inflate
import java.text.SimpleDateFormat
import java.util.*

class RecordsAdapterDelegate(
    private val clickListener: (Int) -> Unit
) : AdapterDelegate<MutableList<Any>>() {

    val dateFormatter = SimpleDateFormat("dd.mm.yy", Locale.US)

    override fun isForViewType(items: MutableList<Any>, position: Int) =
        items[position] is Record

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_record))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = (viewHolder as ViewHolder).bind(items[position] as Record)

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(record: Record) {
            itemView.container.setOnClickListener { clickListener(adapterPosition) }
            itemView.date.text = dateFormatter.format(record.date)
            itemView.comment.text = record.comment
            itemView.category.text = record.category
            itemView.value.text = ("${record.value} ${record.currencyDesignation}")
        }
    }
}