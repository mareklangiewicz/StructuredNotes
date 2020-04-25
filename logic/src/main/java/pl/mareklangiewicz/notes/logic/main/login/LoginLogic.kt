package pl.mareklangiewicz.notes.logic.main.login

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.coroutines.delay
import kotlinx.coroutines.rx2.awaitFirst
import pl.mareklangiewicz.common.createState
import pl.mareklangiewicz.common.put
import pl.mareklangiewicz.common.withS
import pl.mareklangiewicz.notes.logic.main.*
import pl.mareklangiewicz.notes.logic.main.MainCommand.*

sealed class LoginAction : MainAction {
    data class ChangeName(val name: String) : LoginAction()
    data class ChangePass(val pass: String) : LoginAction()
    object Login : LoginAction()
}

class LoginState(val commonS: CommonState = CommonState()) {
    val nameS = createState("")
    val nameHintS = createState("")
    val nameErrorS = createState("")
    val passS = createState("")
    val passHintS = createState("")
    val passErrorS = createState("")
}

suspend fun LoginState.logic(
    actionS: Observable<MainAction>,
    commandS: Consumer<MainCommand>
) = commonS.screenS.withS(Screen.Login) {

    commandS put Hint("Let's translate something to polish")
    delay(1000)
    commandS put LaunchUrl("https://translate.google.pl/#view=home&op=translate&sl=en&tl=pl&text=something")

    nameS put ""
    nameHintS put "Your fake name"
    nameErrorS put ""
    passS put ""
    passHintS put "Your fake password"
    passErrorS put ""
    // TODO: funny logic with hints and errors
    loop@ while (true) {
        when (val action = actionS.awaitFirst()) {
            is LoginAction.ChangeName -> nameS put action.name
            is LoginAction.ChangePass -> passS put action.pass
            LoginAction.Login -> break@loop
            Back -> break@loop // false or sth..
        }
    }
}
