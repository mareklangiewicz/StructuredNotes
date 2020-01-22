package pl.mareklangiewicz.notes.main

import android.annotation.SuppressLint
import android.content.Context
import splitties.views.dsl.core.*
import splitties.views.dsl.material.MaterialComponentsStyles


@SuppressLint("SetTextI18n")
class MainUi(override val ctx: Context) : Ui {

    private val material = MaterialComponentsStyles(ctx)

    private val exampleTextView = textView { text = "example text view" }

    private val exampleButton = material.button.outlined { text = "OK" }

    override val root = verticalLayout {
        add(exampleTextView, lParams(matchParent))
        add(exampleButton, lParams(matchParent))
    }
}
