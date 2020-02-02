package pl.mareklangiewicz.notes.logic.main

import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import kotlinx.coroutines.delay
import pl.mareklangiewicz.common.put
import pl.mareklangiewicz.notes.logic.main.login.LoginLogic

suspend fun MainLogic(
    actionS: Observable<MainAction>,
    state: MainState
) {
    state.screenS put Screen.Splash
    delay(2000)
    LoginLogic(actionS.ofType(), state.login, state.screenS)
}
