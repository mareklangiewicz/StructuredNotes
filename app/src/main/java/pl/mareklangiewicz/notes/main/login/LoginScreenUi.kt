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

class LoginScreenUi(ctx: Context) : ScreenUi<LoginUi>(LoginUi(ctx)) {

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

@Suppress("EXPERIMENTAL_API_USAGE")
class LoginUi(override val ctx: Context) : Ui {

    override val root = verticalLayout { padding = dip(32) }

    private val material = MaterialComponentsStyles(ctx)

    private val nameEditText = editText().addV(root, 32)

    private val passEditText = editText { type = InputType.password }.addV(root, 32)

    private val loginButton = material.button.filled { text = "Login" }.addV(root, 32)

    var name by nameEditText::txt
    var pass by passEditText::txt
    var nameHint by nameEditText::hnt
    var passHint by passEditText::hnt
    var nameError by nameEditText::err
    var passError by passEditText::err

    val nameChangeS = nameEditText.changeS
    val passChangeS = passEditText.changeS
    val loginClickS = loginButton.clickS
}

@Suppress("unused") private class LoginPreview(ctx: Context) : UiFrame(LoginScreenUi(ctx))
