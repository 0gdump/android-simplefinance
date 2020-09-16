package open.zgdump.simplefinance.entity

import androidx.room.Entity
import open.zgdump.simplefinance.global.RoomTablesNames.CATEGORIES_TABLE_NAME

@Entity(
    tableName = CATEGORIES_TABLE_NAME,
    primaryKeys = ["name"]
)
data class Category(
    val name: String,
    val parent: String?,
    val type: OperationsType
)