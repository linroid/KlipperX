package com.linroid.klipperx.storage

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.java.KoinJavaComponent.inject

internal actual fun createSettings(): Settings {
    val context: Context by inject(Context::class.java)
    val delegate = context.getSharedPreferences("default", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(delegate)
}