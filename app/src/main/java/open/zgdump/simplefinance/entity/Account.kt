package open.zgdump.simplefinance.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import open.zgdump.simplefinance.global.RoomTablesNames.ACCOUNTS_TABLE_NAME

@Entity(tableName = ACCOUNTS_TABLE_NAME)
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @PrimaryKey val name: String,
    val currency: Char,
    val type: AccountType,
    val closed: Boolean
)