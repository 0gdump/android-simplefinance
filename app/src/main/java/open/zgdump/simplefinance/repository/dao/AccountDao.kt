package open.zgdump.simplefinance.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.global.RoomTablesNames.ACCOUNTS_TABLE_NAME

@Dao
abstract class AccountDao {

    @Query(
        """
        SELECT *
        FROM $ACCOUNTS_TABLE_NAME
        WHERE rowid in (
            SELECT rowid 
            FROM $ACCOUNTS_TABLE_NAME
            WHERE isSaving = :isSaving
            LIMIT :count
            OFFSET :offset
        )
        """
    )
    abstract suspend fun getAccounts(
        offset: Int,
        count: Int,
        isSaving: Boolean
    ): List<Account>?

    @Query(
        """
        SELECT *
        FROM $ACCOUNTS_TABLE_NAME
        WHERE rowid = (
            SELECT rowid 
            FROM $ACCOUNTS_TABLE_NAME
            WHERE isSaving = :isSaving
            LIMIT 1
            OFFSET :index
        )
    """
    )
    abstract suspend fun getAccount(
        index: Int,
        isSaving: Boolean
    ): Account?

    @Insert
    abstract suspend fun insert(currency: Account)

    @Update
    abstract suspend fun update(currency: Account)

    @Query(
        """
        DELETE 
        FROM $ACCOUNTS_TABLE_NAME
        WHERE rowid = (
            SELECT rowid 
            FROM $ACCOUNTS_TABLE_NAME
            WHERE isSaving = :isSaving
            LIMIT 1 
            OFFSET :index
        )
    """
    )
    abstract fun delete(index: Int, isSaving: Boolean)
}