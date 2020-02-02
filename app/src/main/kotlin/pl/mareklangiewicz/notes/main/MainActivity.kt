package pl.mareklangiewicz.notes.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxrelay2.PublishRelay
import pl.mareklangiewicz.common.put
import splitties.views.dsl.core.setContentView
import pl.mareklangiewicz.notes.DI
import pl.mareklangiewicz.notes.logic.main.Back

class MainActivity : AppCompatActivity() {

    private val model = DI.provideMainModel()
    private val destroyS = PublishRelay.create<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainUi(this, model))
    }

    override fun onDestroy() {
        destroyS put Unit
        super.onDestroy()
    }

    override fun onBackPressed() = model.actionS put Back
}
