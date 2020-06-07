package pl.mareklangiewicz.notes.widgets

import android.annotation.SuppressLint
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.text.parseAsHtml
import androidx.core.text.toHtml
import androidx.core.view.isVisible
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.mareklangiewicz.common.unsupportedGet
import pl.mareklangiewicz.sandboxui.sandbox
import splitties.dimensions.dip
import splitties.dimensions.dp
import splitties.views.dsl.core.*
import splitties.views.dsl.core.styles.AndroidStyles
import splitties.views.gravityCenter
import java.util.concurrent.TimeUnit

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

var TextView.html: String
    get() = editableText?.toHtml() ?: text?.toString() ?: ""
    set(value) {
        text = value.parseAsHtml()
    }

var TextView.htmlRes: Int?
    get() = unsupportedGet
    set(@StringRes value) { html = value?.let { context.getString(it) } ?: "" }

var TextView.txt: String
    get() = text?.toString() ?: ""
    set(value) {
        if (txt != value) text = value
    }

var TextView.txtRes: Int?
    get() = unsupportedGet
    set(@StringRes value) { txt = value?.let { context.getString(it) } ?: "" }

var TextView.hnt: String
    get() = hint?.toString() ?: ""
    set(value) {
        if (hnt != value) hint = value
    }

var TextView.hntRes: Int?
    get() = unsupportedGet
    set(@StringRes value) { hnt = value?.let { context.getString(it) } ?: "" }

var TextView.err: String
    get() = error?.toString() ?: ""
    set(value) {
        if (err != value) error = value
    }

var EditText.errRes: Int?
    get() = unsupportedGet
    set(@StringRes value) { err = value?.let { context.getString(it) } ?: "" }


fun Ui.dip(value: Int) = ctx.dip(value)
fun Ui.dp(value: Int) = ctx.dp(value)

fun <T> Observable<T>.debounceUi(delayMs: Int = 200): Observable<T> =
    debounce(delayMs.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())

@Suppress("MagicNumber")
val EditText.changeS: Observable<String>
    get() = textChanges()
        .skipInitialValue()
        .map { it.toString() }
        .debounceUi(20)
        .distinctUntilChanged()
        .share()

val View.clickS: Observable<Unit> get() = clicks().share()


fun Ui.progressBar(android: AndroidStyles = AndroidStyles(ctx)) = frameLayout {
    add(android.progressBar.large(), lParams(wrapContent, wrapContent, gravityCenter))
    isClickable = true
    isFocusable = true
    isVisible = false
}

fun View.wrapInFrame(
    width: Int = matchParent,
    height: Int = matchParent,
    gravity: Int = FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY,
    initParams: FrameLayout.LayoutParams.() -> Unit = {}
) = frameLayout {
    add(this@wrapInFrame, lParams(width, height, gravity, initParams))
}


@SuppressLint("ViewConstructor")
open class UiFrame(ui: Ui, attrs: AttributeSet?): FrameLayout(ui.ctx, attrs) {
    init { add(ui.root, lParams(matchParent, matchParent)) }
}
@SuppressLint("ViewConstructor")
open class UiBoxFrame(ui: Ui, attrs: AttributeSet?): UiFrame(ui.sandbox { on(-1, -1) lay ui.root }, attrs)



