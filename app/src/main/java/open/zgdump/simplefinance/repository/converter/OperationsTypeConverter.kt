package open.zgdump.simplefinance.repository.converter

import androidx.room.TypeConverter
import open.zgdump.simplefinance.entity.OperationsType

object OperationsTypeConverter {

    @JvmStatic
    @TypeConverter
    fun toOperationsType(value: String) = enumValueOf<OperationsType>(value)

    @JvmStatic
    @TypeConverter
    fun fromOperationsType(value: OperationsType) = value.name
}