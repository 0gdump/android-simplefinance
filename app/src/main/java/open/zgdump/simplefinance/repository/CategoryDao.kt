package open.zgdump.simplefinance.repository

import androidx.room.*
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.global.RoomTablesNames.CATEGORIES_TABLE_NAME

@Dao
interface CategoryDao {

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME")
    fun getAll(): List<Category>?

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME WHERE id = :id")
    fun getCategory(id: Long): Category?

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME WHERE name = :name")
    fun getCategory(name: String): Category?

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME WHERE parent = :parent")
    fun getChildren(parent: String): List<Category>?

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME WHERE parent = NULL")
    fun getRootCategories(): List<Category>?

    @Insert
    fun insert(employee: Category)

    @Update
    fun update(employee: Category)

    @Delete
    fun delete(employee: Category)
}