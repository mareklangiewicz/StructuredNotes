package pl.mareklangiewicz.notes.logic.main.login

import pl.mareklangiewicz.notes.logic.main.MainAction

sealed class LoginAction : MainAction {
    data class ChangeName(val name: String) : LoginAction()
    data class ChangePass(val pass: String) : LoginAction()
    object Login : LoginAction()
}