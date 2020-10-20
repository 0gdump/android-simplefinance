package open.zgdump.simplefinance.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import open.zgdump.simplefinance.util.android.inflate

abstract class MvpFragmentX(
    private val layoutRes: Int
) : MvpFragment() {

    protected lateinit var layout: View

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
}