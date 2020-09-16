package open.zgdump.simplefinance.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import open.zgdump.simplefinance.entity.Account
import open.zgdump.simplefinance.entity.Category
import open.zgdump.simplefinance.entity.Currency
import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.repository.converter.AccountTypeConverter
import open.zgdump.simplefinance.repository.converter.DateConverter
import open.zgdump.simplefinance.repository.converter.OperationsTypeConverter
import open.zgdump.simplefinance.repository.dao.AccountDao
import open.zgdump.simplefinance.repository.dao.CategoryDao
import open.zgdump.simplefinance.repository.dao.CurrencyDao
import open.zgdump.simplefinance.repository.dao.RecordDao

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
    DateConverter::class,
    AccountTypeConverter::class,
    OperationsTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun recordDao(): RecordDao
}