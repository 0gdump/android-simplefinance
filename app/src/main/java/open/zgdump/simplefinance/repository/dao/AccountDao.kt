package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.global.RoomTablesNames.ACCOUNTS_TABLE_NAME


@Dao
interface AccountDao {

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME")
    fun getAll(): List<Account>?

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME WHERE id = :id")
    fun getAccount(id: Long): Account?

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME WHERE name = :name")
    fun getAccount(name: String): Account?

    @Insert
    fun insert(employee: Account)

    @Update
    fun update(employee: Account)

    @Delete
    fun delete(employee: Account)
}