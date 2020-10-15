package open.zgdump.simplefinance.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.entity.SumOfRecordsPerDay

@Dao
abstract class RecordDao {

    @Query(
        """
        SELECT 
            SUM(value) as sum,
            date,
            currencyDesignation
        FROM
            records
        WHERE 
            type = :type AND 
            date >= :minDate AND 
            date <= :maxDate
        GROUP BY
            date
        LIMIT
            :count
        OFFSET
            :offset
        """
    )
    abstract suspend fun getSumOfRecordsPerDays(
        offset: Int,
        count: Int,
        minDate: LocalDate,
        maxDate: LocalDate,
        type: FinancialTypeTransaction
    ): List<SumOfRecordsPerDay>?

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

    @Query(
        """
        SELECT *
        FROM records
        WHERE rowid in (
            SELECT rowid 
            FROM records
            WHERE type = :type AND date >= :minDate AND date <= :maxDate
            LIMIT :count
            OFFSET :offset
        )
        """
    )
    abstract suspend fun getRecords(
        offset: Int,
        count: Int,
        minDate: LocalDate,
        maxDate: LocalDate,
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