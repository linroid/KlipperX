package com.linroid.klipperx

import com.linroid.klipperx.storage.storageModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module

internal expect fun platformModule() : Module

fun setupKoin() {
    startKoin {
        modules(
            platformModule(),
            storageModule(),
        )
    }
}