package open.zgdump.simplefinance.repository.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.entity.TransactionType
import open.zgdump.simplefinance.entity.helper.FinancialValue

@Dao
abstract class StatisticsDao {

    // TODO: Move this into RecordDao
    @Query(
        """
        SELECT 
            SUM(value) as total,
            currencyDesignation
        FROM records
        WHERE 
            type = :type AND
            date >= :minDate AND
            date <= :maxDate
        GROUP BY currencyDesignation
        LIMIT :limit
        """
    )
    abstract suspend fun getSumOfRecordsForCurrencies(
        minDate: LocalDate,
        maxDate: LocalDate,
        type: TransactionType,
        limit: Int
    ): List<FinancialValue>

    @Query(
        """
        SELECT
            SUM(value) as total,
            currencyDesignation
        FROM accounts
        GROUP BY currencyDesignation
        LIMIT :limit
    """
    )
    abstract suspend fun getSumOfValuesForCurrencies(
        limit: Int
    ): List<FinancialValue>

    @Query(
        """
        SELECT
            SUM(value) as total,
            currencyDesignation
        FROM accounts
        WHERE isSaving = :isSaving
        GROUP BY currencyDesignation
        LIMIT :limit
    """
    )
    abstract suspend fun getSumOfValuesForCurrenciesByCriteria(
        limit: Int,
        isSaving: Boolean
    ): List<FinancialValue>
}