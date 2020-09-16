package open.zgdump.simplefinance.repository

import androidx.room.*
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.global.RoomTablesNames.CURRENCIES_TABLE_NAME

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME")
    fun getAll(): List<Currency>?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE id = :id")
    fun getCurrency(id: Long): Currency?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE char = :char")
    fun getCurrency(char: Char): Currency?

    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME WHERE name = :name")
    fun getCurrency(name: String): Currency?

    @Insert
    fun insert(employee: Currency)

    @Update
    fun update(employee: Currency)

    @Delete
    fun delete(employee: Currency)
}