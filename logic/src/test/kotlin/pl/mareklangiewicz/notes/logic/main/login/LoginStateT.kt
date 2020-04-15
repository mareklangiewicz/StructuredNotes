package pl.mareklangiewicz.notes.logic.main.login

import pl.mareklangiewicz.common.T
import pl.mareklangiewicz.notes.logic.main.CommonStateT

class LoginStateT(loginS: LoginState = LoginState()) {
    val commonT = CommonStateT(loginS.commonS)
    val nameT = loginS.nameS.T
    val nameHintT = loginS.nameHintS.T
    val nameErrorT = loginS.nameErrorS.T
    val passT = loginS.passS.T
    val passHintT = loginS.passHintS.T
    val passErrorT = loginS.passErrorS.T
}
