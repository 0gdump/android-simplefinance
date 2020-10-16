package open.zgdump.simplefinance.util.android

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

open class DrawerListener : DrawerLayout.DrawerListener {

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

    override fun onDrawerOpened(drawerView: View) {}

    override fun onDrawerClosed(drawerView: View) {}

    override fun onDrawerStateChanged(newState: Int) {}
}