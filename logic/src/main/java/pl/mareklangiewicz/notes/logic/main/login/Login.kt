package pl.mareklangiewicz.notes.logic.main.login

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.coroutines.delay
import kotlinx.coroutines.rx2.awaitFirst
import pl.mareklangiewicz.common.V
import pl.mareklangiewicz.common.createState
import pl.mareklangiewicz.common.put
import pl.mareklangiewicz.common.withS
import pl.mareklangiewicz.notes.logic.main.*
import pl.mareklangiewicz.notes.logic.main.MainCommand.*
import pl.mareklangiewicz.notes.logic.main.login.LoginAction.*
import pl.mareklangiewicz.notes.logic.main.notifier.Notifier

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
    commandS: Consumer<MainCommand>,
    notify: Notifier
) = commonS.screenS.withS(Screen.Login) {
    nameS put ""
    nameHintS put "Your fake name"
    nameErrorS put ""
    passS put ""
    passHintS put "Your fake password"
    passErrorS put ""
    loop@ while (true) {
        when (val action = actionS.awaitFirst()) {
            is ChangeName -> {
                nameS put action.name
                nameErrorS put when {
                    action.name.toLowerCase() == "marek" -> "You're not ${action.name}! I'm Marek!"
                    action.name.length < 4 -> "Your fake name \"${action.name}\" is too short"
                    else -> ""
                }
            }
            is ChangePass -> {
                passS put action.pass
                passErrorS put when {
                    action.pass.length < 8 -> "Password has to have at least 8 characters"
                    action.pass.all { it.isLetterOrDigit() } -> "Password has to contain special characters"
                    action.pass.none { it.isLetter() } -> "Password has to contain letters"
                    action.pass.none { it.isDigit() } -> "Password has to contain digits"
                    action.pass.none { it.isUpperCase() } -> "Password has to contain upper case characters"
                    action.pass.none { it.isLowerCase() } -> "Password has to contain lower case characters"
                    else -> ""
                }
            }
            Login -> when {
                nameErrorS.V.isNotEmpty() -> commandS put Hint("Please enter correct fake name first")
                passErrorS.V.isNotEmpty() -> commandS put Hint("Please enter correct fake password first")
                else -> {
                    notify("Let's share your fake password with google translator :)") ?: continue@loop
                    delay(500)
                    commandS put LaunchUrl("https://translate.google.pl/#view=home&op=translate&sl=en&tl=pl&text=${passS.V}")
                    break@loop
                }
            }
            Back -> break@loop
        }
    }
}
