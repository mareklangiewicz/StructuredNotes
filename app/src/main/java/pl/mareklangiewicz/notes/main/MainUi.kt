
package pl.mareklangiewicz.notes.main

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import pl.mareklangiewicz.common.subscribeUntil
import pl.mareklangiewicz.notes.widgets.UiFrame
import splitties.views.dsl.core.*
import splitties.views.gravityCenter
import java.util.concurrent.TimeUnit.MILLISECONDS
import androidx.core.view.isVisible
import pl.mareklangiewicz.common.getValue
import pl.mareklangiewicz.common.setValue
import pl.mareklangiewicz.notes.widgets.progressBar
import android.util.AttributeSet


@SuppressLint("SetTextI18n")
class MainUi(override val ctx: Context) : Ui {

    val debugScreenView = textView { gravity = gravityCenter } // TODO: remove

    private val container = frameLayout()

    private val progress = progressBar()

    override val root = frameLayout {
        layoutTransition = LayoutTransition()
        add(container, lParams(matchParent, matchParent))
        add(progress, lParams(matchParent, matchParent))
        add(debugScreenView, lParams(matchParent))
    }

    var isInProgress by progress::isVisible

    fun <U> bindUntil(untilS: ObservableSource<U>, model: MainModelContract) {

        val screenS = model.state.commonS.screenS
            .distinctUntilChanged()

        screenS
            .debounce(100L, MILLISECONDS, mainThread())
            .subscribeUntil(untilS) {
                debugScreenView.text = it.name
                container.removeAllViews()
                val screenUi = ctx.createScreenUi(it)
                if (screenUi !== null) container.apply {
                    screenUi.bindUntil(screenS, model)
                    add(screenUi.root, lParams(matchParent, matchParent))
                    // TODO: some animations / transitions
                }
            }
    }
}

@Suppress("unused") private class MainPreview(ctx: Context) : UiFrame(MainUi(ctx))
