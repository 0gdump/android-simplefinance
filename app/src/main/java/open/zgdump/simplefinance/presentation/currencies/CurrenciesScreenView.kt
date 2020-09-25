package open.zgdump.simplefinance.presentation.currencies

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import open.zgdump.simplefinance.presentation.global.Paginator

interface CurrenciesScreenView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun renderPaginatorState(state: Paginator.State)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)
}
