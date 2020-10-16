package open.zgdump.simplefinance.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.entity.FinancialTypeTransaction
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.entity.SumOfRecordsPerCategory
import open.zgdump.simplefinance.entity.SumOfRecordsPerDay

@Dao
abstract class RecordDao {

    @Query(
        """
        SELECT 
            SUM(value)
        FROM
            records
        WHERE 
            type = :type AND
            date >= :minDate AND
            date <= :maxDate
        """
    )
    abstract suspend fun getSumOfRecords(
        minDate: LocalDate,
        maxDate: LocalDate,
        type: FinancialTypeTransaction
    ): Float?

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
        SELECT 
            SUM(value) as sum,
            currencyDesignation,
            categoryId,
            categoryName
        FROM
            records
        WHERE 
            type = :type AND 
            date >= :minDate AND 
            date <= :maxDate
        GROUP BY
            categoryId
        LIMIT
            :count
        OFFSET
            :offset
        """
    )
    abstract suspend fun getSumOfRecordsPerCategories(
        offset: Int,
        count: Int,
        minDate: LocalDate,
        maxDate: LocalDate,
        type: FinancialTypeTransaction
    ): List<SumOfRecordsPerCategory>?

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