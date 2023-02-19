package com.linroid.klipperx.storage

import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlin.test.Test
import kotlin.test.assertEquals

class SettingsTest {
    @Test
    fun whenSaveShouldReadSame() {
        val settings = createSettings()
        val value = "KlipperX"
        val key = "name"
        settings[key] = value
        assertEquals(value, settings[key])
    }
}