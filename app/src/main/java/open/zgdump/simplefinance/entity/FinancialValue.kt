package open.zgdump.simplefinance.entity

import androidx.room.Entity

@Entity
data class FinancialValue(
    val total: Float,
    val currencyDesignation: String
)
