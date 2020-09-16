package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.global.RoomTablesNames.RECORDS_TABLE_NAME

@Dao
interface RecordDao {

    @Query("SELECT * FROM $RECORDS_TABLE_NAME")
    suspend fun getAll(): List<Record>?

    @Query("SELECT * FROM $RECORDS_TABLE_NAME WHERE id = :id")
    suspend fun getAccount(id: Long): Record?

    @Insert
    suspend fun insert(record: Record)

    @Update
    suspend fun update(record: Record)

    @Delete
    suspend fun delete(record: Record)
}