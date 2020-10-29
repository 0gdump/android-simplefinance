package open.zgdump.simplefinance.repository.converter

import androidx.room.TypeConverter
import open.zgdump.simplefinance.entity.TransactionType

object OperationsTypeConverter {

    @JvmStatic
    @TypeConverter
    fun toOperationsType(value: String) = enumValueOf<TransactionType>(value)

    @JvmStatic
    @TypeConverter
    fun fromOperationsType(value: TransactionType) = value.name
}