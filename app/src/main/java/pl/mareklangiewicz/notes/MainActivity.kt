package pl.mareklangiewicz.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import splitties.views.dsl.core.setContentView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainUi(this))
    }
}
