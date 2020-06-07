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
import pl.mareklangiewicz.notes.logic.main.login.LoginState
import pl.mareklangiewicz.notes.logic.main.login.logic
import pl.mareklangiewicz.notes.logic.main.notifier.Notifier
import pl.mareklangiewicz.notes.logic.utils.fakeNotes
import kotlin.random.Random

data class Note(val value: String = "", val id: Long = Random.nextLong())

sealed class HomeAction : MainAction {
    data class InsertNote(val pos: Int, val note: Note = Note()) : HomeAction()
    data class RemoveNote(val id: Long) : HomeAction()
    data class ChangeNote(val note: Note) : HomeAction()
    // TODO: MoveNote(oldPos: Int, newPos: Int)
    object StartLogin : HomeAction()
}

class HomeState(val commonS: CommonState = CommonState(), val loginS: LoginState = LoginState(commonS)) {
    val notesS = createState(emptyList<Note>())
}

suspend fun HomeState.logic(
    actionS: Observable<MainAction>,
    commandS: Consumer<MainCommand>,
    notify: Notifier
) = commonS.screenS.withS(Screen.Home) {
    notesS put Random.fakeNotes() // FIXME
    loop@ while (true) {
        when (val action = actionS.awaitFirst()) {
            is StartLogin -> loginS.logic(actionS, commandS, notify)
            is InsertNote -> notesS put
                    notesS.V.slice(0 until action.pos) + action.note + notesS.V.slice(action.pos until notesS.V.size)
            is RemoveNote -> {
                val pos = notesS.V.indexOfFirst { it.id == action.id }
                notify("Are you sure?", "Remove note", "Yes", "No") == "Yes" || continue@loop
                notesS put notesS.V.slice(0 until pos) + notesS.V.slice(pos + 1 until notesS.V.size)
            }
            is ChangeNote -> {
                val pos = notesS.V.indexOfFirst { it.id == action.note.id }
                notesS put notesS.V.slice(0 until pos) + action.note + notesS.V.slice(pos + 1 until notesS.V.size)
            }
            Back -> break@loop
        }
    }
}
