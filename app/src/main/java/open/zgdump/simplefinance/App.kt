package open.zgdump.simplefinance

import android.app.Application
import android.content.Context
import android.content.res.Resources
import open.zgdump.simplefinance.util.kotlin.initOnce
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router


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
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        instance = this
        res = resources
        cicerone = Cicerone.create()
    }
}