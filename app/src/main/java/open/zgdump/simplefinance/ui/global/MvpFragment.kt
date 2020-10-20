package open.zgdump.simplefinance.ui.global

import moxy.MvpAppCompatFragment
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.ui.MainActivity

abstract class MvpFragment : MvpAppCompatFragment() {

    protected val activity: MainActivity
        get() = super.getActivity() as MainActivity

    protected val app: App
        get() = activity.application as App
}