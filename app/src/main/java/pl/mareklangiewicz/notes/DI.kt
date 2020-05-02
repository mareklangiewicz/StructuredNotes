package pl.mareklangiewicz.notes

import androidx.appcompat.app.AppCompatActivity
import pl.mareklangiewicz.notes.main.MainActivity
import pl.mareklangiewicz.notes.main.MainModelContract

object DI {
    lateinit var provideMainModel: () -> MainModelContract
    var provideMainActivity: () -> MainActivity? = { null }
}

// can be changed if we have more activities
fun DI.provideActivity() = provideMainActivity() as AppCompatActivity?
