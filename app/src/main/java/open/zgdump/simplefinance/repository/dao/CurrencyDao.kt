package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.global.RoomTablesNames.CURRENCIES_TABLE_NAME

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME")
    suspend fun getAll(): List<Currency>?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE id = :id")
    suspend fun getCurrency(id: Long): Currency?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE char = :char")
    suspend fun getCurrency(char: Char): Currency?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE name = :name")
    suspend fun getCurrency(name: String): Currency?

    @Insert
    suspend fun insert(currency: Currency)

    @Update
    suspend fun update(currency: Currency)

    @Delete
    suspend fun delete(currency: Currency)
}