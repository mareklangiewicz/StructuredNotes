package pl.mareklangiewicz.notes.playground

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import io.reactivex.ObservableSource
import pl.mareklangiewicz.common.subscribeUntil
import pl.mareklangiewicz.notes.logic.main.Screen
import pl.mareklangiewicz.notes.main.MainModelContract
import pl.mareklangiewicz.notes.main.createScreenUi
import pl.mareklangiewicz.sandboxui.SandboxUi
import pl.mareklangiewicz.sandboxui.sandbox
import splitties.dimensions.dip
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.wrapInScrollView


@SuppressLint("SetTextI18n")
class PlaygroundUi(override val ctx: Context) : Ui {

    private val screenBoxes = mutableListOf<SandboxUi>()

    private val sandboxUi = sandbox {
        inbox.flexWrap = FlexWrap.WRAP
        inbox.alignItems = AlignItems.FLEX_START

        action(" expand ") { screenBoxes.forEach { it.expand() } }
        action(" collapse ") { screenBoxes.forEach { it.collapse() } }

        for (screen in Screen.values()) {
            val screenUi = ctx.createScreenUi(screen) ?: continue
            val screenBox = sandbox(screen.name) {
                on(ctx.dip(260), ctx.dip(400)) lay screenUi.root
                action(" \u21d0 ") { screenUi.updateFlexLayout { width -= ctx.dip(8) } }
                action(" \u21d2 ") { screenUi.updateFlexLayout { width += ctx.dip(8) } }
                action(" \u21d1 ") { screenUi.updateFlexLayout { height -= ctx.dip(8) } }
                action(" \u21d3 ") { screenUi.updateFlexLayout { height += ctx.dip(8) } }
            }
            screenBoxes += screenBox
            + screenBox.root
        }
    }

    override val root = sandboxUi.root.wrapInScrollView()

    fun <U> bindUntil(untilS: ObservableSource<U>, model: MainModelContract) {
        model.state.screenS.subscribeUntil(untilS) { sandboxUi.title.text = "current screen: $it" }
    }
}

private fun Ui.updateFlexLayout(block: FlexboxLayout.LayoutParams.() -> Unit) = root.updateLayoutParams(block)

private fun SandboxUi.expand() { inbox.isVisible = true }
private fun SandboxUi.collapse() { inbox.isVisible = false; options.isVisible = false }
