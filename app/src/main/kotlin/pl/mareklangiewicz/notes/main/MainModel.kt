package pl.mareklangiewicz.notes.main

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.functions.Consumer
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pl.mareklangiewicz.common.LogLevel
import pl.mareklangiewicz.common.log
import pl.mareklangiewicz.common.logOnNext
import pl.mareklangiewicz.common.subscribeForever
import pl.mareklangiewicz.notes.logic.main.*

interface MainModelContract {
    val state: MainState
    val actionS: Consumer<MainAction>
}

class MainModel : MainModelContract {

    override val state = MainState()

    override val actionS = PublishRelay.create<MainAction>()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val scope = MainScope()

    init {

        actionS
            .logOnNext(LogLevel.DEBUG, "action")
            .subscribeForever()

        state.screenS
            .distinctUntilChanged()
            .logOnNext(LogLevel.DEBUG, "screen")
            .subscribeForever()

        log("MainModel.init")
        scope.launch { MainLogic(actionS, state) }
    }
}