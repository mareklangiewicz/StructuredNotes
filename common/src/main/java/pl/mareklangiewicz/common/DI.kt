package pl.mareklangiewicz.common

object DI {
    var provideLogger: () -> Logger = memoize { QuietLogger() }
}
