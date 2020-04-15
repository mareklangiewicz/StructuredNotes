package pl.mareklangiewicz.notes.logic.main

import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import kotlinx.coroutines.delay
import pl.mareklangiewicz.common.createState
import pl.mareklangiewicz.common.withS
import pl.mareklangiewicz.notes.logic.main.login.LoginLogic
import pl.mareklangiewicz.notes.logic.main.login.LoginState

interface MainAction

object Back : MainAction

class MainState(val commonS: CommonState = CommonState()) {
    val loginS = LoginState(commonS)
}

class CommonState {
    val screenS = createState(Screen.Empty)
    val isInProgressS = createState(false)
}

suspend fun MainState.MainLogic(actionS: Observable<MainAction>) = commonS.screenS.withS(Screen.Splash) {
    delay(2000)
    loginS.LoginLogic(actionS.ofType())
}
