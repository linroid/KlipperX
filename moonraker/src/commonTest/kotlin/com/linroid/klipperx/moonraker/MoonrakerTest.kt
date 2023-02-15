package com.linroid.klipperx.moonraker

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class MoonrakerTest {

    @Test
    fun testTakeToken() = runTest {
        val moonraker = Moonraker(
            host = "voron.local",
            port = 80,
        )
        assertNotNull(moonraker.takeToken())
    }
}