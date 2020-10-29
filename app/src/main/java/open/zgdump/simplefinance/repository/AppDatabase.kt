package open.zgdump.simplefinance.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.repository.converter.LocalDateConverter
import open.zgdump.simplefinance.repository.converter.OperationsTypeConverter
import open.zgdump.simplefinance.repository.dao.*

@Database(
    entities = [
        Account::class,
        Category::class,
        Currency::class,
        Record::class
    ],
    version = 1
)
@TypeConverters(
    LocalDateConverter::class,
    OperationsTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun recordDao(): RecordDao
    abstract fun statisticsDao(): StatisticsDao
}