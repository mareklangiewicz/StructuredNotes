package pl.mareklangiewicz.notes.logic.utils

class AbortLogicException : RuntimeException()

// TODO: use it! I already decided it's good practice to use exceptions for back navigation (most of the time)
inline fun <reified R> onAbortLogicReturn(abortValue: R, block: () -> R): R {
    return try { block() } catch (_: AbortLogicException) { abortValue }
}

inline fun <reified R, E: Throwable> onAbortLogicThrow(abortException: E, block: () -> R): R {
    return try { block() } catch (_: AbortLogicException) { throw abortException }
}

