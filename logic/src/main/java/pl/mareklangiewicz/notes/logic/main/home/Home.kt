package pl.mareklangiewicz.notes.logic.main.home

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.coroutines.rx2.awaitFirst
import pl.mareklangiewicz.common.V
import pl.mareklangiewicz.common.createState
import pl.mareklangiewicz.common.put
import pl.mareklangiewicz.common.withS
import pl.mareklangiewicz.notes.logic.main.*
import pl.mareklangiewicz.notes.logic.main.home.HomeAction.*
import pl.mareklangiewicz.notes.logic.main.login.LoginAction.*
import pl.mareklangiewicz.notes.logic.main.login.LoginState
import pl.mareklangiewicz.notes.logic.main.login.logic
import pl.mareklangiewicz.notes.logic.main.notifier.Notifier

sealed class HomeAction : MainAction {
    data class InsertNote(val idx: Int, val note: String = "") : HomeAction()
    data class RemoveNote(val idx: Int) : HomeAction()
    data class ChangeNote(val idx: Int, val note: String) : HomeAction()
    object StartLogin : HomeAction()
}

class HomeState(val commonS: CommonState = CommonState(), val loginS: LoginState = LoginState(commonS)) {
    val notesS = createState(emptyList<String>())
}

suspend fun HomeState.logic(
    actionS: Observable<MainAction>,
    commandS: Consumer<MainCommand>,
    notify: Notifier
) = commonS.screenS.withS(Screen.Home) {
    loop@ while (true) {
        when (val action = actionS.awaitFirst()) {
            is StartLogin -> loginS.logic(actionS, commandS, notify)
            is InsertNote -> notesS put
                    notesS.V.slice(0 until action.idx) +
                    action.note +
                    notesS.V.slice(action.idx until notesS.V.size)
            is RemoveNote -> {
                notify("Are you sure?", "Remove note", "Yes", "No") == "Yes" || continue@loop
                notesS put
                        notesS.V.slice(0 until action.idx) +
                        notesS.V.slice(action.idx + 1 until notesS.V.size)
            }
            is ChangeNote -> notesS put
                    notesS.V.slice(0 until action.idx) +
                    action.note +
                    notesS.V.slice(action.idx + 1 until notesS.V.size)
            Back -> break@loop
        }
    }
}
