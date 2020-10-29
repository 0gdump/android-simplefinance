package open.zgdump.simplefinance.entity.helper

import androidx.room.Entity
import kotlinx.datetime.LocalDate

@Entity
data class SumOfRecordsPerDay(
    val sum: Int,
    val date: LocalDate,
    val currencyDesignation: String,
)