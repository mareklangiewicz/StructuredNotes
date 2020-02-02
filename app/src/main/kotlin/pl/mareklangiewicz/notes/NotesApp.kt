package pl.mareklangiewicz.notes

import android.app.Application
import android.util.Log
import pl.mareklangiewicz.common.LogLevel
import pl.mareklangiewicz.common.Logger
import pl.mareklangiewicz.common.memoize
import pl.mareklangiewicz.common.DI as CommonDI
import pl.mareklangiewicz.notes.main.MainModel

class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        CommonDI.provideLogger = memoize { AndroLogger() }
        DI.provideMainModel = memoize { MainModel() }
    }
}

class AndroLogger : Logger {
    override fun invoke(message: String, level: LogLevel) {
        if (BuildConfig.DEBUG) Log.println(level.nr, "nts", message)
    }
}
