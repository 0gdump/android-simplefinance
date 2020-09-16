package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.global.RoomTablesNames.CURRENCIES_TABLE_NAME

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME")
    suspend fun getAll(): List<Currency>?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE name = :k OR designation = :k")
    suspend fun getCurrency(k: String): Currency?

    @Insert
    suspend fun insert(currency: Currency)

    @Update
    suspend fun update(currency: Currency)

    @Delete
    suspend fun delete(currency: Currency)
}