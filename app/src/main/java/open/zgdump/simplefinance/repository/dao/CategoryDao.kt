package open.zgdump.simplefinance.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.entity.FinancialTypeTransaction

@Dao
abstract class CategoryDao {

    @Query("SELECT * FROM categories WHERE type = :type")
    abstract suspend fun getCategories(type: FinancialTypeTransaction): List<Category>?

    @Query(
        """
        SELECT *
        FROM categories
        WHERE rowid in (
            SELECT rowid 
            FROM categories
            WHERE type = :type
            LIMIT :count
            OFFSET :offset
        )
        """
    )
    abstract suspend fun getCategories(
        offset: Int,
        count: Int,
        type: FinancialTypeTransaction
    ): List<Category>?

    @Query(
        """
        SELECT *
        FROM categories
        WHERE rowid = (
            SELECT rowid 
            FROM categories
            WHERE type = :type
            LIMIT 1
            OFFSET :index
        )
    """
    )
    abstract suspend fun getCategory(
        index: Int,
        type: FinancialTypeTransaction
    ): Category?

    @Insert
    abstract suspend fun insert(currency: Category)

    @Update
    abstract suspend fun update(currency: Category)

    @Query(
        """
        DELETE 
        FROM categories
        WHERE rowid = (
            SELECT rowid 
            FROM categories
            WHERE type = :type
            LIMIT 1 
            OFFSET :index
        )
    """
    )
    abstract fun delete(index: Int, type: FinancialTypeTransaction)
}