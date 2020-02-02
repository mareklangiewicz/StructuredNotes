package pl.mareklangiewicz.notes.logic.main.login

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.coroutines.rx2.awaitFirst
import pl.mareklangiewicz.common.put
import pl.mareklangiewicz.notes.logic.main.Back
import pl.mareklangiewicz.notes.logic.main.MainAction
import pl.mareklangiewicz.notes.logic.main.Screen

suspend fun LoginLogic(
    actionS: Observable<MainAction>,
    state: LoginState,
    screenS: Consumer<Screen>
) {
    state.nameS put ""
    state.nameHintS put "Your fake name"
    state.nameErrorS put ""
    state.passS put ""
    state.passHintS put "Your fake password"
    state.passErrorS put ""
    screenS put Screen.Login
    // TODO: funny logic with hints and errors
    loop@ while (true) {
        when (val action = actionS.awaitFirst()) {
            is LoginAction.ChangeName -> state.nameS put action.name
            is LoginAction.ChangePass -> state.passS put action.pass
            LoginAction.Login -> break@loop
            Back -> return // false or sth..
        }
    }
}
