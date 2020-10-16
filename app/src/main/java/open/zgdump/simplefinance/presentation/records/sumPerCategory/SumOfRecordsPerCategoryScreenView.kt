package open.zgdump.simplefinance.presentation.records.sumPerCategory

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.presentation.global.paginal.PaginalView

interface SumOfRecordsPerCategoryScreenView : PaginalView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun newRecordDialog(
        record: Record?,
        categories: List<Category>,
        accounts: List<Account>
    )
}