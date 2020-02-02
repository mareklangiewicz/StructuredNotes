package pl.mareklangiewicz.notes.main

import io.reactivex.Observable
import io.reactivex.ObservableSource
import splitties.views.dsl.core.Ui
import pl.mareklangiewicz.notes.logic.main.MainAction
import pl.mareklangiewicz.notes.logic.main.MainState

abstract class ScreenUi<UiContract : Ui>(protected val ui: UiContract) : Ui by ui {

    abstract val actionS: Observable<out MainAction>

    abstract fun bindToStateUntil(state: MainState, untilS: ObservableSource<Unit>)
}
