package open.zgdump.simplefinance.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import open.zgdump.simplefinance.global.RoomTablesNames.CURRENCIES_TABLE_NAME

@Entity(tableName = CURRENCIES_TABLE_NAME)
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val designation: String,
    val name: String
)