package pl.mareklangiewicz.notes.widgets

import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import pl.mareklangiewicz.sandboxui.sandbox
import splitties.dimensions.dip
import splitties.dimensions.dp
import splitties.views.dsl.core.*
import splitties.views.dsl.core.styles.AndroidStyles
import splitties.views.gravityCenter

fun <V: View> V.addV(
    layout: LinearLayout,
    topMarginDp: Int = 0,
    horizontalMarginDp: Int = 0,
    bottomMarginDp: Int = 0,
    wrapInCenter: Boolean = false,
    adjustParams: LinearLayout.LayoutParams.() -> Unit = {}
): V = apply {
    layout.add(this, layout.lParams(if (wrapInCenter) wrapContent else matchParent) {
        gravity = gravityCenter
        topMargin = dip(topMarginDp)
        bottomMargin = dip(bottomMarginDp)
        horizontalMargin = dip(horizontalMarginDp)
        adjustParams()
    })
}

fun <U: Ui> U.addV(
    layout: LinearLayout,
    topMarginDp: Int = 0,
    horizontalMarginDp: Int = 0,
    bottomMarginDp: Int = 0,
    wrapInCenter: Boolean = false,
    adjustParams: LinearLayout.LayoutParams.() -> Unit = {}
) = apply { root.addV(layout, topMarginDp, horizontalMarginDp, bottomMarginDp, wrapInCenter, adjustParams) }

fun <V: View> V.addH(
    layout: LinearLayout,
    startMarginDp: Int = 0,
    verticalMarginDp: Int = 0,
    endMarginDp: Int = 0,
    wrapInCenter: Boolean = false,
    adjustParams: LinearLayout.LayoutParams.() -> Unit = {}
): V = apply {
    layout.add(this, layout.lParams(wrapContent, if (wrapInCenter) wrapContent else matchParent) {
        gravity = gravityCenter
        startMargin = dip(startMarginDp)
        verticalMargin = dip(verticalMarginDp)
        endMargin = dip(endMarginDp)
        adjustParams()
    })
}

fun <U: Ui> U.addH(
    layout: LinearLayout,
    startMarginDp: Int = 0,
    verticalMarginDp: Int = 0,
    endMarginDp: Int = 0,
    wrapInCenter: Boolean = false,
    adjustParams: LinearLayout.LayoutParams.() -> Unit = {}
) = apply { root.addH(layout, startMarginDp, verticalMarginDp, endMarginDp, wrapInCenter, adjustParams) }

fun Ui.invalidate() = root.invalidate()

var Ui.isVisible: Boolean
    get() = root.isVisible
    set(value) { root.isVisible = value }

fun CharSequence?.containsHtml() = this != null && Regex("<.+>").containsMatchIn(this)

var TextView.txt: CharSequence
    get() = text ?: ""
    set(value) {
        text = if (value.containsHtml()) value.toString().parseAsHtml() else value
    }

var TextView.txtRes: Int?
    get() = null
    set(@StringRes value) { txt = value?.let { context.getText(it) } ?: "" }

var EditText.hintRes: Int?
    get() = null
    set(@StringRes value) { hint = value?.let { context.getText(it) } ?: "" }

fun Ui.dip(value: Int) = ctx.dip(value)
fun Ui.dp(value: Int) = ctx.dp(value)


fun Ui.progressBar(android: AndroidStyles = AndroidStyles(ctx)) = frameLayout {
    add(android.progressBar.large(), lParams(wrapContent, wrapContent, gravityCenter))
    isClickable = true
    isFocusable = true
    isVisible = false
}

open class UiFrame(ui: Ui): FrameLayout(ui.ctx) { init { add(ui.root, lParams(matchParent, matchParent)) } }

open class UiBoxFrame(ui: Ui): UiFrame(ui.sandbox { on(-1, -1) lay ui.root })
