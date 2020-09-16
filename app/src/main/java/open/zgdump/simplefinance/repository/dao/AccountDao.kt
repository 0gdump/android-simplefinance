package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.global.RoomTablesNames.ACCOUNTS_TABLE_NAME


@Dao
interface AccountDao {

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME")
    suspend fun getAll(): List<Account>?

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME WHERE name = :name")
    suspend fun getAccount(name: String): Account?

    @Insert
    suspend fun insert(account: Account)

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)
}