package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.global.RoomTablesNames.CURRENCIES_TABLE_NAME

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME")
    suspend fun getAll(): List<Currency>?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE id >= :id LIMIT :count")
    suspend fun getCurrenciesFromId(id: Int, count: Int): List<Currency>?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE id = :id")
    suspend fun getById(id: Int): Currency?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE name = :k OR designation = :k")
    suspend fun getByCriteria(k: String): Currency?

    @Insert
    suspend fun insert(currency: Currency)

    @Update
    suspend fun update(currency: Currency)

    @Delete
    suspend fun delete(currency: Currency)
}