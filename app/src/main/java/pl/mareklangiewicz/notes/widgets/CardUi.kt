package pl.mareklangiewicz.notes.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.view.updateLayoutParams
import splitties.dimensions.dip
import splitties.views.backgroundColor
import splitties.views.dsl.core.*
import splitties.views.gravityCenter

class CardUi(override val ctx: Context, private val content: View) : Ui {

    private val back = rectangleDrawable {
        setColor(fillColor)
        setStroke(dip(strokeDp), strokeColor)
        cornerRadius = dp(radiusDp)
    }

    // private val ripple = rippleDrawable(ColorStateList.valueOf(rippleColor), back)

    override val root = frameLayout {
        elevation = dp(elevationDp)
        background = back
        add(content, lParams(matchParent, matchParent, gravity = gravityCenter) { margin = dip(marginDp) })
    }

    var marginDp: Int = 0
        set(value) { field = value; content.updateLayoutParams<MarginLayoutParams> { margin = dip(value) } }

    var radiusDp: Int = 0
        set(value) { field = value; back.cornerRadius = dp(value) }

    var elevationDp: Int = 0; set(value) { field = value; root.elevation = dp(value) }

    /** 0 means no stroke at all */
    var strokeDp = 0
        set(value) { field = value; back.setStroke(dip(value), strokeColor) }

    @ColorInt var strokeColor: Int = Color.WHITE
        set(value) { field = value; back.setStroke(dip(strokeDp), value) }

    @ColorInt var fillColor: Int = Color.WHITE; set(value) { field = value; back.setColor(value) }
}

fun View.wrapInCardUi(
    marginDp: Int = 0,
    radiusDp: Int = 0,
    elevationDp: Int = 0,
    strokeDp: Int = 0,
    @ColorInt fillColor: Int = Color.WHITE,
    @ColorInt strokeColor: Int = Color.WHITE
) = CardUi(context, this).also {
    it.marginDp = marginDp
    it.radiusDp = radiusDp
    it.elevationDp = elevationDp
    it.strokeDp = strokeDp
    it.fillColor = fillColor
    it.strokeColor = strokeColor
}

fun View.wrapInFrame(
    width: Int = matchParent,
    height: Int = matchParent,
    gravity: Int = FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY,
    initParams: FrameLayout.LayoutParams.() -> Unit = {}
) = frameLayout {
    add(this@wrapInFrame, lParams(width, height, gravity, initParams))
}


@Suppress("unused")
private class CardUiPreview(ctx: Context, attrs: AttributeSet) : UiBoxFrame(
    ctx.textView { text = "example"; backgroundColor = Color.LTGRAY }
        .wrapInCardUi(32, 32, 8).root
        .wrapInFrame { margin = ctx.dip(16) }
        .wrapInCardUi(64, 64, 8)
    , attrs)
