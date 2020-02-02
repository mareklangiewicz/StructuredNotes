package pl.mareklangiewicz.common

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

@Suppress("unused")
val <T> T.unit get() = Unit

/** Short string representation of any object */
val <T> T.str get() =
    if (this === null) "null"
    else (this as Any)::class.let { klass ->
        if(klass.objectInstance === null) toString()
        else klass.simpleName ?: "unknown"
    }

fun <R> memoize(function: () -> R): () -> R {
    val r by lazy(function)
    return { r }
}

infix fun <T> Consumer<T>.put(value: T) = accept(value)

@Suppress("CheckResult")
fun <T> Observable<T>.subscribeForever(onNext: (T) -> Unit = {}) {
    subscribe(onNext)
}

@Suppress("CheckResult")
fun <T> Observable<T>.subscribeForever(onNext: Consumer<in T>) {
    subscribe(onNext)
}

fun <T, R> Observable<T>.subscribeUntil(untilS: ObservableSource<R>, onNext: (T) -> Unit = {}) =
    takeUntil(untilS).subscribeForever(onNext)

fun <T, R> Observable<T>.subscribeUntil(untilS: ObservableSource<R>, onNext: Consumer<in T>) =
    takeUntil(untilS).subscribeForever(onNext)

