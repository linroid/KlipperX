package com.linroid.klipperx.foundation

import org.koin.core.Koin
import org.koin.mp.KoinPlatformTools

fun koin(): Koin {
    return KoinPlatformTools.defaultContext().get()
}
