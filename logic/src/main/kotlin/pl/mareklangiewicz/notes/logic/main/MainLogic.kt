package pl.mareklangiewicz.notes.logic.main

import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import pl.mareklangiewicz.notes.logic.main.login.LoginLogic

suspend fun MainLogic(
    actionS: Observable<MainAction>,
    state: MainState
) {
    LoginLogic(actionS.ofType(), state.login)
}