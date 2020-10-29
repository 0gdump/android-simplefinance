package open.zgdump.simplefinance.entity.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import open.zgdump.simplefinance.entity.TransactionType

@Entity(tableName = "records")
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
    val type: TransactionType
)