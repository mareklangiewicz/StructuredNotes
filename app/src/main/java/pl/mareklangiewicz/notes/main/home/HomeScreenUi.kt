package pl.mareklangiewicz.notes.main.home

import android.content.Context
import android.util.AttributeSet
import io.reactivex.Observable
import pl.mareklangiewicz.common.subscribeUntil
import pl.mareklangiewicz.notes.logic.main.home.HomeAction
import pl.mareklangiewicz.notes.logic.main.home.Note
import pl.mareklangiewicz.notes.logic.utils.fakeNotes
import pl.mareklangiewicz.notes.main.MainModelContract
import pl.mareklangiewicz.notes.main.ScreenUi
import pl.mareklangiewicz.notes.widgets.*
import pl.mareklangiewicz.recyclerui.ItemUi
import pl.mareklangiewicz.recyclerui.RecyclerUi
import pl.mareklangiewicz.recyclerui.WithId
import splitties.views.dsl.appcompat.toolbar
import splitties.views.dsl.core.*
import splitties.views.dsl.material.MaterialComponentsStyles
import splitties.views.onClick
import kotlin.random.Random

class HomeScreenUi(ctx: Context) : ScreenUi<HomeUiContract>(HomeUi(ctx)) {

    override fun <U> bindUntil(untilS: Observable<U>, model: MainModelContract) {

        model.mainS.homeS.run {
            notesS.subscribeUntil(untilS) { ui.notes = it }
        }

        Observable.mergeArray(
            ui.noteChangeS.map { HomeAction.ChangeNote(it) },
            ui.addNoteClickS.map { HomeAction.InsertNote(it) },
            ui.remNoteClickS.map { HomeAction.RemoveNote(it) },
            ui.loginClickS.map { HomeAction.StartLogin },
            ui.crashClickS.map { HomeAction.TestCrash }
        ).subscribeUntil(untilS, model.actionS)
    }
}

interface HomeUiContract : Ui {
    var notes: List<Note>
    val noteChangeS: Observable<Note>
    val addNoteClickS: Observable<Int>
    val remNoteClickS: Observable<Long>
    val loginClickS: Observable<Unit>
    val crashClickS: Observable<Unit>
}

@Suppress("EXPERIMENTAL_API_USAGE")
class HomeUi(override val ctx: Context) : HomeUiContract {

    override val root = verticalLayout()

    private val material = MaterialComponentsStyles(ctx)

    private val toolbar = toolbar { title = "Notes" }.addV(root)

    // FIXME: move to toolbar
    private val loginButton = material.button.outlined { text = "Login" }.addV(root, 32)
    private val crashButton = material.button.outlined { text = "Crash" }.addV(root, 32)

    private val inputBox = horizontalLayout().addV(root, 32)

    private val inputField = editText().addH(inputBox) { weight = 1f }

    private val inputConfirm = material.button.outlined { text = "✓" }.addH(inputBox)

    private val recyclerUi = RecyclerUi(ctx, ::TextItemUi) {}.addV(root, 32)

    override var notes: List<Note>
        get() = recyclerUi.items.map { Note(it.text, it.id) }
        set(value) { recyclerUi.items = value.map { TextItem(it.value, it.id) } }

    override val noteChangeS: Observable<Note> = Observable.never() // TODO

    override val addNoteClickS: Observable<Int> = Observable.never() // TODO

    override val remNoteClickS: Observable<Long> = Observable.never() // TODO

    override val loginClickS = loginButton.clickS

    override val crashClickS = crashButton.clickS
}

private data class TextItem(val text: String, override val id: Long): WithId

private class TextItemUi(override val ctx: Context) : ItemUi<TextItem> {

    private val view = textView()

    override val root = view.wrapInCardUi(8, 8, 2).root.wrapInFrame { margin = dip(8) }

    override fun render(item: TextItem) { view.text = item.text }
}

@Suppress("unused")
private class HomePreview(ctx: Context, attrs: AttributeSet) : UiFrame(HomeScreenUi(ctx).apply {
    ui.notes = Random.fakeNotes()
}, attrs)
