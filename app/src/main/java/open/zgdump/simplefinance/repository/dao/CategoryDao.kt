package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.global.RoomTablesNames.CATEGORIES_TABLE_NAME

@Dao
interface CategoryDao {

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME")
    suspend fun getAll(): List<Category>?

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME WHERE id = :id")
    suspend fun getCategory(id: Long): Category?

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME WHERE name = :name")
    suspend fun getCategory(name: String): Category?

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME WHERE parent = :parent")
    suspend fun getChildren(parent: String): List<Category>?

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME WHERE parent = NULL")
    suspend fun getRootCategories(): List<Category>?

    @Insert
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)
}