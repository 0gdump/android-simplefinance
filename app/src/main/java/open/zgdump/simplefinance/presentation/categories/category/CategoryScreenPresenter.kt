package open.zgdump.simplefinance.presentation.categories.category

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.db.Account
import open.zgdump.simplefinance.entity.db.Category
import open.zgdump.simplefinance.entity.TransactionType
import open.zgdump.simplefinance.presentation.categories.CategoriesUpdatedObservable
import open.zgdump.simplefinance.presentation.global.Paginator
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter
import open.zgdump.simplefinance.util.pattern.observer.Observer

class CategoryScreenPresenter(
    private val transactionType: TransactionType
) : PaginalPresenter<CategoryScreenView, Category>(),
    Observer {

    private var editableCurrencyIndex = -1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        CategoriesUpdatedObservable.observers.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        CategoriesUpdatedObservable.observers.remove(this)
    }

    override fun observableUpdated() {
        refresh()
    }

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
            transactionType
        ) ?: emptyList()
    }

    override fun fabClicked() {
        viewState.categoryDialog(null)
    }

    override fun itemClicked(index: Int) {
        editableCurrencyIndex = index

        viewState.categoryDialog(runBlocking {
            App.db.categoryDao().getCategory(editableCurrencyIndex, transactionType)
        })
    }

    override fun provideRemove(index: Int) {
        App.db.categoryDao().delete(index, transactionType)
    }

    fun categoryDialogComplete(
        originalCategory: Category?,
        enteredName: String,
        type: TransactionType
    ) {
        val id = originalCategory?.id ?: 0
        val category = Category(
            id,
            enteredName,
            type
        )

        launch {
            when {

                // Вставлен новый элемент
                originalCategory == null && type == transactionType -> {
                    paginator.proceed(Paginator.Action.Insert(category))
                    App.db.categoryDao().insert(category)
                }

                // Вставлен элемент, но другого типа
                originalCategory == null && type != transactionType -> {
                    App.db.categoryDao().insert(category)
                }

                // Обновлён элемент
                originalCategory != null && type == transactionType -> {
                    paginator.proceed(Paginator.Action.Update(category, editableCurrencyIndex))
                    App.db.categoryDao().update(category)
                }

                // Обновлён элемент, но изменился тип
                originalCategory != null && type != transactionType -> {
                    paginator.proceed(Paginator.Action.Remove(editableCurrencyIndex))
                    App.db.categoryDao().update(category)
                }
            }

            CategoriesUpdatedObservable.updated(this@CategoryScreenPresenter)
        }
    }
}
