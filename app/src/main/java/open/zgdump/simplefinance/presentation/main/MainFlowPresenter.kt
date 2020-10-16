package open.zgdump.simplefinance.presentation.main

import open.zgdump.simplefinance.presentation.global.MvpPresenterX

class MainFlowPresenter : MvpPresenterX<MainFlowView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setupNavigation()
    }
}