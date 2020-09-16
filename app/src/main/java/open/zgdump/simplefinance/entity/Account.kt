package open.zgdump.simplefinance.entity

import androidx.room.Entity
import open.zgdump.simplefinance.global.RoomTablesNames.ACCOUNTS_TABLE_NAME

@Entity(
    tableName = ACCOUNTS_TABLE_NAME,
    primaryKeys = ["name"]
)
data class Account(
    val name: String,
    val currency: String,
    val type: AccountType,
    val closed: Boolean
)