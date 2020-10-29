package open.zgdump.simplefinance.ui.global.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.util.android.dpToPx
import kotlin.math.roundToInt

class DividerItemDecorator(context: Context) : ItemDecoration() {

    private val paddingHorizontal = dpToPx(8f).roundToInt()
    private var divider = ColorDrawable(ContextCompat.getColor(context, R.color.separator))

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val left = parent.paddingLeft + paddingHorizontal
        val right = parent.width - parent.paddingRight - paddingHorizontal

        (0 until parent.childCount - 1).forEach { i ->

            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }
}