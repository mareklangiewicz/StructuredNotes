package pl.mareklangiewicz.common

import io.reactivex.rxjava3.core.Observable

data class Optional<out T>(val value: T?)

val <T> T?.optional get() = Optional(this)

val <T> Optional<T>.isNull get() = value === null

val <T> Optional<T>.isNotNull get() = value !== null

fun <T> Observable<Optional<T>>.filterNotNull(): Observable<T> = filter { it.isNotNull }.map { it.value!! }

