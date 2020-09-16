package open.zgdump.simplefinance.repository.converter

import androidx.room.TypeConverter
import open.zgdump.simplefinance.entity.AccountType

object AccountTypeConverter {

    @JvmStatic
    @TypeConverter
    fun toAccountType(value: String) = enumValueOf<AccountType>(value)

    @JvmStatic
    @TypeConverter
    fun fromAccountType(value: AccountType) = value.name
}