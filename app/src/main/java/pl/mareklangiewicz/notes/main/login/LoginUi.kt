package pl.mareklangiewicz.notes.main.login

import android.content.Context
import splitties.views.dsl.core.*
import splitties.views.dsl.material.MaterialComponentsStyles

class LoginUi(override val ctx: Context) : Ui {

    private val material = MaterialComponentsStyles(ctx)

    private val nameEditText = editText() // TODO: remove

    private val passEditText = editText() // TODO: remove

    private val loginButton = material.button.outlined { text = "Login" } // TODO: remove

    override val root = frameLayout {
        add(textView { text = "TODO: LoginUi" }, lParams())
    }
}
