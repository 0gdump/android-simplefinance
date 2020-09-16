package open.zgdump.simplefinance.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import open.zgdump.simplefinance.global.RoomTablesNames.CATEGORIES_TABLE_NAME

@Entity(tableName = CATEGORIES_TABLE_NAME)
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @PrimaryKey val name: String,
    val parent: String?,
    val type: OperationsType
)