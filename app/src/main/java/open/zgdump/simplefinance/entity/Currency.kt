package open.zgdump.simplefinance.entity

import androidx.room.Entity
import open.zgdump.simplefinance.global.RoomTablesNames.CURRENCIES_TABLE_NAME

@Entity(
    tableName = CURRENCIES_TABLE_NAME,
    primaryKeys = ["id", "name"]
)
data class Currency(
    val id: Int,
    val designation: String,
    val name: String
)