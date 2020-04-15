package pl.mareklangiewicz.notes.main.login

import android.content.Context
import pl.mareklangiewicz.notes.main.ScreenUi
import pl.mareklangiewicz.notes.widgets.UiFrame
import splitties.views.dsl.core.*
import splitties.views.dsl.material.MaterialComponentsStyles
import android.util.AttributeSet

class LoginScreenUi(ctx: Context) : ScreenUi<Ui>(LoginUi(ctx))

class LoginUi(override val ctx: Context) : Ui {

    private val material = MaterialComponentsStyles(ctx)

    private val nameEditText = editText() // TODO: remove

    private val passEditText = editText() // TODO: remove

    private val loginButton = material.button.outlined { text = "Login" } // TODO: remove

    override val root = frameLayout {
        add(textView { text = "TODO: LoginUi" }, lParams())
    }
}

@Suppress("unused") private class LoginPreview(ctx: Context) : UiFrame(LoginScreenUi(ctx))
