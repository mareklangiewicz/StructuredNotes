@file:Suppress("unused")

package pl.mareklangiewicz.common

import io.reactivex.Observable
import pl.mareklangiewicz.common.LogLevel.*

@Suppress("MagicNumber")
enum class LogLevel(val nr: Int) { VERBOSE(2), DEBUG(3), INFO(4), WARN(5), ERROR(6) }

typealias Logger = (message: String, level: LogLevel) -> Unit

class QuietLogger : Logger {
    override fun invoke(message: String, level: LogLevel) = Unit
}

class BasicLogger : Logger {

    var minLevel = INFO
    var errLevel: LogLevel = WARN

    override fun invoke(message: String, level: LogLevel) {
        when {
            level >= errLevel -> System.err.println(message)
            level >= minLevel -> println(message)
        }
    }
}

fun log(msg: Any?, level: LogLevel = INFO) = DI.provideLogger()(msg.str, level)

fun <T : Any> Observable<T>.logOnNext(level: LogLevel = INFO, prefix: String = "next"): Observable<T> =
    doOnNext { log("$prefix ${it.str}", level) }

fun <T : Any> Observable<T>.logOnError(level: LogLevel = INFO, prefix: String = "error"): Observable<T> =
    doOnError { log("$prefix ${it.str}", level) }

fun <T : Any> Observable<T>.logOnComplete(level: LogLevel = INFO, message: String = "complete"): Observable<T> =
    doOnComplete { log(message, level) }

fun <T: Any> Observable<T>.logOnSubscribe(level: LogLevel = INFO, message: String = "subscribe"): Observable<T> =
    doOnSubscribe { log(message, level) }

fun <T: Any> Observable<T>.logOnDispose(level: LogLevel = INFO, message: String = "dispose"): Observable<T> =
    doOnDispose { log(message, level) }

fun <T: Any> Observable<T>.logOnAny(level: LogLevel = INFO, prefix: String = "") = this
    .logOnSubscribe(level, "$prefix subscribe")
    .logOnDispose(level, "$prefix dispose")
    .logOnComplete(level, "$prefix complete")
    .logOnError(level, prefix)
    .logOnNext(level, prefix)

