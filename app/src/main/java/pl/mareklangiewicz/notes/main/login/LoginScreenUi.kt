package pl.mareklangiewicz.notes.main.login

import android.content.Context
import pl.mareklangiewicz.notes.main.ScreenUi
import splitties.views.dsl.core.*
import splitties.views.dsl.material.MaterialComponentsStyles
import android.util.AttributeSet
import io.reactivex.Observable
import pl.mareklangiewicz.common.*
import pl.mareklangiewicz.notes.logic.main.login.LoginAction.*
import pl.mareklangiewicz.notes.main.MainModelContract
import pl.mareklangiewicz.notes.widgets.*
import splitties.dimensions.dip
import splitties.views.InputType
import splitties.views.padding
import splitties.views.type

class LoginScreenUi(ctx: Context) : ScreenUi<LoginUiContract>(LoginUi(ctx)) {

    override fun <U> bindUntil(untilS: Observable<U>, model: MainModelContract) {

        model.mainS.loginS.run {
            nameS.subscribeUntil(untilS) { ui.name = it }
            passS.subscribeUntil(untilS) { ui.pass = it }
            nameHintS.subscribeUntil(untilS) { ui.nameHint = it }
            passHintS.subscribeUntil(untilS) { ui.passHint = it }
            nameErrorS.debounceUi(500).subscribeUntil(untilS) { ui.nameError = it }
            passErrorS.debounceUi(500).subscribeUntil(untilS) { ui.passError = it }
        }

        Observable.merge(
            ui.nameChangeS.map { ChangeName(it) },
            ui.passChangeS.map { ChangePass(it) },
            ui.loginClickS.map { Login }
        ).subscribeUntil(untilS, model.actionS)
    }
}

interface LoginUiContract : Ui {
    var name: String
    var pass: String
    var nameHint: String
    var passHint: String
    var nameError: String
    var passError: String
    val nameChangeS: Observable<String>
    val passChangeS: Observable<String>
    val loginClickS: Observable<Unit>
}

@Suppress("EXPERIMENTAL_API_USAGE")
class LoginUi(override val ctx: Context) : LoginUiContract {

    override val root = verticalLayout { padding = dip(32) }

    private val material = MaterialComponentsStyles(ctx)

    private val nameEditText = editText { type = InputType.personName }.addV(root, 32)

    private val passEditText = editText { type = InputType.password }.addV(root, 32)

    private val loginButton = material.button.filled { text = "Login" }.addV(root, 32)

    override var name by nameEditText::txt
    override var pass by passEditText::txt
    override var nameHint by nameEditText::hnt
    override var passHint by passEditText::hnt
    override var nameError by nameEditText::err
    override var passError by passEditText::err

    override val nameChangeS = nameEditText.changeS
    override val passChangeS = passEditText.changeS
    override val loginClickS = loginButton.clickS
}

@Suppress("unused") private class LoginPreview(ctx: Context) : UiFrame(LoginScreenUi(ctx))
