package pl.mareklangiewicz.notes

import android.app.Application
import pl.mareklangiewicz.notes.main.MainModel

class NotesApp : Application() {


    override fun onCreate() {
        super.onCreate()
        val mainModel = MainModel()
        DI.provideMainModel = { mainModel }
    }


}