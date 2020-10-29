package open.zgdump.simplefinance.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import open.zgdump.simplefinance.entity.db.Currency

@Dao
abstract class CurrencyDao {

    @Query("SELECT * FROM currencies")
    abstract suspend fun getCurrencies(): List<Currency>?

    @Query(
        """
        SELECT *
        FROM currencies
        WHERE rowid in (
            SELECT rowid 
            FROM currencies
            LIMIT :count
            OFFSET :offset
        )
    """
    )
    abstract suspend fun getCurrencies(offset: Int, count: Int): List<Currency>?

    @Query(
        """
        SELECT *
        FROM currencies
        WHERE rowid = (
            SELECT rowid 
            FROM currencies
            LIMIT 1
            OFFSET :index
        )
    """
    )
    abstract suspend fun getCurrency(index: Int): Currency?

    @Insert
    abstract suspend fun insert(currency: Currency)

    @Update
    abstract suspend fun update(currency: Currency)

    @Query(
        """
        DELETE 
        FROM currencies
        WHERE rowid = (
            SELECT rowid 
            FROM currencies
            LIMIT 1 
            OFFSET :index
        )
    """
    )
    abstract fun delete(index: Int)
}