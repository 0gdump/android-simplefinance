package open.zgdump.simplefinance.presentation.main

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MainFlowView : MvpView {

    @AddToEndSingle
    fun setupNavigation()
}