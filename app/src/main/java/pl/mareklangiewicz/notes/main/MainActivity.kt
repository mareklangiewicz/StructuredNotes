package pl.mareklangiewicz.notes.main

import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import pl.mareklangiewicz.common.*
import pl.mareklangiewicz.notes.DI
import pl.mareklangiewicz.notes.logic.main.Back
import pl.mareklangiewicz.notes.logic.main.MainCommand.*
import splitties.toast.toast
import splitties.views.dsl.core.setContentView

class MainActivity : AppCompatActivity() {

    private val model = DI.provideMainModel()
    private val destroyS = createBus<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.provideMainActivity = { this }
        setContentView(MainUi(this).apply { bindUntil(destroyS, model) })
        model.commandS.subscribeUntil(destroyS) { when (it) {
            is Hint -> toast(it.message)
            is LaunchUrl -> launchUrl(it.url)
            Finish -> finish()
        } }
    }

    override fun onDestroy() {
        destroyS put Unit
        if(DI.provideMainActivity() == this) DI.provideMainActivity = { null }
        super.onDestroy()
    }

    override fun onBackPressed() = model.actionS put Back
}

private fun Context.launchUrl(url: String) =
    try { CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(url)) }
    catch (_: ActivityNotFoundException) { toast("Can not open $url") }
