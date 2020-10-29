package open.zgdump.simplefinance.presentation.records.betweenDates

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import open.zgdump.simplefinance.entity.db.Account
import open.zgdump.simplefinance.entity.db.Category
import open.zgdump.simplefinance.entity.db.Record
import open.zgdump.simplefinance.presentation.global.paginal.PaginalView

interface RecordsBetweenDatesScreenView : PaginalView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun newRecordDialog(
        record: Record?,
        categories: List<Category>,
        accounts: List<Account>
    )
}