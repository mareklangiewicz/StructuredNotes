package pl.mareklangiewicz.notes.main

import android.annotation.SuppressLint
import android.content.Context
import pl.mareklangiewicz.common.subscribeForever
import splitties.views.dsl.core.*
import splitties.views.dsl.material.MaterialComponentsStyles


@SuppressLint("SetTextI18n")
class MainUi(override val ctx: Context, model: MainModelContract) : Ui {

    private val material = MaterialComponentsStyles(ctx)

    private val debugScreenView = textView() // TODO: remove

    private val nameEditText = editText() // TODO: remove

    private val passEditText = editText() // TODO: remove

    private val loginButton = material.button.outlined { text = "Login" } // TODO: remove

    override val root = verticalLayout {
        add(debugScreenView, lParams(matchParent))
        add(nameEditText, lParams(matchParent))
        add(passEditText, lParams(matchParent))
        add(loginButton, lParams(matchParent))
    }

    init {
        model.state.run {
            screenS.subscribeForever { debugScreenView.text = it.name } // TODO: remove
        }
    }
}
