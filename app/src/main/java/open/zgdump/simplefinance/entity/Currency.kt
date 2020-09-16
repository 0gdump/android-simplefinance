package open.zgdump.simplefinance.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import open.zgdump.simplefinance.global.RoomTablesNames.CURRENCIES_TABLE_NAME

@Entity(tableName = CURRENCIES_TABLE_NAME)
data class Currency(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @PrimaryKey val char: Char,
    @PrimaryKey val name: String
)