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

    var errorImage: Drawable
    var errorTitle: String
    var errorContent: String

    var emptyImage: Drawable
    var emptyTitle: String
    var emptyContent: String

    var hasRefresh: Boolean = true
        set(value) {
            refreshButton.visible(value)
            field = value
        }

    init {
        inflate(context, R.layout.view_empty, this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.EmptyView)

        errorImage = a.getDrawable(R.styleable.EmptyView_errorImage)
            ?: App.res.getDrawable(R.drawable.ic_error, App.appContext.theme)
        errorTitle = a.getString(R.styleable.EmptyView_errorTitle)
            ?: App.res.getString(R.string.EmptyView_defaultErrorTitle)
        errorContent = a.getString(R.styleable.EmptyView_errorContent)
            ?: App.res.getString(R.string.EmptyView_defaultErrorContent)
        emptyImage = a.getDrawable(R.styleable.EmptyView_emptyImage)
            ?: App.res.getDrawable(R.drawable.ic_add, App.appContext.theme)
        emptyTitle = a.getString(R.styleable.EmptyView_emptyTitle)
            ?: App.res.getString(R.string.EmptyView_defaultEmptyTitle)
        emptyContent = a.getString(R.styleable.EmptyView_emptyContent)
            ?: App.res.getString(R.string.EmptyView_defaultEmptyContent)

        hasRefresh = a.getBoolean(R.styleable.EmptyView_hasRefresh, true)

        a.recycle()
    }

    fun setRefreshButtonClickListener(listener: () -> Unit) {
        refreshButton.setOnClickListener { listener() }
    }

    fun showEmptyData() {
        zeroImage.setImageDrawable(emptyImage)
        titleText.text = emptyTitle
        descriptionText.text = emptyContent
        visible(true)
    }

    fun showEmptyError(msg: String? = null) {
        zeroImage.setImageDrawable(errorImage)
        titleText.text = errorTitle
        descriptionText.text = msg ?: errorContent
        visible(true)
    }

    fun hide() {
        visible(false)
    }
}
