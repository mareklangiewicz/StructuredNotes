package pl.mareklangiewicz.notes.main

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pl.mareklangiewicz.common.*
import pl.mareklangiewicz.common.LogLevel.*
import pl.mareklangiewicz.notes.logic.main.MainAction
import pl.mareklangiewicz.notes.logic.main.MainCommand
import pl.mareklangiewicz.notes.logic.main.logic
import pl.mareklangiewicz.notes.logic.main.MainState
import pl.mareklangiewicz.notes.notifier.AndroNotifier

interface MainModelContract {
    val mainS: MainState
    val actionS: Consumer<MainAction>
    val commandS: Observable<MainCommand>
}

class MainModel : MainModelContract {

    override val mainS = MainState()

    override val actionS = createBus<MainAction>()

    override val commandS = createBus<MainCommand>()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val scope = MainScope()

    init {
        log("MainModel.init")

        actionS
            .logOnNext(DEBUG, "action")
            .subscribeForever()

        mainS.commonS.screenS
            .distinctUntilChanged()
            .logOnNext(DEBUG, "screen")
            .subscribeForever()

        val notify = AndroNotifier()

        scope.launch { mainS.logic(actionS, commandS, notify) }
    }
}
