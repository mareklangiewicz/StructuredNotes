package pl.mareklangiewicz.notes.logic.main.login

import com.jakewharton.rxrelay2.BehaviorRelay

class LoginState {
    val nameS = BehaviorRelay.createDefault<String>("")
    val nameHintS = BehaviorRelay.createDefault<String>("")
    val nameErrorS = BehaviorRelay.createDefault<String>("")
    val passS = BehaviorRelay.createDefault<String>("")
    val passHintS = BehaviorRelay.createDefault<String>("")
    val passErrorS = BehaviorRelay.createDefault<String>("")
}