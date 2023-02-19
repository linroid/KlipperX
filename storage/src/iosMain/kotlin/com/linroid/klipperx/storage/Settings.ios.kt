package com.linroid.klipperx.storage

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

internal actual fun createSettings(): Settings {
    val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults
    return NSUserDefaultsSettings(delegate)
}