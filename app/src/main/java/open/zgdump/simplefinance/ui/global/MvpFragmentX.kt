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

abstract class MvpFragmentX(
    private val layoutRes: Int
) : MvpAppCompatFragment() {

    protected lateinit var layout: View

    protected val activity: MainActivity
        get() = super.getActivity() as MainActivity

    protected val app: App
        get() = activity.application as App

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = container!!.inflate(layoutRes)
        setupLayoutOnCreate()
        return layout
    }

    protected open fun setupLayoutOnCreate() {}

    protected fun unimplemented() {
        Toast.makeText(context, "\uD83D\uDE48 Unimplemented", Toast.LENGTH_LONG).show()
    }

    protected fun finish() {
        App.router.exit()
    }
}