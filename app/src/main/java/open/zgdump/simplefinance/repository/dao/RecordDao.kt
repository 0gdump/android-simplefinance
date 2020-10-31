package open.zgdump.simplefinance.repository.dao

import androidx.room.*
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.entity.TransactionType
import open.zgdump.simplefinance.entity.db.Record
import open.zgdump.simplefinance.entity.helper.SumOfRecordsPerCategory
import open.zgdump.simplefinance.entity.helper.SumOfRecordsPerDay

@Dao
abstract class RecordDao {

    @Query(
        """
        SELECT SUM(value)
        FROM records
        WHERE 
            type = :type AND
            date >= :minDate AND
            date <= :maxDate
        """
    )
    abstract suspend fun getSumOfRecords(
        minDate: LocalDate,
        maxDate: LocalDate,
        type: TransactionType
    ): Float?

    @Query(
        """
        SELECT 
            SUM(value) as sum,
            date,
            currencyDesignation
        FROM records
        WHERE 
            type = :type AND 
            date >= :minDate AND 
            date <= :maxDate
        GROUP BY date
        LIMIT :count
        OFFSET :offset
        """
    )
    abstract suspend fun getSumOfRecordsPerDays(
        offset: Int,
        count: Int,
        minDate: LocalDate,
        maxDate: LocalDate,
        type: TransactionType
    ): List<SumOfRecordsPerDay>?

    @Query(
        """
        SELECT 
            SUM(value) as sum,
            currencyDesignation,
            categoryId,
            categoryName
        FROM records
        WHERE 
            type = :type AND 
            date >= :minDate AND 
            date <= :maxDate
        GROUP BY categoryId
        LIMIT :count
        OFFSET  :offset
        """
    )
    abstract suspend fun getSumOfRecordsPerCategories(
        offset: Int,
        count: Int,
        minDate: LocalDate,
        maxDate: LocalDate,
        type: TransactionType
    ): List<SumOfRecordsPerCategory>?

    @Query(
        """
        SELECT *
        FROM records
        WHERE rowid = (
            SELECT rowid 
            FROM records
            ORDER BY rowid DESC
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
            ORDER BY rowid DESC
            LIMIT :count
            OFFSET :offset
        )
        ORDER BY rowid DESC
        """
    )
    abstract suspend fun getRecords(
        offset: Int,
        count: Int,
        type: TransactionType
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
        type: TransactionType
    ): List<Record>?

    @Insert
    abstract suspend fun rawInsert(record: Record)

    @Transaction
    open suspend fun insert(record: Record) {
        rawInsert(record)
        App.db.accountDao().updateValueForAccountById(record.accountId)
    }

    @Update
    abstract suspend fun rawUpdate(record: Record)

    @Transaction
    open suspend fun update(record: Record) {
        rawUpdate(record)
        App.db.accountDao().updateValueForAccountById(record.accountId)
    }

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