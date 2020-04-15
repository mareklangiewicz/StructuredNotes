package pl.mareklangiewicz.common

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import io.reactivex.observers.TestObserver
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

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

typealias State<T> = BehaviorRelay<T>

fun State<Boolean>.toggle() = this put (value != true)

@OptIn(ExperimentalContracts::class)
inline fun <S, R> State<S>.withS(state: S, block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val original = value
    this put state
    try { return block() }
    finally { this put original }
}

fun createState(value: String): State<CharSequence> = State.createDefault(value)
fun <T> createState(value: T): State<T> = State.createDefault(value)

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

fun <T> Observable<T>.shareStatesForever() =
    concatWith(Observable.never()).replay(1).autoConnect().apply { subscribe() }

fun <T> Observable<T>.shareEventsForever() = publish().autoConnect().apply { subscribe() }

val <S: Any> Observable<S>.T: TestObserver<S> get() = test()
