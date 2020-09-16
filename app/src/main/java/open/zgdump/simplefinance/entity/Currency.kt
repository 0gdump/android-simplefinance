package open.zgdump.simplefinance.entity

import androidx.room.Entity
import open.zgdump.simplefinance.global.RoomTablesNames.CURRENCIES_TABLE_NAME

@Entity(
    tableName = CURRENCIES_TABLE_NAME,
    primaryKeys = ["designation", "name"]
)
data class Currency(
    val designation: String,
    val name: String
)