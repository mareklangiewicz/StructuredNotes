package pl.mareklangiewicz.notes.logic.main.login

import io.reactivex.Observable
import kotlinx.coroutines.rx2.awaitFirst
import pl.mareklangiewicz.notes.logic.common.put

suspend fun LoginLogic(
    actionS: Observable<LoginAction>,
    state: LoginState
) {
    // TODO: funny logic with hints and errors
    loop@ while (true) {
        when (val action = actionS.awaitFirst()) {
            is LoginAction.ChangeName -> state.nameS put action.name
            is LoginAction.ChangePass -> state.passS put action.pass
            LoginAction.Login -> break@loop
        }
    }
}