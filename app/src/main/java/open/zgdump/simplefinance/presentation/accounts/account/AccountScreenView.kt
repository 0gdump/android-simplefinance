package open.zgdump.simplefinance.presentation.accounts.account

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.presentation.global.paginal.PaginalView

interface AccountScreenView : PaginalView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun newAccountDialog(account: Account?, currencies: List<Currency>)
}
