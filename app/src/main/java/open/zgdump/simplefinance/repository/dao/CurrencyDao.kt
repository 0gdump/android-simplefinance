package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.global.RoomTablesNames.CURRENCIES_TABLE_NAME

@Dao
abstract class CurrencyDao {

    @Query(
        """
        SELECT *
        FROM $CURRENCIES_TABLE_NAME
        WHERE rowid in (
            SELECT rowid 
            FROM $CURRENCIES_TABLE_NAME
            LIMIT :count
            OFFSET :offset
        )
    """
    )
    abstract suspend fun getCurrencies(offset: Int, count: Int): List<Currency>?

    @Query(
        """
        SELECT *
        FROM $CURRENCIES_TABLE_NAME
        WHERE rowid = (
            SELECT rowid 
            FROM $CURRENCIES_TABLE_NAME
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
        FROM $CURRENCIES_TABLE_NAME
        WHERE rowid = (
            SELECT rowid 
            FROM $CURRENCIES_TABLE_NAME
            LIMIT 1 
            OFFSET :index
        )
    """
    )
    abstract fun delete(index: Int)
}