package open.zgdump.simplefinance.ui.global.paginal

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_empty.view.*
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.util.android.visible

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val res = context.resources

    init {
        inflate(context, R.layout.view_empty, this)
    }

    fun setRefreshListener(listener: () -> Unit) {
        refreshButton.setOnClickListener { listener() }
    }

    fun showEmptyData() {
        titleTextView.text = "TODO" //res.getText(R.string.empty_data)
        descriptionTextView.text = "TODO" // res.getText(R.string.empty_data_description)
        visible(true)
    }

    fun showEmptyError(msg: String? = null) {
        titleTextView.text = "TODO" //  res.getText(R.string.empty_error)
        descriptionTextView.text = "TODO" //  msg ?: res.getText(R.string.empty_error_description)
        visible(true)
    }

    fun hide() {
        visible(false)
    }
}
