package pl.mareklangiewicz.notes.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxrelay2.PublishRelay
import pl.mareklangiewicz.common.put
import pl.mareklangiewicz.common.subscribeUntil
import pl.mareklangiewicz.notes.DI
import pl.mareklangiewicz.notes.logic.main.Back
import pl.mareklangiewicz.notes.logic.main.Screen
import splitties.views.dsl.core.setContentView

class MainActivity : AppCompatActivity() {

    private val model = DI.provideMainModel()
    private val destroyS = PublishRelay.create<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainUi(this).apply { bindUntil(destroyS, model) })
        model.state.screenS
            .skip(1)
            .filter { it == Screen.None }
            .subscribeUntil(destroyS) { finish() }
    }

    override fun onDestroy() {
        destroyS put Unit
        super.onDestroy()
    }

    override fun onBackPressed() = model.actionS put Back
}
