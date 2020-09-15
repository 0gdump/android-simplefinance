package open.zgdump.simplefinance.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val currency: Int,
    val type: AccountType,
    val closed: Boolean
)