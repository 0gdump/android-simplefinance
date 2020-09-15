package open.zgdump.simplefinance.util.android

import open.zgdump.simplefinance.App

object Screen {

    @JvmStatic
    fun getWidthPx() = App.res.displayMetrics.widthPixels

    @JvmStatic
    fun getHeightPx() = App.res.displayMetrics.heightPixels
}