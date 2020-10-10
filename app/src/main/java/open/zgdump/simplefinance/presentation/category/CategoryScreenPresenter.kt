package open.zgdump.simplefinance.presentation.category

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter

class CategoryScreenPresenter(
    private val operationsType: FinancialTypeTransaction
) : PaginalPresenter<CategoryScreenView, Category>() {

    private val pageSize = 10
    private var editableCurrencyIndex = -1

    override fun diffItems(old: Any, new: Any): Boolean {
        return if (old is Account && new is Account)
            old.hashCode() == new.hashCode()
        else
            false
    }

    override suspend fun loadPage(page: Int): List<Category> {
        return App.db.categoryDao().getCategories(
            pageSize * (page - 1),
            pageSize,
            operationsType
        ) ?: emptyList()
    }

    override fun fabClicked() {
        viewState.categoryDialog(null)
    }

    override fun itemClicked(index: Int) {
        editableCurrencyIndex = index

        viewState.categoryDialog(runBlocking { App.db.categoryDao().getCategory(editableCurrencyIndex, operationsType) })
    }

    override fun provideRemove(index: Int) {
        App.db.categoryDao().delete(index, operationsType)
    }

    fun categoryDialogComplete(
        originalCategory: Category?,
        enteredName: String,
        type: FinancialTypeTransaction
    ) {
        val id = originalCategory?.id ?: 0
        val category = Category(
            id,
            enteredName,
            type
        )

        if (originalCategory == null) {
            launch { App.db.categoryDao().insert(category) }
            paginator.proceed(Paginator.Action.Insert(category))
        } else {
            launch { App.db.categoryDao().update(category) }
            paginator.proceed(Paginator.Action.Update(category, editableCurrencyIndex))
        }
    }
}
