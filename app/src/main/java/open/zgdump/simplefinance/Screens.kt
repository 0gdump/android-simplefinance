package open.zgdump.simplefinance

import open.zgdump.simplefinance.ui.home.HomeScreen
import open.zgdump.simplefinance.ui.main.MainFlow
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlow()
    }

    object HomeScreen : SupportAppScreen() {
        override fun getFragment() = HomeScreen()
    }
}