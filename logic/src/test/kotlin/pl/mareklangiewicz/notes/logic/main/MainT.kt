package pl.mareklangiewicz.notes.logic.main

import io.reactivex.observers.TestObserver
import pl.mareklangiewicz.common.T
import pl.mareklangiewicz.notes.logic.main.login.LoginStateT

/** [TestObserver] tree for all streams in [MainState] */
class MainStateT(mainS: MainState = MainState()) {
    val commonT = CommonStateT(mainS.commonS)
    val loginT = LoginStateT(mainS.loginS)
}

class CommonStateT(commonS: CommonState = CommonState()) {
    val screenT = commonS.screenS.T
    val isInProgressT = commonS.isInProgressS.T
}
