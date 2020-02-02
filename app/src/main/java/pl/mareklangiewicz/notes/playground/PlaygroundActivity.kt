package pl.mareklangiewicz.notes.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import splitties.views.dsl.core.setContentView

class PlaygroundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(PlaygroundUi(this))
    }
}
