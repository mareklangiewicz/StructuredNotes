package pl.mareklangiewicz.notes.widgets

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable

inline fun gradientDrawable(shape: Int = GradientDrawable.RECTANGLE, block: GradientDrawable.() -> Unit = {}) =
    GradientDrawable().apply {
        this.shape = shape
        block()
    }

inline fun rippleDrawable(
    rippleColor: ColorStateList,
    content: Drawable,
    block: RippleDrawable.() -> Unit = {}
) = RippleDrawable(rippleColor, content, null).apply(block)

inline fun rectangleDrawable(block: GradientDrawable.() -> Unit = {}) = GradientDrawable().apply(block)
inline fun ovalDrawable(block: GradientDrawable.() -> Unit = {}) = gradientDrawable(GradientDrawable.OVAL, block)
inline fun lineDrawable(block: GradientDrawable.() -> Unit = {}) = gradientDrawable(GradientDrawable.LINE, block)
inline fun ringDrawable(block: GradientDrawable.() -> Unit = {}) = gradientDrawable(GradientDrawable.RING, block)


