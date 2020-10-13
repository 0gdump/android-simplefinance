package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.global.RoomTablesNames
import open.zgdump.simplefinance.global.RoomTablesNames.RECORDS_TABLE_NAME

@Dao
abstract class RecordDao {

    @Query(
        """
        SELECT *
        FROM records
        WHERE rowid = (
            SELECT rowid 
            FROM records
            LIMIT 1
            OFFSET :index
        )
    """
    )
    abstract suspend fun getRecord(index: Int): Record?

    @Query(
        """
        SELECT *
        FROM records
        WHERE rowid in (
            SELECT rowid 
            FROM records
            WHERE type = :type
            LIMIT :count
            OFFSET :offset
        )
        """
    )
    abstract suspend fun getRecords(
        offset: Int,
        count: Int,
        type: FinancialTypeTransaction
    ): List<Record>?

    @Insert
    abstract suspend fun insert(record: Record)

    @Update
    abstract suspend fun update(record: Record)

    @Query(
        """
        DELETE 
        FROM records
        WHERE rowid = (
            SELECT rowid 
            FROM records
            LIMIT 1 
            OFFSET :index
        )
    """
    )
    abstract fun delete(index: Int)
}