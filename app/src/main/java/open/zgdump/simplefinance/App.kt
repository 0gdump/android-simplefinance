package open.zgdump.simplefinance

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import open.zgdump.simplefinance.repository.AppDatabase
import open.zgdump.simplefinance.util.kotlin.initOnce
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber


class App : Application() {

    companion object {

        // Android

        var appContext: Context by initOnce()
            private set

        var instance: App by initOnce()
            private set

        var res: Resources by initOnce()
            private set

        // Navigation

        private var cicerone: Cicerone<Router> by initOnce()

        val navigationHolder
            get() = cicerone.navigatorHolder

        val router
            get() = cicerone.router

        // Backend

        var db: AppDatabase by initOnce()
            private set
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        instance = this
        res = resources

        cicerone = Cicerone.create()

        db = Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "database"
            )
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}