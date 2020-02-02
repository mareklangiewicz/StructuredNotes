package pl.mareklangiewicz.notes.logic.main

import com.jakewharton.rxrelay2.BehaviorRelay
import pl.mareklangiewicz.notes.logic.main.login.LoginState

class MainState {
    val screenS = BehaviorRelay.createDefault(Screen.None)
    val login = LoginState()
}