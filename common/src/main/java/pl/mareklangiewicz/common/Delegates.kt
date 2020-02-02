package pl.mareklangiewicz.common

import kotlin.reflect.*

operator fun <R, T> KProperty0<T>.getValue(thisRef: R, property: KProperty<*>) = get()

operator fun <R, T> KMutableProperty0<T>.setValue(thisRef: R, property: KProperty<*>, value: T) = set(value)
