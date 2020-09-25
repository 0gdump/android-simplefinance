package open.zgdump.simplefinance.ui.global.paginal

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_empty.view.*
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.util.android.visible

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val errorImage: Drawable
    val errorTitle: String
    val errorContent: String

    val emptyImage: Drawable
    val emptyTitle: String
    val emptyContent: String

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.EmptyView)

        errorImage = a.getDrawable(R.styleable.EmptyView_errorImage)
            ?: App.res.getDrawable(R.drawable.ic_error)
        errorTitle = a.getString(R.styleable.EmptyView_errorTitle)
            ?: App.res.getString(R.string.EmptyView_defaultErrorTitle)
        errorContent = a.getString(R.styleable.EmptyView_errorContent)
            ?: App.res.getString(R.string.EmptyView_defaultErrorContent)
        emptyImage = a.getDrawable(R.styleable.EmptyView_emptyImage)
            ?: App.res.getDrawable(R.drawable.ic_add)
        emptyTitle = a.getString(R.styleable.EmptyView_emptyTitle)
            ?: App.res.getString(R.string.EmptyView_defaultEmptyTitle)
        emptyContent = a.getString(R.styleable.EmptyView_emptyContent)
            ?: App.res.getString(R.string.EmptyView_defaultEmptyContent)

        a.recycle()

        inflate(context, R.layout.view_empty, this)
    }

    fun setRefreshButtonClickListener(listener: () -> Unit) {
        refreshButton.setOnClickListener { listener() }
    }

    fun showEmptyData() {
        titleText.text = emptyTitle
        descriptionText.text = emptyContent
        visible(true)
    }

    fun showEmptyError(msg: String? = null) {
        titleText.text = errorTitle
        descriptionText.text = msg ?: errorContent
        visible(true)
    }

    fun hide() {
        visible(false)
    }
}
