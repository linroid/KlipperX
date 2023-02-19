package com.linroid.klipperx

import com.linroid.klipperx.storage.storageModule
import org.koin.core.context.startKoin

fun setupKoin() {
    startKoin {
        storageModule()
    }
}