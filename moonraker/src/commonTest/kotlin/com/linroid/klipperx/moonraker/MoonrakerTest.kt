package com.linroid.klipperx.moonraker.com.linroid.klipperx.moonraker

import com.linroid.klipperx.moonraker.Moonraker
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertNotNull

class MoonrakerTest {

    @Test
    fun testTakeToken() {
        runBlocking {
            val moonraker = Moonraker(
                host = "voron.local",
                port = 80,
            )
            assertNotNull(moonraker.takeToken())
        }
    }
}