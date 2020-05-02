package pl.mareklangiewicz.notes.notifier

import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.suspendCancellableCoroutine
import pl.mareklangiewicz.common.LogLevel.DEBUG
import pl.mareklangiewicz.common.log
import pl.mareklangiewicz.notes.DI
import pl.mareklangiewicz.notes.logic.main.notifier.Notifier
import pl.mareklangiewicz.notes.provideActivity
import splitties.alertdialog.appcompat.alertDialog
import splitties.alertdialog.appcompat.message
import splitties.alertdialog.appcompat.title
import kotlin.coroutines.resume

class AndroNotifier : Notifier {

    private var dialog: AlertDialog? = null

    override suspend fun invoke(
        message: String,
        title: String?,
        positiveAnswer: String,
        negativeAnswer: String?
    ): String? {
        log("notify: $message", DEBUG)
        val activity = DI.provideActivity()
        return when {
            activity == null || activity.isFinishing -> null
            else -> suspendCancellableCoroutine<String?> { cont ->
                cont.invokeOnCancellation { cancel() }
                dialog = activity.alertDialog {
                    this.title = title
                    this.message = message
                    setOnCancelListener { cont.resume(null) }
                    setPositiveButton(positiveAnswer) { _, _ -> cont.resume(positiveAnswer) }
                    negativeAnswer ?: return@alertDialog
                    setNegativeButton(negativeAnswer) { _, _ -> cont.resume(negativeAnswer) }
                }
                dialog?.show()
            }
        }.also { log("answer: $it", DEBUG) }
    }

    private fun cancel() {
        dialog?.dismiss()
        dialog = null
    }
}
