package open.zgdump.simplefinance.ui.records.sumPerDay

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.item_sum_of_records_per_day.view.*
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.SumOfRecordsPerDay
import open.zgdump.simplefinance.util.android.inflate

class SumOfRecordsPerDayAdapterDelegate(
    private val clickListener: (Int) -> Unit
) : AdapterDelegate<MutableList<Any>>() {

    override fun isForViewType(items: MutableList<Any>, position: Int) =
        items[position] is SumOfRecordsPerDay

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_sum_of_records_per_day))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = (viewHolder as ViewHolder).bind(items[position] as SumOfRecordsPerDay)

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(record: SumOfRecordsPerDay) {
            itemView.container.setOnClickListener { clickListener(adapterPosition) }
            itemView.date.text = record.date.run { "$dayOfMonth.$monthNumber.${year % 100}" }
            itemView.value.text = ("${record.sum} ${record.currencyDesignation}")
        }
    }
}