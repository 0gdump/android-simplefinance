package open.zgdump.simplefinance.util.android

import android.util.DisplayMetrics
import open.zgdump.simplefinance.App

fun dpToPx(dp: Float) =
    dp * (App.appContext.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)