package pl.mareklangiewicz.notes.logic.main

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.coroutines.delay
import pl.mareklangiewicz.common.createState
import pl.mareklangiewicz.common.put
import pl.mareklangiewicz.common.withS
import pl.mareklangiewicz.notes.logic.main.MainCommand.*
import pl.mareklangiewicz.notes.logic.main.home.HomeState
import pl.mareklangiewicz.notes.logic.main.home.logic
import pl.mareklangiewicz.notes.logic.main.login.LoginState
import pl.mareklangiewicz.notes.logic.main.login.logic
import pl.mareklangiewicz.notes.logic.main.notifier.Notifier

interface MainAction

object Back : MainAction

class MainState(val commonS: CommonState = CommonState()) {
    val loginS = LoginState(commonS)
    val homeS = HomeState(commonS, loginS)
}

sealed class MainCommand {
    object Finish : MainCommand()
    data class LaunchUrl(val url: String) : MainCommand()
    data class Hint(val message: String) : MainCommand()
}

class CommonState {
    val screenS = createState(Screen.Empty)
    val isInProgressS = createState(false)
}

suspend fun MainState.logic(
    actionS: Observable<MainAction>,
    commandS: Consumer<MainCommand>,
    notify: Notifier
) = commonS.screenS.withS(Screen.Splash) {
    commandS put Hint("Hello world!")
    delay(2000)
    homeS.logic(actionS, commandS, notify)
    commandS put Hint("Goodbye world!")
    commandS put Finish
}
