package com.linroid.klipperx.common

import androidx.compose.ui.window.Application
import platform.UIKit.UIViewController

@Suppress("unused")
fun rootViewController(): UIViewController =
    Application("KotlinX") {
        App()
    }
