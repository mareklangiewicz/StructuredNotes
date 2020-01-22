package pl.mareklangiewicz.notes.logic.common

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

@Suppress("unused")
val <T> T.unit get() = Unit

infix fun <T> Consumer<T>.put(value: T) = accept(value)

data class Optional<out T>(val value: T?)

val <T> T?.optional get() = Optional(this)

val <T> Optional<T>.isNull get() = value === null

val <T> Optional<T>.isNotNull get() = value !== null

fun <T> Observable<T>.startWithNull(): Observable<Optional<T>> = map { it.optional }.startWith(null.optional)

fun <T> Observable<Optional<T>>.filterNotNull(): Observable<T> = filter { it.isNotNull }.map { it.value!! }

fun <T> Observable<T>.subscribeForever(onNext: (T) -> Unit = {}) = subscribe(onNext).unit

fun <T> Observable<T>.subscribeForever(onNext: Consumer<in T>) = subscribe(onNext).unit

fun <T, R> Observable<T>.subscribeUntil(untilS: ObservableSource<R>, onNext: (T) -> Unit = {}) =
    takeUntil(untilS).subscribeForever(onNext)

fun <T, R> Observable<T>.subscribeUntil(untilS: ObservableSource<R>, onNext: Consumer<in T>) =
    takeUntil(untilS).subscribeForever(onNext)

