package com.linroid.klipperx

import android.content.Context
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("StaticFieldLeak")
lateinit var koinContext: Context
internal actual fun platformModule(): Module {
    return module {
        single { koinContext }
    }
}