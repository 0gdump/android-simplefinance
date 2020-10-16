package open.zgdump.simplefinance.presentation.records

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface RecordsScreenView : MvpView {

    @AddToEndSingle
    fun showSumOfRecords(sum: Float)
}
