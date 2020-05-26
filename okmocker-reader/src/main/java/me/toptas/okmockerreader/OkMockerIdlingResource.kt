package me.toptas.okmockerreader

import android.support.test.espresso.idling.CountingIdlingResource

object OkMockerIdlingResource {
    val instance = CountingIdlingResource("OkMockerCountingIdlingRes")
}