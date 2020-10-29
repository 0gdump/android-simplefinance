package open.zgdump.simplefinance.entity.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val value: Float,
    val initialValue: Float,
    val currencyDesignation: String,
    val isSaving: Boolean,
    val isClosed: Boolean
)