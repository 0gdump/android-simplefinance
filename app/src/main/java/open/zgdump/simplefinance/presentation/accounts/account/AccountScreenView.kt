package open.zgdump.simplefinance.presentation.accounts.account

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.entity.FinancialValue
import open.zgdump.simplefinance.presentation.global.paginal.PaginalView

interface AccountScreenView : PaginalView {

    @AddToEndSingle
    fun updateTotal(top: List<FinancialValue>)

    @OneExecution
    fun newAccountDialog(account: Account?, currencies: List<Currency>)
}
