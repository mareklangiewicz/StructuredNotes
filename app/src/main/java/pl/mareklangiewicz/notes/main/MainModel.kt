package pl.mareklangiewicz.notes.main

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pl.mareklangiewicz.common.*
import pl.mareklangiewicz.notes.logic.main.MainAction
import pl.mareklangiewicz.notes.logic.main.MainCommand
import pl.mareklangiewicz.notes.logic.main.MainLogic
import pl.mareklangiewicz.notes.logic.main.MainState

interface MainModelContract {
    val state: MainState
    val actionS: Consumer<MainAction>
    val commandS: Observable<MainCommand>
}

class MainModel : MainModelContract {

    override val state = MainState()

    override val actionS = createBus<MainAction>()

    override val commandS = createBus<MainCommand>()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val scope = MainScope()

    init {
        log("MainModel.init")

        actionS
            .logOnNext(LogLevel.DEBUG, "action")
            .subscribeForever()

        state.commonS.screenS
            .distinctUntilChanged()
            .logOnNext(LogLevel.DEBUG, "screen")
            .subscribeForever()

        scope.launch { state.MainLogic(actionS, commandS) }
    }
}
