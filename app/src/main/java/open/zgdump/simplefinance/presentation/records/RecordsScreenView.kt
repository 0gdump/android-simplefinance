package open.zgdump.simplefinance.presentation.records

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.presentation.global.paginal.PaginalView

interface RecordsScreenView : PaginalView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun newRecordDialog(record: Record?)
}