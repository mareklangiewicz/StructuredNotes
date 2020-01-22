package pl.mareklangiewicz.notes

import pl.mareklangiewicz.notes.main.MainModelContract

object DI {
    lateinit var provideMainModel: () -> MainModelContract
    // TODO: logger; setup log actionS in right place
}