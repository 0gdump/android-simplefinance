package open.zgdump.simplefinance.presentation.categories.category

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import open.zgdump.simplefinance.entity.db.Category
import open.zgdump.simplefinance.presentation.global.paginal.PaginalView

interface CategoryScreenView : PaginalView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun categoryDialog(category: Category?)
}
