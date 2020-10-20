package open.zgdump.simplefinance.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import moxy.MvpAppCompatFragment
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.ui.MainActivity
import open.zgdump.simplefinance.util.android.inflate

abstract class MvpFragment() : MvpAppCompatFragment() {

    protected val activity: MainActivity
        get() = super.getActivity() as MainActivity

    protected val app: App
        get() = activity.application as App
}