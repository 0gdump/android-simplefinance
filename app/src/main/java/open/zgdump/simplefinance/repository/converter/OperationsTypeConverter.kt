package open.zgdump.simplefinance.repository.converter

import androidx.room.TypeConverter
import open.zgdump.simplefinance.entity.FinancialTypeTransaction

object OperationsTypeConverter {

    @JvmStatic
    @TypeConverter
    fun toOperationsType(value: String) = enumValueOf<FinancialTypeTransaction>(value)

    @JvmStatic
    @TypeConverter
    fun fromOperationsType(value: FinancialTypeTransaction) = value.name
}