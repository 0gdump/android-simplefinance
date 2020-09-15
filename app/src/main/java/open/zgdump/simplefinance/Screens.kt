package open.zgdump.simplefinance

import open.zgdump.simplefinance.ui.main.MainScreen
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainScreen()
    }
}