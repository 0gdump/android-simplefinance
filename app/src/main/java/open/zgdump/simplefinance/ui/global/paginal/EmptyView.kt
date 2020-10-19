package open.zgdump.simplefinance.ui.global.paginal

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_empty.view.*
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.util.android.getBoolean
import open.zgdump.simplefinance.util.android.getDrawable
import open.zgdump.simplefinance.util.android.getString
import open.zgdump.simplefinance.util.android.visible

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var errorImage: Drawable
        private set
    lateinit var errorTitle: String
        private set
    lateinit var errorContent: String
        private set

    lateinit var emptyImage: Drawable
        private set
    lateinit var emptyTitle: String
        private set
    lateinit var emptyContent: String
        private set

    var refreshCallback: (() -> Unit)? = null
    var hasRefresh: Boolean = true
        private set

    init {
        inflate(context, R.layout.view_empty, this)

        refreshButton.setOnClickListener { refreshCallback?.invoke() }

        val a = context.obtainStyledAttributes(attrs, R.styleable.EmptyView)

        setErrorImage(a.getDrawable(R.styleable.EmptyView_errorImage))
        setErrorTitle(a.getString(R.styleable.EmptyView_errorTitle))
        setErrorContent(a.getString(R.styleable.EmptyView_errorContent))
        setEmptyImage(a.getDrawable(R.styleable.EmptyView_emptyImage))
        setEmptyTitle(a.getString(R.styleable.EmptyView_emptyTitle))
        setEmptyContent(a.getString(R.styleable.EmptyView_emptyContent))

        setHasRefresh(
            a.getBoolean(
                R.styleable.EmptyView_hasRefresh,
                getBoolean(R.bool.EmptyView_hasRefreshDefault)
            )
        )

        a.recycle()
    }

    //region Setters for settings

    fun setErrorImage(value: Drawable?) {
        errorImage = value ?: getDrawable(R.drawable.ic_add)
    }

    fun setErrorTitle(value: String?) {
        errorTitle = value ?: getString(R.string.EmptyView_defaultErrorTitle)
    }

    fun setErrorContent(value: String?) {
        errorContent = value ?: getString(R.string.EmptyView_defaultErrorContent)
    }

    fun setEmptyImage(value: Drawable?) {
        emptyImage = value ?: getDrawable(R.drawable.ic_add)
    }

    fun setEmptyTitle(value: String?) {
        emptyTitle = value ?: getString(R.string.EmptyView_defaultEmptyTitle)
    }

    fun setEmptyContent(value: String?) {
        emptyContent = value ?: getString(R.string.EmptyView_defaultEmptyContent)
    }

    fun setHasRefresh(value: Boolean?) {
        hasRefresh = value ?: getBoolean(R.bool.EmptyView_hasRefreshDefault)
        refreshButton.visible(hasRefresh)
        guideline.setGuidelinePercent(if (hasRefresh) 0.3f else 0.4f)
    }

    //endregion

    fun showEmptyData() {
        image.setImageDrawable(emptyImage)
        titleText.text = emptyTitle
        descriptionText.text = emptyContent
        visible(true)
    }

    fun showEmptyError(msg: String? = null) {
        image.setImageDrawable(errorImage)
        titleText.text = errorTitle
        descriptionText.text = msg ?: errorContent
        visible(true)
    }

    fun hide() {
        visible(false)
    }
}
