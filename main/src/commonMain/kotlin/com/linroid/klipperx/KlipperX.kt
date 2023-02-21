package com.linroid.klipperx

import com.linroid.klipperx.storage.storageModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin

fun startKlipperX() {
    Napier.base(DebugAntilog())
    startKoin {
        modules(
            platformModule(),
            storageModule(),
        )
    }
}