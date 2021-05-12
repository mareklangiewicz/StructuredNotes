package pl.mareklangiewicz.common

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.observers.TestObserver
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@Suppress("unused")
val <T> T.unit get() = Unit

val unsupportedGet: Nothing get() = throw UnsupportedOperationException("Unsupported get")

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

typealias Bus<T> = PublishRelay<T>

typealias State<T> = BehaviorRelay<T>

val <T> State<T>.V get() = value!!

fun State<Boolean>.toggle() = this put !V

@OptIn(ExperimentalContracts::class)
inline fun <S, R> State<S>.withS(state: S, block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val original = value
    this put state
    try { return block() }
    finally { this put original }
}

fun <T> createBus(): Bus<T> = Bus.create()
fun <T> createState(value: T): State<T> = State.createDefault(value)

@Suppress("CheckResult")
fun <T> Observable<T>.subscribeForever(onNext: (T) -> Unit = {}) {
    subscribe(onNext)
}

@Suppress("CheckResult")
fun <T> Observable<T>.subscribeForever(onNext: Consumer<in T>) {
    subscribe(onNext)
}

fun <T, R> Observable<T>.subscribeUntil(untilS: Observable<R>, onNext: (T) -> Unit = {}) =
    takeUntil(untilS).subscribeForever(onNext)

fun <T, R> Observable<T>.subscribeUntil(untilS: Observable<R>, onNext: Consumer<in T>) =
    takeUntil(untilS).subscribeForever(onNext)

fun <T> Observable<T>.shareStatesForever() =
    concatWith(Observable.never()).replay(1).autoConnect().apply { subscribe() }

fun <T> Observable<T>.shareEventsForever() = publish().autoConnect().apply { subscribe() }

val <S: Any> Observable<S>.T: TestObserver<S> get() = test()
