package open.zgdump.simplefinance.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import open.zgdump.simplefinance.global.RoomTablesNames.RECORDS_TABLE_NAME
import java.util.*

@Entity(tableName = RECORDS_TABLE_NAME)
data class Record(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val account: String,
    val category: String,
    val amount: Int,
    val date: Date,
    val comment: String,
    val type: FinancialTypeTransaction
)