package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.global.RoomTablesNames.RECORDS_TABLE_NAME

@Dao
interface RecordDao {

    @Query("SELECT * FROM $RECORDS_TABLE_NAME")
    fun getAll(): List<Record>?

    @Query("SELECT * FROM $RECORDS_TABLE_NAME WHERE id = :id")
    fun getAccount(id: Long): Record?

    @Insert
    fun insert(employee: Record)

    @Update
    fun update(employee: Record)

    @Delete
    fun delete(employee: Record)
}