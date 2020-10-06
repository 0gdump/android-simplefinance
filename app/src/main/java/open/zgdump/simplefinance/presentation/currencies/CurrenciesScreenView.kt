package open.zgdump.simplefinance.presentation.currencies

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.presentation.global.paginal.PaginalView

interface CurrenciesScreenView : PaginalView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun newCurrencyDialog(currency: Currency?)
}
