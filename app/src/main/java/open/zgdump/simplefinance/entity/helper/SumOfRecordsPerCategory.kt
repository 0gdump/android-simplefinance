package open.zgdump.simplefinance.entity.helper

import androidx.room.Entity

@Entity
data class SumOfRecordsPerCategory(
    val sum: Int,
    val currencyDesignation: String,
    val categoryId: Int,
    val categoryName: String,
)