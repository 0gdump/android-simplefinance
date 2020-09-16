package open.zgdump.simplefinance.repository.converter

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @JvmStatic
    @TypeConverter
    fun toDate(value: Long) = Date(value)

    @JvmStatic
    @TypeConverter
    fun fromDate(date: Date) = date.time
}