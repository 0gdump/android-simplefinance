package open.zgdump.simplefinance.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.global.RoomTablesNames.RECORDS_TABLE_NAME

@Entity(tableName = RECORDS_TABLE_NAME)
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: LocalDate,
    val categoryName: String,
    val categoryId: Int,
    val account: String,
    val accountId: Int,
    val currencyDesignation: String,
    val value: Float,
    val comment: String,
    val type: FinancialTypeTransaction
)