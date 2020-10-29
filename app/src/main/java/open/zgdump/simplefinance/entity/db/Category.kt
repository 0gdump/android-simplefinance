package open.zgdump.simplefinance.entity.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import open.zgdump.simplefinance.entity.TransactionType

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: TransactionType
)