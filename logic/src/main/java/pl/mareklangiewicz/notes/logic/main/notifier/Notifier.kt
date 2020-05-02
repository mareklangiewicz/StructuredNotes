package pl.mareklangiewicz.notes.logic.main.notifier

interface Notifier {
    /** @return given answer or null if user did not answered (cancelled/dismissed/etc) */
    suspend operator fun invoke(
        message: String,
        title: String? = null,
        positiveAnswer: String = "OK",
        negativeAnswer: String? = null
    ): String?
}

