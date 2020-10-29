package open.zgdump.simplefinance.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import open.zgdump.simplefinance.entity.db.Account

@Dao
abstract class AccountDao {

    @Query("SELECT * FROM accounts")
    abstract suspend fun getAccounts(): List<Account>?

    @Query(
        """
        SELECT *
        FROM accounts
        WHERE rowid IN (
            SELECT rowid 
            FROM accounts
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
        FROM accounts
        WHERE rowid = (
            SELECT rowid 
            FROM accounts
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

    @Query(
        """
        UPDATE accounts
        SET
            value = initialValue + (
                SELECT TOTAL(value)
                FROM records
                WHERE
                    accountId = :accountId AND
                    type = "Income" -- FinancialTypeTransaction.Income 
            ) - (
                SELECT TOTAL(value)
                FROM records
                WHERE
                    accountId = :accountId AND
                    type = "Expense" -- FinancialTypeTransaction.Expense 
            )
        WHERE
            id = :accountId
        """
    )
    abstract suspend fun updateValueForAccountById(accountId: Int): Int

    @Insert
    abstract suspend fun insert(currency: Account)

    @Update
    abstract suspend fun update(currency: Account)

    @Query(
        """
        DELETE 
        FROM accounts
        WHERE rowid = (
            SELECT rowid 
            FROM accounts
            WHERE isSaving = :isSaving
            LIMIT 1 
            OFFSET :index
        )
    """
    )
    abstract fun delete(index: Int, isSaving: Boolean)
}