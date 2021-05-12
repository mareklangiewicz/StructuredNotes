package pl.mareklangiewicz.notes.main

import android.content.Context
import io.reactivex.rxjava3.core.Observable
import pl.mareklangiewicz.notes.logic.main.Screen
import pl.mareklangiewicz.notes.main.home.HomeScreenUi
import pl.mareklangiewicz.notes.main.login.LoginScreenUi
import pl.mareklangiewicz.notes.main.splash.SplashScreenUi
import splitties.views.dsl.core.Ui

abstract class ScreenUi<UiContract : Ui>(val ui: UiContract) : Ui by ui {
    open fun <U> bindUntil(untilS: Observable<U>, model: MainModelContract) = Unit
}

fun Context.createScreenUi(screen: Screen) = when(screen) {
    Screen.Empty -> null
    Screen.Splash -> SplashScreenUi(this)
    Screen.Register -> null // TODO
    Screen.Login -> LoginScreenUi(this)
    Screen.Home -> HomeScreenUi(this)
    Screen.Settings -> null // TODO
}
