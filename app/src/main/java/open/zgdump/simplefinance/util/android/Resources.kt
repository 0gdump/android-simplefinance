package open.zgdump.simplefinance.util.android

import android.graphics.drawable.Drawable
import androidx.annotation.BoolRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import open.zgdump.simplefinance.App

fun getDrawable(@DrawableRes drawable: Int): Drawable {
    return ResourcesCompat.getDrawable(
        App.res,
        drawable,
        App.appContext.theme
    ) ?: throw IllegalArgumentException()
}

fun getString(@StringRes string: Int): String {
    return App.res.getString(string)
}

fun getBoolean(@BoolRes bool: Int): Boolean {
    return App.res.getBoolean(bool)
}