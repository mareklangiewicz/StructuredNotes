package pl.mareklangiewicz.notes.logic.main

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.coroutines.delay
import pl.mareklangiewicz.common.createState
import pl.mareklangiewicz.common.put
import pl.mareklangiewicz.common.withS
import pl.mareklangiewicz.notes.logic.main.MainCommand.*
import pl.mareklangiewicz.notes.logic.main.login.LoginState
import pl.mareklangiewicz.notes.logic.main.login.logic

interface MainAction

object Back : MainAction

class MainState(val commonS: CommonState = CommonState()) {
    val loginS = LoginState(commonS)
}

sealed class MainCommand {
    object Finish : MainCommand()
    data class LaunchUrl(val url: String) : MainCommand()
    data class Hint(val message: CharSequence) : MainCommand()
}

class CommonState {
    val screenS = createState(Screen.Empty)
    val isInProgressS = createState(false)
}

suspend fun MainState.MainLogic(
    actionS: Observable<MainAction>,
    commandS: Consumer<MainCommand>
) = commonS.screenS.withS(Screen.Splash) {
    commandS put Hint("Hello world!")
    delay(2000)
    loginS.logic(actionS, commandS)
    commandS put Hint("Goodbye world!")
    commandS put Finish
}
