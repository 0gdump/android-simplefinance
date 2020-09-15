package open.zgdump.simplefinance.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val category: Int,
    val account: Int,
    val amount: Int,
    val date: Long,
    val comment: String,
    val type: Type
)