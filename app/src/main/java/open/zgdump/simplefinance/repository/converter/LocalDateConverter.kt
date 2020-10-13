package open.zgdump.simplefinance.repository.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import open.zgdump.simplefinance.util.kotlin.ofEpochDay
import open.zgdump.simplefinance.util.kotlin.toEpochDay
import java.util.Date

object LocalDateConverter {

    @JvmStatic
    @TypeConverter
    fun toLocalDate(value: Int) = ofEpochDay(value)

    @JvmStatic
    @TypeConverter
    fun fromLocalDate(date: LocalDate) = date.toEpochDay()
}