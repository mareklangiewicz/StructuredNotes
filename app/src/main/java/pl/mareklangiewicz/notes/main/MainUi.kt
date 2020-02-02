package pl.mareklangiewicz.notes.main

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import pl.mareklangiewicz.common.subscribeUntil
import splitties.views.dsl.core.*
import splitties.views.gravityCenter
import java.util.concurrent.TimeUnit.MILLISECONDS


@SuppressLint("SetTextI18n")
class MainUi(override val ctx: Context) : Ui {

    private val debugScreenView = textView { gravity = gravityCenter } // TODO: remove

    private val screenUiContainer = frameLayout()

    override val root = verticalLayout {
        add(debugScreenView, lParams(matchParent))
        add(screenUiContainer, lParams(matchParent, matchParent))
    }

    fun <U> bindUntil(untilS: ObservableSource<U>, model: MainModelContract) {

        val screenS = model.state.screenS
            .distinctUntilChanged()

        screenS
            .debounce(100L, MILLISECONDS, mainThread())
            .subscribeUntil(untilS) {
                debugScreenView.text = it.name
                screenUiContainer.removeAllViews()
                val screenUi = ctx.createScreenUi(it)
                if (screenUi !== null) screenUiContainer.apply {
                    screenUi.bindUntil(screenS, model)
                    add(screenUi.root, lParams(matchParent, matchParent))
                    // TODO: some animations / transitions
                }
            }
    }
}

