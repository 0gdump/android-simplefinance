package open.zgdump.simplefinance.ui.categories.category

import android.view.View
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.dialog_new_category.view.*
import moxy.ktx.moxyPresenter
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.entity.db.Category
import open.zgdump.simplefinance.entity.TransactionType
import open.zgdump.simplefinance.presentation.categories.category.CategoryScreenPresenter
import open.zgdump.simplefinance.presentation.categories.category.CategoryScreenView
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.ui.global.paginal.StandardPaginalFragment
import open.zgdump.simplefinance.util.kotlin.argument

class CategoryScreen :
    StandardPaginalFragment<CategoryScreenView, Category>(),
    CategoryScreenView {

    override val mainPresenter by moxyPresenter { CategoryScreenPresenter(transactionType) }

    private val transactionType: TransactionType by argument(
        ARG_TYPE,
        TransactionType.Expense
    )

    companion object {
        private const val ARG_TYPE = "type"
        fun create(transactionType: TransactionType) =
            CategoryScreen().apply {
                arguments = bundleOf(ARG_TYPE to transactionType)
            }
    }

    override val adapterDelegate: AdapterDelegate<MutableList<Any>>
        get() = CategoryAdapterDelegate(mainPresenter::itemClicked)

    override fun renderPaginatorState(state: Paginator.State) {
        paginalRenderView.render(state)
    }

    override fun showMessage(message: String) {
        Toasty.info(activity, message).show()
    }

    override fun categoryDialog(category: Category?) {
        MaterialDialog(activity, ModalDialog).show {

            val isEdit = category != null
            val title =
                if (isEdit)
                    R.string.AccountScreen_editCurrencyDialogTitle
                else
                    R.string.AccountScreen_newCurrencyDialogTitle

            // Содержимое
            title(title)
            customView(
                R.layout.dialog_new_category,
                scrollable = true,
                horizontalPadding = true
            )

            // Настройка кнопок
            positiveButton(android.R.string.ok) { d ->
                categoryDialogComplete(d.getCustomView(), category)
            }
            negativeButton(android.R.string.cancel)

            // Настройка содержимого
            getCustomView().apply {
                nameEditText.setText(category?.name ?: "")
                operationTypeSpinner.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    TransactionType.values().map { it.name }
                )
            }
        }
    }

    private fun categoryDialogComplete(
        dialogView: View,
        originalCategory: Category?,
    ) {
        mainPresenter.categoryDialogComplete(
            originalCategory,
            dialogView.nameEditText.text.toString(),
            TransactionType.valueOf(dialogView.operationTypeSpinner.selectedItem.toString())
        )
    }
}