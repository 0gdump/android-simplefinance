package open.zgdump.simplefinance.entity.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val designation: String,
)