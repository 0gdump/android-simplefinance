package open.zgdump.simplefinance.ui.records

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.records.RecordsScreenPresenter
import open.zgdump.simplefinance.presentation.records.RecordsScreenView
import open.zgdump.simplefinance.ui.global.paginal.PaginalFragment

class RecordsScreen :
    PaginalFragment<RecordsScreenView, Record>(R.layout.fragment_records),
    RecordsScreenView {

    override val mainPresenter by moxyPresenter { RecordsScreenPresenter() }

    override val adapterDelegate: AdapterDelegate<MutableList<Any>>
        get() = object : AdapterDelegate<MutableList<Any>>() {

            override fun isForViewType(items: MutableList<Any>, position: Int) = false

            override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                TODO("Not yet implemented")
            }

            override fun onBindViewHolder(
                items: MutableList<Any>,
                position: Int,
                holder: RecyclerView.ViewHolder,
                payloads: MutableList<Any>
            ) { TODO("Not yet implemented") }
        }

    override fun renderPaginatorState(state: Paginator.State) {

    }

    override fun showMessage(message: String) {
    }

    override fun newRecordDialog(record: Record?) {
    }
}