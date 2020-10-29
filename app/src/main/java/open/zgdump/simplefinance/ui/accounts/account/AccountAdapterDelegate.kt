package open.zgdump.simplefinance.ui.accounts.account

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.item_account.view.*
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.util.android.inflate
import java.util.*

class AccountAdapterDelegate(
    private val clickListener: (Int) -> Unit
) : AdapterDelegate<MutableList<Any>>() {

    override fun isForViewType(items: MutableList<Any>, position: Int) =
        items[position] is Account

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_account))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = (viewHolder as ViewHolder).bind(items[position] as Account)

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(account: Account) {
            itemView.container.setOnClickListener { clickListener(adapterPosition) }
            itemView.name.text = account.name
            itemView.lockedIndicator.isVisible = account.isClosed
            itemView.value.text =
                ("${account.value} ${account.currencyDesignation.toUpperCase(Locale.ROOT)}")
        }
    }
}