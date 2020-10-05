package open.zgdump.simplefinance

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.cioccarellia.ksprefs.KsPrefs
import open.zgdump.simplefinance.repository.AppDatabase
import open.zgdump.simplefinance.util.kotlin.initOnce
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber


class App : Application() {

    companion object {

        // App

        var isFirstRun: Boolean
            get() = prefs.pull("firstRun", true)
            set(value) = prefs.push("firstRun", value)

        val isDebugRun
            get() = BuildConfig.DEBUG

        // Android

        var appContext: Context by initOnce()
            private set

        var instance: App by initOnce()
            private set

        var res: Resources by initOnce()
            private set

        val prefs by lazy { KsPrefs(appContext) }

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

        initialize()

        if (isDebugRun) debugRun()
        if (isFirstRun) firstRun()
    }

    private fun initialize() {

        appContext = applicationContext
        instance = this
        res = resources

        cicerone = Cicerone.create()

        db = Room
            .databaseBuilder(appContext, AppDatabase::class.java, "database")
            .build()
    }

    private fun debugRun() {
        Timber.plant(Timber.DebugTree())
    }

    private fun firstRun() {
        isFirstRun = false
    }
}