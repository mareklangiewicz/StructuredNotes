
package pl.mareklangiewicz.notes.main

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import io.reactivex.rxjava3.core.Observable
import pl.mareklangiewicz.common.subscribeUntil
import pl.mareklangiewicz.notes.widgets.UiFrame
import splitties.views.dsl.core.*
import splitties.views.gravityCenter
import androidx.core.view.isVisible
import pl.mareklangiewicz.common.getValue
import pl.mareklangiewicz.common.setValue
import pl.mareklangiewicz.notes.widgets.progressBar
import pl.mareklangiewicz.notes.widgets.debounceUi
import splitties.views.generateViewId


@SuppressLint("SetTextI18n")
class MainUi(override val ctx: Context) : Ui {

    val debugScreenView = textView { gravity = gravityCenter } // TODO: remove

    private val container = frameLayout(generateViewId()) { layoutTransition = LayoutTransition() }

    private val progress = progressBar()

    override val root = frameLayout {
        layoutTransition = LayoutTransition()
        add(container, lParams(matchParent, matchParent))
        add(progress, lParams(matchParent, matchParent))
        add(debugScreenView, lParams(matchParent))
    }

    var isInProgress by progress::isVisible

    fun <U> bindUntil(untilS: Observable<U>, model: MainModelContract) {

        model.mainS.commonS.isInProgressS
            .debounceUi()
            .subscribeUntil(untilS) { isInProgress = it }

        val screenS = model.mainS.commonS.screenS
            .distinctUntilChanged()
            .share()

        screenS
            .debounceUi(100)
            .subscribeUntil(untilS) {
                debugScreenView.text = it.name
                container.removeAllViews()
                val screenUi = ctx.createScreenUi(it)
                if (screenUi !== null) container.apply {
                    screenUi.bindUntil(screenS, model)
                    add(screenUi.root, lParams(matchParent, matchParent))
                }
            }
    }
}

@Suppress("unused") private class MainPreview(ctx: Context, attrs: AttributeSet) : UiFrame(MainUi(ctx), attrs)
