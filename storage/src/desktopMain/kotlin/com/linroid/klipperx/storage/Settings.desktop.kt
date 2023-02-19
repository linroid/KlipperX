package com.linroid.klipperx.storage

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

internal actual fun createSettings(): Settings {
    val delegate: Preferences = Preferences.userRoot()
    return PreferencesSettings(delegate)
}